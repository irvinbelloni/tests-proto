package com.ossia.test.service.impl;

import java.util.Collection;
import java.util.Date;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.HistoAction;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.ProfilHisto;
import com.ossia.test.repository.ProfilHistoRepository;
import com.ossia.test.repository.ProfilRepository;
import com.ossia.test.service.ProfilService;
import com.ossia.test.web.sort.SortingInfo;

@Service("profilService") @Transactional
public class ProfilServiceImpl implements ProfilService, UserDetailsService {
	
	@Autowired
	private ProfilRepository profilRepository;
	
	@Autowired
	private ProfilHistoRepository historiqueRepository;

	@Override
	public Profil createProfil(Profil profilACreer, Profil admin) throws ConstraintViolationException {
		profilACreer.setDateActivation(null);
		if (profilACreer.isActive()) {
			profilACreer.setDateActivation(new Date());
		}		
		
		// Tracing the creation
		ProfilHisto histo = new ProfilHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.ADD.getCode());
		histo.setProfil(profilACreer);
		
		profilACreer.getHistorique().add(histo);
		
		Integer newProfileId = profilRepository.create(profilACreer);		
		return getProfilById(newProfileId);		
	}
	
	@Override
	public Profil changeActivation(Integer profilId, Profil admin) {
		
		Profil profil = getProfilById(profilId);
		if (profil == null) {
			return profil;
		}
		
		// Tracing the creation
		ProfilHisto histo = new ProfilHisto();
		histo.setAdmin(admin);
		histo.setProfil(profil);
		
		if (profil.getDateActivation() == null) {
			profil.setDateActivation(new Date());
			profil.setMode(Profil.MODE_ACTIVATION);
			histo.setAction(HistoAction.ACTIVATE.getCode());
		} else {
			profil.setDateActivation(null);
			profil.setMode(Profil.MODE_DESACTIVATION);
			histo.setAction(HistoAction.DEACTIVATE.getCode());
		}		
		
		profil.getHistorique().add(histo);
		
		profilRepository.update(profil);		
		return profil;
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
	public Collection<Profil> getSortedProfilByRole(boolean admin, SortingInfo sortingInfo) {
		return profilRepository.getSortedProfilByRole(admin, sortingInfo.getSortingField(), sortingInfo.getSortingDirection());
	}

	@Override
	public void deleteProfil(Profil profilASupprimer) {
		profilRepository.delete(profilASupprimer) ; 
	}
	
	@Override
	public Profil deleteProfil(Integer profilId, Profil admin) {
		Profil profil = getProfilById(profilId);
		if (profil == null) {
			return profil;
		}
		
		profil.setMode(Profil.MODE_DELETE);
		profilRepository.delete(profil);		
		return profil;		
	}	
	
	@Override
	public void updateProfil(Profil profilAModifier, Profil admin) {
		Profil profilCourant = profilRepository.getById(profilAModifier.getId());
		if (profilCourant == null) {
			throw new NotFoundException("The profil " + profilAModifier.getId() + " does not exist");
		}
		
		profilAModifier.setDateActivation(null);
		if (profilAModifier.isActive()) {
			profilAModifier.setDateActivation(new Date());
		}
		
		profilCourant.copyPropertiesFrom(profilAModifier);
		
		// Tracing the modification
		ProfilHisto histo = new ProfilHisto();
		histo.setAdmin(admin);
		histo.setAction(HistoAction.EDIT.getCode());
		histo.setProfil(profilCourant);
		profilCourant.getHistorique().add(histo);
			
		profilRepository.update(profilCourant);	
	}
	
	@Override
	public void createHistoricalTrace (Profil profil, Profil author, String action) {
		ProfilHisto trace = new ProfilHisto();
		trace.setAction(action);
		trace.setProfil(profil);
		trace.setAdmin(author);
		
		profil.getHistorique().add(trace);
		profilRepository.update(profil);
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
