package edu.iastate.cs309.hb6.foodtime.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.cs309.hb6.foodtime.DashboardActivity;
import edu.iastate.cs309.hb6.foodtime.LoginActivity;
import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.databinding.FragmentDashboardBinding;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

public class DashboardFragment extends Fragment {

    private ArrayList<String> items;
    private ListView listView;
    private Button addButton;

    private ArrayAdapter<String> itemAdapter;

    private EditText input;

    private FragmentDashboardBinding binding;

    private final String TAG = DashboardFragment.class.getSimpleName();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        DashboardViewModel dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);


        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textPantryheader;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);

        listView = (ListView) root.findViewById(R.id.pantryItems);
        addButton = (Button) root.findViewById(R.id.addButton);
        input = root.findViewById(R.id.editTextAddPantry);



        addButton.setOnClickListener(this::addItem);

        items = new ArrayList<>();
        itemAdapter = new ArrayAdapter<String>(root.getContext(),
                android.R.layout.simple_list_item_1 , items);
        listView.setAdapter(itemAdapter);

        setUpListViewListener();



        return root;

    }

    private void setUpListViewListener() {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Context context = view.getContext().getApplicationContext();
                Toast.makeText(context,"Item Removed", Toast.LENGTH_LONG).show();
                items.remove(i);
                itemAdapter.notifyDataSetChanged();
                return true;
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void addItem(View view) {
        String itemText = input.getText().toString();
        
        if (!(itemText.equals(""))) {
            items.add(itemText);
            input.setText("");
            itemAdapter.notifyDataSetChanged();
        }
        else{
            Toast.makeText(view.getContext().getApplicationContext(), "Please enter text", Toast.LENGTH_LONG).show();
        }

    }

    /*

     COMMENTED THIS METHOD OUT B/C DID NOT WANT TO PUSH WITH ERRORS.

    private void addItemToServer (String item) throws JSONException {
        /*
        {
        "name":"butter""
        }

        Map<String, String> params = new HashMap<>();
        params.put("UID", input.getText().toString());  /* how do you get USER ID ?
        params.put("name", input.getText().toString());

        JSONObject reqBody = new JSONObject(params);

        JSONObject addToPantry = new JsonObjectRequest(Request.Method.PUT, Const.URL_PANTRY_ADDITEM, reqBody,
               response -> {

                        Log.d(TAG, "item added: " + response.toString());
                    /*
                    addItem();





                }, error -> {




                });




    }

     */


    private void getPantry() throws JSONException {


    }
}
