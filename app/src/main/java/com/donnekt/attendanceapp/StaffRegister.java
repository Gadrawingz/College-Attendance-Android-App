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
import com.donnekt.attendanceapp.staff.Staff;

public class StaffRegister extends AppCompatActivity {

    EditText staffFirstname, staffLastname, staffEmail, staffPhone, staffRole, staffPassword;
    Button insertButton;
    String FNameHolder, LNameHolder, EmailHolder, PhoneHolder, RoleHolder, PasswordHolder;
    Boolean EditTextEmptyHolder;
    SQLiteDatabase sqLiteDatabaseObj;
    String SQLiteDataBaseQueryHolder;
    SQLiteHelper sqLiteHelper;
    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_register);

        // Getting data from layout
        staffFirstname = findViewById(R.id.editStaffFname);
        staffLastname = findViewById(R.id.editStaffLname);
        staffEmail = findViewById(R.id.editStaffEmail);
        staffPhone = findViewById(R.id.editStaffPhone);
        staffRole = findViewById(R.id.spinnerStaffRole);
        staffPassword = findViewById(R.id.passStaffPassword);
        insertButton = findViewById(R.id.buttonRegister);

        sqLiteHelper = new SQLiteHelper(this);

        // Adding click listener to register button.
        insertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String firstname = staffFirstname.getText().toString();
                String lastname = staffLastname.getText().toString();
                String email = staffEmail.getText().toString();
                String phone = staffPhone.getText().toString();
                String role = staffRole.getText().toString();
                String password = staffPassword.getText().toString();

                int check = checkStaffExistence(new Staff(email));
                if (check == -1) {
                    // Checking if EditTexts is empty or not.
                    checkEditTextEmptiness();
                    sqLiteHelper = new SQLiteHelper(StaffRegister.this);
                    sqLiteHelper.addNewStaff(new Staff(firstname, lastname, email, phone, role, password));
                    Toast.makeText(StaffRegister.this, "New Staff's registered!", Toast.LENGTH_SHORT).show();
                    emptyEditTextAfterDataInsert();
                } else {
                    Toast.makeText(StaffRegister.this, "This member with ("+email+") already exists!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Link creation to loginLink
        TextView loginLink = (TextView)findViewById(R.id.loginLink);
        loginLink.setMovementMethod(LinkMovementMethod.getInstance());
        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffRegister.this, StaffLogin.class);
                startActivity(intent);
            }
        });
    }

    // Checking if Staff exists by email
    public int checkStaffExistence(Staff staff) {
        return sqLiteHelper.checkStaffExistence(staff);
    }

    // Empty edittext after done inserting process method.
    public void emptyEditTextAfterDataInsert() {
        staffFirstname.getText().clear();
        staffLastname.getText().clear();
        staffEmail.getText().clear();
        staffPhone.getText().clear();
        staffRole.getText().clear();
        staffFirstname.getText().clear();
        staffPassword.getText().clear();
    }

    // Checking EditText is empty or not.
    public void checkEditTextEmptiness(){

        // Getting value from All EditText and storing into String Variables.
        FNameHolder = staffFirstname.getText().toString();
        LNameHolder = staffLastname.getText().toString();
        EmailHolder = staffEmail.getText().toString();
        PhoneHolder = staffPhone.getText().toString();
        RoleHolder = staffRole.getText().toString();
        PasswordHolder = staffPassword.getText().toString();

        // Checking EditText is empty or no using TextUtils.
        if( TextUtils.isEmpty(FNameHolder) || TextUtils.isEmpty(LNameHolder) ||
                TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(PhoneHolder) ||
                TextUtils.isEmpty(RoleHolder) || TextUtils.isEmpty(PasswordHolder)){
            EditTextEmptyHolder = false ;
        } else {
            EditTextEmptyHolder = true ;
        }
    }
}