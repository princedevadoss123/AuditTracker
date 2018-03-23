package com.example.audittracker.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

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
	
	@Query("SELECT a FROM Employee a WHERE a.employeeId = :id AND a.password = :pass")
	Employee findByEmailAndPass(@Param("id")String id,@Param("pass")String pass);
	
	Employee findByEmail(String email);

	List<Employee> findByManagerId(Employee id);

}