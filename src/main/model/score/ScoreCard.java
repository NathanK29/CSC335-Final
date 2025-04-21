/*
 * File: ScoreCard.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Manages a player's scorecard in Yahtzee, tracking scores for each category
 * and maintaining which categories have been used.
 * Course: CSC 335 Spring 2025
 */


package model.score;

import java.util.HashMap;
import java.util.Map;


public class ScoreCard {
    private final Map<String, Integer> scores;
    private final Map<String, Boolean> usedCategories;
    
    /*
     * Constructs a new empty scorecard.
     */
    public ScoreCard() {
        scores = new HashMap<>();
        usedCategories = new HashMap<>();
    }

    /*
     * Records a score for the given category using the provided dice values.
     * Returns false if the category has already been used.
     * 
     * Parameters: 
     *   category (type: ScoreCategory) -- the category to score
     *   diceValues (type: int[]) -- array of dice values to score
     * 
     * Returns: boolean -- true if score was recorded, false if category was already used
     */
    public boolean recordScore(ScoreCategory category, int[] diceValues) {
        String name = category.getName();
        if (usedCategories.getOrDefault(name, false)) {
            return false; // Category already used
        }
        int score = category.calculateScore(diceValues);
        scores.put(name, score);
        usedCategories.put(name, true);
        return true;
    }

    /*
     * Gets the score for a given category, or null if unused.
     * 
     * Parameters: categoryName (type: String) -- name of the category
     * 
     * Returns: Integer -- the score for the category, or null if unused
     */
    public Integer getScore(String categoryName) {
        return scores.get(categoryName);
    }

    /*
     * Checks if the category has already been used.
     * 
     * Parameters: categoryName (type: String) -- name of the category
     * 
     * Returns: boolean -- true if category has been used, false otherwise
     */
    public boolean isUsed(String categoryName) {
        return usedCategories.getOrDefault(categoryName, false);
    }

    /*
     * Gets the total score from all recorded categories.
     * 
     * Parameters: none
     * 
     * Returns: int -- sum of all recorded scores
     */
    public int getTotalScore() {
        int total = 0;
        for (int score : scores.values()) {
            total += score;
        }
        return total;
    }

    /*
     * Gets a copy of all scores for display.
     * 
     * Parameters: none
     * 
     * Returns: Map<String, Integer> -- copy of all category scores
     */
    public Map<String, Integer> getAllScores() {
        return new HashMap<>(scores);
    }

    /*
     * Gets a list of categories that have not been used yet.
     * 
     * Parameters: none
     * 
     * Returns: String[] -- array of unused category names
     */
    public String[] getAvailableCategories() {
        return ScoreCardCategories.ALL_CATEGORIES.stream()
                .filter(name -> !usedCategories.getOrDefault(name, false))
                .toArray(String[]::new);
    }
}