package com.ossia.test.web.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.ProfilService;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.form.AssignTestForm;
import com.ossia.test.web.sort.ProfilSortingInfo;

@Controller @RequestMapping("/admin")
public class ProfilAdminController extends AbstractAdminController {
	
	private final static String SESSION_ADMINISTRATOR_LIST_SORT = "candidate.list.sort";
	private final static String SESSION_CANDIDATE_LIST_SORT = "candidate.list.sort";	
	
	@Autowired
	ProfilService profilService;
	
	@Autowired
	TestSheetService testSheetService;
	
	@Autowired
	EvaluationService evaluationService;

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
		
		setLastActionInModel(model, request);
		
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
				
		setLastActionInModel(model, request);
		
		model.put("candidates", profilService.getSortedProfilByRole(false, sortingInfo));
		model.put("sortingInfo", sortingInfo);
		model.put("selectedTab", TAB_CANDIDATE);
		return "candidates";
	}
	
	@RequestMapping(value = "/candidate", method = RequestMethod.GET)
	public String displayCandidateDetail(@RequestParam(value = "candidate", required = true) Integer candidateId, @RequestParam(value = "evaluation", required = false) Integer evalId, ModelMap model, HttpServletRequest request) {
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Profil profil = profilService.getProfilById(candidateId);
		if (profil == null) {
			return "redirect:/errors/404";
		}
		
		setLastActionInModel(model, request);
		
		// Deleting the test assigned to this candidate
		if (evalId != null) {
			Evaluation deletedEval = evaluationService.deleteEvaluation(evalId, candidateId, true, admin);
			if (deletedEval != null) {
				model.put("lastAction", buildNotifyMessage("text.notify.deassign.test.to.candidate", deletedEval.getTest().getIntitule(), profil.getPrenom(), profil.getNom()));
			}
		}
				
		// Test assigning form
		model.put("assignTest", new AssignTestForm());
		
		model.put("profil",  profil);
		model.put("selectedTab", TAB_CANDIDATE);
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
		
		Profil changedProfile = profilService.changeActivation(profileId, admin);
		if (changedProfile == null) {
			return "redirect:/errors/404";
		}
		String messageCodeForLastAction = "text.notify.deactivate.";
		if (changedProfile.isEnabled()) {
			messageCodeForLastAction = "text.notify.activate."; 
		}
		if (changedProfile.isAdmin()) {
			messageCodeForLastAction += "administrator";
		} else {
			messageCodeForLastAction += "candidate";
		}
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage(messageCodeForLastAction, changedProfile.getPrenom(), changedProfile.getNom()));
		
		String redirectUrl = "/admin/" + origin;
		if (origin.equals("candidate")) {
			redirectUrl += "?candidate=" + profileId;
		}
		return "redirect:" + redirectUrl;
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
		
		Profil deletedProfile = profilService.deleteProfil(profileId, admin);
		if (deletedProfile == null) {
			return "redirect:/errors/404";
		}
		String messageCodeForLastAction = "text.notify.delete.candidate";
		if (deletedProfile.isAdmin()) {
			messageCodeForLastAction = "text.notify.delete.administrator";
		}
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage(messageCodeForLastAction, deletedProfile.getPrenom(), deletedProfile.getNom()));
		
		String redirectUrl = "/admin/";
		if (origin.startsWith("candidate")) {
			redirectUrl += "candidates";
		} else {
			redirectUrl += "administrators";
		}
		return "redirect:" + redirectUrl;
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
		model.remove("selectedTab");
		
		// Form data is valid
		profil.setAdmin(false);
		if (origin.equals("administrators")) {
			profil.setAdmin(true);
		}
		
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String messageCodeForLastAction = null;
		try {
			if (profil.getMode().equals(Profil.MODE_ADD)) {
				profilService.createProfil(profil, admin);
				messageCodeForLastAction = "text.notify.add.candidate";
				if (profil.isAdmin()) {
					messageCodeForLastAction = "text.notify.add.administrator";
				}
			} else {
				profilService.updateProfil(profil, admin);
				messageCodeForLastAction = "text.notify.edit.candidate";
				if (profil.isAdmin()) {
					messageCodeForLastAction = "text.notify.edit.administrator";
				}
			}
		} catch (NotFoundException nfe) { // Trying to edit a profil that does not exist
			return "redirect:/errors/404";
		} catch (ConstraintViolationException cve) { // Login is already used by someone else (ADD mode)
			rejectDuplicateLogin(cve, result);
			return origin;			
		} catch (DataIntegrityViolationException dive) { // Login is already used by someone else (EDIT mode)
			rejectDuplicateLogin(dive, result);
			return origin;
		}		
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage(messageCodeForLastAction, profil.getPrenom(), profil.getNom()));
		String redirectUrl = "/admin/" + origin;
		if (origin.equals("candidate")) {
			redirectUrl += "?candidate=" + profil.getId();
		}
		return "redirect:" + redirectUrl;
	}
	
	/**
	 * Assign a test to a candidate
	 * @param assignTestForm
	 * @param result
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/profile/assign-test", method = RequestMethod.POST)
	public String assignTest(@Valid AssignTestForm assignTestForm, BindingResult result, HttpServletRequest request, ModelMap model) {
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.put("sortingInfo", (ProfilSortingInfo)request.getSession().getAttribute(SESSION_CANDIDATE_LIST_SORT));
	
		String redirectUrl = "/admin/candidate?candidate=" + assignTestForm.getCandidateId();
		
		if (assignTestForm.getTestId() == 0) { // No test has been picked
			return "redirect:" + redirectUrl;
		}
		
		Evaluation evaluation = evaluationService.assignTest(assignTestForm.getTestId(), assignTestForm.getCandidateId(), admin);
		
		if (evaluation == null) {
			String errorAction = buildNotifyMessage("text.notify.assign.test.to.candidate.error");
			request.getSession().setAttribute(SESSION_ERROR_ACTION, errorAction);		
		} else {
			String lastAction = buildNotifyMessage("text.notify.assign.test.to.candidate", evaluation.getTest().getIntitule(), evaluation.getProfil().getPrenom(), evaluation.getProfil().getNom());
			request.getSession().setAttribute(SESSION_LAST_ACTION, lastAction);			
		}
		
		return "redirect:" + redirectUrl;
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