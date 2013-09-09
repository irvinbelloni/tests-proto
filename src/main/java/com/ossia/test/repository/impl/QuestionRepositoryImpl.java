package com.ossia.test.repository.impl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Question;
import com.ossia.test.repository.QuestionRepository;

@Repository
public class QuestionRepositoryImpl extends AbstractRepositoryImpl<Question, Integer> implements QuestionRepository {

}
