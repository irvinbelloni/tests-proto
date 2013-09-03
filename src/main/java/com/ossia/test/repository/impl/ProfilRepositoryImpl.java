package com.ossia.test.repository.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Profil;
import com.ossia.test.repository.ProfilRepository;

@Repository
public class ProfilRepositoryImpl extends AbstractRepositoryImpl implements ProfilRepository {
	
	@Transactional
	public Integer createProfil(Profil profilACreer) {
		Integer id = (Integer) getHibernateCurrentSession().save(profilACreer) ; 
		return id ;
	}

	@Transactional
	public Profil getProfilById(Integer idProfil) {
		Query query = getHibernateCurrentSession().createQuery("from Profil user where user.id=:id")
				.setInteger("id", idProfil);
		
		Profil retrieved = (Profil) query.uniqueResult()  ;  
		return retrieved ;
	}

	@Transactional
	public Profil getProfilByLogin(String login) {
		Query query = getHibernateCurrentSession().createQuery("from Profil user where user.login=:login")
				.setString("login", login);
		
		Profil retrieved = (Profil) query.uniqueResult()  ;  
		return retrieved ;
	}

	@SuppressWarnings("unchecked")
	@Transactional
	public Collection<Profil> getProfilByNom(String nom) {
		Query query = getHibernateCurrentSession().createQuery("from Profil user where user.nom=:nom")
				.setString("nom", nom);
		
		List<Profil> retrieved = (List<Profil>) query.list()  ;  
		return retrieved ;
	}

	@Transactional
	public void deleteProfil(Profil profilASupprimer) {
		getHibernateCurrentSession().delete(profilASupprimer) ; 
	}

}
