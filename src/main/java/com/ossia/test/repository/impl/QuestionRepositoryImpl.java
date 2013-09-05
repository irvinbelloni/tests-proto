package com.ossia.test.repository.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.QuestionRepository;

@Repository
public class QuestionRepositoryImpl extends AbstractRepositoryImpl implements QuestionRepository {
	
	
	public Integer createQuestion(Question questionACreer) {
		Integer id = (Integer) getHibernateCurrentSession().save(questionACreer) ;  
		return id ;
	}
	
	
	public Question getQuestionById (Integer idQuestion) {
		Query query = getHibernateCurrentSession().createQuery("from Question question where question.id=:id")
				.setInteger("id", idQuestion);
		
		Question question = (Question) query.uniqueResult() ;  
		
		return question ; 
	}

	
	public void deleteQuestionFromTestSheet(TestSheet test, Question aSupprimer) {
		getHibernateCurrentSession().delete(aSupprimer) ; 
	}

}
