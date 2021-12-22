package com.donnekt.attendanceapp.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.classroom.ClassroomGroup;
import com.donnekt.attendanceapp.module.ModulesLecturer;
import com.donnekt.attendanceapp.staff.Staff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceUpdate extends AppCompatActivity {

    List<Student> studentList;
    ListView lvStudentsList;
    String FINAL_MODULE_ID, FINAL_CLASS_ID, FINAL_ATT_DAY, FINAL_STAFF_ID;
    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_update);

        lvStudentsList = findViewById(R.id.lvStudentsList);
        mainTitle=findViewById(R.id.mainTitle);

        studentList = new ArrayList<>();

        FINAL_MODULE_ID = getIntent().getStringExtra("ref_module_id_key");
        FINAL_CLASS_ID = getIntent().getStringExtra("ref_class_id_key");
        FINAL_STAFF_ID = getIntent().getStringExtra("ref_staff_id_key");
        FINAL_ATT_DAY = getIntent().getStringExtra("attendance_day");

        mainTitle.setOnClickListener(v -> {
            Intent g = new Intent(getApplicationContext(), ClaimingPath.class);
            FINAL_MODULE_ID = getIntent().getStringExtra("ref_module_id_key");
            FINAL_CLASS_ID = getIntent().getStringExtra("ref_class_id_key");
            FINAL_STAFF_ID = getIntent().getStringExtra("ref_staff_id_key");

            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(g);
        });

        loadStudents();
    }

    private void loadStudents() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        // We keep these under constructor to help us catching ids
        int M3_ID = Integer.parseInt(FINAL_MODULE_ID);
        int D3_DY = Integer.parseInt(FINAL_ATT_DAY);
        int S3_ID = Integer.parseInt(FINAL_STAFF_ID);

        Staff activeUser = SharedPrefManager.getInstance(this).getLoggedInStaff();
        String STAFF_ID = String.valueOf(activeUser.getStaffId());

        StringRequest countStaffLDReq = new StringRequest(Request.Method.GET, URLs.MAX_DAY_COUNT+STAFF_ID, response -> {
            try {
                JSONObject object = new JSONObject(response);

                JSONArray staffArray = object.getJSONArray("lecturer_last_day");
                for(int i=0; i<staffArray.length(); i++) {
                    JSONObject staffObject = staffArray.getJSONObject(i);
                    String LAST_DAY = staffObject.optString("max_time");
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countStaffLDReq);

        String FINAL_URL_LAST= FINAL_ATT_DAY+"&module="+FINAL_MODULE_ID+"&staff_id="+FINAL_STAFF_ID;
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.ATT_2_UPDATE+FINAL_URL_LAST, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray staffArray = object.getJSONArray("lecturer_some_day");

                for(int i=0; i<staffArray.length(); i++) {
                    JSONObject staffObject = staffArray.getJSONObject(i);
                    Student studShit = new Student(
                            staffObject.getInt("att_id"),
                            staffObject.getInt("student_id"),
                            staffObject.getString("firstname"),
                            staffObject.getString("lastname"),
                            staffObject.getString("reg_number"),
                            staffObject.getInt("module_id"),
                            staffObject.getInt("lecturer_id"),
                            staffObject.getInt("att_day"),
                            staffObject.getString("att_status")
                    );

                    studentList.add(studShit);
                    StAttendanceUpdate adapter = new StAttendanceUpdate(studentList, getApplicationContext());
                    lvStudentsList.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}