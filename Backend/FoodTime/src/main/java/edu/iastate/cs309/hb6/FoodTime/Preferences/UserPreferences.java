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
    private String UID;

    @Column(name = "darkMode")
    private boolean darkMode;

    //TODO: Add more preferences fields here as necessary

    public UserPreferences() {}

    //Default constructor will be used when a user is created
    public UserPreferences (UUID UID) {
        this.UID = UID.toString();
        darkMode = false;
    }

    //TODO: Update constructor as more prefs are added
    @JsonCreator
    public UserPreferences (UUID UID, boolean darkMode) {
        this.UID = UID.toString();
        this.darkMode = darkMode;
    }

    //TODO update as more prefs are added
    public void update(UserPreferences newPrefs) {
        this.darkMode = newPrefs.darkMode;
    }

    //All private fields need a getter in order to properly return the object as JSON in a response body
    public String getUID() {
        return UID;
    }

    public boolean getDarkMode() {
        return darkMode;
    }
}
