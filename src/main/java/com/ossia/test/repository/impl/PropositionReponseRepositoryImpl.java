package com.ossia.test.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.repository.PropositionReponseRepository;

@Repository
public class PropositionReponseRepositoryImpl extends AbstractRepositoryImpl<PropositionReponse, Integer> implements PropositionReponseRepository {

	@SuppressWarnings("unchecked")
	public List<PropositionReponse> getAllPropositionReponseByQuestionId(Integer id) {
		
		Query query = getHibernateCurrentSession().createQuery("from PropositionReponse pr where pr.question.id=:id")
				.setInteger("id", id);
		
		List<PropositionReponse> liste = (List<PropositionReponse>) query.list() ; 
		return liste ; 
	}
}
