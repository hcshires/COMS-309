package edu.iastate.cs309.hb6.FoodTime.Pantry;

/*
    @author Blake Hardy
 */

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class Ingredient implements Serializable { //no identifier for ingredient?

    private String name;

    private int quantity; //holds units the ingredient is measured in

    private String quantityType; //what unit ingredient is measured in. not going to do anything fancy with this, would cause a lot of extra pain for front end

    //constructor

    public Ingredient(String name){
        this.name = name;
        this.quantity = 0;
        this.quantityType = "typeless";
    }

    public Ingredient(String name, int quantity, String unitsType){
        this.name = name;
        this.quantity = quantity;
        this.quantityType = unitsType;
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
        this.quantity = newQuant;
    }

}