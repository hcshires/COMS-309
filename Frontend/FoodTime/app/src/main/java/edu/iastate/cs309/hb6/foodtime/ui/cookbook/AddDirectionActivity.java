package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;

import edu.iastate.cs309.hb6.foodtime.R;

public class AddDirectionActivity extends AppCompatActivity {
    private EditText recipeTitle;
    private RecyclerView recyclerView;
    private DirectionsAdapter adapter;
    private final String TAG = AddDirectionActivity.class.getSimpleName();
    private final String tag_recipe_req = "recipe_req";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_direction);

        /*Widgets*/
        recipeTitle = findViewById(R.id.edtRecipeTitle);
        recyclerView = findViewById(R.id.rvDirections);

        // Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(AddDirectionActivity.this));

        /*Get Extras*/
        Intent addRecipeIntent = getIntent();
        Bundle usrData = addRecipeIntent.getExtras();
        recipeTitle.setText(usrData.getString("RecipeTitle"));
        Log.d(TAG, usrData.getString("RecipeTitle"));

        /*Attach adapter to RV*/
        adapter = new DirectionsAdapter(AddDirectionActivity.this);
        recyclerView.setAdapter(adapter);

    }
}