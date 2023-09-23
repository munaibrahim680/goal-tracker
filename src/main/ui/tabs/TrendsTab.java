package ui.tabs;

import ui.frames.GoalTrackerMainGUI;

import javax.swing.*;
import java.awt.*;

// Trends Tab GUI displays button and visual component
public class TrendsTab extends Tab {

    //REQUIRES: a GoalTrackerMainGUI controller that holds this tab
    //EFFECTS: creates a tab with a title message and button
    public TrendsTab(GoalTrackerMainGUI controller) {
        super(controller);
        this.setLayout(new GridLayout(2, 1));

        addMessage();
        addButton();

        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates and displays a panel with trends button that triggers a response when clicked
    private void addButton() {
        JButton trendsButton = new JButton("Daily Trends");
        JPanel buttonRow = formatButtonRow(trendsButton);

        trendsButton.addActionListener(e -> {
            visualComponent();
        });

        this.add(buttonRow);
    }

    //MODIFIES: this
    //EFFECTS: creates and places a panel with title message
    private void addMessage() {
        JLabel message = new JLabel("Click button below for visual component:");
        message.setSize(WIDTH, HEIGHT / 2);
        Font msgFont = message.getFont();
        message.setFont(msgFont.deriveFont(Font.BOLD, 20));

        JPanel msgRow = new JPanel(new FlowLayout());
        msgRow.add(message);
        this.add(msgRow);
    }

    //MODIFIES: imgDialog
    //EFFECTS: creates a new dialog that displays an image of a graph image
    private void visualComponent() {
        JDialog imgDialog = new JDialog();
        imgDialog.setSize(500, 500);

        ImageIcon graphIcon = new ImageIcon(TrendsTab.class.getResource("graph.png"));
        Image graphImg = graphIcon.getImage();
        Image scaleGraphImg = graphImg.getScaledInstance(500, 400, Image.SCALE_REPLICATE);

        ImageIcon scaleGraphIcon = new ImageIcon(scaleGraphImg);
        JLabel label = new JLabel(scaleGraphIcon);
        imgDialog.add(label);

        imgDialog.validate();
        imgDialog.repaint();

        imgDialog.setVisible(true);
    }
}
