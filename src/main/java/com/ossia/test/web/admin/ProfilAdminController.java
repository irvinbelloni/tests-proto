package com.ossia.test.web.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.ProfilService;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.sort.ProfilSortingInfo;

@Controller
@RequestMapping("/admin")
public class ProfilAdminController {
	
	private final static String SESSION_ADMINISTRATOR_LIST_SORT = "candidate.list.sort";
	private final static String SESSION_CANDIDATE_LIST_SORT = "candidate.list.sort";
	private final static String SESSION_RECENT_PROFIL = "recent.profil";
	
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
	public String displayAdministratorsList(@RequestParam(value = "sort", required = false) String sortingField, @RequestParam(value = "direction", required = false) String sortingDirection, ModelMap model, HttpServletRequest request) {
		model.put("profil", new Profil());
		
		// Sorting information
		ProfilSortingInfo sortingInfo = completeProfilSortingInfo((ProfilSortingInfo)request.getSession().getAttribute(SESSION_ADMINISTRATOR_LIST_SORT), sortingField, sortingDirection);	
		request.getSession().setAttribute(SESSION_ADMINISTRATOR_LIST_SORT, sortingInfo);
		
		// Recent profil (if a profil has just been created or updated)
		Profil recentProfil = (Profil)request.getSession().getAttribute(SESSION_RECENT_PROFIL);
		request.getSession().removeAttribute(SESSION_RECENT_PROFIL);
		model.put("recentProfil",  recentProfil);
		
		model.put("administrators", profilService.getSortedProfilByRole(true, sortingInfo));
		model.put("sortingInfo", sortingInfo);
		model.put("selectedTab", TAB_ADMINISTRATOR);
		return "administrators";
	}
	
	@RequestMapping(value = "/candidates", method = RequestMethod.GET)
	public String displayCandidatesList(@RequestParam(value = "sort", required = false) String sortingField, @RequestParam(value = "direction", required = false) String sortingDirection, ModelMap model, HttpServletRequest request) {
		model.put("profil", new Profil());
		
		// Sorting information
		ProfilSortingInfo sortingInfo = completeProfilSortingInfo((ProfilSortingInfo)request.getSession().getAttribute(SESSION_CANDIDATE_LIST_SORT), sortingField, sortingDirection);	
		request.getSession().setAttribute(SESSION_CANDIDATE_LIST_SORT, sortingInfo);
		
		// Recent profil (if a profil has just been created or updated)
		Profil recentProfil = (Profil)request.getSession().getAttribute(SESSION_RECENT_PROFIL);
		request.getSession().removeAttribute(SESSION_RECENT_PROFIL);
		model.put("recentProfil",  recentProfil);
		
		model.put("candidates", profilService.getSortedProfilByRole(false, sortingInfo));
		model.put("sortingInfo", sortingInfo);
		model.put("selectedTab", TAB_CANDIDATE);
		return "candidates";
	}
	
	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public String displayCandidateDetail(@RequestParam(value = "candidate", required = false) Integer candidateId, ModelMap model) {
		return "candidate";
	}
	
	/**
	 * Activates or deactivates a profile
	 * @param profileId Id of the user
	 * @param origin Origin (administrators | candidates) page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/profile/activate", method = RequestMethod.GET)
	public String changeProfileActivation(@RequestParam(value = "profile") Integer profileId, @RequestParam(value = "origin") String origin, ModelMap model, HttpServletRequest request) {
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.getSession().setAttribute(SESSION_RECENT_PROFIL, profilService.changeActivation(profileId, admin.getId()));
		return "redirect:/admin/" + origin;
	}
		
	/**
	 * Deletes a profile
	 * @param profileId Id of the user
	 * @param origin Origin (administrators | candidates) page
	 * @param model
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/profile/delete", method = RequestMethod.GET)
	public String deleteProfile(@RequestParam(value = "profile") Integer profileId, @RequestParam(value = "origin") String origin, ModelMap model, HttpServletRequest request) {
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		request.getSession().setAttribute(SESSION_RECENT_PROFIL, profilService.deleteProfil(profileId, admin.getId()));
		return "redirect:/admin/" + origin;
	}
	
	/**
	 * Adds or edits a profile
	 * @param profil Profile to add or edit (form data)
	 * @param origin Origin (administrators | candidates) page
	 * @param result
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/profile/add-or-edit", method = RequestMethod.POST)
	public String addOrEditProfile(@RequestParam(value = "origin") String origin, @Valid Profil profil, BindingResult result, HttpServletRequest request, ModelMap model) {
		model.put("selectedTab", TAB_CANDIDATE);
		model.put("sortingInfo", (ProfilSortingInfo)request.getSession().getAttribute(SESSION_CANDIDATE_LIST_SORT));
		if (origin.equals("administrators")) {
			model.put("selectedTab", TAB_ADMINISTRATOR);
			model.put("sortingInfo", (ProfilSortingInfo)request.getSession().getAttribute(SESSION_ADMINISTRATOR_LIST_SORT));
		}
		if (result.hasErrors()) {			
			return origin;
		}		
		// Form data is valid
		profil.setAdmin(false);
		if (origin.equals("administrators")) {
			profil.setAdmin(true);
		}
		
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		try {
			if (profil.getMode().equals(Profil.MODE_ADD)) {
				profilService.createProfil(profil, admin.getId());			
			} else {
				profilService.updateProfil(profil, admin.getId());
			}
		} catch (ConstraintViolationException cve) { // Login is already used by someone else
			rejectDuplicateLogin(cve, result);
			return origin;
			
		} catch (DataIntegrityViolationException dive) {
			rejectDuplicateLogin(dive, result);
			return origin;
		}
		
		model.remove("selectedTab");
		request.getSession().setAttribute(SESSION_RECENT_PROFIL, profil);
		return "redirect:/admin/" + origin;
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
	
	private void rejectDuplicateLogin (Exception e, BindingResult result) {
		if (e.getMessage().contains("Duplicate") && e.getMessage().contains("login")) {
			result.rejectValue("login", "form.error.login.already.exists");
		}
	}
}