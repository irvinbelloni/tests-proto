package com.ossia.test.repository;

import java.util.Collection;

import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;

public interface QuestionRepository {

	Integer createQuestion (Question questionACreer) ; 
	
	Collection<Question> getQuestionsByTest (TestSheet test) ; 
	
	void deleteQuestionFromTestSheet (TestSheet test , Question aSupprimer) ;
}
