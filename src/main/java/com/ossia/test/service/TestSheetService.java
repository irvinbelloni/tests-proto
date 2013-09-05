package com.ossia.test.service;

import java.util.Collection;

import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;

public interface TestSheetService {

	/*
	 * TESTSHEET
	 */	
	TestSheet createTestSheet(TestSheet testSheetACreer);

	TestSheet getTestSheetById(Integer idTestSheet);

	Collection<TestSheet> getTestSheetsByType(String type);

	void deleteTestSheet(TestSheet testSheetToDelete);

	/*
	 * QUESTION
	 */	
	Question createQuestion (Question questionACreer) ; 
	
	Question getQuestionById (Integer idQuestion) ; 
	
	void deleteQuestionFromTestSheet (TestSheet test , Question aSupprimer) ;
	
	/*
	 * MISC
	 */
	Collection<TestSheet> getAllTestSheets();
}
