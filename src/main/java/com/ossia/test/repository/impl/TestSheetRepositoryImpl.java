package com.ossia.test.repository.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.TestSheetRepository;

@Repository
public class TestSheetRepositoryImpl extends AbstractRepositoryImpl  implements TestSheetRepository {

	@Transactional
	public Integer createTestSheet(TestSheet testSheetACreer) {
		Integer id = (Integer) getHibernateCurrentSession().save(testSheetACreer) ; 
		return id ;
	}

	@Transactional
	public TestSheet getTestSheetById(Integer idTestSheet) {
		Query query = getHibernateCurrentSession().createQuery("from TestSheet ts where ts.id=:id")
				.setInteger("id", idTestSheet);
		
		TestSheet retrieved = (TestSheet) query.uniqueResult()  ;  
		return retrieved ;
	}

	@Transactional
	public Collection<TestSheet> getTestSheetsByType(String type) {
		Query query = getHibernateCurrentSession().createQuery("from TestSheet ts where ts.type=:type")
				.setString("type", type);
		
		List<TestSheet> retrieved = (List<TestSheet>) query.list()  ;  
		return retrieved ;
	}

	@Transactional
	public void deleteTestSheet(TestSheet testSheetToDelete) {
		getHibernateCurrentSession().delete(testSheetToDelete) ;
	}


}
