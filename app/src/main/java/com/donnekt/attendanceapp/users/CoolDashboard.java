package com.donnekt.attendanceapp.users;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.R;

public class CoolDashboard extends AppCompatActivity {

    ImageView hamburgerMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cool_dashboard);

        hamburgerMenu = findViewById(R.id.hamburgerMenu);

        hamburgerMenu.setOnClickListener(view -> {
            //startActivity(new Intent(CoolDashboard.this, Menus.class));
        });

    }
}