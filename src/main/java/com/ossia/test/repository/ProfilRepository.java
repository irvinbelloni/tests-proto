package com.ossia.test.repository;

import java.util.Collection;

import com.ossia.test.domain.Profil;

public interface ProfilRepository {

	Integer createProfil (Profil profilACreer) ; 
	
	Profil getProfilById (Integer idProfil) ; 
	
	Profil getProfilByLogin (String login) ; 
	
	Collection<Profil> getProfilByNom (String nom) ; 
	
	void deleteProfil (Profil profilASupprimer) ; 
}
