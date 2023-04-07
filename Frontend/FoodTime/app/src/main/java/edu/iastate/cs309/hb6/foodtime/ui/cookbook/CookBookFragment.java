package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.databinding.FragmentCookbookBinding;

public class CookBookFragment extends Fragment {

    private FragmentCookbookBinding binding;

    ArrayList<Recipe> recipes;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CookBookViewModel cookBookViewModel =
                new ViewModelProvider(this).get(CookBookViewModel.class);

        binding = FragmentCookbookBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /* Widgets */
        RecyclerView rvRecipes = (RecyclerView) root.findViewById(R.id.recyclerList);
        Button addRecipe = (Button) root.findViewById(R.id.addRecipeButt);

        /* Store user ID for requests */
        Bundle userData = requireActivity().getIntent().getExtras();
        String userID = userData.getString("userID").replaceAll("\"", "");

        /* Initialize Tests */
        recipes = Recipe.createRecipeList(5);
        /* Adapter */
        CardAdapter adapter = new CardAdapter(recipes);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}