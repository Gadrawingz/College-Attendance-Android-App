

package com.donnekt.attendanceapp.users.reports;

import static android.content.Intent.FLAG_ACTIVITY_NO_HISTORY;
import static android.view.View.GONE;
import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.Methods.capitalizeAllFirstLetters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
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
import com.donnekt.attendanceapp.classroom.Classroom;
import com.donnekt.attendanceapp.classroom.ClassroomDepartment;
import com.donnekt.attendanceapp.classroom.ClassroomGroup;
import com.donnekt.attendanceapp.module.Module;
import com.donnekt.attendanceapp.module.ModuleAssigning;
import com.donnekt.attendanceapp.module.ModuleUpdate;
import com.donnekt.attendanceapp.module.ModuleViewAll;
import com.donnekt.attendanceapp.module.ModulesLecturerRep;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.staff.StaffViewAll;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HodReportAdapter extends ArrayAdapter<Report> {

    protected Context mCtx;
    private final List<Report> reportList;
    String LECTURER_ID;

    public HodReportAdapter(List<Report> reportList, Context mCtx) {
        super(mCtx, R.layout.report_hod_daily, reportList);
        this.mCtx = mCtx;
        this.reportList = reportList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.report_hod_daily, null, true);

        // Getting the specified position
        Report report = reportList.get(position);

        // Getting views
        TextView tvLecturerFn = LVItem.findViewById(R.id.tvLecturerFn);
        TextView tvLecturerLn = LVItem.findViewById(R.id.tvLecturerLn);
        Button classRep= LVItem.findViewById(R.id.classRep);
        Button submitReport= LVItem.findViewById(R.id.submitReport);

        tvLecturerFn.setText(report.getlFistName());
        tvLecturerLn.setText(report.getlLastName());
        Staff member = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();
        String sRole = member.getRole();

        // ATTACHED REPORT
        classRep.setOnClickListener(view -> {
            String lecturerId = String.valueOf(report.getlID());
            Intent intent = new Intent(mCtx.getApplicationContext(), ModulesLecturerRep.class);
            intent.putExtra("lecturer_id_key", lecturerId);
            intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(intent);
        });

        submitReport.setOnClickListener(view -> {
            Toast.makeText(mCtx.getApplicationContext(), "Report is submitted successfully!", Toast.LENGTH_SHORT).show();
        });

        return LVItem;
    }
}

class DoqReportAdapter extends ArrayAdapter<Report> {

    protected Context mCtx;
    private final List<Report> reportList;
    String LECTURER_ID;

    public DoqReportAdapter(List<Report> reportList, Context mCtx) {
        super(mCtx, R.layout.report_doq_daily, reportList);
        this.mCtx = mCtx;
        this.reportList = reportList;
    }

    @SuppressLint({"ViewHolder", "InflateParams"})
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.report_doq_daily, null, true);

        Report report = reportList.get(position);

        TextView tvHodFn = LVItem.findViewById(R.id.tvHodFn);
        TextView tvHodLn = LVItem.findViewById(R.id.tvHodLn);
        TextView tvHodDept = LVItem.findViewById(R.id.tvHodDept);
        TextView tvHodClasses = LVItem.findViewById(R.id.tvHodClasses);
        ImageView classRep = LVItem.findViewById(R.id.classRep);
        tvHodFn.setText(report.gethFirstname());
        tvHodLn.setText(report.gethLastname());
        tvHodDept.setText(report.gethDeptName());
        tvHodClasses.setText(report.getClassCounting()+" classes");

        Staff member = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();
        String sRole = member.getRole();

        // ATTACHED RP
        classRep.setOnClickListener(view -> {
            String DEPARTMENT_KEY = String.valueOf(report.gethDeptId());
            Intent intent = new Intent(mCtx.getApplicationContext(), ClassroomDepartment.class);
            intent.putExtra("department_id_key", DEPARTMENT_KEY);
            intent.setFlags(FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(intent);
        });

        return LVItem;
    }
}