package com.donnekt.attendanceapp.classroom;

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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ClassroomViewAll extends AppCompatActivity {

    List<Classroom> classroomList;
    ListView listViewClassrooms;
    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_view_all);

        listViewClassrooms = findViewById(R.id.listViewClassrooms);
        mainTitle = findViewById(R.id.mainTitle);
        classroomList = new ArrayList<>();

        mainTitle.setText("All Classrooms");
        // Method for displaying departments in the list
        showAllClassrooms();
    }

    private void showAllClassrooms() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.CLASS_VIEW_ALL, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray dataArray = object.getJSONArray("classrooms");

                for(int i=0; i<dataArray.length(); i++) {
                    JSONObject dataObject = dataArray.getJSONObject(i);

                    Classroom classroom = new Classroom(
                            dataObject.getInt("class_id"),
                            dataObject.getString("class_name"),
                            dataObject.getString("class_level"),
                            dataObject.getInt("dept_id"),
                            dataObject.getString("dept_name"),
                            dataObject.getString("dept_caption")
                    );
                    classroomList.add(classroom);
                    ClassroomAdapter adapter = new ClassroomAdapter(classroomList, getApplicationContext());
                    listViewClassrooms.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }
}