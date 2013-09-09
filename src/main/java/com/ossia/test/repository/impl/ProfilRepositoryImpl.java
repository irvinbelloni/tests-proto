package com.ossia.test.repository.impl;

import java.util.Collection;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Profil;
import com.ossia.test.repository.ProfilRepository;

@SuppressWarnings("unused")
@Repository 
public class ProfilRepositoryImpl extends AbstractRepositoryImpl<Profil, Integer> implements ProfilRepository {
	
	public Profil getProfilByLogin(String login) {
		Query query = getHibernateCurrentSession().createQuery("from Profil user where user.login=:login")
				.setString("login", login);
		
		Profil retrieved = (Profil) query.uniqueResult()  ;  
		return retrieved ;
	}

	@SuppressWarnings("unchecked")	
	public Collection<Profil> getProfilByNom(String nom) {
		Query query = getHibernateCurrentSession().createQuery("from Profil user where user.nom=:nom")
				.setString("nom", nom);
		
		List<Profil> retrieved = (List<Profil>) query.list()  ;  
		return retrieved ;
	}	
	
	@Override @SuppressWarnings("unchecked")
	public Collection<Profil> getProfilByRole(boolean admin) {
		Query query = getHibernateCurrentSession().createQuery("from Profil user where user.admin=:admin").setBoolean("admin", admin);
		
		List<Profil> retrieved = (List<Profil>) query.list()  ;  
		return retrieved ;
	}
}
