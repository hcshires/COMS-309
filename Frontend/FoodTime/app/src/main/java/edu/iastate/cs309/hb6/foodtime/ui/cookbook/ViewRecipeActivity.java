package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import edu.iastate.cs309.hb6.foodtime.R;

public class ViewRecipeActivity extends AppCompatActivity {

    private TabLayout ingDricTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        /* Widgets */
        ingDricTab = findViewById(R.id.ingDirTabs);
        ViewPager viewRicipeVP = findViewById(R.id.viewRecipeViewPager);
//        viewRicipeVP.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        /*Create the tabs*/
        ingDricTab.addTab(ingDricTab.newTab().setText("Ingredients"));
        ingDricTab.addTab(ingDricTab.newTab().setText("Directions"));
    }
}