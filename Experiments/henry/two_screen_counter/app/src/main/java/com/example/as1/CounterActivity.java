package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CounterActivity extends AppCompatActivity {

    Button increaseBtn, decreaseBtn;
    Button backBtn;
    TextView numberTxt;

    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        increaseBtn = findViewById(R.id.increaseBtn);
        decreaseBtn = findViewById(R.id.decreaseBtn);
        backBtn = findViewById(R.id.backBtn);
        numberTxt = findViewById(R.id.number);

        increaseBtn.setOnClickListener(v -> numberTxt.setText(String.valueOf(++counter)));
        decreaseBtn.setOnClickListener(view -> numberTxt.setText(String.valueOf(--counter)));

        backBtn.setOnClickListener(v -> {
            Intent intent = new Intent(CounterActivity.this, LoginActivity.class);
            intent.putExtra("counter", String.valueOf(counter)); // Send Main the counter value to display in a toast

            startActivity(intent);
        });
    }
}