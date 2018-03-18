package com.example.audittracker.model;

import java.util.Date;

/**
 * 
 * @author vmudigal
 *
 */
public class ActiveTimeInfoTO implements Comparable<ActiveTimeInfoTO> {

    private String employeeId;
    private String firstName;
    private String lastName;
    private Date date;
    private float activeTime;
    private String hour;
    private String proposedStartTime;
    private String proposedEndTime;
    private String startTime;
    private String endTime;
    private String timeZone;

    public String getProposedStartTime() {
	return proposedStartTime;
    }

    public void setProposedStartTime(String proposedStartTime) {
	this.proposedStartTime = proposedStartTime;
    }

    public String getProposedEndTime() {
	return proposedEndTime;
    }

    public void setProposedEndTime(String proposedEndTime) {
	this.proposedEndTime = proposedEndTime;
    }

    public String getTimeZone() {
	return timeZone;
    }

    public void setTimeZone(String timeZone) {
	this.timeZone = timeZone;
    }

    public String getStartTime() {
	return startTime;
    }

    public void setStartTime(String startTime) {
	this.startTime = startTime;
    }

    public String getEndTime() {
	return endTime;
    }

    public void setEndTime(String endTime) {
	this.endTime = endTime;
    }

    public String getFirstName() {
	return firstName;
    }

    public void setFirstName(String firstName) {
	this.firstName = firstName;
    }

    public String getLastName() {
	return lastName;
    }

    public void setLastName(String lastName) {
	this.lastName = lastName;
    }

    public String getEmployeeId() {
	return employeeId;
    }

    public void setEmployeeId(String employeeId) {
	this.employeeId = employeeId;
    }

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public String getHour() {
	return hour;
    }

    public void setHour(String hour) {
	this.hour = hour;
    }

    public float getActiveTime() {
	return activeTime;
    }

    public void setActiveTime(float activeTime) {
	this.activeTime = activeTime;
    }

    @Override
    public String toString() {
	return "ActiveTimeInfoTO [employeeId=" + employeeId + ", firstName=" + firstName + ", lastName=" + lastName
		+ ", date=" + date + ", activeTime=" + activeTime + ", hour=" + hour + ", proposedStartTime="
		+ proposedStartTime + ", proposedEndTime=" + proposedEndTime + ", startTime=" + startTime + ", endTime="
		+ endTime + ", timeZone=" + timeZone + "]";
    }

    @Override
    public int compareTo(ActiveTimeInfoTO o) {
	if (this.getEmployeeId().compareTo(o.getEmployeeId()) > 0) {
	    return 1;
	}
	else if (this.getEmployeeId().compareTo(o.getEmployeeId()) < 0) {
	    return -1;
	}
	return 0;
    }

}
