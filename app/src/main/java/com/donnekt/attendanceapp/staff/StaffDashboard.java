package com.donnekt.attendanceapp.staff;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.donnekt.attendanceapp.MainActivity;
import com.donnekt.attendanceapp.R;
import com.donnekt.attendanceapp.SharedPrefHandler;

public class StaffDashboard extends AppCompatActivity {

    private SharedPrefHandler sharedPrefHandler;
    TextView firstname, lastname, email;
    Button btnLogout;

    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_dashboard);

        sharedPrefHandler = new SharedPrefHandler(this);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(StaffDashboard.this,2));
        adapter = new Adapter(this);
        recyclerView.setAdapter(adapter);
    }
}