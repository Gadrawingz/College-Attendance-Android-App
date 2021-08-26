package com.donnekt.attendanceapp.admin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.R;

public class AdminSettings extends AppCompatActivity {

    // Variables
    SharedPreferences pref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_settings);

        TextView result = (TextView) findViewById(R.id.tvLoggedInUser);
        Button btnLogOut = (Button) findViewById(R.id.buttonLogout);

        pref = getSharedPreferences("admin_details", MODE_PRIVATE);
        intent = new Intent(this, MainActivity.class);

        result.setText("(" + pref.getString("sUsername", null)+")");

        // Logout by clickin'
        btnLogOut.setOnClickListener(v -> {
            SharedPreferences.Editor editor = pref.edit();
            editor.clear();
            editor.commit();
            startActivity(intent);
        });

        TextView goToMenus = (TextView) findViewById(R.id.goToMenus);
        goToMenus.setMovementMethod(LinkMovementMethod.getInstance());
        goToMenus.setOnClickListener(v -> {
            Intent intent = new Intent(this, AdminDashLMenus.class);
            startActivity(intent);
        });
    }
}