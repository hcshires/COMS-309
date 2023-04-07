package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.databinding.FragmentCookbookBinding;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

public class CookBookFragment extends Fragment {

    private final String tag_cookbook_req = "cookbook_req";
    private FragmentCookbookBinding binding;
    private ArrayList<String> recipes;
    private RecyclerView rvRecipes;
    private Button addRecipe;
    private CardAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CookBookViewModel cookBookViewModel =
                new ViewModelProvider(this).get(CookBookViewModel.class);

        binding = FragmentCookbookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /* Widgets */
        rvRecipes = (RecyclerView) root.findViewById(R.id.recyclerList);
        addRecipe = (Button) root.findViewById(R.id.addRecipeButt);

        /* Store user ID for requests */
        Bundle userData = requireActivity().getIntent().getExtras();
        String userID = userData.getString("userID").replaceAll("\"", "");

        /* Initialize Tests */
        recipes = new ArrayList<>();
        Recipe.createRecipeList(getUserRecipes(userID));
        /* Adapter */
        adapter = new CardAdapter(root.getContext(), recipes);
        /* Attach adapter to recycler view */
        rvRecipes.setAdapter(adapter);
        /* Set layout manager to position items */
        rvRecipes.setLayoutManager(new LinearLayoutManager(root.getContext()));

        /*Go to AddRecipe when button clicked*/
        addRecipe.setOnClickListener(view -> {
            Intent cookbookIntent = new Intent(root.getContext(), AddRecipeActivity.class);
            cookbookIntent.putExtra("userID", userID);
            startActivity(cookbookIntent);
        });

        return root;
    }

    private ArrayList<String> getUserRecipes(String userID) {
        JsonArrayRequest getUserRecipes = new JsonArrayRequest(Request.Method.GET, Const.URL_RECIPES_GETLABELS + "?UID=" + userID, null, response -> {
            try {
                recipes.addAll(new ObjectMapper().readValue(String.valueOf(response), ArrayList.class));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            Log.d("TAG", recipes.toString());
        }, error -> {
        });
        AppController.getInstance().addToRequestQueue(getUserRecipes, tag_cookbook_req);
        return recipes;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
