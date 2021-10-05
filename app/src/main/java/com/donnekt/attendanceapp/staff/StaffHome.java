package com.donnekt.attendanceapp.staff;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.donnekt.attendanceapp.ProfileActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.classroom.ClassroomGroup;
import com.donnekt.attendanceapp.classroom.ClassroomViewAll;
import com.donnekt.attendanceapp.student.StudentViewAll;

public class StaffHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ImageButton backButton, logoutButton, profileButton;
        Button todoButton, editProfileB;
        CardView classesCard, settingsCard;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_home);

        // register all the Buttons, CardViews,ImageButtons with their appropriate IDs
        backButton = findViewById(R.id.backB);
        logoutButton = findViewById(R.id.logOutB);
        editProfileB = findViewById(R.id.editProfileB);
        classesCard = findViewById(R.id.classesCard);
        settingsCard = findViewById(R.id.settingsCard);

        backButton.setOnClickListener(view ->Toast.makeText(this, "Back Button", Toast.LENGTH_SHORT).show());
        logoutButton.setOnClickListener(view ->Toast.makeText(this, "Logout Button", Toast.LENGTH_SHORT).show());

        Intent classView, profileView;

        classView = new Intent(StaffHome.this, ClassroomGroup.class);
        classesCard.setOnClickListener(view -> startActivity(classView));

        profileView = new Intent(StaffHome.this, ProfileActivity.class);
        editProfileB.setOnClickListener(view -> startActivity(profileView));


    }
}