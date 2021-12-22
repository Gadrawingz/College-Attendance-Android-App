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
import com.donnekt.attendanceapp.LoginActivity;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.ProfileActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
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

public class ProfileUpdate extends AppCompatActivity {

    EditText editTextFName, editTextLName, editTextPhone, editTextEmail;
    RadioGroup radioGroupGender;
    Spinner spinnerRoles, spinnerDeptId;
    ProgressBar loadingProgBar;
    Button registerBtn, changePass;

    private ArrayList<Department> departmentArrayList;
    private final ArrayList<String> departments = new ArrayList<>();
    private int receivedDepartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_update);
        loadingProgBar = findViewById(R.id.loadingProgress);

        editTextFName = findViewById(R.id.editTextFName);
        editTextLName = findViewById(R.id.editTextLName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        radioGroupGender = findViewById(R.id.radioGender);
        spinnerRoles = findViewById(R.id.spinnerRoles);
        spinnerDeptId = findViewById(R.id.spinnerDeptId);
        registerBtn = findViewById(R.id.registerButton);
        changePass = findViewById(R.id.buttonChangePass);

        Staff activeUser = SharedPrefManager.getInstance(this).getLoggedInStaff();
        String staffId = String.valueOf(activeUser.getStaffId());
        String staffFName = activeUser.getFirstname();
        String staffLName = activeUser.getLastname();
        String staffPhone = activeUser.getTelephone();
        String staffEmail = activeUser.getEmail();

        editTextFName.setText(staffFName);
        editTextLName.setText(staffLName);
        editTextEmail.setText(staffEmail);
        editTextPhone.setText(staffPhone);

        registerBtn.setOnClickListener(view -> registerStaffMember());

        changePass.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileUpdate.this, ChangePassword.class);
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

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ProfileUpdate.this, simple_spinner_item, departments);
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
        final String SRoles= spinnerRoles.getSelectedItem().toString();
        final String refDept = String.valueOf(receivedDepartment);

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
        transferDataUsingVolley(SFName, SLName, SEmail, SPhone, SGender, SRoles, refDept);
    }

    private void transferDataUsingVolley(String FN, String LN, String EM, String PH, String GD, String RL, String DP) {
        loadingProgBar.setVisibility(View.VISIBLE);

        Staff activeUser = SharedPrefManager.getInstance(this).getLoggedInStaff();
        String STAFF_ID = String.valueOf(activeUser.getStaffId());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.STAFF_UPDATE+STAFF_ID, response -> {
            try {
                loadingProgBar.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(ProfileUpdate.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                Staff staff = SharedPrefManager.getInstance(this).getLoggedInStaff();
                String activeStaffRole = staff.getRole();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                switch (activeStaffRole){
                    case "DAS":
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.putExtra("sent_key", "DAS");
                        startActivity(intent);
                        break;

                    case "HOD":
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent2.putExtra("sent_key", "HOD");
                        startActivity(intent2);
                        break;

                    case "DOQ":
                        Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent3.putExtra("sent_key", "DOQ");
                        startActivity(intent3);
                        break;

                    case "LECTURER":
                        Intent intent4 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent4.putExtra("sent_key", "LECTURER");
                        startActivity(intent4);
                        break;

                    case "DPAT":
                        Intent intent5 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent5.putExtra("sent_key", "DPAT");
                        startActivity(intent5);
                        break;

                    default:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(ProfileUpdate.this, "Network problem!", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("firstname", FN);
                params.put("lastname", LN);
                params.put("email", EM);
                params.put("telephone", PH);
                params.put("gender", GD);
                params.put("staff_role", RL);
                params.put("dept_id", DP);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}