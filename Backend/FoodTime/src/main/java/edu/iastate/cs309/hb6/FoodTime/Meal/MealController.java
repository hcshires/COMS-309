package edu.iastate.cs309.hb6.FoodTime.Meal;

import edu.iastate.cs309.hb6.FoodTime.Login.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class MealController {

    @Autowired
    UserRepository userDB;

    @Autowired
    RecipeRepository recipeDB;

    @PutMapping("/meals/add")
    @Transactional
    @ResponseBody
    public ResponseEntity<Object> addMeal(@RequestParam String UID, @RequestParam String day, @RequestBody Meal meal) {
        HashMap<String, Meal> mealsForDay = getUserMealsForDay(UID, day);
        mealsForDay.put(meal.getName(), meal);

        //Also add the meal to the recipe book if it doesn't already exist
        Map<String, Recipe> recipes = userDB.findByUID(UID).getUserRecipes();
        Recipe recipeToAdd = new Recipe (meal);
        recipes.put(recipeToAdd.getName(), recipeToAdd);
        if (!userDB.findByUID(UID).getRecipeLabels().contains(meal.getName())) {
            userDB.findByUID(UID).getRecipeLabels().add(meal.getName());
        }

        return new ResponseEntity<>(mealsForDay + day.toLowerCase(), HttpStatus.OK);
    }

    @DeleteMapping("/meals/remove")
    @Transactional
    public ResponseEntity<Object> removeMeal(@RequestParam String UID, @RequestParam String day, @RequestParam String mealName, @RequestParam boolean removeAll) {
        HashMap<String, Meal> mealsForDay = getUserMealsForDay(UID, day);

        //Remove just one meal for a day
        if (mealsForDay.remove(mealName) == null && !removeAll) {
            return new ResponseEntity<>(String.format("Meal not found on day %s for user %s", day.toLowerCase(), UID), HttpStatus.NOT_FOUND);
        }
        //Clear out all of user's meals for a day
        else if (removeAll) {
            MealList mealsForUser = userDB.findByUID(UID).getUserMeals();
            HashMap<String, Meal> emptyList = new HashMap<>();
            mealsForUser.setMealsForDay(day.toLowerCase(), emptyList);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else {
           return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("/meals/update")
    @Transactional
    public ResponseEntity<Object> updateMeal(@RequestParam String UID, @RequestParam String day, @RequestParam String mealName, @RequestBody Meal newMeal) {
        HashMap<String, Meal> mealsForDay = getUserMealsForDay(UID, day);

        //We will just replace one meal of a given day for a given user
        if (mealsForDay.containsKey(mealName)) {
            mealsForDay.replace(mealName, newMeal);
            return new ResponseEntity<>(mealsForDay.get(mealName), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(String.format("Meal not found on day %s for user %s", day, UID), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("meals/get/by-day")
    public ResponseEntity<Object> returnMealsForDay(@RequestParam String UID, @RequestParam String day) {
        return new ResponseEntity<>(getUserMealsForDay(UID, day), HttpStatus.OK);
    }

    @GetMapping("meals/get/all")
    public ResponseEntity<Object> returnAllMeals(@RequestParam String UID) {
        return new ResponseEntity<>(userDB.findByUID(UID).getUserMeals(), HttpStatus.OK);
    }

    @PutMapping("recipes/add")
    @Transactional
    @ResponseBody
    public ResponseEntity<Object> addRecipe(@RequestParam String UID, @RequestBody Meal mealToAdd) {
        Map<String, Recipe> userRecipes = userDB.findByUID(UID).getUserRecipes();
        if (!userRecipes.containsKey(mealToAdd.getName())) {
            //We create a new Recipe with user information here so that frontend does not
            //have to worry about passing us a whole user object
            userRecipes.put(mealToAdd.getName(), new Recipe(mealToAdd));
            userDB.findByUID(UID).getRecipeLabels().add(mealToAdd.getName());
            return new ResponseEntity<>(userRecipes, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(String.format("User %s already has %s in recipe book", UID, mealToAdd.getName()), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("recipes/get")
    public ResponseEntity<Object> getRecipe(@RequestParam String UID, @RequestParam String mealName) {
        if (userDB.findByUID(UID).getUserRecipes().containsKey(mealName)) {
            return new ResponseEntity<>(userDB.findByUID(UID).getUserRecipes().get(mealName), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(String.format("Recipe for %s not found in database for UID %s", mealName, UID), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("recipes/remove")
    @Transactional
    public ResponseEntity<Object> removeRecipe(@RequestParam String UID, @RequestParam String recipeName) {
        Map<String, Recipe> userRecipes = userDB.findByUID(UID).getUserRecipes();
        if (userRecipes.containsKey(recipeName)) {
            userRecipes.remove(recipeName);
            userDB.findByUID(UID).getRecipeLabels().remove(recipeName);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/recipes/get/labels")
    public ResponseEntity<Object> getRecipeLabels(@RequestParam String UID) {
        return new ResponseEntity<>(userDB.findByUID(UID).getRecipeLabels(), HttpStatus.OK);
    }

    private HashMap<String, Meal> getUserMealsForDay (String UID, String day) {
        MealList mealsForUser = userDB.findByUID(UID).getUserMeals();
        return mealsForUser.getMealsForDay(day.toLowerCase());
    }
}
