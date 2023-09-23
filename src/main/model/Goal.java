package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

// Represents a goal with a name, a list of daily scores, and states whether goal is enabled.
// Determines the lifetime and weekly score averages.

public class Goal implements Writable {
    private String name;
    private List<Score> scoreList;
    private boolean isEnabled;
    private static final int WEEK_LENGTH = 7;

    // EFFECTS: constructs a goal with a given name, an enabled status and empty score list
    public Goal(String name) {
        this.name = name;
        this.isEnabled = true;
        this.scoreList = new ArrayList<>();
    }

    // EFFECTS: returns true if Goal is enabled, false otherwise
    public boolean isEnabled() {
        return this.isEnabled;
    }

    // MODIFIES: this
    // EFFECTS: disables goal (sets isEnabled to false)
    public void disableGoal() {
        this.isEnabled = false;
    }

    // MODIFIES: this
    // EFFECTS: enables goal - changes enabled status to true
    public void enableGoal() {
        this.isEnabled = true;
    }

    // MODIFIES: this
    // EFFECTS: adds a score of given value to scoreList on today's date. If a score is already entered on
    //          today's date, replace with given value - otherwise add score to the end of the list.
    public void addScore(Score score) {
        // Score score = new Score(scoreVal);
        if (scoreList.size() == 0) {
            this.scoreList.add(score);
        } else {
            int lastIndex = scoreList.size() - 1;
            Score lastScore = scoreList.get(lastIndex);
            if (isWithinXDays(score.getScoreDate(), lastScore.getScoreDate(), 0)) {
                scoreList.set(lastIndex, score);
            } else {
                this.scoreList.add(score);
            }
        }
        EventLog.getInstance().logEvent(new Event("Score of " + score.getScoreVal()
                + " added to " + this.getName()));
    }

    // EFFECTS: returns average of all scores, -1 if no score entered
    public double lifetimeAverageScore() {
        if (scoreList.isEmpty()) {
            return -1;
        }
        int total = 0;
        for (Score s : scoreList) {
            total += s.getScoreVal();
        }
        return total / scoreList.size();
    }

    // EFFECTS: return average score of last 7 days, or -1 if less than 7 daily scores entered
    public double weeklyAverageScore() {
        if (scoreList.size() < WEEK_LENGTH) {
            return -1;
        }
        int total = 0;
        for (int i = scoreList.size() - 1; i >= scoreList.size() - WEEK_LENGTH; i--) {
            Score score = scoreList.get(i);
            total += score.getScoreVal();
        }
        return total / WEEK_LENGTH;
    }

    // REQUIRES: numDays >= 0
    // EFFECTS: Calculates the absolute difference between two dates and checks if the
    //          absolute difference is equal to numDays.
    public boolean isWithinXDays(LocalDate date1, LocalDate date2, int numDays) {
        long daysDifference = Math.abs(ChronoUnit.DAYS.between(date1, date2));
        return daysDifference <= numDays;
    }

    // getters
    // EFFECTS: returns name of Goal
    public String getName() {
        return this.name;
    }


    //EFFECTS: returns list of daily scores
    public List<Score> getScoreList() {
        return this.scoreList;
    }

    // REQUIRES: 0 <= index < scoreList.size()
    // EFFECTS: returns most recently entered score value
    public int getMostRecentScore() {
        if (scoreList.size() == 0) {
            return 0;
        }
        int lastIndex = scoreList.size() - 1;
        return this.scoreList.get(lastIndex).getScoreVal();
    }

    public void setScoreList(List<Score> scores) {
        this.scoreList = scores;
    }


    @Override
    // The following code is modified from the Thingy Model class of the JsonSerializationDemo:
    //   https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/Thingy.java
    // EFFECTS: returns Goal as JsonObject
    public JSONObject toJson() {
        JSONObject jsonGoal = new JSONObject();
        jsonGoal.put("name", name);
        jsonGoal.put("isEnabled", isEnabled);
        JSONArray jsonScores = new JSONArray();

        for (Score s : scoreList) {
            jsonScores.put(s.toJson());
        }
        jsonGoal.put("scores", jsonScores);
        return jsonGoal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Goal goal = (Goal) o;
        return Objects.equals(name.toLowerCase(), goal.name.toLowerCase());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
