package com.ossia.test.service.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Profil;
import com.ossia.test.repository.ProfilRepository;
import com.ossia.test.service.ProfilService;
import com.ossia.test.web.sort.ProfilSortingInfo;

@Service("profilService") @Transactional
public class ProfilServiceImpl implements ProfilService, UserDetailsService {
	
	@Autowired
	private ProfilRepository profilRepository ;

	@Override
	public Profil createProfil(Profil profilACreer) throws ConstraintViolationException {
		profilACreer.setDateActivation(null);
		if (profilACreer.isActive()) {
			profilACreer.setDateActivation(new Date());
		}		
		
		Integer value = profilRepository.create(profilACreer) ; 
		return getProfilById(value);		
	}

	@Override @Transactional(readOnly = true)
	public Profil getProfilById(Integer idProfil) {
		return profilRepository.getById(idProfil);
	}

	@Override @Transactional(readOnly = true)
	public Profil getProfilByLogin(String login) {
		return profilRepository.getProfilByLogin(login);
	}

	@Override @Transactional(readOnly = true)
	public Collection<Profil> getProfilByNom(String nom) {
		return profilRepository.getProfilByNom(nom);
	}
	
	@Override @Transactional(readOnly = true)
	public Collection<Profil> getProfilByRole(boolean admin) {
		return profilRepository.getProfilByRole(admin);
	}
	
	@Override @Transactional(readOnly = true)
	public Collection<Profil> getSortedProfilByRole(boolean admin, ProfilSortingInfo sortingInfo) {
		return profilRepository.getSortedProfilByRole(admin, sortingInfo.getSortingField(), sortingInfo.getSortingDirection());
	}

	@Override
	public void deleteProfil(Profil profilASupprimer) {
		profilRepository.delete(profilASupprimer) ; 
	}	
	
	@Override
	public void updateProfil(Profil profilAModifier) throws ConstraintViolationException {
		Profil profilCourant = profilRepository.getById(profilAModifier.getId());
		if (profilCourant == null) {
			// TODO gerer si le profile n'existe pas
			return;
		}
		
		profilAModifier.setDateActivation(null);
		if (profilAModifier.isActive()) {
			profilAModifier.setDateActivation(new Date());
		}
		
		try {
			BeanUtils.copyProperties(profilCourant, profilAModifier);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		profilRepository.update(profilCourant);	
		String text ="";
		text.lastIndexOf("r");
	}

	/**
	 * Method used by spring-security to check if the user exists
	 */
	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		Profil profil = getProfilByLogin(login);
		if (profil == null) {
			throw new UsernameNotFoundException("Login (" + login + ") is not found");			
		}		
		return profil;
	}
	
	
}