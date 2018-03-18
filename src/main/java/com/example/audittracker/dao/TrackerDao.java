package com.example.audittracker.dao;

import java.sql.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.audittracker.domain.Employee;
import com.example.audittracker.domain.Tracker;


/**
 * Tracker data access object
 * 
 * @author vmudigal
 *
 */
@Transactional
public interface TrackerDao extends CrudRepository<Tracker, Long> {

	Tracker findByEmployeeAndDate(Employee employee, Date date);

	@Query("Select t FROM Tracker t WHERE t.employee = :employee AND t.date BETWEEN :startDate and :endDate ORDER BY t.date")
	List<Tracker> findByEmployeeAndBetweenDates(@Param("employee") Employee employee,
			@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	@Query("SELECT t FROM Tracker t WHERE t.employee IN (SELECT e FROM Employee e WHERE e.managerId = :employee) AND t.date BETWEEN :startDate AND :endDate order by t.date")
	List<Tracker> getActiveTimeInfoByDateRange(@Param("employee") Employee employee, @Param("startDate") Date startDate,
			@Param("endDate") Date endDate);

	@Query("SELECT t FROM Tracker t WHERE t.employee IN (SELECT e FROM Employee e WHERE e.managerId = :employee) AND t.date = :date")
	List<Tracker> getActiveTimeInfoByDate(@Param("employee") Employee employee, @Param("date") Date date);

	@Query("SELECT t FROM Tracker t WHERE t.employee IN (SELECT e FROM Employee e WHERE e.managerId = :employee) AND t.date = :date")
	List<Tracker> getActiveTimeInfoByTimeRange(@Param("employee") Employee employee, @Param("date") Date date);
	
}