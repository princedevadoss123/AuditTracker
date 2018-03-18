package com.example.audittracker.service;

import java.util.List;

import com.example.audittracker.model.EmployeeActiveTimeTO;

/**
 * 
 * @author vmudigal
 *
 */
public interface OpenService {

    List<EmployeeActiveTimeTO> getActiveTimeForAllEmployeesByDate(String date);

}
