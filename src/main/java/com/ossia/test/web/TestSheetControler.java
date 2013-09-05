package com.ossia.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ossia.test.service.TestSheetService;

@Controller
public class TestSheetControler {
	
	@Autowired
	public TestSheetService testSheetService ; 
	
	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String displayQuestion(ModelMap model) {
		return "question";
	}
}
