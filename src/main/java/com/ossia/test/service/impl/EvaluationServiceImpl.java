package com.ossia.test.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.Response;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.EvaluationRepository;
import com.ossia.test.repository.ResponseRepository;
import com.ossia.test.service.EvaluationService;

@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationRepository evaluationRepository ;
	
	@Autowired
	private ResponseRepository responseRepository ;

	public Integer createEvaluation(Evaluation toCreate) {
		return evaluationRepository.createEvaluation(toCreate);
	}

	public Evaluation getEvaluationById(Integer idEvaluation) {
		return evaluationRepository.getEvaluationById(idEvaluation);
	}
	
	public Collection<Evaluation> getEvaluationByProfil(Profil profilCandidat) {
		return evaluationRepository.getEvaluationByProfil(profilCandidat);
	}

	public Collection<Evaluation> getEvaluationByTestSheet(
			TestSheet testSheetPasse) {
		return evaluationRepository.getEvaluationByTestSheet(testSheetPasse);
	}

	public void deleteEvaluation(Evaluation toDelete) {
		evaluationRepository.deleteEvaluation(toDelete) ; 
	}

	public Integer createResponse(Response reponse) {
		return responseRepository.createResponse(reponse);
	}

	public Response getResponseById(Integer idResponse) {
		return responseRepository.getResponseById(idResponse);
	}

	public void deleteResponse(Evaluation evaluationAModifier, Response response) {
		responseRepository.deleteResponse(evaluationAModifier, response) ; 
	} 
	
}
