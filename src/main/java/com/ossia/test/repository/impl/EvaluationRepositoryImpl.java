package com.ossia.test.repository.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.EvaluationRepository;

@Repository
public class EvaluationRepositoryImpl implements EvaluationRepository {

	@Override
	public Integer createEvaluation(Evaluation toCreate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Evaluation getEvaluationById(Integer idEvaluation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Evaluation> getEvaluationByProfil(Profil profilCandidat) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Evaluation> getEvaluationByTestSheet(
			TestSheet testSheetPasse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteEvaluation(Evaluation toDelete) {
		// TODO Auto-generated method stub
		
	}

}
