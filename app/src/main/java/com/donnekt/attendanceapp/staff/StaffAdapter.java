package com.donnekt.attendanceapp.staff;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.classroom.ClassroomLecturers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.view.View.GONE;

public class StaffAdapter extends ArrayAdapter<Staff> {

    private List<Staff> staffList;
    private Context mCtx;

    StaffAdapter(List<Staff> staffList, Context mCtx) {
        super(mCtx, R.layout.list_layout_staff, staffList);
        this.mCtx = mCtx;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_staff, null, true);

        // Getting views
        TextView textViewFN = LVItem.findViewById(R.id.tvStaffFullName);
        TextView textViewRL = LVItem.findViewById(R.id.tvStaffRole);
        Button btnStaffDetails = LVItem.findViewById(R.id.buttonStaffDetails);
        Button btnDisableStaff = LVItem.findViewById(R.id.buttonDisableStaff);
        Button btnEnableStaff = LVItem.findViewById(R.id.buttonEnableStaff);

        // Getting record of the specified position
        Staff staff = staffList.get(position);

        // Adding data to views Constructor class
        textViewFN.setText(staff.getFirstname()+" "+staff.getLastname());
        String gender = staff.getGender();
        String initShit = gender.equals("Male")? "He ":"She ";
        String statusShit = staff.getStatus().equals("On")? " active ":" not active ";
        textViewRL.setText(initShit+"'s "+statusShit+" ("+staff.getRole()+")");

        btnStaffDetails.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), StaffDetails.class);
            String staffId = String.valueOf(staff.getStaffId());
            g.putExtra("staff_id_key", staffId);

            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);// For killing activity
        });


        if(staff.getStatus().equals("On")) {
            btnEnableStaff.setVisibility(GONE);
        } else {
            btnDisableStaff.setVisibility(GONE);
        }

        btnDisableStaff.setText("Disable");
        btnDisableStaff.setOnClickListener(view -> {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STAFF_DISABLE+staff.getStaffId(),
                    response -> {
                Intent g=new Intent(mCtx.getApplicationContext(), StaffViewAll.class);
                        g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mCtx.startActivity(g);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(mCtx.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(mCtx.getApplicationContext(), "No network!", Toast.LENGTH_SHORT).show()) {
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            requestQueue.add(stringRequest);
        });

        btnEnableStaff.setText("Enable");
        btnEnableStaff.setOnClickListener(view -> {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STAFF_ENABLE+staff.getStaffId(),
                    response -> {

                Intent intent = new Intent(mCtx.getApplicationContext(), StaffViewAll.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mCtx.startActivity(intent);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(mCtx.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(mCtx.getApplicationContext(), "No network!", Toast.LENGTH_SHORT).show()) {
            };
            RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            requestQueue.add(stringRequest);
        });

        // Return the list item
        return LVItem;
    }
}





class LecturerAdapter extends ArrayAdapter<Staff> {
    private List<Staff> staffList;
    private Context mCtx;

    LecturerAdapter(List<Staff> staffList, Context mCtx) {
        super(mCtx, R.layout.list_layout_lecturer, staffList);
        this.mCtx = mCtx;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_lecturer, null, true);

        // Getting views
        TextView textViewFN = LVItem.findViewById(R.id.tvStaffFullName);
        TextView textViewEM = LVItem.findViewById(R.id.tvStaffEmail);
        TextView textViewPH = LVItem.findViewById(R.id.tvStaffPhone);
        Button btnStaffDetails = LVItem.findViewById(R.id.buttonStaffDetails);
        Button btnStaffClasses = LVItem.findViewById(R.id.buttonClasses);
        // Getting record of the specified position
        Staff staff = staffList.get(position);

        // Adding data to views Constructor class
        textViewFN.setText(staff.getFirstname()+" "+staff.getLastname());
        textViewEM.setText(staff.getEmail());
        textViewPH.setText(staff.getTelephone());

        btnStaffDetails.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), StaffDetails.class);
            String staffId = String.valueOf(staff.getStaffId());
            g.putExtra("staff_id_key", staffId);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
        });

        btnStaffClasses.setOnClickListener(view -> {
            Intent g = new Intent(mCtx.getApplicationContext(), ClassroomLecturers.class);
            String staffId = String.valueOf(staff.getStaffId());
            g.putExtra("lecturer_id_key", staffId);
            g.putExtra("department_key_return", "");
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            g.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            mCtx.startActivity(g);
        });

        if(staff.getRole().equals("HOD")) {
            btnStaffDetails.setVisibility(GONE);
        }

        // Return the list item
        return LVItem;
    }
}






