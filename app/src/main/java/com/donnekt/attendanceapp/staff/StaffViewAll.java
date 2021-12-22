package com.donnekt.attendanceapp.staff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.department.DepartmentViewAll;
import com.donnekt.attendanceapp.users.StaffMenus;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class StaffViewAll extends AppCompatActivity {

    List<Staff> staffList;
    ListView listViewStaffs;
    TextView mainTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_all);

        mainTitle = findViewById(R.id.mainTitle);
        listViewStaffs = findViewById(R.id.listViewStaffs);
        staffList = new ArrayList<>();
        Staff user = SharedPrefManager.getInstance(this).getLoggedInStaff();
        mainTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sentWord = user.getRole();
                Intent intent =new Intent(StaffViewAll.this, StaffMenus.class);
                intent.putExtra("sent_role", sentWord);
                startActivity(intent);
            }
        });

        loadStaffMemberList();
    }

    private void loadStaffMemberList() {
        final ProgressBar progressBar = findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STAFF_VIEW_ALL, response -> {
            progressBar.setVisibility(View.INVISIBLE);
            try {
                JSONObject object = new JSONObject(response);
                JSONArray staffArray = object.getJSONArray("staffs");

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
                    StaffAdapter adapter = new StaffAdapter(staffList, getApplicationContext());
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
