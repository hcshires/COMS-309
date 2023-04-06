package edu.iastate.cs309.hb6.FoodTime.Pantry;

import com.fasterxml.jackson.annotation.JsonCreator;
import edu.iastate.cs309.hb6.FoodTime.Login.User;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Pantry {

    //wrapper class to make associating list of ingredients with a user easier
    //using an object for ingredients rather than just a string so we can add more data to it later

    //instance variables
    @Id
    @Column (unique = true)
    private String UID;

    @OneToOne(mappedBy = "userPantry")
    private User user;

    @Column
    private ArrayList<Ingredient> ingredientList;
    public Pantry(){

    }

    public Pantry(String UID) {
        ingredientList = new ArrayList<Ingredient>();
        this.UID = UID;
    }

    //basic methods
    public ArrayList<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public String[] getIngredientListString(){
        // converts ingredientlist into an array of strings of the ingredient's names
        String[] nameArr = new String[ingredientList.size()];
        for(int i = 0; i< ingredientList.size(); i++){
            nameArr[i] = ingredientList.get(i).getName();
        }
        return nameArr;
    }

    public Ingredient getIngredientByName(String name){

    for(int i = 0; i< ingredientList.size(); i++){
        if(ingredientList.get(i).getName().toLowerCase() == name.toLowerCase()){
            return ingredientList.get(i);
            }
        }

        return  null;
    }

    public boolean deleteIngredientByName(String name){

        for(int i = 0; i< ingredientList.size(); i++){
            //System.out.println(ingredientList.get(i).getName() + " " + name);
            if(ingredientList.get(i).getName().equals(name)){ //to prevent any problems
                ingredientList.remove(i);
                //System.out.println("Deleted item from ingredientList");
                return true;
            }
        }
        return false;
    }


    public void addIngredient(Ingredient ingredient){
        ingredientList.add(ingredient);
    }

    public void addIngredient(String name){
        ingredientList.add(new Ingredient(name));
    }


    public void setUser(User user) {
        this.user = user;
    }
}
