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
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONException;

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

public class DirectionsFragment extends Fragment {

//    public static final String TITLE_D = "Directions";
    private static final String TAG = DirectionsFragment.class.getSimpleName();
    private TextView recipeTitle;
    private View root;
    private RecyclerView recyclerView;
    private DirectionsAdaper adapter;
    private ArrayList<String> directions = new ArrayList<>();
    private  String UID;
    private final String tag_directions_req = "directions_req";


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
//        UID = usrData.getString("UID");
        UID = "3b1bbf93-1f02-4492-aca8-57aa7be65138";
        recipeTitle.setText(usrData.getString("RecipeTitle"));

        /* Recycler View Adapter and Manager */
        getDirections(UID, recipeTitle.getText().toString());
        adapter = new DirectionsAdaper(root.getContext(), directions);


        return root;
    }

    private void getDirections(String UID, String mealName) {
        JsonArrayRequest getDirectionsReq = new JsonArrayRequest(Request.Method.GET, Const.URL_DIRECTIONS_GETDIRECTIONS + "?UID=" + UID + "&mealName=" + mealName, null, response -> {
            try {
                for (int i = 0; i <= 9; i++) {
                    String direction = response.getString(i);
                    directions.add(i, direction);
                }
                Log.d(TAG, "Request Sent");
                recyclerView.setAdapter(adapter);


            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        },error -> {
            Log.d(TAG, "Request Not Sent");
        });
        AppController.getInstance().addToRequestQueue(getDirectionsReq, tag_directions_req);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }
}