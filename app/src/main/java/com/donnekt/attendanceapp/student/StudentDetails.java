package com.donnekt.attendanceapp.student;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StudentDetails extends AppCompatActivity {

    List<Student> studentList;
    ListView listViewStudents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);

        listViewStudents = findViewById(R.id.listViewStudents);
        studentList = new ArrayList<>();

        // Load shit now!
        loadStaffMemberList();
    }

    private void loadStaffMemberList() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        // Getting ID shit
        String studentIdKey = getIntent().getStringExtra("student_id_key");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STUD_VIEW_ONE+studentIdKey, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("student");

                for(int i=0; i<dataArray.length(); i++) {
                    JSONObject myObject = dataArray.getJSONObject(i);

                    Student student = new Student(
                            myObject.getInt("student_id"),
                            myObject.getString("firstname"),
                            myObject.getString("lastname"),
                            myObject.getString("reg_number"),
                            myObject.getString("email"),
                            myObject.getString("telephone"),
                            myObject.getString("gender"),
                            myObject.getInt("class_id"),
                            myObject.getString("class_name"),
                            myObject.getString("class_dept"),
                            myObject.getString("reg_date")
                    );

                    studentList.add(student);
                    StudentADetails adapter = new StudentADetails(studentList, getApplicationContext());
                    listViewStudents.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network error!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
