package com.ossia.test.repository.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Question;
import com.ossia.test.repository.QuestionRepository;

@Repository
public class QuestionRepositoryImpl extends AbstractRepositoryImpl<Question, Integer> implements QuestionRepository {

	public List<Question> getAllQuestionsByTestId(Integer id) {
		Query query = getHibernateCurrentSession().createQuery("from Question questions where questions.test.id=:id")
				.setInteger("id", id);
		
		List<Question> liste = (List<Question>) query.list()  ;
		return liste ; 
	}
}
