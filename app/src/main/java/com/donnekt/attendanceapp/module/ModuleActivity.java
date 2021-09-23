package com.donnekt.attendanceapp.module;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.admin.AdminDashboard;
import com.donnekt.attendanceapp.classroom.ClassroomViewAll;


public class ModuleActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "AttendanceTest";

    EditText moduleName, moduleCode, deptId, lecturerId;
    Button addModuleBtn, doViewModules;
    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module);

        moduleName = (EditText) findViewById(R.id.editModuleName);
        moduleCode = (EditText) findViewById(R.id.editModuleCode);
        deptId     = (EditText) findViewById(R.id.editDeptId);
        lecturerId = (EditText) findViewById(R.id.editLecturerId);
        addModuleBtn = (Button) findViewById(R.id.buttonSave);
        doViewModules = (Button) findViewById(R.id.buttonViewModules);

        // Save & View button
        doViewModules.setOnClickListener(this);
        addModuleBtn.setOnClickListener(this);

        //creating a database
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Creating a table
        createModuleTable();
    }

    private void createModuleTable(){
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS module (" +
                "module_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "module_name VARCHAR(100) NOT NULL, " +
                "module_code VARCHAR(100) NOT NULL, " +
                "dept_id VARCHAR(100) NOT NULL, " +
                "lecturer_id VARCHAR(100) NOT NULL)");
    }

    // This method will validate the name and caption
    private boolean inputsAreCorrect(String name, String code, String dept, String lect) {
        if (name.isEmpty()) {
            moduleName.setError("Please enter module name");
            moduleName.requestFocus();
            return false;
        }

        if (code.isEmpty()) {
            moduleCode.setError("Please add module code");
            moduleCode.requestFocus();
            return false;
        }

        if (dept.isEmpty()) {
            deptId.setError("Make sure you add department");
            deptId.requestFocus();
            return false;
        }

        if (lect.isEmpty()) {
            lecturerId.setError("Please add lecturer");
            lecturerId.requestFocus();
            return false;
        }

        return true;
    }

    // In this method we will do the create operation
    private void addModule() {
        String mName = moduleName.getText().toString().trim();
        String mCode = moduleCode.getText().toString().trim();
        String dId   = (deptId.getText().toString().trim());
        String lId   = (lecturerId.getText().toString().trim());

        // Validating the inputs
        if (inputsAreCorrect(mName, mCode, dId, lId)) {
            String insertSQL = "INSERT INTO module(module_name, module_code, dept_id, lecturer_id) VALUES (?, ?, ?, ?)";

            // the execSQL method is for inserting values with 2 params
            mDatabase.execSQL(insertSQL, new String[]{mName, mCode, dId, lId});
            Toast.makeText(this, "Module's saved!", Toast.LENGTH_SHORT).show();
            emptyEditTextAfterInsertion();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add
            case R.id.buttonSave:
                addModule();
                break;

            // case: click to view all
            case R.id.buttonViewModules:
                startActivity(new Intent(this, ModuleViewAll.class));
                //Toast.makeText(getApplicationContext(), "OOPS!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    // Empty Shits after All shits
    private void emptyEditTextAfterInsertion() {
        moduleName.getText().clear();
        moduleCode.getText().clear();
        deptId.getText().clear();
        lecturerId.getText().clear();
    }
}