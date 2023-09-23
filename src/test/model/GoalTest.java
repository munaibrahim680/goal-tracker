package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

//Tests for Goal class
public class GoalTest {
    private Goal testGoal;
    private Score score1;
    private Score score2;
    private Score score3;
    private Score score4;


    @BeforeEach
    public void runBefore() {
        testGoal = new Goal("Test");
        score1 = new Score(3, LocalDate.of(2023, 7, 16));
        score2 = new Score(3, LocalDate.of(2023, 7, 17));
        score3 = new Score(5, LocalDate.of(2023, 7, 18));
        score4 = new Score(2, LocalDate.of(2023, 7, 18));
    }

    @Test
    public void testConstructor() {
        assertEquals("Test", testGoal.getName());
        assertEquals(-1, testGoal.lifetimeAverageScore());
        assertEquals(0, testGoal.getScoreList().size());
        assertTrue(testGoal.isEnabled());
    }

    @Test
    public void testDisableAndEnable() {
        assertTrue(testGoal.isEnabled());

        testGoal.disableGoal();
        assertFalse(testGoal.isEnabled());

        testGoal.enableGoal();
        assertTrue(testGoal.isEnabled());
    }

    @Test
    public void testAddScoreOnce() {
        testGoal.addScore(score1);
        Score testScore = testGoal.getScoreList().get(0);
        assertEquals(1, testGoal.getScoreList().size());
        assertEquals(score1, testScore);
    }

    @Test
    public void testAddScoreMultiple() {
        //test 2 scores added, with same value but different days
        testGoal.addScore(score1);
        testGoal.addScore(score2);
        assertEquals(2, testGoal.getScoreList().size());
        assertEquals(score2, testGoal.getScoreList().get(1));

        //test 2 scores added on same day
        testGoal.addScore(score3);
        assertEquals(3, testGoal.getScoreList().size());
        assertEquals(score3, testGoal.getScoreList().get(2));

        testGoal.addScore(score4);
        assertEquals(3, testGoal.getScoreList().size());
        assertEquals(score4, testGoal.getScoreList().get(2));
    }

    @Test
    public void testWithinXDays () {
        LocalDate date1 = LocalDate.of(2023, 8, 20);
        LocalDate date2 = LocalDate.of(2023, 8, 20);
        assertTrue(testGoal.isWithinXDays(date1, date2 ,0));

        LocalDate date3 = LocalDate.of(2023, 7, 15);
        LocalDate date4 = LocalDate.of(2023, 8, 15);
        assertTrue(testGoal.isWithinXDays(date3, date4 ,31));

        assertFalse(testGoal.isWithinXDays(date1, date4, 4));
        assertTrue(testGoal.isWithinXDays(date1, date4, 5));
        assertTrue(testGoal.isWithinXDays(date1, date4, 6));
    }

    @Test
    public void testLifetimeAverage() {
        //test empty score list
        assertEquals(-1, testGoal.lifetimeAverageScore());

        //test list of 1
        testGoal.addScore(score1);
        assertEquals(score1.getScoreVal(), testGoal.lifetimeAverageScore());

        //test list of 3
        testGoal.addScore(score2);
        testGoal.addScore(score3);
        double expectedAverage = (score1.getScoreVal() + score2.getScoreVal() + score3.getScoreVal())/3;
        assertEquals(expectedAverage, testGoal.lifetimeAverageScore());
    }

    @Test
    public void testWeeklyAverage() {
        // empty list
        assertEquals(-1, testGoal.weeklyAverageScore());

        //list of size 6
        testGoal.addScore(score1);
        testGoal.addScore(score2);
        testGoal.addScore(score3);
        testGoal.addScore(new Score(5, LocalDate.of(2023, 07, 19)));
        testGoal.addScore(new Score(5, LocalDate.of(2023, 07, 20)));
        testGoal.addScore(new Score(2, LocalDate.of(2023, 07, 21)));
        assertEquals(-1, testGoal.weeklyAverageScore());

        //list of size 7
        testGoal.addScore(new Score(1, LocalDate.of(2023, 07, 22)));
        double expectedAverage1 = (score1.getScoreVal()+ score2.getScoreVal()+ score3.getScoreVal()
                + 13) / testGoal.getScoreList().size();
        assertEquals(expectedAverage1, testGoal.weeklyAverageScore());

        //list of size 8
        testGoal.addScore(new Score(5, LocalDate.of(2023, 07, 23)));
        double expectedAverage2 = (score2.getScoreVal()+ score3.getScoreVal() + 17) / testGoal.getScoreList().size();
        assertEquals(expectedAverage2, testGoal.weeklyAverageScore());
    }

    @Test
    public void testMostRecentScore() {
        testGoal.addScore(score1);
        testGoal.addScore(score2);
        assertEquals(3, testGoal.getMostRecentScore());
        testGoal.addScore(score3);
        assertEquals(5, testGoal.getMostRecentScore());
    }

    @Test
    public void testOverrideHashcodeEquals() {
        Goal goal1 = new Goal("g1");
        Goal goal2 = new Goal("G1");
        assertEquals(goal1, goal2);

        goal1.addScore(score1);
        goal2.addScore(score2);
        assertFalse(goal1.hashCode() == goal2.hashCode());
        assertTrue(goal1.equals(goal2));
    }

    @Test
    void testOverrideEqualsNullObject() {
        Goal g1 = null;
        Goal g2 = new Goal("g2");

        assertFalse(g2.equals(g1));
    }

    @Test
    void testOverrideEqualsDifferentClass() {
        assertFalse(testGoal.equals(score1));
    }
}
