package com.donnekt.attendanceapp.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.users.DashboardDas;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StaffDetails extends AppCompatActivity {

    List<Staff> staffList;
    ListView listViewStaffs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_details);

        listViewStaffs = findViewById(R.id.listViewStaffs);
        staffList = new ArrayList<>();

        // Load shit now!
        loadStaffMemberList();
    }

    private void loadStaffMemberList() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        // Getting ID shit
        String staffIdKey = getIntent().getStringExtra("staff_id_key");

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STAFF_VIEW_ONE+staffIdKey, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray staffArray = object.getJSONArray("staff");

                for(int i=0; i<staffArray.length(); i++) {
                    JSONObject staffObject = staffArray.getJSONObject(i);

                    Staff staff = new Staff(
                            staffObject.getInt("staff_id"),
                            staffObject.getString("firstname"),
                            staffObject.getString("lastname"),
                            staffObject.getString("email"),
                            staffObject.getString("telephone"),
                            staffObject.getString("gender"),
                            staffObject.getString("staff_role"),
                            staffObject.getString("staff_status"),
                            staffObject.getString("password"),
                            staffObject.getString("reg_date")
                    );
                    staffList.add(staff);
                    StaffADetails adapter = new StaffADetails(staffList, getApplicationContext());
                    listViewStaffs.setAdapter(adapter);
                }

            } catch (JSONException error) {
                error.printStackTrace();
            }
        }, error -> Toast.makeText(getApplicationContext(), "Network error!", Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
