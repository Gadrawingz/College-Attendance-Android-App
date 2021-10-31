package com.donnekt.attendanceapp.module;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.staff.Staff;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ModuleDetails extends AppCompatActivity {

    List<Module> moduleList;
    ListView listViewMDetails;
    Button exitDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_details);

        listViewMDetails = findViewById(R.id.listViewMDetails);
        moduleList = new ArrayList<>();
        showAllModules();

        /*findViewById(R.id.exitDetails).setOnClickListener(view -> {
            finish();
            startActivity(new Intent(getApplicationContext(), ModulesLecturer.class));
        });*/
    }

    private void showAllModules() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        // Get SESSION_ID
        Staff staffMember = SharedPrefManager.getInstance(this).getLoggedInStaff();
        int staffId = staffMember.getStaffId();

        // Getting ID shit
        String moduleIdKey = getIntent().getStringExtra("module_id_key");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.MOD_VIEW_ONE+moduleIdKey, response -> {
            progressBar.setVisibility(View.INVISIBLE);

            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("module");

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
                    ModuleAdapterDetail ad = new ModuleAdapterDetail(moduleList, getApplicationContext());
                    listViewMDetails.setAdapter(ad);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show());
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}
