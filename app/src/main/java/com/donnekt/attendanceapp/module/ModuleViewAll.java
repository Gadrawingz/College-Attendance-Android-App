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
import com.donnekt.attendanceapp.department.DepartmentViewAll;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.users.StaffMenus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ModuleViewAll extends AppCompatActivity {

    List<Module> moduleList;
    ListView listViewModules;
    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_view_all);

        listViewModules = findViewById(R.id.listViewModules);
        mainTitle = findViewById(R.id.mainTitle);
        moduleList = new ArrayList<>();
        Staff user = SharedPrefManager.getInstance(this).getLoggedInStaff();
        mainTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sentWord = user.getRole();
                Intent intent =new Intent(ModuleViewAll.this, StaffMenus.class);
                intent.putExtra("sent_role", sentWord);
                startActivity(intent);
            }
        });

        // Method for displaying data in the list
        showAllModules();
    }

    private void showAllModules() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.MOD_VIEW_ALL, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("modules");

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
                    ModuleAdapter adapter = new ModuleAdapter(moduleList, getApplicationContext());
                    listViewModules.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network error!", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

}