package com.donnekt.attendanceapp.users;

import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.LoginActivity;
import com.donnekt.attendanceapp.ProfileActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.users.reports.ReportHod;
import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardHod extends AppCompatActivity {

    MaterialButton goToProfile;
    TextView textRole, userNames,
            card1LowerFirst, card1UpperFirst, card2LowerFirst, card2UpperFirst,
            card3LowerFirst, card3UpperFirst, card4LowerFirst, card4UpperFirst;
    String STAFF_DEPT_ID;

    ConstraintLayout boxViewReport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_hod);

        Staff staffMember = SharedPrefManager.getInstance(this).getLoggedInStaff();
        String activeRole = staffMember.getRole();
        String staffNames = staffMember.getFirstname()+" "+staffMember.getLastname();
        STAFF_DEPT_ID  = String.valueOf(staffMember.getRefDeptId());
        String STAFF_DEPT_NAME  = staffMember.getRefDeptName();

        card1LowerFirst = findViewById(R.id.card1LowerFirst);
        card1UpperFirst = findViewById(R.id.card1UpperFirst);

        card2LowerFirst = findViewById(R.id.card2LowerFirst);
        card2UpperFirst = findViewById(R.id.card2UpperFirst);

        card3LowerFirst = findViewById(R.id.card3LowerFirst);
        card3UpperFirst = findViewById(R.id.card3UpperFirst);

        card4LowerFirst = findViewById(R.id.card4LowerFirst);
        card4UpperFirst = findViewById(R.id.card4UpperFirst);
        boxViewReport = findViewById(R.id.boxReport);

        card1LowerFirst.setText("Reports");
        boxViewReport.setOnClickListener(v -> {
            Intent i = new Intent(DashboardHod.this, ReportHod.class);
            startActivity(i);
        });

        goToProfile = findViewById(R.id.viewProfile);

        userNames = findViewById(R.id.userNames);
        textRole = findViewById(R.id.textRole);

        userNames.setText(staffNames);
        textRole.setText("As ("+activeRole+")");

        // Use this from Dialog class
        getAllCounts();

        // Getting shit done!
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {
            findViewById(R.id.viewMenus).setOnClickListener(v -> {
                String sentWord = activeRole;
                Intent i = new Intent(DashboardHod.this, StaffMenus.class);
                i.putExtra("sent_role", sentWord);
                startActivity(i);
            });

            findViewById(R.id.viewProfile).setOnClickListener(v -> {
                String sentWord = activeRole;
                Intent i = new Intent(DashboardHod.this, ProfileActivity.class);
                i.putExtra("sent_role", sentWord);
                startActivity(i);
            });

        } else {
            Intent intent = new Intent(DashboardHod.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void getAllCounts() {
        showDamnProgressDialog(this, "Loading...","Please wait...", true);
        @SuppressLint("SetTextI18n")
        StringRequest countStaffReq = new StringRequest(Request.Method.GET, URLs.CLASS_DEPT_LECT+STAFF_DEPT_ID, response -> {
            try {
                JSONObject object = new JSONObject(response);
                // Push shit!
                card2LowerFirst.setText("Lecturers");
                card2UpperFirst.setText(object.optString("counts"));
                exitDamnProgressDialog();
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), " No Network!", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countStaffReq);

        StringRequest countClassReq = new StringRequest(Request.Method.GET, URLs.CLASS_DEPT_CLASS+STAFF_DEPT_ID, response -> {
            try {
                JSONObject object = new JSONObject(response);
                // Push shit!
                card3LowerFirst.setText("Classes");
                card3UpperFirst.setText(object.optString("counts"));
                exitDamnProgressDialog();
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countClassReq);

        StringRequest countStudReq = new StringRequest(Request.Method.GET, URLs.CLASS_DEPT_STUD+STAFF_DEPT_ID, response -> {
            try {
                JSONObject object = new JSONObject(response);
                // Push shit!
                card4LowerFirst.setText("Students");
                card4UpperFirst.setText(object.optString("counts"));
                exitDamnProgressDialog();
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countStudReq);
    }
}