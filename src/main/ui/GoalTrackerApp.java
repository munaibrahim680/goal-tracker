package ui;

import model.Goal;
import model.GoalTracker;
import model.Score;
import persistance.JsonReader;
import persistance.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;


// Goal Tracker application
public class GoalTrackerApp {
    private static final String JSON_STORE = "./data/goalTracker.json";
    private Scanner input;
    private GoalTracker goalTracker;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    // EFFECTS: runs goal tracker
    public GoalTrackerApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        input = new Scanner(System.in);
        input.useDelimiter("\n");
        initGoalTracker();
        runGoalTracker();
    }

    // The following code is modified from the TellerApp UI class in the Teller application:
    //   https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: displays load menu and initializes goal tracker
    private void initGoalTracker() {
        System.out.println("\nDo you want to load the previously saved goal tracker");
        System.out.println("\ty -> load tracker");
        System.out.println("\tn -> start new tracker");

        String command = input.next();
        command = command.toLowerCase();

        if (command.equalsIgnoreCase("y")) {
            loadGoalTracker();
        } else {
            goalTracker = new GoalTracker();
        }
    }


    // The following code is modified from the TellerApp UI class in the Teller application:
    //     https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: processes user input
    private void runGoalTracker() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            displayMenu();
            command = input.next();
            command = command.toLowerCase();
            processCommand(command);
        }
    }

    // The following code is modified from the TellerApp UI class in the Teller application:
    //     https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: processes user command
    private void processCommand(String command) {
        if (command.equals("a")) {
            addGoal();
        } else if (command.equals("v")) {
            listCurrentGoals();
        } else if (command.equals("d")) {
            disableGoal();
        } else if (command.equals("r")) {
            rateGoal();
        } else if (command.equals("s")) {
            viewStats();
        } else if (command.equals("q")) {
            quit();
        } else {
            System.out.println("Selection not valid...");
        }
    }


    // The following method name is taken from the TellerApp UI class in the Teller application:
    //     https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // EFFECTS: displays menu of options to user
    private void displayMenu() {
        System.out.println("\nSelect an option from the list:");
        System.out.println("\ta -> add goal");
        System.out.println("\tv -> view current goals");
        System.out.println("\td -> disable goal");
        System.out.println("\tr -> rate goal");
        System.out.println("\ts -> score statistics");
        System.out.println("\tq -> quit");
    }


    // MODIFIES: this
    // EFFECTS: adds a goal to goal tracker when given a goal name
    private void addGoal() {
        System.out.println("Enter a goal");
        String goalName = input.next();
        Goal goal = new Goal(goalName);
        goalTracker.addGoal(goal);
    }

    // EFFECTS: displays a list of current (enabled) goals
    private void listCurrentGoals() {
        List<Goal> currentGoals = goalTracker.getEnabledGoals();
        if (currentGoals.isEmpty()) {
            System.out.println("You have no current goals.");
            runGoalTracker();
        }
        System.out.println("Current Goals:");
        for (Goal goal : currentGoals) {
            System.out.println(goal.getName());
        }
    }

    // MODIFIES: this
    // EFFECTS: given valid goal name, removes goal from goal tracker enabled goals list and adds to disabled goals list
    private void disableGoal() {
        List<Goal> currentGoals = goalTracker.getEnabledGoals();
        if (currentGoals.isEmpty()) {
            System.out.println("You have current goals to disable.");
            runGoalTracker();
        }
        Goal goal = null;
        while (goal == null) {
            goal = selectGoal();
            if (goal == null) {
                System.out.println("Invalid goal. Please select another goal.");
            }
        }
        goalTracker.removeGoal(goal);
    }

    // EFFECTS: displays stats options
    private void viewStats() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            statsMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equals("b")) {
                keepGoing = false;
            } else {
                statsCommand(command);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: processes user command for stats menu
    private void statsCommand(String command) {
        if (command.equals("d")) {
            viewDailyAverageScore();
        } else if (command.equals("w")) {
            viewWeeklyAverage();
        } else if (command.equals("l")) {
            viewLifetimeAverage();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    // EFFECTS: displays stats options
    private void statsMenu() {
        System.out.println("\nSelect a statistic from below");
        System.out.println("\td -> daily average score of ALL enabled goals");
        System.out.println("\tw -> weekly average score for a selected goal");
        System.out.println("\tl -> lifetime average score for a selected goal");
        System.out.println("\tb -> back to main menu");
    }

    // EFFECTS: displays the average daily score of all enabled goals in goal tracker.
    //          unrated goals are considered as having a score of 0 and calculation are made accordingly.
    private void viewDailyAverageScore() {
        List<Goal> currentGoals = goalTracker.getEnabledGoals();
        if (currentGoals.isEmpty()) {
            System.out.println("You have no current goals");
        } else {
            double average = goalTracker.dailyGoalsAverage();
            System.out.println("Your average score for the day is " + average + "/5!");
        }
    }

    // EFFECTS: displays average score of the last 7 days of a selected goal
    private void viewWeeklyAverage() {
        if (goalTracker.getEnabledGoals().size() == 0) {
            System.out.println("You have no current goals");
        } else {
            Goal goal = null;
            while (goal == null) {
                goal = selectGoal();
                if (goal == null) {
                    System.out.println("Invalid selection. Please choose a goal from the list.");
                }
            }

            double average = goal.weeklyAverageScore();
            if (goal.weeklyAverageScore() == -1) {
                System.out.println("ERROR: At least 7 entries are needed to calculate your weekly score average.");
            } else {
                System.out.println("Your weekly average score for " + goal.getName() + " is " + average + "/5!");
            }
        }
    }


    // EFFECTS: displays average of all scores entered for a selected goal
    private void viewLifetimeAverage() {
        if (goalTracker.getEnabledGoals().size() == 0) {
            System.out.println("You have no current goals");
        } else {
            Goal goal = null;
            while (goal == null) {
                goal = selectGoal();
                if (goal == null) {
                    System.out.println("Invalid selection. Please choose a goal from the list.");
                }
            }

            double average = goal.lifetimeAverageScore();
            if (goal.lifetimeAverageScore() == -1) {
                System.out.println("ERROR: No scores have been entered for " + goal.getName());
            }
            System.out.println("Your lifetime average score for " + goal.getName() + " is " + average + "/5!");
        }
    }


    // REQUIRES: must enter number corresponding to goal (input must be positive integer on list).
    // EFFECTS: displays a list of numbered goals and allows user to select goal by entering corresponding number
    private Goal selectGoal() {
        List<Goal> currentGoals = goalTracker.getEnabledGoals();
        System.out.println("Select a goal NUMBER from the list:");
        for (int i = 0; i < currentGoals.size(); i++) {
            int goalNum = i + 1;
            System.out.println(goalNum + ": " + currentGoals.get(i).getName());
        }
        int goalNum = input.nextInt();
        goalNum--;
        if (goalNum < 0 || goalNum >= currentGoals.size()) {
            return null;
        }
        Goal goal = currentGoals.get(goalNum);
        System.out.println("You selected " + goal.getName());
        return goal;
    }

    // REQUIRES: rating must be int, 0 <= score <=5
    // EFFECTS: allows user to rate a success in achieving a selected goal on a scale of 0-5.
    private void rateGoal() {
        if (goalTracker.getEnabledGoals().size() == 0) {
            System.out.println("You have no current goals :(");
        } else {
            Goal goal = null;
            while (goal == null) {
                goal = selectGoal();
                if (goal == null) {
                    System.out.println("Invalid selection. Please choose a goal from the list.");
                }
            }
            while (true) {
                System.out.println("\n Rate your success in achieving this goal on a scale of 0 to 5.");
                int scoreVal = input.nextInt();
                Score score = new Score(scoreVal);
                if (scoreVal >= 0 && scoreVal <= 5) {
                    goal.addScore(score);
                    System.out.println("You've entered a score of " + score.getScoreVal() + " for " + goal.getName());
                    break;
                }
                System.out.println("Invalid score! Please enter a valid rating.");
            }
        }
    }

    // The following code is modified from the TellerApp UI class in the Teller application:
    //     https://github.students.cs.ubc.ca/CPSC210/TellerApp/blob/main/src/main/ca/ubc/cpsc210/bank/ui/TellerApp.java
    // MODIFIES: this
    // EFFECTS: processes user input on quit menu
    private void quit() {
        boolean keepGoing = true;
        String command = null;

        while (keepGoing) {
            quitMenu();
            command = input.next();
            command = command.toLowerCase();
            if (command.equalsIgnoreCase("n")) {
                keepGoing = false;
            } else if (command.equalsIgnoreCase("y")) {
                saveGoalTracker();
                keepGoing = false;
            }
        }
        System.out.println("\nGood luck reaching your goals ^_^!");
        System.exit(0);
    }

    // EFFECTS: displays the quit menu to user
    private void quitMenu() {
        System.out.println("\nWould you like to save your progress?");
        System.out.println("\ty -> save today's scores and goals ");
        System.out.println("\tn -> don't save today's scores and goals");
    }

    // The following code is modified from the WorkRoomApp UI class of the JsonSerializationDemo:
    //   https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/ui/WorkRoomApp.java
    // EFFECTS: saves the goal tracker to file
    private void saveGoalTracker() {
        try {
            jsonWriter.open();
            jsonWriter.write(goalTracker);
            jsonWriter.close();
            System.out.println("Saved progress to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // The following code is taken from the WorkRoomApp UI class of the JsonSerializationDemo:
    //   https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo/blob/master/src/main/ui/WorkRoomApp.java
    // MODIFIES: this
    // EFFECTS: loads goal tracker from file
    private void loadGoalTracker() {
        try {
            goalTracker = jsonReader.read();
            System.out.println("Loaded Goal Tracker from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }
}
