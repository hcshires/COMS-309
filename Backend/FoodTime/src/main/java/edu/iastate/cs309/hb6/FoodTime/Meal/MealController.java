package edu.iastate.cs309.hb6.FoodTime.Meal;

import edu.iastate.cs309.hb6.FoodTime.Login.UserRepository;
import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class MealController {

    @Autowired
    UserRepository userDB;

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

    @GetMapping("meals/enoughForMeal") //behold der UberController
    public ResponseEntity<Object> pantryHasIngredientsForMeal(@RequestParam String userID, @RequestParam Meal meal){

        if(userDB.existsById(userID)){ //ensure user exists cause you can never be too careful

            ArrayList<Ingredient> insufficientQuantity = null;

            //this is gonna be ugly and inefficient
            for(int i = 0; i < userDB.findByUID(userID).getUserPantry().getIngredientList().size(); i++){ //iterate through user's pantry
                for(int j = 0; j < meal.getIngredients().size(); j++){

                    //may need front end to handle type coversions, or at least not leave it up to the user cause that's dangerous
                    //check if ingredient names match, amount required by meal is less than what is in the pantry, and if the quantity types match
                    //this brings me joy
                    if(meal.getIngredients().get(j).getName().equals(userDB.findByUID(userID).getUserPantry().getIngredientList().get(i).getName()) &
                            meal.getIngredients().get(j).getQuantity() <= userDB.findByUID(userID).getUserPantry().getIngredientList().get(i).getQuantity() &
                            meal.getIngredients().get(j).getQuantityType().equals(userDB.findByUID(userID).getUserPantry().getIngredientList().get(i).getQuantityType())
                    ){

                        //do nothing

                    }else{

                        insufficientQuantity.add(meal.getIngredients().get(j));
                        //pantry doesn't have enough, add it to the list
                        //hopefully no duplicates
                    }
                }
            }

            if(insufficientQuantity == null){ //pantry has enough ingredients for everyone
                //return okay
                return new ResponseEntity<>("enougn ingredients in user's pantry to make meal", HttpStatus.OK);

            }else{
                //return list of ingredients that pantry doesn't have enough
                return new ResponseEntity<>(insufficientQuantity, HttpStatus.I_AM_A_TEAPOT); //no good HTTP status for "not enough thing"
            }
        }
        return new ResponseEntity<>("user does not exist", HttpStatus.NOT_FOUND);
    }

    private HashMap<String, Meal> getUserMealsForDay (String UID, String day) {
        MealList mealsForUser = userDB.findByUID(UID).getUserMeals();
        return mealsForUser.getMealsForDay(day.toLowerCase());
    }
}
