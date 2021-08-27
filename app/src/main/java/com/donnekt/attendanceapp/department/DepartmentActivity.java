package com.donnekt.attendanceapp.department;

import android.text.method.LinkMovementMethod;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.TestActivity;
import com.donnekt.attendanceapp.admin.AdminDashboard;

public class DepartmentActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "AttendanceTest";

    TextView viewDepartments, exitDepartments;
    EditText deptName, deptCaption;
    Button addDeptButton;
    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);

        viewDepartments = (TextView) findViewById(R.id.tvViewDepartments);
        exitDepartments = (TextView) findViewById(R.id.tvExitDepartments);

        deptName = (EditText) findViewById(R.id.editDeptName);
        deptCaption = (EditText) findViewById(R.id.editDeptCaption);
        addDeptButton = (Button) findViewById(R.id.buttonAddDept);

        // Save & View Dept button
        viewDepartments.setOnClickListener(this);
        exitDepartments.setOnClickListener(this);
        addDeptButton.setOnClickListener(this);

        //creating a database
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        // Creating a table
        createDepartmentTable();


    }

    private void createDepartmentTable(){
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS departments (departmentId INTEGER PRIMARY KEY AUTOINCREMENT, departmentName VARCHAR(100) NOT NULL, departmentCaption VARCHAR(200) NOT NULL)");
    }

    // This method will validate the name and caption
    private boolean inputsAreCorrect(String name, String caption) {
        if (name.isEmpty()) {
            deptName.setError("Please enter a name");
            deptName.requestFocus();
            return false;
        }

        if (caption.isEmpty()) {
            deptCaption.setError("Please add description");
            deptCaption.requestFocus();
            return false;
        }

        return true;
    }

    // In this method we will do the create operation
    private void addDepartment() {

        String name = deptName.getText().toString().trim();
        String caption = deptCaption.getText().toString().trim();

        // Validating the inputs
        if (inputsAreCorrect(name, caption)) {
            String insertSQL = "INSERT INTO departments(departmentName, departmentCaption) VALUES (?, ?)";

            // the execSQL method is for inserting values with 2 params
            mDatabase.execSQL(insertSQL, new String[]{name, caption});
            Toast.makeText(this, "Department's saved!", Toast.LENGTH_SHORT).show();
            emptyEditTextAfterDataInsert();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add Dept
            case R.id.buttonAddDept:
                addDepartment();
                break;

            // case: click to view all dept
            case R.id.tvViewDepartments:
                startActivity(new Intent(this, DepartmentViewAll.class));
                break;

            // case: click to quit depts
            case R.id.tvExitDepartments:
                startActivity(new Intent(this, AdminDashboard.class));
                break;
        }
    }

    // Empty Shits after All shits
    private void emptyEditTextAfterDataInsert() {
        deptName.getText().clear();
        deptCaption.getText().clear();
    }
}