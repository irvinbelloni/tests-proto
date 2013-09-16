package com.ossia.test.repository;

import java.util.List;

import com.ossia.test.domain.Question;

public interface QuestionRepository extends AbstractRepository<Question, Integer> {

	List<Question> getAllQuestionsByTestId(Integer id);
}
