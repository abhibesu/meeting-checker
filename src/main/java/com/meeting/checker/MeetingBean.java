package com.meeting.checker;

import com.opencsv.bean.CsvBindByName;

/**
 * @author abhi
 * Bean for mapping the Fields in the csv dataset file
 *
 */
public class MeetingBean {
	
	@CsvBindByName(column = "timestamp", required = true)
	private String timeStamp;
	
	@CsvBindByName(column = "x", required = true)
	private double xCoordinator;
	
	@CsvBindByName(column = "y", required = true)
	private double yCordinator;
	
	@CsvBindByName(column = "floor", required = true)
	private int floor;
	
	@CsvBindByName(column = "uid", required = true)
	private String uId;
	
	public String getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}

	public double getxCoordinator() {
		return xCoordinator;
	}

	public void setxCoordinator(double xCoordinator) {
		this.xCoordinator = xCoordinator;
	}

	public double getyCordinator() {
		return yCordinator;
	}

	public void setyCordinator(double yCordinator) {
		this.yCordinator = yCordinator;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public String getuId() {
		return uId;
	}

	public void setuId(String uId) {
		this.uId = uId;
	}
}
