package com.ossia.test.service.impl;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.PropositionReponseRepository;
import com.ossia.test.repository.QuestionRepository;
import com.ossia.test.repository.TestSheetRepository;
import com.ossia.test.service.TestSheetService;

@Service 
public class TestSheetServiceImpl implements TestSheetService {

	@Autowired
	private TestSheetRepository testSheetRepository;

	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private PropositionReponseRepository propositionReponseRepository ; 

	@Transactional
	public TestSheet createTestSheet(TestSheet testSheetACreer) {
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
	
	@Transactional
	public TestSheet updateTestSheet(TestSheet testSheet) {
		testSheetRepository.update(testSheet) ; 
		return testSheetRepository.getById(testSheet.getId()) ; 
	}

	@Transactional
	public void deleteTestSheet(TestSheet testSheetToDelete) {
		testSheetRepository.delete(testSheetToDelete);
	}

	@Transactional
	public Question createQuestion(Question questionACreer) {
		Integer id = questionRepository.create(questionACreer);
		return questionRepository.getById(id);
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
	public Question updateQuestion(Question question) {
		questionRepository.update(question);
		return questionRepository.getById(question.getId()) ; 
	}
	
	@Transactional
	public void deleteQuestionFromTestSheet(TestSheet test, Question aSupprimer) {
		questionRepository.delete(aSupprimer);
	}
	
	@Transactional
	public PropositionReponse createPropositionReponse (PropositionReponse pr){
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
	public PropositionReponse updatePropositionReponse (PropositionReponse pr) {
		propositionReponseRepository.update(pr) ; 
		return propositionReponseRepository.getById(pr.getId()) ;
	}
	
	@Transactional
	public void deletePropositionReponseFromQuestion (Question question , PropositionReponse pr ) {
		propositionReponseRepository.delete(pr);
	}
}
