package com.meeting.checker;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.List;

import org.apache.log4j.Logger;
import org.javatuples.Triplet;

/**
 * @author abhi
 * This class counts the number of meetings happening between people
 */
public class MeetingCheckerManager implements MeetingCheckerInterface {

	static Logger LOGGER = Logger.getLogger(MeetingCheckerManager.class);
	
	private final static String DATE_FORMAT = "yyyy-mm-DD'T'HH:mm:ss.SSSX";
	
	private static List<MeetingBean> meetingBeans = null;

	/**
	 * Loads the Meetings related stuff in List of MeetingBean type
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void init() throws FileNotFoundException, IOException {
		meetingBeans = LoadCSVSingleton.loadCsv();
	}

	/**
	 * In distributed environment it will help to load the list where meetBeans is null
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private static void ifMeetingBeansIsNull() throws FileNotFoundException, IOException {
		if (meetingBeans == null) {
			// Required in distributed environment
			synchronized (MeetingCheckerManager.class) {
				if (meetingBeans == null) {
					init();
				}
			}
		}

	}

	/**
	 * Meeting checker method which will find if meeting happened or not between 2
	 * input Uids
	 * @param uId1
	 * @param uId2
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public MeetingBean meetingDetails(String uId1, String uId2) throws IOException, ParseException {

		ifMeetingBeansIsNull();

		List<MeetingBean> uId1BeanList = new ArrayList<MeetingBean>();
		List<MeetingBean> uId2BeanList = new ArrayList<MeetingBean>();

		for (MeetingBean meet : meetingBeans) {
			if (meet.getuId().trim().equalsIgnoreCase(uId1)) {
				uId1BeanList.add(meet);
			}
			if (meet.getuId().trim().equalsIgnoreCase(uId2)) {
				uId2BeanList.add(meet);
			}
		}

		// Validate if the First User ID exist in the CSV file
		if (uId1BeanList.isEmpty()) {
			throw new InputMismatchException("The first user id does not exist in the list: " + uId1);

		}

		// Validate if the Second User ID exist in the CSV file
		if (uId2BeanList.isEmpty()) {
			throw new InputMismatchException("The second user id does not exist in the list: " + uId2);

		}

		// Main logic: Checking the First User and Second User met each other
		for (MeetingBean meetingBean1 : uId1BeanList) {
			for (MeetingBean meetingBean2 : uId2BeanList) {
				if (!checkTimingDifference(meetingBean1.getTimeStamp(), meetingBean2.getTimeStamp())) {
					break;
				}
				if (!checkLocationDifference(
						Triplet.with(meetingBean1.getFloor(), meetingBean1.getxCoordinator(),
								meetingBean1.getyCordinator()),
						Triplet.with(meetingBean2.getFloor(), meetingBean2.getxCoordinator(),
								meetingBean2.getyCordinator()))) {
					break;

				} else {
					//They will meet around this time and location
					return meetingBean1;
				}
			}
		}
		return null;
	}

	/**
	 * This method checks if two person was there a particular location within 2 minutes
	 * @param dateFirst
	 * @param dateSecond
	 * @return
	 * @throws ParseException
	 */
	public boolean checkTimingDifference(String dateFirst, String dateSecond) throws ParseException {

		SimpleDateFormat format = new SimpleDateFormat(DATE_FORMAT);
		Date d1 = null;
		Date d2 = null;
		long diffMinutes = 0L;
		boolean timingOkForMeeting = false;
		try {
			d1 = format.parse(dateFirst);
			d2 = format.parse(dateSecond);

			// Getting the time difference in minutes
			if (java.lang.Long.compare(d1.getTime(), d2.getTime()) == 1) {
				diffMinutes = (d1.getTime() - d2.getTime()) / (60 * 1000);
			} else {
				diffMinutes = (d2.getTime() - d1.getTime()) / (60 * 1000);
			}
		} catch (ParseException parseException) {
			throw new ParseException("Check the dateformat \n" + parseException.getMessage(), 0);
		}
		if (diffMinutes <= 2L) {
			timingOkForMeeting = true;
		}
		return timingOkForMeeting;
	}

	/**
	 * This method checks if two person were in ALMOST same unit location Criteria
	 * for meeting location is as follows: X-Coordinates should within 2 unit
	 * Y-Coordinates should within 2 unit Floor will be same
	 * @param firstPersonPosition
	 * @param secondPersonPosition
	 * @return
	 */
	public boolean checkLocationDifference(Triplet<Integer, Double, Double> firstPersonPosition,
			Triplet<Integer, Double, Double> secondPersonPosition) {

		boolean isTheyAreCloseProximity = false;
		if (checkTheFloorDifference(firstPersonPosition.getValue0(), secondPersonPosition.getValue0())
				&& checkXPositionCoordinatesDifference(firstPersonPosition.getValue1().doubleValue(),
						secondPersonPosition.getValue1().doubleValue())
				&& checkYPositionCoordinatesDifference(firstPersonPosition.getValue2().doubleValue(),
						secondPersonPosition.getValue2().doubleValue())) {
			isTheyAreCloseProximity = true;
		}
		return isTheyAreCloseProximity;
	}
	
	/**
	 * Find the difference if the two coordinates values
	 * @param firstValue
	 * @param secondValue
	 * @return
	 */
	public double differenceTwoCordinates(double firstValue, double secondValue) {
		double positionDifference = 0.0;
		if (java.lang.Double.compare(firstValue, secondValue) == 1) {
			positionDifference = firstValue - secondValue;
		} else {
			positionDifference = secondValue - firstValue;
		}
		return positionDifference;
	}
	
	/**
	 * This private method checks the two persons floor difference
	 * @param firstpersonFloornumber
	 * @param secondPersonFloorNumber
	 * @return
	 */
	public boolean checkTheFloorDifference(int firstpersonFloornumber, int secondPersonFloorNumber) {
		boolean isSameFloor = false;
		if (firstpersonFloornumber == secondPersonFloorNumber) {
			isSameFloor = true;
		}
		return isSameFloor;
	}

	/**
	 * This private method checks the two persons X coordinates difference
	 * @param firstPersonXPositionCoordinates
	 * @param secondPersonXPositionCoordinates
	 * @return
	 */
	public boolean checkXPositionCoordinatesDifference(double firstPersonXPositionCoordinates,
			double secondPersonXPositionCoordinates) {
		boolean xPositionCoordinatesDifference = false;
		double positionDifference = differenceTwoCordinates(firstPersonXPositionCoordinates,
				secondPersonXPositionCoordinates);
		if (positionDifference < 2.0) {
			xPositionCoordinatesDifference = true;
		}
		return xPositionCoordinatesDifference;
	}

	/**
	 * This private method checks the two persons Y coordinates difference
	 * @param firstPersonYPositionCoordinates
	 * @param secondPersonYPositionCoordinates
	 * @return
	 */
	public boolean checkYPositionCoordinatesDifference(double firstPersonYPositionCoordinates,
			double secondPersonYPositionCoordinates) {
		boolean yPositionCoordinatesDifference = false;
		double positionDifference = differenceTwoCordinates(firstPersonYPositionCoordinates,
				secondPersonYPositionCoordinates);

		if (positionDifference < 2.0) {
			yPositionCoordinatesDifference = true;
		}
		return yPositionCoordinatesDifference;
	}
}
