package com.ossia.test.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Niveau;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.QuestionRepository;

@Repository
public class QuestionRepositoryImpl extends AbstractRepositoryImpl<Question, Integer> implements QuestionRepository {

	@SuppressWarnings("unchecked")
	public List<Question> getAllQuestionsByTestId(Integer id) {
		Query query = getHibernateCurrentSession().createQuery("from Question questions where questions.test.id=:id")
				.setInteger("id", id);		
		return (List<Question>) query.list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Question> getQuestionsByTestAndNiveau(TestSheet test , Niveau level) {	    
	    Query query = getHibernateCurrentSession().createQuery("FROM Question question WHERE question.test.id = :testId AND niveau = :niveau");
	    query.setInteger("testId", test.getId());	
	    query.setString("niveau", level.getValue());
		return (List<Question>) query.list();
	}
}
