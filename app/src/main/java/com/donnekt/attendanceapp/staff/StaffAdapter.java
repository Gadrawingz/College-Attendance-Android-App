package com.donnekt.attendanceapp.staff;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.admin.AdminDashboard;
import com.donnekt.attendanceapp.department.DepartmentViewAll;

import java.util.List;

import static androidx.core.content.ContextCompat.startActivity;

import android.content.Intent;

public class StaffAdapter extends ArrayAdapter<Staff> {

    private static SQLiteDatabase staticDb;
    Context mCtx;
    int listLayoutRes;
    List<Staff> staffList;
    SQLiteDatabase mDatabase;

    public StaffAdapter(Context mCtx, int listLayoutRes, List<Staff> staffList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, staffList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.staffList = staffList;
        this.mDatabase = mDatabase;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        // Getting record of the specified position
        Staff staff = staffList.get(position);

        // Getting views
        TextView textViewFN = view.findViewById(R.id.tvStaffFirst);
        TextView textViewLN = view.findViewById(R.id.tvStaffLast);
        TextView textViewEM = view.findViewById(R.id.tvStaffEmail);
        TextView textViewPH = view.findViewById(R.id.tvStaffPhone);
        TextView textViewRL = view.findViewById(R.id.tvStaffRole);
        TextView textViewPS = view.findViewById(R.id.tvStaffPass);
        TextView textViewDT = view.findViewById(R.id.tvRegDate);

        // Adding data to views Constructor class
        textViewFN.setText(staff.getFirstname());
        textViewLN.setText(staff.getLastname());
        textViewEM.setText(staff.getEmail());
        textViewPH.setText(staff.getTelephone());
        textViewRL.setText(staff.getRole());
        textViewPS.setText(staff.getPassword());
        textViewDT.setText(staff.getRegDate());

        // Update & delete operation
        Button buttonDelete = view.findViewById(R.id.buttonDeleteStaff);
        Button linkViewStaffDetails = view.findViewById(R.id.linkViewStaffDetails);

        // Adding a clickListener using LAMBDA_SHIT
        linkViewStaffDetails.setOnClickListener(view5 -> viewStaffDetails(staff));


        // The delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Sure u want to delete ("+staff.getFirstname()+" "+staff.getLastname()+"?");

                builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    String sql = "DELETE FROM stafftable WHERE staff_id = ?";
                    mDatabase.execSQL(sql, new Integer[]{staff.getStaffId()});
                    reloadStaffMembersFromDb();
                });

                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    // Nothing happened
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        return view;
    }

    private void updateStaff(final Staff staff) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_staff, null);
        builder.setView(view);

        final EditText staffFN = view.findViewById(R.id.editStaffFName);
        final EditText staffLN = view.findViewById(R.id.editStaffLName);
        final EditText staffEM = view.findViewById(R.id.editStaffEmail);
        final EditText staffPH = view.findViewById(R.id.editStaffPhone);
        final EditText staffRL = view.findViewById(R.id.editStaffRole);
        final EditText staffPS = view.findViewById(R.id.editStaffPassword);
        //final Spinner classDept = view.findViewById(R.id.spinnerClassDept);

        staffFN.setText(staff.getFirstname());
        staffLN.setText(staff.getLastname());
        staffEM.setText(staff.getEmail());
        staffPH.setText(staff.getTelephone());
        staffRL.setText(staff.getRole());
        staffPS.setText(staff.getPassword());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdateStaff).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname = staffFN.getText().toString().trim();
                String lastname = staffLN.getText().toString().trim();
                String email = staffEM.getText().toString().trim();
                String phone = staffPH.getText().toString().trim();
                String role = staffRL.getText().toString().trim();
                String password = staffPS.getText().toString().trim();

                if (firstname.isEmpty()) {
                    staffFN.setError("Firstname can't be blank");
                    staffFN.requestFocus();
                    return;
                }

                String sql = "UPDATE stafftable SET firstname = ?, lastname = ?, email = ?, telephone = ?, staff_role = ?, password = ? WHERE staff_id = ?";

                String STAFF_ID = String.valueOf(staff.getStaffId());
                mDatabase.execSQL(sql, new String[]{firstname, lastname, email, phone, role, password, STAFF_ID});

                Toast.makeText(mCtx, "Staff member's updated", Toast.LENGTH_SHORT).show();

                // Then, Reload data again!
                reloadStaffMembersFromDb();
                dialog.dismiss();
            }
        });
    }

    private void viewStaffDetails(final Staff staff) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_details_staff, null);
        builder.setView(view);

        final TextView detailsHeader = view.findViewById(R.id.detailsHeader);
        final TextView staffFN = view.findViewById(R.id.detailStaffFName);
        final TextView staffLN = view.findViewById(R.id.detailStaffLName);
        final TextView staffEM = view.findViewById(R.id.detailStaffEmail);
        final TextView staffPH = view.findViewById(R.id.detailStaffPhone);
        final TextView staffRL = view.findViewById(R.id.detailStaffRole);
        final TextView staffDT = view.findViewById(R.id.detailRegDate);

        detailsHeader.setText("Staff member details of "+staff.getFirstname()+" "+staff.getLastname());
        staffFN.setText("First Name : "+staff.getFirstname());
        staffLN.setText("Last Name : "+staff.getLastname());
        staffEM.setText("Email address : "+staff.getEmail());
        staffPH.setText("Phone Number : "+staff.getTelephone());
        staffRL.setText("Staff Member Role : "+staff.getRole());
        staffDT.setText("Registered on : "+staff.getRegDate());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.linkUpdateStaff).setOnClickListener(view1 -> updateStaff(staff));
        view.findViewById(R.id.linkExitStaff).setOnClickListener(view1 -> dialog.dismiss());

    }


    private void reloadStaffMembersFromDb() {
        String realSQL = "SELECT * FROM stafftable";
        Cursor cursorStaffs = mDatabase.rawQuery(realSQL, null);
        if (cursorStaffs.moveToFirst()) {
            staffList.clear();
            do {
                staffList.add(new Staff(
                        cursorStaffs.getInt(0),
                        cursorStaffs.getString(1),
                        cursorStaffs.getString(2),
                        cursorStaffs.getString(3),
                        cursorStaffs.getString(4),
                        cursorStaffs.getString(5),
                        cursorStaffs.getString(6),
                        cursorStaffs.getString(7)
                ));
            } while (cursorStaffs.moveToNext());
        }
        cursorStaffs.close();
        notifyDataSetChanged();
    }

}
