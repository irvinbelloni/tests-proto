package com.ossia.test.repository.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.TestSheetRepository;

@Repository
public class TestSheetRepositoryImpl extends AbstractRepositoryImpl  implements TestSheetRepository {

	
	public Integer createTestSheet(TestSheet testSheetACreer) {
		Integer id = (Integer) getHibernateCurrentSession().save(testSheetACreer) ; 
		return id ;
	}

	
	public TestSheet getTestSheetById(Integer idTestSheet) {
		Query query = getHibernateCurrentSession().createQuery("from TestSheet ts where ts.id=:id")
				.setInteger("id", idTestSheet);
		
		TestSheet retrieved = (TestSheet) query.uniqueResult()  ;  
		return retrieved ;
	}

	
	public Collection<TestSheet> getTestSheetsByType(String type) {
		Query query = getHibernateCurrentSession().createQuery("from TestSheet ts where ts.type=:type")
				.setString("type", type);
		
		List<TestSheet> retrieved = (List<TestSheet>) query.list()  ;  
		return retrieved ;
	}

	
	public void deleteTestSheet(TestSheet testSheetToDelete) {
		getHibernateCurrentSession().delete(testSheetToDelete) ;
	}


}
