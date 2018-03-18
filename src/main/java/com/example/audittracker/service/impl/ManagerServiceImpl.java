package com.example.audittracker.service.impl;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.audittracker.dao.ActiveInfoDao;
import com.example.audittracker.dao.EmployeeDao;
import com.example.audittracker.dao.TrackerDao;
import com.example.audittracker.domain.ActiveInfo;
import com.example.audittracker.domain.Employee;
import com.example.audittracker.domain.Tracker;
import com.example.audittracker.exception.EmployeeNotFoundException;
import com.example.audittracker.model.ActiveTimeInfoTO;
import com.example.audittracker.model.ActiveTimeTO;
import com.example.audittracker.model.EmployeeActiveTimeTO;
import com.example.audittracker.model.team.ActiveDateTimeInfoTO;
import com.example.audittracker.service.ManagerService;
import com.example.audittracker.util.CommonUtil;
import com.example.audittracker.util.TimeSliceCalculatorUtil;



/**
 * 
 * @author vmudigal
 *
 */
@Service
public class ManagerServiceImpl implements ManagerService {


	@Autowired
	private EmployeeDao employeeDao;
	

    @Autowired
    private EntityManager em;

	@Autowired
	private TrackerDao trackerDao;

	@Autowired
	private ActiveInfoDao activeInfoDao;

	/**
	 * Get active information for a team based on date range
	 * 
	 * @param managerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */

@SuppressWarnings("unchecked")
	 @Override
	    public List<ActiveDateTimeInfoTO> getActiveTimeByDateTimeRange(String managerId, String startDate, String endDate,
		    String startTime, String endTime) {

		List<Object[]> resultSet = null;
		List<ActiveDateTimeInfoTO> activeDateTimeInfoTOs = new LinkedList<ActiveDateTimeInfoTO>();
		try {
		    String startDateTime = startDate + ' ' + startTime;
		    String endDateTime = endDate + ' ' + endTime;

		    resultSet = em.createNamedQuery("TeamInfoForDateRange").setParameter("managerId", managerId)
			    .setParameter("startDateTime",
				    new java.util.Date(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(startDateTime).getTime()))
			    .setParameter("endDateTime",
				    new java.util.Date(new SimpleDateFormat("yyyy-MM-dd hh:mm").parse(endDateTime).getTime()))
			    .getResultList();
		} catch (Exception ex) {
		    
		}
		String oldTrackerDate = null;
		String oldEmpId = null;
		ActiveTimeInfoTO activeTimeInfoTO = null;
		List<ActiveTimeInfoTO> activeTimeInfoTOs = new ArrayList<>();
		ActiveDateTimeInfoTO activeDateTimeInfoTO = new ActiveDateTimeInfoTO();
		for (int i = 0; i < resultSet.size(); i++) {
		    Object[] row = resultSet.get(i);
		    String trackerDate = ((Date) row[0]).toString();
		    String empId = (String) row [1];
		    if (oldTrackerDate == null) {
			oldTrackerDate = trackerDate;
		    }
		    if (!oldTrackerDate.equals(trackerDate)) {
			activeDateTimeInfoTO.setDate(oldTrackerDate);
			activeDateTimeInfoTO.setActiveTimeInfoTOs(activeTimeInfoTOs);
			activeDateTimeInfoTOs.add(activeDateTimeInfoTO);
			
			activeTimeInfoTOs = new ArrayList<>();
			activeDateTimeInfoTO = new ActiveDateTimeInfoTO();
			
			oldTrackerDate = trackerDate;
		    }
		    if (oldEmpId != null && oldEmpId.equals(empId)) {
			activeTimeInfoTO.setActiveTime(activeTimeInfoTO.getActiveTime() + (float) row [4]);
		    } else {
		    activeTimeInfoTO = new ActiveTimeInfoTO();
		    activeTimeInfoTO.setDate((java.util.Date) row[0]);
		    activeTimeInfoTO.setEmployeeId(empId);
		    activeTimeInfoTO.setFirstName((String) row [2]);
		    activeTimeInfoTO.setLastName((String) row [3]);
		    activeTimeInfoTO.setActiveTime((float) row [4]);
		    activeTimeInfoTOs.add(activeTimeInfoTO);
		    oldEmpId = empId;
		    }
		    if (oldEmpId == null) {
			oldEmpId = empId;
		    }
		}
		// Last date information included
		activeDateTimeInfoTO = new ActiveDateTimeInfoTO();
		activeDateTimeInfoTO.setDate(oldTrackerDate);
		activeDateTimeInfoTO.setActiveTimeInfoTOs(activeTimeInfoTOs);
		activeDateTimeInfoTOs.add(activeDateTimeInfoTO);
		return activeDateTimeInfoTOs;
	    }

