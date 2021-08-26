package com.donnekt.attendanceapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.admin.AdminDashLMenus;
import com.donnekt.attendanceapp.admin.AdminDashboard;

public class MainActivity extends AppCompatActivity {

    EditText uName, uPass;
    Button btnLogin;
    SharedPreferences pref;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uName = (EditText) findViewById(R.id.editUsername);
        uPass = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.buttonLogin);

        pref = getSharedPreferences("admin_details", MODE_PRIVATE);
        intent = new Intent(MainActivity.this, AdminDashboard.class);

        // Condition
        if (pref.contains("sUsername") && pref.contains("sPassword")) {
            startActivity(intent);
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
                String username = uName.getText().toString();
                String password = uPass.getText().toString();

                if (username.equals("Admin") && password.equals("321")) {
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("sUsername", username);
                    editor.putString("sPassword", password);
                    editor.commit();
                    //Toast.makeText(getApplicationContext(), "Login Successful", 0).show();
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid credentials!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}