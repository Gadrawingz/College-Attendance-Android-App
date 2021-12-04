package com.donnekt.attendanceapp.classroom;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.donnekt.attendanceapp.student.AttendanceAct;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.content.Intent.getIntent;
import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;

class HelperClass {
    public static final String NAME = "";
    /*public static Intent getNameIntent(Context context, String name) {
        Intent intent = new Intent(context, ClassroomActivity.class);
        intent.putExtra(NAME, name);
        return intent;
    }*/
}

public class ClassroomAdapter extends ArrayAdapter<Classroom> {

    private final List<Classroom> classroomList;
    private final Context mCtx;
    // String moduleIdKey2 = getIntent().getStringExtra("module_id_key_again");
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
        final Button buttonEdit = LVItem.findViewById(R.id.buttonEdit);
        final ProgressBar deleteProgBar = LVItem.findViewById(R.id.deleteProgBar);

        // Getting record of the specified position
        Classroom classroom = classroomList.get(position);

        // Add 'em to views
        tvClassName.setText(classroom.getClassroomName());
        tvClassLevel.setText(classroom.getClassroomLevel());
        String department = classroom.getRefDeptName();
        tvClassDept.setTypeface(tvClassDept.getTypeface(), Typeface.BOLD);
        tvClassDept.setText("In ("+department+") department");

        String C_ID= String.valueOf(classroom.getClassroomId());
        String C_NM= String.valueOf(classroom.getClassroomName());
        String C_LV= String.valueOf(classroom.getClassroomLevel());

        // UPDATE SHIT
        buttonEdit.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), ClassroomUpdate.class);
            g.putExtra("c_id_key", C_ID);
            g.putExtra("c_name_key", C_NM);
            g.putExtra("c_level_key", C_LV);
            g.putExtra("c_dept_key", department);
            mCtx.startActivity(g);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        });

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
        final TextView tvClassDeptName = LVItem.findViewById(R.id.tvClassDeptName);
        final TextView tvTotalStudents = LVItem.findViewById(R.id.tvTotalStudents);
        final Button buttonViewStuds = LVItem.findViewById(R.id.buttonViewStuds);

        // Getting record of the specified position
        Classroom classroom = classroomList.get(position);

        // Add 'em to views
        tvClassName.setText("Class: "+classroom.getClassroomName()+" - "+classroom.getClassroomLevel());
        tvClassDeptName.setText("Department: "+classroom.getRefDeptName());

        // tvTotalStudents.set something in!
        StringRequest countTotalStudents = new StringRequest(Request.Method.GET, URLs.STUD_CLASS+classroom.getClassroomId(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                // Push shit in count!
                tvTotalStudents.setText("All students ("+object.optString("counts")+")");
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network problem!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue.add(countTotalStudents);

        // go to students list
        buttonViewStuds.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), AttendanceAct.class);
            String classId = String.valueOf(classroom.getClassroomId());
            g.putExtra("class_id_key", classId);
            // g.putExtra("module_id_key", moduleIdKey2);
            mCtx.startActivity(g);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

            // Keep sending this shit again!


        });

        // Return the list item
        return LVItem;
    }
}