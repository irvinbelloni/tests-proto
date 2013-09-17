package com.ossia.test.service.impl;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.HistoAction;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.Response;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.EvaluationRepository;
import com.ossia.test.repository.ProfilRepository;
import com.ossia.test.repository.ResponseRepository;
import com.ossia.test.repository.TestSheetRepository;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.ProfilService;

@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {
	
	private final static Logger logger = Logger.getLogger(EvaluationServiceImpl.class);

	@Autowired
	private EvaluationRepository evaluationRepository ;
	
	@Autowired
	private ResponseRepository responseRepository ;
	
	@Autowired
	private ProfilRepository profilRepository;
	
	@Autowired
	private TestSheetRepository testSheetRepository;
	
	@Autowired 
	ProfilService profilService;

	public Evaluation createEvaluation(Evaluation toCreate) {
		Integer id = evaluationRepository.create(toCreate); 
		return getEvaluationById(id) ; 
	}

	@Transactional(readOnly = true)
	public Evaluation getEvaluationById(Integer idEvaluation) {
		return evaluationRepository.getById(idEvaluation);
	}
	
	@Transactional(readOnly = true)
	public Collection<Evaluation> getEvaluationByProfil(Profil profilCandidat) {
		return evaluationRepository.getEvaluationByProfil(profilCandidat);
	}
	
	@Transactional(readOnly = true)
	public Collection<Evaluation> getEvaluationByTestSheet(
			TestSheet testSheetPasse) {
		return evaluationRepository.getEvaluationByTestSheet(testSheetPasse);
	}

	public void deleteEvaluation(Evaluation toDelete) {
		evaluationRepository.delete(toDelete) ; 
	}

	public Response createResponse(Response reponse) {
		Integer id = responseRepository.create(reponse); 
		return getResponseById(id) ; 
	}

	@Transactional(readOnly = true)
	public Response getResponseById(Integer idResponse) {
		return responseRepository.getById(idResponse);
	}

	public void deleteResponse(Evaluation evaluationAModifier, Response response) {
		responseRepository.delete(response) ; 
	} 
	
	public Evaluation deleteEvaluation (int evalId, int candidateId, boolean onlyNonTakenTest, Profil admin) {
		Evaluation evaluation = evaluationRepository.getById(evalId);
		if (evaluation != null && evaluation.getProfil().getId() == candidateId) { // The evaluation must belong to the given candidate
			if (!onlyNonTakenTest || (onlyNonTakenTest && !evaluation.isTestTaken())) {
				evaluation.getProfil().getEvaluations().remove(evaluation);
				evaluation.getTest().getEvaluations().remove(evaluation);
				evaluationRepository.delete(evaluation);
				
				// Tracing test removal
				profilService.createHistoricalTrace(evaluation.getProfil(), admin, HistoAction.DESASSIGNATION_TEST.getCode());
				
				// Returning the deleted evaluation
				return evaluation;
			} else {
				logger.warn("Trying to delete un-deletable evaluation " + evalId + " for candidate " + candidateId);
			}
		} else {
			logger.warn("Trying to delete unexisting evaluation " + evalId + " for candidate " + candidateId);
		}
		// Evaluation does not exist or cannot be deleted. Therefore nothing is deleted, we return null 
		return null;
	}

	@Override
	public Evaluation assignTest(int testId, int candidateId, Profil admin) {
		Profil candidate = profilRepository.getById(candidateId);
		if (candidate == null) {
			logger.error("Test assignment: The candidate " + candidateId + " does not exist");
			return null;
		}
		
		TestSheet testSheet = testSheetRepository.getById(testId);
		if (testSheet == null) {
			logger.error("Test assignment: The test " + testId + " does not exist");
			return null;
		}
		
		Evaluation evaluation = new Evaluation();
		evaluation.setProfil(candidate);
		evaluation.setTest(testSheet);	
		testSheet.getEvaluations().add(evaluation);				
		candidate.getEvaluations().add(evaluation);		
		profilRepository.update(candidate);

		// Tracing test removal
		profilService.createHistoricalTrace(evaluation.getProfil(), admin, HistoAction.ASSIGNATION_TEST.getCode());		
		
		return evaluation;
	}	
}
