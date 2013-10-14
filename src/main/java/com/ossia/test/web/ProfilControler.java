package com.ossia.test.web;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ossia.test.domain.Profil;
import com.ossia.test.service.ProfilService;

@Controller
public class ProfilControler extends AbstractController {
	
	@SuppressWarnings("unused")
	private final static Logger LOGGER = Logger.getLogger(ProfilControler.class);
	
	@Autowired
	public ProfilService profilService;
	
	@RequestMapping(value="/tests/home", method = RequestMethod.GET)
	public String displayTesterHome(ModelMap model) {
		model.put("selectedTab", TAB_HOME);
		Profil candidate = profilService.getProfilById(((Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
		model.put("candidate", candidate);
		return "candidate-home";
	}
	
	/**
	 * Login action
	 * @param error
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/login", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error, HttpServletRequest request, ModelMap model) { 
		// Checking if user is logged in. If he is, redirecting him to the dashboard page
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			if (auth.getAuthorities().contains("ROLE_CANDIDATE")) {
				return "redirect:" + request.getContextPath() + "/test";
			}
			return "redirect:" + request.getContextPath() + "/admin/home";
		}
		if (error != null) {
			model.put("error",  true);
		}
		
		return "login"; 
	}	
}
