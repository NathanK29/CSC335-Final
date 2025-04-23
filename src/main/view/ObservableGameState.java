/*
 * File: ObservableGameState.java
 * Authors: Jaden Gee + Kyle Dervin + Abe Didan + Nathan
 * Purpose: Manages the game state, specifically for dice rolling and scoring updates.
 *          This class notifies registered observers whenever the state of the game
 *          changes. It follows the Observer design pattern.
 * Course: CSC 335 Spring 2025
 */


package view;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class ObservableGameState {

    private final List<GameObserver> observers = new ArrayList<>(); // list of registered observers
    private final int[] diceValues = new int[5];
    private int rollCount = 0;
    private final Random random = new Random();

    /**
     * Adds an observer to the list and immediately notifies it of the initial state.
     * 
     * Parameters:
     *   observer (type: GameObserver) -- the registered observer to be notified
     */
    public void addObserver(GameObserver observer) {
        observers.add(observer);
        notifyObservers("Game started", diceValues);
    }

    /**
     * Rolls the dice up to 3 times, keeping the ones the player marked as true in
     * the 'keep' array. Notifies all registered observers of the new dice values.
     * 
     * Parameters:
     *   keep (type: boolean[]) -- a boolean array indicating which dice to keep (true)
     */
    public void rollDice(boolean[] keep) {
        if (rollCount >= 3) {
        	return;
        }

        for (int i = 0; i < 5; i++) {
            if (!keep[i]) {
                diceValues[i] = random.nextInt(6) + 1;
            }
        }

        rollCount++;
        notifyObservers("Roll " + rollCount, diceValues);
    }

    /**
     * Ends the current turn, resets the roll count, and clears the dice.
     * Notifies observers with a summary of the score.
     */
    public void endTurn() {
        int score = 0;
        for (int value : diceValues) {
            score += value;
        }

        rollCount = 0;
        for (int i = 0; i < 5; i++) {
            diceValues[i] = 0;
        }

        notifyObservers("Scored " + score + " points!", diceValues);
    }

    /**
     * Notifies all registered observers with a message and the current dice values.
     * 
     * Parameters:
     *   message (type: String) -- a description of the game event
     *   values  (type: int[])  -- the current values of the dice
     */
    private void notifyObservers(String message, int[] values) {
        for (GameObserver observer : observers) {
            observer.onGameStateChanged(message, values);
        }
    }
}