package com.donnekt.attendanceapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.donnekt.attendanceapp.staff.Staff;

public class SQLiteHelper extends SQLiteOpenHelper {

    public static final int DB_VERSION = 1;
    public static String DB_NAME = "AttendanceDb";

    public static final String TABLE_NAME = "StaffMember";

    // Creating all columns
    public static final String COL_STAFF_ID = "staffId";
    public static final String COL_STAFF_FIRSTNAME = "staffFirstname";
    public static final String COL_STAFF_LASTNAME = "staffLastname";
    public static final String COL_STAFF_EMAIL = "staffEmail";
    public static final String COL_STAFF_PHONE = "staffPhone";
    public static final String COL_STAFF_ROLE = "staffRole";
    public static final String COL_STAFF_PASSWORD = "staffPassword";

    // Creating Database
    public SQLiteHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Writing command for sqlite to create table with required columns
        String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "
                + TABLE_NAME + " ("
                + COL_STAFF_ID + " INTEGER PRIMARY KEY, "
                + COL_STAFF_FIRSTNAME + " VARCHAR, "
                + COL_STAFF_LASTNAME + " VARCHAR, "
                + COL_STAFF_EMAIL + " VARCHAR, "
                + COL_STAFF_PHONE + " VARCHAR, "
                + COL_STAFF_ROLE + " VARCHAR, "
                + COL_STAFF_PASSWORD + " VARCHAR)";

        database.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop table if exists
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // Create the table again
        onCreate(db);
    }

    // Add new User by calling this method
    public void addNewStaff(Staff staff) {

        // Getting db instance for writing the staff
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        // cv.put(COL_STAFF_ID,staff.getStaffId());
        cv.put(COL_STAFF_FIRSTNAME, staff.getFirstname());
        cv.put(COL_STAFF_LASTNAME, staff.getLastname());
        cv.put(COL_STAFF_EMAIL, staff.getEmail());
        cv.put(COL_STAFF_PHONE, staff.getPhone());
        cv.put(COL_STAFF_ROLE, staff.getRole());
        cv.put(COL_STAFF_PASSWORD, staff.getPassword());

        // Inserting row
        db.insert(TABLE_NAME, null, cv);
        // Close the db to avoid any leak
        db.close();
    }


    public int checkStaffExistence(Staff staff) {
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT staffEmail FROM StaffMember WHERE staffEmail=? ", new String[]{staff.getEmail()});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
            cursor.close();
        }
        return id;
    }




    // LOGIN ACTIVITY
    public int checkValidPassword(Staff staff) {
        int id = -1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM StaffMember WHERE staffEmail=? AND staffPassword=?", new String[]{staff.getEmail(), staff.getPassword()});

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            id = cursor.getInt(0);
            cursor.close();
        }
        return id;
    }


}