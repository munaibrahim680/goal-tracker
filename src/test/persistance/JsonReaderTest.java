package persistance;

import model.Goal;
import model.GoalTracker;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


// The following code is modified from the JsonReaderTest class of the JsonSerializationDemo:
// https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence/JsonReaderTest.java
public class JsonReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            GoalTracker gt = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyGoalTracker() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyGoalTracker.json");
        try {
            GoalTracker gt = reader.read();
            assertTrue(gt.getDisabledGoals().isEmpty());
            assertTrue(gt.getEnabledGoals().isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralTracker.json");
        testScoresEnabled.add(score2);
        testScoresEnabled.add(score3);
        testScoresDisabled.add(score1);
        try {
            GoalTracker gt = reader.read();
            List<Goal> enabledGoals = gt.getEnabledGoals();
            assertEquals(2, enabledGoals.size());
            checkGoal("study", true, testScoresEnabled, enabledGoals.get(0));
            checkGoal("workout", true, testScoresEnabled, enabledGoals.get(1));

            List<Goal> disabledGoals = gt.getDisabledGoals();
            assertEquals(1, disabledGoals.size());
            checkGoal("sleep", false, testScoresDisabled, disabledGoals.get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
