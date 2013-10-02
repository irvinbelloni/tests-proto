package com.ossia.test.repository.impl;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.EvaluationRepository;

@Repository
public class EvaluationRepositoryImpl extends AbstractRepositoryImpl<Evaluation, Integer> implements EvaluationRepository {
	
	@SuppressWarnings("unchecked")
	public Collection<Evaluation> getEvaluationByProfil(Profil profilCandidat) {
		Query query = getHibernateCurrentSession().createQuery("from Evaluation eval where eval.profil_id=:id")
				.setInteger("id", profilCandidat.getId());
		
		List<Evaluation> retrieved = (List<Evaluation>) query.list() ; 
		return retrieved ;
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Evaluation> getEvaluationByTestSheet(
			TestSheet testSheetPasse) {
		Query query = getHibernateCurrentSession().createQuery("from Evaluation eval where eval.test_id=:id")
				.setInteger("id", testSheetPasse.getId());
		
		List<Evaluation> retrieved = (List<Evaluation>) query.list() ; 
		return retrieved ;
	}
	
	@SuppressWarnings("unchecked")
	public List<Evaluation> getSortedAndFilteredEvaluations (String orderingField, String orderingDirection, int testId, String testType, int candidateId, Date passDateFrom, Date passDateTo) {
		StringBuffer sqlQuery = new StringBuffer("FROM Evaluation eval WHERE eval.status = :status ");
		
		if (testId != 0) {
			sqlQuery.append("AND eval.test.id = :testId ");
		}
		if (testType != null && !testType.isEmpty()) {
			sqlQuery.append("AND eval.test.type = :testType ");
		}
		if (candidateId != 0) {
			sqlQuery.append("AND eval.profil.id = :candidateId ");
		}
		if (passDateFrom != null) {
			sqlQuery.append("AND eval.startTime >= :passDateFrom ");
		}
		if (passDateTo != null) {
			sqlQuery.append("AND eval.startTime <= :passDateTo ");
		}
		
		sqlQuery.append("ORDER BY ");
		if (orderingField.equals("type")) {
			sqlQuery.append("eval.test.type ");
		} else if (orderingField.equals("candidat")) {
			sqlQuery.append("eval.profil.nom ");
		} else if (orderingField.equals("test")) {
			sqlQuery.append("eval.test.intitule ");
		} else {
			sqlQuery.append("eval.startTime ");
		}	
		sqlQuery.append(orderingDirection);
		
		Query query = getHibernateCurrentSession().createQuery(sqlQuery.toString());
		query.setInteger("status", 3);
		if (testId != 0) {
			query.setInteger("testId", testId);
		}
		if (testType != null && !testType.isEmpty()) {
			query.setString("testType", testType);
		}
		if (candidateId != 0) {
			query.setInteger("candidateId", candidateId);
		}
		if (passDateFrom != null) {
			query.setDate("passDateFrom",  passDateFrom);
		}
		if (passDateTo != null) {
			query.setDate("passDateTo",  passDateTo);
		}
		
		return (List<Evaluation>) query.list();
	}
}
