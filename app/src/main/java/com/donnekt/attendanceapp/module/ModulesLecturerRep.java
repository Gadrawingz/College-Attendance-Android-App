package com.donnekt.attendanceapp.module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.users.DashboardHod;
import com.donnekt.attendanceapp.users.DashboardLecturer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ModulesLecturerRep extends AppCompatActivity {

    List<Module> moduleList;
    ListView listViewModules;
    String  LECTURER_REPORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modules_lecturer_rep);

        listViewModules = findViewById(R.id.listViewModules);
        moduleList = new ArrayList<>();

        LECTURER_REPORT = getIntent().getStringExtra("lecturer_id_key");
        showAllModules();

        TextView mainTitle = findViewById(R.id.mainTitle);
        mainTitle.setText("Lecturer's modules");
        mainTitle.setOnClickListener(v -> {
            Intent intent=new Intent(ModulesLecturerRep.this, DashboardHod.class);
            startActivity(intent);
        });
    }

    private void showAllModules() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);
        Staff staffMember = SharedPrefManager.getInstance(this).getLoggedInStaff();
        int staffId = staffMember.getStaffId();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.MOD_VIEW_LECT+LECTURER_REPORT, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            findViewById(R.id.mainTitle).setVisibility(View.VISIBLE);

            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("modulelects");

                for(int i=0; i<dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);

                    Module module = new Module(
                            dataObject.getInt("module_id"),
                            dataObject.getString("module_name"),
                            dataObject.getString("module_code"),
                            dataObject.getInt("lecturer_id"),
                            dataObject.getString("firstname"),
                            dataObject.getString("lastname"),
                            dataObject.getString("telephone"),
                            dataObject.getString("email"),
                            dataObject.getString("staff_role")
                    );

                    moduleList.add(module);
                    ModuleAdapterMLReport adapter = new ModuleAdapterMLReport(moduleList, getApplicationContext());
                    listViewModules.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network!", Toast.LENGTH_LONG).show());
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}