package com.donnekt.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.donnekt.attendanceapp.staff.ProfileUpdate;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.staff.StaffDetails;
import com.donnekt.attendanceapp.users.DashboardDas;
import com.donnekt.attendanceapp.users.DashboardDoq;
import com.donnekt.attendanceapp.users.DashboardDpat;
import com.donnekt.attendanceapp.users.DashboardHod;
import com.donnekt.attendanceapp.users.DashboardLecturer;

public class ProfileActivity extends AppCompatActivity {

    Button backHomeBtn, editProfile;
    TextView tvEmailField, tvGenderField, tvPhoneField, tvStaffRole, tvDepartment, tvStaffStatus, tvFullName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Staff activeUser = SharedPrefManager.getInstance(this).getLoggedInStaff();
        String fullName = activeUser.getFirstname() + " " + activeUser.getLastname();
        String staffRole = activeUser.getRole();

        backHomeBtn = findViewById(R.id.backHomeBtn);
        editProfile = findViewById(R.id.editProfileBtn);
        tvEmailField = findViewById(R.id.tvEmailField);
        tvGenderField = findViewById(R.id.tvGenderField);
        tvPhoneField = findViewById(R.id.tvPhoneField);
        tvStaffRole = findViewById(R.id.tvStaffRole);
        tvFullName = findViewById(R.id.tvFullName);
        tvDepartment = findViewById(R.id.tvDepartment);
        tvStaffStatus = findViewById(R.id.tvStaffStatus);

        findViewById(R.id.backHomeBtn).setOnClickListener(v -> {
            switch (staffRole) {
                case "DAS":
                    startActivity(new Intent(ProfileActivity.this, DashboardDas.class));
                    break;
                case "HOD":
                    startActivity(new Intent(ProfileActivity.this, DashboardHod.class));
                    break;
                case "DOQ":
                    startActivity(new Intent(ProfileActivity.this, DashboardDoq.class));
                    break;
                case "LECTURER":
                    startActivity(new Intent(ProfileActivity.this, DashboardLecturer.class));
                    break;

                case "DPAT":
                    startActivity(new Intent(ProfileActivity.this, DashboardDpat.class));
                    break;

                default:
                    Toast.makeText(getApplicationContext(), "PROBLEM OCCURRED!", Toast.LENGTH_SHORT).show();
            }
        });

        // RECEIVING DATA
        tvStaffRole.setText(activeUser.getRole());
        tvDepartment.setText(activeUser.getRefDeptName());
        tvStaffStatus.setText(activeUser.getStatus());

        tvFullName.setText((fullName));
        tvPhoneField.setText(activeUser.getTelephone());
        tvEmailField.setText(activeUser.getEmail());
        tvGenderField.setText(activeUser.getGender());

        editProfile.setOnClickListener(view -> {
            Intent g = new Intent(getApplicationContext(), ProfileUpdate.class);
            String staffId = String.valueOf(activeUser.getStaffId());
            g.putExtra("staff_id_key", staffId);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(g);
        });

    }
}
