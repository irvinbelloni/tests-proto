package com.ossia.test.repository;

import java.util.Collection;

import com.ossia.test.domain.TestSheet;

public interface TestSheetRepository extends AbstractRepository<TestSheet, Integer> {

	Collection<TestSheet> getTestSheetsByType(String type);
}
