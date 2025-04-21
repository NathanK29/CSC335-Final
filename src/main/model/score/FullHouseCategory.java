/*
 * File: FullHouseCategory.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Implements the Full House category scoring for Yahtzee, where the score
 * is 25 points if there are three dice of one value and two dice of another value.
 * Course: CSC 335 Spring 2025
 */


package model.score;


public class FullHouseCategory extends ScoreCategory {
	/*
     * Constructs a new FullHouseCategory.
     */
    public FullHouseCategory() {
        super("Full House");
    }
    
    /*
     * This method calculates the score for the Full House category by checking
     * if there are three dice of one value and two dice of another value.
     * Returns 25 points if a full house is found, 0 otherwise.
     * 
     * Parameters: diceValues (type: int[]) -- array of dice values to score
     * 
     * Returns: int -- 25 if full house is found, 0 otherwise
     */
    @Override
    public int calculateScore(int[] diceValues) {
    	int[] counts = new int[7];
        for (int val : diceValues) {
        	counts[val]++;
        }

        boolean hasThree = false;
        boolean hasTwo = false;

        for (int count : counts) {
            if (count == 3) {
            	hasThree = true;
            }
            else if (count == 2) {
            	hasTwo = true;
            }
        }
        if (hasThree && hasTwo) {
        	return 25;
        } else {
        	return 0;
        }
    }
}