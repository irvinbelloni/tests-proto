package com.ossia.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ossia.test.service.TestSheetService;

@Controller
public class TestSheetControler {
	
	@Autowired
	public TestSheetService testSheetService ; 

}
