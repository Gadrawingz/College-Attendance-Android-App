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
import com.donnekt.attendanceapp.classroom.ClassroomGroup;
import com.donnekt.attendanceapp.module.ModulesLecturer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAct extends AppCompatActivity {

    List<Student> studentList;
    ListView lvStudentsList;
    String FINAL_MODULE_ID, FINAL_CLASS_ID;
    TextView mainTitle;
    private ArrayList<MainData> mainDataArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        lvStudentsList = findViewById(R.id.lvStudentsList);
        mainTitle=findViewById(R.id.mainTitle);

        studentList = new ArrayList<>();

        FINAL_MODULE_ID = getIntent().getStringExtra("ref_module_id_key");
        FINAL_CLASS_ID = getIntent().getStringExtra("ref_class_id_key");

        mainTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent g = new Intent(getApplicationContext(), ModulesLecturer.class);
                g.putExtra("ref_class_id_key", FINAL_MODULE_ID);
                g.putExtra("ref_module_id_key", FINAL_CLASS_ID);
                g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(g);

            }
        });

        loadStudents();
    }

    private void loadStudents() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        // We keep these under constructor to help us catching ids
        int M3_ID = Integer.parseInt(FINAL_MODULE_ID);
        int C3_ID = Integer.parseInt(FINAL_CLASS_ID);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STUD_CLASS+FINAL_CLASS_ID, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray staffArray = object.getJSONArray("studclasses");

                for(int i=0; i<staffArray.length(); i++) {
                    JSONObject staffObject = staffArray.getJSONObject(i);
                    Student studShit = new Student(
                            staffObject.getInt("student_id"),
                            staffObject.getString("firstname"),
                            staffObject.getString("lastname"),
                            staffObject.getString("reg_number"),
                            M3_ID,
                            C3_ID
                    );

                    studentList.add(studShit);
                    StAttendanceAdapter adapter = new StAttendanceAdapter(studentList, getApplicationContext());
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