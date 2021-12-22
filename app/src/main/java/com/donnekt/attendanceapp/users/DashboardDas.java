package com.donnekt.attendanceapp.users;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.*;
import com.donnekt.attendanceapp.staff.Staff;
import com.google.android.material.button.MaterialButton;
import org.json.JSONException;
import org.json.JSONObject;

import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

public class DashboardDas extends AppCompatActivity {

    MaterialButton goToProfile;
    ImageButton logoutButton, goMenus;
    TextView textRole, userNames,
            card1LowerFirst, card1UpperFirst, card2LowerFirst, card2UpperFirst,
            card3LowerFirst, card3UpperFirst, card4LowerFirst, card4UpperFirst;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_das);

        Staff staffMember = SharedPrefManager.getInstance(this).getLoggedInStaff();
        String activeRole = staffMember.getRole();
        String staffNames = staffMember.getFirstname()+" "+staffMember.getLastname();

        card1LowerFirst = findViewById(R.id.card1LowerFirst);
        card1UpperFirst = findViewById(R.id.card1UpperFirst);

        card2LowerFirst = findViewById(R.id.card2LowerFirst);
        card2UpperFirst = findViewById(R.id.card2UpperFirst);

        card3LowerFirst = findViewById(R.id.card3LowerFirst);
        card3UpperFirst = findViewById(R.id.card3UpperFirst);

        card4LowerFirst = findViewById(R.id.card4LowerFirst);
        card4UpperFirst = findViewById(R.id.card4UpperFirst);

        goToProfile = findViewById(R.id.viewProfile);

        userNames = findViewById(R.id.userNames);
        textRole = findViewById(R.id.textRole);
        logoutButton = findViewById(R.id.logoutButton);

        userNames.setText(staffNames);
        textRole.setText("As ("+activeRole+")");

        // Use this from Dialog class
        getAllCounts();

        // Getting shit done!
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {
            findViewById(R.id.viewMenus).setOnClickListener(v -> {
                String sentWord = activeRole;
                Intent i = new Intent(DashboardDas.this, StaffMenus.class);
                i.putExtra("sent_role", sentWord);
                startActivity(i);
            });

            findViewById(R.id.viewProfile).setOnClickListener(v -> {
                String sentWord = activeRole;
                Intent i = new Intent(DashboardDas.this, ProfileActivity.class);
                i.putExtra("sent_role", sentWord);
                startActivity(i);
            });

        } else {
            Intent intent = new Intent(DashboardDas.this, MainActivity.class);
            startActivity(intent);
            finish();
        }


        // LOGOUT!
        logoutButton.setOnClickListener(v -> {
            SharedPrefManager.getInstance(getApplicationContext()).logout();
            Intent i = new Intent(DashboardDas.this, MainActivity.class);
            startActivity(i);
        });
    }

    private void getAllCounts() {
        showDamnProgressDialog(this, "Loading...","Please wait...", true);

        StringRequest countDeptReq = new StringRequest(Request.Method.GET, URLs.DEPT_VIEW_ALL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                //Damn it!
                card1LowerFirst.setText("Departments");
                card1UpperFirst.setText(object.optString("counts"));
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countDeptReq);

        StringRequest countStaffReq = new StringRequest(Request.Method.GET, URLs.STAFF_VIEW_ALL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                // Push shit!
                card2LowerFirst.setText("Staffs");
                card2UpperFirst.setText(object.optString("counts"));
                exitDamnProgressDialog();
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network!", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countStaffReq);

        StringRequest countClassReq = new StringRequest(Request.Method.GET, URLs.CLASS_VIEW_ALL, response -> {
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

        StringRequest countStudReq = new StringRequest(Request.Method.GET, URLs.STUD_VIEW_ALL, response -> {
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