/*
 * File: FourOfAKindCategory.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Implements the Four of a Kind category scoring for Yahtzee, where the score
 * is the sum of all dice values if there are at least four dice showing the same value.
 * Course: CSC 335 Spring 2025
 */


package model.score;


public class FourOfAKindCategory extends ScoreCategory {
	/*
     * Constructs a new FourOfAKindCategory.
     */
    public FourOfAKindCategory() {
        super("Four of a Kind");
    }

    /*
     * This method calculates the score for the Four of a Kind category by checking
     * if there are at least four dice showing the same value. If found, returns
     * the sum of all dice values; otherwise returns 0.
     * 
     * Parameters: diceValues (type: int[]) -- array of dice values to score
     * 
     * Returns: int -- sum of all dice values if four of a kind is found, 0 otherwise
     */
    @Override
    public int calculateScore(int[] diceValues) {
    	int[] counts = new int[7];
        for (int val : diceValues) {
        	counts[val]++;
        }

        for (int i = 1; i <= 6; i++) {
            if (counts[i] >= 4) {
                int sum = 0;
                for (int val : diceValues) {
                	sum += val;
                }
                return sum;
            }
        }
        return 0;
    }
}
