package com.example.audittracker.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.audittracker.model.ActiveTimeInfoTO;
import com.example.audittracker.model.EmployeeActiveTimeTO;
import com.example.audittracker.model.team.ActiveDateTimeInfoTO;
import com.example.audittracker.service.ManagerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * Rest Controller which accepts the web request and returns the response for
 * team details.
 * 
 * @author vmudigal
 *
 */
@RestController
@Api(value = "/manager")
@RequestMapping(value = "/manager")
public class ManagerController {
    
	
    @Autowired
    private ManagerService managerService;
    
    /**
     * Gets the Team active time information for a given date range.
     * 
     * @param managerId
     * @param startDate
     * @param endDate
     * 
     * @return list of activeTimeInfoTO object in json format.
     */
    @ApiOperation(value = "Get active time info", notes = "Get active time info for the team by date range")
    @RequestMapping(value = "/{id}/date/start/{start_date}/end/{end_date}/time/start/{start_time}/end/{end_time}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ActiveDateTimeInfoTO> getActiveTimeForTheTeam(@PathVariable(value = "id") String empid,
	    @ApiParam(value = "Start Date", required = true) @PathVariable(value = "start_date") String startDate,
	    @ApiParam(value = "End Date", required = true) @PathVariable(value = "end_date") String endDate,
	    @ApiParam(value = "Start Time", required = true) @PathVariable(value = "start_time") String startTime,
	    @ApiParam(value = "End Time", required = true) @PathVariable(value = "end_time") String endTime) {
	return managerService.getActiveTimeByDateTimeRange(
		empid, startDate, endDate, startTime,
		endTime);
    }
    @ApiOperation(value = "Get active time info", notes = "Get active time info for the team by date range")
    @RequestMapping(value = "/{id}/date/start/{start_date}/end/{end_date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ActiveDateTimeInfoTO> getActiveTimeForATeam(@PathVariable(value = "id") String empid,
	    @ApiParam(value = "Start Date", required = true) @PathVariable(value = "start_date") String startDate,
	    @ApiParam(value = "End Date", required = true) @PathVariable(value = "end_date") String endDate) {
	return managerService.getActiveTimeByDateRange(empid, startDate, endDate);
    }

    @ApiOperation(value = "Get active time", notes = "Get active time for an employee on given date", response = ActiveTimeInfoTO.class)
    @RequestMapping(value = "/{id}/emp/{empid}/date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public EmployeeActiveTimeTO getActiveTimeForAnManagerOnDate(@PathVariable(value = "id") String empid,@PathVariable(value = "empid") String emp,
	    @ApiParam(value = "Date", required = true) @PathVariable(value = "date") String date) {
	return managerService.getActiveTimeForAnEmployeeforManagerOnDate(emp,empid, date);
    }

    /**
     * Gets the Team active time information for a given date .
     * 
     * @param managerId
     * @param Date
     * 
     * @return list of activeTimeInfoTO object in json format.
     */
    @ApiOperation(value = "Get active time info on particular date", notes = "Get active time info for the team by date")
    @RequestMapping(value = "/{id}/date/{date}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ActiveTimeInfoTO> getActiveTimeForATeam(@PathVariable(value = "id") String empid,
	    @ApiParam(value = "Start Date", required = true) @PathVariable(value = "date") String date) {
	return managerService.getActiveTimeByDate(empid, date);
    }

    /**
     * Gets the Team active time information for a given date .
     * 
     * @param managerId
     * @param Date
     * @param starttime
     * @param endtime
     * 
     * @return list of activeTimeInfoTO object in json format.
     */
    @ApiOperation(value = "Get active time for range of time", notes = "get active time for a team")
    @RequestMapping(value = "/{id}/date/{date}/time/start/{starttime}/end/{endtime}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ActiveTimeInfoTO> getActiveTimeForAnTeamForTimeRange(@PathVariable(value = "id") String empid,
	    @ApiParam(value = "Date", required = true) @PathVariable(value = "date") String date,
	    @ApiParam(value = "Start Time", required = true) @PathVariable(value = "starttime") String startTime,
	    @ApiParam(value = "End Time", required = true) @PathVariable(value = "endtime") String endTime) {
	return managerService.getActiveTimeForTeamByTime(empid, date, startTime, endTime);
    }
    
    @RequestMapping(value = "/uploadfile/upload", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public void singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
    	byte b[]=new byte[(int)file.getSize()];
    	try {
			b=file.getBytes();
			FileOutputStream fw=new FileOutputStream("C:\\Users\\I328242\\Documents\\context.txt");
			fw.write(b);
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(file.getOriginalFilename()+" "+file.getSize());
    	
    }
   
}
