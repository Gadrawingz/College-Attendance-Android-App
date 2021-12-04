package com.donnekt.attendanceapp.staff;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;

import java.util.HashMap;
import java.util.Map;

public class StaffActivity extends AppCompatActivity {

    EditText editTextFName, editTextLName, editTextPhone, editTextEmail, editTextPassword;
    RadioGroup radioGroupGender;
    Spinner spinnerRoles;
    ProgressBar loadingProgBar;
    Button registerBtn, viewStaffsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);
        loadingProgBar = findViewById(R.id.loadingProgress);

        editTextFName = findViewById(R.id.editTextFName);
        editTextLName = findViewById(R.id.editTextLName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        radioGroupGender = findViewById(R.id.radioGender);
        spinnerRoles = findViewById(R.id.spinnerRoles);
        registerBtn = findViewById(R.id.registerButton);
        viewStaffsButton= findViewById(R.id.viewStaffsButton);

        registerBtn.setOnClickListener(view -> registerStaffMember());

        viewStaffsButton.setOnClickListener(v -> {
            Intent intent = new Intent(StaffActivity.this, StaffViewAll.class);
            startActivity(intent);
        });
    }

    private void registerStaffMember() {
        final String SFName = editTextFName.getText().toString().trim();
        final String SLName = editTextLName.getText().toString().trim();
        final String SPhone = editTextPhone.getText().toString().trim();
        final String SEmail = editTextEmail.getText().toString().trim();
        final String SGender= ((RadioButton) findViewById(
                radioGroupGender.getCheckedRadioButtonId())).getText().toString();
        final String SRoles= spinnerRoles.getSelectedItem().toString();
        final String SPass = editTextPassword.getText().toString().trim();

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
        transferDataUsingVolley(SFName, SLName, SEmail, SPhone, SGender, SRoles, SPass);
    }

    private void transferDataUsingVolley(String FN, String LN, String EM, String PH, String GD, String RL, String PS) {
        loadingProgBar.setVisibility(View.VISIBLE);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.STAFF_REGISTER, response -> {

            // Inside on response method we are hiding & emptying EditText
            loadingProgBar.setVisibility(View.GONE);
            Toast.makeText(StaffActivity.this, response, Toast.LENGTH_LONG).show();
        }, error -> Toast.makeText(StaffActivity.this, "Network error!", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", FN);
                params.put("lastname", LN);
                params.put("telephone", PH);
                params.put("email", EM);
                params.put("gender", GD);
                params.put("staff_role", RL);
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