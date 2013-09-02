package com.ossia.test.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.ossia.test.service.ProfilService;

@Controller
public class ProfilControler {
	
	
	@Autowired
	public ProfilService profilService ; 
}
