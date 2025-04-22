/*
 * File: Player.java
 * Authors: Jaden Gee + Kyle Dervin + Abe Didan + Nathan
 * Purpose: Represents a abstract class for a Yahtzee player. The
 *          player can be human or a computer. Each player has a name
 *          and a scorecard, getters for each, and a method to make
 *          their next move.
 * Course: CSC 335 Spring 2025
 */


package model.player;
import model.score.*;


public abstract class Player {
    protected final String name;          // protected so subclasses have access
    protected final ScoreCard scoreCard;  // final so the references cannot change once assigned

    public Player(String name) {
        this.name = name;
        this.scoreCard = new ScoreCard();
    }

    public String getName() {
        return name;
    }

    public ScoreCard getScoreCard() {
        return scoreCard;
    }
    
    /*
     * Player chooses which category to use based on the current
     * dice that were rolled. If the score is recorded, returns
     * true, else false.
     * 
     * Parameters: diceValues (type: int[]) -- a list of the integer values
     *             of the rolled dice
     * 
     * Returns: true if score is recorded, else false
     */
    public abstract boolean takeTurn(int[] diceValues);
}
