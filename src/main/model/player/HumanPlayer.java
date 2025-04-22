/*
 * File: HumanPlayer.java
 * Authors: Jaden Gee + Kyle Dervin + Abe Didan + Nathan
 * Purpose: Represents a human-controlled player in the Yahtzee GUI.
 *          The GameView handles all interaction, so this class simply
 *          records scores based on selected input.
 * Course: CSC 335 Spring 2025
 */


package model.player;


public class HumanPlayer extends Player {

    /**
     * Constructs a HumanPlayer with the given name.
     * 
     * Parameters:
     *   name (type: String) -- the name of the player
     */
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * Records the player's selected score category.
     * The category is assumed to have been selected in the GUI.
     * 
     * Parameters:
     *   diceValues (type: int[]) -- the values of the dice rolled this turn
     * 
     * Returns: true if the score was successfully recorded, else false
     */
    @Override
    public boolean takeTurn(int[] diceValues) {
        return false;
    }
}