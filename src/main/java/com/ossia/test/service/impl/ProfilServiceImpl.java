package com.ossia.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossia.test.repository.ProfilRepository;
import com.ossia.test.service.ProfilService;

@Service
public class ProfilServiceImpl implements ProfilService {
	
	@Autowired
	private ProfilRepository profilRepository ; 

}