	@Override
	public List<ActiveDateTimeInfoTO> getActiveTimeByDateRange(String managerId, String startDate, String endDate) {
		List<Tracker> trackers = null;
		List<ActiveDateTimeInfoTO> activeDateTimeInfoTOs = new LinkedList<ActiveDateTimeInfoTO>();
		try {
			trackers = trackerDao.getActiveTimeInfoByDateRange(employeeDao.findByEmployeeId(managerId),
					new Date(new SimpleDateFormat("yyyy-MM-dd").parse(startDate).getTime()),
					new Date(new SimpleDateFormat("yyyy-MM-dd").parse(endDate).getTime()));
		} catch (Exception ex) {
			
		}
		List<Tracker> subTrackers = new LinkedList<Tracker>();
		List<ActiveTimeInfoTO> activeTimeInfoTOs = null;
		String oldTrackerDate = null;
		for (Tracker tracker : trackers) {
			String trackerDate = new SimpleDateFormat("yyyy-MM-dd").format(tracker.getDate());
			if (oldTrackerDate == null) {
				oldTrackerDate = trackerDate;
			}
			if (!oldTrackerDate.equals(trackerDate)) {
				try {
					activeTimeInfoTOs = createActiveTimeInfoTOFromTracker(subTrackers, managerId);

					ActiveDateTimeInfoTO activeDateTimeInfoTO = new ActiveDateTimeInfoTO();
					activeDateTimeInfoTO.setDate(oldTrackerDate);
					activeDateTimeInfoTO.setActiveTimeInfoTOs(activeTimeInfoTOs);

					activeDateTimeInfoTOs.add(activeDateTimeInfoTO);

					oldTrackerDate = trackerDate;
					subTrackers = new LinkedList<Tracker>();

				} catch (Exception ex) {
					
				}
			}
			subTrackers.add(tracker);
		}
		// Last date information included
		activeTimeInfoTOs = createActiveTimeInfoTOFromTracker(subTrackers, managerId);
		ActiveDateTimeInfoTO activeDateTimeInfoTO = new ActiveDateTimeInfoTO();
		activeDateTimeInfoTO.setDate(oldTrackerDate);
		activeDateTimeInfoTO.setActiveTimeInfoTOs(activeTimeInfoTOs);
		activeDateTimeInfoTOs.add(activeDateTimeInfoTO);
		return activeDateTimeInfoTOs;
	}

	/**
	 * Team based data for a manager on a particular date
	 * 
	 * @param managerId
	 * @param date
	 * 
	 * @return List<ActiveTimeInfoTO>
	 */

