/*
 * File: HardStrategy.java
 * Authors: Jaden Gee + Kyle Dervin + Abe Didan + Nathan
 * Purpose: Represents the hard strategy used by a computer player.
 *          The hard strategy selects the best scoring category based
 *          on the first roll, and then determines which dice to keep
 *          for subsequent rolls in order to maximize the score
 *          in that chosen category.
 * Course: CSC 335 Spring 2025
 */


package main.strategy;

import flyweight.*;
import model.score.*;

import java.util.*;


public class HardStrategy implements ComputerStrategy {
    private String committedCategory;

    @Override
    public String chooseCategory(int[] dice, ScoreCard card) {
        int bestScore = -1;
        committedCategory = null;

        for (String category : ScoreCardCategories.ALL_CATEGORIES) {
            if (!card.isUsed(category)) {
                ScoreCategory sc = ScoreCategoryStore.getCategory(category);
                int score = sc.calculateScore(dice);
                if (score > bestScore) {
                    bestScore = score;
                    committedCategory = category;
                }
            }
        }

        return committedCategory;
    }

    public boolean[] bestMove(int[] diceValues) {
    	boolean[] keep = new boolean[diceValues.length];
  
        if (committedCategory == null) {
        	return keep;
        }

        if (committedCategory.equals("Aces") || committedCategory.equals("Twos") ||
            committedCategory.equals("Threes") || committedCategory.equals("Fours") ||
            committedCategory.equals("Fives") || committedCategory.equals("Sixes")) {

            int faceValue = ScoreCardCategories.ALL_CATEGORIES.indexOf(committedCategory) + 1;
            for (int i = 0; i < diceValues.length; i++) {
                if (diceValues[i] == faceValue) {
                    keep[i] = true;
                }
            }

        } else if (committedCategory.equals("Three of a Kind") ||
                   committedCategory.equals("Four of a Kind") ||
                   committedCategory.equals("Yahtzee")) {

            int[] valueCounts = new int[7];
            for (int value : diceValues) {
                valueCounts[value]++;
            }

            int mostCommonValue = 1;
            for (int val = 2; val <= 6; val++) {
                if (valueCounts[val] > valueCounts[mostCommonValue]) {
                    mostCommonValue = val;
                }
            }

            for (int i = 0; i < diceValues.length; i++) {
                if (diceValues[i] == mostCommonValue) {
                    keep[i] = true;
                }
            }

        } else if (committedCategory.equals("Full House")) {

            int[] valueCounts = new int[7];
            for (int value : diceValues) {
                valueCounts[value]++;
            }

            int tripleValue = -1;
            int pairValue = -1;
            for (int val = 1; val <= 6; val++) {
                if (valueCounts[val] >= 3) {
                    tripleValue = val;
                } else if (valueCounts[val] >= 2) {
                    pairValue = val;
                }
            }

            for (int i = 0; i < diceValues.length; i++) {
                if (diceValues[i] == tripleValue || diceValues[i] == pairValue) {
                    keep[i] = true;
                }
            }

        } else if (committedCategory.equals("Small Straight") || committedCategory.equals("Large Straight")) {
            Arrays.sort(diceValues);
            for (int i = 0; i < keep.length; i++) {
                keep[i] = true;
            }

        } else if (committedCategory.equals("Chance")) {
        	for (int i = 0; i < keep.length; i++) {
        	    keep[i] = true;
        	}
        }

        return keep;
    }

    public void reset() {
        committedCategory = null;
    }

    public String getCommittedCategory() {
        return committedCategory;
    }
}