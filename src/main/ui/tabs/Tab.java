package ui.tabs;

import ui.frames.GoalTrackerMainGUI;

import javax.swing.*;
import java.awt.*;

// Abstract Tab class containing formatting methods used in subclasses
public abstract class Tab extends JPanel {
    private final GoalTrackerMainGUI controller;

    //This code is taken from the ui.tabs.Tab class in SmartHome starter:
    // https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/blob/main/SmartHome/src/main/ui/tabs/Tab.java
    //REQUIRES: GoalTrackerUI controller that holds this tab
    public Tab(GoalTrackerMainGUI controller) {
        this.controller = controller;
    }

    //This code is taken from ui.tabs.Tab class in the SmartHome starter:
    // https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/blob/main/SmartHome/src/main/ui/tabs/Tab.java
    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());

        p.add(b);

        return p;
    }

    //EFFECTS: creates and returns 2 rows with title and subtitle
    public JPanel formatTitle(JLabel title) {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.add(title);

        return p;
    }

    //This code is taken from ui.tabs.Tab class in the SmartHome starter:
    // https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters/blob/main/SmartHome/src/main/ui/tabs/Tab.java
    //EFFECTS: returns the GoalTrackerMainUI controller for this tab
    public GoalTrackerMainGUI getController() {
        return controller;
    }

}
