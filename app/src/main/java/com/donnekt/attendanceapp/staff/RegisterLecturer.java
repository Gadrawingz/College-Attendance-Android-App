package com.donnekt.attendanceapp.staff;

import static android.R.layout.simple_spinner_item;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.classroom.Classroom;
import com.donnekt.attendanceapp.department.Department;
import com.donnekt.attendanceapp.student.StudentActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RegisterLecturer extends AppCompatActivity {

    EditText editTextFName, editTextLName, editTextPhone, editTextEmail, editTextPassword;
    RadioGroup radioGroupGender;
    Spinner spinnerDeptId;
    ProgressBar loadingProgBar;
    Button registerBtn, viewStaffsButton;

    private ArrayList<Department> departmentArrayList;
    private final ArrayList<String> departments = new ArrayList<>();
    private int receivedDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_lecturer);
        loadingProgBar = findViewById(R.id.loadingProgress);

        editTextFName = findViewById(R.id.editTextFName);
        editTextLName = findViewById(R.id.editTextLName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        radioGroupGender = findViewById(R.id.radioGender);
        spinnerDeptId = findViewById(R.id.spinnerDeptId);
        registerBtn = findViewById(R.id.registerButton);
        viewStaffsButton= findViewById(R.id.viewStaffsButton);

        registerBtn.setOnClickListener(view -> registerStaffMember());

        viewStaffsButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegisterLecturer.this, StaffViewAll.class);
            startActivity(intent);
        });

        spinnerDeptId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (Department department : departmentArrayList) {
                    if (spinnerDeptId.getSelectedItem().toString().equals(department.getDepartmentName())) {
                        receivedDepartment = department.getDepartmentId();
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
        loadingDepartments();

    }

    private void loadingDepartments() {
        showDamnProgressDialog(this, "Loading...","Getting departments",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.DEPT_VIEW_ALL,
                response -> {
                    Log.d("string", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){
                            departmentArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("departments");

                            for (int i = 0; i < dataArray.length(); i++) {
                                Department d = new Department();
                                JSONObject dataObj = dataArray.getJSONObject(i);

                                d.setDepartmentId(dataObj.getInt("dept_id"));
                                d.setDepartmentName(dataObj.getString("dept_name"));
                                departmentArrayList.add(d);
                            }

                            for (Department department : departmentArrayList) {
                                departments.add(String.valueOf(department.getDepartmentName()));
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(RegisterLecturer.this, simple_spinner_item, departments);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            spinnerDeptId.setAdapter(spinnerArrayAdapter);
                            exitDamnProgressDialog();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "No network!", Toast.LENGTH_LONG).show());
        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void registerStaffMember() {
        final String SFName = editTextFName.getText().toString().trim();
        final String SLName = editTextLName.getText().toString().trim();
        final String SPhone = editTextPhone.getText().toString().trim();
        final String SEmail = editTextEmail.getText().toString().trim();
        final String SGender= ((RadioButton) findViewById(
                radioGroupGender.getCheckedRadioButtonId())).getText().toString();
        final String SPass = editTextPassword.getText().toString().trim();
        final String refDept = String.valueOf(receivedDepartment);

        //first we will do the validations
        if (TextUtils.isEmpty(SFName)) {
            editTextFName.setError("Please enter firstname");
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

        if (TextUtils.isEmpty(SPass)) {
            editTextPassword.setError("Enter a password");
            editTextPassword.requestFocus();
            return;
        }
        // Finalize operation!
        transferDataUsingVolley(SFName, SLName, SEmail, SPhone, SGender, refDept, SPass);
    }

    private void transferDataUsingVolley(String FN, String LN, String EM, String PH, String GD, String DP, String PS) {
        loadingProgBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.STAFF_REGISTER, response -> {
            loadingProgBar.setVisibility(View.GONE);
            Toast.makeText(RegisterLecturer.this, response, Toast.LENGTH_LONG).show();
        }, error -> Toast.makeText(RegisterLecturer.this, "No Network!", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", FN);
                params.put("lastname", LN);
                params.put("telephone", PH);
                params.put("email", EM);
                params.put("gender", GD);
                params.put("staff_role", "LECTURER");
                params.put("dept_id", DP);
                params.put("password", PS);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
        emptyEditTextAfterInsertion();
    }

    private void emptyEditTextAfterInsertion() {
        editTextFName.getText().clear();
        editTextLName.getText().clear();
        editTextPhone.getText().clear();
        editTextEmail.getText().clear();
        editTextPassword.getText().clear();
    }
}