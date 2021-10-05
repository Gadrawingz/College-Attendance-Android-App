package com.donnekt.attendanceapp.student;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class StudentActivity extends AppCompatActivity {

    EditText editTextFName, editTextLName, editTextRegNum, editTextPhone, editTextEmail, spinnerClassId;
    RadioGroup radioGroupGender;
    //Spinner spinnerClassId;
    ProgressBar loadingProgBar;
    Button registerBtn, viewStudentsButton;

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

        registerBtn.setOnClickListener(view -> registerStudent());
        viewStudentsButton.setOnClickListener(v -> startActivity(new Intent(StudentActivity.this, StudentViewAll.class)));
    }

    private void registerStudent() {
        final String SFName = editTextFName.getText().toString().trim();
        final String SLName = editTextLName.getText().toString().trim();
        final String SRegNo = editTextRegNum.getText().toString().trim();
        final String SPhone = editTextPhone.getText().toString().trim();
        final String SEmail = editTextEmail.getText().toString().trim();
        final String SGender= ((RadioButton) findViewById(
                radioGroupGender.getCheckedRadioButtonId())).getText().toString();
        //final String SRoles= spinnerRoles.getSelectedItem().toString();
        final String SClass = spinnerClassId.getText().toString().trim();

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
        transferDataUsingVolley(SFName, SLName, SRegNo, SEmail, SPhone, SGender, SClass);
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

        }, error -> Toast.makeText(StudentActivity.this, error.toString(), Toast.LENGTH_LONG).show()) {
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


}