package model;

import org.json.JSONObject;
import persistance.Writable;

import java.time.LocalDate;

// Represents a score with a set value (int) on a given day (today's date).
public class Score implements Writable {
    private int scoreVal;
    private LocalDate scoreDate;

    // REQUIRES: 0 <= score <= 5
    // EFFECTS: constructs a score with score number set to given integer
    //          and date set to day in which score is entered in the format of YYYY-MM-DD
    public Score(int value) {
        this.scoreVal = value;
        this.scoreDate = LocalDate.now();
    }

    // REQUIRES: 0 <= score <= 5
    // EFFECTS: Constructs a score with a given value and date. This is for testing only.
    public Score(int value, LocalDate date) {
        this.scoreVal = value;
        this.scoreDate = date;
    }


    // getters
    // EFFECTS: returns value of the score.
    public int getScoreVal() {
        return this.scoreVal;
    }

    // EFFECTS: returns date of the score.
    public LocalDate getScoreDate() {
        return this.scoreDate;
    }



    @Override
    // The following code is modified from the Thingy Model class of the JsonSerializationDemo:
    //   https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/model/Thingy.java
    // EFFECTS: returns Score as JSONObject
    public JSONObject toJson() {
        JSONObject jsonScore = new JSONObject();
        jsonScore.put("value", scoreVal);
        jsonScore.put("date", scoreDate);
        return jsonScore;
    }
}
