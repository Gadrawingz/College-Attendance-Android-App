package com.donnekt.attendanceapp.student;

import static android.R.layout.simple_spinner_item;
import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;
import static com.donnekt.attendanceapp.Methods.capitalizeAllFirstLetters;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.module.ModuleActivity;
import com.donnekt.attendanceapp.staff.Staff;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClaimingPath extends AppCompatActivity {

    EditText attendanceDay;
    ProgressBar loadingProgBar;
    Button registerBtn;

    String FINAL_MODULE_ID, FINAL_CLASS_ID, FINAL_STAFF_ID;

    Spinner lecturerId;
    private ArrayList<Staff> staffArrayList;
    private final ArrayList<String> lecturers = new ArrayList<>();
    private int selectedLecturer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_claiming_path);

        loadingProgBar = findViewById(R.id.loadingProgress);
        attendanceDay = findViewById(R.id.attendanceDay);
        lecturerId = findViewById(R.id.spinnerLecturer);
        registerBtn = findViewById(R.id.submitButton);
        registerBtn.setOnClickListener(view -> submitClaimingData());

        FINAL_MODULE_ID = getIntent().getStringExtra("ref_module_id_key");
        FINAL_CLASS_ID = getIntent().getStringExtra("ref_class_id_key");
        FINAL_STAFF_ID = getIntent().getStringExtra("ref_staff_id_key");

        loadAllLecturers();

        lecturerId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long id) {
                for (Staff staff : staffArrayList) {
                    if(lecturerId.getSelectedItem().toString().trim().equals(staff.getFirstname()+" "+staff.getLastname())) {
                        selectedLecturer = staff.getStaffId();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) { }
        });

    }

    private void loadAllLecturers() {
        showDamnProgressDialog(this, "Loading...","Please wait...",false);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STAFF_LECTURERS,
                response -> {
                    Log.d("strrrrr", ">>" + response);
                    try {
                        JSONObject obj = new JSONObject(response);
                        if(obj.optString("status").equals("fetched")){
                            staffArrayList = new ArrayList<>();
                            JSONArray dataArray  = obj.getJSONArray("lecturers");

                            for (int i = 0; i < dataArray.length(); i++) {
                                Staff staffModel = new Staff();
                                JSONObject dataObj = dataArray.getJSONObject(i);
                                staffModel.setStaffId(dataObj.getInt("staff_id"));
                                staffModel.setFirstname(dataObj.getString("firstname"));
                                staffModel.setLastname(dataObj.getString("lastname"));
                                staffArrayList.add(staffModel);
                            }

                            for (Staff staff : staffArrayList) {
                                String FULL_NAME = capitalizeAllFirstLetters(staff.getFirstname()+" "+staff.getLastname());
                                lecturers.add(FULL_NAME);
                            }

                            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(ClaimingPath.this, simple_spinner_item, lecturers);
                            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                            lecturerId.setAdapter(spinnerArrayAdapter);
                            exitDamnProgressDialog();
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Toast.makeText(getApplicationContext(), "Network issues!", Toast.LENGTH_LONG).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void submitClaimingData() {
        final String AT_DAY = attendanceDay.getText().toString().trim();
        final String LEC_ID = String.valueOf(selectedLecturer);

        if (TextUtils.isEmpty(AT_DAY)) {
            attendanceDay.setError("Please enter attendance Day!");
            attendanceDay.requestFocus();
            return;
        }

        Intent g = new Intent(getApplicationContext(), AttendanceUpdate.class);
        g.putExtra("ref_class_id_key", FINAL_CLASS_ID);
        g.putExtra("ref_module_id_key", FINAL_MODULE_ID);
        g.putExtra("ref_staff_id_key", LEC_ID);
        g.putExtra("attendance_day", AT_DAY);

        g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(g);

    }

}