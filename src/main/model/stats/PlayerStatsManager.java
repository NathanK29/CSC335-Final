/*
 * File: PlayerStatsManager.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Manages player win/loss statistics for the Yahtzee game.
 *          Stats are stored in a text file and save across games.
 *          Provides functionality for storing wins/losses,
 *          reseting the leaderboard, and displaying stats.
 * AI USED FOR THIS FILE ADVANVCED FEATURE !!!!
 * Course: CSC 335 Spring 2025
 */

package model.stats;

import java.io.*;
import java.util.*;


public class PlayerStatsManager {

    // Name of the file where stats are stored
    private static final String FILE_NAME = "stats.txt";

    // Maps player names to an array of two integers: [wins, losses]
    private Map<String, int[]> statsMap;

    /**
     * Constructs a new PlayerStatsManager and loads existing stats from the file (if it exists).
     */
    public PlayerStatsManager() {
        statsMap = new HashMap<>();
        loadStats(); // Load existing player stats into the map
    }

    /**
     * Loads player statistics from the stats file into the in-memory statsMap.
     * If the file doesn't exist (e.g. first run), this method does nothing.
     */
    private void loadStats() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;

            // Read each line from the file
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                // Ensure the line has all three expected parts
                if (parts.length == 3) {
                    String name = parts[0];
                    int wins = Integer.parseInt(parts[1]);
                    int losses = Integer.parseInt(parts[2]);

                    // Save to the map
                    statsMap.put(name, new int[]{wins, losses});
                }
            }
        } catch (IOException e) {
            // No file? That’s fine. It may be the first time running the program.
        }
    }

    /**
     * Records a win for the specified player.
     * If the player doesn't exist in the map yet, their record is created.
     */
    public void recordWin(String playerName) {
        int[] record = statsMap.getOrDefault(playerName, new int[]{0, 0});
        record[0]++; // Increment win count
        statsMap.put(playerName, record); // Update the map
    }

    /**
     * Records a loss for the specified player.
     * If the player doesn't exist in the map yet, their record is created.
     */
    public void recordLoss(String playerName) {
        int[] record = statsMap.getOrDefault(playerName, new int[]{0, 0});
        record[1]++; // Increment loss count
        statsMap.put(playerName, record); // Update the map
    }

    /**
     * Saves the current statsMap to the file so it persists across games.
     * Each player's record is saved on a separate line.
     */
    public void saveStats() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Map.Entry<String, int[]> entry : statsMap.entrySet()) {
                String name = entry.getKey();
                int[] record = entry.getValue();
                pw.println(name + "," + record[0] + "," + record[1]);
            }
        } catch (IOException e) {
            System.err.println("Error saving stats: " + e.getMessage());
        }
    }

    /**
     * Returns a formatted string showing the wins and losses of a specific player.
     * If the player has no record yet, they are shown with 0 wins and 0 losses.
     *
     * @param playerName the name of the player
     * @return string like "Wins: 2, Losses: 3"
     */
    public String getStats(String playerName) {
        int[] record = statsMap.getOrDefault(playerName, new int[]{0, 0});
        return "Wins: " + record[0] + ", Losses: " + record[1];
    }

    /**
     * Prints all stats to the console — useful for debugging (not shown in GUI).
     */
    public void printAllStats() {
        for (Map.Entry<String, int[]> entry : statsMap.entrySet()) {
            String name = entry.getKey();
            int[] record = entry.getValue();
            System.out.println(name + " - Wins: " + record[0] + ", Losses: " + record[1]);
        }
    }

    /**
     * Returns a formatted string showing the stats for all players,
     * suitable for display in a leaderboard-style pop-up.
     *
     * @return formatted string of all player stats
     */
    public String getAllStats() {
        StringBuilder sb = new StringBuilder();
        sb.append("All Player Stats:\n\n");
        for (Map.Entry<String, int[]> entry : statsMap.entrySet()) {
            sb.append(entry.getKey()).append(" - Wins: ")
              .append(entry.getValue()[0]).append(", Losses: ")
              .append(entry.getValue()[1]).append("\n");
        }
        return sb.toString();
    }

    /**
     * Clears all stats both from memory and from the file on disk.
     * This is useful for a full leaderboard reset.
     */
    public void resetAllStats() {
        statsMap.clear(); // Clear map in memory

        File f = new File(FILE_NAME);
        if (f.exists()) {
            f.delete(); // Remove the stats file if it exists
        }
    }
}