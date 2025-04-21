/*
 * File: SmallStraightCategory.java
 * Authors: Jaden + Kyle + Nathan + Abe
 * Purpose: Implements the Small Straight category scoring for Yahtzee, where the score
 * is 30 points if there are four consecutive dice values (1-4, 2-5, or 3-6).
 * Course: CSC 335 Spring 2025
 */


package model.score;


public class SmallStraightCategory extends ScoreCategory {
	/*
     * Constructs a new SmallStraightCategory.
     */
    public SmallStraightCategory() {
        super("Small Straight");
    }
    
    /*
     * This method calculates the score for the Small Straight category by checking
     * if there are four consecutive dice values (1-4, 2-5, or 3-6).
     * Returns 30 points if a small straight is found, 0 otherwise.
     * 
     * Parameters: diceValues (type: int[]) -- array of dice values to score
     * 
     * Returns: int -- 30 if small straight is found, 0 otherwise
     */
    @Override
    public int calculateScore(int[] diceValues) {
    	boolean[] present = new boolean[7];
        for (int val : diceValues) {
        	present[val] = true;
        }

        if ((present[1] && present[2] && present[3] && present[4]) ||
            (present[2] && present[3] && present[4] && present[5]) ||
            (present[3] && present[4] && present[5] && present[6])) {
            return 30;
        } else {
        	return 0;
        }
    }
}