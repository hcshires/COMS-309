package edu.iastate.cs309.hb6.FoodTime.Meal;

import edu.iastate.cs309.hb6.FoodTime.Pantry.Ingredient;
import org.hibernate.annotations.Type;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.UUID;


@Component
class Meal {
    private String name;
    //HashMap so that we can easily add and remove ingredients
    private HashMap<String, Ingredient> necessaryIngredients;

    public Meal () {

    }

    public Meal (String name, HashMap<String, Ingredient> necessaryIngredients) {
        this.name = name;
        this.necessaryIngredients = necessaryIngredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addIngredient(Ingredient ingredient) {
        necessaryIngredients.putIfAbsent(ingredient.getName(), ingredient);
    }

    public void removeIngredient(String ingredientName) {
        necessaryIngredients.remove(ingredientName);
    }
}

@Entity
@Table(name = "meal_list")
public class MealList {

    @Id
    @Column(unique = true)
    private String UID;

    //Each hash map will contain a number of meals that the user wishes to make on that day
    @Column (name = "sunday")
    @Type( type = "json" )
    private HashMap<String, Meal> sunday;
    @Column (name = "monday")
    @Type( type = "json" )
    private HashMap<String, Meal> monday;
    @Column (name = "tuesday")
    @Type( type = "json" )
    private HashMap<String, Meal> tuesday;
    @Column (name = "wednesday")
    @Type( type = "json" )
    private HashMap<String, Meal> wednesday;
    @Column (name = "thursday")
    @Type( type = "json" )
    private HashMap<String, Meal> thursday;
    @Column (name = "friday")
    @Type( type = "json" )
    private HashMap<String, Meal> friday;
    @Column (name = "saturday")
    @Type( type = "json" )
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
}
