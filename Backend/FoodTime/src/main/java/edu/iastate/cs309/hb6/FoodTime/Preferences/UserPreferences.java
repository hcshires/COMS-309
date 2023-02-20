package edu.iastate.cs309.hb6.FoodTime.Preferences;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "preferences")
/*
  Adding preference options to this class is straightforward. Add the following:

  At the top in class members:
  @Column
  private [type] [newPreferenceName]

  To the default constructor (second constructor, labeled), initialize the value that will be used when the user is first created
  e.g:
  [newPreferenceName] = [defaultValueForPref]

  And to the last constructor (labeled @JsonCreator), add a parameter to the declaration, and assign its value to the class member in the body.

  Then, to the update() method, add your new preference option:
  this.[newPreferenceName] = newPrefs.[newPreferenceName]
 */
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
