package ui.tabs;

import model.Goal;
import model.GoalTracker;
import model.Score;
import ui.frames.GoalTrackerMainGUI;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

import java.util.List;


// CurrentGoals GUI that displays a table of current goals and their stats.
// Allows users to add and rate goals.

public class CurrentGoalsTab extends Tab {
    private DefaultTableModel tableModel;
    private JTextArea goalText;
    private JTextArea ratingText;
    private GoalTracker goalTracker;
    private List<Goal> goals;

    //REQUIRES: a GoalTrackerMainUI controller that holds this tab
    //EFFECTS: constructs a current goals tab with buttons, text fields, and goals table
    public CurrentGoalsTab(GoalTrackerMainGUI controller) {
        super(controller);
        tableModel = new DefaultTableModel();
        goalText = new JTextArea(1, 25);
        ratingText = new JTextArea(1, 25);

        goals = getController().getGoalTracker().getEnabledGoals();
        goalTracker = getController().getGoalTracker();

        placeTitle();
        displayGoalTable();
        displayGoalTextField();
        displayRateTextField();
    }


    //MODIFIES: this
    //EFFECTS: creates and places a panel containing text fields and buttons for adding goals
    public void displayGoalTextField() {
        JLabel goalLabel = new JLabel("Enter a goal name:");
        goalText.setLineWrap(false);
        JButton addButton = new JButton("ADD GOAL");
        addButton.setBackground(Color.GREEN);

        addButtonAction(addButton);

        JPanel newGoalPanel = new JPanel();
        newGoalPanel.setLayout(new FlowLayout());

        newGoalPanel.add(goalLabel);
        newGoalPanel.add(goalText);
        newGoalPanel.add(addButton);

        newGoalPanel.setBackground(Color.LIGHT_GRAY);
        newGoalPanel.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.black));

        this.add(newGoalPanel);
    }

    //MODIFIES: this
    //EFFECTS: creates and places a panel containing text fields and buttons for rating goals
    public void displayRateTextField() {
        JLabel ratingLabel = new JLabel("Enter score from 0-5:");
        ratingText.setLineWrap(false);
        JButton rateButton = new JButton("RATE GOAL");
        rateButton.setBackground(Color.ORANGE);

        rateButtonAction(rateButton);

        JPanel newRatePanel = new JPanel();
        newRatePanel.setLayout(new FlowLayout());

        newRatePanel.add(ratingLabel);
        newRatePanel.add(ratingText);
        newRatePanel.add(rateButton);

        newRatePanel.setBackground(Color.LIGHT_GRAY);
        newRatePanel.setBorder(BorderFactory.createMatteBorder(2, 0, 2, 0, Color.black));

        this.add(newRatePanel);
    }

    //MODIFIES: this, GoalTracker
    //EFFECTS: updates goal table and GoalTracker with new goal after "ADD GOAL" button is pressed.
    //         If no goal is entered in the goal text field, no changes will be made.
    //         If goal name already exists in goal list, not changes will be made.
    private void addButtonAction(JButton addButton) {
        addButton.addActionListener(e -> {
            String inputText = goalText.getText();
            if (!inputText.equals("")) {
                Goal goal = new Goal(inputText);
                if (!goals.contains(goal)) {
                    goalTracker.addGoal(goal);
                    updateTableData();
                }
            }
        });
    }


    //REQUIRES: valid integer to be entered in ratings text field; goal name must exist in goal list
    //MODIFIES: this, GoalTracker
    //EFFECTS: If a valid goal name is entered in goal text field, and a valid integer entered in ratings text field,
    //         goal table and goal tracker will update with new score, otherwise no changes made.
    private void rateButtonAction(JButton rateButton) {
        rateButton.addActionListener(e -> {
            String goalInputText = goalText.getText();
            String scoreInputText = ratingText.getText();

            if (!goalInputText.equals("")) {
                Goal goal = new Goal(goalInputText);

                for (int i = 0; i < goals.size(); i++) {
                    Goal loadedGoal = goals.get(i);
                    if (loadedGoal.equals(goal)) {
                        if (scoreInputText.matches("[0-5]")) {
                            int scoreValue = Integer.parseInt(scoreInputText);
                            Score score = new Score(scoreValue);

                            loadedGoal.addScore(score);
                            updateTableData();
                            break;
                        }
                    }
                }
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: updates goal table with new goals and scores
    private void updateTableData() {
        populateTableModel();
        tableModel.fireTableDataChanged();
        repaint();
        revalidate();
    }

    // MODIFIES: this
    // EFFECTS: displays title message
    private void placeTitle() {
        JLabel title = new JLabel("Current Goals", JLabel.CENTER);
        title.setSize(WIDTH, HEIGHT / 2);
        Font titleFont = title.getFont();
        title.setFont(titleFont.deriveFont(Font.BOLD, 20));

        JLabel subTitle = new JLabel("Type in a goal in 'ENTER GOAL' text field below and click "
                + "'ADD' to add it to your tracker");
        subTitle.setFont(titleFont.deriveFont(Font.BOLD, 10));

        JLabel note = new JLabel("Note: at least 7 days of ratings are needed "
                + "\n to obtain an accurate 'Weekly Average Score");
        note.setFont(titleFont.deriveFont(Font.ITALIC, 8));

        JPanel header = formatTitle(title);
        header.add(subTitle);
        header.add(note);
        this.add(header);
    }

    //MODIFIES: this
    //EFFECTS: displays table of current goals and their score stats
    private void displayGoalTable() {
        tableModel.addColumn("Goal Name");
        tableModel.addColumn("Daily Score");
        tableModel.addColumn("Weekly Average");
        tableModel.addColumn("Lifetime Average");

        populateTableModel();

        JTable goalTable = new JTable(tableModel);
        this.add(new JScrollPane(goalTable));
    }

    // MODIFIES: this
    // EFFECTS: adds goal data to table
    private void populateTableModel() {
        tableModel.setRowCount(0);

        for (Goal g : goals) {
            Object[] rowData = {g.getName(), g.getMostRecentScore(), g.weeklyAverageScore(), g.lifetimeAverageScore()};
            tableModel.addRow(rowData);
        }
    }

      //MODIFIED: this
//    //EFFECTS: creates text labelling daily average score of all current goals
//    private void displayDailyAvgLabel() {
//        JLabel dailyAvg = new JLabel("Daily Average Score: ");
//        Font font = dailyAvg.getFont();
//        dailyAvg.setFont(font.deriveFont(Font.BOLD, 15));
//
//        JPanel avgDisplay = formatTitle(dailyAvg);
//        avgDisplay.add(dailyAvg);
//        this.add(avgDisplay);
//    }
//
//    //MODIFIES: this
//    //EFFECTS: creates label of  daily average score value
//    private void updateDailyAvgScore() {
//        dailyGoalsAvg = getController().getGoalTracker().dailyGoalsAverage();
//        System.out.println("update avg  " + dailyGoalsAvg);
//
//        JLabel dailyAvg = new JLabel(dailyGoalsAvg + "/5");
//        Font font = dailyAvg.getFont();
//        dailyAvg.setFont(font.deriveFont(Font.BOLD, 15));
//
//        JPanel avgDisplay = formatTitle(dailyAvg);
//        avgDisplay.add(dailyAvg);
//        this.add(avgDisplay);
//    }
}
