package com.example.audittracker.dao;

import java.util.List;

import javax.transaction.Transactional;
import org.springframework.data.repository.CrudRepository;

import com.example.audittracker.domain.Employee;


/**
 * Employee related data access object
 * 
 * @author vmudigal
 *
 */
@Transactional
public interface EmployeeDao extends CrudRepository<Employee, String> {

	Employee findByEmployeeId(String employeeId);

	Employee findByEmail(String email);

	List<Employee> findByManagerId(Employee id);

}