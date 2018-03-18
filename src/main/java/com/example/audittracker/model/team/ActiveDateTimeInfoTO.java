package com.example.audittracker.model.team;

import java.util.List;

import com.example.audittracker.model.ActiveTimeInfoTO;


/**
 * 
 * @author vmudigal
 *
 */
public class ActiveDateTimeInfoTO {

    String date;
    List<ActiveTimeInfoTO> activeTimeInfoTOs;

    public String getDate() {
	return date;
    }

    public void setDate(String date) {
	this.date = date;
    }

    public List<ActiveTimeInfoTO> getActiveTimeInfoTOs() {
	return activeTimeInfoTOs;
    }

    public void setActiveTimeInfoTOs(List<ActiveTimeInfoTO> activeTimeInfoTO) {
	this.activeTimeInfoTOs = activeTimeInfoTO;
    }

}
