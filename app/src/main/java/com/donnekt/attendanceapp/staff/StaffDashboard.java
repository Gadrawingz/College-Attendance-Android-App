package com.donnekt.attendanceapp.staff;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefHandler;
import com.donnekt.attendanceapp.SharedPrefManager;

public class StaffDashboard extends AppCompatActivity {

    private SharedPrefHandler sharedPrefHandler;
    TextView firstname, lastname, email;
    Button btnLogout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        sharedPrefHandler = new SharedPrefHandler(this);

        String staffFName = sharedPrefHandler.getFirstname();

        /*if(staffFName == null || staffFName.isEmpty()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }*/


        firstname = findViewById(R.id.TVFirstName);
        lastname = findViewById(R.id.TVLastName);
        email = findViewById(R.id.TVEmail);
        btnLogout = findViewById(R.id.logoutButton);

        firstname.setText(sharedPrefHandler.getFirstname());
        lastname.setText(sharedPrefHandler.getLastname());
        email.setText(sharedPrefHandler.getEmail());

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sharedPrefHandler.setFirstname(null);
                sharedPrefHandler.setLastname(null);
                sharedPrefHandler.setEmail(null);
                sharedPrefHandler.setGender(null);
                sharedPrefHandler.setRole(null);
                startActivity(new Intent(StaffDashboard.this, MainActivity.class));
            }
        });
    }
}