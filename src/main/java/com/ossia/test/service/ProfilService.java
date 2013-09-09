package com.ossia.test.service;

import java.util.Collection;

import com.ossia.test.domain.Profil;

public interface ProfilService {

	Profil createProfil (Profil profilACreer) ; 
	
	Profil getProfilById (Integer idProfil) ; 
	
	Profil getProfilByLogin (String login) ; 
	
	Collection<Profil> getProfilByNom (String nom) ; 
	
	Collection<Profil> getProfilByRole (boolean admin); 
	
	void deleteProfil (Profil profilASupprimer) ; 

}
