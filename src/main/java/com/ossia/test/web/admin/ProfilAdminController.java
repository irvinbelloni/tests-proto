package com.ossia.test.web.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.ProfilService;
import com.ossia.test.service.TestSheetService;

@Controller
@RequestMapping("/admin")
public class ProfilAdminController {
	
	@Autowired
	ProfilService profilService;
	
	@Autowired
	TestSheetService testSheetService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String displayAdminHome(ModelMap model) {
		
		return "admin-home";
	}
	
	@ModelAttribute("countAdmin")
    public int getCountAdministrators() {
		return profilService.getProfilByRole(true).size();
	}
	
	@ModelAttribute("users")
    public Collection<Profil> getAllUsers() {
		return profilService.getProfilByRole(false);
	}
	
	@ModelAttribute("tests")
    public Collection<TestSheet> getAllTests() {
		return testSheetService.getAllTestSheets();
	}
}
