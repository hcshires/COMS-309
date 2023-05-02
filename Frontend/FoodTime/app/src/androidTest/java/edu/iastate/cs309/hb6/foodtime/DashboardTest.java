package edu.iastate.cs309.hb6.foodtime;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static androidx.test.espresso.matcher.ViewMatchers.hasChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.hasDescendant;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import edu.iastate.cs309.hb6.foodtime.ui.cookbook.AddDirectionActivity;
import edu.iastate.cs309.hb6.foodtime.ui.cookbook.AddRecipeActivity;
import edu.iastate.cs309.hb6.foodtime.ui.cookbook.CardAdapter;
import edu.iastate.cs309.hb6.foodtime.ui.cookbook.IngredientsAdapter;
import edu.iastate.cs309.hb6.foodtime.ui.cookbook.Recipe;
import edu.iastate.cs309.hb6.foodtime.ui.cookbook.ViewRecipeActivity;
import edu.iastate.cs309.hb6.foodtime.utils.Const;

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
    /** Sample UID to test app with */
    private static final String testUID = Const.CRED_PARENT_USER[2];

    /** ActivityScenarioRule to log in a user first before using the Dashboard */
    @Rule
    public ActivityScenarioRule<LoginActivity> setupScenarioRule = new ActivityScenarioRule<>(LoginActivity.class);

    /**
     * Set up the tests
     */
    @Before
    public void setUp() {
        // Inject UID into Activity
        setupScenarioRule.getScenario().onActivity(activity -> {
            Intent intent = new Intent(activity.getApplicationContext(), DashboardActivity.class);
            intent.putExtra("UID", testUID);
            activity.startActivity(intent);
        });

        Intents.init();
    }

    /**
     * Test the navigation bar
     */
    @Test
    public void testDashboard() {
        // Test Meal Planner fragment
        onView(withId(R.id.navigation_home)).perform(click());
        onView(withId(R.id.HomeFragment)).check(matches(isDisplayed()));

        // Test Pantry fragment
        onView(withId(R.id.navigation_pantry)).perform(click());
        onView(withId(R.id.PantryFragment)).check(matches(isDisplayed()));

        // Test Cookbook fragment
        onView(withId(R.id.navigation_cookbook)).perform(click());
        onView(withId(R.id.CookBookFragment)).check(matches(isDisplayed()));
    }

    /**
     * Test adding items and ingredients to the user's pantry
     */
    @Test
    public void addToPantry(){
        // Navigate to pantry
        onView(withId(R.id.navigation_pantry)).perform(click());
        onView(withId(R.id.PantryFragment)).check(matches(isDisplayed()));

        // Get the current size of the pantry listview
        final int[] listSize = { 0 };
        onView(withId(R.id.pantryItems)).check(matches(new TypeSafeMatcher<>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                ListView listView = (ListView) view;

                // here we assume the adapter has been fully loaded already
                listSize[0] = listView.getAdapter().getCount();

                return true;
            }
        }));

        // Input a new ingredient
        String testName = "Test Item";
        String testUnit = "Cups";
        int testQuantity = 5;

        onView(withId(R.id.editTextAddPantry)).perform(typeText(testName));
        onView(withId(R.id.quantityTxt)).perform(typeText(Integer.toString(testQuantity)));
        onView(withId(R.id.unitTypesTxt)).perform(typeText(testUnit), closeSoftKeyboard());

        // Add to pantry
        onView(withId(R.id.addToPantryBtn)).perform(click());
        onView(withId(R.id.pantryItems)).check(matches(isDisplayed())); // Is there a listview

        // Check if the item is in the listview, don't subtract one from size since we're adding new item
        onData(anything()).inAdapterView(withId(R.id.pantryItems)).atPosition(listSize[0]).check(matches(withText(testName + " - " + testQuantity + " - " + testUnit)));
    }

    /**
     * Test adding a recipe to the cookbook
     */
    @Test
    public void addToCookbook() {
        // Navigate to cookbook
        onView(withId(R.id.navigation_cookbook)).perform(click());
        onView(withId(R.id.CookBookFragment)).check(matches(isDisplayed()));

        // Get the current size of the cookbook listview
        final int[] listSize = {0};
        onView(withId(R.id.cookbookItems)).check(matches(new TypeSafeMatcher<>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            public boolean matchesSafely(View view) {
                RecyclerView list = (RecyclerView) view;

                // here we assume the adapter has been fully loaded already
                listSize[0] = list.getAdapter().getItemCount();

                return true;
            }
        }));

        // Navigate to AddRecipe
        onView(withId(R.id.addRecipeBtn)).perform(click());
        intended(hasComponent(AddRecipeActivity.class.getName()));

        // Input a new recipe (random to prevent duplicate recipe names and server error)
        String testName = Integer.toString(new Random().nextInt());
        String testDay = "Monday";

        String testIngredient1 = "Chicken";
        String testUnit1 = "Pounds";
        int testQuantity1 = 2;

        String testIngredient2 = "Chicken";
        String testUnit2 = "Pounds";
        int testQuantity2 = 2;

        String testIngredient3 = "Chicken";
        String testUnit3 = "Pounds";
        int testQuantity3 = 2;

        String testIngredient4 = "Chicken";
        String testUnit4 = "Pounds";
        int testQuantity4 = 2;

        String testIngredient5 = "Chicken";
        String testUnit5 = "Pounds";
        int testQuantity5 = 2;

        // Name and date
        onView(withId(R.id.recipeTitleInput)).perform(typeText(testName));
        onView(withId(R.id.dayInput)).perform(typeText(testDay));

        // Ingredient 1
        onView(withId(R.id.ingredientInput1)).perform(typeText(testIngredient1));
        onView(withId(R.id.qtInput1)).perform(typeText(Integer.toString(testQuantity1)));
        onView(withId(R.id.typeInput1)).perform(typeText(testUnit1), closeSoftKeyboard());

        // Ingredient 2
        onView(withId(R.id.ingredientInput2)).perform(typeText(testIngredient2));
        onView(withId(R.id.qtInput2)).perform(typeText(Integer.toString(testQuantity2)));
        onView(withId(R.id.typeInput2)).perform(typeText(testUnit2), closeSoftKeyboard());

        // Ingredient 3
        onView(withId(R.id.ingredientInput3)).perform(typeText(testIngredient3));
        onView(withId(R.id.qtInput3)).perform(typeText(Integer.toString(testQuantity3)));
        onView(withId(R.id.typeInput3)).perform(typeText(testUnit3), closeSoftKeyboard());

        // Ingredient 4
        onView(withId(R.id.ingredientInput4)).perform(typeText(testIngredient4));
        onView(withId(R.id.qtInput4)).perform(typeText(Integer.toString(testQuantity4)));
        onView(withId(R.id.typeInput4)).perform(typeText(testUnit4), closeSoftKeyboard());

        // Ingredient 5
        onView(withId(R.id.ingredientInput5)).perform(typeText(testIngredient5));
        onView(withId(R.id.qtInput5)).perform(typeText(Integer.toString(testQuantity5)));
        onView(withId(R.id.typeInput5)).perform(typeText(testUnit5), closeSoftKeyboard());

        // TODO: Test directions

        // Add recipe and navigate back to Cookbook
        onView(withId(R.id.submitRecipe)).perform(click());
        intended(hasComponent(AddDirectionActivity.class.getName()));

        String testDirection1 = "Aaaaa";

        String testDirection2 = "Bbbbb";

        String testDirection3 = "Ccccc";

        String testDirection4 = "Dddddd";

        String testDirection5 = "Eeeeee";

        String testDirection6 = "Fffff";

        String testDirection7 = "Ggggg";

        String testDirection8 = "Hhhhh";

        String testDirection9 = "Iiiii";

        String testDirection10 = "Jjjjj";

        onView(withId(R.id.btnAdd)).perform(click());

        onView(withId(R.id.navigation_cookbook)).perform(click());
        onView(withId(R.id.CookBookFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.cookbookItems)).check(matches(isDisplayed())); // Is there a listview

        // Check if the item is in the listview, don't subtract one from size since we're adding new item
        onView(withId(R.id.cookbookItems)).perform(RecyclerViewActions.actionOnItem(hasDescendant(withText(testName)), scrollTo()));
    }
    @Test
    public void viewRecipeTitle() {
        //navigate to cookbook
        onView(withId(R.id.navigation_cookbook)).perform(click());
        onView(withId(R.id.CookBookFragment)).check(matches(isDisplayed()));
        onView(withId(R.id.cookbookItems)).perform(RecyclerViewActions.actionOnItemAtPosition(0, MyViewAction.clickChildViewWithId(R.id.cv)));

        onView(withId(R.id.ViewRecipeActivity)).check(matches(isDisplayed()));
        onView(withId(R.id.recipeTitle)).check(matches(isDisplayed()));

        Matcher<Intent> intent = allOf(
                hasComponent(ViewRecipeActivity.class.getName()),
                hasExtraWithKey("RecipeTitle")
        );
        intended(intent);

    }




    /**
     * After tests, run cleanup
     */
    @After
    public void tearDown() {
        Intents.release();
    }

    public static class MyViewAction {

        public static ViewAction clickChildViewWithId(final int id) {
            return new ViewAction() {
                @Override
                public Matcher<View> getConstraints() {
                    return null;
                }


                @Override
                public String getDescription() {
                    return "Click on a child view with specified id.";
                }

                @Override
                public void perform(UiController uiController, View view) {
                    View v = view.findViewById(id);
                    v.performClick();
                }
            };
        }

    }
}
