package persistance;

import model.Goal;
import model.GoalTracker;
import model.Score;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

//The following code is modified from the JsonReader class of the JsonSerializationDemo:
//https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/persistence/JsonReader.java

// Represents a reader that reads goal tracker from JSON  data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads workroom from file and returns it;
    // throws IOException if an error occurs reading data from file
    public GoalTracker read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGoalTracker(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }


    // MODIFIES: goal tracker
    // EFFECTS: parses goalTracker from JSON object and returns it
    private GoalTracker parseGoalTracker(JSONObject jsonObject) {
        GoalTracker gt = new GoalTracker();
        List<Goal> enabled = parseGoalList(jsonObject, "enabledGoals");
        List<Goal> disabled = parseGoalList(jsonObject, "disabledGoals");
        gt.setEnabledGoals(enabled);
        gt.setDisabledGoals(disabled);
        return gt;
    }


    // MODIFIES: goals
    // EFFECTS: parses goal from JSON object, adds it to respective goal list, and returns list (goals)
    private List<Goal> parseGoalList(JSONObject jsonObject, String goalKey) {
        JSONArray jsonGoalsArray = jsonObject.getJSONArray(goalKey);
        List<Goal> goals = new ArrayList<>();

        for (Object jsonGoal : jsonGoalsArray) {
            JSONObject nextGoal = (JSONObject) jsonGoal;
            Goal g = new Goal(nextGoal.getString("name"));
            boolean isEnabled = nextGoal.getBoolean("isEnabled");
            if (isEnabled) {
                g.enableGoal();
            } else {
                g.disableGoal();
            }
            List<Score> scores = parseScoreList(nextGoal, "scores");
            g.setScoreList(scores);
            goals.add(g);
        }
        return goals;
    }

    // MODIFIES: scores
    // EFFECTS: parses score from JSON object, adds it to score list, and returns list (scores)
    private List<Score> parseScoreList(JSONObject jsonObject, String scoreKey) {
        JSONArray jsonScoresArray = jsonObject.getJSONArray(scoreKey);
        List<Score> scores = new ArrayList<>();

        for (Object jsonScore : jsonScoresArray) {
            JSONObject nextScore = (JSONObject) jsonScore;
            String date = nextScore.getString("date");

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate localDate = LocalDate.parse(date, formatter);
            Score s = new Score(nextScore.getInt("value"), localDate);

            scores.add(s);
        }
        return scores;
    }
}
