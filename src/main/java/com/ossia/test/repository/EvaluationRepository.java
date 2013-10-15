package com.ossia.test.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;

public interface EvaluationRepository extends AbstractRepository<Evaluation, Integer> {
		
	Collection <Evaluation> getEvaluationByProfil (Profil profilCandidat) ; 
	
	Collection <Evaluation> getEvaluationByTestSheet (TestSheet testSheetPasse) ;
	
	List<Evaluation> getRunningEvaluations();
	
	/**
	 * Gets a list of finished evaluations sorted and filtered with the given parameters
	 * @param orderingField Field we must order the list by
	 * @param orderingDirection Direction we must order the list by
	 * @param testId Id of the test we want to retrieve (0 means all tests)
	 * @param testType Type of test we want to retrieve (empty or null means all types)
	 * @param candidateId Id of the candidate we want to retrieve (0 means all candidates)
	 * @param passDateFrom Date from which we want to retrieve results (null means no limit)
	 * @param passDateTo Date to which we want to retrieve results (null means no limit)
	 * @return
	 */
	List<Evaluation> getSortedAndFilteredEvaluations (String orderingField, String orderingDirection, int testId, String testType, int candidateId, Date passDateFrom, Date passDateTo);
}
