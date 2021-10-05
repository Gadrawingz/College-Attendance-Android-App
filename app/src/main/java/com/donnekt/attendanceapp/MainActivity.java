package com.donnekt.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.*;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.admin.AdminDashboard;
import com.donnekt.attendanceapp.module.ModuleViewAll;
import com.donnekt.attendanceapp.staff.StaffDashboard;
import com.donnekt.attendanceapp.staff.StaffHome;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    ProgressBar loginLoadingPB;
    SharedPrefHandler sharedPrefHandler;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init handler
        sharedPrefHandler = new SharedPrefHandler(this);

        /*if (SharedPrefManager.getInstance(this).isLogin()){
            finish();
            startActivity(new Intent(MainActivity.this,StaffDashboard.class));
            return;
        }*/

        loginLoadingPB = findViewById(R.id.loginLoadingPB);
        editTextEmail = findViewById(R.id.editEmail);
        editTextPassword = findViewById(R.id.editPassword);

        //calling the method userLogin() for login the user
        findViewById(R.id.buttonLogin).setOnClickListener(view -> staffLogin());
        findViewById(R.id.buttonTest).setOnClickListener(view -> startActivity(new Intent(this, AdminDashboard.class)));
    }

    private void staffLogin() {

        // Getting the values
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        // Validating inputs
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Please enter your email");
            editTextEmail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter valid email address!");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Please enter your password!");
            editTextPassword.requestFocus();
            return;
        }

        // If everything is fine
        loginLoadingPB.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.STAFF_LOGIN, response -> {
            requestQueue.getCache().clear();
            loginLoadingPB.setVisibility(View.GONE);

            try {
                JSONObject jsonObject = new JSONObject(response);
                if(!jsonObject.getBoolean("error")) {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    finish();
                    startActivity(new Intent(getApplicationContext(), StaffHome.class));
                } else {
                    Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(MainActivity.this, error.toString(), Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);
    }
}