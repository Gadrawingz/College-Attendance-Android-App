package com.donnekt.attendanceapp.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SpinnerTest;
import com.donnekt.attendanceapp.classroom.ClassroomActivity;
import com.donnekt.attendanceapp.classroom.ClassroomViewAll;
import com.donnekt.attendanceapp.department.DepartmentActivity;
import com.donnekt.attendanceapp.department.DepartmentViewAll;
import com.donnekt.attendanceapp.module.ModuleActivity;
import com.donnekt.attendanceapp.module.ModuleViewAll;
import com.donnekt.attendanceapp.staff.StaffActivity;
import com.donnekt.attendanceapp.staff.StaffViewAll;
import com.donnekt.attendanceapp.student.StudentActivity;
import com.donnekt.attendanceapp.student.StudentViewAll;

public class AdminDashboard extends AppCompatActivity {

    // Variables
    SharedPreferences pref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        TextView goToSettings = findViewById(R.id.btnAdminSettings);
        TextView addDept = findViewById(R.id.add_department);
        TextView viewDept = findViewById(R.id.view_departs);
        TextView addClass = findViewById(R.id.add_classroom);
        TextView viewClass = findViewById(R.id.view_classes);
        TextView addStaff = findViewById(R.id.add_staff);
        TextView viewStaff = findViewById(R.id.view_staff);
        TextView addModule = findViewById(R.id.add_module);
        TextView viewModule = findViewById(R.id.view_module);


        goToSettings.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, AdminSettings.class));
        });

        addDept.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, DepartmentActivity.class));
        });

        viewDept.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, DepartmentViewAll.class));
        });

        addClass.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, ClassroomActivity.class));
        });

        viewClass.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, ClassroomViewAll.class));
        });

        addStaff.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, StaffActivity.class));
        });

        viewStaff.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, StaffViewAll.class));
        });

        addModule.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, ModuleActivity.class));
        });

        viewModule.setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, ModuleViewAll.class));
        });

        findViewById(R.id.add_student).setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, StudentActivity.class));
        });

        findViewById(R.id.view_students).setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, StudentViewAll.class));
        });

        findViewById(R.id.btnTesting).setOnClickListener(v -> {
            startActivity(new Intent(AdminDashboard.this, SpinnerTest.class));
        });


    }

}