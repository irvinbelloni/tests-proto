package com.ossia.test.repository.impl;

import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.Response;
import com.ossia.test.repository.ResponseRepository;

@Repository
public class ResponseRepositoryImpl extends AbstractRepositoryImpl<Response, Integer> implements ResponseRepository {

	@Override
	public Response getResponseByEvaluationAndQuestion(
			Evaluation evalParamEntree, Question question) {
		// TODO Auto-generated method stub
		return null;
	}
}
