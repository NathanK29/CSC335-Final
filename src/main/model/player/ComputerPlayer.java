/*
 * File: ComputerPlayer.java
 * Authors: Jaden Gee + Kyle Dervin + Abe Didan + Nathan
 * Purpose: Represents an extension of the Player class specifically for
 *          a computer/AI player. The computer has a strategy instance
 *          variable (either easy or hard), and its takeTurn() method
 *          depends on which strategy the computer has.
 * Course: CSC 335 Spring 2025
 */


package model.player;

import flyweight.*;
import strategy.*;
import model.score.*;


public class ComputerPlayer extends Player {
    private final ComputerStrategy strategy;

    public ComputerPlayer(String name, ComputerStrategy strategy) {
        super(name);
        this.strategy = strategy;
    }
    
    public ComputerStrategy getStrategy() {
        return strategy;
    }
 
    /*
     * The computer player chooses which ScoreCategory to use based
     * on its strategy. It then calculates the given score of the current dice
     * and the chosen ScoreCateogry.
     * 
     * Parameters: diceValues (type: int[]) -- a list of the integer values
     *             of the rolled dice
     * 
     * Returns: true if score is successfully recorded, false if the chosen
     *          ScoreCategory has already been used
     */
    @Override
    public boolean takeTurn(int[] diceValues) {
        String categoryName = strategy.chooseCategory(diceValues, scoreCard);
        ScoreCategory category = ScoreCategoryStore.getCategory(categoryName);
        return scoreCard.recordScore(category, diceValues);
    }
}