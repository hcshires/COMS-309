package edu.iastate.cs309.hb6.FoodTime.Meal;

import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
@Table(name = "recipe_book")
public class Recipe extends Meal {

    @Id
    @GeneratedValue
    private Long id;

    public Recipe() {

    }

    public Recipe(Meal meal) {
        super(meal.getName(), meal.getIngredients());
    }

    public Recipe(String name) {
        super(name);
    }

    public Recipe(String name, ArrayList<Ingredient> necessaryIngredients) {
        super(name, necessaryIngredients);
    }

}
