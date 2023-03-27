package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
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

        /* Initialize Tests */
        recipes = Recipe.createRecipeList(5);

        /* Adapter */
        CardAdapter adapter = new CardAdapter(recipes);

        return root;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}