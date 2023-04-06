package edu.iastate.cs309.hb6.foodtime.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.Locale;

import edu.iastate.cs309.hb6.foodtime.R;
import edu.iastate.cs309.hb6.foodtime.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    private final String TAG = HomeFragment.class.getSimpleName();
    private final String tag_home_req = "home_req";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        /* Calendar View - Constrain to week-by-week with current week */
        CalendarView calendar = root.findViewById(R.id.calendarView);

        // Get current Sunday-Saturday week
        final DayOfWeek firstDayOfWeek = WeekFields.of(Locale.US).getFirstDayOfWeek();
        final DayOfWeek lastDayOfWeek = DayOfWeek.of(((firstDayOfWeek.getValue() + 5) % DayOfWeek.values().length) + 1);

        // Convert to dates based on current time
        LocalDateTime firstDay = LocalDateTime.now(/* tz */).with(TemporalAdjusters.previousOrSame(firstDayOfWeek));
        LocalDateTime lastDay = LocalDateTime.now(/* tz */).with(TemporalAdjusters.nextOrSame(lastDayOfWeek));

        // Set to calendar as Epoch milliseconds (ms since 1-1-1970)
        calendar.setMinDate(firstDay.toEpochSecond(ZoneOffset.MIN) * 1000); // set first day
        calendar.setMaxDate(lastDay.toEpochSecond(ZoneOffset.MIN) * 1000);  // set last day

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}