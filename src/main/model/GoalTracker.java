package model;


import org.json.JSONArray;
import org.json.JSONObject;
import persistance.Writable;

import java.util.ArrayList;
import java.util.List;

// Represents a GoalTracker with
public class GoalTracker implements Writable {
    private List<Goal> enabledGoals;
    private List<Goal> disabledGoals;

    // EFFECTS: constructs a goal tracker with no enabled and no disabled goals
    public GoalTracker() {
        this.enabledGoals = new ArrayList<>();
        this.disabledGoals = new ArrayList<>();
    }

    // REQUIRES: goal cannot already be in the list. List can only contain enabled goals.
    // MODIFIES: this
    // EFFECTS: adds a new goal to the enabled goals list. If goal with same name exists in list, does not add.
    public void addGoal(Goal goal) {
        for (Goal g : enabledGoals) {
            if (g.getName().equalsIgnoreCase(goal.getName())) {
                return;
            }
        }
        this.enabledGoals.add(goal);
        EventLog.getInstance().logEvent(new Event(goal.getName() + " added to goal tracker!"));
    }

    // REQUIRES: non-empty enabled goal list
    // MODIFIES: this
    // EFFECTS: removes goal from enabled goals list, sets to disabled and adds to disabled goals list
    public void removeGoal(Goal goal) {
        this.enabledGoals.remove(goal);
        goal.disableGoal();
        this.disabledGoals.add(goal);
    }

    // REQUIRES: non-empty enabled goal list
    // EFFECTS: calculates the average score of ALL enabled goals for the day.
    public double dailyGoalsAverage() {
        if (enabledGoals.size() == 0) {
            return 0;
        }
        double total = 0;
        for (Goal g : enabledGoals) {
            total += g.getMostRecentScore();
        }
        return total / enabledGoals.size();
    }

    @Override
    // EFFECTS: returns enabled and disabled goals in goal tracker as a JSON array
    public JSONObject toJson() {
        JSONObject jsonGoalTracker = new JSONObject();
        JSONArray jsonEnabledGoals = new JSONArray();
        for (Goal g : this.enabledGoals) {
            jsonEnabledGoals.put(g.toJson());
        }
        jsonGoalTracker.put("enabledGoals", jsonEnabledGoals);
        JSONArray jsonDisabledGoals = new JSONArray();
        for (Goal g : this.disabledGoals) {
            jsonDisabledGoals.put(g.toJson());
        }
        jsonGoalTracker.put("disabledGoals", jsonDisabledGoals);
        return jsonGoalTracker;
    }


    //getters
    public List<Goal> getEnabledGoals() {
        return this.enabledGoals;
    }

    public List<Goal> getDisabledGoals() {
        return this.disabledGoals;
    }

    public void setEnabledGoals(List<Goal> goals) {
        this.enabledGoals = goals;
    }

    public void setDisabledGoals(List<Goal> goals) {
        this.disabledGoals = goals;
    }

    // For code coverage only:

    // REQUIRES: goal cannot already be in the list. List can only contain disabled goals.
    // MODIFIES: this
    // EFFECTS: adds a  disabled goal to the disabled goals list. If goal with same name exists in list, does not add.
    public void addDisabledGoal(Goal goal) {
        for (Goal g : disabledGoals) {
            if (g.getName().equalsIgnoreCase(goal.getName())) {
                return;
            }
        }
        this.disabledGoals.add(goal);
    }

}

