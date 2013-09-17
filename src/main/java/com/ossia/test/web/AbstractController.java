package com.ossia.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.ossia.test.domain.Profil;
import com.ossia.test.service.ProfilService;

public abstract class AbstractController {
	
	protected final static String SESSION_LAST_ACTION = "last.action";
	protected final static String SESSION_ERROR_ACTION = "error.action";
	
	protected final static String TAB_HOME = "home";
	
	@Autowired
	protected ProfilService profilService;
	
	@ModelAttribute("candidate") 
	public Profil getCandidate() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication(); 
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			Profil user = (Profil)auth.getPrincipal();
			if (user != null) { 
				return profilService.getProfilById(user.getId());
			}
		}
		return null;
	}
}
