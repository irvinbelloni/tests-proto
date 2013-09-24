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
	public String deleteTest(@RequestParam(value = "id") String idRequestParam, HttpServletRequest request, ModelMap model ) {
		
		Integer identifier = Integer.parseInt(idRequestParam) ;  
		TestSheet testSheetToDelete = testSheetService.getTestSheetById( identifier ) ; 
		testSheetService.deleteTestSheet(testSheetToDelete) ; 
		model.put("testSheet", new TestSheet()) ; 
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage("text.notify.delete.test", testSheetToDelete.getIntitule()));
		
		
		return "redirect:" + "/admin/test/home" ; 
	}

	@RequestMapping(value = "/test/print", method = RequestMethod.GET)
	public String printTest(@RequestParam(value = "id") String idRequestParam , ModelMap model ) {
		
//		Integer identifier = Integer.parseInt(idRequestParam) ;  
//		TestSheet testSheetToPrint = testSheetService.getTestSheetById( identifier ) ; 
		
		// TODO TDS - Complete implementation 
		
		model.put("testSheet", new TestSheet()) ; 
		return "redirect:" + "/admin/test/home" ;
	}
	
	/*
	 * GESTION DES QUESTIONS 
	 */
	
	@RequestMapping(value = "/question/detail", method = RequestMethod.GET)
	public String displayQuestionDetail(@RequestParam(value = "id") String idRequestParam , ModelMap model) {
		
		Integer identifier = Integer.parseInt(idRequestParam) ;  
		Question question = testSheetService.getQuestionById(identifier) ;
		
		model.put("proposition", new CreateUpdatePropositionReponseForm(question) ) ; 
		model.put("propositions", testSheetService.getAllPropositionReponseFromQuestion(question)) ; 
		model.put("question", question) ; 
		
		return "question-detail" ; 
	}
	
	@RequestMapping(value = "/question/createUpdate", method = RequestMethod.POST)
	public String addOrEditQuestionToTest (@RequestParam(value = "origin") String origin, @Valid @ModelAttribute(value = "questionForm") CreateUpdateQuestionForm questionForm, BindingResult result, ModelMap model, HttpServletRequest request) {
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (result.hasErrors()) {
			model.put("selectedTab", TAB_TEST);
			if (origin.equals("test")) {
				model.put("testSheet",  testSheetService.getTestSheetById(questionForm.getTestId()));
			}
			model.put("displayAddQuestionForm", true);
			return "test-detail";
		}
		
		TestSheet test = testSheetService.getTestSheetById(questionForm.getTestId()) ;
		
		String messageCodeForLastAction = null;
		Question question = null;
		if (questionForm.getId() == null || questionForm.getId() == 0) {
			question = testSheetService.addQuestionToTest(test, questionForm.convertToNewTestDomain(test), admin);
			messageCodeForLastAction = "text.notify.add.question";
		} else {
			Question existingQuestion = testSheetService.getQuestionById(questionForm.getId()) ; 
			question = testSheetService.updateQuestion(questionForm.updateQuestion (existingQuestion) ) ; 
			messageCodeForLastAction = "text.notify.edit.question";
		}
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage(messageCodeForLastAction, question.getIntitule(), question.getTest().getIntitule()));
		
		model.put("questionForm", new CreateUpdateQuestionForm( test ) ) ; 
		
		Collection<Question> liste = testSheetService.getAllQuestionsFromTest(test) ;
		model.put("questions", liste ) ; 
		model.put("testSheet", test ) ; 
		
		return "redirect:/admin/test/detail" + "?id="+test.getId() ; 
	}
	
	@RequestMapping(value = "/question/delete", method = RequestMethod.GET)
	public String deleteQuestionFromTest(@RequestParam(value = "id") Integer questionId, HttpServletRequest request, ModelMap model ) {
		Profil admin = (Profil)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Question questionToDelete = testSheetService.getQuestionById(questionId); 
		
		TestSheet test = questionToDelete.getTest() ; 
		testSheetService.deleteQuestionFromTestSheet(test, questionToDelete, admin) ;  
		
		request.getSession().setAttribute(SESSION_LAST_ACTION, buildNotifyMessage("text.notify.delete.question", questionToDelete.getIntitule(), test.getIntitule()));
		return "redirect:/admin/test/detail" + "?id="+test.getId() ;
	}
	
	/*
	 * GESTION DES PROPOSITIONS
	 */

	@RequestMapping(value = "/proposition/createUpdate", method = RequestMethod.POST)
	public String addOrEditPropositionReponseToQuestion (@Valid CreateUpdatePropositionReponseForm pr , HttpServletRequest arg0 , BindingResult result , ModelMap model) {
		
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
		
		model.put("proposition", new CreateUpdatePropositionReponseForm(question) ) ; 
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
		
		model.put("proposition", new CreateUpdatePropositionReponseForm(question) ) ; 
		model.put("propositions", testSheetService.getAllPropositionReponseFromQuestion(question) ) ; 
		model.put("question", question) ; 
		
		return "redirect:/admin/question/detail" + "?id="+question.getId() ; 
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
