package edu.iastate.cs309.hb6.foodtime.ui.dashboard;

import android.content.Context;
import android.os.Bundle;
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

import java.util.ArrayList;

import edu.iastate.cs309.hb6.foodtime.DashboardActivity;
import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private ArrayList<String> items;
    private ListView listView;
    private Button addButton;

    private ArrayAdapter<String> itemAdapter;

    private EditText input;

    private FragmentDashboardBinding binding;

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
}