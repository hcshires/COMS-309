package edu.iastate.cs309.hb6.foodtime.ui.pantry;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.databinding.FragmentPantryBinding;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

/**
 * Dashboard Fragment: Pantry
 * Used for keeping a list of known kitchen ingredients to use in the Cook Book
 */
public class PantryFragment extends Fragment {

    /**
     * The pantry list
     */
    private static ArrayList<String> ingredientsList;
    /**
     * The tag for this fragment
     */
    private final String TAG = PantryFragment.class.getSimpleName();
    /**
     * The tag for pantry requests
     */
    private final String tag_pantry_req = "pantry_req";
    /**
     * The adapter for the pantry list
     */
    private ArrayAdapter<String> pantryAdapter;
    /**
     * The pantry ListView
     */
    private ListView pantry;
    /**
     * An input text box
     */
    private EditText input, quantityInput, unitInput;
    /**
     * The binding for this fragment
     */
    private FragmentPantryBinding binding;

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
        pantry = root.findViewById(R.id.pantryItems);
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
            int quantity;

            if (!quantityInput.getText().toString().isEmpty()) {
                quantity = Integer.parseInt(quantityInput.getText().toString());
            } else {
                quantity = 0;
            }

            if (!ingredient.isEmpty() && !unitType.isEmpty() && quantity != 0) {
                try {
                    addItem(view, UID, ingredient, quantity, unitType);
                    input.setText("");
                    quantityInput.setText("");
                    unitInput.setText("");
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Toast.makeText(root.getContext(), "One or more input fields blank", Toast.LENGTH_SHORT).show();
            }
        });

        /* Listen for clicks on the pantry (i.e. delete items) */
        setUpListViewListener(root, UID);
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
     * @param root the current view
     * @param UID  the UID of the current user pertaining to their unique pantry
     */
    private void setUpListViewListener(View root, String UID) {
        pantry.setOnItemLongClickListener((adapterView, view, i, l) -> {
            /* Update server and local list */
            removeFromPantry(root, UID, ingredientsList.get(i), i);

            return true;
        });
    }

    /**
     * For a given UID and ingredient, sends req to add that ingredient to the Users pantry
     * response is string of all items in the pantry of the user request returns
     *
     * @param view       the current view
     * @param UID        the UID of the current user pertaining to their unique pantry
     * @param ingredient the name of a given ingredient the user wants to add
     * @param quantity   the quantity of the same item in the pantry
     * @param unitsType  the type of amount (can, cup, tablespoon, etc.)
     * @throws JSONException if the JSON is malformed
     */
    private void addItem(View view, String UID, String ingredient, int quantity, String unitsType) throws JSONException {
        /* If the user inputted text, send it */
        JsonArrayRequest addToPantryRequest = new JsonArrayRequest(Request.Method.PUT,
                Const.URL_PANTRY_ADDITEM + "?UID=" + UID + "&ingredientName=" + ingredient + "&quantity=" + quantity + "&unitsType=" + unitsType, null,
                response -> {
                    ingredientsList.add(ingredient + " - " + quantity + " - " + unitsType);
                    pantryAdapter.notifyDataSetChanged();
                }, error -> {
            if (error.networkResponse.statusCode == 403) {
                Toast.makeText(view.getContext(), new String(error.networkResponse.data, StandardCharsets.UTF_8), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(view.getContext(), "An unexpected error occurred. Please try again.", Toast.LENGTH_LONG).show();
            }
        });

        AppController.getInstance().addToRequestQueue(addToPantryRequest, tag_pantry_req);
    }

    /**
     * Get the pantry as an array of ingredients as strings from the server
     *
     * @param UID the UID of the current user pertaining to their unique pantry
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
        });

        AppController.getInstance().addToRequestQueue(pantryStringRequest, tag_pantry_req);
    }

    /**
     * Remove a requested ingredient from the pantry on the server and locally
     *
     * @param view       the current view
     * @param UID        the UID of the current user pertaining to their unique pantry
     * @param ingredient the name of the specified ingredient
     * @param index      the index of the ingredients list of the removed item, for use to remove from ArrayList after successful request
     */
    private void removeFromPantry(View view, String UID, String ingredient, int index) {
        StringRequest pantryRemoveRequest = new StringRequest(
            Request.Method.DELETE, Const.URL_PANTRY_REMOVEITEM + "?UID=" + UID + "&ingredientName=" + ingredient,
            response -> {
                ingredientsList.remove(index);
                pantryAdapter.notifyDataSetChanged();

                /* Clear the input fields */
                input.setText("");
                quantityInput.setText("");
                unitInput.setText("");

                /* Notify the user */
                Toast.makeText(view.getContext(), "Item Removed", Toast.LENGTH_SHORT).show();
            }, error -> {
                /* No permission */
                if (error.networkResponse.statusCode == 403) {
                    Toast.makeText(view.getContext(), new String(error.networkResponse.data, StandardCharsets.UTF_8), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(view.getContext(), "An unexpected error occurred. Please try again.", Toast.LENGTH_LONG).show();
                }
        });

        AppController.getInstance().addToRequestQueue(pantryRemoveRequest, tag_pantry_req);
    }
}