package com.donnekt.attendanceapp.classroom;

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
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.module.ModulesLecturer;
import com.donnekt.attendanceapp.staff.StaffDetails;
import com.donnekt.attendanceapp.staff.StaffLecturers;
import com.donnekt.attendanceapp.student.AttendanceAct;
import com.donnekt.attendanceapp.student.StudentDetails;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassroomLecturers extends AppCompatActivity {

    List<Classroom> classroomList;
    ListView listViewClassGroup;
    String LECTURER_ID;
    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_lecturers);

        listViewClassGroup = findViewById(R.id.listViewClassGroup);
        mainTitle=findViewById(R.id.mainTitle);
        classroomList = new ArrayList<>();

        mainTitle.setOnClickListener(v -> {
            Intent intent=new Intent(ClassroomLecturers.this, StaffLecturers.class);
            startActivity(intent);

            Intent g = new Intent(ClassroomLecturers.this, StaffLecturers.class);
            String deptId = "";
            g.putExtra("department_key", deptId);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            //startActivity(g); WAIT PLEASE! THERE IS A BIG ISSUE IN PASSING
        });

        // Method for displaying in the list
        showAllClassroomByGroup();
    }

    private void showAllClassroomByGroup() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        LECTURER_ID = getIntent().getStringExtra("lecturer_id_key");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.CLASS_VIEW_GROUP+LECTURER_ID, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("classgroup");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);

                    Classroom classroom = new Classroom(
                            dataObject.getInt("class_id"),
                            dataObject.getString("class_name"),
                            dataObject.getString("class_level"),
                            dataObject.getInt("staff_id"),
                            dataObject.getString("firstname"),
                            dataObject.getString("dept_name"),
                            dataObject.getString("dept_caption")
                    );
                    classroomList.add(classroom);
                    ClassGroupLecturer adapter = new ClassGroupLecturer(classroomList, getApplicationContext());
                    listViewClassGroup.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}