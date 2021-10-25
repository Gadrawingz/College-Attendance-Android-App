package com.donnekt.attendanceapp.users;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.LoginActivity;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.admin.AdminDashboard;
import com.donnekt.attendanceapp.admin.AdminSettings;
import com.donnekt.attendanceapp.classroom.ClassroomActivity;
import com.donnekt.attendanceapp.classroom.ClassroomViewAll;
import com.donnekt.attendanceapp.department.DepartmentActivity;
import com.donnekt.attendanceapp.department.DepartmentViewAll;
import com.donnekt.attendanceapp.module.ModuleActivity;
import com.donnekt.attendanceapp.module.ModuleViewAll;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.staff.StaffActivity;
import com.donnekt.attendanceapp.staff.StaffViewAll;
import com.donnekt.attendanceapp.student.StudentActivity;
import com.donnekt.attendanceapp.student.StudentViewAll;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class StaffMenus extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutDas, layoutHod, layoutDoq, layoutLecturer;
    TextView loggedInRole, tvGeneralLogout;
    // ImageView backToDash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_menus);

        // Declaration
        TextView addDept = findViewById(R.id.add_department);
        TextView viewDept = findViewById(R.id.view_departs);
        TextView addClass = findViewById(R.id.add_classroom);
        TextView viewClass = findViewById(R.id.view_classes);
        TextView addStaff = findViewById(R.id.add_staff);
        TextView viewStaff = findViewById(R.id.view_staff);
        TextView addModule = findViewById(R.id.add_module);
        TextView viewModule = findViewById(R.id.view_module);

        layoutDas = findViewById(R.id.layoutDas);
        layoutHod = findViewById(R.id.layoutHod);
        layoutDoq = findViewById(R.id.layoutDoq);
        layoutLecturer = findViewById(R.id.layoutLecturer);
        loggedInRole = findViewById(R.id.dashboard_header);
        tvGeneralLogout = findViewById(R.id.tvGeneralLogout);

        layoutDas.setVisibility(GONE);
        layoutHod.setVisibility(GONE);
        layoutDoq.setVisibility(GONE);
        layoutLecturer.setVisibility(GONE);


        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            Staff activeUser = SharedPrefManager.getInstance(this).getLoggedInStaff();
            String sRole = activeUser.getRole();
            // String sRole = getIntent().getStringExtra("sent_role");
            // String sRole = "HOD";
            loggedInRole.setText(sRole);

            switch (sRole) {
                case "DAS":
                    // Hiding and Showing shits accordingly
                    layoutDas.setVisibility(VISIBLE);
                    Toast.makeText(getApplicationContext(), sRole, Toast.LENGTH_LONG).show();
                    break;

                case "HOD":
                    layoutHod.setVisibility(VISIBLE);
                    Toast.makeText(getApplicationContext(), sRole, Toast.LENGTH_LONG).show();
                    break;

                case "DOQ":
                    layoutDoq.setVisibility(VISIBLE);
                    Toast.makeText(getApplicationContext(), sRole, Toast.LENGTH_LONG).show();

                    break;
                case "LECTURER":
                    layoutLecturer.setVisibility(VISIBLE);
                    Toast.makeText(getApplicationContext(), sRole, Toast.LENGTH_LONG).show();
                    break;
            }

            // Getting outta shit
            findViewById(R.id.backToDash).setOnClickListener(v -> {
                switch (sRole) {
                    case "DAS":
                        startActivity(new Intent(StaffMenus.this, DashboardDas.class));
                        break;
                    case "HOD":
                        startActivity(new Intent(StaffMenus.this, DashboardHod.class));
                        break;
                    case "DOQ":
                        startActivity(new Intent(StaffMenus.this, DashboardDoq.class));
                        break;
                    case "LECTURER":
                        startActivity(new Intent(StaffMenus.this, DashboardLecturer.class));
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "PROBLEM OCCURRED!", Toast.LENGTH_SHORT).show();
                }
            });


        } else {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        // General logout button! (Outta shit by condition
        tvGeneralLogout.setOnClickListener(this);



        // Linking

        addDept.setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, DepartmentActivity.class)));

        viewDept.setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, DepartmentViewAll.class)));

        addClass.setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, ClassroomActivity.class)));

        viewClass.setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, ClassroomViewAll.class)));

        addStaff.setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, StaffActivity.class)));

        viewStaff.setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, StaffViewAll.class)));

        addModule.setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, ModuleActivity.class)));

        viewModule.setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, ModuleViewAll.class)));

        findViewById(R.id.add_student).setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, StudentActivity.class)));

        findViewById(R.id.view_students).setOnClickListener(v -> startActivity(new Intent(StaffMenus.this, StudentViewAll.class)));


    }

    @Override
    public void onClick(View view) {
        // Switch now
        if(view.equals(tvGeneralLogout)) {
            // Call logout func for getting out of shit!
            Staff staff = SharedPrefManager.getInstance(this).getLoggedInStaff();
            String activeStaffRole = staff.getRole();

            SharedPrefManager.getInstance(getApplicationContext()).logout();

            switch (activeStaffRole){
                case "DAS":
                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                    intent.putExtra("sent_key", "DAS");
                    startActivity(intent);
                    break;

                case "HOD":
                    Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                    intent2.putExtra("sent_key", "HOD");
                    startActivity(intent2);
                    break;

                case "DOQ":
                    Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                    intent3.putExtra("sent_key", "DOQ");
                    startActivity(intent3);
                    break;

                case "LECTURER":
                    Intent intent4 = new Intent(getApplicationContext(), LoginActivity.class);
                    intent4.putExtra("sent_key", "LECTURER");
                    startActivity(intent4);
                    break;

                default:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        }

    }
}