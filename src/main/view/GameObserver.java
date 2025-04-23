/*
 * File: GameObserver.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Defines the interface for classes that want to observe changes
 *          in the game state, such as dice rolls or score updates. This is
 *          commonly used in GUI applications to update the display when the
 *          underlying game logic changes.
 * Course: CSC 335 Spring 2025
 */


package view;


/**
 * Observer interface for the game state.
 */
public interface GameObserver {

    /**
     * Called whenever the game state updates (dice rolled, score recorded, etc).
     * 
     * Parameters:
     *   message (type: String) -- a description of the game event
     *   diceValues (type: int[]) -- the current values of the dice after the update
     */
    void onGameStateChanged(String message, int[] diceValues);
}