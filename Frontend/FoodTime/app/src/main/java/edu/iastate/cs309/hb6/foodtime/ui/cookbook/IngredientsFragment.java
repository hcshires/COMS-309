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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.utils.Const;


public class IngredientsFragment extends Fragment {

    private static final String TAG = IngredientsFragment.class.getSimpleName();

    private TextView recipeTitle;
    private RecyclerView recyclerView;
    private String UID;

    private View root;
    private IngredientsAdapter adapter;

    private ArrayList<String> ingredients = new ArrayList<>();
    private ArrayList<String> quantity = new ArrayList<>();
    private ArrayList<String> type = new ArrayList<>();


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
        UID = usrData.getString("UID");
        recipeTitle.setText(usrData.getString("RecipeTitle"));

        /* Recycler View Adapter and Manager */
        adapter = new IngredientsAdapter(root.getContext(), ingredients);

        getIngredients(UID, recipeTitle.getText().toString(), root);


        recyclerView.setAdapter(adapter);


        return root;
    }

    private void getIngredients(String UID, String mealName, View view) {


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
}