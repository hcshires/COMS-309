package edu.iastate.cs309.hb6.FoodTime.Meal;

import com.fasterxml.jackson.annotation.JsonCreator;
import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;


//TODO use this class for weekly meals (no user) then extend to Recipe class
@Entity
@Table(name = "DISREGARD_ME_IGNORE")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Meal implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    //HashMap so that we can easily add and remove ingredients
    @Column(columnDefinition = "json")
    @Type(type = "io.hypersistence.utils.hibernate.type.json.JsonStringType")
    private ArrayList<Ingredient> necessaryIngredients;

    public Meal() {

    }

    public Meal(String name) {
        this.name = name;
        necessaryIngredients = new ArrayList<>();
    }

    @JsonCreator
    public Meal(String name, ArrayList<Ingredient> necessaryIngredients) {
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
