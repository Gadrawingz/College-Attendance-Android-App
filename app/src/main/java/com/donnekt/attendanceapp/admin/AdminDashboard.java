package com.donnekt.attendanceapp.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.classroom.ClassroomActivity;
import com.donnekt.attendanceapp.department.DepartmentActivity;
import com.donnekt.attendanceapp.department.DepartmentViewAll;

public class AdminDashboard extends AppCompatActivity {

    // Variables
    SharedPreferences pref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);


        TextView goToSettings = (TextView) findViewById(R.id.btnAdminSettings);
        TextView addDept = (TextView) findViewById(R.id.add_department);
        TextView viewDept = (TextView) findViewById(R.id.view_departs);
        TextView addClass = (TextView) findViewById(R.id.add_classroom);

        goToSettings.setMovementMethod(LinkMovementMethod.getInstance());
        goToSettings.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, AdminSettings.class);
            startActivity(intent);
        });

        addDept.setMovementMethod(LinkMovementMethod.getInstance());
        addDept.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, DepartmentActivity.class);
            startActivity(intent);
        });

        viewDept.setMovementMethod(LinkMovementMethod.getInstance());
        viewDept.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, DepartmentViewAll.class);
            startActivity(intent);
        });

        addClass.setMovementMethod(LinkMovementMethod.getInstance());
        addClass.setOnClickListener(v -> {
            Intent intent = new Intent(AdminDashboard.this, ClassroomActivity.class);
            startActivity(intent);
        });


    }

}