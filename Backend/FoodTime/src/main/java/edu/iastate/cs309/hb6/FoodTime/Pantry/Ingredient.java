package edu.iastate.cs309.hb6.FoodTime.Pantry;

/*
    @author Blake Hardy
 */

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.iastate.cs309.hb6.FoodTime.Login.User;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Ingredient implements Serializable { //no identifier for ingredient?

    private String name;

    //not sure yet if I want to do something with this
    //private IngredientType ingredientType;

    private int quantity; //holds units the ingredient is measured in

    private String quantityType; //what unit ingredient is measured in. not going to do anything fancy with this, would cause a lot of extra pain for front end

    //constructor

    public Ingredient(String name){
        this.name = name;
        //this.ingredientType = IngredientType.NONE; //not using so I'll keep it out so it doesn't clutter database
        this.quantity = 0;
        this.quantityType = "";
    }

    public Ingredient(){}

    //basic get/set methods

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQuantityType(){
        return quantityType;
    }

    public void setQuantityType(String str){
        this.quantityType = str;
    }

    public int getQuantity(){
        return quantity;
    }

    public void setQuantity(int newQuant){
        quantity = newQuant;
    }

}