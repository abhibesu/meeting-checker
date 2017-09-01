package com.meeting.checker;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;

/**
 * @author abhi 
 * This is the main class for the meeting checker - entry point for
 * the application
 */
public class MeetingChecker {
	static final Logger LOGGER = Logger.getLogger(MeetingChecker.class);

	public static void main(String[] args) throws IOException, ParseException {

		MeetingCheckerManager meetingCheckerManager = new MeetingCheckerManager();

		// TODO check if user has given correct number of inputs
		String firstUid = args[0];
		String secondUid = args[1];

		MeetingBean meetingDetails = meetingCheckerManager.meetingDetails(firstUid, secondUid);
		if (meetingDetails == null) {
			LOGGER.info("These guys did not met each other!!");
		} else {
			LOGGER.info("These guys met each other ..Please find the approximate meeting details below: \n");
			LOGGER.info("Approximate Timestamp: "+meetingDetails.getTimeStamp());
			LOGGER.info("Floor: "+meetingDetails.getFloor());
			LOGGER.info("Approximate X-coordinate "+meetingDetails.getxCoordinator());
			LOGGER.info("Approximate Y-coordinate: "+meetingDetails.getyCordinator());
		}
	}
}
