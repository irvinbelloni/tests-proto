package com.ossia.test.web.admin;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ossia.test.domain.Profil;
import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.form.CreateUpdatePropositionReponseForm;
import com.ossia.test.web.form.CreateUpdateQuestionForm;
import com.ossia.test.web.sort.SortingInfo;

@Controller
@RequestMapping("/admin")
public class TestSheetAdminController extends AbstractAdminController {
	
	// TODO empecher la suppression d'un test si celui ci est en cours de pasage
	// TODO empecher l'assignation d'un test si celui comporte un equestion sans au moins 2 propositions de réponses (dont au moins une correcte)
	private final Log log = LogFactory.getLog(getClass()) ; 
	
	private final static String SESSION_TEST_LIST_SORT = "test.list.sort";
	
	@Autowired
	private TestSheetService testSheetService ; 

	/*
	 * GESTION TESTS
	 */
	@RequestMapping(value = "/test/home", method = RequestMethod.GET)
	public String displayTestHome(@RequestParam(value = "sort", required = false) String sortingField, @RequestParam(value = "direction", required = false) String sortingDirection, ModelMap model, HttpServletRequest request) {
		
		// Sorting information
		SortingInfo sortingInfo = completeTestSortingInfo((SortingInfo)request.getSession().getAttribute(SESSION_TEST_LIST_SORT), sortingField, sortingDirection);	
		request.getSession().setAttribute(SESSION_TEST_LIST_SORT, sortingInfo);
		
		setLastActionInModel(model, request);
				
		model.put("tests", getTestList(request));
		model.put("sortingInfo", sortingInfo);
		
		model.put("testSheetForm", new TestSheet()) ; 
		model.put("selectedTab", TAB_TEST);
		return "test-home";
	}
	
	@RequestMapping(value = "/test/detail", method = RequestMethod.GET)
	public String displayTestDetailForm(@RequestParam(value = "id") String idRequestParam, HttpServletRequest request, ModelMap model  ) {

		Integer identifier = Integer.parseInt(idRequestParam) ;  
		TestSheet testSheet = testSheetService.getTestSheetById( identifier ) ; 
		model.put("questionForm", new CreateUpdateQuestionForm (testSheet) ) ; 
		
		setLastActionInModel(model, request);
		
		model.put("testSheet", testSheet ) ; 
		model.put("selectedTab", TAB_TEST);
		return "test-detail";
	}
	
	@RequestMapping(value = "/test/createUpdate", method = RequestMethod.POST)
	public String addOrEditTest (@RequestParam(value = "origin") String origin, @Valid @ModelAttribute(value = "testSheetForm") TestSheet testSheetForm, BindingResult result, HttpServletRequest request, ModelMap model) {
		
		if (result.hasErrors()) {			
			model.put("selectedTab", TAB_TEST);			
			if (origin.equals("detail")) {
				model.put("displayEditForm", true);
				return "test-detail";
			}			
			model.put("tests", getTestList(request));
			model.put("sortingInfo", (SortingInfo)request.getSession().getAttribute(SESSION_TEST_LIST_SORT));
			return "test-home";
		}
		model.remove("selectedTab");
		
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String messageCodeForLastAction = null;
		
		if (testSheetForm.getId() == null || testSheetForm.getId() == 0) {
			testSheetForm = testSheetService.createTestSheet(testSheetForm, admin) ;
			messageCodeForLastAction = "text.notify.add.test";
		} else {
			try {
				testSheetForm = testSheetService.updateTestSheet(testSheetForm, admin) ; 
				messageCodeForLastAction = "text.notify.edit.test";
			} catch (NotFoundException nfe) { // Trying to edit a test that does not exist
				return "redirect:/errors/404";
			} 
		}
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage(messageCodeForLastAction, testSheetForm.getIntitule()));
				
