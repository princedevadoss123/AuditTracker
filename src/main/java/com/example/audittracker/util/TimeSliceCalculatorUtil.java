package com.example.audittracker.util;

import java.sql.Date;
import java.text.SimpleDateFormat;

import com.example.audittracker.domain.ActiveInfo;

/**
 * 
 * @author vmudigal
 *
 */
public class TimeSliceCalculatorUtil {

    public static final String SLICE_DELIMITER = ":";

    public static String timeToIdConverter(String time) {
	String TimeSliceId = time.substring(0, 2);
	String minutes = time.substring(3, 5);
	int min = Integer.parseInt(minutes) / 15;
	TimeSliceId = TimeSliceId + min;
	return TimeSliceId;

    }

    public static String getTimeSlice(Date date) {
	String hour = "00";
	String min = "0";
	try {
	    // Calculate Time Slice
	    hour = new SimpleDateFormat("HH").format(date);
	    min = new SimpleDateFormat("mm").format(date);

	    Integer intMin = Integer.valueOf(min);

	    if (intMin < 15) {
		min = "0";
	    } else if (intMin > 14 && intMin < 30) {
		min = "1";
	    } else if (intMin > 29 && intMin < 45) {
		min = "2";
	    } else if (intMin < 60) {
		min = "3";
	    }

	} catch (Exception ex) {
	    // log here
	    ex.printStackTrace();
	}
	return (hour + min);
    }

    public static String getActiveInfoCurrentTime(ActiveInfo activeInfo) {
	String hourSlice = activeInfo.getTimeSliceId().substring(0, 2);
	String minSlice = activeInfo.getTimeSliceId().substring(2, 3);
	return hourSlice + SLICE_DELIMITER + getMinData(minSlice);
    }

    public static String getMinData(String minSlice) {
	switch (minSlice) {
	case "0":
	    return "15";
	case "1":
	    return "30";
	case "2":
	    return "45";
	case "3":
	    return "60";
	}
	return "0";
    }

}