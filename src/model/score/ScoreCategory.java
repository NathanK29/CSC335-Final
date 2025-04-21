/*
 * File: ScoreCategory.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Abstract base class for all scoring categories in Yahtzee,
 * defining the common interface for calculating scores.
 * Course: CSC 335 Spring 2025
 */


package model.score;


public abstract class ScoreCategory {
    private final String name;
    
    /*
     * Constructs a new ScoreCategory with the given name.
     */
    public ScoreCategory(String name) {
        this.name = name;
    }

    /*
     * Gets the name of this category.
     * 
     * Parameters: none
     * 
     * Returns: String -- the category name
     */
    public String getName() {
        return name;
    }

    /*
     * Abstract method to calculate the score for a given set of dice values.
     * Must be implemented by concrete category classes.
     * 
     * Parameters: diceValues (type: int[]) -- array of dice values to score
     * 
     * Returns: int -- the calculated score
     */
    public abstract int calculateScore(int[] diceValues);
}