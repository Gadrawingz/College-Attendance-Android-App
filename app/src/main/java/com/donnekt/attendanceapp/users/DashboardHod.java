package com.donnekt.attendanceapp.users;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.*;
import com.donnekt.attendanceapp.staff.Staff;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

public class DashboardHod extends AppCompatActivity {

    TextView tvCurrentDate, boxInHeader, topDHeader, goToProfile,
            card1LowerFirst, card1UpperFirst, card2LowerFirst, card2UpperFirst,
            card3LowerFirst, card3UpperFirst, card4LowerFirst, card4UpperFirst, manageActs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_das);

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

        manageActs = findViewById(R.id.manageActs);
        goToProfile = findViewById(R.id.goToProfile);

        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        boxInHeader = findViewById(R.id.boxInHeader);
        topDHeader = findViewById(R.id.topDHeader);

        tvCurrentDate.setText("Today's: "+todayDate);

        // Use this from Dialog class
        getAllCounts();

        // Getting shit done!
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {

            Staff staffMember = SharedPrefManager.getInstance(this).getLoggedInStaff();
            String activeRole = staffMember.getRole();

            findViewById(R.id.manageActs).setOnClickListener(v -> {
                String sentWord = activeRole;
                Intent i = new Intent(DashboardHod.this, StaffMenus.class);
                i.putExtra("sent_role", sentWord);
                startActivity(i);
            });

            findViewById(R.id.goToProfile).setOnClickListener(v -> {
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
        showDamnProgressDialog(this, "Loading...","Retrieving data...", true);

        StringRequest countDeptReq = new StringRequest(Request.Method.GET, URLs.DEPT_VIEW_ALL, response -> {
            try {
                JSONObject object = new JSONObject(response);
                //Damn it!
                card1LowerFirst.setText("Departments");
                card1UpperFirst.setText(object.optString("counts"));
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
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
        }, error -> Toast.makeText(getApplicationContext(), "Network problem!", Toast.LENGTH_SHORT).show());
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
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
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
        }, error -> Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(countStudReq);

    }
}