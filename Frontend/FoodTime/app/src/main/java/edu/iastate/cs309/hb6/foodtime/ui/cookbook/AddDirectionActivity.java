package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import edu.iastate.cs309.hb6.foodtime.DashboardActivity;
import edu.iastate.cs309.hb6.foodtime.R;

public class AddDirectionActivity extends AppCompatActivity {
    private EditText recipeTitle;
    private RecyclerView recyclerView;
    private AddDirectionsAdapter adapter;
    private Button btnAdd;
    private String UID;
    private final String TAG = AddDirectionActivity.class.getSimpleName();
    private final String tag_recipe_req = "recipe_req";
    private String test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_direction);


        /*Widgets*/
        recipeTitle = findViewById(R.id.edtRecipeTitle);
        recyclerView = findViewById(R.id.rvDirections);
        btnAdd = findViewById(R.id.btnAdd);

        // Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(AddDirectionActivity.this));

        /*Get Extras*/
        Intent addRecipeIntent = getIntent();
        Bundle usrData = addRecipeIntent.getExtras();
        recipeTitle.setText(usrData.getString("RecipeTitle"));
        UID = usrData.getString("UID");
        Log.d(TAG, usrData.getString("RecipeTitle"));

        /*Attach adapter to RV*/
        adapter = new AddDirectionsAdapter(AddDirectionActivity.this);
        recyclerView.setAdapter(adapter);

        /*OnClick Listener*/
        Intent addDirectionsIntent = new Intent(AddDirectionActivity.this, DashboardActivity.class);
        btnAdd.setOnClickListener(view -> {
            addDirectionsIntent.putExtra("UID", UID);
            startActivity(addDirectionsIntent);
        });

    }
}