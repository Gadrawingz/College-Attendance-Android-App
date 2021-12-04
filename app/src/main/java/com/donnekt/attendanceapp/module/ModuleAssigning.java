package com.donnekt.attendanceapp.module;

import android.annotation.SuppressLint;
import android.content.Entity;
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
import com.donnekt.attendanceapp.classroom.Classroom;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static android.R.layout.simple_spinner_item;
import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

public class ModuleAssigning extends AppCompatActivity implements View.OnClickListener {

    private final ArrayList<String> classes = new ArrayList<>();
    TextView tvModuleName;
    Button saveMA, viewMA;
    Spinner classToGo;
    ProgressBar isDataLoading;
    private ArrayList<Classroom> classArrayList;
    private int selectedClass;
    private String receivedModule;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_assigning);

        tvModuleName = findViewById(R.id.tvModuleName);
        classToGo = findViewById(R.id.spinnerClassroom);
        saveMA = findViewById(R.id.buttonSaveMA);
        viewMA = findViewById(R.id.buttonViewMA);
        String moduleName = getIntent().getStringExtra("module_name_key");
        tvModuleName.setText("Course: " + moduleName);
        saveMA.setOnClickListener(this);
        viewMA.setOnClickListener(this);
        loadRegisteredClasses();

        String MID = getIntent().getStringExtra("module_id_key");

        classToGo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (Classroom classModel : classArrayList) {
                    if (classToGo.getSelectedItem().toString().trim().equals(classModel.getClassroomName())) {
                        selectedClass = classModel.getClassroomId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void loadRegisteredClasses() {
        showDamnProgressDialog(this, "Loading...", "Getting classrooms", false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.CLASS_VIEW_ALL,
                response -> {
                    Log.d("strrrrr", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if (obj.optString("status").equals("fetched")) {

                            classArrayList = new ArrayList<>();
                            JSONArray dataArray = obj.getJSONArray("classrooms");

                            for (int i = 0; i < dataArray.length(); i++) {
                                Classroom classModel = new Classroom();
                                JSONObject dataObj = dataArray.getJSONObject(i);

                                classModel.setClassroomId(dataObj.getInt("class_id"));
                                classModel.setClassroomName(dataObj.getString("class_name"));
                                classModel.setClassroomLevel(dataObj.getString("class_level"));
                                classArrayList.add(classModel);
                            }

                            for (Classroom class1 : classArrayList) {
                                classes.add(class1.getClassroomName());
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ModuleAssigning.this, simple_spinner_item, classes);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            classToGo.setAdapter(spinnerArrayAdapter);
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


    private void assignModule() {
        String moduleId = getIntent().getStringExtra("module_id_key");
        String classID = String.valueOf(selectedClass);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.MOD_C_GROUP,
                    response -> {
                        try {
                            //isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(ModuleAssigning.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), "Something is wrong!", Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("module_id", moduleId);
                    params.put("class_id", classID);
                    return checkParams(params);
                }
        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        //Toast.makeText(getApplicationContext(), classID, Toast.LENGTH_SHORT).show();
    }

    private Map<String, String> checkParams(Map<String, String> map) {
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while(it.hasNext()) {
            Map.Entry<String, String> pairs = (Map.Entry<String, String>)it.next();
            if(pairs.getValue()==null) {
                map.put(pairs.getKey(), "");
            }
        }
        return map;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add
            case R.id.buttonSaveMA:
                assignModule();
                break;

            // case: click to view all
            case R.id.buttonViewMA:
                startActivity(new Intent(this, ModuleViewAll.class));
                break;
        }
    }

}