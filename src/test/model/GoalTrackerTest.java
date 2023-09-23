package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Tests for GoalTracker class
public class GoalTrackerTest {
    private GoalTracker testGoalTracker;
    private Goal goal1;
    private Goal goal2;
    private Goal goal3;
    private Score score1;
    private Score score2;
    private Score score3;

    @BeforeEach
    public void runBefore() {
        testGoalTracker = new GoalTracker();
        goal1 = new Goal("goal 1");
        goal2 = new Goal("goal 2");
        goal3 = new Goal("goal 3");
    }

    @Test
    public void testConstructor() {
        assertEquals(0, testGoalTracker.getEnabledGoals().size());
        assertEquals(0, testGoalTracker.getDisabledGoals().size());
    }

    @Test
    public void testAddGoalOnce() {
        testGoalTracker.addGoal(goal1);
        String actualGoalName = testGoalTracker.getEnabledGoals().get(0).getName();
        assertEquals(1, testGoalTracker.getEnabledGoals().size());
        assertEquals("goal 1", actualGoalName);
    }

    @Test
    public void testAddGoalMultiple() {
        testGoalTracker.addGoal(goal1); //add 2 unique goals
        testGoalTracker.addGoal(goal2);
        assertEquals(2, testGoalTracker.getEnabledGoals().size());
        assertEquals("goal 2", testGoalTracker.getEnabledGoals().get(1).getName());
        assertEquals(0, testGoalTracker.getDisabledGoals().size());

        testGoalTracker.addGoal(goal1); //add third goal already on list
        assertEquals(2, testGoalTracker.getEnabledGoals().size());
        assertEquals("goal 2", testGoalTracker.getEnabledGoals().get(1).getName());
        assertEquals(0, testGoalTracker.getDisabledGoals().size());


        testGoalTracker.addGoal(goal3); //add third goal, not already on list
        assertEquals(3, testGoalTracker.getEnabledGoals().size());
        assertEquals("goal 3", testGoalTracker.getEnabledGoals().get(2).getName());
        assertEquals(0, testGoalTracker.getDisabledGoals().size());
    }

    @Test
    public void testRemoveGoalOnce() {
        testGoalTracker.addGoal(goal1);
        testGoalTracker.addGoal(goal2);
        testGoalTracker.addGoal(goal3);
        testGoalTracker.removeGoal(goal2);
        assertEquals(2, testGoalTracker.getEnabledGoals().size());
        assertEquals(1, testGoalTracker.getDisabledGoals().size());
    }

    @Test
    public void testRemoveGoalMultiple() {
        testGoalTracker.addGoal(goal1);
        testGoalTracker.addGoal(goal2);
        testGoalTracker.addGoal(goal3);
        testGoalTracker.removeGoal(goal2);
        testGoalTracker.removeGoal(goal3);
        assertEquals(1, testGoalTracker.getEnabledGoals().size());
        assertEquals(2, testGoalTracker.getDisabledGoals().size());
    }

    //todo: test this method!
    @Test
    public void testDailyGoalAverage() {
        //no goals added
        assertEquals(0, testGoalTracker.dailyGoalsAverage());

        //set up
        score1 = new Score(4);
        score2 = new Score(2);
        score3 = new Score(3);
        goal1.addScore(score1);
        goal2.addScore(score2);
        goal3.addScore(score3);
        testGoalTracker.addGoal(goal1);
        testGoalTracker.addGoal(goal2);
        testGoalTracker.addGoal(goal3);

        //3 goals added with score
        double expectedAverage = (4+2+3)/3;
        assertEquals(expectedAverage, testGoalTracker.dailyGoalsAverage());

        //1 goal, no score
        GoalTracker testGoalTracker2 = new GoalTracker();
        testGoalTracker2.addGoal(new Goal("no score"));
        assertEquals(0, testGoalTracker2.dailyGoalsAverage());
    }

    @Test
    void testOverrideHashcodeEquals() {
        Goal g1 = new Goal ("g1");
        Goal g2 = new Goal ("g2");
        Goal g3 = new Goal ("G1");

        testGoalTracker.addGoal(g1);
        testGoalTracker.addGoal(g2);
        assertEquals(2, testGoalTracker.getEnabledGoals().size());

        testGoalTracker.addGoal(g3);
        assertEquals(2, testGoalTracker.getEnabledGoals().size());

        assertTrue(testGoalTracker.getEnabledGoals().contains(g1));
        assertTrue(testGoalTracker.getEnabledGoals().contains(g3));
    }

    @Test
    void testToJsonWithDisabledGoals() {
        goal1.disableGoal();
        goal2.disableGoal();
        testGoalTracker.addDisabledGoal(goal1);
        testGoalTracker.addDisabledGoal(goal2);

        JSONObject json = testGoalTracker.toJson();
        assertTrue(json.has("disabledGoals"));

        JSONArray jsonDisabledGoals = json.getJSONArray("disabledGoals");
        assertEquals(2, jsonDisabledGoals.length());

        JSONObject jsonGoal1 = jsonDisabledGoals.getJSONObject(0);
        assertEquals("goal 1", jsonGoal1.getString("name"));

        JSONObject jsonGoal2 = jsonDisabledGoals.getJSONObject(1);
        assertEquals("goal 2", jsonGoal2.getString("name"));
    }

    @Test
    void testAddToDisabledList() {
        goal1.disableGoal();
        goal2.disableGoal();

        testGoalTracker.addDisabledGoal(goal1);
        assertEquals(1, testGoalTracker.getDisabledGoals().size());

        testGoalTracker.addDisabledGoal(goal2);
        assertEquals(2, testGoalTracker.getDisabledGoals().size());

        testGoalTracker.addDisabledGoal(goal1);
        testGoalTracker.addGoal(goal3);
        assertEquals(2, testGoalTracker.getDisabledGoals().size());
    }

}
