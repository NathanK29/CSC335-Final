// This file used AI to help with code development


/*
 * File: GameView.java
 * Authors: Jaden + Kyle + Abe + Nathan
 * Purpose: Main graphical user interface for the Yahtzee game. Handles player setup,
 *          dice display, category selection, score tracking, and integration with
 *          the underlying game model and strategy patterns.
 * Course: CSC 335 Spring 2025
 */

package view;
import flyweight.*;
import model.dice.*;
import model.player.*;
import model.score.*;
import model.stats.*;
import strategy.*;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GameView {

    // UI Components
    private JFrame frame;
    private JLabel[] diceLabels;
    private JCheckBox[] keepBoxes;
    private JButton rollButton, scoreButton;
    private JComboBox<String> categorySelector;
    private JPanel scoreCardPanel;
    private JLabel messageLabel;

    // Game logic
    private DiceSet diceSet;
    private ArrayList<Player> players;
    private int currentPlayerIndex;
    private int round;
    private int rolls;
    private boolean hasRolled;

    // Constants
    private static final int MAX_ROLLS = 3;
    private static final int MAX_ROUNDS = 12;
    private final ImageIcon[] diceIcons = new ImageIcon[7];  // dice[1-6] indexed by face value

    /**
     * Constructor: Starts the game setup and loads dice icons.
     */
    public GameView() {
        loadDiceImages();
        showSetupDialog();
    }

    /**
     * Loads dice face images into diceIcons array.
     */
    private void loadDiceImages() {
        for (int i = 1; i <= 6; i++) {
            diceIcons[i] = new ImageIcon("images/dice" + i + ".png");
        }
    }

    /**
     * Dialog prompting for number of players and difficulty (if playing vs computer).
     */
    private void showSetupDialog() {
        JTextField playerCountField = new JTextField("2");
        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("How many players?"));
        panel.add(playerCountField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Game Setup", JOptionPane.OK_CANCEL_OPTION);
        if (result != JOptionPane.OK_OPTION) System.exit(0);

        int count = Integer.parseInt(playerCountField.getText());
        players = new ArrayList<>();

        // Human vs. Computer
        if (count == 1) {
            JTextField nameField = new JTextField("Player1");
            String[] difficulty = {"Easy", "Hard"};
            JComboBox<String> diffBox = new JComboBox<>(difficulty);

            JPanel p = new JPanel(new GridLayout(0, 1));
            p.add(new JLabel("Enter your name:"));
            p.add(nameField);
            p.add(new JLabel("Play against:"));
            p.add(diffBox);

            int r = JOptionPane.showConfirmDialog(null, p, "Player Setup", JOptionPane.OK_CANCEL_OPTION);
            if (r != JOptionPane.OK_OPTION) System.exit(0);

            players.add(new HumanPlayer(nameField.getText()));
            String type = (String) diffBox.getSelectedItem();
            players.add(type.equals("Easy") ? new ComputerPlayer("CPU (Easy)", new EasyStrategy())
                                            : new ComputerPlayer("CPU (Hard)", new HardStrategy()));
        } 
        // Multiple human players
        else {
            for (int i = 1; i <= count; i++) {
                JTextField nameField = new JTextField("Player" + i);
                JPanel p = new JPanel(new GridLayout(0, 1));
                p.add(new JLabel("Enter name for Player " + i + ":"));
                p.add(nameField);

                int r = JOptionPane.showConfirmDialog(null, p, "Player Setup", JOptionPane.OK_CANCEL_OPTION);
                if (r != JOptionPane.OK_OPTION) System.exit(0);

                players.add(new HumanPlayer(nameField.getText()));
            }
        }

        diceSet = new DiceSet();
        currentPlayerIndex = 0;
        round = 1;
        buildGUI();
        startTurn();
    }

    /**
     * Builds the entire GUI layout with background, dice, buttons, and scorecards.
     */
    private void buildGUI() {
        frame = new JFrame("Enhanced Yahtzee");
        JPanel background = new BackgroundPanel("/images/background.jpg");
        background.setLayout(new BorderLayout(10, 10));
        frame.setContentPane(background);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 800);

        // Top message bar
        JPanel top = new JPanel();
        messageLabel = new JLabel("Welcome to Yahtzee!");
        messageLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        top.add(messageLabel);

        // Dice area (dice + keep checkboxes)
        JPanel dicePanel = new JPanel(new GridLayout(2, 5, 10, 10));
        diceLabels = new JLabel[5];
        keepBoxes = new JCheckBox[5];
        for (int i = 0; i < 5; i++) {
            diceLabels[i] = new JLabel(diceIcons[1]);
            diceLabels[i].setHorizontalAlignment(JLabel.CENTER);
            diceLabels[i].setPreferredSize(new Dimension(100, 100));
            dicePanel.add(diceLabels[i]);
        }
        for (int i = 0; i < 5; i++) {
            keepBoxes[i] = new JCheckBox("Keep");
            keepBoxes[i].setFont(new Font("SansSerif", Font.PLAIN, 14));
            keepBoxes[i].setOpaque(false);
            keepBoxes[i].setEnabled(false); // disabled until first roll
            dicePanel.add(keepBoxes[i]);
        }

        // Buttons and category selector
        JPanel controlPanel = new JPanel();
        rollButton = new JButton("Roll Dice");
        scoreButton = new JButton("Score Category");
        categorySelector = new JComboBox<>();

        JButton statsButton = new JButton("View Stats");
        JButton leaderboardButton = new JButton("Leaderboard");
        JButton resetStatsButton = new JButton("Reset Stats");

        statsButton.addActionListener(e -> showStats());
        leaderboardButton.addActionListener(e -> showLeaderboard());
        resetStatsButton.addActionListener(e -> resetStats());

        controlPanel.add(rollButton);
        controlPanel.add(categorySelector);
        controlPanel.add(scoreButton);
        controlPanel.add(statsButton);
        controlPanel.add(leaderboardButton);
        controlPanel.add(resetStatsButton);

        // Scorecard panel on the right
        scoreCardPanel = new JPanel(new GridLayout(0, players.size() + 1, 10, 4));
        scoreCardPanel.setBorder(BorderFactory.createTitledBorder("Scorecards"));
        updateScoreDisplay();

        // Hook up listeners
        rollButton.addActionListener(e -> rollDice());
        scoreButton.addActionListener(e -> scoreCategory());

        // Layout everything
        frame.add(top, BorderLayout.NORTH);
        frame.add(dicePanel, BorderLayout.CENTER);
        frame.add(controlPanel, BorderLayout.SOUTH);
        frame.add(scoreCardPanel, BorderLayout.EAST);
        frame.setVisible(true);

        dicePanel.setOpaque(false);
        controlPanel.setOpaque(false);
    }

    /**
     * Displays all scores and which categories are used.
     */
    private void updateScoreDisplay() {
        scoreCardPanel.removeAll();
        scoreCardPanel.add(new JLabel("Category"));
        for (Player p : players) scoreCardPanel.add(new JLabel(p.getName()));

        for (String cat : ScoreCardCategories.ALL_CATEGORIES) {
            scoreCardPanel.add(new JLabel(cat));
            for (Player p : players) {
                boolean used = p.getScoreCard().isUsed(cat);
                JLabel status = new JLabel(used ? "✔" : "");
                status.setHorizontalAlignment(JLabel.CENTER);
                scoreCardPanel.add(status);
            }
        }

        // Add total scores
        scoreCardPanel.add(new JLabel("Total Score"));
        for (Player p : players) {
            JLabel total = new JLabel(String.valueOf(p.getScoreCard().getTotalScore()));
            total.setHorizontalAlignment(JLabel.CENTER);
            scoreCardPanel.add(total);
        }

        scoreCardPanel.revalidate();
        scoreCardPanel.repaint();
    }

    /**
     * Starts the next player's turn.
     */
    private void startTurn() {
        rolls = 0;
        hasRolled = false;
        diceSet.resetKeep();

        // Disable checkboxes until first roll
        for (JCheckBox cb : keepBoxes) {
            cb.setSelected(false);
            cb.setEnabled(false);
        }

        Player current = players.get(currentPlayerIndex);
        messageLabel.setText(current.getName() + "'s turn (Round " + round + "/" + MAX_ROUNDS + ")");
        updateDiceDisplay();
        updateCategoryDropdown(current);

        // If CPU player, skip manual steps
        if (current instanceof ComputerPlayer) {
            doComputerTurn((ComputerPlayer) current);
        }
    }

    /**
     * Handles rolling dice and enabling checkboxes afterward.
     */
    private void rollDice() {
        if (rolls >= MAX_ROLLS) return;

        boolean[] keep = new boolean[5];
        if (hasRolled) {
            for (int i = 0; i < 5; i++) {
                keep[i] = keepBoxes[i].isSelected();
            }
        }

        diceSet.setKeepStatus(keep);
        diceSet.rollUnkeptDice();
        rolls++;
        hasRolled = true;
        updateDiceDisplay();

        for (JCheckBox cb : keepBoxes) cb.setEnabled(true);
        messageLabel.setText(players.get(currentPlayerIndex).getName() + " rolled (" + rolls + "/" + MAX_ROLLS + ")");
    }

    /**
     * Updates dice icons based on current values.
     */
    private void updateDiceDisplay() {
        int[] values = diceSet.getDiceValues();
        for (int i = 0; i < 5; i++) {
            int value = values[i];
            diceLabels[i].setIcon(diceIcons[Math.max(1, Math.min(6, value))]);
        }
    }

    /**
     * Records the selected category score and moves to next turn.
     */
    private void scoreCategory() {
        if (!hasRolled) {
            messageLabel.setText("You must roll at least once before scoring.");
            return;
        }

        Player current = players.get(currentPlayerIndex);
        int[] diceValues = diceSet.getDiceValues();
        String selected = (String) categorySelector.getSelectedItem();

        try {
            ScoreCategory category = ScoreCategoryStore.getCategory(selected);
            if (current.getScoreCard().recordScore(category, diceValues)) {
                updateCategoryDropdown(current);
                for (JCheckBox cb : keepBoxes) cb.setEnabled(false);
                messageLabel.setText(current.getName() + " scored in " + selected);
                nextTurn();
            } else {
                messageLabel.setText("Category already used. Choose another.");
            }
        } catch (IllegalArgumentException e) {
            messageLabel.setText("Invalid category");
        }
        updateScoreDisplay();
    }

    /**
     * Automated CPU logic for rolling and choosing the best score.
     */
    private void doComputerTurn(ComputerPlayer cpu) {
        ComputerStrategy strategy = cpu.getStrategy();
        diceSet.resetKeep();

        // First roll
        diceSet.rollUnkeptDice();
        updateDiceDisplay();
        int[] dice = diceSet.getDiceValues();

        // Let the strategy pick a category — must be saved from the method
        String chosenCategory = strategy.chooseCategory(dice, cpu.getScoreCard());

        // If it's HardStrategy, do bestMove logic
        if (strategy instanceof HardStrategy hard) {
            hard.reset(); // Reset first
            hard.chooseCategory(dice, cpu.getScoreCard()); // Set committedCategory

            for (int i = 0; i < 2; i++) {
                boolean[] keep = hard.bestMove(dice);
                diceSet.setKeepStatus(keep);
                diceSet.rollUnkeptDice();
                updateDiceDisplay();
                dice = diceSet.getDiceValues();
            }

            chosenCategory = hard.getCommittedCategory(); // ✅ now guaranteed to be non-null
        }

        // Final scoring
        if (chosenCategory == null) {
            JOptionPane.showMessageDialog(frame, "Computer failed to choose a category.");
            nextTurn(); // fallback to avoid crashing
            return;
        }

        ScoreCategory category = ScoreCategoryStore.getCategory(chosenCategory);
        cpu.getScoreCard().recordScore(category, dice);
        messageLabel.setText(cpu.getName() + " chose: " + chosenCategory);
        updateScoreDisplay();
        nextTurn();
    }

    /**
     * Advances to the next player or ends the game if all rounds complete.
     */
    private void nextTurn() {
        currentPlayerIndex++;
        if (currentPlayerIndex >= players.size()) {
            currentPlayerIndex = 0;
            round++;
        }

        if (round > MAX_ROUNDS) {
            endGame();
        } else {
            startTurn();
        }
    }

    /**
     * Final game logic: calculates winner and updates stats.
     */
    private void endGame() {
        PlayerStatsManager statsManager = new PlayerStatsManager();

        Player winner = null;
        int high = -1;
        for (Player p : players) {
            int score = p.getScoreCard().getTotalScore();
            if (score > high) {
                high = score;
                winner = p;
            }
        }

        for (Player p : players) {
            if (p == winner) statsManager.recordWin(p.getName());
            else statsManager.recordLoss(p.getName());
        }

        statsManager.saveStats();
        JOptionPane.showMessageDialog(frame, "Game Over! Winner: " + winner.getName());
        frame.dispose();
    }

    // --- Stats and Leaderboard ---

    private void showStats() {
        PlayerStatsManager statsManager = new PlayerStatsManager();
        StringBuilder sb = new StringBuilder("Current Player Stats:\n\n");
        for (Player p : players) {
            sb.append(p.getName()).append(" - ").append(statsManager.getStats(p.getName())).append("\n");
        }
        JOptionPane.showMessageDialog(frame, sb.toString(), "Stats", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showLeaderboard() {
        PlayerStatsManager statsManager = new PlayerStatsManager();
        String allStats = statsManager.getAllStats();
        JOptionPane.showMessageDialog(frame, allStats, "Global Leaderboard", JOptionPane.INFORMATION_MESSAGE);
    }

    private void resetStats() {
        int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to reset all stats?", "Confirm Reset", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            PlayerStatsManager statsManager = new PlayerStatsManager();
            statsManager.resetAllStats();
            statsManager.saveStats();
            JOptionPane.showMessageDialog(frame, "Stats have been reset.", "Reset Complete", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /**
     * Filters available categories for the current player in dropdown.
     */
    private void updateCategoryDropdown(Player p) {
        categorySelector.removeAllItems();
        for (String cat : ScoreCardCategories.ALL_CATEGORIES) {
            if (!p.getScoreCard().isUsed(cat)) {
                categorySelector.addItem(cat);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameView::new);
    }

}