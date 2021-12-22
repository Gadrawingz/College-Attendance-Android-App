package com.donnekt.attendanceapp.staff;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.donnekt.attendanceapp.LoginActivity;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.ProfileActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefManager;
import com.donnekt.attendanceapp.URLs;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    EditText newPassword, confirmPass;
    ProgressBar loadingProgBar;
    Button registerBtn, changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        loadingProgBar = findViewById(R.id.loadingProgress);

        newPassword = findViewById(R.id.newPassword);
        confirmPass = findViewById(R.id.confirmPassword);
        registerBtn = findViewById(R.id.registerButton);
        registerBtn.setOnClickListener(view -> changePassword());
    }

    private void changePassword() {
        final String NEW_PASS = newPassword.getText().toString().trim();
        final String CON_PASS = confirmPass.getText().toString().trim();

        if (TextUtils.isEmpty(NEW_PASS)) {
            newPassword.setError("Please enter new password!");
            newPassword.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(CON_PASS)) {
            confirmPass.setError("Please confirm password!");
            confirmPass.requestFocus();
            return;
        }

        if(!NEW_PASS.equals(CON_PASS)) {
            confirmPass.setError("Both password must be the same!");
            confirmPass.requestFocus();
            return;
        }

        transferDataUsingVolley(NEW_PASS);
    }

    private void transferDataUsingVolley(String NEW_PASS) {
        loadingProgBar.setVisibility(View.VISIBLE);

        Staff activeUser = SharedPrefManager.getInstance(this).getLoggedInStaff();
        String STAFF_ID = String.valueOf(activeUser.getStaffId());

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.CHANGE_PASS+STAFF_ID, response -> {
            try {
                loadingProgBar.setVisibility(View.GONE);
                JSONObject jsonObject = new JSONObject(response);
                Toast.makeText(ChangePassword.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                Staff staff = SharedPrefManager.getInstance(this).getLoggedInStaff();
                String activeStaffRole = staff.getRole();
                SharedPrefManager.getInstance(getApplicationContext()).logout();
                switch (activeStaffRole) {
                    case "DAS":
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        intent.putExtra("sent_key", "DAS");
                        startActivity(intent);
                        break;

                    case "HOD":
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent2.putExtra("sent_key", "HOD");
                        startActivity(intent2);
                        break;

                    case "DOQ":
                        Intent intent3 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent3.putExtra("sent_key", "DOQ");
                        startActivity(intent3);
                        break;

                    case "LECTURER":
                        Intent intent4 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent4.putExtra("sent_key", "LECTURER");
                        startActivity(intent4);
                        break;

                    case "DPAT":
                        Intent intent5 = new Intent(getApplicationContext(), LoginActivity.class);
                        intent5.putExtra("sent_key", "DPAT");
                        startActivity(intent5);
                        break;

                    default:
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(ChangePassword.this, "Network problem!", Toast.LENGTH_LONG).show()) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("password", NEW_PASS);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}