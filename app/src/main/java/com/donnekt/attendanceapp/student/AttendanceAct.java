package com.donnekt.attendanceapp.student;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.staff.ListViewAdapter;
import com.donnekt.attendanceapp.staff.Staff;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AttendanceAct extends AppCompatActivity {

    List<Student> studentList;
    ListView lvStudentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        lvStudentsList = findViewById(R.id.lvStudentsList);
        studentList = new ArrayList<>();
        loadStudents();
    }

    private void loadStudents() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STUD_VIEW_ALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray staffArray = object.getJSONArray("students");

                    int studentCount = Integer.parseInt(object.optString("counts"));

                    for(int i=0; i<staffArray.length(); i++) {
                        JSONObject staffObject = staffArray.getJSONObject(i);
                        int studentId = i+=1;

                        Student studShit = new Student(
                                staffObject.getInt("student_id"),
                                staffObject.getString("firstname"),
                                staffObject.getString("lastname"),
                                staffObject.getString("reg_number")
                        );

                        studentList.add(studShit);
                        StAttendanceAdapter adapter = new StAttendanceAdapter(studentList, getApplicationContext());
                        lvStudentsList.setAdapter(adapter);
                    }

                } catch (JSONException error) {
                    error.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}