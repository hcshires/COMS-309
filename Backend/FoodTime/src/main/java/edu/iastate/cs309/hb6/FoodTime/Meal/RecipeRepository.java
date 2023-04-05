package edu.iastate.cs309.hb6.FoodTime.Meal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<Meal, String> {
}
