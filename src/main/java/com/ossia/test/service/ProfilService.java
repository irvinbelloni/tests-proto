package com.ossia.test.service;

import java.util.Collection;

import com.ossia.test.domain.Profil;
import com.ossia.test.web.sort.ProfilSortingInfo;

public interface ProfilService {

	Profil createProfil (Profil profilACreer, int adminId); 
	
	Profil changeActivation (Integer profilId, int adminId);
	
	Profil getProfilById (Integer idProfil) ; 
	
	Profil getProfilByLogin (String login) ; 
	
	Collection<Profil> getProfilByNom (String nom) ; 
	
	Collection<Profil> getProfilByRole (boolean admin); 
	
	Collection<Profil> getSortedProfilByRole(boolean admin, ProfilSortingInfo sortingInfo);
	
	void deleteProfil (Profil profilASupprimer) ;
	
	Profil deleteProfil (Integer profilId, int adminId);
	
	void updateProfil (Profil profilAModifier, int adminId);
}