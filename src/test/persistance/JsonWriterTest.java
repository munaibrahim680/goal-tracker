package persistance;

import model.Goal;
import model.GoalTracker;
import model.Score;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Tests  for JsonWriter
public class JsonWriterTest extends JsonTest{

    // The following code is modified from JsonWriterTest persistance class from  JsonSerializationDemo:
    //    https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence
    //    /JsonWriterTest.java
    @Test
    void testWriterInvalidFile() {
        try {
            GoalTracker gt = new GoalTracker();
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    // The following code is modified from JsonWriterTest persistance class from  JsonSerializationDemo:
    //    https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/test/persistence
    //    /JsonWriterTest.j
    @Test
    void testWriterEmptyGoalTracker() {
        try {
            GoalTracker gt = new GoalTracker();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyGoalTracker.json");
            writer.open();
            writer.write(gt);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyGoalTracker.json");
            gt = reader.read();
            assertTrue(gt.getEnabledGoals().isEmpty());
            assertTrue(gt.getDisabledGoals().isEmpty());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGoalTracker() {
        try {
            GoalTracker gt = new GoalTracker();
            Goal study = new Goal("study");
            Goal workout = new Goal("workout");
            gt.addGoal(study);
            gt.addGoal(workout);
            study.addScore(score2);
            study.addScore(score3);
            JsonWriter writer = new JsonWriter("./data/testWriterGeneralTracker.json");
            writer.open();
            writer.write(gt);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralTracker.json");
            List<Score> noScore = new ArrayList<>();
            testScoresEnabled.add(score2);
            testScoresEnabled.add(score3);
            List<Goal> enabledGoals = gt.getEnabledGoals();

            gt = reader.read();
            assertTrue(gt.getDisabledGoals().isEmpty());
            assertEquals(2, enabledGoals.size());
            checkGoal("workout", true, noScore, enabledGoals.get(1));
            checkGoal("study", true, testScoresEnabled, enabledGoals.get(0));

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }


}
