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

import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;

@RestController

public class MealController {

    @Autowired
    UserRepository userDB;
    @Autowired
    private PantryRepository pantryRepository;

    @PutMapping("/meals/add")
    @Transactional
    public ResponseEntity<Object> addMeal(@RequestParam String UID, @RequestParam String day, @RequestBody Meal meal) {
        HashMap<String, Meal> mealsForDay = getUserMealsForDay(UID, day);
        mealsForDay.put(meal.getName(), meal);

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

    @GetMapping("meals/enoughForMeal") //der UberController
    public ResponseEntity<Object> pantryHasIngredientsForMeal(@RequestParam String userID, @RequestBody Meal meal) {

        if (!userDB.existsById(userID)) { //ensure user exists cause you can never be too careful
            return new ResponseEntity<>("user does not exist", HttpStatus.NOT_FOUND);
        }

        ArrayList<Ingredient> userPantry = pantryRepository.findByUID(userID).getIngredientList();


        for(int i = 0; i < userPantry.size(); i++){
            for(int j = 0; j < meal.getIngredients().size(); j++){

                //if ingredient name and quantity type match,
                if(meal.getIngredients().get(j).getName().equals(userPantry.get(i).getName()) &&
                        meal.getIngredients().get(j).getQuantityType().equals(userPantry.get(i).getQuantityType())){ //if names match

                    //if meal requires more ingredient than what the pantry has, save the difference between the two values in that ingredient in meal
                    if(meal.getIngredients().get(j).getQuantity() > userPantry.get(i).getQuantity()){

                        int diff =  userPantry.get(i).getQuantity() - meal.getIngredients().get(j).getQuantity(); //should be negative

                        meal.getIngredients().get(j).setQuantity(diff);

                    }else{ //you have enough ingredients for that ingredient, remove that ingredient from meal
                        //will return what's left of the meal object to tell front end what the pantry is missing
                        //any ingredients remaining in the meal object either dont have enough quantity to make, have the wrong type, or dont exist in the pantry

                        meal.removeIngredient(meal.getIngredients().get(j).getName());
                    }
                }
            }
        }


        if(meal.getIngredients().isEmpty()){
            return new ResponseEntity<>("can make meal", HttpStatus.OK);
        }else{
            //only things left in the meal object should be:
            // type mismatches
            // insufficient quantity of ingredients to make, in which the quantity value will be how much quantity is missing, will be negative
            //ingredients that are requested but not in the pantry at all
            return new ResponseEntity<>(meal, HttpStatus.I_AM_A_TEAPOT);
        }
    }



    private HashMap<String, Meal> getUserMealsForDay (String UID, String day) {
        MealList mealsForUser = userDB.findByUID(UID).getUserMeals();
        return mealsForUser.getMealsForDay(day.toLowerCase());
    }
}
