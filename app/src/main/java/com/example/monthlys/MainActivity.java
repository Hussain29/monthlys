package com.example.monthlys;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonWeekendFund = findViewById(R.id.buttonWeekendFund);
        Button buttonPersonal = findViewById(R.id.buttonPersonal);

        buttonWeekendFund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WFMasterActivity.class);
                startActivity(intent);
            }
        });

        buttonPersonal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PMasterActivity.class);
                startActivity(intent);
            }
        });
    }
}
