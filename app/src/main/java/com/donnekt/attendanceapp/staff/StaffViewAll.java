package com.donnekt.attendanceapp.staff;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StaffViewAll extends AppCompatActivity {

    List<Staff> staffList;
    ListView listViewStaffs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_view_all);

        listViewStaffs = (ListView) findViewById(R.id.listViewStaffs);
        staffList = new ArrayList<>();

        loadStaffMemberList();
    }

    private void loadStaffMemberList() {
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadingProgBar);
        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STAFF_VIEW_ALL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray staffArray = object.getJSONArray("staffs");

                    for(int i=0; i<staffArray.length(); i++) {
                        JSONObject staffObject = staffArray.getJSONObject(i);

                        Staff staff = new Staff(
                                staffObject.getString("firstname"),
                                staffObject.getString("lastname"),
                                staffObject.getString("email"),
                                staffObject.getString("telephone"),
                                staffObject.getString("staff_role"),
                                staffObject.getString("password"),
                                staffObject.getString("reg_date")
                        );
                        staffList.add(staff);
                        ListViewAdapter adapter = new ListViewAdapter(staffList, getApplicationContext());
                        listViewStaffs.setAdapter(adapter);
                    }

                } catch (JSONException error) {
                    error.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}