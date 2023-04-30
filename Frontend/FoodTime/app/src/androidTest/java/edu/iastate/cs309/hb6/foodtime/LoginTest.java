package edu.iastate.cs309.hb6.foodtime;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;

/**
 * Test cases for LoginActivity on FoodTime
 * <p>
 * This testing file uses ActivityScenarioRule instead of ActivityTestRule
 * to demonstrate system testings cases
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class LoginTest {
    private static final int SIMULATED_DELAY_MS = 500;

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

//    /**
//     * Start the server and run this test
//     */
//    @Test
//    public void loginUser(){
//        String testString = "defaultstring";
//        String resultString = "gnirtstluafed";
//
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            activity.defaultString = testString;
//            activity.aSwitch.setChecked(true);
//        });
//
//        onView(withId(R.id.submit)).perform(click());
//        // Put thread to sleep to allow volley to handle the request
//        try {
//            Thread.sleep(SIMULATED_DELAY_MS);
//        } catch (InterruptedException e) {}
//
//        // Verify that volley returned the correct value
//        onView(withId(R.id.myTextView)).check(matches(withText(endsWith(resultString))));
//    }
//
//    /**
//     * Start the server and run this test
//     */
//    @Test
//    public void createUser(){
//
//        String testString = "inputstring";
//        String resultString = "gnirtstupni";
//
//        activityScenarioRule.getScenario().onActivity(activity -> {
//            activity.defaultString = null;
//            activity.aSwitch.setChecked(false);
//        });
//
//        // Type in testString and send request
//        onView(withId(R.id.stringEntry)).perform(typeText(testString), closeSoftKeyboard());
//
//        // Click button to submit
//        onView(withId(R.id.submit)).perform(click());
//
//        // Put thread to sleep to allow volley to handle the request
//        try {
//            Thread.sleep(SIMULATED_DELAY_MS);
//        } catch (InterruptedException e) {}
//
//        // Verify that volley returned the correct value
//        onView(withId(R.id.myTextView)).check(matches(withText(endsWith(resultString))));
//    }
}

