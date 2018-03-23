package com.example.audittracker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author vmudigal
 *
 */
@Controller
public class MainController {

    @RequestMapping(value = { "","/login","/", "/home", "/team", "/admin","/week","/month","/three" }, method = RequestMethod.GET)
    public String homepage() {
	return "index";
    }

}
