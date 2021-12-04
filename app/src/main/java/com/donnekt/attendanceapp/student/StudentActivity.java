package com.donnekt.attendanceapp.student;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.classroom.Classroom;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_spinner_item;
import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {

    EditText editTextFName, editTextLName, editTextRegNum, editTextPhone, editTextEmail;
    RadioGroup radioGroupGender;
    Spinner spinnerClassId;
    ProgressBar loadingProgBar;
    Button registerBtn, viewStudentsButton;

    private ArrayList<Classroom> classroomArrayList;
    private final ArrayList<String> classes = new ArrayList<>();
    private int selectedClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        loadingProgBar = findViewById(R.id.loadingProgress);

        editTextFName = findViewById(R.id.editTextFName);
        editTextLName = findViewById(R.id.editTextLName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextRegNum = findViewById(R.id.editTextRegNum);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupGender = findViewById(R.id.radioGender);
        spinnerClassId = findViewById(R.id.spinnerClassId);
        registerBtn = findViewById(R.id.registerButton);
        viewStudentsButton= findViewById(R.id.viewStudentsButton);

        registerBtn.setOnClickListener(this);
        viewStudentsButton.setOnClickListener(this);

        spinnerClassId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (Classroom classModel : classroomArrayList) {
                    if (spinnerClassId.getSelectedItem().toString().equals(classModel.getClassroomName())) {
                        selectedClass = classModel.getClassroomId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        loadAllClasses();
    }

    private void loadAllClasses() {
        showDamnProgressDialog(this, "Loading...","Getting classrooms",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.CLASS_VIEW_ALL,
                response -> {
                    Log.d("string", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){
                            classroomArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("classrooms");

                            for (int i = 0; i < dataArray.length(); i++) {
                                Classroom cModel = new Classroom();
                                JSONObject dataObj = dataArray.getJSONObject(i);

                                cModel.setClassroomId(dataObj.getInt("class_id"));
                                cModel.setClassroomName(dataObj.getString("class_name"));
                                cModel.setClassroomLevel(dataObj.getString("class_level"));
                                classroomArrayList.add(cModel);
                            }

                            for (Classroom c : classroomArrayList) {
                                classes.add(String.valueOf(c.getClassroomName()));
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(StudentActivity.this, simple_spinner_item, classes);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinnerClassId.setAdapter(spinnerArrayAdapter);
                            exitDamnProgressDialog();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show());
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    private void registerStudent() {
        final String SFName = editTextFName.getText().toString().trim();
        final String SLName = editTextLName.getText().toString().trim();
        final String SRegNo = editTextRegNum.getText().toString().trim();
        final String SPhone = editTextPhone.getText().toString().trim();
        final String SEmail = editTextEmail.getText().toString().trim();
        final String SGender= ((RadioButton) findViewById(
                radioGroupGender.getCheckedRadioButtonId())).getText().toString();
        final String SClassId = String.valueOf(selectedClass);

        //first we will do the validations
        if (TextUtils.isEmpty(SFName)) {
            editTextFName.setError("Please enter firstname");
            editTextFName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(SRegNo)) {
            editTextFName.setError("Please enter Reg.Number");
            editTextFName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(SLName)) {
            editTextFName.setError("Please enter lastname");
            editTextFName.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(SPhone)) {
            editTextPhone.setError("Please enter telephone");
            editTextPhone.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(SEmail)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(SEmail).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        // Finalize operation!
        transferDataUsingVolley(SFName, SLName, SRegNo, SEmail, SPhone, SGender, SClassId);
    }

    private void transferDataUsingVolley(String sfName, String slName, String sRegNo, String sEmail, String sPhone, String sGender, String sClass) {
        loadingProgBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.STUD_REGISTER, response -> {
            // Inside on response method we are hiding & emptying EditText
            try {
                loadingProgBar.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(StudentActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                emptyEditTextAfterInsertion();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(StudentActivity.this, "Network Issues", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", sfName);
                params.put("lastname", slName);
                params.put("reg_number", sRegNo);
                params.put("email", sEmail);
                params.put("telephone", sPhone);
                params.put("gender", sGender);
                params.put("class_id", sClass);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void emptyEditTextAfterInsertion() {
        editTextFName.getText().clear();
        editTextLName.getText().clear();
        editTextPhone.getText().clear();
        editTextRegNum.getText().clear();
        editTextEmail.getText().clear();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        // I decided not to use this shit

        switch (view.getId()) {
            case R.id.registerButton:
                registerStudent();
                break;

            case R.id.viewStudentsButton:
                startActivity(new Intent(StudentActivity.this, StudentViewAll.class));
                break;
        }
    }
}