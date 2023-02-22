package edu.iastate.cs309.hb6.FoodTime.Pantry;

/*
    @author Blake Hardy

 */

import org.springframework.data.jpa.repository.JpaRepository;

public interface PantryRepository extends JpaRepository<Pantry, Long> {

    Pantry findById(int id);

    void deleteById(int id);
}
