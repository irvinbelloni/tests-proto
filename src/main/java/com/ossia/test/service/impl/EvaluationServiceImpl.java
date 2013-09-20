package com.ossia.test.service.impl;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.HistoAction;
import com.ossia.test.domain.Niveau;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.Response;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.domain.TestStatus;
import com.ossia.test.repository.EvaluationRepository;
import com.ossia.test.repository.ProfilRepository;
import com.ossia.test.repository.QuestionRepository;
import com.ossia.test.repository.ResponseRepository;
import com.ossia.test.repository.TestSheetRepository;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.ProfilService;
import com.ossia.test.web.form.QuestionForm;

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
	private QuestionRepository questionRepository ; 
	
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
	
	@Transactional(readOnly = true)
	public List<Evaluation> getAllActiveResultats() {
		return  evaluationRepository.getAll() ;
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
		evaluation.setStatus(TestStatus.ASSIGNED.getCode());
		testSheet.getEvaluations().add(evaluation);				
		candidate.getEvaluations().add(evaluation);		
		profilRepository.update(candidate);

		// Tracing test removal
		profilService.createHistoricalTrace(evaluation.getProfil(), admin, HistoAction.ASSIGNATION_TEST.getCode());		
		
		return evaluation;
	}	
	
	public Boolean verifyConformityResponse (Set<PropositionReponse> reponses ) {
		Boolean resultat = null ; 
		for (PropositionReponse propositionReponse : reponses) {
			if (propositionReponse.getPropositionCorrecte()) {
				resultat = Boolean.TRUE ; 
			}
			else {
				resultat = Boolean.FALSE ; 
				break ; 
			}
		}
		return resultat ; 
	}

	@Override
	public String determinerNoteGlobale(Evaluation evalParamEntree) {
		Integer nombreQuestions = evalParamEntree.getResponses().size() ;
		Integer nombreReponsesVraies = 0 ; 
		Integer nombreReponsesFausses = 0 ;
		
		for (Response response : evalParamEntree.getResponses()) {
			Set<PropositionReponse> reponseChoisie = response.getReponsesChoisies() ; 
			
			if (verifyConformityResponse(reponseChoisie)) {
				nombreReponsesVraies ++ ; 
			} else {
				nombreReponsesFausses ++ ; 
			}
		}
		return nombreReponsesVraies + "/" + nombreQuestions ;
	}

	@Override
	public String determinerNoteParNiveau(Evaluation evalParamEntree, Niveau level) {
		List<Question> liste = questionRepository.getQuestionsByTestAndNiveau(evalParamEntree.getTest(), level) ;
		Integer nombreQuestions = liste.size() ;
		Integer nombreReponsesVraies = 0 ; 
		Integer nombreReponsesFausses = 0 ;
		
		for (Question question : liste) {
			Response response = responseRepository.getResponseByEvaluationAndQuestion (evalParamEntree , question) ; 
			Set<PropositionReponse> reponseChoisie = response.getReponsesChoisies() ; 
			
			if (verifyConformityResponse(reponseChoisie)) {
				nombreReponsesVraies ++ ; 
			} else {
				nombreReponsesFausses ++ ; 
			}
		}
		return nombreReponsesVraies + "/" + nombreQuestions ;
	}
	
	@Override
	public void markTestAsStarted(Evaluation evaluation) {
		evaluation.setStartTime(new Date());
		evaluation.setStatus(TestStatus.IN_PROGRESS.getCode());
		
		evaluationRepository.update(evaluation);
	}

	@Override
	public boolean isTimeOver(Evaluation evaluation) {
		if (evaluation.getStatus() != TestStatus.IN_PROGRESS.getCode()) { // The test must be in progress
			return true;
		}	
		
		try {
			Date now = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(evaluation.getStartTime());
			calendar.add(Calendar.MINUTE, evaluation.getTest().getDuree()); // adds the test length to the starting time
			Date estimatedEndTime = calendar.getTime();
			
			// The current timestamp is after the estimated ending time, so the test is over, we return true, false otherwise (the test can go on)
			return now.after(estimatedEndTime);
		} catch (NullPointerException npe) { // In case the starting time has not been set
			return true;
		}
	}

	@Override
	public Evaluation saveCandidateResponse(QuestionForm questionForm) {
		Evaluation evaluation = evaluationRepository.getById(questionForm.getEvaluationId());
		if (evaluation == null) {
			return null;
		}
		
		boolean newAnswer = true;
		
		// Checking if the question has already been answered
		for (Response response : evaluation.getResponses()) {
			if (response.getQuestion().getId() == questionForm.getQuestionId()) {
				// The question has already been answered. Then we have to update the answers picked by the candidate
				response.setReponsesChoisies(new HashSet<PropositionReponse>());
				newAnswer = false;
				
				for (PropositionReponse proposition : response.getQuestion().getPropositionsReponses()) {
					for (int i : questionForm.getPropositions()) {
						if (i == proposition.getId()) {
							response.getReponsesChoisies().add(proposition);
						}
					}
				}	
			}
		}
		
		if (newAnswer) { // The question has never been answered before		
			Response response = new Response();
			response.setEvaluation(evaluation);
			
			for (Question question : evaluation.getTest().getQuestions()) { // Parsing all the test
				if (question.getId() == questionForm.getQuestionId()) {
					response.setQuestion(question);
					
					for (PropositionReponse proposition : response.getQuestion().getPropositionsReponses()) {
						for (int i : questionForm.getPropositions()) {
							if (i == proposition.getId()) {
								response.getReponsesChoisies().add(proposition);
							}
						}
					}			
				}
			}
			evaluation.getResponses().add(response);
		}		
		
		evaluationRepository.update(evaluation);	
		return evaluation;
	}

	@Override
	public void closeTest(int evaluationId) {
		Evaluation evaluation = evaluationRepository.getById(evaluationId);
		if (evaluation == null) {
			return;
		}
		if (!evaluation.isTestInProgress()) {
			return;
		}
		
		evaluation.setEndTime(new Date());
		evaluation.setStatus(TestStatus.DONE.getCode());
		
		evaluationRepository.update(evaluation);		
	}	
}
