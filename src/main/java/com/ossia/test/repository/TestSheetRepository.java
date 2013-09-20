package com.ossia.test.repository;

import java.util.Collection;
import java.util.List;

import com.ossia.test.domain.TestSheet;

public interface TestSheetRepository extends AbstractRepository<TestSheet, Integer> {

	Collection<TestSheet> getTestSheetsByType(String type);
	
	List<TestSheet> getSortedTestSheets(String orderingField, String orderingDirection);
}
