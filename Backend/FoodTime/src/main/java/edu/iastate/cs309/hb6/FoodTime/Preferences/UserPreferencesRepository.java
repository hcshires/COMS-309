package edu.iastate.cs309.hb6.FoodTime.Preferences;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, UUID> {
    UserPreferences findByUID(UUID UID);
}
