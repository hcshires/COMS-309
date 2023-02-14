package edu.iastate.cs309.hb6.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    Button loginBtn;
    EditText email, pwd;

    /**
     * Create the LoginActivity and manage its widgets
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);

        loginBtn.setOnClickListener(view -> {
            if (!email.getText().toString().equals("") && !pwd.getText().toString().equals("")) {
                if (true) {
                    Intent intent2 = new Intent(LoginActivity.this, DashboardActivity.class);
                    startActivity(intent2);
                } else {
                    Toast.makeText(this, "Email and/or password is incorrect. Please try again.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Email and/or password field is blank.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     *
     * @return the UID of an existing user that matches the entered credentials
     */
    private String loginUser() {
        return "";
    }

    /**
     *
     * @return a newly created UID from the backend that matches the entered credentials of this new user
     */
    private String createUser() {
        return "";
    }
}