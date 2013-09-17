package com.ossia.test.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorController {

	@RequestMapping(value="/errors/404")
    public String handle404() {
		return "error404";
    }
	
	@RequestMapping(value="/errors/403")
    public String handle403() {
		return "error403";
    }
	
	@RequestMapping(value="/errors/500")
    public String handle500() {
		// TODO send email to specify exception
		return "errorException";
    }
	
	@RequestMapping(value="/errors/exception")
    public String handleException() {
		// TODO send email to specify exception
		return "errorException";
    }
}
