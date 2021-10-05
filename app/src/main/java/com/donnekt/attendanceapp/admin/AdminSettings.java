package com.donnekt.attendanceapp.admin;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.donnekt.attendanceapp.DarkModePrefManager;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.ProfileActivity;
import com.donnekt.attendanceapp.R;

public class AdminSettings extends AppCompatActivity {

    SharedPreferences pref;
    Intent intent;
    TextView result, tvGo2AdminHome, goToMenus, tvViewProfile;
    Button btnLogOut;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        result = findViewById(R.id.tvLoggedInUser);
        goToMenus = findViewById(R.id.goToMenus);
        tvViewProfile = findViewById(R.id.goToProfile);
        tvGo2AdminHome = findViewById(R.id.backToAdminHome);
        btnLogOut = findViewById(R.id.buttonLogout);

        pref = getSharedPreferences("admin_details", MODE_PRIVATE);
        intent = new Intent(this, MainActivity.class);

        result.setText("(" + pref.getString("sUsername", null)+")");

        // Logout by clickin'
        btnLogOut.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.apply();
            startActivity(intent);
        });

        goToMenus.setOnClickListener(v -> startActivity(new Intent(this, AdminDashLMenus.class)));
        tvViewProfile.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
        tvGo2AdminHome.setOnClickListener(v -> startActivity(new Intent(this, AdminDashboard.class)));

        if(new DarkModePrefManager(this).isNightMode()){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }

        //function for enabling dark mode
        setDarkModeSwitch();
    }

    private void setDarkModeSwitch(){
        // Variables
        Switch darkModeSwitch = findViewById(R.id.darkModeSwitch);
        darkModeSwitch.setChecked(new DarkModePrefManager(this).isNightMode());
        darkModeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            Toast.makeText(AdminSettings.this, "Dark mode is not ready yet!", Toast.LENGTH_SHORT).show();
            /*DarkModePrefManager darkModePrefManager = new DarkModePrefManager(AdminSettings.this);
            darkModePrefManager.setDarkMode(!darkModePrefManager.isNightMode());
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            recreate();*/
        });
    }
}