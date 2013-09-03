package com.ossia.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossia.test.repository.EvaluationRepository;
import com.ossia.test.repository.ResponseRepository;
import com.ossia.test.service.EvaluationService;

@Service
public class EvaluationServiceImpl implements EvaluationService {

	@Autowired
	private EvaluationRepository evaluationRepository ;
	
	@Autowired
	private ResponseRepository responseRepository ; 
	
}
