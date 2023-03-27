package edu.iastate.cs309.hb6.foodtime.ui.cookbook;


import java.util.ArrayList;

public class Recipe {
    private String title;
    private double cookTime;
    private int difficulty;
    private String textTest1;
    private String textTest2;

    //public Recipe(String rTitle, double rCookTime, int rDifficulty) {
        //rTitle = title;
        //rCookTime = cookTime;
        //rDifficulty = difficulty;
    //}

    public Recipe(String test1, String test2){
        test1 = textTest1;
        test2 = textTest2;
    }

    public String getTextTest1() {
        return textTest1;
    }

    public String getTextTest2() {
        return textTest2;
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


    public static ArrayList<Recipe> createRecipeList(int numRecipes){
        ArrayList<Recipe> recipes = new ArrayList<Recipe>();

        for (int i = 1; i <= numRecipes; i++) {
            recipes.add(new Recipe("textTest1", "textTest2"));
        }

        return recipes;
    }
}
