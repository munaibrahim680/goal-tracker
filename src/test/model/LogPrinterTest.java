package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

// Unit tests for LogPrinter
public class LogPrinterTest {
    LogPrinter logPrinter;
    private Event e1;
    private Event e2;
//    private Event e3;

    @BeforeEach
    void runBefore() {
        logPrinter = new LogPrinter();
        e1 = new Event("E1");
        e2 = new Event("E2");
    }

    @Test
    void testConstructor() {
        LogPrinter expected = new LogPrinter();
        assertEquals(expected.getClass(), logPrinter.getClass());
    }

    @Test
    void testPrintLog() {
        //setup - make console output readable:
        ByteArrayOutputStream consoleOutput = new ByteArrayOutputStream();
        System.setOut(new PrintStream(consoleOutput));
        EventLog el = EventLog.getInstance();

        //no events added
        logPrinter.printLog(el);
        assertEquals("", consoleOutput.toString());

        //two events added
        el.logEvent(e1);
        el.logEvent(e2);
        logPrinter.printLog(el);
        assertEquals("E1\nE2\n", consoleOutput.toString());
    }
}
