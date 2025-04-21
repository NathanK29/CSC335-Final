/*
 * File: NumberCategory.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Implements the number categories (Aces to Sixes) scoring for Yahtzee,
 * where the score is the sum of all dice showing the target number.
 * Course: CSC 335 Spring 2025
 */


package model.score;


/**
 * Category for scoring all dice showing a specific number (Aces to Sixes).
 */
public class NumberCategory extends ScoreCategory {
    private final int targetNumber;
    
    /*
     * Constructs a new NumberCategory for the specified target number.
     */
    public NumberCategory(int targetNumber) {
        super(getCategoryName(targetNumber));
        this.targetNumber = targetNumber;
    }
    
    /*
     * Helper method to get the category name based on the target number.
     * 
     * Parameters: number (type: int) -- the number to get the category name for
     * 
     * Returns: String -- the category name (Aces, Twos, etc.)
     */
    private static String getCategoryName(int number) {
        if (number == 1) {
        	return "Aces";
        }
        else if (number == 2) {
        	return "Twos";
        }
        else if (number == 3) {
        	return "Threes";
        }
        else if (number == 4) {
        	return "Fours";
        }
        else if (number == 5) {
        	return "Fives";
        }
        else if (number == 6) {
        	return "Sixes";
        }
        else {
        	throw new IllegalArgumentException("Invalid number category: " + number);
        }
    }
    
    /*
     * This method calculates the score for the number category by summing
     * all dice that show the target number.
     * 
     * Parameters: diceValues (type: int[]) -- array of dice values to score
     * 
     * Returns: int -- sum of all dice showing the target number
     */
    @Override
    public int calculateScore(int[] diceValues) {
        int score = 0;
        for (int val : diceValues) {
            if (val == targetNumber) {
                score += targetNumber;
            }
        }
        return score;
    }
}