	@Override
	public List<ActiveTimeInfoTO> getActiveTimeByDate(String managerId, String date) {
		List<Tracker> trackers = null;
		try {
			trackers = trackerDao.getActiveTimeInfoByDate(employeeDao.findByEmployeeId(managerId),
					new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
		} catch (Exception ex) {
			
		}
		return createActiveTimeInfoTOFromTracker(trackers, managerId);
	}

	/**
	 * Get Active info for all the team members for a manager. Trackers should
	 * be for particular date
	 * 
	 * @param trackers
	 * @param managerId
	 * @return
	 */
	private List<ActiveTimeInfoTO> createActiveTimeInfoTOFromTracker(List<Tracker> trackers, String managerId) {
		List<ActiveTimeInfoTO> activeTimeInfoTOs = new LinkedList<ActiveTimeInfoTO>();
		// Get all employees for a manager
		List<Employee> employees = employeeDao.findByManagerId(employeeDao.findByEmployeeId(managerId));
		if (!trackers.isEmpty()) {
			for (Tracker tracker : trackers) {
				// Find if the tracker associated with the employee is present
				// in the employee list.
				employees.remove(employeeDao.findByEmployeeId(tracker.getEmployee().getEmployeeId()));
				ActiveTimeInfoTO activeTimeInfoTO = new ActiveTimeInfoTO();
				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
				String startTime = dateFormat.format(tracker.getStartTime());
				String endTime = dateFormat.format(tracker.getEndTime());
				activeTimeInfoTO.setEmployeeId(tracker.getEmployee().getEmployeeId());
				activeTimeInfoTO.setFirstName(tracker.getEmployee().getFirstName());
				activeTimeInfoTO.setLastName(tracker.getEmployee().getLastName());
				for (ActiveInfo activeInfo : tracker.getActiveInfo()) {
					activeTimeInfoTO.setActiveTime(activeTimeInfoTO.getActiveTime() + activeInfo.getActiveTime());
					CommonUtil.getActualStartTimeAndEndTime(activeInfo, startTime, endTime);
				}
				activeTimeInfoTO.setProposedStartTime(dateFormat.format(tracker.getStartTime()));
				activeTimeInfoTO.setProposedEndTime(dateFormat.format(tracker.getEndTime()));
				activeTimeInfoTO.setStartTime(startTime);
				activeTimeInfoTO.setEndTime(endTime);
				// TODO: Need to remove the hardcoded logic
				activeTimeInfoTO.setTimeZone("IST");
				activeTimeInfoTO.setDate(tracker.getDate());
				activeTimeInfoTOs.add(activeTimeInfoTO);
			}
		}
		// Add empty tracker details for those employees with no tracker
		// information
		for (Employee employee : employees) {
			ActiveTimeInfoTO activeTimeInfoTO = new ActiveTimeInfoTO();
			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
			String startTime = dateFormat.format(employee.getCurrentLoginTime());
			String endTime = dateFormat.format(employee.getCurrentLogoutTime());
			activeTimeInfoTO.setEmployeeId(employee.getEmployeeId());
			activeTimeInfoTO.setFirstName(employee.getFirstName());
			activeTimeInfoTO.setLastName(employee.getLastName());
			activeTimeInfoTO.setProposedStartTime(startTime);
			activeTimeInfoTO.setProposedEndTime(endTime);
			activeTimeInfoTO.setStartTime(startTime);
			activeTimeInfoTO.setEndTime(endTime);
			// TODO: Need to remove the hardcoded logic
			activeTimeInfoTO.setTimeZone("IST");
			activeTimeInfoTOs.add(activeTimeInfoTO);
		}
		Collections.sort(activeTimeInfoTOs);
		return activeTimeInfoTOs;
	}

	@Override
	public List<ActiveTimeInfoTO> getActiveTimeForTeamByTime(String managerId, String date, String startTime,
			String endTime) {
		List<ActiveInfo> activeFinalInfos = new LinkedList<ActiveInfo>();
		List<ActiveInfo> activeInfos = null;
		List<Tracker> trackers = null;
		String initialTimeSliceId = TimeSliceCalculatorUtil.timeToIdConverter(startTime);
		String finalTimeSliceId = TimeSliceCalculatorUtil.timeToIdConverter(endTime);

		try {
			trackers = trackerDao.getActiveTimeInfoByTimeRange(employeeDao.findByEmployeeId(managerId),
					new Date(new SimpleDateFormat("yyyy-MM-dd").parse(date).getTime()));
		} catch (Exception ex) {
			
		}
		for (int i = 0; i < trackers.size(); i++) {
			activeInfos = activeInfoDao.getActiveTimeInfoByTimeRange(trackers.get(i), initialTimeSliceId,
					finalTimeSliceId);
			activeFinalInfos.addAll(activeInfos);
		}
		float[] sumOfActive = new float[trackers.size()];
		for (int i = 0; i < trackers.size(); i++) {
			sumOfActive[i] = 0;
			for (int j = 0; j < activeFinalInfos.size(); j++) {
				if (activeFinalInfos.get(j).getTracker().getTrackerId() == trackers.get(i).getTrackerId()) {
					sumOfActive[i] = sumOfActive[i] + activeFinalInfos.get(j).getActiveTime();
				}
			}
		}
		return createActiveTimeInfoTOFromTracker(trackers, managerId);
	}

	/**
	 * Logic to generate excel data required by the manager to view sub
	 * ordinates information
	 * 
	 * @param managerId
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	@Override
	public EmployeeActiveTimeTO getActiveTimeForAnEmployeeforManagerOnDate(String empId, String manager, String date) {
		Employee empl = employeeDao.findByEmployeeId(empId);
		if (empl.getManagerId().getEmployeeId().equals(manager)) {
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
		} else {
			throw new EmployeeNotFoundException("You are not allowed to see the current information");
		}
	}
}
