package com.example.audittracker.util;

import com.example.audittracker.domain.ActiveInfo;

/**
 * 
 * @author vmudigal
 *
 */
public class CommonUtil {

    /**
     * Sets the actual start time and end time based on the active info
     * available
     * 
     * @param activeInfo
     * @param startTime
     * @param endTime
     */
    public static void getActualStartTimeAndEndTime(ActiveInfo activeInfo, String startTime, String endTime) {
	String activeInfoCurrentTime = TimeSliceCalculatorUtil.getActiveInfoCurrentTime(activeInfo);
	if (activeInfoCurrentTime.compareTo(startTime) < 0) {
	    startTime = activeInfoCurrentTime;
	}
	if (activeInfoCurrentTime.compareTo(endTime) > 0) {
	    endTime = activeInfoCurrentTime;
	}
    }

}
