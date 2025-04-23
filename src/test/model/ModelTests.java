package model;

import flyweight.*;
import strategy.*;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.Map;

import org.junit.jupiter.api.Test;



import model.dice.Dice;
import model.dice.DiceSet;
import model.player.HumanPlayer;
import model.player.ComputerPlayer;
import model.score.*;
import model.stats.PlayerStatsManager;


public class ModelTests {

	
    @Test
    public void testDiceAndDiceSet() {
        DiceSet set = new DiceSet();
        set.rollUnkeptDice();
        int[] firstRoll = set.getDiceValues();
        assertEquals(5, firstRoll.length);

        boolean[] keep = new boolean[] {true, false, true, false, true};
        set.setKeepStatus(keep);
        int[] beforeSecondRoll = set.getDiceValues();
        set.rollUnkeptDice();
        int[] afterSecondRoll = set.getDiceValues();

        for (int i = 0; i < 5; i++) {
            if (keep[i]) {
                assertEquals(beforeSecondRoll[i], afterSecondRoll[i]);
            }
        }
        
        boolean[] actualKeepStatus = set.getKeepStatus();
        for (int i = 0; i < 5; i++) {
            assertEquals(keep[i], actualKeepStatus[i]);
        }
        
        set.resetKeep();
        boolean[] reset = set.getKeepStatus();
        for (int i = 0; i < 5; i++) {
            assertFalse(reset[i]);
        }
    }
    

    @Test
    public void testHumanPlayer() {
        HumanPlayer hp = new HumanPlayer("Jaden");
        assertEquals("Jaden", hp.getName());
        assertNotNull(hp.getScoreCard());
        assertFalse(hp.takeTurn(new int[] {1,2,3,4,5}));
    }
    

    @Test
    public void testComputerPlayer() {
        ComputerPlayer cp = new ComputerPlayer("CPU", new EasyStrategy());
        assertEquals("CPU", cp.getName());
        assertNotNull(cp.getStrategy());
        int[] dice = {1, 2, 3, 4, 5};
        String chosen = cp.getStrategy().chooseCategory(dice, cp.getScoreCard());
        assertTrue(ScoreCardCategories.ALL_CATEGORIES.contains(chosen));
        boolean result = cp.takeTurn(dice);
        assertTrue(result);
    }

    
    @Test
    public void testNumberCategoryScore1() {
        ScoreCategory category = new NumberCategory(1);
        assertEquals(2, category.calculateScore(new int[] {3, 1, 3, 1, 2}));
        assertEquals(0, category.calculateScore(new int[] {2, 2, 4, 5, 6}));
    }

    @Test
    public void testNumberCategoryScore2() {
        ScoreCategory category = new NumberCategory(2);
        assertEquals(6, category.calculateScore(new int[] {2, 2, 3, 1, 2}));
        assertEquals(0, category.calculateScore(new int[] {1, 3, 4, 5, 6}));
    }

    @Test
    public void testNumberCategoryScore3() {
        ScoreCategory category = new NumberCategory(3);
        assertEquals(9, category.calculateScore(new int[] {3, 3, 3, 1, 2}));
        assertEquals(0, category.calculateScore(new int[] {1, 2, 4, 5, 6}));
    }
    
    @Test
    public void testNumberCategoryScore4() {
        ScoreCategory category = new NumberCategory(4);
        assertEquals(16, category.calculateScore(new int[] {4, 4, 4, 4, 2}));
        assertEquals(0, category.calculateScore(new int[] {1, 2, 6, 5, 6}));
    }

    @Test
    public void testNumberCategoryScore5() {
        ScoreCategory category = new NumberCategory(5);
        assertEquals(10, category.calculateScore(new int[] {5, 5, 4, 4, 2}));
        assertEquals(0, category.calculateScore(new int[] {1, 2, 6, 4, 6}));
    }

