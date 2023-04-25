package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import edu.iastate.cs309.hb6.foodtime.R;

public class ViewRecipeActivity extends AppCompatActivity {

    private final String TAG = AddRecipeActivity.class.getSimpleName();
    private final String tag_viewrecipe_req = "recipe_req";
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

        /*Create the tabs*/
        ingDricTab.addTab(ingDricTab.newTab().setText("Ingredients"));
        ingDricTab.addTab(ingDricTab.newTab().setText("Directions"));

        /* Set the Adapter*/
        pagerAdapter = new PagerAdapter(getSupportFragmentManager(), getLifecycle());
        viewRecipeVP.setAdapter(pagerAdapter);

        /* Tab Layout Mediator */
        new TabLayoutMediator(ingDricTab, viewRecipeVP, (tab, position) -> {
            tab.setText("Ingredients");
        }).attach();


        //  switches pages if slid
        viewRecipeVP.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            /**
             * @param position Position index of the new selected page.
             */
            @Override
            public void onPageSelected(int position) {
                ingDricTab.selectTab(ingDricTab.getTabAt(position));
            }
        });


        //old code, might re implement
//        pagerAdapter.addFragment(new IngredientsFragment(), recipeTitle);
//        pagerAdapter.addFragment(new DirectionsFragment(), recipeTitle);

    }
}