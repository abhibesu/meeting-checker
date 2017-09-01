package com.meeting.checker;

import java.io.IOException;
import java.text.ParseException;

import org.apache.log4j.Logger;

/**
 * Interface for the meeting checker defining the methods 
 * 
 */
public interface MeetingCheckerInterface {

	static final Logger LOGGER = Logger.getLogger(MeetingCheckerInterface.class);


	/**
	 * Get the meeting details between 2 uids
	 * @param uId1
	 * @param uId2
	 * @return MeetingBean
	 * @throws IOException
	 * @throws ParseException
	 */
	public MeetingBean meetingDetails(String uId1, String uId2) throws IOException, ParseException;
	
}
