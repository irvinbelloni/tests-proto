package com.ossia.test.repository.impl;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.Response;
import com.ossia.test.repository.ResponseRepository;

@Repository
public class ResponseRepositoryImpl extends AbstractRepositoryImpl<Response, Integer> implements ResponseRepository {

	@Override
	public Response getResponseByEvaluationAndQuestion(
			Evaluation evalParamEntree, Question question) {
	    Criteria crit = getHibernateCurrentSession().createCriteria(Response.class); 
	    crit.add(Restrictions.eq("evaluation", evalParamEntree))  ; 
	    crit.add(Restrictions.eq("question", question))  ;
	    Response liste = (Response) crit.uniqueResult();  
	    return liste ; 
	}
}
