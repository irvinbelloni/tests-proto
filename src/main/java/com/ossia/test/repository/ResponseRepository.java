package com.ossia.test.repository;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.Response;

public interface ResponseRepository extends AbstractRepository<Response, Integer> {

	Response getResponseByEvaluationAndQuestion(Evaluation evalParamEntree,
			Question question);
}
