/*
 * File: YahtzeeCategory.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Implements the Yahtzee category scoring, where the score
 * is 50 points if all five dice show the same value.
 * Course: CSC 335 Spring 2025
 */


package model.score;


public class YahtzeeCategory extends ScoreCategory {
	/*
     * Constructs a new YahtzeeCategory.
     */
    public YahtzeeCategory() {
        super("Yahtzee");
    }
    
    /*
     * This method calculates the score for the Yahtzee category by checking
     * if all five dice show the same value. Returns 50 points if a Yahtzee
     * is found, 0 otherwise.
     * 
     * Parameters: diceValues (type: int[]) -- array of dice values to score
     * 
     * Returns: int -- 50 if Yahtzee is found, 0 otherwise
     */
    @Override
    public int calculateScore(int[] diceValues) {
        int first = diceValues[0];
        for (int val : diceValues) {
            if (val != first) {
            	return 0;
            }
        }
        return 50;
    }
}