package com.ossia.test.repository;

import java.util.List;

import com.ossia.test.domain.Niveau;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;

public interface QuestionRepository extends AbstractRepository<Question, Integer> {

	List<Question> getAllQuestionsByTestId(Integer id);
	
	List<Question> getQuestionsByTestAndNiveau(TestSheet test , Niveau level) ;
}
