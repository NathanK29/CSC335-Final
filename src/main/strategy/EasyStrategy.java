/*
 * File: EasyStrategy.java
 * Authors: Jaden Gee + Kyle Dervin + Abe Didan + Nathan
 * Purpose: Represents an implementation of the ComputerStrategy interface
 *          that selects a random available category from the scorecard.
 *          This strategy is used for the "easy" mode of the computer player.
 * Course: CSC 335 Spring 2025
 */


package main.strategy;

import model.score.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class EasyStrategy implements ComputerStrategy {
    
    private static final Random random = new Random();  // used to pick random score category

    /*
     * Chooses a category from the list of all unused score categories.
     * The computer doesn't try to maximize score — it just picks randomly.
     * 
     * Parameters: diceValues (type: int[]) -- a list of the integer values
     *             of the rolled dice (not actually used in this strategy)
     *             scoreCard (type: ScoreCard) -- the current scorecard for the computer player,
     *                                            which keeps track of used categories
     * 
     * Returns: the name (type: String) of a randomly chosen unused category
     */
    @Override
    public String chooseCategory(int[] diceValues, ScoreCard scoreCard) {
        List<String> available = new ArrayList<>();
        
        for (String category : ScoreCardCategories.ALL_CATEGORIES) {
            if (!scoreCard.isUsed(category)) {
                available.add(category);          // available is list of unused score categories
            }
        }
        return available.get(random.nextInt(available.size())); // randomly pick from available
    }

}