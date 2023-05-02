package edu.iastate.cs309.hb6.foodtime;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Test cases for LoginActivity on FoodTime
 * <p>
 * This testing file uses ActivityScenarioRule instead of ActivityTestRule
 * to demonstrate system testings cases
 */
@RunWith(AndroidJUnit4ClassRunner.class)
public class MainTest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityScenarioRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Launch the LoginActivity from MainActivity
     */
    @Test
    public void launchApp() {
        Intents.init();
        onView(withId(R.id.toLoginBtn)).perform(click());
        intended(hasComponent(LoginActivity.class.getName())); // Check the intent started the correct activity
        Intents.release();
    }
}
