package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private List<Direction> directionList = new ArrayList<>();
    private ArrayList<String> inputs = new ArrayList<>();
    private final String TAG = AddDirectionActivity.class.getSimpleName();
    private final String tag_directions_req = "directions_req";
    private JSONArray dircObj;
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

        /*Attach adapter to RV*/
        adapter = new AddDirectionsAdapter(AddDirectionActivity.this, directionList, (position, charSeq) -> inputs.add(position, charSeq));
        recyclerView.setAdapter(adapter);



        /*OnClick Listener*/
        Intent addDirectionsIntent = new Intent(AddDirectionActivity.this, DashboardActivity.class);
        btnAdd.setOnClickListener(view -> {
            addDirectionsIntent.putExtra("UID", UID);
            Log.d(TAG, inputs.toString());
            dircObj = new JSONArray(inputs);
            addIngredientRequest(UID, recipeTitle.getText().toString(), view);
            startActivity(addDirectionsIntent);

        });

    }

    private void addIngredientRequest(String UID, String mealName, View view) {
        JsonArrayRequest addDirectionReq = new JsonArrayRequest(Request.Method.PUT, Const.URL_DIRECTIONS_SETDIRECTIONS + "?UID=" + UID + "&mealName=" + mealName, dircObj, response -> {
                Log.d(TAG, "Request Sent");
                Toast.makeText(view.getContext(), "Meal added", Toast.LENGTH_LONG).show();
        }, error -> {
            Toast.makeText(view.getContext(), "An unexpected error occurred", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Request NOT Sent");
        });
        AppController.getInstance().addToRequestQueue(addDirectionReq, tag_directions_req);

    }
}