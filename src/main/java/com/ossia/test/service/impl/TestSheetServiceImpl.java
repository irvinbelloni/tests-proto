package com.ossia.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossia.test.repository.QuestionRepository;
import com.ossia.test.repository.TestSheetRepository;
import com.ossia.test.service.TestSheetService;

@Service
public class TestSheetServiceImpl implements TestSheetService {
	
	@Autowired
	private TestSheetRepository testSheetRepository ; 
	
	@Autowired 
	private QuestionRepository questionRepository ; 
	
	

}
