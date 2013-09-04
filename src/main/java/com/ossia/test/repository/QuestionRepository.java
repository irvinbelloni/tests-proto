package com.ossia.test.repository;

import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;

public interface QuestionRepository {

	Integer createQuestion (Question questionACreer) ; 
	
	Question getQuestionById (Integer idQuestion) ; 
	
	void deleteQuestionFromTestSheet (TestSheet test , Question aSupprimer) ;
}
