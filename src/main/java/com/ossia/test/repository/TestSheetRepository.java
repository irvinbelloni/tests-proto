package com.ossia.test.repository;

import java.util.Collection;

import com.ossia.test.domain.TestSheet;

public interface TestSheetRepository {

	Integer createTestSheet(TestSheet testSheetACreer);

	TestSheet getTestSheetById(Integer idTestSheet);

	Collection<TestSheet> getTestSheetsByType(String type);

	void deleteTestSheet(TestSheet testSheetToDelete);

}
