package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;

import edu.iastate.cs309.hb6.foodtime.R;

/**
 * ViewRecipeActivity
 * <p>
 * Displays the recipe information for a recipe that the user has selected.
 */
public class ViewRecipeActivity extends AppCompatActivity {

    /**
     * Create an instance of the ViewRecipeActivity
     * @param savedInstanceState The saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        /* Widgets */
//        TextView recipeTitle = findViewById(R.id.recipeTitle);
//        ListView ingredientsLV = findViewById(R.id.ingredientsList);
//
//        TabLayout ingDircTab = findViewById(R.id.viewRecipeTabLayout);
//        ViewPager viewRicipeVP = findViewById(R.id.viewRecipeViewPager);
//
//        recipeTitle.setText(recipe_title_from_intent);
    }
}