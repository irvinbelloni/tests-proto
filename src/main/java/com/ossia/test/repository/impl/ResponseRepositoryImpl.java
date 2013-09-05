package com.ossia.test.repository.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Response;
import com.ossia.test.repository.ResponseRepository;

@Repository
public class ResponseRepositoryImpl extends AbstractRepositoryImpl implements ResponseRepository {
	
	
	public Integer createResponse(Response reponse) {
		Integer id = (Integer)  getHibernateCurrentSession().save(reponse) ; 
		return id;
	}

	
	public Response getResponseById(Integer idResponse) {
		Query query = getHibernateCurrentSession()
				.createQuery("from Response response where response.id=:id")
				.setInteger("id", idResponse) ; 
		
		return (Response) query.uniqueResult() ;
	}

	
	public void deleteResponse(Evaluation evaluationAModifier, Response response) {
		getHibernateCurrentSession().delete(response) ; 
		
		// FIXME - check propagation !!
	}

}
