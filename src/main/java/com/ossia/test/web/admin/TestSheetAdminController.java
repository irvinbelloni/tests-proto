package com.ossia.test.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/admin")
public class TestSheetAdminController {

	@RequestMapping(value = "/test/new", method = RequestMethod.GET)
	public String displayTestForm(ModelMap model) {
		return "test-form";
	}
}
