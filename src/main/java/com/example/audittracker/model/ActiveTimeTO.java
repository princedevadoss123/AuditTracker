package com.example.audittracker.model;

import java.util.Date;

/**
 * 
 * @author vmudigal
 *
 */
public class ActiveTimeTO {
    
    private Date date;
    private float activeTime;
    private String hour;
    private String min;

    public Date getDate() {
	return date;
    }

    public void setDate(Date date) {
	this.date = date;
    }

    public float getActiveTime() {
	return activeTime;
    }

    public void setActiveTime(float activeTime) {
	this.activeTime = activeTime;
    }

    public String getHour() {
	return hour;
    }

    public void setHour(String hour) {
	this.hour = hour;
    }

    public String getMin() {
	return min;
    }

    public void setMin(String min) {
	this.min = min;
    }
}
