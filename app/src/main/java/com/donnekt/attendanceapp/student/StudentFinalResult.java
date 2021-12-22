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
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.users.reports.Report;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentFinalResult extends AppCompatActivity {

    List<Report> reportModuleList;
    ListView lvFinalModStudents;
    TextView mainTitle;
    String STUDENT_F_KEY, RETURN_CLASS_ID_KEY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_final_result);

        lvFinalModStudents = findViewById(R.id.lvFinalModStudents);
        mainTitle = findViewById(R.id.mainTitle);
        reportModuleList = new ArrayList<>();

        STUDENT_F_KEY = getIntent().getStringExtra("student_id_final_key");
        RETURN_CLASS_ID_KEY = getIntent().getStringExtra("ref_class_id_key");

        Staff user = SharedPrefManager.getInstance(this).getLoggedInStaff();
        mainTitle.setOnClickListener(v -> {
            Intent intent =new Intent(StudentFinalResult.this, StudentClassReportDoq.class);
            intent.putExtra("ref_class_id_key", RETURN_CLASS_ID_KEY);
            startActivity(intent);
        });

        showAllAttendedModules();

    }

    private void showAllAttendedModules() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);
        StringRequest detailRequest = new StringRequest(Request.Method.GET, URLs.DOQ_STUDENT_DX_STUD+STUDENT_F_KEY, response -> {
            try {
                progressBar.setVisibility(View.INVISIBLE);
                ArrayList<Report> studentArrayList = new ArrayList<>();
                JSONObject dataObj = new JSONObject(response);
                JSONArray dataArray = dataObj.getJSONArray("stud_followed_modules");

                for(int i=0; i<dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);
                    Report report = new Report(
                            dataObject.getInt("student_id"),
                            dataObject.getInt("module_id"),
                            dataObject.getString("module_name"),
                            dataObject.getString("module_code"),
                            dataObject.getString("staff_firstname"),
                            dataObject.getString("staff_lastname")
                    );

                    reportModuleList.add(report);
                    StudentFinalData adapter = new StudentFinalData(reportModuleList, getApplicationContext());
                    lvFinalModStudents.setAdapter(adapter);
                }
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue8 = Volley.newRequestQueue(getApplicationContext());
        requestQueue8.add(detailRequest);
    }
}