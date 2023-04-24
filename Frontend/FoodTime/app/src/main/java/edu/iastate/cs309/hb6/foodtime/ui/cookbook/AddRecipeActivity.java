package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import edu.iastate.cs309.hb6.foodtime.DashboardActivity;
import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

/**
 * Add a recipe to the user's Cookbook and save to the database
 */
public class AddRecipeActivity extends AppCompatActivity {
    /** Hash map for each ingredient containing name, quantity, and quantity type */
    private final HashMap<String, Object> ingredientHash1 = new HashMap<>(6);

    /** Hash map for each ingredient containing name, quantity, and quantity type */
    private final HashMap<String, Object> ingredientHash2 = new HashMap<>(6);

    /** Hash map for each ingredient containing name, quantity, and quantity type */
    private final HashMap<String, Object> ingredientHash3 = new HashMap<>(6);

    /** Hash map for each ingredient containing name, quantity, and quantity type */
    private final HashMap<String, Object> ingredientHash4 = new HashMap<>(6);

    /** Hash map for each ingredient containing name, quantity, and quantity type */
    private final HashMap<String, Object> ingredientHash5 = new HashMap<>(6);

    /** List of ingredients */
    private final ArrayList<HashMap<String, Object>> ingredientsHashList = new ArrayList<>(6);

    /** Hash map for the recipe containing the ingredients and the recipe title */
    private final HashMap<String, Object> recipe = new HashMap<>(3);

    /** UI input for recipe title, ingredients, and directions */
    private EditText recipeTitle, ingredient1, ingredient2, ingredient3, ingredient4, ingredient5, qt1, qt2, qt3, qt4, qt5, type1, type2, type3, type4, type5, dayInput;

    /** Tag for logging */
    private final String TAG = AddRecipeActivity.class.getSimpleName();

    /** Tag for request */
    private final String tag_recipe_req = "recipe_req";

    /**
     * Create an AddRecipeActivity instance
     * @param savedInstanceState - Bundle
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent recipeIntent = getIntent();
        String UID = recipeIntent.getStringExtra("UID");

        /* Widgets */
        Button submitRecipe = findViewById(R.id.submitRecipe);

        recipeTitle = findViewById(R.id.recipeTitleInput);
        ingredient1 = findViewById(R.id.ingredientInput1);
        ingredient2 = findViewById(R.id.ingredientInput2);
        ingredient3 = findViewById(R.id.ingredientInput3);
        ingredient4 = findViewById(R.id.ingredientInput4);
        ingredient5 = findViewById(R.id.ingredientInput5);

        qt1 = findViewById(R.id.qtInput1);
        qt2 = findViewById(R.id.qtInput2);
        qt3 = findViewById(R.id.qtInput3);
        qt4 = findViewById(R.id.qtInput4);
        qt5 = findViewById(R.id.qtInput5);

        type1 = findViewById(R.id.typeInput1);
        type2 = findViewById(R.id.typeInput2);
        type3 = findViewById(R.id.typeInput3);
        type4 = findViewById(R.id.typeInput4);
        type5 = findViewById(R.id.typeInput5);

        dayInput = findViewById(R.id.dayInput);

        /* Add ingredients and directions to the recipe, submit to the database when clicked */
        submitRecipe.setOnClickListener(view -> {
            ingredientHash1.clear();
            ingredientHash2.clear();
            ingredientHash3.clear();
            ingredientHash4.clear();
            ingredientHash5.clear();
            ingredientsHashList.clear();

            /* Create Ingredients Hash Maps */
            ingredientHash1.put("name", ingredient1.getText().toString());
            ingredientHash1.put("quantity", Integer.parseInt(qt1.getText().toString()));
            ingredientHash1.put("quantityType", type1.getText().toString());
            ingredientsHashList.add(ingredientHash1);

            ingredientHash2.put("name", ingredient2.getText().toString());
            ingredientHash2.put("quantity", Integer.parseInt(qt2.getText().toString()));
            ingredientHash2.put("quantityType", type2.getText().toString());
            ingredientsHashList.add(ingredientHash2);

            ingredientHash3.put("name", ingredient3.getText().toString());
            ingredientHash3.put("quantity", Integer.parseInt(qt3.getText().toString()));
            ingredientHash3.put("quantityType", type3.getText().toString());
            ingredientsHashList.add(ingredientHash3);

            ingredientHash4.put("name", ingredient4.getText().toString());
            ingredientHash4.put("quantity", Integer.parseInt(qt4.getText().toString()));
            ingredientHash4.put("quantityType", type4.getText().toString());
            ingredientsHashList.add(ingredientHash4);

            ingredientHash5.put("name", ingredient5.getText().toString());
            ingredientHash5.put("quantity", Integer.parseInt(qt5.getText().toString()));
            ingredientHash5.put("quantityType", type5.getText().toString());
            ingredientsHashList.add(ingredientHash5);

            /* Turn recipe title and day to string */
            String recipeTitle = this.recipeTitle.getText().toString();
            String dayIn = dayInput.getText().toString();

            /* Make JSON Obj */
            recipe.put("ingredients", ingredientsHashList);
            recipe.put("name", recipeTitle);
            JSONObject mealObj = new JSONObject(recipe);

            Log.d(TAG, "JSON Body: " + mealObj);

            // Send request to database
            if (!ingredientsHashList.isEmpty()) {
                addRecipeRequest(UID, mealObj, view);
            } else {
                Toast.makeText(view.getContext(), "Please add ingredients first.", Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Send an HTTP request to database to add a recipe
     * @param UID - The user's ID
     * @param mealObj  - The meal to add as a recipe
     * @param view - Parent view
     */
    private void addRecipeRequest(String UID, JSONObject mealObj, View view) {
        JsonObjectRequest addRecipeReq = new JsonObjectRequest(Request.Method.PUT, Const.URL_RECIPES_ADDRECIPE + "?UID=" + UID, mealObj,
                response -> {
                    Toast.makeText(view.getContext(), "Meal added", Toast.LENGTH_LONG).show();
                    Intent addRecipeIntent = new Intent(AddRecipeActivity.this, DashboardActivity.class);
                    addRecipeIntent.putExtra("UID", UID);
                    startActivity(addRecipeIntent);
                }, error -> {
            Toast.makeText(view.getContext(), "An Error Occurred.", Toast.LENGTH_LONG).show();
        });

        AppController.getInstance().addToRequestQueue(addRecipeReq, tag_recipe_req);
    }

}