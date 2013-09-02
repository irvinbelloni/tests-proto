package com.ossia.test.repository;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Response;

public interface ResponseRepository {

	Integer createResponse (Response reponse) ; 
	
	Response getResponseById (Integer idResponse) ; 
	
	void deleteResponse (Evaluation evaluationAModifier , Response response ) ; 
}
