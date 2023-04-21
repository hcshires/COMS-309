package edu.iastate.cs309.hb6.foodtime.ui.home;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.databinding.FragmentHomeBinding;
import edu.iastate.cs309.hb6.foodtime.utils.AppController;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private HashMap<String, HashMap<String, Object>> meals;
    private ArrayList<String> recipes;
    private HashMap recipe;
    private ArrayList<String> dayMealsLabels;
    private ArrayAdapter<String> dayMealsAdapter;
    private final String TAG = HomeFragment.class.getSimpleName();
    private final String tag_home_req = "home_req";

    /**
     * HomeFragment View
     * @param inflater The LayoutInflater object that can be used to inflate
     * any views in the fragment,
     * @param container If non-null, this is the parent view that the fragment's
     * UI should be attached to.  The fragment should not add the view itself,
     * but this can be used to generate the LayoutParams of the view.
     * @param savedInstanceState If non-null, this fragment is being re-constructed
     * from a previous saved state as given here.
     *
     * @return HomeFragment
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /* Fragment Binding to Dashboard */
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /* User Fields */
        // User ID
        Bundle userData = requireActivity().getIntent().getExtras();
        final String UID = userData.getString("UID").replaceAll("\"", "");

        /* Meals */
        meals = new HashMap<>();
        recipes = new ArrayList<>();
        recipe = new HashMap<>();

        getRecipeLabels(UID);

        /* Widgets */
        // Calendar View - Constrain to week-by-week with current week
        CalendarView calendar = root.findViewById(R.id.calendarView);
        Calendar calUtil = Calendar.getInstance();

        // Get current Sunday-Saturday week
        final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.US).getFirstDayOfWeek();
        final DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);

        // Convert to dates based on current time
        LocalDateTime firstDay = LocalDateTime.now(ZoneId.of("-5")).with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDateTime lastDay = LocalDateTime.now(ZoneId.of("-5")).with(TemporalAdjusters.nextOrSame(lastDayOfWeek));

        // Set to calendar as Epoch milliseconds (ms since 1-1-1970)
        calendar.setMinDate(firstDay.toEpochSecond(ZoneOffset.of("-5")) * 1000); // set first day
        calendar.setMaxDate(lastDay.toEpochSecond(ZoneOffset.of("-5")) * 1000);  // set last day

        // ListView - Display list of meals scheduled for given day
        ListView dayMealsList = root.findViewById(R.id.dayMealsList);
        dayMealsLabels = new ArrayList<>();
        dayMealsAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, dayMealsLabels);
        dayMealsList.setAdapter(dayMealsAdapter);

        dayMealsList.setOnItemClickListener((adapterView, view, i, l) -> {
            // TODO: Navigate to recipe corresponding to meal
            // Intent recipeIntent = new Intent(DashboardActivity.this, RecipeActivity.class);
            // startActivity(recipeIntent);
        });

        dayMealsList.setOnItemLongClickListener(((adapterView, view, i, l) -> {
            removeMeal(UID, toDayString(calUtil.get(Calendar.DAY_OF_WEEK)), dayMealsList.getItemAtPosition(i).toString(), "false");
            dayMealsLabels.remove(i);
            dayMealsAdapter.notifyDataSetChanged();
            return true;
        }));

        // Initialize calendar meals for current day
        setMealsToCalendar(root, UID, calUtil.get(Calendar.DAY_OF_WEEK));

        // Re-render list of meals based on day of week selected by user
        calendar.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            // Convert date to day of the week
            calUtil.set(year, month, dayOfMonth);

            // Add meals for a given day to the calendar list
            setMealsToCalendar(root, UID, calUtil.get(Calendar.DAY_OF_WEEK));
        });

        // Add Meal button
        FloatingActionButton addMealBtn = root.findViewById(R.id.addMealBtn);
        addMealBtn.setOnClickListener(view -> {
            // Add Meal AlertDialog
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            View mView = getLayoutInflater().inflate(R.layout.dialog_addmeal, null);

            // Add Spinner to Dialog to select any existing recipes to plan
            Spinner recipeSelect = mView.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, recipes); // Use cook book recipe labels
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            recipeSelect.setAdapter(adapter);

            recipeSelect.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            builder.setTitle("Add a Meal")
                    .setView(recipeSelect)
                    .setCancelable(true)
                    .setNeutralButton("Create New Recipe", (dialogInterface, i) -> {
                        // TODO: Navigate to add recipe
                    })
                    .setPositiveButton("Add", (dialog, id) -> {
                        // Get the recipe corresponding to the name in the cookbook and add it as a meal in the calendar
                        String day = toDayString(calUtil.get(Calendar.DAY_OF_WEEK));
                        String recipeName = recipeSelect.getSelectedItem().toString();
                        getRecipeFromBook(UID, recipeName);
                        addMeal(UID, day, recipe);
                    })
                    .setNegativeButton("Cancel", (dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    });

            builder.setView(mView);
            AlertDialog alert = builder.create();
            alert.show();
        });
        /* End Widgets */

        return root;
    }

    /**
     * DestroyView handler
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    /**
     * Convert CalendarUtil day of week integer to English day string
     *
     * @param dayOfWeek - day of week represented as an int
     * @return day of week represented as a string in English
     */
    private String toDayString(int dayOfWeek) {
        switch (dayOfWeek) {
            case Calendar.SUNDAY:
                return "sunday";
            case Calendar.MONDAY:
                return "monday";
            case Calendar.TUESDAY:
                return "tuesday";
            case Calendar.WEDNESDAY:
                return "wednesday";
            case Calendar.THURSDAY:
                return "thursday";
            case Calendar.FRIDAY:
                return "friday";
            case Calendar.SATURDAY:
                return "saturday";
            default:
                return null;
        }
    }

    /**
     * Populate the list of meals onto the calendar based on the selected day of the week
     * Add meals to the ListView array list based on the selected day in the calendar
     *
     * @param UID - the ID of the user to get meals for
     * @param day - the day of the week as an integer per the java.util.Calendar class
     *
     * @returns the weekday the meals were set for as a string
     */
    private void setMealsToCalendar(View root, String UID, int day) {
        // List Label
        TextView listLabel = root.findViewById(R.id.mealsLbl);

        // Convert day int to string
        String dayString = toDayString(day);

        dayMealsLabels.clear();
        getMealsByDay(UID, dayString);

        for (Map.Entry<String, HashMap<String, Object>> entry : meals.entrySet()) {
            dayMealsLabels.add(entry.getKey());
        }

        listLabel.setText(dayString.toUpperCase());
        dayMealsAdapter.notifyDataSetChanged();
    }

    /**
     * Get a recipe from the user's cookbook based on the name
     *
     * @param UID - the user
     * @param mealName - the name of the recipe to get
     */
    private void getRecipeFromBook(String UID, String mealName) {
        JsonObjectRequest getRecipeReq = new JsonObjectRequest(Request.Method.GET, Const.URL_RECIPES_GETRECIPE + "?UID=" + UID + "&mealName=" + mealName, null, response -> {
            try {
                recipe.putAll(new ObjectMapper().readValue(String.valueOf(response), HashMap.class));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, error -> {
            VolleyLog.d(TAG, error);
        });

        AppController.getInstance().addToRequestQueue(getRecipeReq, tag_home_req);
    }

    /**
     * Get the labels of each recipe in the user's cookbook to populate the add item options
     *
     * @param UID - the ID of the current user
     */
    private void getRecipeLabels(String UID) {
        JsonArrayRequest recipeLabelsReq = new JsonArrayRequest(Request.Method.GET, Const.URL_RECIPES_GETLABELS + "?UID=" + UID, null, response -> {
            recipes.addAll(new Gson().fromJson(String.valueOf(response), ArrayList.class));
        }, error -> {
            VolleyLog.d(TAG, error);
        });

        AppController.getInstance().addToRequestQueue(recipeLabelsReq, tag_home_req);
    }

    /**
     * Get the list of meals planned for a given day
     *
     * @param UID - the ID of the current user
     * @param day - the day to get meals for
     */
    private void getMealsByDay(String UID, String day) {
        JsonObjectRequest getMealByDayReq = new JsonObjectRequest(Request.Method.GET, Const.URL_MEALS_GET_BYDAY + "?UID=" + UID + "&day=" + day, null, response -> {
            meals.clear();
            meals.putAll(new Gson().fromJson(String.valueOf(response), HashMap.class));
        }, error -> {
            VolleyLog.d(TAG, error);
        });

        AppController.getInstance().addToRequestQueue(getMealByDayReq, tag_home_req);
    }

    /**
     * Add a meal to a given day in the calendar
     *
     * @param UID - the user to add a meal to their calendar
     * @param day - the given day of the week to add the meal to
     * @param meal - the meal to add
     */
    private void addMeal(String UID, String day, HashMap<String, Object> meal) {
        JsonObjectRequest addMealReq = new JsonObjectRequest(Request.Method.PUT, Const.URL_MEALS_ADD + "?UID=" + UID + "&day=" + day, new JSONObject(meal), response -> {
            Toast.makeText(getContext(), "Meal added", Toast.LENGTH_SHORT).show();
        }, error -> {
            // Toast.makeText(getContext(), "Failed to add meal", Toast.LENGTH_SHORT).show();
        });

        AppController.getInstance().addToRequestQueue(addMealReq, tag_home_req);
    }

    /**
     * Remove a meal from a given day in the calendar
     *
     * @param UID - the user to remove a meal from their calendar
     * @param day - the given day of the week to remove the meal from
     * @param mealName - the name of the meal
     * @param removeAll - whether to disregard mealName and remove all meals or not
     */
    private void removeMeal(String UID, String day, String mealName, String removeAll) {
        JsonObjectRequest removeMealReq = new JsonObjectRequest(Request.Method.DELETE,
                Const.URL_MEALS_REMOVE + "?UID=" + UID + "&day=" + day + "&mealName=" + mealName + "&removeAll=" + removeAll, null, response -> {
            Toast.makeText(getContext(), "Meal removed", Toast.LENGTH_SHORT).show();
        }, error -> {
        });

        AppController.getInstance().addToRequestQueue(removeMealReq, tag_home_req);
    }
}