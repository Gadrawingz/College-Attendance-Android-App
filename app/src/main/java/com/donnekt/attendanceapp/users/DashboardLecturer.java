package com.donnekt.attendanceapp.users;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.LoginActivity;
import com.donnekt.attendanceapp.ProfileActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.module.ModulesLecturer;
import com.donnekt.attendanceapp.staff.Staff;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DashboardLecturer extends AppCompatActivity {

    TextView
            tvCurrentDate, boxInHeader, topDHeader, makeAttendance, goToProfile,
            card1LowerFirst, card1UpperFirst, card2LowerFirst, card2UpperFirst,
            card3LowerFirst, card3UpperFirst, card4LowerFirst, card4UpperFirst;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_lecturer);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-mm-yyyy");
        String todayDate = dateFormat.format(calendar.getTime());

        card1LowerFirst = findViewById(R.id.card1LowerFirst);
        card1UpperFirst = findViewById(R.id.card1UpperFirst);

        card2LowerFirst = findViewById(R.id.card2LowerFirst);
        card2UpperFirst = findViewById(R.id.card2UpperFirst);

        card3LowerFirst = findViewById(R.id.card3LowerFirst);
        card3UpperFirst = findViewById(R.id.card3UpperFirst);

        card4LowerFirst = findViewById(R.id.card4LowerFirst);
        card4UpperFirst = findViewById(R.id.card4UpperFirst);

        makeAttendance = findViewById(R.id.make_attendance);
        goToProfile = findViewById(R.id.goToProfile);

        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        boxInHeader = findViewById(R.id.boxInHeader);
        topDHeader = findViewById(R.id.topDHeader);


        // If you are logged in!
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {

            Staff staffMember = SharedPrefManager.getInstance(this).getLoggedInStaff();
            String activeRole = staffMember.getRole();
            String fullName = "Lect. "+staffMember.getLastname();

            boxInHeader.setText(activeRole+" 's Overview".toUpperCase());
            topDHeader.setText(fullName.toUpperCase());


            // 1, 2, 3 & 4
            card1LowerFirst.setText("Your courses");
            card1UpperFirst.setText("2");

            card2LowerFirst.setText("Classes");
            card2UpperFirst.setText("21");

            card3LowerFirst.setText("Students");
            card3UpperFirst.setText("77");

            card4LowerFirst.setText("Semester");
            card4UpperFirst.setText("2");

            // Push time in!
            tvCurrentDate.setText("Today's: "+todayDate+" >>");

            // Go to menus
            findViewById(R.id.backToDash).setOnClickListener(v -> {
                String sentWord = activeRole;
                Intent i = new Intent(DashboardLecturer.this, StaffMenus.class);
                i.putExtra("sent_role", sentWord);
                startActivity(i);
            });


        } else {
            Intent intent = new Intent(DashboardLecturer.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        makeAttendance.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ModulesLecturer.class));
        });

        goToProfile.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        });

    }
}