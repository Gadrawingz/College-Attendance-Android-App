package com.donnekt.attendanceapp.classroom;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.department.Department;
import com.donnekt.attendanceapp.department.DepartmentActivity;
import com.donnekt.attendanceapp.department.DepartmentAdapter;

import java.util.ArrayList;
import java.util.List;

public class ClassroomViewAll extends AppCompatActivity {

    List<Classroom> classroomList;
    SQLiteDatabase mDatabase;
    ListView listViewClassrooms;
    ClassroomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classroom_view_all);

        listViewClassrooms = (ListView) findViewById(R.id.listViewClassrooms);
        classroomList = new ArrayList<>();

        // Opening the database
        mDatabase = openOrCreateDatabase(DepartmentActivity.DATABASE_NAME, MODE_PRIVATE, null);

        // Method for displaying departments in the list
        showAllClassrooms();
    }

    private void showAllClassrooms() {

        // We used rawQuery(sql, selectionArgs) for fetching all the departments
        Cursor cursorClassrooms = mDatabase.rawQuery("SELECT * FROM classrooms", null);

        // If the cursor has some data
        if (cursorClassrooms.moveToFirst()) {
            // Loop through all the records
            do {
                // Pushing each record in the employee list
                classroomList.add(new Classroom(
                        cursorClassrooms.getInt(0),
                        cursorClassrooms.getString(1),
                        cursorClassrooms.getString(2),
                        cursorClassrooms.getString(3)
                ));
            } while (cursorClassrooms.moveToNext());
        }

        // Closing the cursor
        cursorClassrooms.close();

        // Creating the adapter object
        adapter = new ClassroomAdapter(this, R.layout.list_layout_class, classroomList, mDatabase);

        // Adding the adapter to listview
        listViewClassrooms.setAdapter(adapter);
    }
}