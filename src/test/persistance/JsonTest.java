package persistance;

import model.Goal;
import model.Score;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//The following code is modelled after  //todo - citation
public class JsonTest {
    List<Score> testScoresEnabled;
    List<Score> testScoresDisabled;
    Score score1;
    Score score2;
    Score score3;

    @BeforeEach
    protected void runBefore() {
        testScoresEnabled = new ArrayList<>();
        testScoresDisabled = new ArrayList<>();
        score1 = new Score(5, LocalDate.of(2023, 7, 26));
        score2 = new Score(4, LocalDate.of(2023, 7, 26));
        score3 = new Score(3, LocalDate.of(2023, 7, 27));
    }

    protected void checkGoal(String name, boolean isEnabled, List<Score> scores, Goal goal) {
        int i = 0;
        List<Score> scoreList = goal.getScoreList();
        assertEquals(scores.size(), scoreList.size());
        if(scores.isEmpty()) {
            assertTrue(scoreList.isEmpty());
        } else {
            for(Score s : scoreList) {
                assertEquals(scores.get(i).getScoreVal(), s.getScoreVal());
                assertEquals(scores.get(i).getScoreDate(), s.getScoreDate());
                i++;
            }
        }
        assertEquals(isEnabled, goal.isEnabled());
        assertEquals(name, goal.getName());
    }
}
