package edu.iastate.cs309.hb6.foodtime;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.Objects;

import edu.iastate.cs309.hb6.foodtime.databinding.ActivityDashboardBinding;

/**
 * The DashboardActivity class allows the user to navigate the FoodTime app's other pages and features.
 */
public class DashboardActivity extends AppCompatActivity {

    /** The binding for the DashboardActivity */
    private ActivityDashboardBinding binding;

    /**
     * Create the DashboardActivity and manage its widgets
     * @param savedInstanceState - the saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Passing each menu ID as a set of Ids because each menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_pantry, R.id.navigation_cookbook)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);

        // Show the action bar
        Objects.requireNonNull(getSupportActionBar()).show();
    }
}