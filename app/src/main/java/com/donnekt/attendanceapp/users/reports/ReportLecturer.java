package com.donnekt.attendanceapp.users.reports;

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
import com.donnekt.attendanceapp.users.StaffMenus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReportLecturer extends AppCompatActivity {


    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
    private final String CURRENT_DAY = dateFormat.format(calendar.getTime());
    List<Report> hodReportList;
    ListView listViewHodReport;
    int STAFF_ID;
    int DEPT_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_lecturer);

        listViewHodReport = findViewById(R.id.listViewHodReport);
        hodReportList = new ArrayList<>();

        Staff member = SharedPrefManager.getInstance(this).getLoggedInStaff();
        DEPT_ID = member.getRefDeptId();
        String DEPT_NAME = member.getRefDeptName();
        String STAFF_ROLE = member.getRole();
        STAFF_ID = member.getStaffId();


        TextView mainTitle = findViewById(R.id.mainTitle);
        mainTitle.setText("Attendance Report in "+DEPT_NAME);

        mainTitle.setOnClickListener(v -> {
            Intent i = new Intent(ReportLecturer.this, StaffMenus.class);
            i.putExtra("sent_role", STAFF_ROLE);
            startActivity(i);
        });

        // Display shit
        showHodReportData();
    }

    private void showHodReportData() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        //StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.REPORT_HOD_BY_DAY+CURRENT_DAY+"&dept="+DEPT_ID+"&staff="+STAFF_ID, response -> {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.REPORT_HOD_LECTURER+DEPT_ID, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            findViewById(R.id.mainTitle).setVisibility(View.VISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("hod_rep_lecturers");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);

                    Report report = new Report(
                            dataObject.getInt("staff_id"),
                            dataObject.getString("firstname"),
                            dataObject.getString("lastname")
                    );

                    hodReportList.add(report);
                    HodReportAdapter adapter = new HodReportAdapter(hodReportList, getApplicationContext());
                    listViewHodReport.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network!", Toast.LENGTH_LONG).show());
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}