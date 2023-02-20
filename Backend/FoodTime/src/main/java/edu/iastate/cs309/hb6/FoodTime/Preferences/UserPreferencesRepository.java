package edu.iastate.cs309.hb6.FoodTime.Preferences;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPreferencesRepository extends JpaRepository<UserPreferences, String> {
    UserPreferences findByUID(String UID);
}
