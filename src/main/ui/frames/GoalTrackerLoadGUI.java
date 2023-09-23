package ui.frames;


import model.GoalTracker;
import persistance.JsonReader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// GoalTrackerLoadUI - creates load screen for user to interact with
public class GoalTrackerLoadGUI extends JFrame {
    public static final int WIDTH = 650; //window width
    public static final int HEIGHT = 650; //window height

    private JsonReader jsonReader;
    private static final String JSON_STORE = "./data/goalTracker.json";

    private JLabel loadLabel;
    private static final String LOAD_STRING = "Would you like to load file or start a new goal tracker?";


    //This code is loosely based on the SmartHomeUI class of the SmartHome starter:
    //https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/blob/main/SmartHome/src/main/ui/SmartHomeUI.java
    //MODIFIES: this
    //EFFECTS: creates load screen with load or new buttons, and load instructions
    public GoalTrackerLoadGUI() {
        super("GoalTracker Console");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        jsonReader = new JsonReader(JSON_STORE);

        loadMessage();
        placeLoadNewButtons();

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates and displays load option at top of console
    private void loadMessage() {
        setLayout(new GridLayout(2, 1));
        loadLabel = new JLabel(LOAD_STRING, JLabel.CENTER);
        loadLabel.setSize(WIDTH, HEIGHT);
        this.add(loadLabel);
    }

    //The following code is modified from the ui.tabs.ReportTab Class in the SmartHome starter:
    //https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/blob/main/SmartHome/src/
    // main/ui/tabs/ReportTab.java
    //MODIFIES: this
    //EFFECTS: creates Load and New buttons that load previous file or
    // starts new tracker respectively, and change greeting message
    private void placeLoadNewButtons() {
        JButton loadFileButton = new JButton("Load");
        JButton newFileButton = new JButton("New");

        JPanel buttonRow = formatButtonRow(loadFileButton);
        buttonRow.add(newFileButton);

        loadFileButton.addActionListener(e -> {
            loadGoalTracker();
        });

        newFileButton.addActionListener(e -> {
            GoalTracker goalTracker = new GoalTracker();
            new GoalTrackerMainGUI(goalTracker);
            this.dispose();
        });

        this.add(buttonRow);
    }

    //The following code is taken from the Tab UI class in SmartHomes:
    //https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/blob/main/SmartHome/src/main/ui/tabs/Tab.java
    //MODIFIES: this
    //EFFECTS: creates and returns row with button includes
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    // MODIFIES: this
    // EFFECTS: loads GoalTracker from file to GoalTrackerMainUI, then closes this frame
    private void loadGoalTracker() {
        try {
            loadLabel.setText("Loaded Goal Tracker from " + JSON_STORE);
            new GoalTrackerMainGUI(jsonReader.read());
            this.dispose();

        } catch (IOException e) {
            loadLabel.setText("Unable to read from file: " + JSON_STORE);
        }
    }


    //getters
//    public GoalTracker getGoalTracker() {
//        return this.goalTracker;
//    }
//
//    public void setGoalTracker(GoalTracker gt) {
//        this.goalTracker = gt;
//    }

    public JsonReader getJsonReader() {
        return this.jsonReader;
    }

    public String getJsonStore() {
        return JSON_STORE;
    }
}
