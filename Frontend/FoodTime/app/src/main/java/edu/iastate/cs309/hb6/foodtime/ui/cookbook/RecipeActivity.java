package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.ui.pantry.PantryFragment;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

public class RecipeActivity extends AppCompatActivity {

    private EditText recipeTitle;
    private EditText ingredient1;
    private EditText ingredient2;
    private EditText ingredient3;
    private EditText ingredient4;
    private EditText ingredient5;
    private EditText ingredient6;
    private EditText dayInput;
    private Button submitRecipe;

    private final String TAG = RecipeActivity.class.getSimpleName();
    private final String tag_recipe_req = "recipe_req";
    private HashMap<String,String> ingredientHash1 = new HashMap<>(6);
    private HashMap<String,String> ingredientHash2 = new HashMap<>(6);
    private HashMap<String,String> ingredientHash3 = new HashMap<>(6);
    private HashMap<String,String> ingredientHash4 = new HashMap<>(6);
    private HashMap<String,String> ingredientHash5 = new HashMap<>(6);
    private HashMap<String,String> ingredientHash6 = new HashMap<>(6);
    private ArrayList<HashMap<String,String>> ingredientsHashList = new ArrayList<>(6);
    private HashMap<String, ArrayList<HashMap<String,String>>> meal = new HashMap<>(5);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent recipeIntent = getIntent();
        String userID = recipeIntent.getStringExtra("userID");
        Log.d(TAG, userID);

        /*Widgets*/
        recipeTitle = (EditText) findViewById(R.id.recipeTitleInput);
        ingredient1 = (EditText) findViewById(R.id.ingredientInput1);
        ingredient2 = (EditText) findViewById(R.id.ingredientInput2);
        ingredient3 = (EditText) findViewById(R.id.ingredientInput3);
        ingredient4 = (EditText) findViewById(R.id.ingredientInput4);
        ingredient5 = (EditText) findViewById(R.id.ingredientInput5);
        ingredient6 = (EditText) findViewById(R.id.ingredientInput6);
        submitRecipe = (Button) findViewById(R.id.submitRecipe);
        dayInput = (EditText) findViewById(R.id.dayInput);

        /**/
        submitRecipe.setOnClickListener(view -> {
            /*Create Ingredients Hash Maps*/
            ingredientHash1.put("name", ingredient1.getText().toString());
            ingredientsHashList.add(ingredientHash1);
            ingredientHash2.put("name", ingredient2.getText().toString());
            ingredientsHashList.add(ingredientHash2);
            ingredientHash3.put("name", ingredient3.getText().toString());
            ingredientsHashList.add(ingredientHash3);
            ingredientHash4.put("name", ingredient4.getText().toString());
            ingredientsHashList.add(ingredientHash4);
            ingredientHash5.put("name", ingredient5.getText().toString());
            ingredientsHashList.add(ingredientHash5);
            ingredientHash6.put("name", ingredient6.getText().toString());
            ingredientsHashList.add(ingredientHash6);

            meal.put("ingredients", ingredientsHashList);
            JSONObject jsonobj = new JSONObject(meal);

            /*Turn recipe title to string*/
            String recipeTitl = recipeTitle.getText().toString();
            String dayIn = dayInput.getText().toString();

            if(!ingredientsHashList.isEmpty()) {
                Recipe recipe = new Recipe(recipeTitl, ingredientsHashList);
                JsonObjectRequest addRecipeRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_MEALS_ADDMEAL + "?UID" + userID + "&day" + dayIn, jsonobj,
                        response ->{
                            Toast.makeText(view.getContext(), "Meal added", Toast.LENGTH_LONG).show();
                            ingredient1.setText("");

                        }, error -> {
                            Toast.makeText(view.getContext(), "An Error Occurred.", Toast.LENGTH_LONG).show();
                });
                AppController.getInstance().addToRequestQueue(addRecipeRequest, tag_recipe_req);
                Toast.makeText(view.getContext(), "Meal added", Toast.LENGTH_LONG).show();
                Log.d(TAG, jsonobj.toString());
            }
        });






    }
}