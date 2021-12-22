package com.donnekt.attendanceapp.users;

import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.donnekt.attendanceapp.LoginActivity;
import com.donnekt.attendanceapp.ProfileActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.VolleySingleton;
import com.donnekt.attendanceapp.module.ModulesLecturer;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.student.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DashboardLecturer extends AppCompatActivity {

    TextView
            tvCurrentDate, boxInHeader, topDHeader, makeAttendance, goToProfile,
            reviewAttendance,
            card1LowerFirst, card1UpperFirst, card2LowerFirst, card2UpperFirst;
    String STAFF_ID_KEY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_lecturer);

        Staff staffMember = SharedPrefManager.getInstance(this).getLoggedInStaff();
        STAFF_ID_KEY  = String.valueOf(staffMember.getStaffId());

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todayDate = dateFormat.format(calendar.getTime());

        card1LowerFirst = findViewById(R.id.card1LowerFirst);
        card1UpperFirst = findViewById(R.id.card1UpperFirst);

        card2LowerFirst = findViewById(R.id.card2LowerFirst);
        card2UpperFirst = findViewById(R.id.card2UpperFirst);

        makeAttendance = findViewById(R.id.make_attendance);
        reviewAttendance = findViewById(R.id.review_attendance);
        goToProfile = findViewById(R.id.goToProfile);

        tvCurrentDate = findViewById(R.id.tvCurrentDate);
        boxInHeader = findViewById(R.id.boxInHeader);
        topDHeader = findViewById(R.id.topDHeader);

        // If you are logged in!
        if(SharedPrefManager.getInstance(this).isLoggedIn()) {
            String activeRole = staffMember.getRole();
            String fullName = "Lect. "+staffMember.getLastname();

            boxInHeader.setText(activeRole+" 's Overview".toUpperCase());
            topDHeader.setText(fullName.toUpperCase());

            // Go to menus
            findViewById(R.id.backToDash).setOnClickListener(v -> {
                String sentWord = activeRole;
                Intent i = new Intent(DashboardLecturer.this, StaffMenus.class);
                i.putExtra("sent_role", sentWord);
                startActivity(i);
            });


        } else {
            Intent intent = new Intent(DashboardLecturer.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

        loadAllCounts();

        makeAttendance.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ModulesLecturer.class));
        });

        goToProfile.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        });

        reviewAttendance.setOnClickListener(v -> {
            LayoutInflater inflater = getLayoutInflater();
            View layout = inflater.inflate(R.layout.lecturer_last_day, (ViewGroup) findViewById(R.id.custom_toast_layout));
            TextView tv = (TextView) layout.findViewById(R.id.showSample);

            StringRequest countStudReq = new StringRequest(Request.Method.GET, URLs.MAX_DAY_COUNT+STAFF_ID_KEY, response -> {
                try {
                    JSONObject object = new JSONObject(response);

                    JSONArray staffArray = object.getJSONArray("lecturer_last_day");
                    for(int i=0; i<staffArray.length(); i++) {
                        JSONObject staffObject = staffArray.getJSONObject(i);
                        tv.setText(staffObject.optString("max_time")+" day.");
                    }
                } catch (JSONException error) {
                    error.printStackTrace();
                }
            }, error -> Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
            VolleySingleton.getInstance(this).addToRequestQueue(countStudReq);


            Toast toast = new Toast(getApplicationContext());
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
            toast.setDuration(Toast.LENGTH_LONG);
            toast.setView(layout);
            toast.show();
        });
    }

    private void loadAllCounts() {
        showDamnProgressDialog(this, "Loading...","Please wait...", true);
        StringRequest count1 = new StringRequest(Request.Method.GET, URLs.MOD_VIEW_LECT+STAFF_ID_KEY, response -> {
            try {
                JSONObject object = new JSONObject(response);
                card1LowerFirst.setText("Modules");
                card1UpperFirst.setText(object.optString("counts"));
                exitDamnProgressDialog();
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(count1);


        StringRequest count2 = new StringRequest(Request.Method.GET, URLs.CLASS_VIEW_GROUP+STAFF_ID_KEY, response -> {
            try {
                JSONObject object = new JSONObject(response);
                card2LowerFirst.setText("Classes");
                card2UpperFirst.setText(object.optString("counts"));
                exitDamnProgressDialog();
            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "No Network", Toast.LENGTH_SHORT).show());
        VolleySingleton.getInstance(this).addToRequestQueue(count2);

    }
}