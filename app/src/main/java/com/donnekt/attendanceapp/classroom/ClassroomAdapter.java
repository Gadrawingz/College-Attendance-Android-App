package com.donnekt.attendanceapp.classroom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.UpdatingActivity;
import com.donnekt.attendanceapp.department.Department;
import com.donnekt.attendanceapp.department.DepartmentViewAll;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ClassroomAdapter extends ArrayAdapter<Classroom> {

    private final List<Classroom> classroomList;
    private final Context mCtx;

    ClassroomAdapter(List<Classroom> classroomList, Context mCtx) {
        super(mCtx, R.layout.list_layout_class, classroomList);
        this.mCtx = mCtx;
        this.classroomList = classroomList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_class, null, true);

        final TextView tvClassName = LVItem.findViewById(R.id.tvClassName);
        final TextView tvClassLevel = LVItem.findViewById(R.id.tvClassLevel);
        final TextView tvClassDept = LVItem.findViewById(R.id.tvClassDept);

        final Button buttonDelete = LVItem.findViewById(R.id.buttonDelete);
        final ProgressBar deleteProgBar = LVItem.findViewById(R.id.deleteProgBar);

        // Getting record of the specified position
        Classroom classroom = classroomList.get(position);

        // Add 'em to views
        tvClassName.setText(classroom.getClassroomName());
        tvClassLevel.setText(classroom.getClassroomLevel());
        tvClassDept.setText(classroom.getRefDeptName());

        // D&U-SHIT
        buttonDelete.setOnClickListener(view -> {
            deleteProgBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.CLASS_DELETE+classroom.getClassroomId(),
                    response -> {
                        mCtx.startActivity(new Intent(mCtx.getApplicationContext(), ClassroomViewAll.class));
                        try {
                            deleteProgBar.setVisibility(View.INVISIBLE);
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(mCtx.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(mCtx.getApplicationContext(), "Network issues!", Toast.LENGTH_SHORT).show()) {
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            requestQueue.add(stringRequest);
        });

        // Return the list item
        return LVItem;
    }
}


class ClassGroupHandler extends ArrayAdapter<Classroom> {
    private final List<Classroom> classroomList;
    private final Context mCtx;

    ClassGroupHandler(List<Classroom> classroomList, Context mCtx) {
        super(mCtx, R.layout.layout_group_class, classroomList);
        this.mCtx = mCtx;
        this.classroomList = classroomList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.layout_group_class, null, true);

        final TextView tvClassName = LVItem.findViewById(R.id.tvClassName);
        final TextView tvClassLevel = LVItem.findViewById(R.id.tvClassLevel);
        final TextView tvClassDept = LVItem.findViewById(R.id.tvClassDept);

        final Button buttonViewStuds = LVItem.findViewById(R.id.buttonViewStuds);

        // Getting record of the specified position
        Classroom classroom = classroomList.get(position);

        // Add 'em to views
        tvClassName.setText(classroom.getClassroomName());
        tvClassLevel.setText(classroom.getClassroomLevel());
        tvClassDept.setText(classroom.getDepartment());

        // Return the list item
        return LVItem;
    }
}