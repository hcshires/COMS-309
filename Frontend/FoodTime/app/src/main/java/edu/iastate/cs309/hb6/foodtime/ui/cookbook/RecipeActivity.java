package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;

public class RecipeActivity extends AppCompatActivity {

    private EditText recipeTitle;
    private EditText ingredient1;
    private EditText ingredient2;
    private EditText ingredient3;
    private EditText ingredient4;
    private EditText ingredient5;
    private EditText ingredient6;
    private Button submitRecipe;

    private ArrayList<String> ingredients = new ArrayList<>(6);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        /*Widgets*/
        recipeTitle = (EditText) findViewById(R.id.recipeTitleInput);
        ingredient1 = (EditText) findViewById(R.id.ingredientInput1);
        ingredient2 = (EditText) findViewById(R.id.ingredientInput2);
        ingredient3 = (EditText) findViewById(R.id.ingredientInput3);
        ingredient4 = (EditText) findViewById(R.id.ingredientInput4);
        ingredient5 = (EditText) findViewById(R.id.ingredientInput5);
        ingredient6 = (EditText) findViewById(R.id.ingredientInput6);
        submitRecipe = (Button) findViewById(R.id.submitRecipe);


        submitRecipe.setOnClickListener(view -> {
            /*Create Ingredients List*/
            ingredients.add(ingredient1.getText().toString());
            ingredients.add(ingredient2.getText().toString());
            ingredients.add(ingredient3.getText().toString());
            ingredients.add(ingredient4.getText().toString());
            ingredients.add(ingredient5.getText().toString());
            ingredients.add(ingredient6.getText().toString());

            /*Turn recipe title to string*/
            String recipeTitl = recipeTitle.getText().toString();

            if(!ingredients.isEmpty()) {
                Recipe recipe = new Recipe(recipeTitl, ingredients);
            }
        });






    }
}