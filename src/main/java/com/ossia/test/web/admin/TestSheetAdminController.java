package com.ossia.test.web.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.TestSheetService;

@Controller
@RequestMapping("/admin")
public class TestSheetAdminController {
	
	private final Log log = LogFactory.getLog(getClass()) ; 
	
	@Autowired
	private TestSheetService testSheetService ; 

	@RequestMapping(value = "/test/home", method = RequestMethod.GET)
	public String displayTestHome(ModelMap model) {
		
		model.put("testLists", testSheetService.getAllTestSheets()  ) ; 
		model.put("testSheet", new TestSheet()) ; 
		
		return "test-home";
	}
	
	@RequestMapping(value = "/test/detail", method = RequestMethod.GET)
	public String displayTestDetailForm(ModelMap model ,BindingResult result, HttpServletRequest arg0 ) {
		
		Integer identifier = Integer.parseInt( arg0.getParameter("id") ) ;  
		TestSheet testSheet = testSheetService.getTestSheetById( identifier ) ; 
		model.put("question", new Question () ) ; 
		model.put("questions", testSheet.getQuestions() ) ; 
		model.put("testSheet", testSheet ) ; 
		
		return "test-detail";
	}
	
	@RequestMapping(value = "/test/createUpdate", method = RequestMethod.POST)
	public String addOrEditTest (@Valid TestSheet testSheet , BindingResult result, ModelMap model) {
		
		if (result.hasErrors()) {
			return "test-home";
		}
		
		if (testSheet.getId().equals(null) || testSheet.getId() == 0) {
			testSheet = testSheetService.createTestSheet(testSheet) ;
		} else {
			testSheet = testSheetService.updateTestSheet(testSheet) ; 
		}
		
		model.put("testLists", testSheetService.getAllTestSheets()  ) ; 
		model.put("testSheet", new TestSheet()) ; 
		
		return "test-home" ; 
	}
	
	@RequestMapping(value = "/test/delete", method = RequestMethod.GET)
	public String deleteTest(HttpServletRequest arg0 , ModelMap model ) {
		
		log.debug( arg0.getParameter("id") ) ; 
		
		Integer identifier = Integer.parseInt( arg0.getParameter("id") ) ;  
		TestSheet testSheetToDelete = testSheetService.getTestSheetById( identifier ) ; 
		testSheetService.deleteTestSheet(testSheetToDelete) ; 
		
		model.put("testLists", testSheetService.getAllTestSheets() ) ; 
		model.put("testSheet", new TestSheet()) ; 
		
		return "test-home";
	}

	@RequestMapping(value = "/test/print", method = RequestMethod.GET)
	public String printTest(HttpServletRequest arg0 , ModelMap model ) {
		
		Integer.parseInt( arg0.getParameter("id") ) ;
		
		// TODO TDS - Complete implementation 
		
		model.put("testLists", testSheetService.getAllTestSheets() ) ; 
		model.put("testSheet", new TestSheet()) ; 
		
		return "test-home";
	}
	
	@RequestMapping(value = "/question/detail", method = RequestMethod.GET)
	public String displayQuestionDetail(ModelMap model ,BindingResult result, HttpServletRequest arg0 ) {
		
		Integer identifier = Integer.parseInt( arg0.getParameter("id") ) ;  
		
		Question question = testSheetService.getQuestionById(identifier) ;
		model.put("proposition", new PropositionReponse() ) ; 
		model.put("propositions", question.getPropositionsReponses()) ; 
		model.put("question", question) ; 
		
		return "question-detail" ; 
	}
	
	@RequestMapping(value = "/question/createUpdate", method = RequestMethod.POST)
	public String addEditQuestionToTest (@Valid Question question , HttpServletRequest arg0 , BindingResult result, ModelMap model) {
		
		if (result.hasErrors()) {
			return "test-detail";
		}
		
		if (question.getId().equals(null) || question.getId() == 0) {
			question = testSheetService.createQuestion(question) ;
		} else {
			question = testSheetService.updateQuestion(question) ; 
		}
		
		model.put("question", new Question () ) ; 
		model.put("questions", question.getTest().getQuestions() ) ; 
		model.put("testSheet", question.getTest() ) ; 
		
		return "test-detail" ; 
	}
	
	@RequestMapping(value = "/question/delete", method = RequestMethod.GET)
	public String deleteQuestionFromTest(HttpServletRequest arg0 , ModelMap model ) {
		
		log.debug( arg0.getParameter("id") ) ; 
		Integer idQuestion  = Integer.parseInt( arg0.getParameter("id") ) ;  
		Question questionToDelete = testSheetService.getQuestionById(idQuestion); 
		
		TestSheet test = questionToDelete.getTest() ; 
		testSheetService.deleteQuestionFromTestSheet(test, questionToDelete) ;  
		
		Collection <Question> liste =  (Collection <Question>) testSheetService.getAllQuestionsFromTest (test) ; 
		
		model.put("question", new Question()) ; 
		model.put("questions", liste ) ; 
		model.put("testSheet", test ) ;
		
		return "test-detail";
	}

	@RequestMapping(value = "/proposition/createUpdate", method = RequestMethod.POST)
	public String addEditPropositionReponseToQuestion (@Valid PropositionReponse pr , HttpServletRequest arg0 , BindingResult result , ModelMap model) {
		
		if (result.hasErrors()) {
			return "question-detail";
		}
		
		Question question = pr.getQuestion() ; 
		if (pr.getId() == null || pr.getId() == 0) {
			pr = testSheetService.createPropositionReponse(pr) ; 
		} else {
			pr = testSheetService.updatePropositionReponse(pr) ; 
		}
		
		model.put("proposition", new PropositionReponse() ) ; 
		model.put("propositions", question.getPropositionsReponses() ) ; 
		model.put("question", question) ; 
		
		return "question-detail" ; 
	}
	
	@RequestMapping(value = "/proposition/delete", method = RequestMethod.GET)
	public String deletePropositionReponseFromQuestion (HttpServletRequest arg0 , ModelMap model ) {
		
		log.debug( arg0.getParameter("id") ) ; 
		Integer idProposition  = Integer.parseInt( arg0.getParameter("id") ) ;  
		
		PropositionReponse pr = testSheetService.getPropositionReponseById (idProposition) ; 
		Question question = pr.getQuestion() ; 
		testSheetService.deletePropositionReponseFromQuestion(pr.getQuestion(), pr) ; 
		
		model.put("proposition", new PropositionReponse() ) ; 
		model.put("propositions", question.getPropositionsReponses() ) ; 
		model.put("question", question) ; 
		
		return "question-detail" ; 
	}
}
