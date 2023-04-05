package edu.iastate.cs309.hb6.FoodTime.Meal;

import com.fasterxml.jackson.annotation.JsonCreator;
import edu.iastate.cs309.hb6.FoodTime.Login.User;
import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashMap;

@Entity
@Table(name = "recipe_book")
//TODO use this class for weekly meals (no user) then extend to Recipe class
public class Meal implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "meal_id")
    private Long id;

    private String name;

    //HashMap so that we can easily add and remove ingredients
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private HashMap<String, Ingredient> necessaryIngredients;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "recipes_user_uid")
    private User meal_user;

    public User getUser() {
        return meal_user;
    }

    public void setUser(User user) {
        this.meal_user = user;
    }

    public Meal() {

    }

    public Meal(String name) {
        this.name = name;
        necessaryIngredients = new HashMap<>();
    }

    @JsonCreator
    public Meal(String name, HashMap<String, Ingredient> necessaryIngredients) {
        this.name = name;
        this.necessaryIngredients = necessaryIngredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Ingredient> getIngredients() {
        return necessaryIngredients;
    }

    public void setIngredients(HashMap<String, Ingredient> ingredients) {
        necessaryIngredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        necessaryIngredients.putIfAbsent(ingredient.getName(), ingredient);
    }

    public void setQuantityRequired(String ingredientName, int quantity) {
        Ingredient itemToUpdate = necessaryIngredients.get(ingredientName);
        //TODO Blake should implement a quantity metric in Ingredient
        //itemToUpdate.setQuantity(quantity);
    }

    public void removeIngredient(String ingredientName) {
        necessaryIngredients.remove(ingredientName);
    }

    public User getMeal_user() {
        return meal_user;
    }

    public void setMeal_user(User meal_user) {
        this.meal_user = meal_user;
    }
}
