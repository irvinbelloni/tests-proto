package com.ossia.test.repository.impl;

import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Question;
import com.ossia.test.repository.QuestionRepository;

@Repository
public class QuestionRepositoryImpl extends AbstractRepositoryImpl<Question, Integer> implements QuestionRepository {

}
