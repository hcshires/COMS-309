package edu.iastate.cs309.hb6.foodtime.ui.cookbook;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Recipe class
 * <p>
 * An object representing a recipe in the user's cookbook, containing a name, image, set of ingredients, and a set of directions.
 */
public class Recipe {
    /**
     * Title of the recipe
     */
    private String title;

    /**
     * Cook time of the recipe
     */
    private double cookTime;

    /**
     * Difficulty of the recipe
     */
    private int difficulty;

    /**
     * Ingredients of the recipe
     */
    private ArrayList<HashMap<String, Object>> ingredients;

    /**
     * Constructor for Recipe
     *
     * @param rTitle      title of the recipe
     * @param rCookTime   cook time of the recipe
     * @param rDifficulty difficulty of the recipe
     */
    public Recipe(String rTitle, double rCookTime, int rDifficulty) {
        rTitle = title;
        rCookTime = cookTime;
        rDifficulty = difficulty;
    }

    /**
     * Constructor for Recipe
     *
     * @param rTitle       title of the recipe
     * @param rIngredients ingredients of the recipe
     */
    public Recipe(String rTitle, ArrayList<HashMap<String, Object>> rIngredients) {
        title = rTitle;
        ingredients = rIngredients;
    }

    /**
     * Returns the title of the recipe
     *
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title of the recipe
     *
     * @param title title of the recipe
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Returns the cook time of the recipe
     *
     * @return cookTime
     */
    public double getCookTime() {
        return cookTime;
    }

    /**
     * Returns the difficulty of the recipe
     *
     * @return difficulty
     */
    public int getDifficulty() {
        return difficulty;
    }

    /**
     * Returns the list of ingredients for the recipe
     *
     * @return ingredients as an ArrayList
     */
    public ArrayList<HashMap<String, Object>> getIngredients() {
        return ingredients;
    }
}