		String redirectUrl = "/admin/test/" + origin;
		if (origin.equals("detail")) {
			redirectUrl += "?id=" + testSheetForm.getId();
		}
		return "redirect:" + redirectUrl;
	}
	
	@RequestMapping(value = "/test/delete", method = RequestMethod.GET)
	public String deleteTest(@RequestParam(value = "id") Integer testId, HttpServletRequest request, ModelMap model ) {
		
		TestSheet testSheetToDelete = testSheetService.getTestSheetById( testId ) ; 
		testSheetService.deleteTestSheet(testSheetToDelete) ; 
		model.put("testSheet", new TestSheet()) ; 
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage("text.notify.delete.test", testSheetToDelete.getIntitule()));
		
		
		return "redirect:" + "/admin/test/home" ; 
	}

	/*
	 * GESTION DES QUESTIONS 
	 */
	
	@RequestMapping(value = "/question/detail", method = RequestMethod.GET)
	public String displayQuestionDetail(@RequestParam(value = "id") String idRequestParam , HttpServletRequest request, ModelMap model) {
		// TODO ajouter une popup d'aide à la saisie des questions et des réponses pour le formattage du code
		Integer identifier = Integer.parseInt(idRequestParam) ;  
		Question question = testSheetService.getQuestionById(identifier) ;
		
		model.put("propositionForm", new CreateUpdatePropositionReponseForm(question) ) ; 
		model.put("propositions", question.getPropositionsReponses()) ; 
		model.put("question", question) ;
		
		model.put("selectedTab", TAB_TEST);
		
		model.put("questionForm", new CreateUpdateQuestionForm (question.getTest()) ) ; 
		
		setLastActionInModel(model, request);
		
		return "question-detail" ; 
	}
	
	@RequestMapping(value = "/question/createUpdate", method = RequestMethod.POST)
	public String addOrEditQuestionToTest (@RequestParam(value = "origin") String origin, @Valid @ModelAttribute(value = "questionForm") CreateUpdateQuestionForm questionForm, BindingResult result, ModelMap model, HttpServletRequest request) {
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (result.hasErrors()) {
			model.put("selectedTab", TAB_TEST);
			if (origin.equals("test")) {
				model.put("testSheet",  testSheetService.getTestSheetById(questionForm.getTestId()));
			} else {
				Question question = testSheetService.getQuestionById(questionForm.getId());
				model.put("question", question) ;
				model.put("propositionForm", new CreateUpdatePropositionReponseForm(question)) ; 
				model.put("propositions", testSheetService.getAllPropositionReponseFromQuestion(question)) ;
				model.put("displayEditQuestionForm", true);
			}
			model.put("displayAddQuestionForm", true);
			return origin + "-detail";
		}
		
		TestSheet test = testSheetService.getTestSheetById(questionForm.getTestId()) ;
		
		String messageCodeForLastAction = null;
		Question question = null;
		if (questionForm.getId() == null || questionForm.getId() == 0) {
			question = testSheetService.addQuestionToTest(test, questionForm.convertToNewTestDomain(test), admin);
			messageCodeForLastAction = "text.notify.add.question";
		} else {
			Question existingQuestion = testSheetService.getQuestionById(questionForm.getId()) ; 
			question = testSheetService.updateQuestion(test, questionForm.updateQuestion (existingQuestion), admin) ; 
			messageCodeForLastAction = "text.notify.edit.question";
		}
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage(messageCodeForLastAction, question.getIntitule(), question.getTest().getIntitule()));
		
		model.put("questionForm", new CreateUpdateQuestionForm( test ) ) ; 
		
		Collection<Question> liste = testSheetService.getAllQuestionsFromTest(test) ;
		model.put("questions", liste ) ; 
		model.put("testSheet", test ) ;
		
		if (origin.equals("test")) {
			return "redirect:/admin/test/detail" + "?id="+test.getId() ; 
		} else {
			return "redirect:/admin/question/detail" + "?id="+question.getId() ; 
		}		
	}
	
	@RequestMapping(value = "/question/delete", method = RequestMethod.GET)
	public String deleteQuestionFromTest(@RequestParam(value = "question") Integer questionId, @RequestParam(value = "test") Integer testId, HttpServletRequest request, ModelMap model ) {
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Question questionToDelete = testSheetService.getQuestionById(questionId); 
		
		TestSheet test = questionToDelete.getTest(); 
		
		if (test.getId() != testId) { // Trying to delete a question that does not belong to the given test
			return "redirect:/admin/test/detail" + "?id="+testId;			
		}
		
		testSheetService.deleteQuestionFromTestSheet(test, questionToDelete, admin) ;  
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage("text.notify.delete.question", questionToDelete.getIntitule(), test.getIntitule()));
		return "redirect:/admin/test/detail" + "?id="+test.getId() ;
	}
	
	/*
	 * GESTION DES PROPOSITIONS
	 */

	@RequestMapping(value = "/proposition/createUpdate", method = RequestMethod.POST)
	public String addOrEditPropositionReponseToQuestion (@Valid @ModelAttribute(value = "propositionForm") CreateUpdatePropositionReponseForm propform,  BindingResult result, HttpServletRequest request, ModelMap model) {
		
		if (result.hasErrors()) {
			Question question = testSheetService.getQuestionById(propform.getQuestionId());
			model.put("propositions", question.getPropositionsReponses()) ; 
			model.put("question", question) ;
			model.put("selectedTab", TAB_TEST);
			model.put("questionForm", new CreateUpdateQuestionForm (question.getTest()));
			model.put("displayPropositionForm",  true);
			return "question-detail";
		}
		
		Question question = testSheetService.getQuestionById(propform.getQuestionId()) ; 
		
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String messageCodeForLastAction = null;
		
		if (propform.getId() == null || propform.getId() == 0) {
			PropositionReponse prToCreate = propform.convertToNewPropositionReponseDomain(question) ; 
			testSheetService.createPropositionReponse(prToCreate, admin) ; 
			messageCodeForLastAction = "text.notify.add.proposition";
		} else {
			PropositionReponse prToUpdate = testSheetService.getPropositionReponseById(propform.getId()) ; 
			prToUpdate = propform.updatePropositionReponseModel(prToUpdate) ; 
			testSheetService.updatePropositionReponse(prToUpdate, admin) ; 
			messageCodeForLastAction = "text.notify.edit.proposition";
		}
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage(messageCodeForLastAction, question.getIntitule()));
		
		
		model.put("proposition", new CreateUpdatePropositionReponseForm(question) ) ; 
		model.put("propositions", testSheetService.getAllPropositionReponseFromQuestion(question) ) ; 
		model.put("question", question) ;
		
		return "redirect:/admin/question/detail" + "?id="+question.getId() ; 
	}
	
	@RequestMapping(value = "/proposition/delete", method = RequestMethod.GET)
	public String deletePropositionReponseFromQuestion (@RequestParam(value = "proposition") Integer propositionId, @RequestParam(value = "question") Integer questionId, HttpServletRequest request, ModelMap model) {
				
		PropositionReponse pr = testSheetService.getPropositionReponseById (propositionId) ; 
		Question question = pr.getQuestion();
		if (question.getId() != questionId) { // Trying to delete a proposition that does not belong to the given question
			return "redirect:/admin/question/detail" + "?id=" + questionId; 			
		}
		
		question = testSheetService.deletePropositionReponseFromQuestion(pr.getQuestion(), pr) ; 
		
		model.put("proposition", new CreateUpdatePropositionReponseForm(question) ) ; 
		model.put("propositions", question.getPropositionsReponses()); 
		model.put("question", question); 
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage("text.notify.delete.proposition", question.getIntitule()));		
		return "redirect:/admin/question/detail" + "?id=" + question.getId() ; 
	}
	
	private SortingInfo completeTestSortingInfo (SortingInfo sortingInfo, String sortingField, String sortingDirection) {
		if (sortingInfo == null) {
			sortingInfo = new SortingInfo();
			sortingInfo.setSortingDirection(SortingInfo.ASC);
			sortingInfo.setSortingField(SortingInfo.SORT_INTITULE);
		}
		if (sortingField != null) {
			if (SortingInfo.SORT_INTITULE.equals(sortingField) || SortingInfo.SORT_DUREE.equals(sortingField) || SortingInfo.SORT_TYPE.equals(sortingField)) {
				sortingInfo.setSortingField(sortingField);
			}
		}
		if (sortingDirection != null) {
			if (SortingInfo.ASC.equals(sortingDirection) || SortingInfo.DESC.equals(sortingDirection)) {
				sortingInfo.setSortingDirection(sortingDirection);
			}
		}		
		return sortingInfo;
	}
	
    private Collection<TestSheet> getTestList(HttpServletRequest request) {
		SortingInfo sortingInfo = (SortingInfo)request.getSession().getAttribute(SESSION_TEST_LIST_SORT);
		if (sortingInfo == null) {
			sortingInfo = new SortingInfo();
			sortingInfo.setSortingField(SortingInfo.SORT_INTITULE);
			sortingInfo.setSortingDirection(SortingInfo.ASC);
		}
		return testSheetService.getSortedTestSheets(sortingInfo);
	}
	
}
