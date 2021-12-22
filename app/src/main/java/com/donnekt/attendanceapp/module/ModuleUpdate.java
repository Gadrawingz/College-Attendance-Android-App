package com.donnekt.attendanceapp.module;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import com.donnekt.attendanceapp.classroom.ClassroomActivity;
import com.donnekt.attendanceapp.department.Department;
import com.donnekt.attendanceapp.staff.Staff;
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
import static com.donnekt.attendanceapp.Methods.*;

public class ModuleUpdate extends AppCompatActivity implements View.OnClickListener {

    EditText moduleName, moduleCode;
    Spinner lecturerId;
    TextView doViewModules, addModuleBtn;
    ProgressBar isDataLoading;
    String MODULE_ID;

    private ArrayList<Staff> staffArrayList;
    private final ArrayList<String> lecturers = new ArrayList<>();
    private int selectedLecturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_update);

        moduleName = findViewById(R.id.editModuleName);
        moduleCode = findViewById(R.id.editModuleCode);
        lecturerId = findViewById(R.id.spinnerLecturer);
        isDataLoading = findViewById(R.id.dataLoading);
        addModuleBtn = findViewById(R.id.buttonSave);
        doViewModules= findViewById(R.id.tvViewModules);

        MODULE_ID = getIntent().getStringExtra("m_id_key");
        moduleName.setText(getIntent().getStringExtra("m_name_key"));
        moduleCode.setText(getIntent().getStringExtra("m_code_key"));

        // Save & View button
        doViewModules.setOnClickListener(this);
        addModuleBtn.setOnClickListener(this);
        loadAllLecturers();

        lecturerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (Staff staff : staffArrayList) {
                    if(lecturerId.getSelectedItem().toString().trim().equals(staff.getFirstname()+" "+staff.getLastname())) {
                        selectedLecturer = staff.getStaffId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });
    }


    private void loadAllLecturers() {
        showDamnProgressDialog(this, "Loading...","Getting lecturers",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STAFF_LECTURERS,
                response -> {
                    Log.d("strrrrr", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){
                            staffArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("lecturers");

                            for (int i = 0; i < dataArray.length(); i++) {
                                Staff staffModel = new Staff();
                                JSONObject dataObj = dataArray.getJSONObject(i);
                                staffModel.setStaffId(dataObj.getInt("staff_id"));
                                staffModel.setFirstname(dataObj.getString("firstname"));
                                staffModel.setLastname(dataObj.getString("lastname"));
                                staffArrayList.add(staffModel);
                            }

                            for (Staff staff : staffArrayList) {
                                String FULL_NAME = capitalizeAllFirstLetters(staff.getFirstname()+" "+staff.getLastname());
                                lecturers.add(FULL_NAME);
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ModuleUpdate.this, simple_spinner_item, lecturers);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            lecturerId.setAdapter(spinnerArrayAdapter);
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


    // This method will validate the name and caption
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private boolean inputsAreCorrect(String name, String code) {
        if (name.isEmpty()) {
            moduleName.setError("Please enter module name");
            moduleName.requestFocus();
            return false;
        }

        if (code.isEmpty()) {
            moduleCode.setError("Please add module code");
            moduleCode.requestFocus();
            return false;
        }

        return true;
    }

    // In this method we will do the create operation
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void updateModule() {
        String mName = (moduleName.getText().toString().trim());
        String mCode = (moduleCode.getText().toString().trim());
        String lId   = String.valueOf(selectedLecturer);
        isDataLoading.setVisibility(View.VISIBLE);

        // Validating the inputs
        if (inputsAreCorrect(mName, mCode)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.MOD_UPDATE+MODULE_ID,
                    response -> {
                        try {
                            isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(ModuleUpdate.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            //Toast.makeText(ModuleUpdate.this, "Module:"+mName+" MCOde: "+mCode+" LECT: "+lId, Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ModuleUpdate.this, ModuleViewAll.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("module_name", mName);
                    params.put("module_code", mCode);
                    params.put("lecturer_id", lId);
                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            moduleName.getText().clear();
            moduleCode.getText().clear();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add
            case R.id.buttonSave:
                updateModule();
                break;

            // case: click to view all
            case R.id.tvViewModules:
                startActivity(new Intent(this, ModuleViewAll.class));
                break;
        }
    }
}