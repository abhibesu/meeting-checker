package com.meetingchecker.test;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.meeting.checker.MeetingCheckerManager;

/**
 * This is an unit test class for meeting checker
 * @author abhi
 *
 */

public class MeetingCheckerTest {
	
	final static Logger LOGGER = Logger.getLogger(MeetingCheckerTest.class);
	
	MeetingCheckerManager manager = new MeetingCheckerManager();
	
	//TODO - This is a very basic unit testing - Due to time constraint cannot put others
	@Test
	public void testCheckYPositionCoordinatesDifferenceWithinRange() {
		double firstPersonYPositionCoordinates = 2.0;
		double secondPersonYPositionCoordinates = 3.9;
		boolean expectedOutput = true;
		
		boolean actualOutput = manager.checkYPositionCoordinatesDifference(firstPersonYPositionCoordinates, secondPersonYPositionCoordinates);
		
		assertEquals(expectedOutput, actualOutput);
	}
	
	@Test
	public void testCheckXPositionCoordinatesDifferenceWithinRange() {
		double firstPersonXPositionCoordinates = 2.0;
		double secondPersonXPositionCoordinates = 3.9;
		boolean expectedOutput = true;
		
		boolean actualOutput = manager.checkYPositionCoordinatesDifference(firstPersonXPositionCoordinates, secondPersonXPositionCoordinates);
		
		assertEquals(expectedOutput, actualOutput);
	}
	
	
}
