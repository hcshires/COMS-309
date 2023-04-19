package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import edu.iastate.cs309.hb6.foodtime.R;

public class ViewRecipeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        /* Widgets */
        //recipeTitle = (TextView) findViewById(R.id.recipeTitle);
        //ingredientsLV = (ListView) findViewById(R.id.ingredientsListViewer);

        TabLayout ingDircTab = findViewById(R.id.viewRecipeTabLayout);
        ViewPager viewRicipeVP = findViewById(R.id.viewRecipeViewPager);

        //ingDircTab;
        //recipeTitle.setText(set text from put extras);
    }
}