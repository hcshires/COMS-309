package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import java.util.ArrayList;
import java.util.HashMap;

public class Recipe {
    private String title;
    private double cookTime;
    private int difficulty;

    private ArrayList<HashMap<String,Object>> ingredients;

//    public Recipe(String rTitle, double rCookTime, int rDifficulty) {
//        rTitle = title;
//        rCookTime = cookTime;
//        rDifficulty = difficulty;
//    }

    public Recipe(String rTitle, ArrayList<HashMap<String,Object>> rIngredients) {
        title = rTitle;
        ingredients = rIngredients;
    }

    public String getTitle() {
        return title;
    }

    public double getCookTime() {
        return cookTime;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<HashMap<String,Object>> getIngredients() {
        return ingredients;
    }
}
