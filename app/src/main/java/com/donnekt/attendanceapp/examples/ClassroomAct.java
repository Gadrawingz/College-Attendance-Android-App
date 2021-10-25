package com.donnekt.attendanceapp.examples;

/*
package com.donnekt.attendanceapp.classroom;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.admin.AdminDashboard;
import com.donnekt.attendanceapp.department.Department;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.layout.simple_spinner_item;

public class ClassroomActivity extends AppCompatActivity implements View.OnClickListener {

    TextView viewClassrooms, exitClassrooms;
    EditText className;
    Spinner classLevel, classDept;
    Button saveButton;
    ProgressBar isDataLoading;

    private static ProgressDialog mProgressDialog;
    private ArrayList<Department> departmentArrayList;

    private final ArrayList<String> departments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        viewClassrooms = findViewById(R.id.tvViewClassrooms);
        exitClassrooms = findViewById(R.id.tvExitClassrooms);

        className = findViewById(R.id.editClassName);
        classLevel= findViewById(R.id.spinnerClassLevel);

        // Departments should be dynamic shits
        classDept = findViewById(R.id.spinnerClassDept);
        // classDept.setOnItemSelectedListener();
        loadAllDepartments();

        isDataLoading = findViewById(R.id.dataLoading);
        saveButton = findViewById(R.id.buttonAddClass);

        // Save & View events killInQ
        saveButton.setOnClickListener(this);
        viewClassrooms.setOnClickListener(this);
        exitClassrooms.setOnClickListener(this);
    }

    private void loadAllDepartments() {
        showSimpleProgressDialog(this, "Loading...","Getting departments",false);
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

                            List<String> labels = new ArrayList<String>();
                            List<String> IDs = new ArrayList<String>();

                            for (Department department : departmentArrayList) {
                                departments.add(department.getDepartmentId() + ") " + department.getDepartmentName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ClassroomActivity.this, simple_spinner_item, departments);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            classDept.setAdapter(spinnerArrayAdapter);

                            removeSimpleProgressDialog();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    //displaying the error in toast if occurs
                    Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                });

        // request queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
        String depart = classDept.getSelectedItem().toString().trim();
        // String finalized1 = depart.lastIndexOf("", Integer.parseInt(depart));
        String dept = depart;

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


    @SuppressLint("NonConstantResourceId")
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


    public static void removeSimpleProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                    mProgressDialog = null;
                }
            }
        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();

        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showSimpleProgressDialog(Context context, String title, String msg, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = ProgressDialog.show(context, title, msg);
                mProgressDialog.setCancelable(isCancelable);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }

        } catch (IllegalArgumentException ie) {
            ie.printStackTrace();
        } catch (RuntimeException re) {
            re.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
*/