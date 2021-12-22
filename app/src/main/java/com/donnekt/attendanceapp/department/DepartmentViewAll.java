package com.donnekt.attendanceapp.department;

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
import com.donnekt.attendanceapp.users.StaffMenus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DepartmentViewAll extends AppCompatActivity {

    List<Department> departmentList;
    ListView listViewDepartments;
    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_view_all);

        listViewDepartments = findViewById(R.id.listViewDepartments);
        mainTitle = findViewById(R.id.mainTitle);
        departmentList = new ArrayList<>();
        loadDepartments();

        Staff user = SharedPrefManager.getInstance(this).getLoggedInStaff();

        mainTitle.setOnClickListener(v -> {
            String sentWord = user.getRole();
            Intent intent =new Intent(DepartmentViewAll.this, StaffMenus.class);
            intent.putExtra("sent_role", sentWord);
            startActivity(intent);
        });
    }

    private void loadDepartments() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.DEPT_VIEW_ALL, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("departments");

                for(int i=0; i<dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);

                    Department department = new Department(
                            dataObject.getInt("dept_id"),
                            dataObject.getString("dept_name"),
                            dataObject.getString("dept_caption")
                    );
                    departmentList.add(department);
                    DepartmentAdapter adapter = new DepartmentAdapter(departmentList, getApplicationContext());
                    listViewDepartments.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network Issues", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}