package com.donnekt.attendanceapp.classroom;

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
import com.donnekt.attendanceapp.department.DepartmentActivity;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ClassroomActivity extends AppCompatActivity implements View.OnClickListener {

    TextView viewClassrooms, exitClassrooms;
    EditText className, classDept;
    Spinner classLevel;
    Button saveButton;
    ProgressBar isDataLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        viewClassrooms = findViewById(R.id.tvViewClassrooms);
        exitClassrooms = findViewById(R.id.tvExitClassrooms);

        className = (EditText) findViewById(R.id.editClassName);
        classLevel= (Spinner) findViewById(R.id.spinnerClassLevel);
        classDept = (EditText) findViewById(R.id.spinnerClassDept);
        //classDept = (Spinner) findViewById(R.id.spinnerClassDept);
        isDataLoading = findViewById(R.id.dataLoading);
        saveButton = findViewById(R.id.buttonAddClass);

        // Save & View events killInQ
        saveButton.setOnClickListener(this);
        viewClassrooms.setOnClickListener(this);
        exitClassrooms.setOnClickListener(this);
    }

    // This method will validate the name
    private boolean inputsAreCorrect(String name) {
        if (name.isEmpty()) {
            className.setError("Please enter class name");
            className.requestFocus();
            return false;
        }
        return true;
    }

    // In this method we will do the create operation
    private void addClassroom() {
        String name = className.getText().toString().trim();
        String level= classLevel.getSelectedItem().toString().trim();
        String dept = classDept.getText().toString().trim();
        // String dept = classDept.getSelectedItem().toString().trim();
        // Validating the inputs
        if(inputsAreCorrect(name)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CLASS_CREATE,
                    response -> {
                        try {
                            isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(ClassroomActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            className.getText().clear();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("class_name", name);
                    params.put("class_level", level);
                    params.put("dept_id", dept);
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
            case R.id.buttonAddClass:
                addClassroom();
                break;

            // case: click to view all
            case R.id.tvViewClassrooms:
                startActivity(new Intent(this, ClassroomViewAll.class));
                break;

            // case: click to quit departments
            case R.id.tvExitClassrooms:
                startActivity(new Intent(this, AdminDashboard.class));
                break;
        }
    }
}