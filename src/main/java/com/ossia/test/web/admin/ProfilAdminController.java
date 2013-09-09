package com.ossia.test.web.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
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
	
	@RequestMapping(value = "/administrators", method = RequestMethod.GET)
	public String displayAdministratorsList(ModelMap model) {		
		return "administrators";
	}
	
	@RequestMapping(value = "/candidates", method = RequestMethod.GET)
	public String displayCandidatesList(ModelMap model) {
		model.put("profil", new Profil());
		return "candidates";
	}
	
	@RequestMapping(value = "/candidates", method = RequestMethod.POST)
	public String addOrEditCandidate(@Valid Profil profil, BindingResult result, HttpServletRequest request, ModelMap model) {
		// TODO valider l'unicité du login et email
		if (result.hasErrors()) {
			return "candidates";
		}
		
		// Form data is valid
		profil.setAdmin(false);
		profilService.createProfil(profil);
		return "redirect:/admin/candidates";
	}
	
	@ModelAttribute("candidates")
    public Collection<Profil> getAllUsers() {
		return profilService.getProfilByRole(false);
	}
	
	@ModelAttribute("administrators")
    public Collection<Profil> getAllAdministrators() {
		return profilService.getProfilByRole(true);
	}
	
	@ModelAttribute("tests")
    public Collection<TestSheet> getAllTests() {
		return testSheetService.getAllTestSheets();
	}
}