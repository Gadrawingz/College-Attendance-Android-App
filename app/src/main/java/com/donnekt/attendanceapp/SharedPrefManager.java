package com.donnekt.attendanceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.donnekt.attendanceapp.staff.Staff;

public class SharedPrefManager {

    private static final String SHARED_PREF_NAME = "staff_prefs";

    private static final String KEY_STAFF_ID = "key_staff_id";
    private static final String KEY_FIRSTNAME = "key_firstname";
    private static final String KEY_LASTNAME = "key_lastname";
    private static final String KEY_EMAIL = "key_email";
    private static final String KEY_GENDER = "key_gender";
    private static final String KEY_STAFF_ROLE = "key_staff_role";

    private static SharedPrefManager mInstance;
    private static Context ctx;

    private SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    // This method will store the user data in shared preferences
    public void staffLogin(Staff staff) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_STAFF_ID, staff.getStaffId());
        editor.putString(KEY_FIRSTNAME, staff.getFirstname());
        editor.putString(KEY_LASTNAME, staff.getLastname());
        editor.putString(KEY_GENDER, staff.getTelephone());
        editor.putString(KEY_STAFF_ROLE, staff.getEmail());
        editor.apply();
    }

    //this method will checker whether user is already logged in or not
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL, null) != null;
    }

    // This method will give the logged in user
    public Staff getLoggedInStaff() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new Staff(
                sharedPreferences.getInt(KEY_STAFF_ID, -1),
                sharedPreferences.getString(KEY_FIRSTNAME, null),
                sharedPreferences.getString(KEY_LASTNAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_GENDER, null),
                sharedPreferences.getString(KEY_STAFF_ROLE, null)
        );
    }

    //this method will logout the user
    public void logoutStaff() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, MainActivity.class));
    }
}
