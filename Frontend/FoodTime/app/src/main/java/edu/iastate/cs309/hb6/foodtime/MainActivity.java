/**
 * FoodTime Android App
 *
 * @author: Henry Shires
 * @author: Will Maahs
 * @version: 1.0
 * @date: 2023-05-03
 */

package edu.iastate.cs309.hb6.foodtime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

/**
 * The main activity of the app. This is the activity that is launched when the app is opened.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * This method is called when the activity is created. It sets the content view to the main activity layout.
     * @param savedInstanceState The saved instance state.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.toLoginBtn);

        // Launch Login Activity when the button is clicked
        button.setOnClickListener(v -> {
            Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent1);
        });
    }
}