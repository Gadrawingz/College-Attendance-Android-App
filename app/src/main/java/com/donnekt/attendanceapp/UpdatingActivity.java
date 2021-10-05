package com.donnekt.attendanceapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.department.Department;

import java.util.List;

public class UpdatingActivity extends AppCompatActivity {

    LinearLayout layoutUpdateDept;
    LinearLayout layoutUpdateClass;

    List<Department> departmentList;
    ListView listViewDepartments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updating);

        layoutUpdateDept = findViewById(R.id.layoutUpdateDept);
        layoutUpdateClass = findViewById(R.id.layoutUpdateClass);
        layoutUpdateClass.setVisibility(View.GONE);

        EditText deptName = findViewById(R.id.editDeptName);
        EditText deptCaption = findViewById(R.id.editDeptCaption);
        Button realEditButton = findViewById(R.id.buttonUpdateDept);

        // Department department = departmentList.get(position);


    }



    private void updateDepartment() {
        /*deptName.setText(department.getDepartmentName());
        deptCaption.setText(department.getDepartmentCaption());*/
    }
}