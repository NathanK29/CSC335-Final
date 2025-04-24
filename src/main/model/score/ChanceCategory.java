/*
 * File: ChanceCategory.java
 * Authors: Jaden + Kyle + Nathan + Abe
 * Purpose: Implements the Chance category scoring for Yahtzee, where the score
 * is the sum of all dice values.
 * Course: CSC 335 Spring 2025
 */

package model.score;

public class ChanceCategory extends ScoreCategory {
	/*
     * Constructs a new ChanceCategory.
     */
    public ChanceCategory() {
        super("Chance");
    }

    /*
     * This method calculates the score for the Chance category by summing
     * all the dice values. This is the simplest scoring category as it
     * doesn't require any specific dice combinations.
     * 
     * Parameters: diceValues (type: int[]) -- array of dice values to score
     * 
     * Returns: int -- the sum of all dice values
     */
    @Override
    public int calculateScore(int[] diceValues) {
        int sum = 0;
        for (int diceValue : diceValues) {
        	sum += diceValue;
        }
        return sum;
    }
}