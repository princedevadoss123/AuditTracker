package com.example.audittracker.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.audittracker.dao.ActiveInfoDao;
import com.example.audittracker.dao.EmployeeDao;
import com.example.audittracker.dao.TrackerDao;
import com.example.audittracker.domain.ActiveInfo;
import com.example.audittracker.domain.Employee;
import com.example.audittracker.domain.Tracker;
import com.example.audittracker.model.ActiveTimeInfoTO;
import com.example.audittracker.model.ActiveTimeTO;
import com.example.audittracker.model.EmployeeActiveTimeTO;
import com.example.audittracker.model.LoginTO;
import com.example.audittracker.model.individual.record.EmployeeDataTO;
import com.example.audittracker.service.EmployeeService;
import com.example.audittracker.util.CommonUtil;
import com.example.audittracker.util.TimeSliceCalculatorUtil;



/**
 * @author vmudigal
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {


    @Autowired
    private EmployeeDao employeeDao;
    
    
    @Autowired
    private ManagerServiceImpl managerServiceImpl;

    @Autowired
    private TrackerDao trackerDao;

    @Autowired
    private ActiveInfoDao activeInfoDao;

    /**
     * Records the active time for a user received by the VC++ tracking
     * application
     * 
     * @param employeeId
     * @param employeeDataTOs
     * @return
     */
    @Override
    public boolean recordActiveTimeForAnEmployee(String employeeId, List<EmployeeDataTO> employeeDataTOs) {

	Employee employee = employeeDao.findByEmployeeId(employeeId);

	for (EmployeeDataTO employeeDataTO : employeeDataTOs) {
	    Date date = null;
	    try {
		date = new Date(
			new SimpleDateFormat("yyyy-MM-dd HH-mm").parse(employeeDataTO.getDateAndTime()).getTime());
	    } catch (Exception ex) {
		return false;
	    }

	    Tracker tracker = trackerDao.findByEmployeeAndDate(employee, date);
	    if (tracker == null) {
		tracker = new Tracker();
		tracker.setEmployee(employee);
		tracker.setDate(date);
		tracker.setStartTime(tracker.getEmployee().getCurrentLoginTime());
		tracker.setEndTime(tracker.getEmployee().getCurrentLogoutTime());
		tracker = trackerDao.save(tracker);
	    }
	    ActiveInfo activeInfo = activeInfoDao.findByTrackerAndTimeSliceId(tracker,
		    TimeSliceCalculatorUtil.getTimeSlice(date));
	    if (activeInfo == null) {
		activeInfo = new ActiveInfo();
		activeInfo.setActiveTime(employeeDataTO.getActiveTime());
		activeInfo.setTimeSliceId(TimeSliceCalculatorUtil.getTimeSlice(date));
		activeInfo.setTracker(tracker);
	    } else {
		// Sum to find the total active time in seconds
		Float totalActiveTime = activeInfo.getActiveTime() + employeeDataTO.getActiveTime();

		// Do not add the active time if its more than 15 mins.
		if (totalActiveTime > (15 * 60)) {
		    totalActiveTime = (float) (15 * 60);
		}
		activeInfo.setActiveTime(totalActiveTime);
	    }
	    activeInfoDao.save(activeInfo);
	}
	return true;
    }

    @Override
    public EmployeeActiveTimeTO getActiveTimeForAnEmployeeOnDate(String empId, String date) {
	Tracker tracker = null;
	List<ActiveInfo> activeInfos = null;
	Employee employee = employeeDao.findByEmployeeId(empId);

	try {
	    tracker = trackerDao.findByEmployeeAndDate(employee,
		    new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));

	    if (tracker != null) {
		activeInfos = tracker.getActiveInfo();
	    }
	} catch (ParseException ex) {
	    
	}

	Map<String, Float> hourMinData = new HashMap<String, Float>();
	for (int i = 0; i < 24; i++) {
	    for (int j = 0; j < 4; j++) {
		String hourMinKey = String.valueOf(i) + TimeSliceCalculatorUtil.SLICE_DELIMITER
			+ TimeSliceCalculatorUtil.getMinData(String.valueOf(j));
		if (i < 10) {
		    hourMinKey = "0" + hourMinKey;
		}
		hourMinData.put(hourMinKey, (float) 0);
	    }
	}
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
	String actualStartTime = null, actualEndTime = null;
	if (activeInfos != null) {
	    actualStartTime = dateFormat.format(tracker.getStartTime());
	    actualEndTime = dateFormat.format(tracker.getEndTime());

	    for (ActiveInfo activeInfo : activeInfos) {
		String currentSlice = TimeSliceCalculatorUtil.getActiveInfoCurrentTime(activeInfo);
		hourMinData.put(currentSlice, activeInfo.getActiveTime());
		CommonUtil.getActualStartTimeAndEndTime(activeInfo, actualStartTime, actualEndTime);
	    }
	}
	EmployeeActiveTimeTO employeeActiveTimeTO = new EmployeeActiveTimeTO();
	employeeActiveTimeTO.setEmployeeId(employee.getEmployeeId());
	employeeActiveTimeTO.setFirstName(employee.getFirstName());
	employeeActiveTimeTO.setLastName(employee.getLastName());
	if (tracker != null) {
	    employeeActiveTimeTO.setProposedStartTime(dateFormat.format(tracker.getStartTime()));
	    employeeActiveTimeTO.setProposedEndTime(dateFormat.format(tracker.getEndTime()));
	    employeeActiveTimeTO.setActualStartTime(actualStartTime);
	    employeeActiveTimeTO.setActualEndTime(actualEndTime);
	    // TODO: Need to remove the hardcoded logic
	    employeeActiveTimeTO.setTimeZone("IST");
	} else {
	    employeeActiveTimeTO.setProposedStartTime(dateFormat.format(employee.getCurrentLoginTime()));
	    employeeActiveTimeTO.setProposedEndTime(dateFormat.format(employee.getCurrentLogoutTime()));
	    employeeActiveTimeTO.setActualStartTime(dateFormat.format(employee.getCurrentLoginTime()));
	    employeeActiveTimeTO.setActualEndTime(dateFormat.format(employee.getCurrentLogoutTime()));
	    // TODO: Need to remove the hardcoded logic
	    employeeActiveTimeTO.setTimeZone("IST");
	}

	List<ActiveTimeTO> activeTimeTOs = new ArrayList<ActiveTimeTO>(96);
	hourMinData.forEach((hourMin, activeTime) -> {
	    ActiveTimeTO activeTimeTO = new ActiveTimeTO();
	    activeTimeTO.setActiveTime(activeTime);
	    activeTimeTO.setHour(hourMin.toString().split(TimeSliceCalculatorUtil.SLICE_DELIMITER)[0]);
	    activeTimeTO.setMin(hourMin.toString().split(TimeSliceCalculatorUtil.SLICE_DELIMITER)[1]);
	    try {
		activeTimeTO.setDate(new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
	    } catch (ParseException e) {
	    }
	    activeTimeTOs.add(activeTimeTO);
	});
	employeeActiveTimeTO.setActiveTime(activeTimeTOs);
	return employeeActiveTimeTO;
    }

    @Override
    public List<ActiveTimeInfoTO> getActiveTimeForAnEmployeeForDateRange(String empId, String startDate,
	    String endDate) {
	int i = 0;
	int count = 0;
	float[] dayData = new float[10];
	Date[] day = new Date[10];
	List<Tracker> trackers = null;
	Employee emp = null;
	List<ActiveInfo> activeInfos = null;
	try {
	    emp = employeeDao.findByEmployeeId(empId);
	    trackers = trackerDao.findByEmployeeAndBetweenDates(emp,
		    new Date(new SimpleDateFormat("yyyy-MM-dd").parse(startDate).getTime()),
		    new Date(new SimpleDateFormat("yyyy-MM-dd").parse(endDate).getTime()));
	    for (i = 0; i < trackers.size(); i++) {
		activeInfos = activeInfoDao.findByTracker(trackers.get(i));
		day[i] = trackers.get(i).getDate();
		dayData[i] = 0;
		for (int j = 0; j < activeInfos.size(); j++) {
		    dayData[i] = dayData[i] + activeInfos.get(j).getActiveTime();
		}
	    }
	} catch (Exception ex) {
	    return null;
	}
	List<ActiveTimeInfoTO> ActiveTimeInfoTOs = new LinkedList<ActiveTimeInfoTO>();
	count = i;
	if (!trackers.isEmpty()) {
	    for (int j = 0; j < count; j++) {
		ActiveTimeInfoTO activeTimeInfoTO = new ActiveTimeInfoTO();
		activeTimeInfoTO.setActiveTime(dayData[j]);
		activeTimeInfoTO.setDate(day[j]);
		activeTimeInfoTO.setEmployeeId(empId);
		activeTimeInfoTO.setFirstName(emp.getFirstName());
		activeTimeInfoTO.setLastName(emp.getLastName());

		ActiveTimeInfoTOs.add(activeTimeInfoTO);
	    }
	} else {
	    ActiveTimeInfoTO activeTimeInfoTO = new ActiveTimeInfoTO();
	    activeTimeInfoTO.setEmployeeId(empId);
	    activeTimeInfoTO.setFirstName(emp.getFirstName());
	    activeTimeInfoTO.setLastName(emp.getLastName());
	    activeTimeInfoTO.setActiveTime(0);
	    activeTimeInfoTO.setDate(new Date(System.currentTimeMillis()));
	    ActiveTimeInfoTOs.add(activeTimeInfoTO);
	}
	return ActiveTimeInfoTOs;
    }

    @Override
    public ActiveTimeInfoTO getActiveTimeForEmployeeByTime(String empId, String date, String startTime,
	    String endTime) {
	List<ActiveInfo> activeInfos = null;
	Tracker tracker = new Tracker();
	String initialTimeSliceId = TimeSliceCalculatorUtil.timeToIdConverter(startTime);
	String finalTimeSliceId = TimeSliceCalculatorUtil.timeToIdConverter(endTime);

	Employee emp = employeeDao.findByEmployeeId(empId);
	try {
	    tracker = trackerDao.findByEmployeeAndDate(emp,
		    new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
	} catch (ParseException e) {
	}
	activeInfos = activeInfoDao.getActiveTimeInfoByTimeRange(tracker, initialTimeSliceId, finalTimeSliceId);
	float sumOfActive = 0;
	ActiveTimeInfoTO transfer = new ActiveTimeInfoTO();
	for (int i = 0; i < activeInfos.size(); i++) {
	    ActiveInfo temp = activeInfos.get(i);
	    sumOfActive = sumOfActive + temp.getActiveTime();
	}

	transfer.setEmployeeId(empId);
	transfer.setActiveTime(sumOfActive);
	return transfer;
    }

    /**
     * The service used by the API to get the proposed start time of an employee
     * used by VC++ tracking application
     * 
     * @param empId
     * @return
     */
    @Override
    public String getStartTime(String empId) {
	Employee employee;
	String startTime;
	employee = employeeDao.findByEmployeeId(empId);
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
	startTime = dateFormat.format(employee.getCurrentLoginTime());
	return startTime;
    }

    /**
     * Method to populate the principal object after OKTA authentication.
     * 
     * @param emailId
     * @return
     */

	@Override
	public List<ActiveTimeInfoTO> getLeadershipDataForEmployee(String employeeId, String date) {
		Employee employee = employeeDao.findByEmployeeId(employeeId);
		List<ActiveTimeInfoTO> activeTimeInfoTOs = managerServiceImpl.getActiveTimeByDate(employee.getManagerId().getEmployeeId(), date);
		return activeTimeInfoTOs;
	}

	@Override
	public LoginTO getLogin(String id, String pass) {
		Employee emp=employeeDao.findByEmailAndPass(id, pass);
		LoginTO login = new LoginTO();
		if(emp != null){
			login.setEmployeeId(emp.getEmployeeId());
			login.setFirstName(emp.getFirstName());
			login.setLastName(emp.getFirstName());
			if(employeeDao.findByManagerId(emp).size()>0){
				login.setRole("manager");
			}
			else{
				login.setRole("employee");
			}
		}
		else{
			login.setEmployeeId("invalid");
			login.setFirstName("");
			login.setLastName("");
			login.setRole("");
			
		}
		return login;
	}
}
