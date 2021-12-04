package com.donnekt.attendanceapp.staff;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.URLs;
import com.donnekt.attendanceapp.module.ModuleViewAll;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import static android.view.View.GONE;
import static com.donnekt.attendanceapp.DialogShit.exitDamnProgressDialog;
import static com.donnekt.attendanceapp.DialogShit.showDamnProgressDialog;

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
            mCtx.startActivity(g);
            g.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY); // For killing activity
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
                        mCtx.startActivity(new Intent(mCtx.getApplicationContext(), StaffViewAll.class));
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
                        mCtx.startActivity(new Intent(mCtx.getApplicationContext(), StaffViewAll.class));
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

        // Getting views
        Button buttonExitStaff = LVItem.findViewById(R.id.buttonExitStaff);
        Button buttonDisableStaff = LVItem.findViewById(R.id.buttonDisableStaff);

        TextView tvTopTitle = LVItem.findViewById(R.id.tvTopTitle);
        TextView textViewFN = LVItem.findViewById(R.id.tvStaffFirst);
        TextView textViewLN = LVItem.findViewById(R.id.tvStaffLast);
        TextView textViewEM = LVItem.findViewById(R.id.tvStaffEmail);
        TextView textViewPH = LVItem.findViewById(R.id.tvStaffPhone);
        TextView textViewRL = LVItem.findViewById(R.id.tvStaffRole);

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
            mCtx.startActivity(i);
            i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        });

        buttonDisableStaff.setText("Disable");
        buttonDisableStaff.setOnClickListener(view -> {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.STAFF_DISABLE+staff.getStaffId(),
                    response -> {
                        mCtx.startActivity(new Intent(mCtx.getApplicationContext(), StaffViewAll.class));
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
