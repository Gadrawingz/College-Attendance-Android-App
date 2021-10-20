package com.donnekt.attendanceapp.department;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.admin.AdminDashboard;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener {

    TextView viewDepartments, exitDepartments;
    EditText deptName, deptCaption;
    Button addDeptButton;
    ProgressBar isDataLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        viewDepartments = (TextView) findViewById(R.id.tvViewDepartments);
        exitDepartments = (TextView) findViewById(R.id.tvExitDepartments);

        deptName = (EditText) findViewById(R.id.editDeptName);
        deptCaption = (EditText) findViewById(R.id.editDeptCaption);
        addDeptButton = (Button) findViewById(R.id.buttonAddDept);
        isDataLoading = findViewById(R.id.dataLoading);

        // Save & View Dept button
        viewDepartments.setOnClickListener(this);
        exitDepartments.setOnClickListener(this);
        addDeptButton.setOnClickListener(this);
    }

    // This method will validate the name and caption
    private boolean inputsAreCorrect(String name, String caption) {
        if (name.isEmpty()) {
            deptName.setError("Please enter a name");
            deptName.requestFocus();
            return false;
        }

        if (caption.isEmpty()) {
            deptCaption.setError("Please add description");
            deptCaption.requestFocus();
            return false;
        }
        return true;
    }

    // In this method we will do the create operation
    private void addDepartment() {
        String name = deptName.getText().toString().trim();
        String caption = deptCaption.getText().toString().trim();
        isDataLoading.setVisibility(View.VISIBLE);

        // Validating the inputs
        if(inputsAreCorrect(name, caption)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DEPT_CREATE,
                    response -> {
                        try {
                            isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(DepartmentActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            deptName.getText().clear();
                            deptCaption.getText().clear();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("dept_name", name);
                    params.put("dept_caption", caption);
                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add Dept
            case R.id.buttonAddDept:
                addDepartment();
                break;

            // case: click to view all dept
            case R.id.tvViewDepartments:
                startActivity(new Intent(this, DepartmentViewAll.class));
                break;

            // case: click to quit depts
            case R.id.tvExitDepartments:
                startActivity(new Intent(this, AdminDashboard.class));
                break;
        }
    }
}