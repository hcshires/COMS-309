package edu.iastate.cs309.hb6.foodtime.ui.pantry;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Objects;

import edu.iastate.cs309.hb6.foodtime.DashboardActivity;
import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.databinding.FragmentPantryBinding;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

/**
 * Dashboard Fragment: Pantry
 * Used for keeping a list of known kitchen ingredients to use in the Cook Book
 */
public class PantryFragment extends Fragment {

    private static ArrayList<String> ingredientsList;
    private ArrayAdapter<String> pantryAdapter;
    private ListView pantry;
    private EditText input, quantityInput, unitInput;
    private FragmentPantryBinding binding;
    private final String TAG = PantryFragment.class.getSimpleName();
    private final String tag_pantry_req = "pantry_req";

    /**
     * Create the PantryFragment
     *
     * @param inflater           The LayoutInflater object that can be used to inflate
     *                           any views in the fragment,
     * @param container          If non-null, this is the parent view that the fragment's
     *                           UI should be attached to.  The fragment should not add the view itself,
     *                           but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     *                           from a previous saved state as given here.
     * @return PantryFragment
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* Attach this fragment to the DashboardActivity and access its parent view */
        PantryViewModel pantryViewModel = new ViewModelProvider(this).get(PantryViewModel.class);
        binding = FragmentPantryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /* Widgets */
        FloatingActionButton addButton = root.findViewById(R.id.addButton);
        pantry = (ListView) root.findViewById(R.id.pantryItems);
        input = root.findViewById(R.id.editTextAddPantry);
        quantityInput = root.findViewById(R.id.quantityTxt);
        unitInput = root.findViewById(R.id.unitTypesTxt);

        /* Store user ID for requests */
        Bundle userData = requireActivity().getIntent().getExtras();
        String UID = userData.getString("UID").replaceAll("\"", "");

        /* Store the pantry */
        ingredientsList = new ArrayList<>();
        pantryAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, ingredientsList);
        pantry.setAdapter(pantryAdapter);

        /* Pull the pantry version from the server to update the local array list */
        getUserPantryString(UID);

        /* Add an item typed in the text box */
        addButton.setOnClickListener(view -> {
            String ingredient = input.getText().toString();
            String unitType = unitInput.getText().toString();
            int quantity = Integer.parseInt(quantityInput.getText().toString());
            if (!ingredient.isEmpty()) {
                try {
                    addItem(view, UID, ingredient, quantity, unitType);
                    input.setText("");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(root.getContext().getApplicationContext(), "Please enter text", Toast.LENGTH_LONG).show();
            }
        });

        /* Listen for clicks on the pantry (i.e. delete items) */
        setUpListViewListener(UID);
        return root;
    }

    /**
     * Handler for switching to a different Dashboard Fragment
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Listens for events pertaining to the pantry
     * If the user long presses an item in the list, the handler will remove the item from the server and local list
     *
     * @param UID - the UID of the current user pertaining to their unique pantry
     */
    private void setUpListViewListener(String UID) {
        pantry.setOnItemLongClickListener((adapterView, view, i, l) -> {
            Context context = view.getContext().getApplicationContext();

            /* Update server and local list simultaneously */
            removeFromPantry(UID, ingredientsList.remove(i));
            pantryAdapter.notifyDataSetChanged();

            return true;
        });
    }


    /**
     * For a given UID and ingredient, sends req to add that ingredient to the Users pantry
     * response is string of all items in the pantry of the user request returns
     *
     * @param view       - the current view
     * @param UID     - the UID of the current user pertaining to their unique pantry
     * @param ingredient - the name of a given ingredient the user wants to add
     * @param quantity   - the quantity of the same item in the pantry
     * @param unitsType  - the type of amount (can, cup, tablespoon, etc.)
     */
    private void addItem(View view, String UID, String ingredient, int quantity, String unitsType) throws JSONException {
        /* If the user inputted text, send it */
        if (!ingredient.isEmpty()) {
            JsonArrayRequest addToPantryRequest = new JsonArrayRequest(Request.Method.PUT,
                    Const.URL_PANTRY_ADDITEM + "?UID=" + UID + "&ingredientName=" + ingredient + "&quantity=" + quantity + "&unitsType=" + unitsType, null,
                    response -> {
                        ingredientsList.add(ingredient + " - " + quantity + " - " + unitsType);
                        pantryAdapter.notifyDataSetChanged();
                    }, error -> {
                if (error.networkResponse != null) {
                    Log.d(TAG, "Error: " + error.getMessage());
                    Toast.makeText(view.getContext(), "An unexpected error occurred. Please try again.", Toast.LENGTH_SHORT).show();

                }
            });

            AppController.getInstance().addToRequestQueue(addToPantryRequest, tag_pantry_req);
        } else {
            Toast.makeText(view.getContext().getApplicationContext(), "Please enter text", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Get the pantry as an array of ingredients as strings from the server
     *
     * @param UID - the UID of the current user pertaining to their unique pantry
     */
    private void getUserPantryString(String UID) {
        JsonArrayRequest pantryStringRequest = new JsonArrayRequest(
                Request.Method.GET, Const.URL_PANTRY_GETPANTRYITEMS + "?UID=" + UID, null,
                response -> {
                    try {
                        for (int i = 0; i < response.length(); i++) {
                            String item = response.getString(i);
                            ingredientsList.add(item); // Add to ArrayList
                        }
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    pantryAdapter.notifyDataSetChanged(); // Update the ListView
                }, error -> {
            Log.d(TAG, "Error: " + error.getMessage());
        });

        AppController.getInstance().addToRequestQueue(pantryStringRequest, tag_pantry_req);
    }

    /**
     * Remove a requested ingredient from the pantry on the server and locally
     *
     * @param UID     - the UID of the current user pertaining to their unique pantry
     * @param ingredient - the name of the specified ingredient
     */
    private void removeFromPantry(String UID, String ingredient) {
        StringRequest pantryRemoveRequest = new StringRequest(
                Request.Method.DELETE, Const.URL_PANTRY_REMOVEITEM + "?UID=" + UID + "&ingredientName=" + ingredient,
                response -> {
                    Toast.makeText(this.getContext(), "Item Removed", Toast.LENGTH_LONG).show();
                }, error -> {

        });

        AppController.getInstance().addToRequestQueue(pantryRemoveRequest, tag_pantry_req);
    }
}