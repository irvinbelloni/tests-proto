package com.ossia.test.repository.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.ossia.test.domain.TestSheet;
import com.ossia.test.repository.TestSheetRepository;

@Repository
public class TestSheetRepositoryImpl implements TestSheetRepository {

	@Override
	public Integer createTestSheet(TestSheet testSheetACreer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TestSheet getTestSheetById(Integer idTestSheet) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<TestSheet> getTestSheetsByType(String type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteTestSheet(TestSheet testSheetToDelete) {
		// TODO Auto-generated method stub
		
	}


}
