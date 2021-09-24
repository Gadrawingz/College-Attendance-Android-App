package com.donnekt.attendanceapp;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefManager {
    private static SharedPrefManager minst;
    private static Context mct;

    private static final String SHARD_PERFNAME="myshardperf624";
    private static final String KEY_ID="staff_id";
    private static final String KEY_FIRSTNAME="firstname";
    private static final String KEY_LASTNAME="lastname";
    private static final String KEY_EMAIL="email";
    private SharedPrefManager(Context context){
        mct=context;
    }
    public static synchronized SharedPrefManager getInstance(Context context){
        if (minst==null){
            minst=new SharedPrefManager(context);
        }
        return minst;
    }
    public boolean userLogin(String id,String firstname,String lastname,String email){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(KEY_ID,id);
        editor.putString(KEY_FIRSTNAME,firstname);
        editor.putString(KEY_LASTNAME,lastname);
        editor.putString(KEY_EMAIL,email);
        editor.apply();
        return true;
    }
    public boolean isLogin(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        if (sharedPreferences.getString(KEY_ID,null)!=null){
            return true;
        }
        return false;
    }
    public boolean logout(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.clear();
        editor.apply();
        return true;

    }
    public String getUserId(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_ID,null);

    }
    public String getFirstname(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_FIRSTNAME,null);
    }

    public String getLastname(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_LASTNAME,null);

    }

    public String getUserEmail(){
        SharedPreferences sharedPreferences=mct.getSharedPreferences(SHARD_PERFNAME,Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_EMAIL,null);

    }
}
