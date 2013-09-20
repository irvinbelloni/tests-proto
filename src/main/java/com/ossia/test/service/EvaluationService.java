package com.ossia.test.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Niveau;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Response;
import com.ossia.test.domain.TestSheet;

public interface EvaluationService {

	/*
	 * EVALUATION
	 */
	Evaluation createEvaluation (Evaluation toCreate) ; 
	
	Evaluation getEvaluationById (Integer idEvaluation) ; 
	
	Collection <Evaluation> getEvaluationByProfil (Profil profilCandidat) ; 
	
	Collection <Evaluation> getEvaluationByTestSheet (TestSheet testSheetPasse) ;
	
	List<Evaluation> getAllActiveResultats(); 
	
	void deleteEvaluation (Evaluation toDelete) ; 
	
	/**
	 * Deletes a evaluation based on its id.
	 * The evaluation must belong to the given candidate to be deleted.
	 * If onlyNonTakenTest is set to true, we can delete the evaluation only if the test has not been taken yet
	 * If onlyNonTakenTest is set to false, we can delete the evaluation no matter what
	 * @param evalId Evaluation id
	 * @param candidateId Candidate id
	 * @param onlyNonTakenTest Indicates if the test must have not been taken yet
	 * @param admin Administrator requesting the deletion
	 * @return the deleted evaluation
	 */
	Evaluation deleteEvaluation (int evalId, int candidateId, boolean onlyNonTakenTest, Profil admin);
	
	/**
	 * Assing a test to a candidate
	 * @param testId test id
	 * @param candidateId candidate id
	 * @param admin Administrator requesting the assignment
	 * @return The created evaluation
	 */
	Evaluation assignTest (int testId, int candidateId, Profil admin);

	/*
	 * RESPONSES
	 */
	Response createResponse (Response reponse) ; 
	
	Response getResponseById (Integer idResponse) ; 
	
	void deleteResponse (Evaluation evaluationAModifier , Response response ) ; 
	
	Boolean verifyConformityResponse (Set<PropositionReponse> reponses ) ;

	String determinerNoteGlobale(Evaluation evalParamEntree);

	String determinerNoteParNiveau(Evaluation evalParamEntree, Niveau object);
	
	/*
	 * CALCULS 
	 */
	
}