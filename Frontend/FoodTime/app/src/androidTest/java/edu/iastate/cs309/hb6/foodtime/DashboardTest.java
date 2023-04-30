package edu.iastate.cs309.hb6.foodtime;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.runner.RunWith;

/**
 * Test cases for DashboardActivity on FoodTime
 * <p>
 * Test cases involve fragment classes related to the Dashboard
 * This testing file uses ActivityScenarioRule instead of ActivityTestRule
 * to demonstrate system testings cases
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class DashboardTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<DashboardActivity> activityScenarioRule = new ActivityScenarioRule<>(DashboardActivity.class);
}
