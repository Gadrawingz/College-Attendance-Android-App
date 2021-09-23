package com.donnekt.attendanceapp.module;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.R;
import java.util.ArrayList;
import java.util.List;

public class ModuleViewAll extends AppCompatActivity {

    List<Module> moduleList;
    SQLiteDatabase mDatabase;
    ListView listViewModules;
    ModuleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module_view_all);

        listViewModules = (ListView) findViewById(R.id.listViewModules);
        moduleList = new ArrayList<>();

        // Opening the database
        mDatabase = openOrCreateDatabase(ModuleActivity.DATABASE_NAME, MODE_PRIVATE, null);

        // Method for displaying in the list
        showModulesFromDatabase();
    }


    private void showModulesFromDatabase() {

        try {
            // We used rawQuery(sql, selectionArgs) for fetching all the departments
            Cursor cursorModules = mDatabase.rawQuery("SELECT * FROM module", null);

            // If the cursor has some data
            if (cursorModules.moveToFirst()) {
                // Loop through all the records
                do {
                    // Pushing each record in the module list
                    moduleList.add(new Module(
                            cursorModules.getInt(0),
                            cursorModules.getString(1),
                            cursorModules.getString(2),
                            cursorModules.getString(3),
                            cursorModules.getString(4)
                    ));
                } while (cursorModules.moveToNext());
            }
            // Closing the cursor
            cursorModules.close();

            // Creating the adapter object
            adapter = new ModuleAdapter(this, R.layout.list_layout_module, moduleList, mDatabase);

            // Adding the adapter to listview
            listViewModules.setAdapter(adapter);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}