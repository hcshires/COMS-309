package edu.iastate.cs309.hb6.foodtime;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

public class LoginActivity extends AppCompatActivity {

    private Button loginBtn, registerBtn;
    private EditText email, pwd;
    private Intent loginIntent;

    private final String TAG = LoginActivity.class.getSimpleName();
    private final String tag_login_req = "login_req";


    /**
     * Create the LoginActivity and manage its widgets
     *
     * @param savedInstanceState -
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = findViewById(R.id.loginBtn);
        registerBtn = findViewById(R.id.registerBtn);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.password);

        loginIntent = new Intent(LoginActivity.this, DashboardActivity.class);

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
            if (email.getText().toString().isEmpty() || pwd.getText().toString().isEmpty()) {
                Toast.makeText(this, "Email and/or password field is blank.", Toast.LENGTH_SHORT).show();
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
     */
    private void loginUser() throws JSONException {
        /* Get UID */
        Map<String, String> params = new HashMap<>();
        params.put("username", email.getText().toString());
        params.put("password", pwd.getText().toString());

        JSONObject reqBody = new JSONObject(params);
        Log.d(TAG, reqBody.toString());

        JsonObjectRequest loginRequest = new JsonObjectRequest
                (Request.Method.GET, Const.URL_LOGIN_USER, reqBody, response -> {
                    Log.d(TAG, response.toString());
                    loginIntent.putExtra("userID", response.toString());
                    startActivity(loginIntent);
                }, error -> {
                    Log.d(TAG, "Error: " + error.networkResponse.statusCode + " " + error.getMessage());
                    Toast.makeText(this, "Email and/or password is incorrect. Please try again.", Toast.LENGTH_SHORT).show();
                }) {
            /**
             * Passing some request headers
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();

                return headers;
            }

            @Override
            public String getBodyContentType() {
                return "application/json";
            }
        };

        AppController.getInstance().addToRequestQueue(loginRequest, tag_login_req);
    }

    /**
     * Create a new FoodTime user with the specified credentials in the TextFields
     */
    private void createUser() throws JSONException {
        /*
            {
            "username":"user",
            "password":"pass"
            }
         */
        Map<String, String> params = new HashMap<>();
        params.put("username", email.getText().toString());
        params.put("password", pwd.getText().toString());

        JSONObject reqBody = new JSONObject(params);

        /*
            {
            "username":"user",
            "password":"pass",
            "uid":"uid"
            }
         */
        JsonObjectRequest createUserRequest = new JsonObjectRequest(Request.Method.POST,
                Const.URL_CREATE_USER, reqBody,
                response -> {
                    Log.d(TAG, "Success: " + response.toString());
                    try {
                        // Get the UID from the response body
                        loginIntent.putExtra("userID", response.get("uid").toString());
                        startActivity(loginIntent);
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }
                }, error -> {
            VolleyLog.d(TAG, "Error: " + error.getMessage());
        });

        AppController.getInstance().addToRequestQueue(createUserRequest, tag_login_req);
    }
}