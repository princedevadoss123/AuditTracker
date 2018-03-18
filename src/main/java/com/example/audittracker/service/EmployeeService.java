package com.example.audittracker.service;

import java.util.List;

import com.example.audittracker.model.ActiveTimeInfoTO;
import com.example.audittracker.model.EmployeeActiveTimeTO;
import com.example.audittracker.model.individual.record.EmployeeDataTO;



/**
 * Tracker service interface
 * 
 * @author vmudigal
 *
 */
public interface EmployeeService {

	boolean recordActiveTimeForAnEmployee(String employeeId, List<EmployeeDataTO> employeeDataTOs);

	EmployeeActiveTimeTO getActiveTimeForAnEmployeeOnDate(String empId, String Date);

	List<ActiveTimeInfoTO> getActiveTimeForAnEmployeeForDateRange(String empId, String startDate, String endDate);

	ActiveTimeInfoTO getActiveTimeForEmployeeByTime(String empId, String date, String startTime, String endTime);

	String getStartTime(String empId);

	List<ActiveTimeInfoTO> getLeadershipDataForEmployee(String employeeId, String date);

}