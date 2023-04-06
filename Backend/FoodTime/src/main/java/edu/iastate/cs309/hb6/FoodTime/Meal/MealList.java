package edu.iastate.cs309.hb6.FoodTime.Meal;

import com.fasterxml.jackson.annotation.JsonCreator;
import edu.iastate.cs309.hb6.FoodTime.Login.User;
import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


class Meal implements Serializable {
    private String name;
    //HashMap so that we can easily add and remove ingredients
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private ArrayList<Ingredient> necessaryIngredients;

    public Meal () {

    }

    public Meal (String name) {
        this.name = name;
        necessaryIngredients = new ArrayList<>();
    }

    @JsonCreator
    public Meal (String name, ArrayList<Ingredient> necessaryIngredients) {
        this.name = name;
        this.necessaryIngredients = necessaryIngredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return necessaryIngredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        necessaryIngredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        necessaryIngredients.add(ingredient);
    }

    public void setQuantityRequired(String ingredientName, int quantity) {
        Ingredient ing = findIngredient(ingredientName);
        //TODO Blake should implement a quantity metric in Ingredient
        //itemToUpdate.setQuantity(quantity);





    }

    public void removeIngredient(String ingredientName) {
        necessaryIngredients.remove(findIngredientIndex(ingredientName));
    }
    
    private Ingredient findIngredient(String ingredientName) {
        for (Ingredient i : necessaryIngredients) {
            if (i.getName().equals(ingredientName)) {
                return i;
            }
        }
        return null;
    }

    private int findIngredientIndex (String ingredientName) {
        for (Ingredient i : necessaryIngredients) {
            if (i.getName().equals(ingredientName)) {
                return necessaryIngredients.indexOf(i);
            }
        }
        return -1;
    }
}

@Entity
@Table(name = "meal_list")
public class MealList {

    @Id
    @Column(unique = true)
    private String UID;

    @OneToOne(mappedBy = "userMeals")
    private User user;

    //Each hash map will contain a number of meals that the user wishes to make on that day
    @Column (name = "sunday")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> sunday;
    @Column (name = "monday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> monday;
    @Column (name = "tuesday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> tuesday;
    @Column (name = "wednesday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> wednesday;
    @Column (name = "thursday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> thursday;
    @Column (name = "friday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> friday;
    @Column (name = "saturday", columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Meal> saturday;

    public MealList () {

    }

    public MealList (UUID UID) {
        this.UID = UID.toString();
        sunday = new HashMap<>();
        monday = new HashMap<>();
        tuesday = new HashMap<>();
        wednesday = new HashMap<>();
        thursday = new HashMap<>();
        friday = new HashMap<>();
        saturday = new HashMap<>();
    }

    public HashMap<String, Meal> getMealsForDay(String day) {
        switch (day) {
            case "sunday": return sunday;
            case "monday": return monday;
            case "tuesday": return tuesday;
            case "wednesday": return wednesday;
            case "thursday": return thursday;
            case "friday": return friday;
            case "saturday": return saturday;
            default: return null;
        }
    }

    public void setMealsForDay(String day, HashMap<String, Meal> newList) {
        switch (day) {
            case "sunday": sunday = newList;
            case "monday": monday = newList;
            case "tuesday": tuesday = newList;
            case "wednesday": wednesday = newList;
            case "thursday": thursday = newList;
            case "friday": friday = newList;
            case "saturday": saturday = newList;
        }
    }

    public HashMap<String, Meal> getSunday() {
        return sunday;
    }

    public HashMap<String, Meal> getMonday() {
        return monday;
    }

    public HashMap<String, Meal> getTuesday() {
        return tuesday;
    }

    public HashMap<String, Meal> getWednesday() {
        return wednesday;
    }

    public HashMap<String, Meal> getThursday() {
        return thursday;
    }

    public HashMap<String, Meal> getFriday() {
        return friday;
    }

    public HashMap<String, Meal> getSaturday() {
        return saturday;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
