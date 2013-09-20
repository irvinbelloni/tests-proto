package com.ossia.test.web.admin;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.ProfilService;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.form.AssignTestForm;
import com.ossia.test.web.sort.ProfilSortingInfo;

@Controller
@RequestMapping("/admin")
public class EvaluationController {
	
	@Autowired 
	private ProfilService profilService ; 
	
	@Autowired
	private EvaluationService evaluationService ; 

	@Autowired
	private TestSheetService testSheetService ; 
	
	@ModelAttribute("testLists")
    public Collection<TestSheet> getAllTests() {
		return testSheetService.getAllTestSheets();
	}
	
	@ModelAttribute("resultats")
	public List<Evaluation> getResultats () {
		return evaluationService.getAllActiveResultats() ; 
	}
	
	@ModelAttribute("candidates")
	public Collection<Profil> displayCandidatesList() {
		return profilService.getSortedProfilByRole(false, new ProfilSortingInfo() );
	}
	
	@RequestMapping(value = "/resultats", method = RequestMethod.GET)
	public String displayResultats (ModelMap model ) {
		
		
		model.put("assignForm", new AssignTestForm())  ; 
		return "resultat-home" ;
	}

}
