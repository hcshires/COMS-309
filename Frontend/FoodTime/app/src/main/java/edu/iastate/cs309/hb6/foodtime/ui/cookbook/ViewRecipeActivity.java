package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import edu.iastate.cs309.hb6.foodtime.R;

public class ViewRecipeActivity extends AppCompatActivity {

    private TableLayout ingDircTab;
    private ViewPager viewRicipeVP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        /*Widgets*/
        //recipeTitle = (TextView) findViewById(R.id.recipeTitle);
        //ingredientsLV = (ListView) findViewById(R.id.ingredientsListViewer);
        ingDircTab = (TableLayout) findViewById(R.id.viewRecipeTabLayout);
        viewRicipeVP = (ViewPager) findViewById(R.id.viewRecipeViewPager);
        //ingDircTab;
        //recipeTitle.setText(set text from put extras);

    }
}