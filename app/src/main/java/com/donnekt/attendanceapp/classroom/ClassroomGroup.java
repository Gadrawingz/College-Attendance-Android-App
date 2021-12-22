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
import com.donnekt.attendanceapp.student.AttendanceAct;
import com.donnekt.attendanceapp.student.StudentDetails;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassroomGroup extends AppCompatActivity {

    List<Classroom> classroomList;
    ListView listViewClassGroup;
    String MODULE_ID;
    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_group);

        listViewClassGroup = findViewById(R.id.listViewClassGroup);
        mainTitle=findViewById(R.id.mainTitle);
        classroomList = new ArrayList<>();

        mainTitle.setOnClickListener(v -> {
            Intent intent=new Intent(ClassroomGroup.this, ModulesLecturer.class);
            startActivity(intent);
        });
        showAllClassroomByGroup();
    }

    private void showAllClassroomByGroup() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        MODULE_ID = getIntent().getStringExtra("module_id_key");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.MOD_VIEW_GROUP+MODULE_ID, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("modulegroup");

                for (int i = 0; i < dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);

                    Classroom classroom = new Classroom(
                            dataObject.getInt("class_id"),
                            dataObject.getString("class_name"),
                            dataObject.getString("class_level"),
                            dataObject.getInt("module_id"),
                            dataObject.getString("module_name"),
                            dataObject.getString("module_code"),
                            dataObject.getString("dept_name"),
                            dataObject.getString("dept_caption")
                    );
                    classroomList.add(classroom);
                    ClassGroupHandler adapter = new ClassGroupHandler(classroomList, getApplicationContext());
                    listViewClassGroup.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}