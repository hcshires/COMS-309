package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;

public class DirectionsFragment extends Fragment {

//    public static final String TITLE_D = "Directions";
    private static final String TAG = DirectionsFragment.class.getSimpleName();
    private TextView recipeTitle;
    private View root;
    private RecyclerView recyclerView;
    private DirectionsAdaper adapter;
    private ArrayList<String> directions = new ArrayList<>();

    public DirectionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("DirectionsFragment", "Directions Fragment was created");
        root = inflater.inflate(R.layout.fragment_directions, container, false);
        recipeTitle = root.findViewById(R.id.recipeTitle);
        recyclerView = root.findViewById(R.id.rvDirections);
        recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

        Intent intent = requireActivity().getIntent();
        Bundle usrData = intent.getExtras();
        recipeTitle.setText(usrData.getString("RecipeTitle"));

        /* Recycler View Adapter and Manager */
        adapter = new DirectionsAdaper(root.getContext(), directions);

        directions.add(0, "This is the first direction");
        directions.add(1, "This is the second direction");
        directions.add(2, "This is the third direction");
        directions.add(3, "This is the fourth direction");
        directions.add(4, "This is the fifth direction");
        directions.add(5, "This is the sixth direction");
        directions.add(6, "This is the seventh direction");


        recyclerView.setAdapter(adapter);


        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}