package edu.iastate.cs309.hb6.foodtime;

import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.hamcrest.CoreMatchers.allOf;

import android.content.Intent;

import java.util.Random;

import edu.iastate.cs309.hb6.foodtime.utils.Const;

/**
 * Test cases for LoginActivity on FoodTime
 * <p>
 * This testing file uses ActivityScenarioRule instead of ActivityTestRule
 * to demonstrate system testings cases
 */
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest   // large execution time
public class LoginTest {
    private static final int SIMULATED_DELAY_MS = 1000;

    private static final String testEmail = Const.CRED_PARENT_USER[0];
    private static final String testPass = Const.CRED_PARENT_USER[1];

    @Rule
    public ActivityScenarioRule<LoginActivity> activityScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    @Before
    public void setUp() {
        Intents.init();
    }

    /**
     * Start the server and run this test
     */
    @Test
    public void loginUser(){
        onView(withId(R.id.email)).perform(typeText(testEmail));                        // Enter email
        onView(withId(R.id.password)).perform(typeText(testPass), closeSoftKeyboard()); // Enter password
        onView(withId(R.id.loginBtn)).perform(click());                                 // Click login button

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        Matcher<Intent> intent = allOf(
                hasComponent(DashboardActivity.class.getName()),
                hasExtraWithKey("UID")
        );

        intended(intent); // Check the intent started the Dashboard
    }

    /**
     * Start the server and run this test
     */
    @Test
    public void createUser(){
        String testEmail = new Random().nextInt() + "@COMS309.com";
        String testPass = "food";
        String testParentUsername = "SystemTestUser@COMS309.com";

        onView(withId(R.id.email)).perform(typeText(testEmail));                   // Enter email
        onView(withId(R.id.password)).perform(typeText(testPass), closeSoftKeyboard());                 // Enter password
        onView(withId(R.id.childRoleBtn)).perform(click());                        // Enter role "Child"
        onView(withId(R.id.parentUsername)).perform(typeText(testParentUsername), closeSoftKeyboard()); // Enter parent username

        // Verify that volley returned the correct value
        onView(withId(R.id.registerBtn)).perform(click()); // Click register button

        // Put thread to sleep to allow volley to handle the request
        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {}

        Matcher<Intent> intent = allOf(
                hasComponent(DashboardActivity.class.getName()),
                hasExtraWithKey("UID")
        );

        intended(intent); // Check the intent started the Dashboard
    }

    @After
    public void tearDown() {
        Intents.release();
    }
}

