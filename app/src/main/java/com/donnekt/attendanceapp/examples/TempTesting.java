package com.donnekt.attendanceapp.examples;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import com.donnekt.attendanceapp.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TempTesting extends AppCompatActivity {


    Spinner spinner;
    String [] XDepartments = new String[] {"Leo messi", "Daniel"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_testing);

        /*spinner = findViewById(R.id.spinnerDP);
        final List<String> pList = new ArrayList<>(Arrays.asList(XDepartments));
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, pList);
        spinner.setAdapter(spinnerArrayAdapter);*/
    }


}