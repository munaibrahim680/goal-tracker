# Goal Tracker App - My CPSC 210 Project

## Introduction

As someone with a lot of personal projects, I've struggled with taking all baby steps needed to finish them. In fact,
when it comes down to setting goals, long-term tasks can be extremely daunting and often fall through. However, it's
the *small*, daily achievements that yield **big** results in the long-run. Achieving these baby
steps will not only boost morale, but also increase motivation to keep at it.

The Goal Tracker app is for anyone who's looking for a little more motivation and accountability when it comes down
achieving their daily goals. With this app, you not only can you log all of your goals, but you can also track your 
progress over time. Want to get fit? Then add "workout for 30 minutes" to your tracker! Or maybe you want to become a 
pottery expert? Just add "practice pottery" to the list! Goal Tracker makes things easy.

## User Stories

- As a user, I want to be able to add goals to my goal tracker
- As a user, I want to be able to view my current goals
- As a user, I want to be able to disable goals from my goal tracker
- As a user, I want to be able to rate my success in achieving each daily goal on a scale of 0 to 5
- As a user, I want to be able to view my daily average score of all current goals
- As a user, I want to be able to view a weekly average score for each given goal
- As a user, I want to be able to view a lifetime average score for each given goal
- As a user, I want the option to save my goals to the tracker before I quit if I so choose
- As a user, I want the option to load my tracker from file if I so choose. 


## Instructions for Grader

- You can add a goal the current goals table by navigating to the "Current Goal" tab (in the left sidebar) and 
  typing in a goal in the text field next to the "Enter Goal Name" label. Then click the "Add Goal button" to add 
  a goal to the table.
- You can rate a goal on a scale of 0-5  by first typing a goal name that is already listed
  in the aforementioned text field (case does not matter). Then enter a valid integer [0-5] in the text field next to 
  the "Enter score..." label 
  and click the "RATE GOAL" button.
- You can locate my visual component by navigating to the "Trends" tab on the left sidebar 
   and clicking the "Daily Trends" button
- You can save the state of my application by navigating to the "Save & Quit" tab in the left sidebar and clicking the
  "Save" button to save progress, and then "Quit" to close the application.  You can also quit without saving by 
   pressing the "Quit" button first, or the X at the top of the frame.
- You can reload the state of my application clicking "Load" button on the welcome screen when you first 
  run the application 

## Phase 4: Task 2

*Event log sample:* <br/>
study added to goal tracker! <br/>
workout added to goal tracker! <br/>
read added to goal tracker! <br/>
Score of 3 added to read <br/>
Score of 5 added to study <br/>
Score of 4 added to study <br/>
Score of 1 added to workout

## Phase 4: Task 3

In reviewing the current design of my Goal Tracker application, the first thing that stands out to me is the over-
coupling that exists in the CurrentGoalsTab class. To reduce this coupling, I would remove the association of 
CurrentGoalsTab to Goal and GoalTracker (ie remove the Goal and GoalTracker fields the CurrentGoalsTab class). Instead, 
these two classes can be accessed by CurrentGoalsTab through the getController() method inherited from its super, Tab. 
In doing so, CurrentGoalsTab will gain access to GoalTrackerMainGUI, and from there, it can then call other getter 
methods to gain access to GoalTracker and Goal as follows: 
<br/>

    getController.getGoalTracker
    // This gives CurrentGoalsTab access to the GoalTrackerType istantitated by GoalTrackerMainGUI

    getController.getGoalTracker.getEnabledGoals
    // This CurrentGoalsTab access to List<Goal> in GoalTracker 

<br/>

The second major change I would make is introduce a new abstract class called GoalTrackerGUI which would be extended
by GoalTrackerLoadGUI and GoalTrackerMainGUI. This will reduce any duplication that exists between GoalTrackerLoadGUI 
and GoalTrackerMainGUI. This new abstract class would abstract any similarity between the aforementioned classes, such
as setting frame dimensions. Specifically I would declare the following fields:

    public abstract class GoalTrackerGUI extends JFrame {

        private final static int JSON_STORE = "./data/goalTracker.json";
        private final static int WIDTH = 650;
        private final static in HEIGHT = 650;
        private JsonReader jsonReader;
        private JsonWriter jsonWriter;
    
    ....
    }

Declaring WIDTH and HEIGHT in the abstract class makes it such that desired changes in frame dimensions only needs to be 
done in one location (the subclasses GoalTrackerLoadGUI and GoalTrackerMainGUI would inherit these fields). I would also 
declare JsonReader and JsonWriter as fields in this abstract classes so that both subclasses inherit them.  This way, 
if I want to redesign app such that a user can be given the option to load data from  GoalTrackerMainGUI 
(as opposed to only doing so from the load screen/GoalTrackerLoadGUI), the program can be refactored easily.