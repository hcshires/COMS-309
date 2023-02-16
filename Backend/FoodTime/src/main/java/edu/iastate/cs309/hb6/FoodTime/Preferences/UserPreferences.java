package edu.iastate.cs309.hb6.FoodTime.Preferences;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "preferences")
public class UserPreferences {

    @Id
    @Column(unique = true)
    private UUID UID;

    @Column
    private boolean darkMode;

    //TODO: Add more preferences fields here as necessary

    public UserPreferences() {}

    //Default constructor will be used when a user is created
    public UserPreferences (UUID UID) {
        this.UID = UID;
        darkMode = false;
    }

    //TODO: Update constructor as more prefs are added
    @JsonCreator
    public UserPreferences (UUID UID, boolean darkMode) {
        this.UID = UID;
        this.darkMode = darkMode;
    }

    //TODO update as more prefs are added
    public void update(UserPreferences newPrefs) {
        this.darkMode = newPrefs.darkMode;
    }

    public UUID getUID() {
        return UID;
    }
}
