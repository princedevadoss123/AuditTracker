package com.example.audittracker.model.individual.record;

/**
 * 
 * @author vmudigal
 *
 */
public class EmployeeDataTO {

    /** yyyy-MM-dd HH-mm **/
    private String dateAndTime;
    private Integer activeTime;
    private Integer inActiveTime;
    private Integer totalLoggedInTime;

    public Integer getActiveTime() {
	return activeTime;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public void setActiveTime(Integer activeTime) {
	this.activeTime = activeTime;
    }

    public Integer getInActiveTime() {
	return inActiveTime;
    }

    public void setInActiveTime(Integer inActiveTime) {
	this.inActiveTime = inActiveTime;
    }

    public Integer getTotalLoggedInTime() {
	return totalLoggedInTime;
    }

    public void setTotalLoggedInTime(Integer totalLoggedInTime) {
	this.totalLoggedInTime = totalLoggedInTime;
    }

	@Override
	public String toString() {
	return "EmployeeDataTO [dateAndTime=" + dateAndTime + ", activeTime=" + activeTime + ", inActiveTime="
		+ inActiveTime + ", totalLoggedInTime=" + totalLoggedInTime + "]";
	}

}
