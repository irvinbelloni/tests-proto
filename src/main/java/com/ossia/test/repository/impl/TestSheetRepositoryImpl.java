package com.ossia.test.repository.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.TestSheetRepository;

@Repository
public class TestSheetRepositoryImpl extends AbstractRepositoryImpl  implements TestSheetRepository {

	@Transactional
	public Integer createTestSheet(TestSheet testSheetACreer) {
		
		return null;
	}

	@Transactional
	public TestSheet getTestSheetById(Integer idTestSheet) {
		
		return null;
	}

	@Transactional
	public Collection<TestSheet> getTestSheetsByType(String type) {
		
		return null;
	}

	@Transactional
	public void deleteTestSheet(TestSheet testSheetToDelete) {
		
		
	}


}
