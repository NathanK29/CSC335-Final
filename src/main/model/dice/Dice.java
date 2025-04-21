/*
 * File: Dice.java
 * Authors: Jaden Gee + Kyle Dervin + Abe Didan + Nathan
 * Purpose: Represents a single die (1-6) used in Yahtzee. The
 *          initial value is 0, indicating it has not been rolled yet.
 * Course: CSC 335 Spring 2025
 */


package model.dice;

import java.util.Random;


public class Dice {
    private int value = 0;
    private static final Random random = new Random();


    public Dice() {}

    /**
     * Rolls the die to generate a random value between 1 and 6.
     */
    public void roll() {
        value = random.nextInt(6) + 1;
    }

    /**
     * Gets the current value of the die.
     * 
     * Returns: the rolled value (1–6), or 0 if the die hasn't been rolled yet
     */
    public int getValue() {
        return value;
    }
}