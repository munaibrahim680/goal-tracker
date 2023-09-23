package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

// The following code is copied from the EventTest class in the AlarmSystem starter:
// https://github.students.cs.ubc.ca/CPSC210/AlarmSystem/blob/main/src/test/ca/ubc/cpsc210/alarm/test/EventTest.java
/**
 * Unit tests for the Event class
 */
public class EventTest {
	private Event e;
	private Date d;
	
	//NOTE: these tests might fail if time at which line (2) below is executed
	//is different from time that line (1) is executed.  Lines (1) and (2) must
	//run in same millisecond for this test to make sense and pass.
	
	@BeforeEach
	public void runBefore() {
		e = new Event("Goal added to tracker");   // (1)
		d = Calendar.getInstance().getTime();   // (2)
	}
	
	@Test
	public void testEvent() {
		assertEquals("Goal added to tracker", e.getDescription());
		assertEquals(d, e.getDate());
	}

	@Test
	public void testToString() {
		assertEquals(d.toString() + "\n" + "Goal added to tracker", e.toString());
	}


    //The following three tests are NOT  copied from AlarmSystem:
    @Test
    public void testOverrideHashcodeEquals() {
        Event e2 = new Event("Goal added to tracker");
        assertTrue(e.hashCode() == e2.hashCode());
        assertTrue(e.equals(e2));
    }

    @Test
    void testOverrideEqualsNullObject() {
        Event e0 = null;
        assertFalse(e.equals(e0));
    }

    @Test
    void testOverrideEqualsDifferentClass() {
        Goal g = new Goal("Study!");
        assertFalse(e.equals(g));
    }
}
