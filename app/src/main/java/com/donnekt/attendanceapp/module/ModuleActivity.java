package com.donnekt.attendanceapp.module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class ModuleActivity extends AppCompatActivity implements View.OnClickListener {

    EditText moduleName, moduleCode, deptId, lecturerId;
    Button addModuleBtn, doViewModules;
    ProgressBar isDataLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        moduleName = findViewById(R.id.editModuleName);
        moduleCode = findViewById(R.id.editModuleCode);
        deptId     = findViewById(R.id.editDeptId);
        lecturerId = findViewById(R.id.editLecturerId);
        isDataLoading = findViewById(R.id.dataLoading);
        addModuleBtn = findViewById(R.id.buttonSave);
        doViewModules= findViewById(R.id.buttonViewModules);

        // Save & View button
        doViewModules.setOnClickListener(this);
        addModuleBtn.setOnClickListener(this);
    }

    // This method will validate the name and caption
    private boolean inputsAreCorrect(String name, String code, String dept, String lect) {
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

        if (dept.isEmpty()) {
            deptId.setError("Make sure you add department");
            deptId.requestFocus();
            return false;
        }

        if (lect.isEmpty()) {
            lecturerId.setError("Please add lecturer");
            lecturerId.requestFocus();
            return false;
        }

        return true;
    }

    // In this method we will do the create operation
    private void addModule() {
        String mName = (moduleName.getText().toString().trim());
        String mCode = (moduleCode.getText().toString().trim());
        String dId   = (deptId.getText().toString().trim());
        String lId   = (lecturerId.getText().toString().trim());

        // Validating the inputs
        if (inputsAreCorrect(mName, mCode, dId, lId)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.MOD_CREATE,
                    response -> {
                        try {
                            isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(ModuleActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            moduleName.getText().clear();
                            moduleCode.getText().clear();
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
                    params.put("dept_id", dId);
                    params.put("lecturer_id", lId);
                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
            moduleName.getText().clear();
            moduleCode.getText().clear();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add
            case R.id.buttonSave:
                addModule();
                break;

            // case: click to view all
            case R.id.buttonViewModules:
                startActivity(new Intent(this, ModuleViewAll.class));
                //Toast.makeText(getApplicationContext(), "OOPS!", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}