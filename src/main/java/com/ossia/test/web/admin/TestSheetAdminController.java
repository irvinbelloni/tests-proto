package com.ossia.test.web.admin;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.model.PropositionReponseModel;
import com.ossia.test.web.model.QuestionModel;

@Controller
@RequestMapping("/admin")
public class TestSheetAdminController {
	
	private final Log log = LogFactory.getLog(getClass()) ; 
	
	@Autowired
	private TestSheetService testSheetService ; 
	
	@ModelAttribute("testLists")
    public Collection<TestSheet> getAllTests() {
		return testSheetService.getAllTestSheets();
	}
	
	/*
	 * GESTION TESTS
	 */

	@RequestMapping(value = "/test/home", method = RequestMethod.GET)
	public String displayTestHome(ModelMap model) {
		model.put("testSheet", new TestSheet()) ; 
		return "test-home";
	}
	
	@RequestMapping(value = "/test/detail", method = RequestMethod.GET)
	public String displayTestDetailForm(@RequestParam(value = "id") String idRequestParam , ModelMap model  ) {

		Integer identifier = Integer.parseInt(idRequestParam) ;  
		TestSheet testSheet = testSheetService.getTestSheetById( identifier ) ; 
		model.put("questionForm", new QuestionModel (testSheet) ) ; 
		
		Collection<Question> liste = testSheetService.getAllQuestionsFromTest(testSheet) ;
		model.put("questions", liste ) ; 
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
		model.put("testSheet", new TestSheet()) ; 
		
		return "redirect:" + "/admin/test/test-home" ; 
	}
	
	@RequestMapping(value = "/test/delete", method = RequestMethod.GET)
	public String deleteTest(@RequestParam(value = "id") String idRequestParam , ModelMap model ) {
		
		Integer identifier = Integer.parseInt(idRequestParam) ;  
		TestSheet testSheetToDelete = testSheetService.getTestSheetById( identifier ) ; 
		testSheetService.deleteTestSheet(testSheetToDelete) ; 
		model.put("testSheet", new TestSheet()) ; 
		
		return "redirect:" + "/admin/test/test-home" ; 
	}

	@RequestMapping(value = "/test/print", method = RequestMethod.GET)
	public String printTest(@RequestParam(value = "id") String idRequestParam , ModelMap model ) {
		
//		Integer identifier = Integer.parseInt(idRequestParam) ;  
//		TestSheet testSheetToPrint = testSheetService.getTestSheetById( identifier ) ; 
		
		// TODO TDS - Complete implementation 
		
		model.put("testSheet", new TestSheet()) ; 
		return "redirect:" + "/admin/test/test-home" ;
	}
	
	/*
	 * GESTION DES QUESTIONS 
	 */
	
	@RequestMapping(value = "/question/detail", method = RequestMethod.GET)
	public String displayQuestionDetail(@RequestParam(value = "id") String idRequestParam , ModelMap model) {
		
		Integer identifier = Integer.parseInt(idRequestParam) ;  
		Question question = testSheetService.getQuestionById(identifier) ;
		
		model.put("proposition", new PropositionReponseModel(question) ) ; 
		model.put("propositions", testSheetService.getAllPropositionReponseFromQuestion(question)) ; 
		model.put("question", question) ; 
		
		return "question-detail" ; 
	}
	
	@RequestMapping(value = "/question/createUpdate", method = RequestMethod.POST)
	public String addOrEditQuestionToTest (@Valid QuestionModel questionModel , HttpServletRequest arg0 , BindingResult result, ModelMap model) {
		
		if (result.hasErrors()) {
			return "test-detail";
		}
		
		TestSheet test = testSheetService.getTestSheetById(questionModel.getTestId()) ;
		
		if (questionModel.getId().equals(null) || questionModel.getId() == 0) {
			Question questionToCreate = questionModel.convertToNewTestDomain(test) ; 
			testSheetService.createQuestion(questionToCreate) ;
		} else {
			Question questionToUpdate = testSheetService.getQuestionById(questionModel.getId()) ; 
			testSheetService.updateQuestion(questionModel.updateQuestion (questionToUpdate) ) ; 
		}
		
		model.put("questionForm", new QuestionModel( test ) ) ; 
		
		Collection<Question> liste = testSheetService.getAllQuestionsFromTest(test) ;
		model.put("questions", liste ) ; 
		model.put("testSheet", test ) ; 
		
		return "redirect:/admin/test/detail" + "?id="+test.getId() ; 
	}
	
	@RequestMapping(value = "/question/delete", method = RequestMethod.GET)
	public String deleteQuestionFromTest(@RequestParam(value = "id") String idRequestParam , ModelMap model ) {
		
		Integer idQuestion  = Integer.parseInt(idRequestParam) ;  
		Question questionToDelete = testSheetService.getQuestionById(idQuestion); 
		
		TestSheet test = questionToDelete.getTest() ; 
		testSheetService.deleteQuestionFromTestSheet(test, questionToDelete) ;  
		
		List<Question> liste = testSheetService.getAllQuestionsFromTest(test) ;
		model.put("questions", liste ) ; 
		model.put("questionForm", new QuestionModel(test)) ; 
		model.put("testSheet", test ) ;
		
		return "redirect:/admin/test/detail" + "?id="+test.getId() ;
	}
	
	/*
	 * GESTION DES PROPOSITIONS
	 */

	@RequestMapping(value = "/proposition/createUpdate", method = RequestMethod.POST)
	public String addOrEditPropositionReponseToQuestion (@Valid PropositionReponseModel pr , HttpServletRequest arg0 , BindingResult result , ModelMap model) {
		
		if (result.hasErrors()) {
			return "question-detail";
		}
		
		Question question = testSheetService.getQuestionById(pr.getQuestionId()) ; 
		
		if (pr.getId() == null || pr.getId() == 0) {
			PropositionReponse prToCreate = pr.convertToNewPropositionReponseDomain(question) ; 
			testSheetService.createPropositionReponse(prToCreate) ; 
		} else {
			PropositionReponse prToUpdate = testSheetService.getPropositionReponseById(pr.getId()) ; 
			prToUpdate = pr.updatePropositionReponseModel(prToUpdate) ; 
			testSheetService.updatePropositionReponse(prToUpdate) ; 
		}
		
		model.put("proposition", new PropositionReponseModel(question) ) ; 
		model.put("propositions", testSheetService.getAllPropositionReponseFromQuestion(question) ) ; 
		model.put("question", question) ; 
		
		return "redirect:/admin/question/detail" + "?id="+question.getId() ; 
	}
	
	@RequestMapping(value = "/proposition/delete", method = RequestMethod.GET)
	public String deletePropositionReponseFromQuestion ( @RequestParam(value = "id", required = false) String idproposition , HttpServletRequest arg0 , ModelMap model ) {
		
		log.debug( arg0.getParameter("id") ) ; 
		Integer idProposition  = Integer.parseInt(idproposition) ;  
		
		PropositionReponse pr = testSheetService.getPropositionReponseById (idProposition) ; 
		Question question = pr.getQuestion() ; 
		testSheetService.deletePropositionReponseFromQuestion(pr.getQuestion(), pr) ; 
		
		model.put("proposition", new PropositionReponseModel(question) ) ; 
		model.put("propositions", testSheetService.getAllPropositionReponseFromQuestion(question) ) ; 
		model.put("question", question) ; 
		
		return "redirect:/admin/question/detail" + "?id="+question.getId() ; 
	}
}
