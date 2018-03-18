package com.example.audittracker.domain;

import java.sql.Date;
import java.sql.Time;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Tracker domain object
 * 
 * @author vmudigal
 *
 */
@Entity
@Table(name = "tracker")
public class Tracker {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long trackerId;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "emp_id")
	private Employee employee;

	@Column(name = "date")
	private Date date;

	@Column(name = "start_time")
	private Time startTime;

	@Column(name = "end_time")
	private Time endTime;

	@OneToMany(mappedBy = "tracker")
	private List<ActiveInfo> activeInfo = new LinkedList<ActiveInfo>();

	public Long getTrackerId() {
		return trackerId;
	}

	public void setTrackerId(Long trackerId) {
		this.trackerId = trackerId;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}

	public List<ActiveInfo> getActiveInfo() {
		return activeInfo;
	}

	public void setActiveInfo(List<ActiveInfo> activeInfo) {
		this.activeInfo = activeInfo;
	}

	@Override
	public String toString() {
	    return "Tracker [trackerId=" + trackerId + ", employee=" + employee.getEmployeeId() + ", date=" + date + ", startTime="
		    + startTime + ", endTime=" + endTime + ", activeInfo=" + activeInfo + "]";
	}

}
