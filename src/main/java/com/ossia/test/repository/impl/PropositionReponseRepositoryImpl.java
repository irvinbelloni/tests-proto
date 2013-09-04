package com.ossia.test.repository.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.repository.PropositionReponseRepository;

@Repository
public class PropositionReponseRepositoryImpl extends AbstractRepositoryImpl implements
		PropositionReponseRepository {

	@Transactional
	public Integer createPropositionReponse(PropositionReponse pr) {
		Integer id = (Integer) getHibernateCurrentSession().save(pr) ; 
		return id ;
	}

	@Transactional
	public PropositionReponse getPropositionReponsebyId(Integer id) {
		Query query = getHibernateCurrentSession().createQuery("from PropositionReponse pr where pr.id=:id")
				.setInteger("id", id);
		
		PropositionReponse retrieved = (PropositionReponse) query.uniqueResult()  ;  
		return retrieved ;
	}

	@Transactional
	public void deletePropositionReponse(PropositionReponse pr) {
		getHibernateCurrentSession().delete(pr) ;
	}

}
