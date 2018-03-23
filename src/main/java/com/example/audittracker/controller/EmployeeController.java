package com.example.audittracker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.audittracker.model.ActiveTimeInfoTO;
import com.example.audittracker.model.EmployeeActiveTimeTO;
import com.example.audittracker.model.LoginTO;
import com.example.audittracker.model.individual.record.EmployeeDataTO;
import com.example.audittracker.service.EmployeeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * Rest Controller which accepts the web request and returns the response for
 * employee details.
 * 
 * @author vmudigal
 *
 */
@RestController
@Api(value = "/employee")
@RequestMapping(value = "/employee")
public class EmployeeController {
	
	@Autowired
	private EmployeeService employeeService;
	
	
	
	/**
	 * Records the active time data for an employee.
	 * 
	 * @param employeeId
	 * @body employeeDataTO
	 * @return
	 */
	@ApiOperation(value = "Record active time", notes = "Record active time for an employee", response = Boolean.class)
	@RequestMapping(value = "/{employee_id}/record", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public boolean recordActiveTimeForAnEmployee(
			@ApiParam(value = "Employee ID", required = true) @PathVariable("employee_id") String employeeId,
			@ApiParam(value = "Employee data", required = true) @RequestBody List<EmployeeDataTO> employeeDataTOs) {
		return employeeService.recordActiveTimeForAnEmployee(employeeId, employeeDataTOs);
	}

	/**
	 * Gets active time information for an employee on a given Date.
	 * 
	 * @param employeeId
	 * @param date
	 * @return
	 */
	@ApiOperation(value = "Get active time", notes = "Get active time for an employee on given date", response = ActiveTimeInfoTO.class)
	@RequestMapping(value = "/{id}/leadership/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActiveTimeInfoTO> getLeadershipDataForEmployee(@PathVariable(value="id") String empid,
			@ApiParam(value = "Date", required = true) @PathVariable(value = "date") String date) {
		return employeeService.getLeadershipDataForEmployee(empid, date);
	}

	/**
	 * Gets active time information for an employee on a given Date.
	 * 
	 * @param employeeId
	 * @param date
	 * @return
	 */
	@ApiOperation(value = "Get active time", notes = "Get active time for an employee on given date", response = ActiveTimeInfoTO.class)
	@RequestMapping(value = "/{id}/date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public EmployeeActiveTimeTO getActiveTimeForAnEmployeeOnDate(@PathVariable(value = "id") String empid,
			@ApiParam(value = "Date", required = true) @PathVariable(value = "date") String date) {
		return employeeService.getActiveTimeForAnEmployeeOnDate(empid, date);
	}
	
	@ApiOperation(value = "Get Login Details", notes = "Get Login Details for an employee", response = LoginTO.class)
	@RequestMapping(value = "/{id}/{passwd}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public LoginTO getLogin(@PathVariable(value = "id") String empid,
			@ApiParam(value = "password", required = true) @PathVariable(value = "passwd") String passwd) {
		return employeeService.getLogin(empid,passwd);
	}
	/**
	 * Gets active time information for an employee for a given Date Range.
	 * 
	 * @param employeeId
	 * @param start
	 *            date
	 * @param end
	 *            date
	 * @return
	 */
	@ApiOperation(value = "Get date range active time", notes = "Get active time for an employee for given date range", response = ActiveTimeInfoTO.class)
	@RequestMapping(value = "/{id}/date/start/{startDate}/end/{endDate}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<ActiveTimeInfoTO> getActiveTimeForAnEmployeeForDateRange(@PathVariable(value = "id") String empid,
			@ApiParam(value = "Start Date", required = true) @PathVariable(value = "startDate") String startDate,
			@ApiParam(value = "End Date", required = true) @PathVariable(value = "endDate") String endDate) {
		return employeeService.getActiveTimeForAnEmployeeForDateRange(empid, startDate, endDate);
	}

	/**
	 * Gets the active time data for an employee.
	 * 
	 * @param employeeId
	 * @param activeTime
	 * @return
	 */
	@ApiOperation(value = "Get active time for range of time", notes = "get active time for an employee", response = ActiveTimeInfoTO.class)
	@RequestMapping(value = "/{id}/date/{date}/time/start/{starttime}/end/{endtime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ActiveTimeInfoTO getActiveTimeForAnEmployeeForTimeRange(
			@PathVariable(value = "id") String empid, @ApiParam(value = "Date", required = true) @PathVariable(value = "date") String date,
			@ApiParam(value = "Active time in minutes", required = true) @PathVariable(value = "starttime") String startTime,
			@ApiParam(value = "Active time in minutes", required = true) @PathVariable(value = "endtime") String endTime) {
		return employeeService.getActiveTimeForEmployeeByTime(empid, date, startTime, endTime);
	}

	/**
	 * Gets the start time of an employee.
	 * 
	 * @param employeeId
	 * @return start time of corresponding employee
	 */
	@ApiOperation(value = "Get starting time", notes = "get start time of an employee")
	@RequestMapping(value = "/login/time/{employee_id}", method = RequestMethod.GET)
	public String getStartTimeForEmployee(
			@ApiParam(value = "Employee ID", required = true) @PathVariable("employee_id") String employeeId) {
		return employeeService.getStartTime(employeeId);
	}

}
