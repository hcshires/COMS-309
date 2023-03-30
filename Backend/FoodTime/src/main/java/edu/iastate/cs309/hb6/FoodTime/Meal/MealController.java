package edu.iastate.cs309.hb6.FoodTime.Meal;

import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class MealController {

    @Autowired
    MealRepository mealDB;

    @PutMapping("/meals/add")
    @Transactional
    public ResponseEntity<Object> addMeal(@RequestParam String UID, @RequestParam String day, @RequestBody Meal meal) {
        HashMap<String, Meal> mealsForDay = getUserMealsForDay(UID, day);
        mealsForDay.put(meal.getName(), meal);

        return new ResponseEntity<>(mealsForDay + day, HttpStatus.OK);
    }

    @DeleteMapping("/meals/remove")
    @Transactional
    public ResponseEntity<Object> removeMeal(@RequestParam String UID, @RequestParam String day, @RequestParam String mealName, @RequestParam boolean removeAll) {
        HashMap<String, Meal> mealsForDay = getUserMealsForDay(UID, day);

        //Remove just one meal for a day
        if (mealsForDay.remove(mealName) == null && !removeAll) {
            return new ResponseEntity<>(String.format("Meal not found on day %s for user %s", day, UID), HttpStatus.NOT_FOUND);
        }
        //Clear out all of user's meals for a day
        else if (removeAll) {
            MealList mealsForUser = mealDB.findByUID(UID);
            HashMap<String, Meal> emptyList = new HashMap<>();
            mealsForUser.setMealsForDay(day, emptyList);
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

    private HashMap<String, Meal> getUserMealsForDay (String UID, String day) {
        MealList mealsForUser = mealDB.findByUID(UID);
        return mealsForUser.getMealsForDay(day);
    }
}