    @Test
    public void testNumberCategoryScore6() {
        ScoreCategory category = new NumberCategory(6);
        assertEquals(24, category.calculateScore(new int[] {6, 6, 6, 6, 2}));
        assertEquals(0, category.calculateScore(new int[] {1, 2, 2, 5, 2}));
    }

    
    @Test
    public void testFourOfAKindCategory() {
        ScoreCategory category = new FourOfAKindCategory();
        assertEquals(22, category.calculateScore(new int[] {5, 5, 5, 5, 2}));
        assertEquals(0, category.calculateScore(new int[] {1, 2, 3, 4, 5}));
    }

    
    @Test
    public void testLargeStraightCategory() {
        ScoreCategory category = new LargeStraightCategory();
        assertEquals(40, category.calculateScore(new int[] {2, 3, 4, 5, 6}));
        assertEquals(40, category.calculateScore(new int[] {5, 3, 2, 1, 4}));
        assertEquals(0, category.calculateScore(new int[] {1, 2, 2, 4, 5}));
    }

    @Test
    public void testSmallStraightCategory() {
    	ScoreCategory category = new SmallStraightCategory();
        assertEquals(30, category.calculateScore(new int[] {1, 2, 3, 4, 6}));
        assertEquals(30, category.calculateScore(new int[] {2, 3, 4, 5, 1}));
        assertEquals(30, category.calculateScore(new int[] {3, 4, 5, 6, 2}));
        assertEquals(0, category.calculateScore(new int[] {1, 1, 2, 2, 5}));
        assertEquals(30, category.calculateScore(new int[] {3, 4, 5, 6, 6}));
        assertEquals(0, category.calculateScore(new int[] {2, 2, 4, 5, 6}));
    }

    
    @Test
    public void testFullHouseCategory() {
        ScoreCategory category = new FullHouseCategory();
        assertEquals(25, category.calculateScore(new int[] {3, 3, 3, 2, 2}));
        assertEquals(0, category.calculateScore(new int[] {2, 2, 3, 3, 4}));
    }
    

    @Test
    public void testYahtzeeCategory() {
        ScoreCategory category = new YahtzeeCategory();
        assertEquals(50, category.calculateScore(new int[] {6, 6, 6, 6, 6}));
        assertEquals(0, category.calculateScore(new int[] {6, 6, 6, 6, 5}));
    }

