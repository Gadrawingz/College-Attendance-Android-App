package com.donnekt.attendanceapp.classroom;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.department.Department;
import com.donnekt.attendanceapp.users.StaffMenus;
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

public class ClassroomUpdate extends AppCompatActivity implements View.OnClickListener {

    TextView viewClassrooms, saveButton;
    EditText className;
    Spinner classLevel, classDept;
    ProgressBar isDataLoading;
    String CLASS_ID;

    @SuppressWarnings("deprecation")
    private static ProgressDialog mProgressDialog;
    private ArrayList<Department> departmentArrayList;

    private final ArrayList<String> departments = new ArrayList<>();
    private int selectedDept;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_update);

        viewClassrooms = findViewById(R.id.tvViewClassrooms);
        className = findViewById(R.id.editClassName);
        classLevel= findViewById(R.id.spinnerClassLevel);
        isDataLoading = findViewById(R.id.dataLoading);
        saveButton = findViewById(R.id.buttonAddClass);

        // Departments should be dynamic shits
        classDept = findViewById(R.id.spinnerClassDept);

        CLASS_ID = getIntent().getStringExtra("c_id_key");
        className.setText(getIntent().getStringExtra("c_name_key"));

        classDept.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                //String item = (String) adapterView.getItemAtPosition(pos);
                for (Department department : departmentArrayList) {
                    if(classDept.getSelectedItem().toString().trim().equals(department.getDepartmentName())) {
                        List<String> departs = new ArrayList<>();
                        selectedDept = department.getDepartmentId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

        loadAllDepartments();

        // Save & View events killInQ
        saveButton.setOnClickListener(this);
        viewClassrooms.setOnClickListener(this);
    }


    private void loadAllDepartments() {
        showDamnProgressDialog(this, "Loading...","Getting departments",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.DEPT_VIEW_ALL,
                response -> {
                    Log.d("strrrrr", ">>" + response);
                    try {

                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){
                            departmentArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("departments");

                            for (int i = 0; i < dataArray.length(); i++) {

                                Department deptModel = new Department();

                                JSONObject dataObj = dataArray.getJSONObject(i);

                                deptModel.setDepartmentId(dataObj.getInt("dept_id"));
                                deptModel.setDepartmentName(dataObj.getString("dept_name"));
                                deptModel.setDepartmentCaption(dataObj.getString("dept_caption"));
                                departmentArrayList.add(deptModel);
                            }

                            for (Department department : departmentArrayList) {
                                departments.add(department.getDepartmentName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ClassroomUpdate.this, simple_spinner_item, departments);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            classDept.setAdapter(spinnerArrayAdapter);
                            exitDamnProgressDialog();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    //displaying the error in toast if occurs
                    Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show();
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // This method will validate the name
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private boolean inputsAreCorrect(String name) {
        if (name.isEmpty()) {
            className.setError("Please enter class name");
            className.requestFocus();
            return false;
        }
        return true;
    }

    // In this method we will do the create operation
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void updateClassroom() {
        String name = className.getText().toString().trim();
        String level= classLevel.getSelectedItem().toString().trim();
        String dept = String.valueOf(selectedDept);

        // Validating the inputs
        if(inputsAreCorrect(name)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CLASS_UPDATE+CLASS_ID,
                    response -> {
                        try {
                            isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(ClassroomUpdate.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            className.getText().clear();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show()) {
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

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add Dept
            case R.id.buttonAddClass:
                updateClassroom();
                break;

            // case: click to view all
            case R.id.tvViewClassrooms:
                startActivity(new Intent(this, ClassroomViewAll.class));
                break;
        }
    }
}