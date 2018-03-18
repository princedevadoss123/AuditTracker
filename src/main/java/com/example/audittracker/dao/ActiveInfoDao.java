package com.example.audittracker.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.audittracker.domain.ActiveInfo;
import com.example.audittracker.domain.Tracker;



/**
 * Active info related data access object
 * 
 * @author vmudigal
 *
 */
@Transactional
public interface ActiveInfoDao extends CrudRepository<ActiveInfo, Long> {

	List<ActiveInfo> findByTracker(Tracker tracker);
	
	ActiveInfo findByTrackerAndTimeSliceId(Tracker tracker, String timeSliceId);

	@Query("SELECT a FROM ActiveInfo a WHERE a.tracker = :tracker AND a.timeSliceId BETWEEN :startTime AND :endTime")
	List<ActiveInfo> getActiveTimeInfoByTimeRange(@Param("tracker") Tracker tracker, @Param("startTime") String start,
			@Param("endTime") String end);

}
