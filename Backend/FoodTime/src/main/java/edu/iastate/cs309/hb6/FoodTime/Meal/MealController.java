package edu.iastate.cs309.hb6.FoodTime.Meal;

import edu.iastate.cs309.hb6.FoodTime.Login.UserRepository;
import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;
import edu.iastate.cs309.hb6.FoodTime.Pantry.PantryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;

@RestController

public class MealController {

    @Autowired
    UserRepository userDB;
    @Autowired
    private PantryRepository pantryRepository;

    @Autowired
    RecipeRepository recipeDB;
    @Autowired
    private MealRepository mealRepository;

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
        } else {
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
        } else {
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

    @GetMapping("recipe/compareIngredients")
    public ResponseEntity<Object> pantryHasIngredientsForMeal(@RequestParam String UID, @RequestParam String mealName) {

        if (!userDB.existsById(UID)) { //ensure user exists cause you can never be too careful
            return new ResponseEntity<>("user does not exist", HttpStatus.NOT_FOUND);
        }

        if(!userDB.findByUID(UID).getUserRecipes().containsKey(mealName)){
            return new ResponseEntity<>("meal does not exist", HttpStatus.NOT_FOUND);
        }

        ArrayList<Ingredient> userPantry = pantryRepository.findByUID(UID).getIngredientList();

        //uses a MAP to store a user's recipes. because why not.
        ArrayList<Ingredient> meal = userDB.findByUID(UID).getUserRecipes().get(mealName).getIngredients();

        for(int i = 0; i < userPantry.size(); i++){
            for(int j = 0; j < meal.size(); j++){

                //if ingredient name and quantity type match,
                if(meal.get(j).getName().equals(userPantry.get(i).getName()) &&
                        meal.get(j).getQuantityType().equals(userPantry.get(i).getQuantityType())){ //if names match

                    //if meal requires more ingredient than what the pantry has, save the difference between the two values in that ingredient in meal
                    if(meal.get(j).getQuantity() > userPantry.get(i).getQuantity()){

                        int diff =  userPantry.get(i).getQuantity() - meal.get(j).getQuantity(); //should be negative

                        meal.get(j).setQuantity(diff);

                    }else{ //you have enough ingredients for that ingredient, remove that ingredient from meal
                        //will return what's left of the meal object to tell front end what the pantry is missing
                        //any ingredients remaining in the meal object either dont have enough quantity to make, have the wrong type, or dont exist in the pantry

//                        meal.remove(meal.get(j).getName());
                          meal.remove(j);
                    }
                }
            }
        }

        if(meal.isEmpty()){
            return new ResponseEntity<>("can make meal", HttpStatus.OK);
        }else{
            //only things left in the meal object should be:
            // unit type mismatches
            // insufficient quantity of ingredients to make, in which the quantity value will be how much quantity is missing, should be negative
            //ingredients that are requested but not in the pantry at all
            return new ResponseEntity<>(meal, HttpStatus.I_AM_A_TEAPOT);
        }
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
