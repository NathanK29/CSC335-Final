/*
 * File: DiceSet.java
 * Authors: Jaden Gee + Kyle Dervin + Abe Didan + Nathan
 * Purpose: Represents a group of five dice used in the Yahtzee game.
 *          This class handles rolling dice, keeping selected ones,
 *          getting their values, and resetting them.
 * Course: CSC 335 Spring 2025
 */


package model.dice;

import java.util.Arrays;


public class DiceSet {
    private final Dice[] dice;
    private final boolean[] keep;

    public DiceSet() {
        dice = new Dice[5];
        keep = new boolean[5];
        for (int i = 0; i < 5; i++) {
            dice[i] = new Dice();
        }
    }

    /**
     * Rolls all dice that are not kept.
     */
    public void rollUnkeptDice() {
        for (int i = 0; i < 5; i++) {
            if (!keep[i]) {
                dice[i].roll();
            }
        }
    }

    /**
     * Gets the current values of the dice.
     * 
     * Returns: an array of integers representing each die's value
     */
    public int[] getDiceValues() {
        int[] values = new int[5];
        for (int i = 0; i < 5; i++) {
            values[i] = dice[i].getValue();
        }
        return values;
    }

    /**
     * Sets which dice to keep for the next roll based on an input list
     * of boolean values specifying the new keeps (true) vs rolls (false).
     */
    public void setKeepStatus(boolean[] updatedKeep) {
        if (updatedKeep.length == 5) {
            for (int i = 0; i < 5; i++) {
            	keep[i] = updatedKeep[i];
            }
        }
    }
    
    
    /**
     * Returns a copy of the current keep status.
     */
    public boolean[] getKeepStatus() {
        return Arrays.copyOf(keep, 5);       // to avoid escaping reference
    }

    /**
     * Resets all the keep status of each dice to false.
     */
    public void resetKeep() {
        for (int i = 0; i < 5; i++) {
        	keep[i] = false;
        }
    }
} 