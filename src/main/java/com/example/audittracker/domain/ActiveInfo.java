package com.example.audittracker.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * 
 * @author vmudigal
 *
 */
@Entity
@Table(name = "active_info")
public class ActiveInfo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long activeInfoId;

	@Column(name = "timeslice_id", nullable = false, length = 3)
	private String timeSliceId;

	@Column(name = "active_time", nullable = false, length = 3)
	private float activeTime;

	@ManyToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "tracker_id")
	private Tracker tracker;

	public Long getActiveInfoId() {
		return activeInfoId;
	}

	public void setActiveInfoId(Long activeInfoId) {
		this.activeInfoId = activeInfoId;
	}

	public String getTimeSliceId() {
		return timeSliceId;
	}

	public void setTimeSliceId(String timeSliceId) {
		this.timeSliceId = timeSliceId;
	}

	// convert to minutes from seconds
	public float getActiveTime() {
		return activeTime/60;
	}

	public void setActiveTime(float activeTime) {
		this.activeTime = activeTime;
	}

	public Tracker getTracker() {
		return tracker;
	}

	public void setTracker(Tracker tracker) {
		this.tracker = tracker;
	}

	@Override
	public String toString() {
	    return "ActiveInfo [activeInfoId=" + activeInfoId + ", timeSliceId=" + timeSliceId + ", activeTime="
		    + activeTime + "]";
	}

}
