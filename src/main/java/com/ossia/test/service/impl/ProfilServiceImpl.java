package com.ossia.test.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ossia.test.domain.Profil;
import com.ossia.test.repository.ProfilRepository;
import com.ossia.test.service.ProfilService;

@Service
public class ProfilServiceImpl implements ProfilService {
	
	@Autowired
	private ProfilRepository profilRepository ;

	@Override
	public Profil createProfil(Profil profilACreer) {
		Integer value = profilRepository.createProfil(profilACreer) ; 
		Profil toReturn = getProfilById(value) ; 
		return toReturn ;
	}

	@Override
	public Profil getProfilById(Integer idProfil) {
		return profilRepository.getProfilById(idProfil);
	}

	@Override
	public Profil getProfilByLogin(String login) {
		return profilRepository.getProfilByLogin(login);
	}

	@Override
	public Collection<Profil> getProfilByNom(String nom) {
		return profilRepository.getProfilByNom(nom);
	}

	@Override
	public void deleteProfil(Profil profilASupprimer) {
		profilRepository.deleteProfil(profilASupprimer) ; 
	} 

}
