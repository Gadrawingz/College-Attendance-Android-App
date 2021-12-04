package com.donnekt.attendanceapp.department;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.users.StaffMenus;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DepartmentUpdate extends AppCompatActivity implements View.OnClickListener {

    TextView exitDepartments;
    EditText deptName, deptCaption;
    Button addDeptButton;
    ProgressBar isDataLoading;
    String DEPT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_update);

        exitDepartments = findViewById(R.id.tvExitDepartments);

        deptName = findViewById(R.id.editDeptName);
        deptCaption = findViewById(R.id.editDeptCaption);
        addDeptButton= findViewById(R.id.buttonAddDept);
        isDataLoading= findViewById(R.id.dataLoading);

        DEPT_ID = getIntent().getStringExtra("d_id_key");
        deptName.setText(getIntent().getStringExtra("d_name_key"));
        deptCaption.setText(getIntent().getStringExtra("d_caption_key"));

        exitDepartments.setOnClickListener(this);
        addDeptButton.setOnClickListener(this);
    }

    // This method will validate the name and caption
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
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

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void addDepartment() {
        String name = deptName.getText().toString().trim();
        String caption = deptCaption.getText().toString().trim();
        isDataLoading.setVisibility(View.VISIBLE);

        // Validating the inputs
        if(inputsAreCorrect(name, caption)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.DEPT_UPDATE+DEPT_ID,
                    response -> {
                        try {
                            isDataLoading.setVisibility(View.GONE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(DepartmentUpdate.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(DepartmentUpdate.this, DepartmentViewAll.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(getApplicationContext(), "Network Issues", Toast.LENGTH_SHORT).show()) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("dept_name", name);
                    params.put("dept_caption", caption);
                    return params;
                }
            };
            VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
        }
    }

    @SuppressLint({"NewApi", "NonConstantResourceId"})
    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add Dept
            case R.id.buttonAddDept:
                addDepartment();
                break;

            // case: click to quit
            case R.id.tvExitDepartments:
                startActivity(new Intent(this, StaffMenus.class));
                break;
        }
    }
}