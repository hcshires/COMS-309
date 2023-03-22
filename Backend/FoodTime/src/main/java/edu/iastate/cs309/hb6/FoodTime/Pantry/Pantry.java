package edu.iastate.cs309.hb6.FoodTime.Pantry;

import com.fasterxml.jackson.annotation.JsonCreator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.ArrayList;

@Entity
public class Pantry {

    //wrapper class to make associating list of ingredients with a user easier
    //using an object for ingredients rather than just a string so we can add more data to it later

    //instance variables
    @Id
    @Column (unique = true)
    private String UID;

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

    public int getQuantity(String name){

        return getIngredientByName(name).getQuantity();

    }

    public void setQuantity(String name, int num){
        ingredientList.get(findIngredientIndex(name)).setQuantity(num);
    }

    public String getQuantityType(String name){
        return ingredientList.get(findIngredientIndex(name)).getQuantityType();
    }

    public void setQuantityType(String name, String quantityType){
        ingredientList.get(findIngredientIndex(name)).setQuantityType(quantityType);
    }

    //utility. might make private
    public boolean hasIngredient(String name){
        for(int i = 0; i< ingredientList.size(); i++){
            if(ingredientList.get(i).getName().equals(name)){ //to prevent any problems
                return true;
            }
        }
        return false;
    }

    //utility. might make private
    public int findIngredientIndex(String name) {
        for (int i = 0; i < ingredientList.size(); i++) {
            if (ingredientList.get(i).getName().equals(name)) { //to prevent any problems
                return i;
            }
        }
        return -1; //will throw error down the line
    }

}
