package com.ossia.test.repository.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Response;
import com.ossia.test.repository.ResponseRepository;

@Repository
public class ResponseRepositoryImpl extends AbstractRepositoryImpl implements ResponseRepository {
	
	@Transactional
	public Integer createResponse(Response reponse) {
		
		Response reponseCree = (Response) getHibernateCurrentSession().save(reponse) ; 
		return reponseCree.getId();
	}

	@Transactional
	public Response getResponseById(Integer idResponse) {
		Query query = getHibernateCurrentSession()
				.createQuery("from Response response where response.id=:id")
				.setInteger("id", idResponse) ; 
		
		return (Response) query.uniqueResult() ;
	}

	@Transactional
	public void deleteResponse(Evaluation evaluationAModifier, Response response) {
		getHibernateCurrentSession().delete(response) ; 
		
		// FIXME - check propagation !!
	}

}
