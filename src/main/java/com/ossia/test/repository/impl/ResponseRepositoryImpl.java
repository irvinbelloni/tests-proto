package com.ossia.test.repository.impl;

import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Response;
import com.ossia.test.repository.ResponseRepository;

@Repository
public class ResponseRepositoryImpl implements ResponseRepository {

	@Override
	public Integer createResponse(Response reponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response getResponseById(Integer idResponse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteResponse(Evaluation evaluationAModifier, Response response) {
		// TODO Auto-generated method stub

	}

}
