/*
 * File: ComputerStrategy.java
 * Authors: Jaden Gee + Kyle Dervin + Abe Didan + Nathan
 * Purpose: Represents an interface for the automatic computer player's
 *          strategy. There is one method which will return a strategy based
 *          on the current dice values and the computer player's scorecard.
 * Course: CSC 335 Spring 2025
 */


package main.strategy;

import model.score.*;


public interface ComputerStrategy {
    
	/*
     * The computer player chooses which ScoreCategory to use based
     * on the current dice values and the computer player's current scoreCard.
     * 
     * Parameters: diceValues (type: int[]) -- a list of the integer values
     *             of the rolled dice
     *             scoreCard (type: ScoreCard) - a scoreCard containing all the
     *             possible score categories and which ones have been used
     * 
     * Returns: the name of the selected ScoreCategory as a String
     */
    String chooseCategory(int[] diceValues, ScoreCard scoreCard);
}