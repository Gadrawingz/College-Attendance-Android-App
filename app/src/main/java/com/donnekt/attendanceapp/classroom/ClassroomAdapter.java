package com.donnekt.attendanceapp.classroom;

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
import com.donnekt.attendanceapp.department.Department;

import java.util.List;

public class ClassroomAdapter extends ArrayAdapter<Classroom> {

    Context mCtx;
    int listLayoutRes;
    List<Classroom> classroomList;
    SQLiteDatabase mDatabase;

    public ClassroomAdapter(Context mCtx, int listLayoutRes, List<Classroom> classroomList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, classroomList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.classroomList = classroomList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        // Getting employee of the specified position
        Classroom classroom = classroomList.get(position);

        // Getting views
        TextView textViewCName = view.findViewById(R.id.tvClassName);
        TextView textViewCLevel = view.findViewById(R.id.tvClassLevel);
        TextView textViewCDept = view.findViewById(R.id.tvClassDept);

        // Adding data to views (using DepartmentClass
        textViewCName.setText(classroom.getClassroomName());
        textViewCLevel.setText(classroom.getClassroomLevel());
        textViewCDept.setText("Department: ("+classroom.getDepartment()+")");

        // We will use these buttons later for update and delete operation
        Button buttonDelete = view.findViewById(R.id.buttonDelete);
        Button buttonEdit = view.findViewById(R.id.buttonEdit);

        // Adding a clickListener using LAMBDA_SHIT
        buttonEdit.setOnClickListener(view1 -> updateClassroom(classroom));

        // The delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Sure u want to delete ("+classroom.classroomName+")?");

                builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    String sql = "DELETE FROM classrooms WHERE classroomId = ?";
                    mDatabase.execSQL(sql, new Integer[]{classroom.getClassroomId()});
                    reloadClassroomsFromDatabase();
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


    private void updateClassroom(final Classroom classroom) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_classroom, null);
        builder.setView(view);

        final EditText className = view.findViewById(R.id.editClassName);
        final TextView XLevel = view.findViewById(R.id.spinnerXLevel);
        final TextView XDept = view.findViewById(R.id.spinnerXDept);
        final Spinner classLevel = view.findViewById(R.id.spinnerClassLevel);
        final Spinner classDept = view.findViewById(R.id.spinnerClassDept);

        className.setText(classroom.getClassroomName());
        XLevel.setText(classroom.getClassroomLevel());
        XDept.setText(classroom.getDepartment());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = className.getText().toString().trim();
                String level = classLevel.getSelectedItem().toString().trim();
                String dept = classDept.getSelectedItem().toString().trim();

                if (name.isEmpty()) {
                    className.setError("ClassName can't be blank");
                    className.requestFocus();
                    return;
                }

                String sql = "UPDATE classrooms SET classroomName = ?, classroomLevel = ?, department = ? WHERE classroomId = ?";

                String CLASS_ID = String.valueOf(classroom.getClassroomId());
                mDatabase.execSQL(sql, new String[]{name, level, dept, CLASS_ID});

                Toast.makeText(mCtx, "Classroom's updated", Toast.LENGTH_SHORT).show();
                // Then, Reload data again!
                reloadClassroomsFromDatabase();
                dialog.dismiss();
            }
        });
    }


    private void reloadClassroomsFromDatabase() {
        String sql = "SELECT * FROM classrooms";
        Cursor cursorClassrooms = mDatabase.rawQuery(sql, null);
        if (cursorClassrooms.moveToFirst()) {
            classroomList.clear();
            do {
                classroomList.add(new Classroom(
                        cursorClassrooms.getInt(0),
                        cursorClassrooms.getString(1),
                        cursorClassrooms.getString(2),
                        cursorClassrooms.getString(3)
                ));
            } while (cursorClassrooms.moveToNext());
        }
        cursorClassrooms.close();
        notifyDataSetChanged();
    }




}
