package edu.iastate.cs309.hb6.FoodTime.Login;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.*;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import edu.iastate.cs309.hb6.FoodTime.Meal.Meal;
import edu.iastate.cs309.hb6.FoodTime.Meal.MealList;
import edu.iastate.cs309.hb6.FoodTime.Meal.Recipe;
import edu.iastate.cs309.hb6.FoodTime.Pantry.*;
import edu.iastate.cs309.hb6.FoodTime.Preferences.UserPreferences;
import org.hibernate.annotations.Type;


@Entity
@Table(name = "users")
public class User {
    public enum AccessLevel {
        CHILD,
        PARENT
    }

    @Column (unique = true)
    private String username;

    @Column
    private String password;

    @Column
    private AccessLevel accessLevel;

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

    @OneToOne(fetch = FetchType.EAGER)
    private User parentUser;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "recipe_book_uid")
    private Map<String, Recipe> userRecipes;

    @Column(name = "recipe_labels")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private ArrayList<String> recipeLabels;

    public User () {

    }

    @JsonCreator
    public User (String username, String password, AccessLevel accessLevel) {
        this.username = username;
        this.password = password;
        this.accessLevel = accessLevel;
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

    public Map<String, Recipe> getUserRecipes() {
        return userRecipes;
    }

    public void setUserRecipes(Map<String, Recipe> userRecipes) {
        this.userRecipes = userRecipes;
    }

    public ArrayList<String> getRecipeLabels() {
        return recipeLabels;
    }

    public void setRecipeLabels(ArrayList<String> recipeLabels) {
        this.recipeLabels = recipeLabels;
    }

    public AccessLevel getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(AccessLevel accessLevel) {
        this.accessLevel = accessLevel;
    }

    public User getParentUser() {
        return parentUser;
    }

    public void setParentUser(User parentUser) {
        this.parentUser = parentUser;
    }
}
