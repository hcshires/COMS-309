package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import edu.iastate.cs309.hb6.foodtime.R;

public class ViewRecipeActivity extends AppCompatActivity {

    private TabLayout ingDricTab;
    private ViewPager2 viewRecipeVP;
    private PagerAdapter pagerAdapter;

    private String recipeTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        /* for testing*/
        recipeTitle = "Lasagna";
        //eventually we will grab recipeTitle through putsextra

        /* Widgets */
        ingDricTab = findViewById(R.id.ingDirTabs);
        viewRecipeVP = findViewById(R.id.viewRecipeViewPager);


        /* Set the Adapter*/
        pagerAdapter = new PagerAdapter(ViewRecipeActivity.this);
        pagerAdapter.addFragment(new IngredientsFragment(), recipeTitle);
        pagerAdapter.addFragment(new DirectionsFragment(), recipeTitle);
        viewRecipeVP.setAdapter(pagerAdapter);

        /*Create the tabs*/
        ingDricTab.addTab(ingDricTab.newTab().setText("Ingredients"));
        ingDricTab.addTab(ingDricTab.newTab().setText("Directions"));

    }
}