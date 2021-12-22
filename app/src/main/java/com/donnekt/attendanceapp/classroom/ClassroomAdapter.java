package com.donnekt.attendanceapp.classroom;

import static android.view.View.GONE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.student.AttendanceAct;
import com.donnekt.attendanceapp.student.AttendanceUpdate;
import com.donnekt.attendanceapp.student.ClaimingPath;
import com.donnekt.attendanceapp.student.StudentClassReport;
import com.donnekt.attendanceapp.student.StudentClassReportDoq;

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

            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);

        });

        // D&U-SHIT
        buttonDelete.setOnClickListener(view -> {
            deleteProgBar.setVisibility(View.VISIBLE);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.CLASS_DELETE+classroom.getClassroomId(),
                    response -> {

                Intent intent =new Intent(mCtx.getApplicationContext(), ClassroomViewAll.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mCtx.startActivity(intent);
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

        Staff member = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();
        String sRole = member.getRole();

        final TextView tvClassName = LVItem.findViewById(R.id.tvClassName);
        final TextView tvClassDeptName = LVItem.findViewById(R.id.tvClassDeptName);
        final TextView tvTotalStudents = LVItem.findViewById(R.id.tvTotalStudents);
        final Button buttonViewStuds = LVItem.findViewById(R.id.buttonViewStuds);
        final Button buttonViewStudsRep = LVItem.findViewById(R.id.buttonViewStudsRep);
        final Button updateAttendance = LVItem.findViewById(R.id.updateAttendance);

        Classroom classroom = classroomList.get(position);

        tvClassName.setText("Class: "+classroom.getClassroomName()+" - "+classroom.getClassroomLevel());
        tvClassDeptName.setText("Department: "+classroom.getRefDeptName());

        StringRequest countTotalStudents = new StringRequest(Request.Method.GET, URLs.STUD_CLASS+classroom.getClassroomId(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                tvTotalStudents.setText("All students ("+object.optString("counts")+")");
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network problem!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue.add(countTotalStudents);

        if(sRole.equals("HOD")) {
            buttonViewStuds.setVisibility(GONE);
            buttonViewStudsRep.setVisibility(View.VISIBLE);
            updateAttendance.setVisibility(View.VISIBLE);
        }

        buttonViewStuds.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), AttendanceAct.class);
            String finalClassId = String.valueOf(classroom.getClassroomId());
            String finalRefModuleId = String.valueOf(classroom.getRefModuleId());
            g.putExtra("ref_class_id_key", finalClassId);
            g.putExtra("ref_module_id_key", finalRefModuleId);

            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
        });

        updateAttendance.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), ClaimingPath.class);
            String finalClassId = String.valueOf(classroom.getClassroomId());
            String finalRefModuleId = String.valueOf(classroom.getRefModuleId());
            String finalRefStaffId = String.valueOf(classroom.getRefStaffId());

            g.putExtra("ref_class_id_key", finalClassId);
            g.putExtra("ref_module_id_key", finalRefModuleId);
            g.putExtra("ref_staff_id_key", finalRefStaffId);

            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
        });

        buttonViewStudsRep.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), StudentClassReport.class);
            String finalClassId = String.valueOf(classroom.getClassroomId());
            String finalRefModuleId = String.valueOf(classroom.getRefModuleId());
            g.putExtra("ref_class_id_key", finalClassId);
            g.putExtra("ref_module_id_key", finalRefModuleId);

            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
        });


        return LVItem;
    }
}


class ClassGroupDepart extends ArrayAdapter<Classroom> {
    private final List<Classroom> classroomList;
    private final Context mCtx;

    ClassGroupDepart(List<Classroom> classroomList, Context mCtx) {
        super(mCtx, R.layout.layout_group_class_depart, classroomList);
        this.mCtx = mCtx;
        this.classroomList = classroomList;
    }

    @SuppressLint({"ViewHolder", "InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.layout_group_class_depart, null, true);

        Staff member = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();
        String sRole = member.getRole();

        final TextView tvClassName = LVItem.findViewById(R.id.tvClassName);
        final TextView tvClassDeptName = LVItem.findViewById(R.id.tvClassDeptName);
        final TextView tvTotalStudents = LVItem.findViewById(R.id.tvTotalStudents);
        final Button classroomReport = LVItem.findViewById(R.id.classroomReport);

        Classroom classroom = classroomList.get(position);

        tvClassName.setText("Class: "+classroom.getClassroomName()+" - "+classroom.getClassroomLevel());
        tvClassDeptName.setText("Department: "+classroom.getRefDeptName());

        StringRequest countTotalStudents = new StringRequest(Request.Method.GET, URLs.STUD_CLASS+classroom.getClassroomId(), response -> {
            try {
                JSONObject object = new JSONObject(response);
                tvTotalStudents.setText("Active students are ("+object.optString("counts")+")");
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(mCtx.getApplicationContext(), "Network problem!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        requestQueue.add(countTotalStudents);


        classroomReport.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), StudentClassReportDoq.class);
            String finalClassId = String.valueOf(classroom.getClassroomId());
            String finalReturnDeptId = String.valueOf(classroom.getRefDeptId());
            g.putExtra("ref_class_id_key", finalClassId);
            g.putExtra("return_department_id_key", finalReturnDeptId);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
        });

        return LVItem;
    }
}










class ClassGroupLecturer extends ArrayAdapter<Classroom> {
    private final List<Classroom> classroomList;
    private final Context mCtx;

    ClassGroupLecturer(List<Classroom> classroomList, Context mCtx) {
        super(mCtx, R.layout.layout_group_class_depart, classroomList);
        this.mCtx = mCtx;
        this.classroomList = classroomList;
    }

    @SuppressLint({"ViewHolder", "InflateParams", "SetTextI18n"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.layout_group_class_depart, null, true);

        final TextView tvClassName = LVItem.findViewById(R.id.tvClassName);
        final TextView tvClassDeptName = LVItem.findViewById(R.id.tvClassDeptName);
        final TextView tvTotalStudents = LVItem.findViewById(R.id.tvTotalStudents);
        //final TextView tvAbsence = LVItem.findViewById(R.id.dailyAbsence);
        //final TextView tvPresence = LVItem.findViewById(R.id.dailyPresence);

        // Getting record of the specified position
        Classroom classroom = classroomList.get(position);

        // Add 'em to views
        tvClassName.setText("Class: "+classroom.getClassroomName()+" - "+classroom.getClassroomLevel());
        tvClassDeptName.setText("Department: "+classroom.getRefDeptName());
        //tvAbsence.setText("40 absences");
        //tvPresence.setText("18 presences");

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


        // Return the list item
        return LVItem;
    }
}