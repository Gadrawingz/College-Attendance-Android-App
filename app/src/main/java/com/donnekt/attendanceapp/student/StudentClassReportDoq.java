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
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.classroom.ClassroomDepartment;
import com.donnekt.attendanceapp.classroom.ClassroomGroup;
import com.donnekt.attendanceapp.module.ModulesLecturer;
import com.donnekt.attendanceapp.module.ModulesLecturerRep;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class StudentClassReportDoq extends AppCompatActivity {

    List<Student> studentList;
    ListView lvStudentsList;
    String FINAL_CLASS_ID, RETURN_DEPARTMENT_ID;
    TextView mainTitle, totalCountsPresent, totalCountsAbsent;

    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
    private final String CURRENT_DAY = dateFormat.format(calendar.getTime());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class_report_doq);

        lvStudentsList = findViewById(R.id.lvStudentsList);
        mainTitle=findViewById(R.id.mainTitle);

        studentList = new ArrayList<>();
        FINAL_CLASS_ID = getIntent().getStringExtra("ref_class_id_key");
        RETURN_DEPARTMENT_ID = getIntent().getStringExtra("return_department_id_key");

        mainTitle.setOnClickListener(v -> {
            Intent g = new Intent(getApplicationContext(), ClassroomDepartment.class);
            g.putExtra("department_id_key", RETURN_DEPARTMENT_ID);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(g);
        });

        loadStudents();
    }

    private void loadStudents() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STUD_CLASS+FINAL_CLASS_ID, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray staffArray = object.getJSONArray("studclasses");

                for(int i=0; i<staffArray.length(); i++) {
                    JSONObject staffObject = staffArray.getJSONObject(i);
                    // Keep this under its own const in Student, for keeping sh**
                    Student studShit = new Student(
                            staffObject.getInt("student_id"),
                            staffObject.getString("firstname"),
                            staffObject.getString("lastname"),
                            staffObject.getString("reg_number"),
                            staffObject.getString("telephone"),
                            staffObject.getString("gender"),
                            staffObject.getInt("class_id")
                    );

                    studentList.add(studShit);
                    StClassReportDoqAdapter adapter = new StClassReportDoqAdapter(studentList, getApplicationContext());
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