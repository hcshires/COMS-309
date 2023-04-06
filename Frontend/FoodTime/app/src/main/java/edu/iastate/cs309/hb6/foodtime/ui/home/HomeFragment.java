package edu.iastate.cs309.hb6.foodtime.ui.home;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.android.volley.Request;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

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
        final String userID = userData.getString("userID").replaceAll("\"", "");

        /* Meals */
        meals = new HashMap<>();

        /* Widgets */

        // ListView - Display list of meals scheduled for given day
        ListView dayMealsList = (ListView) root.findViewById(R.id.dayMealsList);
        dayMealsLabels = new ArrayList<>();
        dayMealsAdapter = new ArrayAdapter<>(root.getContext(), android.R.layout.simple_list_item_1, dayMealsLabels);
        dayMealsList.setAdapter(dayMealsAdapter);

        dayMealsList.setOnItemClickListener((adapterView, view, i, l) -> {
            // TODO: Navigate to recipe corresponding to meal
            // Intent recipeIntent = new Intent(DashboardActivity.this, RecipeActivity.class);
            // startActivity(recipeIntent);
        });

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

        // Initialize calendar meals for current day
        setMealsToCalendar(root, userID, calUtil.get(Calendar.DAY_OF_WEEK));

        // Re-render list of meals based on day of week selected by user
        calendar.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            // Convert date to day of the week
            calUtil.set(year, month, dayOfMonth);

            // Add meals for a given day to the calendar list
            setMealsToCalendar(root, userID, calUtil.get(Calendar.DAY_OF_WEEK));
        });

        // Add Meal AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Add a Meal")
                .setCancelable(true)
                .setPositiveButton("Add", (dialog, id) -> {

                });
        AlertDialog alert = builder.create();

        // Add Meal button
        FloatingActionButton addMealBtn = root.findViewById(R.id.addMealBtn);
        addMealBtn.setOnClickListener(view -> {
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
     * Populate the list of meals onto the calendar based on the selected day of the week
     * Add meals to the ListView array list based on the selected day in the calendar
     *
     * @param userID - the ID of the user to get meals for
     * @param day - the day of the week as an integer per the java.util.Calendar class
     *
     * @returns the weekday the meals were set for as a string
     */
    private void setMealsToCalendar(View root, String userID, int day) {
        // List Label
        TextView listLabel = root.findViewById(R.id.mealsLbl);

        // Convert day int to string
        String dayString = "";

        switch (day) {
            case Calendar.SUNDAY:
                dayString = "sunday";
                break;
            case Calendar.MONDAY:
                dayString = "monday";
                break;
            case Calendar.TUESDAY:
                dayString = "tuesday";
                break;
            case Calendar.WEDNESDAY:
                dayString = "wednesday";
                break;
            case Calendar.THURSDAY:
                dayString = "thursday";
                break;
            case Calendar.FRIDAY:
                dayString = "friday";
                break;
            case Calendar.SATURDAY:
                dayString = "saturday";
                break;
        }

        dayMealsLabels.clear();
        getMealsByDay(userID, dayString);

        for (Map.Entry<String, HashMap<String, Object>> entry : meals.entrySet()) {
            dayMealsLabels.add(entry.getKey());
        }

        listLabel.setText(dayString.toUpperCase());
        dayMealsAdapter.notifyDataSetChanged();
    }

    /**
     *
     * @param userID
     * @param day
     */
    private void getMealsByDay(String userID, String day) {
        JsonObjectRequest getMealByDayReq = new JsonObjectRequest(Request.Method.GET, Const.URL_MEALS_GET_BYDAY + "?UID=" + userID + "&day=" + day, null, response -> {
            meals.clear();
            meals.putAll(new Gson().fromJson(String.valueOf(response), HashMap.class));
        }, error -> {
            VolleyLog.d(TAG, error);
        });

        AppController.getInstance().addToRequestQueue(getMealByDayReq, tag_home_req);
    }

    /**
     *
     * @param userID
     * @param day
     */
    private void getAllMeals(String userID, String day) {
        JsonObjectRequest getMealByDayReq = new JsonObjectRequest(Request.Method.GET, Const.URL_MEALS_GET_BYDAY + "?UID=" + userID, null, response -> {
            meals.clear();
            meals.putAll(new Gson().fromJson(String.valueOf(response), HashMap.class));
        }, error -> {
            VolleyLog.d(TAG, error);
        });

        AppController.getInstance().addToRequestQueue(getMealByDayReq, tag_home_req);
    }

    /**
     *
     * @param userID
     * @param day
     * @param mealName
     * @param removeAll
     */
    private void removeMeal(String userID, String day, String mealName, String removeAll) {

    }
}