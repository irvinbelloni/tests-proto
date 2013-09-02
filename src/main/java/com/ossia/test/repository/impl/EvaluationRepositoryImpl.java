package com.ossia.test.repository.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.EvaluationRepository;

@Repository
public class EvaluationRepositoryImpl extends AbstractRepositoryImpl implements EvaluationRepository {

	@Transactional
	public Integer createEvaluation(Evaluation toCreate) {
		Evaluation created = (Evaluation) getHibernateCurrentSession().save(toCreate) ; 
		return created.getId() ;
	}

	@Transactional
	public Evaluation getEvaluationById(Integer idEvaluation) {
		Query query = getHibernateCurrentSession().createQuery("from Evaluation eval where eval.id=:id").setInteger("id", idEvaluation);
		
		Evaluation retrieved = (Evaluation) query.uniqueResult() ; 
		return retrieved ;
	}

	@Transactional
	public Collection<Evaluation> getEvaluationByProfil(Profil profilCandidat) {
		Query query = getHibernateCurrentSession().createQuery("from Evaluation eval where eval.profil_id=:id")
				.setInteger("id", profilCandidat.getId());
		
		List<Evaluation> retrieved = (List<Evaluation>) query.list() ; 
		return retrieved ;
	}

	@Transactional
	public Collection<Evaluation> getEvaluationByTestSheet(
			TestSheet testSheetPasse) {
		Query query = getHibernateCurrentSession().createQuery("from Evaluation eval where eval.test_id=:id")
				.setInteger("id", testSheetPasse.getId());
		
		List<Evaluation> retrieved = (List<Evaluation>) query.list() ; 
		return retrieved ;
	}

	@Transactional
	public void deleteEvaluation(Evaluation toDelete) {
		getHibernateCurrentSession().delete(toDelete) ; 
	}

}
