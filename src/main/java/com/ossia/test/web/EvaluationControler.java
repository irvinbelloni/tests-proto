package com.ossia.test.web;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.Response;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.form.QuestionForm;

@Controller @RequestMapping("/tests")
public class EvaluationControler extends AbstractController {
	
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
		model.put("testEnCours",  true);
		model.put("selectedTab", TAB_CURRENT_TEST);
		return "test-setup";
	}
	
	@RequestMapping(value = "/end-of-test", method = RequestMethod.GET)
	public String displayEndOfTest(@RequestParam("test") Integer evalId, ModelMap model) {
		evaluationService.closeTest(evalId);
		return "end-of-test";
	}
	
	@RequestMapping(value = "/question", method = RequestMethod.GET)
	public String displayQuestion(@RequestParam("test") Integer evalId, @RequestParam("question") Integer questionIndex, ModelMap model) {
		
		// Checking that the evaluation exists and that the test is really assign to the candidate
		Evaluation evaluation = evaluationService.getEvaluationById(evalId);		
		Profil candidate = (Profil)model.get("candidate");
		if (evaluation == null || evaluation.getProfil().getId() != candidate.getId()) {
			return "redirect:/errors/404";
		}
		
		// Checking that the test has questions
		if (evaluation.getTest().getQuestions().isEmpty()) {
			return "redirect:/errors/404";
		}		
		
		// Checking that the question number is less that the total questions count
		if (questionIndex < 1 || questionIndex > evaluation.getTest().getQuestionSize()) { // If not, redirect to question 1
			return "redirect:/tests/question?test=" + evaluation.getId() + "&question=1";
		}
		
		// If this is the first question, we start the test by setting the start time in the evaluation
		if (evaluation.isTestAssigned()) {
			evaluationService.markTestAsStarted(evaluation);
		}
		
		// Checking that the time allowed to pass the test is not over
		if (evaluationService.isTimeOver(evaluation)) {
			return "redirect:/tests/end-of-test?test=" + evaluation.getId();
		}
					
		model.put("questionForm", buildQuestionForm(evaluation, questionIndex));		
		
		model.put("question",  evaluation.getTest().getQuestions().get(questionIndex - 1));
		model.put("questionCount", questionIndex);
		model.put("evaluation", evaluation);	
		
		// Remaining time
		model.put("remainingTime", getRemainingTime(evaluation));
		
		model.put("testEnCours",  true);
		model.put("selectedTab", TAB_CURRENT_TEST);
		return "question";
	}	
	
	@RequestMapping(value = "/validate-question", method = RequestMethod.POST)
	public String saveQuestion(@Valid QuestionForm questionForm, BindingResult result, ModelMap model) {
		
		Evaluation evaluation = evaluationService.saveCandidateResponse(questionForm);
		if (evaluation == null) {
			return "redirect:/errors/404";
		}
		
		// Last question was answer, the test is now over
		if (evaluation.getTest().getQuestionSize() == questionForm.getQuestionIndex()) {
			return "redirect:/tests/end-of-test?test=" + evaluation.getId();
		}
		
		// On to the next question ...
		return "redirect:/tests/question?test=" + questionForm.getEvaluationId() + "&question=" + (questionForm.getQuestionIndex() + 1);
	}
	
	private QuestionForm buildQuestionForm(Evaluation evaluation, int questionIndex) {
		QuestionForm form = new QuestionForm();
		form.setQuestionIndex(questionIndex);
		form.setEvaluationId(evaluation.getId());
		Question currentQuestion = evaluation.getTest().getQuestions().get(questionIndex - 1);		
		form.setQuestionId(currentQuestion.getId());
		
		for(Response response : evaluation.getResponses()) {
			if (response.getQuestion().getId() == currentQuestion.getId()) {
				int[] reponsesCochees = new int[response.getReponsesChoisies().size()];
				int i = 0;
				for (PropositionReponse proposition : response.getReponsesChoisies()) {
					reponsesCochees[i] = proposition.getId();
					i ++;
				}
				form.setPropositions(reponsesCochees);
			}
		}		
		return form;
	}
	
	private int getRemainingTime(Evaluation evaluation) {
		int testLength = evaluation.getTest().getDuree() * 60;
		Date now = new Date();
		int elapsedTime = (int) ((now.getTime() - evaluation.getStartTime().getTime()) / 1000);
		
		return testLength - elapsedTime;		
	}
}
