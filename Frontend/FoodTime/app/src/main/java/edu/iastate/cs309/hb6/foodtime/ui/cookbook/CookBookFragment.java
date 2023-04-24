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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.databinding.FragmentCookbookBinding;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

/**
 * Dashboard Fragment: CookBook
 * View the user's recipes (collection of all possible meals to make) and add new ones
 */
public class CookBookFragment extends Fragment {

    /** The binding for this fragment */
    private FragmentCookbookBinding binding;

    /** View to hold recipe cards */
    private RecyclerView recipeCards;

    /** Data set adapter for recipe cards */
    private CardAdapter adapter;

    /** List of recipes */
    private final ArrayList<String> recipes = new ArrayList<>();

    /** Tag for logging */
    private final String TAG = CookBookFragment.class.getSimpleName();

    /** Tag for requests */
    private final String tag_cookbook_req = "cookbook_req";

    /**
     * OnCreateView
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return View
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CookBookViewModel cookBookViewModel =
                new ViewModelProvider(this).get(CookBookViewModel.class);

        binding = FragmentCookbookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /* Store user ID for requests */
        Bundle userData = requireActivity().getIntent().getExtras();
        String UID = userData.getString("UID").replaceAll("\"", "");

        /* Widgets */
        FloatingActionButton addRecipe = root.findViewById(R.id.addRecipeBtn);
        recipeCards = root.findViewById(R.id.recyclerList);

        // Set layout manager to position items
        recipeCards.setLayoutManager(new LinearLayoutManager(root.getContext()));

        // Adapter
        adapter = new CardAdapter(root.getContext(), recipes);

        /* Initialize Recipes */
        getUserRecipes(UID);
        Log.d(TAG, "Recipes List: " + recipes);

        /* Go to AddRecipe when button clicked */
        addRecipe.setOnClickListener(view -> {
            Intent cookbookIntent = new Intent(root.getContext(), AddRecipeActivity.class);
            cookbookIntent.putExtra("UID", UID);
            startActivity(cookbookIntent);
        });

        return root;
    }

    /**
     * Return an ArrayList of recipes for the user from the database
     * @param UID - the given user ID for the user
     */
    private void getUserRecipes(String UID) {
        JsonArrayRequest getUserRecipes = new JsonArrayRequest(Request.Method.GET, Const.URL_RECIPES_GETLABELS + "?UID=" + UID, null, response -> {
            try {
                for (int i = 0; i < response.length(); i++) {
                    String item = response.getString(i);
                    recipes.add(item); // Add to ArrayList
                }

                /* Attach adapter to recycler view */
                recipeCards.setAdapter(adapter);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            // Error
        });

        AppController.getInstance().addToRequestQueue(getUserRecipes, tag_cookbook_req);
    }

    /**
     * onDestroyView
     * Handle when the view is no longer active
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
