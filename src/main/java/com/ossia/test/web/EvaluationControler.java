package com.ossia.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ossia.test.service.EvaluationService;

@Controller
public class EvaluationControler {
	
	
	@Autowired
	public EvaluationService evaluationService ; 
}
