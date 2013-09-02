package com.ossia.test.repository.impl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.ossia.test.domain.Profil;
import com.ossia.test.repository.ProfilRepository;

@Repository
public class ProfilRepositoryImpl implements ProfilRepository {

	@Override
	public Integer createProfil(Profil profilACreer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profil getProfilById(Integer idProfil) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Profil getProfilByLogin(String login) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Profil> getProfilByNom(String nom) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteProfil(Profil profilASupprimer) {
		// TODO Auto-generated method stub
		
	}

}
