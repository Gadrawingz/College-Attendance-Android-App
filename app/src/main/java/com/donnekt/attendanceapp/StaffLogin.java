package com.donnekt.attendanceapp;

import android.content.Intent;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.donnekt.attendanceapp.department.DepartmentActivity;
import com.donnekt.attendanceapp.staff.Staff;

public class StaffLogin extends AppCompatActivity {

    EditText Email, Password, Name;
    Button loginButton;
    String NameHolder, EmailHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;

    public static final String Username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        Email = findViewById(R.id.editEmail);
        Password = findViewById(R.id.editPassword);
        loginButton = findViewById(R.id.btnLogin);
        sqLiteHelper = new SQLiteHelper(this);

        // Adding click listener to register button.
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = Email.getText().toString();
                String password = Password.getText().toString();

                // Calling editText is empty or no method.
                checkEditTextStatus();

                int check = checkValidPassword(new Staff(email, password));
                if (EditTextEmptyHolder == true) {
                    Toast.makeText(StaffLogin.this,"Login Successful",Toast.LENGTH_LONG).show();

                    // Going to Dashboard activity after login success message.
                    Intent intent = new Intent(StaffLogin.this, StaffDashboard.class);
                    // Sending Username to Dashboard Activity using intent.
                    intent.putExtra(Username, email);
                    startActivity(intent);
                } else {
                    Toast.makeText(StaffLogin.this, "Please fill all shits", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Link creation to buttonRegister
        TextView buttonRegister = findViewById(R.id.btnRegister);
        buttonRegister.setMovementMethod(LinkMovementMethod.getInstance());
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffLogin.this, StaffRegister.class);
                startActivity(intent);
            }
        });

        // Link creation to buttonRegister
        /*TextView btnLogin2 = findViewById(R.id.btnLogin2);
        btnLogin2.setMovementMethod(LinkMovementMethod.getInstance());
        btnLogin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffLogin.this, SignupActivity.class);
                startActivity(intent);
            }
        });*/


        // Link creation to buttonRegister
        TextView btnGoDept = findViewById(R.id.btnGoDept);
        btnGoDept.setMovementMethod(LinkMovementMethod.getInstance());
        btnGoDept.setOnClickListener(v -> {
            Intent intent = new Intent(StaffLogin.this, DepartmentActivity.class);
            startActivity(intent);
        });


    }

    public int checkValidPassword(Staff staff) {
        return sqLiteHelper.checkValidPassword(staff);
    }

    public void checkEditTextStatus(){
        // Getting value from All EditText and storing into String Variables.
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        } else {
            EditTextEmptyHolder = true ;
        }
    }
}