package edu.iastate.cs309.hb6.foodtime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

/**
 * The LoginActivity class allows the user to login with an existing FoodTime account or create a new one.
 * The user can then use their account to navigate the FoodTime app with, allowing a place to store
 * their recipes, meals, and pantry
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * The tag for logging
     */
    private final String TAG = LoginActivity.class.getSimpleName();
    /**
     * The tag for the login request
     */
    private final String tag_login_req = "login_req";
    /**
     * The EditText fields for the user's email and password
     */
    private EditText email, pwd, parentUsername;
    /**
     * RadioButtons for user's account permission level
     */
    private RadioGroup userLevels;
    /**
     * The Intent to start the DashboardActivity
     */
    private Intent loginIntent;

    /**
     * Create the LoginActivity and manage its widgets
     *
     * @param savedInstanceState - the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = findViewById(R.id.loginBtn);
        Button registerBtn = findViewById(R.id.registerBtn);

        // Get fields
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);
        userLevels = findViewById(R.id.userLevelOptions);
        parentUsername = findViewById(R.id.parentUsername);

        loginIntent = new Intent(LoginActivity.this, DashboardActivity.class);

        /* Disable parent username field if user is not a child */
        userLevels.setOnCheckedChangeListener((radioGroup, i) -> {
            RadioButton selectedLevel = findViewById(i);
            // TODO Add to Const.java when 56 is merged
            parentUsername.setEnabled(selectedLevel.getText().toString().equals("Child"));
        });

        /* API Request for Login */
        loginBtn.setOnClickListener(view -> {
            if (email.getText().toString().isEmpty() || pwd.getText().toString().isEmpty()) {
                Toast.makeText(this, "Email and/or password field is blank.", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                Toast.makeText(this, "Email address is incorrectly formatted. Please enter a valid email.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    loginUser();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        /* API Request for Registration */
        registerBtn.setOnClickListener(view -> {
            if (email.getText().toString().isEmpty() || pwd.getText().toString().isEmpty() || userLevels.getCheckedRadioButtonId() == -1 || parentUsername.getText().toString().isEmpty()) {
                Toast.makeText(this, "One or more fields blank", Toast.LENGTH_SHORT).show();
            } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText()).matches()) {
                Toast.makeText(this, "Email address is incorrectly formatted. Please enter a valid email.", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    // Use the contents of the fields to create a user instead of logging in TODO: change to new activity
                    createUser();
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    /**
     * Login a FoodTime user with the inputted credentials from the TextFields
     * If a user ID exists in the database that matches the credentials, pass
     * in a UID from the response and log in the user otherwise, return an error.
     *
     * @throws JSONException if the response body is not a valid JSON object
     */
    private void loginUser() throws JSONException {
        StringRequest loginRequest = new StringRequest(
                Request.Method.GET, Const.URL_LOGIN_USER + "?username=" + email.getText().toString() + "&password=" + pwd.getText().toString(), response -> {
            Log.d(TAG, response);
            loginIntent.putExtra("UID", response);
            startActivity(loginIntent);
        }, error -> {
            if (error.networkResponse.statusCode == 404) {
                Toast.makeText(this, "Email and/or password is incorrect. Please try again.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "An unexpected error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });

        AppController.getInstance().addToRequestQueue(loginRequest, tag_login_req);
    }

    /**
     * Create a FoodTime user with the inputted credentials from the TextFields
     *
     * @throws JSONException if the response body is not a valid JSON object
     */
    private void createUser() throws JSONException {
        // JSON Body
        Map<String, String> params = new HashMap<>();

        // User, pass
        params.put("username", email.getText().toString());
        params.put("password", pwd.getText().toString());

        // User access level select
        int selectedLevelId = userLevels.getCheckedRadioButtonId();
        RadioButton selectedLevel = findViewById(selectedLevelId);
        params.put("accessLevel", selectedLevel.getText().toString().toUpperCase());

        JSONObject reqBody = new JSONObject(params);
        JsonObjectRequest createUserRequest = new JsonObjectRequest(Request.Method.POST,
                Const.URL_CREATE_USER + "?parentUsername=" + parentUsername.getText().toString(), reqBody,
                response -> {
                    Log.d(TAG, "New user created: " + response.toString());
                    try {
                        // Get the UID from the response body
                        loginIntent.putExtra("UID", response.get("uid").toString());
                        startActivity(loginIntent);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> {
            if (error.networkResponse.statusCode == 409) {
                Toast.makeText(this, "There is already a user with that email. Please use another email address.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "An unexpected error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
            VolleyLog.d(TAG, "Error: " + error.getMessage());
        });

        AppController.getInstance().addToRequestQueue(createUserRequest, tag_login_req);
    }
}