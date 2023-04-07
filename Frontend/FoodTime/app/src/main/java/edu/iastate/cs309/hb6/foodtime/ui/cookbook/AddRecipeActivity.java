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

import edu.iastate.cs309.hb6.foodtime.DashboardActivity;
import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

public class AddRecipeActivity extends AppCompatActivity {

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
    private Button submitRecipe;

    private final String TAG = AddRecipeActivity.class.getSimpleName();
    private final String tag_recipe_req = "recipe_req";
    private HashMap<String,Object> ingredientHash1 = new HashMap<>(6);
    private HashMap<String,Object> ingredientHash2 = new HashMap<>(6);
    private HashMap<String,Object> ingredientHash3 = new HashMap<>(6);
    private HashMap<String,Object> ingredientHash4 = new HashMap<>(6);
    private HashMap<String,Object> ingredientHash5 = new HashMap<>(6);
    private ArrayList<HashMap<String,Object>> ingredientsHashList = new ArrayList<>(6);

    private HashMap<String, Object> meal = new HashMap<>(3);

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
        qt1 = (EditText) findViewById(R.id.qtInput1);
        qt2 = (EditText) findViewById(R.id.qtInput2);
        qt3 = (EditText) findViewById(R.id.qtInput3);
        qt4 = (EditText) findViewById(R.id.qtInput4);
        qt5 = (EditText) findViewById(R.id.qtInput5);
        type1 = (EditText) findViewById(R.id.typeInput1);
        type2 = (EditText) findViewById(R.id.typeInput2);
        type3 = (EditText) findViewById(R.id.typeInput3);
        type4 = (EditText) findViewById(R.id.typeInput4);
        type5 = (EditText) findViewById(R.id.typeInput5);
        submitRecipe = (Button) findViewById(R.id.submitRecipe);
        dayInput = (EditText) findViewById(R.id.dayInput);

        /**/
        submitRecipe.setOnClickListener(view -> {
            ingredientHash1.clear();
            ingredientHash2.clear();
            ingredientHash3.clear();
            ingredientHash4.clear();
            ingredientHash5.clear();
            ingredientsHashList.clear();
            /*Create Ingredients Hash Maps*/
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
//            ingredientHash5.put("name", ingredient5.getText().toString());
//            ingredientHash5.put("quantity", Integer.parseInt(qt5.getText().toString()));
//            ingredientHash5.put("quantityType", type5.getText().toString());
//            ingredientsHashList.add(ingredientHash5);

            /*Turn recipe title and day to string*/
            String recipeTitl = recipeTitle.getText().toString();
            String dayIn = dayInput.getText().toString();

            /*Make JSon Obj*/
            meal.put("ingredients", ingredientsHashList);
            meal.put("name", recipeTitl);
            JSONObject jsonobj = new JSONObject(meal);


            if(!ingredientsHashList.isEmpty()) {
                Recipe recipe = new Recipe(recipeTitl, ingredientsHashList);
                JsonObjectRequest addRecipeRequest = new JsonObjectRequest(Request.Method.PUT, Const.URL_RECIPES_ADDRECIPE + "?UID=" + userID, jsonobj,
                        response ->{
                            Toast.makeText(view.getContext(), "Meal added", Toast.LENGTH_LONG).show();
//                            ingredient1.setText("");
                            Intent addRecipeIntent = new Intent(AddRecipeActivity.this, DashboardActivity.class);
                            addRecipeIntent.putExtra("userID", userID);
                            startActivity(addRecipeIntent);

                        }, error -> {
                            Toast.makeText(view.getContext(), "An Error Occurred.", Toast.LENGTH_LONG).show();
                });
                AppController.getInstance().addToRequestQueue(addRecipeRequest, tag_recipe_req);
                Toast.makeText(view.getContext(), "Meal added", Toast.LENGTH_LONG).show();
                Log.d(TAG, meal.toString());
                Log.d(TAG, jsonobj.toString());
            }
        });






    }
}