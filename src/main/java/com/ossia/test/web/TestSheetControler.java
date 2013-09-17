package com.ossia.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.TestSheetService;

@Controller
@RequestMapping("/tests")
public class TestSheetControler extends AbstractController {
	
	@Autowired
	private TestSheetService testSheetService;
	
	@Autowired 
	private EvaluationService evaluationService;	
	
	@RequestMapping(value = "/setup", method = RequestMethod.GET)
	public String displayTestSetup(@RequestParam("test") Integer evalId, ModelMap model) {
		Evaluation evaluation = evaluationService.getEvaluationById(evalId);		
		Profil candidate = (Profil)model.get("candidate");
		if (evaluation == null || evaluation.getProfil().getId() != candidate.getId()) {
			return "redirect:/errors/404";
		}
		
		model.put("evaluation", evaluation);
		return "test-setup";
	}
	
	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String displayQuestion(ModelMap model) {
		return "question";
	}	
}
