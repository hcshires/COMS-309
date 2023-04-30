package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.iastate.cs309.hb6.foodtime.R;

public class DirectionsFragment extends Fragment {

//    public static final String TITLE_D = "Directions";
    private static final String TAG = DirectionsFragment.class.getSimpleName();
    private TextView recipeTitle;
    public DirectionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DirectionsFragment", "Directions Fragment was created");

        Intent intent = requireActivity().getIntent();
        Bundle usrData = intent.getExtras();
        recipeTitle.setText(usrData.getString("RecipeTitle"));
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_directions, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}