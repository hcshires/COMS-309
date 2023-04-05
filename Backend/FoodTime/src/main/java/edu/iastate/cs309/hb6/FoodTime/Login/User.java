package edu.iastate.cs309.hb6.FoodTime.Login;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.*;
import java.util.*;

import edu.iastate.cs309.hb6.FoodTime.Meal.Meal;
import edu.iastate.cs309.hb6.FoodTime.Meal.MealList;
import edu.iastate.cs309.hb6.FoodTime.Pantry.*;
import edu.iastate.cs309.hb6.FoodTime.Preferences.UserPreferences;


@Entity
@Table(name = "users")
public class User {

    @Column (unique = true)
    private String username;

    @Column
    private String password;

    //This is a string because UUIDs do not play super nicely in the DB
    @Id
    @Column (unique = true)
    private String UID;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private UserPreferences userPreferences;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Pantry userPantry;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private MealList userMealsWeekly;

    @OneToMany(mappedBy = "meal_user", cascade = CascadeType.ALL)
    private Map<String, Meal> userRecipes;

    public User () {

    }

    @JsonCreator
    public User (String username, String password) {
        this.username = username;
        this.password = password;
        userRecipes = new HashMap<>();
    }

    public void assignUID() {
        UID = UUID.randomUUID().toString();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UUID getUID() {
        return java.util.UUID.fromString(UID);
    }

    public void setUserPreferences(UserPreferences userPreferences) {
        this.userPreferences = userPreferences;
    }

    public void setUserPantry(Pantry userPantry) {
        this.userPantry = userPantry;
    }

    public void setUserMeals(MealList userMeals) {
        this.userMealsWeekly = userMeals;
    }

    public UserPreferences getUserPreferences() {
        return userPreferences;
    }

    public Pantry getUserPantry() {
        return userPantry;
    }

    public MealList getUserMeals() {
        return userMealsWeekly;
    }

    public Map<String, Meal> getUserRecipes() {
        return userRecipes;
    }

    public void setUserRecipes(Map<String, Meal> userRecipes) {
        this.userRecipes = userRecipes;
    }
}
