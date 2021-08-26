package com.donnekt.attendanceapp;

import android.database.DataSetObserver;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;

public class StaffMembers extends AppCompatActivity {

    SQLiteHelper sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListAdapter listAdapter;
    ListView LISTVIEW;

    ArrayList<String> ID_Array;
    ArrayList<String> NAME_Array;
    ArrayList<String> PHONE_NUMBER_Array;

    ArrayList<String> ListViewClickItemArray = new ArrayList<String>();
    String TempHolder ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_members);

        LISTVIEW = (ListView) findViewById(R.id.staffMembersList);
        ID_Array = new ArrayList<String>();
        NAME_Array = new ArrayList<String>();
        PHONE_NUMBER_Array = new ArrayList<String>();
        sqLiteHelper = new SQLiteHelper(this);

        LISTVIEW.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub
                Intent intent = new Intent(getApplicationContext(),StaffMember.class);

                intent.putExtra("ListViewClickedItemValue", ListViewClickItemArray.get(position).toString());
                startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        ShowSQLiteDBdata() ;
        super.onResume();
    }

    private void ShowSQLiteDBdata() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();

        cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME+"", null);

        ID_Array.clear();
        NAME_Array.clear();
        PHONE_NUMBER_Array.clear();

        if (cursor.moveToFirst()) {
            do {

                ID_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_STAFF_ID)));

                //Inserting Column ID into Array to Use at ListView Click Listener Method.
                ListViewClickItemArray.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_STAFF_ID)));

                NAME_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_STAFF_FIRSTNAME)));
                PHONE_NUMBER_Array.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.COL_STAFF_PHONE)));

            } while (cursor.moveToNext());
        }

        listAdapter = new ListAdapter() {
            @Override
            public void registerDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

            }

            @Override
            public int getCount() {
                return 0;
            }

            @Override
            public Object getItem(int i) {
                return null;
            }

            @Override
            public long getItemId(int i) {
                return 0;
            }

            @Override
            public boolean hasStableIds() {
                return false;
            }

            @Override
            public View getView(int i, View view, ViewGroup viewGroup) {
                return null;
            }

            @Override
            public int getItemViewType(int i) {
                return 0;
            }

            @Override
            public int getViewTypeCount() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Nullable
            @Override
            public CharSequence[] getAutofillOptions() {
                return ListAdapter.super.getAutofillOptions();
            }

            @Override
            public boolean areAllItemsEnabled() {
                return false;
            }

            @Override
            public boolean isEnabled(int i) {
                return false;
            }
        };

        LISTVIEW.setAdapter(listAdapter);
        cursor.close();
    }
}