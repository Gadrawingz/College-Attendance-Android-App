package com.donnekt.attendanceapp.users;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.*;
import com.donnekt.attendanceapp.admin.AdminDashboard;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.staff.StaffDashboard;
import org.json.JSONException;
import org.json.JSONObject;

import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

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
        //getAllCounts();

        // Getting shit done!
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {

            Staff staffMember = SharedPrefManager.getInstance(this).getLoggedInStaff();
            String activeRole = staffMember.getRole();

            dashboardHeader.setText(activeRole+": "+staffMember.getFirstname()+" "+staffMember.getLastname());
            //Toast.makeText(getApplicationContext(), staffMember.getFirstname(), Toast.LENGTH_SHORT).show();

            findViewById(R.id.manageActs).setOnClickListener(v -> {
                String sentWord = activeRole;
                Intent i = new Intent(DashboardDas.this, StaffMenus.class);
                i.putExtra("sent_role", sentWord);
                startActivity(i);
            });

            findViewById(R.id.tvSettings).setOnClickListener(v -> {
                String sentWord = activeRole;
                Intent i = new Intent(DashboardDas.this, StaffDashboard.class);
                i.putExtra("sent_role", sentWord);
                startActivity(i);
            });


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