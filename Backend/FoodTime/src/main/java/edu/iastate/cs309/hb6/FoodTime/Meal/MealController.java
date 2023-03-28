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
    public ResponseEntity<Object> removeMeal(@RequestParam String UID, @RequestParam String day, @RequestParam String mealName) {
        HashMap<String, Meal> mealsForDay = getUserMealsForDay(UID, day);

        if (mealsForDay.remove(mealName) == null) {
            return new ResponseEntity<>("Meal name not found for UID on given day", HttpStatus.NOT_FOUND);
        }
        else {
           return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("/meals/update")
    @Transactional
    public ResponseEntity<Object> updateMeal(@RequestParam String UID, @RequestParam String day, @RequestParam String mealName, @RequestBody Meal newMeal) {
        HashMap<String, Meal> mealsForDay = getUserMealsForDay(UID, day);

        if (mealsForDay.containsKey(mealName)) {
            mealsForDay.replace(mealName, newMeal);
            return new ResponseEntity<>(mealsForDay.get(mealName), HttpStatus.OK);
        }
    }

    @PutMapping("/meals/add/test")
    @ResponseBody
    public Meal addMeal() {
        Meal addMe = new Meal("test meal");
        Ingredient ingredient = new Ingredient("beans");
        addMe.addIngredient(ingredient);
        ingredient = new Ingredient("lettuce");
        addMe.addIngredient(ingredient);

        System.out.println(addMe.getIngredients().toString());

        return addMe;
    }

    private HashMap<String, Meal> getUserMealsForDay (String UID, String day) {
        MealList mealsForUser = mealDB.findByUID(UID);
        return mealsForUser.getMealsForDay(day);
    }
}
