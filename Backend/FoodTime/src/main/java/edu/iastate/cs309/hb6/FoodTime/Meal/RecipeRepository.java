package edu.iastate.cs309.hb6.FoodTime.Meal;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface RecipeRepository extends JpaRepository<Recipe, String> {
}
