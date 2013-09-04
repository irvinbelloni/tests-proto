package com.ossia.test.service;

import java.util.Collection;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.Response;
import com.ossia.test.domain.TestSheet;

public interface EvaluationService {

	/*
	 * EVALUATION
	 */
	Integer createEvaluation (Evaluation toCreate) ; 
	
	Evaluation getEvaluationById (Integer idEvaluation) ; 
	
	Collection <Evaluation> getEvaluationByProfil (Profil profilCandidat) ; 
	
	Collection <Evaluation> getEvaluationByTestSheet (TestSheet testSheetPasse) ;
	
	void deleteEvaluation (Evaluation toDelete) ; 

	/*
	 * RESPONSES
	 */
	Integer createResponse (Response reponse) ; 
	
	Response getResponseById (Integer idResponse) ; 
	
	void deleteResponse (Evaluation evaluationAModifier , Response response ) ; 

}
