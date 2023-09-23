package model;

import org.w3c.dom.ls.LSOutput;

import javax.security.auth.login.LoginException;

public class LogPrinter {


    //EFFECTS: Constructs an empty LogPrinter with no parameters
    public LogPrinter() {

    }

    //EFFECTS: prints a log of events taken place in GUI to console
    public void printLog(EventLog el) {
        for (Event e : el) {
            System.out.println(e.getDescription());
        }
    }
}
