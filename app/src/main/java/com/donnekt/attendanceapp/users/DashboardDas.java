package com.donnekt.attendanceapp.users;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.*;
import com.donnekt.attendanceapp.department.Department;
import com.donnekt.attendanceapp.department.DepartmentAdapter;
import com.donnekt.attendanceapp.staff.Staff;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import static com.donnekt.attendanceapp.DialogShit.*;

public class DashboardDas extends AppCompatActivity {

    TextView dashboardHeader, staffCounts, studentCounts, deptCounts, classCounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_das);

        staffCounts = findViewById(R.id.staffCounts);
        studentCounts = findViewById(R.id.studentCounts);
        deptCounts = findViewById(R.id.deptCounts);
        classCounts = findViewById(R.id.classCounts);


        dashboardHeader = findViewById(R.id.dashboard_header);

        // Use this from Dialog class
        getAllCounts();

        // Getting shit done!
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {

            Staff staffMember = SharedPrefManager.getInstance(this).getLoggedInStaff();
            dashboardHeader.setText(staffMember.getRole()+": "+staffMember.getFirstname()+" "+staffMember.getLastname());
            Toast.makeText(getApplicationContext(), staffMember.getFirstname(), Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(DashboardDas.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }

    private void getAllCounts() {
        showDamnProgressDialog(this, "Loading...","Retrieving data...",false);

        StringRequest countDeptReq = new StringRequest(Request.Method.GET, URLs.DEPT_VIEW_ALL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                //Damn it!
                deptCounts.setText(object.optString("counts"));
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countDeptReq);

        StringRequest countStaffReq = new StringRequest(Request.Method.GET, URLs.STAFF_VIEW_ALL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                // Push shit!
                staffCounts.setText(object.optString("counts"));
                exitDamnProgressDialog();
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countStaffReq);

        StringRequest countClassReq = new StringRequest(Request.Method.GET, URLs.CLASS_VIEW_ALL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                // Push shit!
                classCounts.setText(object.optString("counts"));
                exitDamnProgressDialog();
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countClassReq);

        StringRequest countStudReq = new StringRequest(Request.Method.GET, URLs.STUD_VIEW_ALL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                // Push shit!
                studentCounts.setText(object.optString("counts"));
                exitDamnProgressDialog();
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countStudReq);

    }
}