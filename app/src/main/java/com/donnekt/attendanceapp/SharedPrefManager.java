package com.donnekt.attendanceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import com.donnekt.attendanceapp.staff.Staff;

public class SharedPrefManager {
    private static SharedPrefManager sharedPrefManager;
    private static Context mct;

    private static final String SH_PREF_NAME="gPrefManager";
    private static final String KEY_ID="staff_id";
    private static final String KEY_REF_DEPT_ID="dept_id";
    private static final String KEY_REF_DEPT_NAME="dept_name";
    private static final String KEY_FIRSTNAME="firstname";
    private static final String KEY_LASTNAME="lastname";
    private static final String KEY_EMAIL="email";
    private static final String KEY_PHONE="telephone";
    private static final String KEY_GENDER="gender";
    private static final String KEY_ROLE="staff_role";
    private static final String KEY_STATUS="staff_status";
    private static final String KEY_PASSWORD="password";


    private SharedPrefManager(Context context){
        mct=context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (sharedPrefManager==null){
            sharedPrefManager=new SharedPrefManager(context);
        }
        return sharedPrefManager;
    }

    public void staffLogin(Staff staff) {
        SharedPreferences shPrefs = mct.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shPrefs.edit();

        editor.putInt(KEY_ID, staff.getStaffId());
        editor.putInt(KEY_REF_DEPT_ID, staff.getRefDeptId());
        editor.putString(KEY_REF_DEPT_NAME, staff.getRefDeptName());
        editor.putString(KEY_FIRSTNAME, staff.getFirstname());
        editor.putString(KEY_LASTNAME, staff.getLastname());
        editor.putString(KEY_EMAIL, staff.getEmail());
        editor.putString(KEY_PHONE, staff.getTelephone());
        editor.putString(KEY_GENDER, staff.getGender());
        editor.putString(KEY_ROLE, staff.getRole());
        editor.putString(KEY_STATUS, staff.getStatus());
        editor.putString(KEY_PASSWORD, staff.getPassword());
        editor.apply();
    }

    public boolean isLoggedIn(){
        SharedPreferences shPrefs = mct.getSharedPreferences(SH_PREF_NAME,Context.MODE_PRIVATE);
        return shPrefs.getString(KEY_EMAIL, null) != null;
    }

    public void logout(){
        SharedPreferences shPrefs = mct.getSharedPreferences(SH_PREF_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= shPrefs.edit();
        editor.clear();
        editor.apply();
        mct.startActivity(new Intent(mct, MainActivity.class));
    }

    public Staff getLoggedInStaff(){
        SharedPreferences shPrefs = mct.getSharedPreferences(SH_PREF_NAME, Context.MODE_PRIVATE);
        return new Staff(
                shPrefs.getInt(KEY_ID, -1),
                shPrefs.getInt(KEY_REF_DEPT_ID, -1),
                shPrefs.getString(KEY_REF_DEPT_NAME, null),
                shPrefs.getString(KEY_FIRSTNAME, null),
                shPrefs.getString(KEY_LASTNAME, null),
                shPrefs.getString(KEY_EMAIL, null),
                shPrefs.getString(KEY_PHONE, null),
                shPrefs.getString(KEY_GENDER, null),
                shPrefs.getString(KEY_ROLE, null),
                shPrefs.getString(KEY_STATUS, null)
        );
    }

}