package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TextView;

import edu.iastate.cs309.hb6.foodtime.R;

public class ViewRecipeActivity extends AppCompatActivity {

    private TextView recipeTitle;
    private ListView ingredientsLV;
    private TableLayout ingDircTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        /*Widgets*/
        //recipeTitle = (TextView) findViewById(R.id.recipeTitle);
        //ingredientsLV = (ListView) findViewById(R.id.ingredientsListViewer);
        ingDircTab = (TableLayout) findViewById(R.id.ingDircTab);
        //recipeTitle.setText(set text from put extras);

    }
}