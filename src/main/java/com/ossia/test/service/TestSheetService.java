package com.ossia.test.service;

import java.util.Collection;
import java.util.List;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;

public interface TestSheetService {

	/*
	 * TESTSHEET
	 */	
	TestSheet createTestSheet(TestSheet testSheetACreer);

	TestSheet getTestSheetById(Integer idTestSheet);

	Collection<TestSheet> getTestSheetsByType(String type);

	List<TestSheet> getAllTestSheets();
	
	TestSheet updateTestSheet(TestSheet testSheet);
	
	void deleteTestSheet(TestSheet testSheetToDelete);

	/*
	 * QUESTION
	 */	
	Question createQuestion (Question questionACreer) ; 
	
	Question getQuestionById (Integer idQuestion) ; 

	List<Question> getAllQuestionsFromTest(TestSheet test);

	Question updateQuestion(Question question);
	
	void deleteQuestionFromTestSheet (TestSheet test , Question aSupprimer) ;

	/*
	 * PROPOSITION_REPONSE
	 */
	PropositionReponse createPropositionReponse (PropositionReponse pr) ; 
	
	PropositionReponse getPropositionReponseById(Integer idProposition);
	
	List <PropositionReponse> getAllPropositionReponseFromQuestion (Question question) ; 
	
	PropositionReponse updatePropositionReponse (PropositionReponse pr) ; 
	
	void deletePropositionReponseFromQuestion (Question question , PropositionReponse pr ) ; 
	
}
