package com.ossia.test.repository.impl;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.QuestionRepository;

@Repository
public class QuestionRepositoryImpl extends AbstractRepositoryImpl implements QuestionRepository {
	
	@Transactional
	public Integer createQuestion(Question questionACreer) {
		Question newQuestion = (Question) getHibernateCurrentSession().save(questionACreer) ;  
		return newQuestion.getId() ;
	}
	
	@Transactional
	public Question getQuestionById (Integer idQuestion) {
		Query query = getHibernateCurrentSession().createQuery("from Question question where question.id=:id")
				.setInteger("id", idQuestion);
		
		Question question = (Question) query.uniqueResult() ;  
		
		return question ; 
	}

	@Transactional
	public void deleteQuestionFromTestSheet(TestSheet test, Question aSupprimer) {
		getHibernateCurrentSession().delete(aSupprimer) ; 
	}

}
