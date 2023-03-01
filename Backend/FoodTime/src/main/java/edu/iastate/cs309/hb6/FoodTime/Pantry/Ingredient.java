package edu.iastate.cs309.hb6.FoodTime.Pantry;

/*
    @author Blake Hardy
 */

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.iastate.cs309.hb6.FoodTime.Login.User;

import javax.persistence.*;

public class Ingredient { //no identifier for ingredient?

    private String name;

    //maybe add some sort of catagory system? fruit, veg, starch, meat, etc? can do later.

    //constructor

    public Ingredient(String name){
        this.name = name;
    }

    public Ingredient(){}

    //basic get/set methods

    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}