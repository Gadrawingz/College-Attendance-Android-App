package com.donnekt.attendanceapp.module;

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

public class ModuleAdapter extends ArrayAdapter<Module> {

    Context mCtx;
    int listLayoutRes;
    List<Module> moduleList;
    SQLiteDatabase mDatabase;

    public ModuleAdapter(Context mCtx, int listLayoutRes, List<Module> moduleList, SQLiteDatabase mDatabase) {
        super(mCtx, listLayoutRes, moduleList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.moduleList = moduleList;
        this.mDatabase = mDatabase;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        // Getting module of the specified position
        Module module = moduleList.get(position);

        // Getting views
        TextView textViewModuleName = view.findViewById(R.id.tvModuleName);
        TextView textViewModuleCode = view.findViewById(R.id.tvModuleCode);
        TextView textViewDepartment = view.findViewById(R.id.tvModDept);
        TextView textViewLecturerId = view.findViewById(R.id.tvModLecturer);

        // Adding data to views
        textViewModuleName.setText(module.getModuleName());
        textViewModuleCode.setText(module.getModuleCode());
        textViewDepartment.setText(module.getDepartmentId());
        textViewLecturerId.setText(module.getLecturerId());

        // We will use these buttons later for update and delete operation
        Button buttonDelete = view.findViewById(R.id.buttonDeleteMod);
        Button buttonEdit = view.findViewById(R.id.buttonEditMod);

        // Adding a clickListener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateModule(module);
            }
        });

        // The delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Are you sure you want to delete ("+module.getModuleName()+")?");
                builder.setPositiveButton("Yes", (dialogInterface, i) -> {
                    String sql = "DELETE FROM module WHERE module_id = ?";
                    mDatabase.execSQL(sql, new Integer[]{module.getModuleId()});
                    reloadModulesFromDatabase();
                });

                builder.setNegativeButton("Cancel", (dialogInterface, i) -> {
                    // Do nothing
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }

    private void updateModule(final Module module) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_module, null);
        builder.setView(view);

        final EditText moduleName = view.findViewById(R.id.editModuleName);
        final EditText moduleCode = view.findViewById(R.id.editModuleCode);
        final EditText department = view.findViewById(R.id.editDeptId);
        final EditText lecturer = view.findViewById(R.id.editLecturerId);

        moduleName.setText(module.getModuleName());
        moduleCode.setText(module.getModuleCode());
        department.setText(module.getDepartmentId());
        lecturer.setText(module.getLecturerId());

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = moduleName.getText().toString().trim();
                String code = moduleCode.getText().toString().trim();
                String dept = department.getText().toString().trim();
                String lect = lecturer.getText().toString().trim();

                if (name.isEmpty()) {
                    moduleName.setError("Name can't be blank");
                    moduleName.requestFocus();
                    return;
                }

                if (code.isEmpty()) {
                    moduleCode.setError("Module code is required");
                    moduleCode.requestFocus();
                    return;
                }

                if (dept.isEmpty()) {
                    department.setError("Make sure you add department");
                    department.requestFocus();
                    return;
                }

                if (lect.isEmpty()) {
                    lecturer.setError("Please add lecturer");
                    lecturer.requestFocus();
                    return;
                }

                String sql = "UPDATE module SET " +
                        "module_name = ?," +
                        "module_code = ?," +
                        "dept_id = ?," +
                        "lecturer_id = ?" +
                        " WHERE module_id = ?";

                String MOD_ID = String.valueOf(module.getModuleId());
                mDatabase.execSQL(sql, new String[]{name, code, dept, lect, MOD_ID});

                Toast.makeText(mCtx, "Module's updated", Toast.LENGTH_SHORT).show();
                reloadModulesFromDatabase();
                dialog.dismiss();
            }
        });
    }

    private void reloadModulesFromDatabase() {
        String sql = "SELECT * FROM module";
        Cursor cursorModules = mDatabase.rawQuery(sql, null);
        if (cursorModules.moveToFirst()) {
            moduleList.clear();
            do {
                moduleList.add(new Module(
                        cursorModules.getInt(0),
                        cursorModules.getString(1),
                        cursorModules.getString(2),
                        cursorModules.getString(3),
                        cursorModules.getString(4)
                ));
            } while (cursorModules.moveToNext());
        }
        cursorModules.close();
        notifyDataSetChanged();
    }




}
