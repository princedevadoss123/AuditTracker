package com.example.audittracker.service;

import java.util.List;

import com.example.audittracker.model.ActiveTimeInfoTO;
import com.example.audittracker.model.EmployeeActiveTimeTO;
import com.example.audittracker.model.team.ActiveDateTimeInfoTO;



/**
 * 
 * @author vmudigal
 *
 */
public interface ManagerService {

    List<ActiveTimeInfoTO> getActiveTimeByDate(String managerId, String date);

    List<ActiveDateTimeInfoTO> getActiveTimeByDateRange(String managerId, String startDate, String endDate);

    List<ActiveDateTimeInfoTO> getActiveTimeByDateTimeRange(String managerId, String startDate, String endDate,String startTime,String endTime);

    List<ActiveTimeInfoTO> getActiveTimeForTeamByTime(String empId, String date, String startTime, String endTime);
    
    EmployeeActiveTimeTO getActiveTimeForAnEmployeeforManagerOnDate(String empId,String manager, String date);

}
