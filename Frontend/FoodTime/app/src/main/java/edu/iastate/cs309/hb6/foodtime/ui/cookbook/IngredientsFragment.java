package edu.iastate.cs309.hb6.foodtime.ui.cookbook;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;


public class IngredientsFragment extends Fragment {

    private static final String TAG = IngredientsFragment.class.getSimpleName();

    private TextView recipeTitle;
    private RecyclerView recyclerView;
    private String UID;

    private View root;
    private IngredientsAdapter adapter;

    private ArrayList<Object> ingredients = new ArrayList<>();
    private ArrayList<Object> quantity = new ArrayList<>();
    private ArrayList<Object> type = new ArrayList<>();
    private ArrayList<HashMap<Object, Object>> jsons = new ArrayList<>();
    private HashMap<Object, Object> hash1  = new HashMap<>();
    private HashMap<Object, Object> hash2  = new HashMap<>();
    private HashMap<Object, Object> hash3  = new HashMap<>();
    private HashMap<Object, Object> hash4  = new HashMap<>();
    private HashMap<Object, Object> hash5  = new HashMap<>();




//    private HashMap<String, String> hashMap = new HashMap<>();


    public IngredientsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_ingredients, container, false);

        // Inflate the layout for this fragment
        Log.d(TAG, "Ingredients Fragment was created");



        /* Widgets */
        recipeTitle = root.findViewById(R.id.recipeTitle);
        recyclerView = root.findViewById(R.id.rvIngredients);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        /*Set Recipe Title*/
        Intent intent = requireActivity().getIntent();
        Bundle usrData = intent.getExtras();
//        UID = usrData.getString("UID");
        UID = "28002a17-e105-4ae3-9e07-765fd1b2ae3a";
        recipeTitle.setText(usrData.getString("RecipeTitle"));

        /* Recycler View Adapter and Manager */
        adapter = new IngredientsAdapter(root.getContext(), ingredients, quantity, type);
        getIngredients(UID, recipeTitle.getText().toString(), root);

        return root;
    }

    private void getIngredients(String UID, String mealName, View view) {
        JsonArrayRequest getIngredientsReq = new JsonArrayRequest(Request.Method.GET, Const.URL_RECIPES_COMPARE + "?UID=" + UID + "&mealName=" + mealName, null, response -> {
            formatResponse(response);
            recyclerView.setAdapter(adapter);
            Log.d(TAG, "Request Sent");
        }, error -> {
            if (error.networkResponse.statusCode == 418) {
                try {
                    String body = new String(error.networkResponse.data,"UTF-8");
                    JSONArray response = new JSONArray(body);
                    formatResponse(response);
                    recyclerView.setAdapter(adapter);
                } catch (UnsupportedEncodingException | JSONException e) {
                    throw new RuntimeException(e);
                }
            }
            Log.d(TAG, "Request NOT Sent");
        });
        AppController.getInstance().addToRequestQueue(getIngredientsReq, "tag_directoins");
    }

    /**
     * @param view               The View returned by {@link #onCreateView(LayoutInflater, ViewGroup, Bundle)}.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    public void formatResponse(JSONArray response){

        jsons.addAll(new Gson().fromJson(String.valueOf(response), ArrayList.class));

        hash1.putAll(jsons.get(0));
        Object ingredient1 = hash1.get("name");
        Object quantity1 = hash1.get("quantity");
        Object type1 = hash1.get("quantityType");
        ingredients.add(ingredient1);
        quantity.add(quantity1);
        type.add(type1);

        hash2.putAll(jsons.get(1));
        Object ingredient2 = hash2.get("name");
        Object quantity2 = hash2.get("quantity");
        Object type2 = hash2.get("quantityType");
        ingredients.add(ingredient2);
        quantity.add(quantity2);
        type.add(type2);

        hash3.putAll(jsons.get(2));
        Object ingredient3 = hash3.get("name");
        Object quantity3 = hash3.get("quantity");
        Object type3 = hash3.get("quantityType");
        ingredients.add(ingredient3);
        quantity.add(quantity3);
        type.add(type3);

        hash4.putAll(jsons.get(3));
        Object ingredient4 = hash4.get("name");
        Object quantity4 = hash4.get("quantity");
        Object type4 = hash4.get("quantityType");
        ingredients.add(ingredient4);
        quantity.add(quantity4);
        type.add(type4);

        hash5.putAll(jsons.get(4));
        Object ingredient5 = hash5.get("name");
        Object quantity5 = hash5.get("quantity");
        Object type5 = hash5.get("quantityType");
        ingredients.add(ingredient5);
        quantity.add(quantity5);
        type.add(type5);

        Log.d(TAG, ingredients.toString());
        Log.d(TAG, quantity.toString());
        Log.d(TAG, type.toString());
    }
}