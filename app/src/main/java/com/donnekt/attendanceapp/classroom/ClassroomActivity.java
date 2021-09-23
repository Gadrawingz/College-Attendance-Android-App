package com.donnekt.attendanceapp.classroom;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.admin.AdminDashboard;
import com.donnekt.attendanceapp.department.DepartmentViewAll;
import com.donnekt.attendanceapp.classroom.ClassroomViewAll;

public class ClassroomActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String DATABASE_NAME = "AttendanceTest";

    TextView viewClassrooms, exitClassrooms;
    EditText className;
    Spinner classLevel, classDept;
    Button saveButton;
    SQLiteDatabase mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom);

        viewClassrooms = (TextView) findViewById(R.id.tvViewClassrooms);
        exitClassrooms = (TextView) findViewById(R.id.tvExitClassrooms);

        className = (EditText) findViewById(R.id.editClassName);
        classLevel= (Spinner) findViewById(R.id.spinnerClassLevel);
        classDept = (Spinner) findViewById(R.id.spinnerClassDept);

        saveButton = (Button) findViewById(R.id.buttonAddClass);

        // Save & View events killInQ
        saveButton.setOnClickListener(this);
        viewClassrooms.setOnClickListener(this);
        exitClassrooms.setOnClickListener(this);

        //creating a database
        mDatabase = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);

        createDepartmentTable();

    }

    private void createDepartmentTable(){
        mDatabase.execSQL("CREATE TABLE IF NOT EXISTS classrooms (classroomId INTEGER PRIMARY KEY AUTOINCREMENT, classroomName VARCHAR(100) NOT NULL, classroomLevel VARCHAR(50) NOT NULL, department VARCHAR(200) NOT NULL)");
    }

    // This method will validate the name
    private boolean inputsAreCorrect(String name) {
        if (name.isEmpty()) {
            className.setError("Please enter class name");
            className.requestFocus();
            return false;
        }
        return true;
    }

    // In this method we will do the create operation
    private void addClassroom() {
        String name = className.getText().toString().trim();
        String level= classLevel.getSelectedItem().toString().trim();
        String dept = classDept.getSelectedItem().toString().trim();

        // Validating the inputs
        if (inputsAreCorrect(name)) {
            String insertSQL = "INSERT INTO classrooms(classroomName, classroomLevel, department) VALUES (?, ?, ?)";

            // the execSQL method is for inserting values with 2 params
            mDatabase.execSQL(insertSQL, new String[]{name, level, dept});
            Toast.makeText(this, "Classroom's saved!", Toast.LENGTH_SHORT).show();
            emptyEditTextAfterDataInsert();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            // case to add Dept
            case R.id.buttonAddClass:
                addClassroom();
                break;

            // case: click to view all
            case R.id.tvViewClassrooms:
                startActivity(new Intent(this, ClassroomViewAll.class));
                break;

            // case: click to quit departments
            case R.id.tvExitClassrooms:
                startActivity(new Intent(this, AdminDashboard.class));
                break;
        }
    }

    // Empty Shits after All shits
    private void emptyEditTextAfterDataInsert() {
        className.getText().clear();
    }
}