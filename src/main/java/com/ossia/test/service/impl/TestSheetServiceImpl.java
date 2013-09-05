package com.ossia.test.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.QuestionRepository;
import com.ossia.test.repository.TestSheetRepository;
import com.ossia.test.service.TestSheetService;

@Service
public class TestSheetServiceImpl implements TestSheetService {

	@Autowired
	private TestSheetRepository testSheetRepository;

	@Autowired
	private QuestionRepository questionRepository;

	@Override
	public TestSheet createTestSheet(TestSheet testSheetACreer) {
		Integer id = testSheetRepository.createTestSheet(testSheetACreer); 
		return getTestSheetById(id) ; 
	}

	@Override
	public TestSheet getTestSheetById(Integer idTestSheet) {
		return testSheetRepository.getTestSheetById(idTestSheet);
	}

	@Override
	public Collection<TestSheet> getTestSheetsByType(String type) {
		return testSheetRepository.getTestSheetsByType(type);
	}

	@Override
	public void deleteTestSheet(TestSheet testSheetToDelete) {
		testSheetRepository.deleteTestSheet(testSheetToDelete);
	}

	@Override
	public Question createQuestion(Question questionACreer) {
		Integer id = questionRepository.createQuestion(questionACreer);
		return getQuestionById(id) ; 
	}

	@Override
	public Question getQuestionById(Integer idQuestion) {
		return questionRepository.getQuestionById(idQuestion);
	}

	@Override
	public void deleteQuestionFromTestSheet(TestSheet test, Question aSupprimer) {
		questionRepository.deleteQuestionFromTestSheet(test, aSupprimer);
	}

}
