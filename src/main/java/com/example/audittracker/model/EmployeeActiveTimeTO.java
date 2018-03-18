package com.example.audittracker.model;

import java.util.List;

/**
 * 
 * @author vmudigal
 *
 */
public class EmployeeActiveTimeTO {

    private String employeeId;
    private String firstName;
    private String lastName;
    private String proposedStartTime;
    private String proposedEndTime;
    private String actualStartTime;
    private String actualEndTime;
    private String timeZone;
    private List<ActiveTimeTO> activeTime;

    public String getEmployeeId() {
	return employeeId;
    }

    public void setEmployeeId(String employeeId) {
	this.employeeId = employeeId;
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

    public List<ActiveTimeTO> getActiveTime() {
	return activeTime;
    }

    public void setActiveTime(List<ActiveTimeTO> activeTime) {
	this.activeTime = activeTime;
    }

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

    public String getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(String actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public String getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(String actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

}
