package com.donnekt.attendanceapp;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class StaffDashboard extends AppCompatActivity {

    String usernameHolder;
    TextView username;
    Button  buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);


        username = (TextView)findViewById(R.id.tvUsername);
        buttonLogout = (Button)findViewById(R.id.btnLogout);
        Intent intent = getIntent();


        // Receiving Username sent By StaffLogin.
        usernameHolder = intent.getStringExtra(StaffLogin.Username);

        // Setting up received email to TextView.
        username.setText(username.getText().toString()+ usernameHolder);

        // Adding click listener to Log Out button.
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Finishing current DashBoard activity on button click.
                finish();
                Toast.makeText(StaffDashboard.this,"Log Out Successful", Toast.LENGTH_LONG).show();
            }
        });


    }
}