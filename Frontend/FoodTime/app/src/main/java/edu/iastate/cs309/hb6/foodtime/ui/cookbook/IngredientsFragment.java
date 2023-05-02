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
import java.util.HashMap;
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
        UID = "3b1bbf93-1f02-4492-aca8-57aa7be65138";
//        recipeTitle.setText(usrData.getString("RecipeTitle"));
        recipeTitle.setText("Lasgna");
        /* Recycler View Adapter and Manager */
        adapter = new IngredientsAdapter(root.getContext(), ingredients, quantity, type);
        getIngredients(UID, recipeTitle.getText().toString(), root);

        recyclerView.setAdapter(adapter);


        return root;
    }

    private void getIngredients(String UID, String mealName, View view) {
        JsonArrayRequest getIngredientsReq = new JsonArrayRequest(Request.Method.GET, Const.URL_RECIPES_COMPARE + "?UID=" + UID + "&mealName=" + mealName, null, response -> {
            doStuff(response);
            Log.d(TAG, "Request Sent");
        }, error -> {
            if (error.networkResponse.statusCode == 418) {
                try {
                    String body = new String(error.networkResponse.data,"UTF-8");
                    JSONArray response = new JSONArray(body);
                    doStuff(response);
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

    public void doStuff(JSONArray response){
//                    for(int i = 0; i < response.length(); i++) {
//                jsons.add(i, response.getJSONObject(i));
//            }
        jsons.addAll(new Gson().fromJson(String.valueOf(response), ArrayList.class));

//        HashMap<Object, Object> json1 = jsons.get(0);
//        Object ingredient1 = json1.get("name:");


//        ingredients.add(ingredient1);
//        quantity.add(Objects.requireNonNull(jsons.get(0).get("quantity")).toString());
//        type.add(Objects.requireNonNull(jsons.get(0).get("quantityType")).toString());

//        ingredients.add(jsons.get(1).get("name").toString());
//        quantity.add(jsons.get(1).get("quantity").toString());
//        type.add(jsons.get(1).get("quantityType").toString());
//
//        ingredients.add(jsons.get(2).get("name").toString());
//        quantity.add(jsons.get(2).get("quantity").toString());
//        type.add(jsons.get(2).get("quantityType").toString());

        Log.d(TAG, ingredients.toString());
        Log.d(TAG, quantity.toString());
        Log.d(TAG, type.toString());
    }
}