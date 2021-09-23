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
import com.donnekt.attendanceapp.module.ModuleActivity;
import com.donnekt.attendanceapp.staff.StaffDashboard;
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

        /*if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(this, StaffDashboard.class));
        }*/

        loginLoadingPB = findViewById(R.id.loginLoadingPB);
        editTextEmail = findViewById(R.id.editEmail);
        editTextPassword = findViewById(R.id.editPassword);


        //calling the method userLogin() for login the user
        findViewById(R.id.buttonLogin).setOnClickListener(view -> staffLogin());

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
        final ProgressBar progressBar = (ProgressBar) findViewById(R.id.loadingProgBar);
        loginLoadingPB.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.STAFF_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                requestQueue.getCache().clear();
                loginLoadingPB.setVisibility(View.INVISIBLE);


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if(jsonObject.optString("success").equals("1")) {
                        JSONObject jObj = jsonObject.getJSONObject("details");
                        sharedPrefHandler.setFirstname(jObj.getString("firstname"));
                        sharedPrefHandler.setLastname(jObj.getString("lastname"));
                        sharedPrefHandler.setEmail(jObj.getString("email"));
                        sharedPrefHandler.setGender(jObj.getString("gender"));
                        sharedPrefHandler.setRole(jObj.getString("staff_role"));

                        Toast.makeText(MainActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getBaseContext(), StaffDashboard.class));
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException error) {
                    error.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
        @Override
        protected Map<String, String> getParams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("email", email);
        params.put("password", password);
        return params;
    }
    };
        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}