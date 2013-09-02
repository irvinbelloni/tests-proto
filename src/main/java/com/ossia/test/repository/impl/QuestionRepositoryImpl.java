package com.ossia.test.repository.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.QuestionRepository;

@Repository
public class QuestionRepositoryImpl implements QuestionRepository {

	@Override
	public Integer createQuestion(Question questionACreer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Question> getQuestionsByTest(TestSheet test) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteQuestionFromTestSheet(TestSheet test, Question aSupprimer) {
		// TODO Auto-generated method stub

	}

}
