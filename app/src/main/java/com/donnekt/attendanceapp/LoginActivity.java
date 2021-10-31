package com.donnekt.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.staff.Staff;
import com.donnekt.attendanceapp.users.DashboardDas;
import com.donnekt.attendanceapp.users.DashboardDoq;
import com.donnekt.attendanceapp.users.DashboardHod;
import com.donnekt.attendanceapp.users.DashboardLecturer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    public TextView loginRole, exitLogin;
    EditText editTextEmail, editTextPassword;
    ProgressBar loginLoadingPB;
    SharedPrefManager sharedPrefHandler;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Stay on good shit bruh!
        if (SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();

            Staff staff = SharedPrefManager.getInstance(this).getLoggedInStaff();
            String activeStaffRole = staff.getRole();

            switch (activeStaffRole) {
                case "DAS":
                    startActivity(new Intent(this, DashboardDas.class));
                    break;

                case "HOD":
                    startActivity(new Intent(this, DashboardHod.class));
                    break;

                case "DOQ":
                    startActivity(new Intent(this, DashboardDoq.class));
                    break;

                case "LECTURER":
                    startActivity(new Intent(this, DashboardLecturer.class));
                    break;

                default:
                    Toast.makeText(getApplicationContext(), "WRONG PAGE", Toast.LENGTH_SHORT).show();
            }
        }

        loginRole = findViewById(R.id.loginRole);
        exitLogin = findViewById(R.id.exitLogin);

        // Receive shit and Push it!
        String receivedKey = getIntent().getStringExtra("sent_key");
        loginRole.setText("("+receivedKey+")");

        loginLoadingPB = findViewById(R.id.loginLoadingPB);
        editTextEmail = findViewById(R.id.editEmail);
        editTextPassword = findViewById(R.id.editPassword);

        //calling the method userLogin() for login the user
        findViewById(R.id.buttonLogin).setOnClickListener(view -> staffLogin());

        // Quit muh'fuckin shit
        exitLogin.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        });

    }

    private void staffLogin() {

        // Getting the values
        final String staffRole = loginRole.getText().toString();
        final String email = editTextEmail.getText().toString();
        final String password = editTextPassword.getText().toString();

        // Validating inputs
        if (TextUtils.isEmpty(staffRole)) {
            editTextEmail.setError("Staff member role must be selected!");
            editTextEmail.requestFocus();
            return;
        }

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
                if(jsonObject.optString("status").equals("ok")) {

                    // If there is no shit on the way!
                    JSONArray staffJson = jsonObject.getJSONArray("details");

                    for (int k=0; k<staffJson.length(); k++) {
                        JSONObject staffObject = staffJson.getJSONObject(k);

                        Staff staff = new Staff(
                                staffObject.getInt("staff_id"),
                                staffObject.getString("firstname"),
                                staffObject.getString("lastname"),
                                staffObject.getString("email"),
                                staffObject.getString("gender"),
                                staffObject.getString("staff_role")
                        );

                        // Store shit
                        SharedPrefManager.getInstance(getApplicationContext()).staffLogin(staff);

                        if(staffObject.getString("staff_role").equals("DAS")) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), DashboardDas.class));

                        } else if(staffObject.getString("staff_role").equals("HOD")) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), DashboardHod.class));

                        } else if(staffObject.getString("staff_role").equals("DOQ")) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), DashboardDoq.class));

                        } else if(staffObject.getString("staff_role").equals("LECTURER")) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), DashboardLecturer.class));

                        } else {
                            Toast.makeText(getApplicationContext(), "INVALID ROLE", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(LoginActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_LONG).show()) {
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