class StaffADetails extends ArrayAdapter<Staff> {
    private List<Staff> staffList;
    private Context mCtx;

    StaffADetails(List<Staff> staffList, Context mCtx) {
        super(mCtx, R.layout.layout_staff_details, staffList);
        this.mCtx = mCtx;
        this.staffList = staffList;
    }

    @NonNull
    @Override
    @SuppressLint("ViewHolder")
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.layout_staff_details, null, true);


        Staff member = SharedPrefManager.getInstance(mCtx.getApplicationContext()).getLoggedInStaff();
        String sRole = member.getRole();
        String deptKey = String.valueOf(member.getRefDeptId());

        // Getting views
        Button buttonExitStaff = LVItem.findViewById(R.id.buttonExitStaff);
        Button buttonDisableStaff = LVItem.findViewById(R.id.buttonDisableStaff);

        TextView tvTopTitle = LVItem.findViewById(R.id.tvTopTitle);
        TextView textViewFN = LVItem.findViewById(R.id.tvStaffFirst);
        TextView textViewLN = LVItem.findViewById(R.id.tvStaffLast);
        TextView textViewEM = LVItem.findViewById(R.id.tvStaffEmail);
        TextView textViewPH = LVItem.findViewById(R.id.tvStaffPhone);
        TextView textViewRL = LVItem.findViewById(R.id.tvStaffRole);
        ImageView buttonBack = LVItem.findViewById(R.id.buttonBack);

        // Getting record of the specified position
        Staff staff = staffList.get(position);

        // Adding data to views Constructor class
        tvTopTitle.setText("Staff member details".toUpperCase());
        textViewFN.setText("Firstname: "+staff.getFirstname());
        textViewLN.setText("Lastname: "+staff.getLastname());
        textViewEM.setText("Email: "+staff.getEmail());
        textViewPH.setText("Tel.No: "+staff.getTelephone());
        textViewRL.setText("Role: "+staff.getRole());

        // For details
        buttonExitStaff.setOnClickListener(v -> {
            Intent i = new Intent(mCtx.getApplicationContext(), StaffViewAll.class);
            Intent j = new Intent(mCtx.getApplicationContext(), StaffLecturers.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            j.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if(sRole.equals("DAS")) {
                mCtx.startActivity(i);
            } else if(sRole.equals("HOD")) {
                j.putExtra("department_key", deptKey);
                mCtx.startActivity(j);
            }
        });

        buttonBack.setOnClickListener(v -> {
            Intent i = new Intent(mCtx.getApplicationContext(), StaffViewAll.class);
            Intent j = new Intent(mCtx.getApplicationContext(), StaffLecturers.class);

            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            j.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            if(sRole.equals("DAS")) {
                mCtx.startActivity(i);
            } else if(sRole.equals("HOD")) {
                j.putExtra("department_key", deptKey);
                mCtx.startActivity(j);
            }
        });

        buttonDisableStaff.setText("Disable");
        buttonDisableStaff.setOnClickListener(view -> {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STAFF_DISABLE+staff.getStaffId(),
                    response -> {
                Intent intent=new Intent(mCtx.getApplicationContext(), StaffViewAll.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                        mCtx.startActivity(intent);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            Toast.makeText(mCtx.getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> Toast.makeText(mCtx.getApplicationContext(), "No network!", Toast.LENGTH_SHORT).show()) {
            };

            RequestQueue requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
            requestQueue.add(stringRequest);
        });

        // Return the list item
        return LVItem;

    }
}
