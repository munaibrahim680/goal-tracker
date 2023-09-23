package ui.tabs;

import model.EventLog;
import model.GoalTracker;
import model.LogPrinter;
import persistance.JsonWriter;
import ui.frames.GoalTrackerMainGUI;

import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

// Quit Screen GUI
public class QuitTab extends Tab {
    private static final String QUIT_MESSAGE = "Would you like to quit?";
    private static final String SAVE_INSTRUCTIONS = "Click 'SAVE' before quitting, otherwise  progress will be lost";
    private JLabel saveLabel;

    //REQUIRES: a GoalTrackerMainUI controller that holds this tab
    //EFFECTS: constructs a tab with save instructions and save/quit buttons
    public QuitTab(GoalTrackerMainGUI controller) {
        super(controller);
        this.setLayout(new GridLayout(2, 1));

        quitMessage();
        placeSaveQuitButtons();

        setVisible(true);
    }

    //EFFECTS: creates and displays quit screen messages save instructions
    private void quitMessage() {
        JLabel quitLabel = new JLabel(QUIT_MESSAGE, JLabel.CENTER);
        quitLabel.setSize(WIDTH, HEIGHT / 2);
        Font titleFont = quitLabel.getFont();
        quitLabel.setFont(titleFont.deriveFont(Font.BOLD, 20));

        saveLabel = new JLabel(SAVE_INSTRUCTIONS, JLabel.CENTER);

        JPanel quitScreen = new JPanel();
        quitScreen.setLayout(new GridLayout(2, 1));
        quitScreen.add(quitLabel);
        quitScreen.add(saveLabel);
        this.add(quitScreen);
    }

    //EFFECTS: creates SAVE and QUIT buttons that save to file and quit the application
    private void placeSaveQuitButtons() {
        JButton saveButton = new JButton("Save");
        JButton quitButton = new JButton("Quit");

        JPanel buttonRow = formatButtonRow(saveButton);
        buttonRow.add(quitButton);

        saveButton.addActionListener(e -> {
            saveGoalTracker();
        });

        quitButton.addActionListener(e -> {
            quitGoalTracker();
        });

        this.add(buttonRow);
    }

    //EFFECTS: saves goal tracker to file
    private void saveGoalTracker() {
        JsonWriter jsonWriter = getController().getJsonWriter();
        String jsonStore = getController().getJsonStore();
        GoalTracker goalTracker = getController().getGoalTracker();

        try {
            jsonWriter.open();
            jsonWriter.write(goalTracker);
            jsonWriter.close();
            saveLabel.setText("Saved progress to " + jsonStore);
        } catch (FileNotFoundException e) {
            saveLabel.setText("Unable to write to file " + jsonStore);
        }
    }

    //EFFECTS: prints log of events, closes the GoalTrackerMainUI and quits the applications
    private void quitGoalTracker() {
        LogPrinter lp = new LogPrinter();
        lp.printLog(EventLog.getInstance());
        getController().dispose();
    }
}
