package com.donnekt.attendanceapp;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHandler {
    private SharedPreferences sharedPreferences;
    private Context context;

    public String staffId;
    public String firstname;
    public String lastname;
    public String email;
    public String gender;
    public String role;

    public SharedPrefHandler(Context context) {
        this.sharedPreferences = context.getSharedPreferences("login_session", Context.MODE_PRIVATE);
        this.context = context;
    }

    public String getFirstname() {
        return sharedPreferences.getString(firstname, "");
    }

    public String getLastname() {
        return sharedPreferences.getString(lastname, "");
    }

    public String getEmail() {
        return sharedPreferences.getString(email, "");
    }

    public String getGender() {
        return sharedPreferences.getString(gender, "");
    }

    public String getRole() {
        return sharedPreferences.getString(role, "");
    }

    public void setFirstname(String firstname) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.firstname, firstname);
        edit.commit();
    }

    public void setLastname(String lastname) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.lastname, lastname);
        edit.commit();
    }

    public void setEmail(String email) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.email, email);
        edit.commit();
    }

    public void setGender(String gender) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.gender, gender);
        edit.commit();
    }

    public void setRole(String role) {
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putString(this.role, role);
        edit.commit();
    }
}
