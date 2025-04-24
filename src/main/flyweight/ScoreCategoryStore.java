/*
 * File: ScoreCategoryStore.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Implements a flyweight factory for creating and managing shared
 * ScoreCategory instances in Yahtzee.
 * Course: CSC 335 Spring 2025
 */

package flyweight;

import model.score.*;

import java.util.HashMap;
import java.util.Map;

public class ScoreCategoryStore {
    private static final Map<String, ScoreCategory> categories = new HashMap<>();
    
    /*
     * Gets or creates a ScoreCategory instance for the given category name.
     * 
     * Parameters: name (type: String) -- name of the category to get/create
     * 
     * Returns: ScoreCategory -- the category instance
     * 
     * Throws: IllegalArgumentException -- if the category name is unknown
     */
    public static ScoreCategory getCategory(String name) {
    	if (!categories.containsKey(name)) {
            if (name.equals("Aces")) {
                categories.put(name, new NumberCategory(1));
            } else if (name.equals("Twos")) {
                categories.put(name, new NumberCategory(2));
            } else if (name.equals("Threes")) {
                categories.put(name, new NumberCategory(3));
            } else if (name.equals("Fours")) {
                categories.put(name, new NumberCategory(4));
            } else if (name.equals("Fives")) {
                categories.put(name, new NumberCategory(5));
            } else if (name.equals("Sixes")) {
                categories.put(name, new NumberCategory(6));
            } else if (name.equals("Three of a Kind")) {
                categories.put(name, new ThreeOfAKindCategory());
            } else if (name.equals("Four of a Kind")) {
                categories.put(name, new FourOfAKindCategory());
            } else if (name.equals("Full House")) {
                categories.put(name, new FullHouseCategory());
            } else if (name.equals("Small Straight")) {
                categories.put(name, new SmallStraightCategory());
            } else if (name.equals("Large Straight")) {
                categories.put(name, new LargeStraightCategory());
            } else if (name.equals("Yahtzee")) {
                categories.put(name, new YahtzeeCategory());
            } else if (name.equals("Chance")) {
                categories.put(name, new ChanceCategory());
            } else {
                throw new IllegalArgumentException("Unknown category: " + name);
            }
        }
        return categories.get(name);
    }
}