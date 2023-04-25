package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.iastate.cs309.hb6.foodtime.R;

public class DirectionsFragment extends Fragment {

    public static final String TITLE_D = "Directions";
    public DirectionsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_directions, container, false);
    }

//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        ((TextView)view.findViewById(R.id.recipeTitle)).setText(getArguments().getString(TITLE_D));
//        ((CardView)view.findViewById(R.id.ingrCV))
//
//    }
}