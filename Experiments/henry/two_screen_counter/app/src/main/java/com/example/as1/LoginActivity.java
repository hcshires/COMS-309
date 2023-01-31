package com.example.as1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText email, pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Intent intent = getIntent();
        String counter = intent.getStringExtra("counter");

        // Did we return from CounterActivity?
        if (counter != null) {
            Toast.makeText(this, "Counter value is: " + counter, Toast.LENGTH_SHORT).show();
        }

        loginBtn = findViewById(R.id.loginBtn);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);

        loginBtn.setOnClickListener(view -> {
            if (!email.getText().toString().equals("") && !pwd.getText().toString().equals("")) {
                Intent intent2 = new Intent(LoginActivity.this, CounterActivity.class);
                startActivity(intent2);
            } else {
                Toast.makeText(this, "Email and/or password field is blank.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}