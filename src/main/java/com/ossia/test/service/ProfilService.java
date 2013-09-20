package com.ossia.test.service;

import java.util.Collection;

import com.ossia.test.domain.Profil;
import com.ossia.test.web.sort.SortingInfo;

public interface ProfilService {

	Profil createProfil (Profil profilACreer, Profil admin); 
	
	Profil changeActivation (Integer profilId, Profil admin);
	
	Profil getProfilById (Integer idProfil) ; 
	
	Profil getProfilByLogin (String login) ; 
	
	Collection<Profil> getProfilByNom (String nom) ; 
	
	Collection<Profil> getProfilByRole (boolean admin); 
	
	Collection<Profil> getSortedProfilByRole(boolean admin, SortingInfo sortingInfo);
	
	void deleteProfil (Profil profilASupprimer) ;
	
	Profil deleteProfil (Integer profilId, Profil admin);
	
	void updateProfil (Profil profilAModifier, Profil admin);
	
	void createHistoricalTrace (Profil profil, Profil author, String action);
}
