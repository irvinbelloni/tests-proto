package com.ossia.test.repository.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.PropositionReponse;
import com.ossia.test.repository.PropositionReponseRepository;

@Repository
public class PropositionReponseRepositoryImpl extends AbstractRepositoryImpl implements
		PropositionReponseRepository {

	
	public Integer createPropositionReponse(PropositionReponse pr) {
		Integer id = (Integer) getHibernateCurrentSession().save(pr) ; 
		return id ;
	}

	
	public PropositionReponse getPropositionReponsebyId(Integer id) {
		Query query = getHibernateCurrentSession().createQuery("from PropositionReponse pr where pr.id=:id")
				.setInteger("id", id);
		
		PropositionReponse retrieved = (PropositionReponse) query.uniqueResult()  ;  
		return retrieved ;
	}

	
	public void deletePropositionReponse(PropositionReponse pr) {
		getHibernateCurrentSession().delete(pr) ;
	}

}
