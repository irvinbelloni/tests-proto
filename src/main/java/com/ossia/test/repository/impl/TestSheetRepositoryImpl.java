package com.ossia.test.repository.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.TestSheetRepository;

@Repository
public class TestSheetRepositoryImpl extends AbstractRepositoryImpl<TestSheet, Integer>  implements TestSheetRepository {

	@SuppressWarnings("unchecked")
	public Collection<TestSheet> getTestSheetsByType(String type) {
		Query query = getHibernateCurrentSession().createQuery("from TestSheet ts where ts.type=:type")
				.setString("type", type);
		
		List<TestSheet> retrieved = (List<TestSheet>) query.list()  ;  
		return retrieved ;
	}	
}
