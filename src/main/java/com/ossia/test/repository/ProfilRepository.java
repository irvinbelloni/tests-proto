package com.ossia.test.repository;

import java.util.Collection;

import com.ossia.test.domain.Profil;

public interface ProfilRepository extends AbstractRepository<Profil, Integer> {
	
	Profil getProfilByLogin (String login) ; 
	
	Collection<Profil> getProfilByNom (String nom) ; 
	
	Collection<Profil> getProfilByRole (boolean admin) ;
	
	Collection<Profil> getSortedProfilByRole(boolean admin, String orderingField, String orderingDirection);
}
