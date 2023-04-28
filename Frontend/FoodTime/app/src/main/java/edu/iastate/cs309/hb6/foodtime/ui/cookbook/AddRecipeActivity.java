package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

public class AddRecipeActivity extends AppCompatActivity {

    private final String TAG = AddRecipeActivity.class.getSimpleName();
    private final String tag_recipe_req = "recipe_req";
    private final HashMap<String, Object> ingredientHash1 = new HashMap<>(6);
    private final HashMap<String, Object> ingredientHash2 = new HashMap<>(6);
    private final HashMap<String, Object> ingredientHash3 = new HashMap<>(6);
    private final HashMap<String, Object> ingredientHash4 = new HashMap<>(6);
    private final HashMap<String, Object> ingredientHash5 = new HashMap<>(6);
    private final ArrayList<HashMap<String, Object>> ingredientsHashList = new ArrayList<>(6);
    private final HashMap<String, Object> meal = new HashMap<>(3);
    private EditText recipeTitle;
    private EditText ingredient1;
    private EditText ingredient2;
    private EditText ingredient3;
    private EditText ingredient4;
    private EditText ingredient5;
    private EditText qt1;
    private EditText qt2;
    private EditText qt3;
    private EditText qt4;
    private EditText qt5;
    private EditText type1;
    private EditText type2;
    private EditText type3;
    private EditText type4;
    private EditText type5;
    private EditText dayInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent recipeIntent = getIntent();
        String userID = recipeIntent.getStringExtra("userID");

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

            /*Make JSON Obj*/
            meal.put("ingredients", ingredientsHashList);
            meal.put("name", recipeTitle);
            JSONObject mealObj = new JSONObject(meal);

            Log.d(TAG, "JSON Body: " + mealObj);

            if (!ingredientsHashList.isEmpty()) {
                JsonObjectRequest addRecipeRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_RECIPES_ADDRECIPE + "?UID=" + userID, mealObj,
                    response -> {
                        Toast.makeText(view.getContext(), "Meal added", Toast.LENGTH_LONG).show();
                        Intent addRecipeIntent = new Intent(AddRecipeActivity.this, AddDirectionActivity.class);
                        addRecipeIntent.putExtra("userID", userID);
                        addRecipeIntent.putExtra("RecipeTitle", recipeTitle);
                        startActivity(addRecipeIntent);
                    }, error -> {
                        Toast.makeText(view.getContext(), "An Error Occurred.", Toast.LENGTH_LONG).show();
                        Intent addRecipeIntent = new Intent(AddRecipeActivity.this, AddDirectionActivity.class);
                        addRecipeIntent.putExtra("userID", userID);
                        addRecipeIntent.putExtra("RecipeTitle", recipeTitle);
                        startActivity(addRecipeIntent);
                });

                AppController.getInstance().addToRequestQueue(addRecipeRequest, tag_recipe_req);
            } else {
                Toast.makeText(view.getContext(), "Add ingredients, ya derp.", Toast.LENGTH_LONG).show();
            }
        });


    }
}