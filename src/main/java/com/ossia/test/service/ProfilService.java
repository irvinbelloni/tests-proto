package com.ossia.test.service;

import java.util.Collection;

import org.hibernate.exception.ConstraintViolationException;

import com.ossia.test.domain.Profil;
import com.ossia.test.web.sort.ProfilSortingInfo;

public interface ProfilService {

	Profil createProfil (Profil profilACreer); 
	
	Profil getProfilById (Integer idProfil) ; 
	
	Profil getProfilByLogin (String login) ; 
	
	Collection<Profil> getProfilByNom (String nom) ; 
	
	Collection<Profil> getProfilByRole (boolean admin); 
	
	Collection<Profil> getSortedProfilByRole(boolean admin, ProfilSortingInfo sortingInfo);
	
	void deleteProfil (Profil profilASupprimer) ;
	
	void updateProfil (Profil profilAModifier);
}