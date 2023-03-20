package edu.iastate.cs309.hb6.FoodTime.Meal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<MealList, String> {
    MealList findByUID(String UID);
}
