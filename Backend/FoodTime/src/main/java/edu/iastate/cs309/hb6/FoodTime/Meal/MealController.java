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
        MealList mealsForUser = mealDB.findByUID(UID);
        HashMap<String, Meal> mealsForDay = mealsForUser.getMealsForDay(day);
        mealsForDay.put(meal.getName(), meal);

        return new ResponseEntity<>(mealsForDay + day, HttpStatus.OK);
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
}