    @Test
    public void testChanceCategory() {
        ScoreCategory category = new ChanceCategory();
        assertEquals(18, category.calculateScore(new int[] {1, 2, 3, 4, 8}));
    }


    
    @Test
    public void testScoreCardMultipleEntries() {
    	ScoreCard card = new ScoreCard();

        assertTrue(card.recordScore(ScoreCategoryStore.getCategory("Aces"), new int[] {1, 1, 2, 2, 3}));
        assertTrue(card.recordScore(ScoreCategoryStore.getCategory("Twos"), new int[] {2, 2, 2, 3, 3}));
        assertTrue(card.recordScore(ScoreCategoryStore.getCategory("Chance"), new int[] {6, 6, 6, 6, 6}));

        assertEquals(2, card.getScore("Aces"));
        assertEquals(6, card.getScore("Twos"));
        assertEquals(30, card.getScore("Chance"));
        assertNull(card.getScore("Fives"));

        assertEquals(38, card.getTotalScore()); // 2 + 6 + 30

        assertTrue(card.isUsed("Aces"));
        assertTrue(card.isUsed("Twos"));
        assertTrue(card.isUsed("Chance"));
        assertFalse(card.isUsed("Fives"));

        Map<String, Integer> allScores = card.getAllScores();
        assertEquals(3, allScores.size());
        assertTrue(allScores.containsKey("Aces"));
        assertTrue(allScores.containsKey("Twos"));
        assertTrue(allScores.containsKey("Chance"));

        String[] available = card.getAvailableCategories();
        for (String category : available) {
            assertFalse(card.isUsed(category));
            assertNotEquals("Aces", category);
            assertNotEquals("Twos", category);
            assertNotEquals("Chance", category);
        }

        boolean reused = card.recordScore(ScoreCategoryStore.getCategory("Twos"), new int[] {2, 2, 2, 3, 3});
        assertFalse(reused, "Should not allow recording a used category again");
    }
    
    
    @Test
    public void testThreeOfAKindScoring() {
    	ScoreCategory category = new ThreeOfAKindCategory();
        assertEquals(0, category.calculateScore(new int[] {1, 2, 3, 4, 5}));
        assertEquals(24, category.calculateScore(new int[] {6, 6, 6, 6, 0}));
        assertEquals(30, category.calculateScore(new int[] {6, 6, 6, 6, 6}));
        assertEquals(19, category.calculateScore(new int[] {3, 3, 3, 5, 5}));
    }
    
    
    @Test
    public void testPlayerStatsManagerFullCoverage() {
        PlayerStatsManager manager = new PlayerStatsManager();
        manager.recordWin("Kyle");
        manager.recordLoss("Abe");
        manager.recordLoss("Abe");
        manager.saveStats();

        PlayerStatsManager reloaded = new PlayerStatsManager();
        String kyleStats = reloaded.getStats("Kyle");
        String abeStats = reloaded.getStats("Abe");

        assertTrue(kyleStats.contains("Wins: 1"));
        assertTrue(abeStats.contains("Losses: 2"));

        String allStats = reloaded.getAllStats();
        assertTrue(allStats.contains("Kyle"));
        assertTrue(allStats.contains("Abe"));

        assertDoesNotThrow(() -> reloaded.printAllStats());

        assertEquals("Wins: 0, Losses: 0", reloaded.getStats("Unknown"));

        reloaded.resetAllStats();
        assertEquals("Wins: 0, Losses: 0", reloaded.getStats("Kyle"));
        assertFalse(new File("stats.txt").exists(), "stats.txt should be deleted after reset");
    }
    
    
    @Test
    public void testHardStrategyAllPaths() {
        HardStrategy strategy = new HardStrategy();
        ScoreCard card = new ScoreCard();

        for (String category : ScoreCardCategories.ALL_CATEGORIES) {
            if (!category.equals("Threes")) {
                card.recordScore(ScoreCategoryStore.getCategory(category), new int[] {1, 1, 1, 1, 1});
            }
        }

        String chosen = strategy.chooseCategory(new int[] {3, 3, 3, 2, 1}, card);
        assertEquals("Threes", chosen);
        assertEquals("Threes", strategy.getCommittedCategory());

        boolean[] keep = strategy.bestMove(new int[] {3, 3, 3, 2, 4});
        assertTrue(keep[0] && keep[1] && keep[2]);
        assertFalse(keep[3] || keep[4]);

        strategy.reset();
        assertNull(strategy.getCommittedCategory());
    }
    

    @Test
    public void testHardStrategyBestMoveAllBranches() {
        ScoreCard dummy = new ScoreCard();
        HardStrategy strategy = new HardStrategy();

        // Aces
        strategy.chooseCategory(new int[] {1, 1, 4, 5, 6}, dummy);
        strategy.bestMove(new int[] {1, 1, 4, 5, 6});

        // Three of a Kind
        strategy.chooseCategory(new int[] {4, 4, 4, 2, 6}, dummy);
        strategy.bestMove(new int[] {4, 4, 4, 2, 6});

        // Full House
        strategy.chooseCategory(new int[] {5, 5, 2, 2, 2}, dummy);
        strategy.bestMove(new int[] {5, 5, 2, 2, 2});

        // Small Straight
        strategy.chooseCategory(new int[] {1, 2, 3, 4, 6}, dummy);
        strategy.bestMove(new int[] {1, 2, 3, 4, 6});

        // Chance
        strategy.chooseCategory(new int[] {6, 6, 6, 6, 6}, dummy);
        strategy.bestMove(new int[] {6, 6, 6, 6, 6});
    }
}