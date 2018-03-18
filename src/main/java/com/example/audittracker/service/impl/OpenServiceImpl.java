package com.example.audittracker.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.audittracker.dao.EmployeeDao;
import com.example.audittracker.domain.Employee;
import com.example.audittracker.model.EmployeeActiveTimeTO;
import com.example.audittracker.service.EmployeeService;
import com.example.audittracker.service.OpenService;



/**
 * 
 * @author vmudigal
 *
 */
@Service("openService")
public class OpenServiceImpl implements OpenService {

    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private EmployeeDao employeeDao;

    @Override
    public List<EmployeeActiveTimeTO> getActiveTimeForAllEmployeesByDate(String date) {
	List<Employee> employees = (List<Employee>) employeeDao.findAll();
	List<EmployeeActiveTimeTO> employeeActiveTimeTOs = new LinkedList<EmployeeActiveTimeTO>();
	for (Employee employee: employees) {
	    EmployeeActiveTimeTO employeeActiveTimeTO = employeeService.getActiveTimeForAnEmployeeOnDate(employee.getEmployeeId(), date);
	    employeeActiveTimeTOs.add(employeeActiveTimeTO);
	}
	return employeeActiveTimeTOs;
    }

}
