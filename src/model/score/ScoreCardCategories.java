/*
 * File: ScoreCardCategories.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Defines all valid scoring categories in Yahtzee and provides
 * a list of all available categories.
 * Course: CSC 335 Spring 2025
 */


package model.score;

import java.util.Arrays;
import java.util.List;


/**
 * Defines all valid Yahtzee categories.
 */
public class ScoreCardCategories {
    public static final List<String> ALL_CATEGORIES = Arrays.asList(
        "Aces", "Twos", "Threes", "Fours", "Fives", "Sixes",
        "Three of a Kind", "Four of a Kind", "Full House",
        "Small Straight", "Large Straight", "Yahtzee", "Chance"
    );
}
