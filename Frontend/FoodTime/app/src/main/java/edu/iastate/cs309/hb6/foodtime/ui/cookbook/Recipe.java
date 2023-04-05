package edu.iastate.cs309.hb6.foodtime.ui.cookbook;


import java.util.ArrayList;

public class Recipe {
    private String title;
    private double cookTime;
    private int difficulty;

    private ArrayList<String> ingredients;

//    public Recipe(String rTitle, double rCookTime, int rDifficulty) {
//        rTitle = title;
//        rCookTime = cookTime;
//        rDifficulty = difficulty;
//    }

    public Recipe(String rTitle, ArrayList<String> rIngredients) {
        rTitle = title;
        ingredients = new ArrayList<>(6);
        rIngredients = ingredients;

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

    public ArrayList<String> getIngredients() {
        return ingredients;
    }


    public static ArrayList<Recipe> createRecipeList(int numRecipes){
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();
        ArrayList<String> ingredients = new ArrayList<>(5);

        for (int i = 1; i <= numRecipes; i++) {
            recipes.add(new Recipe("textTest1",ingredients));
        }

        return recipes;
    }
}
