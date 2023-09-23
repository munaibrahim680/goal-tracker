package ui.frames;

import model.EventLog;
import model.GoalTracker;
import model.LogPrinter;
import persistance.JsonWriter;
import ui.tabs.CurrentGoalsTab;
import ui.tabs.QuitTab;
import ui.tabs.TrendsTab;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Main GoalTracker GUI - sets up tabs
public class GoalTrackerMainGUI extends JFrame {
    private static final int GOALS_TAB_INDEX = 0;
    private static final int STATS_TAB_INDEX = 1;
    private static final int QUIT_TAB_INDEX = 2;
    private static final int WIDTH = 650; //window width
    private static final int HEIGHT = 650; //window height

    private JsonWriter jsonWriter;
    private static final String JSON_STORE = "./data/goalTracker.json";
    private JTabbedPane sidebar;
    private GoalTracker goalTracker;

    //MODIFIES: this
    //EFFECTS: creates GoalTrackerMainUI, initializes goal tracker and displays  tabs & sidebar
    public GoalTrackerMainGUI(GoalTracker goalTracker) {
        super("GoalTracker Console");
        jsonWriter = new JsonWriter(JSON_STORE);

        setSize(WIDTH, HEIGHT);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                LogPrinter lp = new LogPrinter();
                lp.printLog(EventLog.getInstance());
                dispose();
            }
        });

        this.goalTracker = goalTracker;

        sidebar = new JTabbedPane();
        sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadTabs();
        add(sidebar);

        setVisible(true);
    }

    //THis code is modified from SmartHomeUI class in the SmartHome starter:
    //https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/blob/main/SmartHome/src/main/ui/SmartHomeUI.java
    // MODIFIES: this
    // EFFECTS: adds home tab, trends tab, and  quit tab to this UI
    private void loadTabs() {
        JPanel goalsTab = new CurrentGoalsTab(this);
        JPanel statsTab = new TrendsTab(this);
        JPanel quitTab = new QuitTab(this);

        sidebar.add(goalsTab, GOALS_TAB_INDEX);
        sidebar.setTitleAt(GOALS_TAB_INDEX, "Current Goals");
        sidebar.add(statsTab, STATS_TAB_INDEX);
        sidebar.setTitleAt(STATS_TAB_INDEX, "Trends");
        sidebar.add(quitTab, QUIT_TAB_INDEX);
        sidebar.setTitleAt(QUIT_TAB_INDEX, "Save & Quit");
    }

    //getters and setters:

    public GoalTracker getGoalTracker() {
        return this.goalTracker;
    }

    public void setGoalTracker(GoalTracker gt) {
        this.goalTracker = gt;
    }

    public JsonWriter getJsonWriter() {
        return this.jsonWriter;
    }

    public String getJsonStore() {
        return JSON_STORE;
    }


}
