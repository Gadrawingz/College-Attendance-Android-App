package com.donnekt.attendanceapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class MainActivity extends AppCompatActivity {

    CardView hodCard, dasCard, lecturerCard, doqCard, dpatCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hodCard = findViewById(R.id.hodCard);
        dasCard = findViewById(R.id.dasCard);
        lecturerCard = findViewById(R.id.lecturerCard);
        doqCard = findViewById(R.id.doqCard);
        dpatCard = findViewById(R.id.dpatCard);


        dasCard.setOnClickListener(view -> {
            String sentWord = "DAS";
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("sent_key", sentWord);
            startActivity(intent);
        });

        hodCard.setOnClickListener(view -> {
            String sentWord = "HOD";
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("sent_key", sentWord);
            startActivity(intent);
        });

        lecturerCard.setOnClickListener(view -> {
            String sentWord = "LECTURER";
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("sent_key", sentWord);
            startActivity(intent);
        });

        doqCard.setOnClickListener(view -> {
            String sentWord = "DOQ";
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("sent_key", sentWord);
            startActivity(intent);
        });

        dpatCard.setOnClickListener(view -> {
            String sentWord = "DPAT";
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.putExtra("sent_key", sentWord);
            startActivity(intent);
        });
    }
}