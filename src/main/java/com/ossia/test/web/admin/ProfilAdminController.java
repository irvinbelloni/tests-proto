package com.ossia.test.web.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.ProfilService;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.sort.ProfilSortingInfo;

@Controller
@RequestMapping("/admin")
public class ProfilAdminController {
	
	private final static String SESSION_CANDIDATE_LIST_SORT = "candidate.list.sort";
	private final static String TAB_HOME = "home";
	private final static String TAB_ADMINISTRATOR = "administrator";
	private final static String TAB_CANDIDATE = "candidate";
	
	@Autowired
	ProfilService profilService;
	
	@Autowired
	TestSheetService testSheetService;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String displayAdminHome(ModelMap model) {
		model.put("selectedTab", TAB_HOME);
		return "admin-home";
	}
	
	@RequestMapping(value = "/administrators", method = RequestMethod.GET)
	public String displayAdministratorsList(ModelMap model) {
		model.put("selectedTab", TAB_ADMINISTRATOR);
		return "administrators";
	}
	
	@RequestMapping(value = "/candidates", method = RequestMethod.GET)
	public String displayCandidatesList(@RequestParam(value = "sort", required = false) String sortingField, @RequestParam(value = "direction", required = false) String sortingDirection, ModelMap model, HttpServletRequest request) {
		model.put("profil", new Profil());
		
		ProfilSortingInfo sortingInfo = completeProfilSortingInfo((ProfilSortingInfo)request.getSession().getAttribute(SESSION_CANDIDATE_LIST_SORT), sortingField, sortingDirection);	
		request.getSession().setAttribute(SESSION_CANDIDATE_LIST_SORT, sortingInfo);
		
		model.put("candidates", profilService.getSortedProfilByRole(false, sortingInfo));
		model.put("sortingInfo", sortingInfo);
		model.put("selectedTab", TAB_CANDIDATE);
		return "candidates";
	}
	
	@RequestMapping(value = "/candidates", method = RequestMethod.POST)
	public String addOrEditCandidate(@Valid Profil profil, BindingResult result, HttpServletRequest request, ModelMap model) {
		// TODO valider l'unicité du login et email
		if (result.hasErrors()) {
			model.put("selectedTab", TAB_CANDIDATE);
			return "candidates";
		}		
		// Form data is valid
		profil.setAdmin(false);
		try {
			if (profil.getMode().equals(Profil.MODE_ADD)) {
				profilService.createProfil(profil);				
			} else {
				profilService.updateProfil(profil);
				profilService.getProfilByLogin("default");
			}
		} catch (ConstraintViolationException cve) { // Login is already used by someone else
			if (cve.getMessage().contains("Duplicate") && cve.getMessage().contains("login")) {
				model.put("selectedTab", TAB_CANDIDATE);
				result.rejectValue("login", "form.error.login.already.exists");
				return "candidates";
			}
		} 
		return "redirect:/admin/candidates";
	}
	
	@ModelAttribute("candidates")
    public Collection<Profil> getAllUsers() {
		return profilService.getSortedProfilByRole(false, new ProfilSortingInfo());
	}
	
	@ModelAttribute("administrators")
    public Collection<Profil> getAllAdministrators() {
		return profilService.getProfilByRole(true);
	}
	
	@ModelAttribute("tests")
    public Collection<TestSheet> getAllTests() {
		return testSheetService.getAllTestSheets();
	}
	
	private ProfilSortingInfo completeProfilSortingInfo (ProfilSortingInfo sortingInfo, String sortingField, String sortingDirection) {
		if (sortingInfo == null) {
			sortingInfo = new ProfilSortingInfo();
		}
		if (sortingField != null) {
			if (ProfilSortingInfo.SORT_ACTIVE.equals(sortingField) || ProfilSortingInfo.SORT_CREATION_DATE.equals(sortingField) || ProfilSortingInfo.SORT_NAME.equals(sortingField) || ProfilSortingInfo.SORT_FIRSTNAME.equals(sortingField)) {
				sortingInfo.setSortingField(sortingField);
			}
		}
		if (sortingDirection != null) {
			if (ProfilSortingInfo.ASC.equals(sortingDirection) || ProfilSortingInfo.DESC.equals(sortingDirection)) {
				sortingInfo.setSortingDirection(sortingDirection);
			}
		}
		
		return sortingInfo;
	}
 }