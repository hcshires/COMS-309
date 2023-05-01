package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import java.util.ArrayList;
import java.util.List;

import edu.iastate.cs309.hb6.foodtime.DashboardActivity;
import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

public class AddDirectionActivity extends AppCompatActivity {
    private EditText recipeTitle;
    private RecyclerView recyclerView;
    private AddDirectionsAdapter adapter;
    private int numberOfCVs = 0;
    private Button btnAdd;
    private String UID;
    private CardView cardView1;
    private final String TAG = AddDirectionActivity.class.getSimpleName();
    private final String tag_directions_req = "directions_req";
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
//        Log.d(TAG, usrData.getString("RecipeTitle"));

        /*Attach adapter to RV*/
        adapter = new AddDirectionsAdapter(AddDirectionActivity.this);
        recyclerView.setAdapter(adapter);


        /*OnClick Listener*/
        Intent addDirectionsIntent = new Intent(AddDirectionActivity.this, DashboardActivity.class);
        btnAdd.setOnClickListener(view -> {
            addDirectionsIntent.putExtra("UID", UID);

            List<Direction> directions = adapter.getDirections();

            for (int i = 0; i < directions.size(); i++) {
                View view1 = recyclerView.getChildAt(i);
                EditText edtDirc = (EditText) view1.findViewById(R.id.edtDirection);
                String strDirection = edtDirc.getText().toString();
                Log.d(TAG, strDirection);

                Direction dirDirection = new Direction();
                dirDirection.setDirection(strDirection);
                directions.add(dirDirection);
            }

            Log.d(TAG, directions.toString());



//            addIngredientRequest(UID, recipeTitle.getText().toString(), view);
            startActivity(addDirectionsIntent);

        });

    }

//    private void addIngredientRequest(String UID, String mealName, View view) {
//        JsonObjectRequest addDirectionReq = new JsonObjectRequest(Request.Method.PUT, Const.URL_DIRECTIONS_SETDIRECTIONS + "?UID=" + UID + "&mealName=" + mealName, dircObj, response -> {
//
//        }, error -> {
//
//        });
//
//        AppController.getInstance().addToRequestQueue(addDirectionReq, tag_directions_req);
//
//    }
}