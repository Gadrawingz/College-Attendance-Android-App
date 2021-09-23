package com.donnekt.attendanceapp.staff;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.donnekt.attendanceapp.R;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<Staff> {

    private List<Staff> staffList;
    private Context mCtx;

    ListViewAdapter(List<Staff> staffList, Context mCtx) {
        super(mCtx, R.layout.list_layout_staff, staffList);
        this.mCtx = mCtx;
        this.staffList = staffList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View LVItem = inflater.inflate(R.layout.list_layout_staff, null, true);


        // Getting views
        TextView textViewFN = LVItem.findViewById(R.id.tvStaffFirst);
        TextView textViewLN = LVItem.findViewById(R.id.tvStaffLast);
        TextView textViewEM = LVItem.findViewById(R.id.tvStaffEmail);
        TextView textViewPH = LVItem.findViewById(R.id.tvStaffPhone);
        TextView textViewRL = LVItem.findViewById(R.id.tvStaffRole);
        TextView textViewPS = LVItem.findViewById(R.id.tvStaffPass);
        TextView textViewDT = LVItem.findViewById(R.id.tvRegDate);

        // Getting record of the specified position
        Staff staff = staffList.get(position);

        // Adding data to views Constructor class
        textViewFN.setText(staff.getFirstname());
        textViewLN.setText(staff.getLastname());
        textViewEM.setText(staff.getEmail());
        textViewPH.setText(staff.getTelephone());
        textViewRL.setText(staff.getRole());
        textViewPS.setText(staff.getPassword());
        textViewDT.setText(staff.getRegDate());

        // Return the list item
        return LVItem;

    }
}