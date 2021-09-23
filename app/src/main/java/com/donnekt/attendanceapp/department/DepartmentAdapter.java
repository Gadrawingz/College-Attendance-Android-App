package com.donnekt.attendanceapp.department;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import com.donnekt.attendanceapp.R;

import java.util.List;

public class DepartmentAdapter extends ArrayAdapter<Department> {

    Context mCtx;
    int listLayoutRes;
    List<Department> departmentList;
    SQLiteDatabase mDatabase;

    public DepartmentAdapter(Context mCtx, int listLayoutRes, List<Department> departmentList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, departmentList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.departmentList = departmentList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        // Getting employee of the specified position
        Department department = departmentList.get(position);

        // Getting views
        TextView textViewDeptName = view.findViewById(R.id.tvDeptName);
        TextView textViewDeptCaption = view.findViewById(R.id.tvDeptCaption);

        // Adding data to views (using DepartmentClass
        textViewDeptName.setText(department.getDepartmentName());
        textViewDeptCaption.setText(">>"+department.getDepartmentCaption().trim());

        // We will use these buttons later for update and delete operation
        Button buttonDelete = view.findViewById(R.id.buttonDeleteDept);
        Button buttonEdit = view.findViewById(R.id.buttonEditDept);

        // Adding a clickListener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDepartment(department);
            }
        });

        // The delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Are you sure you want to delete ("+department.departmentName+")?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sql = "DELETE FROM departments WHERE departmentId = ?";
                        mDatabase.execSQL(sql, new Integer[]{department.getDepartmentId()});
                        reloadDepartmentsFromDatabase();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }


    private void updateDepartment(final Department department) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_department, null);
        builder.setView(view);

        final EditText deptName = view.findViewById(R.id.editDeptName);
        final EditText deptCaption = view.findViewById(R.id.editDeptCaption);

        deptName.setText(department.getDepartmentName());
        deptCaption.setText(department.getDepartmentCaption());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdateDept).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = deptName.getText().toString().trim();
                String caption = deptCaption.getText().toString().trim();

                if (name.isEmpty()) {
                    deptName.setError("Name can't be blank");
                    deptName.requestFocus();
                    return;
                }

                if (caption.isEmpty()) {
                    deptCaption.setError("Description is Required");
                    deptCaption.requestFocus();
                    return;
                }

                String sql = "UPDATE departments SET departmentName = ?, departmentCaption = ? WHERE departmentId = ?";

                String DEPT_ID = String.valueOf(department.getDepartmentId());
                mDatabase.execSQL(sql, new String[]{name, caption, DEPT_ID});

                Toast.makeText(mCtx, "Department's updated", Toast.LENGTH_SHORT).show();
                // Then, Reload departments daRa again!
                reloadDepartmentsFromDatabase();
                dialog.dismiss();
            }
        });

    }


    private void reloadDepartmentsFromDatabase() {
        String sql = "SELECT * FROM departments";
        Cursor cursorDepartments = mDatabase.rawQuery(sql, null);
        if (cursorDepartments.moveToFirst()) {
            departmentList.clear();
            do {
                departmentList.add(new Department(
                        cursorDepartments.getInt(0),
                        cursorDepartments.getString(1),
                        cursorDepartments.getString(2)
                ));
            } while (cursorDepartments.moveToNext());
        }
        cursorDepartments.close();
        notifyDataSetChanged();
    }




}
