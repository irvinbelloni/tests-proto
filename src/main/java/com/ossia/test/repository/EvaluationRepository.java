package com.ossia.test.repository;

import java.util.Collection;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;

public interface EvaluationRepository extends AbstractRepository<Evaluation, Integer> {
		
	Collection <Evaluation> getEvaluationByProfil (Profil profilCandidat) ; 
	
	Collection <Evaluation> getEvaluationByTestSheet (TestSheet testSheetPasse) ;
}
