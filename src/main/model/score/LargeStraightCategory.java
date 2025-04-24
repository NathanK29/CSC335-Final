/*
 * File: LargeStraightCategory.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Implements the Large Straight category scoring for Yahtzee, where the score
 * is 40 points if there are five consecutive dice values (1-5 or 2-6).
 * Course: CSC 335 Spring 2025
 */

package model.score;

public class LargeStraightCategory extends ScoreCategory {
	/*
     * Constructs a new LargeStraightCategory.
     */
    public LargeStraightCategory() {
        super("Large Straight");
    }
    
    /*
     * This method calculates the score for the Large Straight category by checking
     * if there are five consecutive dice values (either 1-5 or 2-6).
     * Returns 40 points if a large straight is found, 0 otherwise.
     * 
     * Parameters: diceValues (type: int[]) -- array of dice values to score
     * 
     * Returns: int -- 40 if large straight is found, 0 otherwise
     */
    @Override
    public int calculateScore(int[] diceValues) {
        boolean[] present = new boolean[7];
        for (int val : diceValues) {
            present[val] = true;
        }
        if ((present[1] && present[2] && present[3] && present[4] && present[5]) ||
            (present[2] && present[3] && present[4] && present[5] && present[6])) {
            return 40;
        } else {
        	return 0;
        }
    }
}