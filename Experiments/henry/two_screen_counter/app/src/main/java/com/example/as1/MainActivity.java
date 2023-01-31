package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        String counter = intent.getStringExtra("counter");

        // Did we return from CounterActivity?
        if (counter != null) {
            Toast.makeText(this, "Counter value is: " + counter, Toast.LENGTH_SHORT).show();
        }

        button = findViewById(R.id.toCounterBtn);

        button.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, CounterActivity.class);
            startActivity(intent1);
        });
    }
}