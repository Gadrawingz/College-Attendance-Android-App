package com.donnekt.attendanceapp.department;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.donnekt.attendanceapp.R;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

public class DepartmentViewAll extends AppCompatActivity {

    List<Department> departmentList;
    SQLiteDatabase mDatabase;
    ListView listViewDepartments;
    DepartmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_view_all);

        listViewDepartments = (ListView) findViewById(R.id.listViewDepartments);
        departmentList = new ArrayList<>();

        // Opening the database
        mDatabase = openOrCreateDatabase(DepartmentActivity.DATABASE_NAME, MODE_PRIVATE, null);

        // Method for displaying departments in the list
        showDepartmentsFromDatabase();
    }

    private void showDepartmentsFromDatabase() {

        // We used rawQuery(sql, selectionArgs) for fetching all the departments
        Cursor cursorDepartments = mDatabase.rawQuery("SELECT * FROM departments", null);

        // If the cursor has some data
        if (cursorDepartments.moveToFirst()) {
            // Loop through all the records
            do {
                // Pushing each record in the employee list
                departmentList.add(new Department(
                        cursorDepartments.getInt(0),
                        cursorDepartments.getString(1),
                        cursorDepartments.getString(2)
                ));
            } while (cursorDepartments.moveToNext());
        }
        // Closing the cursor
        cursorDepartments.close();

        // Creating the adapter object
        adapter = new DepartmentAdapter(this, R.layout.list_layout_department, departmentList, mDatabase);

        // Adding the adapter to listview
        listViewDepartments.setAdapter(adapter);
    }

}