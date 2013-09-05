package com.ossia.test.repository;

import java.util.Collection;

import com.ossia.test.domain.Profil;

public interface ProfilRepository {

	Integer createProfil (Profil profilACreer) ; 
	
	Profil getProfilById (Integer idProfil) ; 
	
	Profil getProfilByLogin (String login) ; 
	
	Collection<Profil> getProfilByNom (String nom) ; 
	
	Collection<Profil> getProfilByRole (boolean admin) ;
	
	void deleteProfil (Profil profilASupprimer) ; 
}
