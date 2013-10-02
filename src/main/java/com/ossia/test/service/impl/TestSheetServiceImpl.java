package com.ossia.test.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.HistoAction;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestHisto;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.domain.TestStatus;
import com.ossia.test.repository.PropositionReponseRepository;
import com.ossia.test.repository.QuestionRepository;
import com.ossia.test.repository.TestSheetRepository;
import com.ossia.test.service.TestSheetService;
import com.ossia.test.web.sort.SortingInfo;

@Service 
public class TestSheetServiceImpl implements TestSheetService {

	@Autowired
	private TestSheetRepository testSheetRepository;

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private PropositionReponseRepository propositionReponseRepository ; 

	@Transactional
	public TestSheet createTestSheet(TestSheet testSheetACreer, Profil admin) {
		testSheetACreer.setStatus(TestStatus.DRAFT);
		
		// Tracing the creation
		TestHisto histo = new TestHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.ADD.getCode());
		histo.setTestSheet(testSheetACreer);
		
		testSheetACreer.getHistorique().add(histo);
		
		Integer id = testSheetRepository.create(testSheetACreer);		
		return getTestSheetById(id) ; 
	}

	@Transactional(readOnly = true)
	public TestSheet getTestSheetById(Integer idTestSheet) {
		return testSheetRepository.getById(idTestSheet);
	}

	@Transactional(readOnly = true)
	public Collection<TestSheet> getTestSheetsByType(String type) {
		return testSheetRepository.getTestSheetsByType(type);
	}
	
	@Transactional(readOnly = true)
	public List<TestSheet> getAllTestSheets() {
		return testSheetRepository.getAll() ;
	}
	
	@Override
	public List<TestSheet> getSortedTestSheets(SortingInfo sortingInfo) {
		return testSheetRepository.getSortedTestSheets(sortingInfo.getSortingField(), sortingInfo.getSortingDirection());
	}
	
	@Transactional
	public TestSheet updateTestSheet(TestSheet testSheet, Profil admin) {
		// Tracing the modification
		TestHisto histo = new TestHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.EDIT.getCode());
		histo.setTestSheet(testSheet);
		
		testSheet.getHistorique().add(histo);
		
		testSheetRepository.update(testSheet) ; 
		return testSheetRepository.getById(testSheet.getId()) ; 
	}

	@Transactional
	public void deleteTestSheet(TestSheet testSheetToDelete) {
		testSheetRepository.delete(testSheetToDelete);
	}	
	
	public List<String> getAllTestTypes() {
		SortingInfo sortingInfo = new SortingInfo();
		sortingInfo.setSortingDirection(SortingInfo.ASC);
		sortingInfo.setSortingField(SortingInfo.SORT_TYPE);
		List<TestSheet> tests = getSortedTestSheets(sortingInfo);
		List<String> testTypes = new ArrayList<String>();
		for (TestSheet test: tests) {
			if (!testTypes.contains(test.getType())) {
				testTypes.add(test.getType());
			}
		}
		return testTypes;
	}

	@Override @Transactional
	public void validateTestSheet(TestSheet testSheet, Profil admin) {
		if (!testSheet.isValidable() || !testSheet.isDraft()) { // Test non validable or not in draft state
			return;
		}
		
		testSheet.setStatus(TestStatus.VALIDATED);
		
		// Tracing the validation
		TestHisto histo = new TestHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.VALIDATE_TEST.getCode());
		histo.setTestSheet(testSheet);
		
		testSheet.getHistorique().add(histo);
		
		testSheetRepository.update(testSheet);		
	}
	
	@Override @Transactional
	public void archiveTestSheet(TestSheet testSheet, Profil admin) {
		if (!testSheet.isValidated()) { // Test un-archivable
			return;
		}
		
		testSheet.setStatus(TestStatus.ARCHIVED);
		
		// Tracing the validation
		TestHisto histo = new TestHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.ARCHIVE_TEST.getCode());
		histo.setTestSheet(testSheet);
		
		testSheet.getHistorique().add(histo);
		
		testSheetRepository.update(testSheet);		
	}
	
	@Override @Transactional
	public TestSheet duplicateTestSheet(TestSheet testSheet, Profil admin) {
		if (testSheet.isDraft()) {
			return testSheet;
		}
		
		// Tracing the duplication
		TestHisto histo = new TestHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.DUPLICATE_TEST.getCode());
		histo.setTestSheet(testSheet);
		testSheet.getHistorique().add(histo);		
		testSheetRepository.update(testSheet);	
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		TestSheet newTestSheet = new TestSheet();
		newTestSheet.setDuree(testSheet.getDuree());
		newTestSheet.setIntitule(testSheet.getIntitule() + " - " + simpleDateFormat.format(new Date()));
		newTestSheet.setType(testSheet.getType());
		newTestSheet.setStatus(TestStatus.DRAFT);
		
		for(Question question : testSheet.getQuestions()) {
			Question newQuestion = new Question();
			newQuestion.setContenu(question.getContenu());
			newQuestion.setIntitule(question.getIntitule());
			newQuestion.setNiveau(question.getNiveau());
			newQuestion.setTest(newTestSheet);
			
			for (PropositionReponse proposition : question.getPropositionsReponses()) {
				PropositionReponse newProposition = new PropositionReponse();
				newProposition.setValeur(proposition.getValeur());
				newProposition.setPropositionCorrecte(proposition.isPropositionCorrecte());
				newProposition.setQuestion(newQuestion);
				newQuestion.getPropositionsReponses().add(newProposition);
			}			
			newTestSheet.getQuestions().add(newQuestion);
		}
		
		// Tracing the duplication
		histo = new TestHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.ADD_DUPLICATION.getCode());
		histo.setTestSheet(newTestSheet);		
		newTestSheet.getHistorique().add(histo);
		
		Integer id = testSheetRepository.create(newTestSheet);		
		return getTestSheetById(id);
		
	}

	@Transactional
	public Question addQuestionToTest (TestSheet test, Question question, Profil admin) {
		
		question.setContenu(encodeTextareaContent(question.getContenu()));
		
		question.setTest(test);
		test.getQuestions().add(question);
		
		// Tracing the creation
		TestHisto histo = new TestHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.ADD_QUESTION.getCode());
		histo.setTestSheet(test);				
		test.getHistorique().add(histo);
		
		testSheetRepository.update(test);		
		return question;
	}

	@Transactional(readOnly = true)
	public Question getQuestionById(Integer idQuestion) {
		return questionRepository.getById(idQuestion);
	}

	@Transactional(readOnly = true)
	public List<Question> getAllQuestionsFromTest(TestSheet test) {
		return questionRepository.getAllQuestionsByTestId(test.getId()) ; 
	}
	
	@Transactional 
	public Question updateQuestion(TestSheet test, Question question, Profil admin) {		
		question.setContenu(encodeTextareaContent(question.getContenu()));
		questionRepository.update(question);
				
		// Tracing the update
		TestHisto histo = new TestHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.EDIT_QUESTION.getCode());
		histo.setTestSheet(test);				
		test.getHistorique().add(histo);
		
		testSheetRepository.update(test);
		
		return questionRepository.getById(question.getId()) ; 
	}
	
	@Transactional
	public void deleteQuestionFromTestSheet(TestSheet test, Question aSupprimer, Profil admin) {
		questionRepository.delete(aSupprimer);
		
		// Tracing the deletion
		TestHisto histo = new TestHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.DELETE_QUESTION.getCode());
		histo.setTestSheet(test);				
		test.getHistorique().add(histo);		
		testSheetRepository.update(test);	
	}
	
	@Transactional
	public PropositionReponse createPropositionReponse (PropositionReponse pr, Profil admin){
		pr.setValeur(encodeTextareaContent(pr.getValeur()));				
		Integer id = propositionReponseRepository.create(pr) ;
		return propositionReponseRepository.getById(id) ; 
	}
	
	@Transactional(readOnly = true)
	public PropositionReponse getPropositionReponseById(Integer idProposition){
		return propositionReponseRepository.getById(idProposition) ;
	}
	
	@Transactional
	public List <PropositionReponse> getAllPropositionReponseFromQuestion (Question question) {
		return propositionReponseRepository.getAllPropositionReponseByQuestionId(question.getId()) ; 
	} 
	
	@Transactional
	public PropositionReponse updatePropositionReponse (PropositionReponse pr, Profil admin) {
		pr.setValeur(encodeTextareaContent(pr.getValeur()));
		propositionReponseRepository.update(pr) ; 
		return propositionReponseRepository.getById(pr.getId()) ;
	}
	
	@Transactional
	public Question deletePropositionReponseFromQuestion (Question question , PropositionReponse pr) {
		
		for(PropositionReponse propositionReponse : question.getPropositionsReponses()) {
			if (propositionReponse.getId() == pr.getId()) {
				question.getPropositionsReponses().remove(propositionReponse);
				break;
			}
		}
		questionRepository.update(question);
		propositionReponseRepository.delete(pr);
		return question;
	}
	
	private String encodeTextareaContent(String content) {
		String encodedContent = content.replace("\"", "[DB]");
		encodedContent = encodedContent.replace("    ", "[TAB]");
		encodedContent = encodedContent.replace("\t", "[TAB]");
		encodedContent = encodedContent.replace("\r\n", "[NL]");
		encodedContent = encodedContent.replace("\n\r", "[NL]");
		encodedContent = encodedContent.replace("\r", "[NL]");
		encodedContent = encodedContent.replace("\n", "[NL]");		
		return encodedContent;
	}
}
