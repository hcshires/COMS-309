package edu.iastate.cs309.hb6.FoodTime.Pantry;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryRepository extends JpaRepository<Pantry, String> {
    Pantry findByUID(String UID);
}
