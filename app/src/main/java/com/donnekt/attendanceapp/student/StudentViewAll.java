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
import com.donnekt.attendanceapp.department.DepartmentViewAll;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.users.StaffMenus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentViewAll extends AppCompatActivity {

    List<Student> studentList;
    ListView listViewStudents;
    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view_all);

        listViewStudents = findViewById(R.id.listViewStudents);
        mainTitle = findViewById(R.id.mainTitle);
        studentList = new ArrayList<>();
        Staff user = SharedPrefManager.getInstance(this).getLoggedInStaff();
        mainTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sentWord = user.getRole();
                Intent intent =new Intent(StudentViewAll.this, StaffMenus.class);
                intent.putExtra("sent_role", sentWord);
                startActivity(intent);
            }
        });
        loadStudents();
    }

    private void loadStudents() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STUD_VIEW_ALL, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("students");

                for(int i=0; i<dataArray.length(); i++) {
                    JSONObject staffObject = dataArray.getJSONObject(i);

                    Student studShit = new Student(
                            staffObject.getInt("student_id"),
                            staffObject.getString("firstname"),
                            staffObject.getString("lastname"),
                            staffObject.getString("reg_number")
                    );

                    studentList.add(studShit);
                    StudentAdapter adapter = new StudentAdapter(studentList, getApplicationContext());
                    listViewStudents.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network Issues", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}