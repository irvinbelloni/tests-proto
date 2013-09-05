package com.ossia.test.domain;//

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@Table(name = "T_PROFILS")
public class Profil implements UserDetails {

	private static final long serialVersionUID = 1L;

	public static final SimpleGrantedAuthority ROLE_ADMIN = new SimpleGrantedAuthority(
			"ROLE_ADMIN");
	private static final Collection<GrantedAuthority> ADMIN = wrapAuthority(ROLE_ADMIN);
	private static final Collection<GrantedAuthority> USER = wrapAuthority(new SimpleGrantedAuthority(
			"ROLE_USER"));

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@NotEmpty
	private String nom;

	@NotNull
	private String prenom;

	@Pattern(regexp = "[-.a-zA-Z0-9]+@[-a-zA-Z0-9]+\\.[a-zA-Z0-9]+")
	private String email;

	@OneToMany(mappedBy = "profil")
	private Set<Evaluation> evaluations;

	@NotEmpty
	@Column(name = "login", unique = true)
	private String login;

	@NotEmpty
	private String pass;

	@NotNull
	private boolean admin;

	@NotNull
	private Date dateDebut;

	private Date dateFin;

	public Profil() {
		super();
	}

	public Profil(String nom, String prenom) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		
		dateDebut = new Date() ; 
		this.login = generateLoginFromNom() ; 
		this.pass = generatePassFromNom() ; 
	}
	
	private String generateLoginFromNom () {
		return getPrenom().substring(0, 1).toLowerCase().concat(getNom().toLowerCase().trim())  ; 
	}
	
	private String generatePassFromNom () {
		return getNom().toLowerCase().trim() ; 
	}

	/* Authorization fields */
	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public Set<Evaluation> getEvaluations() {
		return evaluations;
	}

	public void setEvaluations(Set<Evaluation> evaluations) {
		this.evaluations = evaluations;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Date getDateDebut() {
		return dateDebut;
	}

	public void setDateDebut(Date dateDebut) {
		this.dateDebut = dateDebut;
	}

	public Date getDateFin() {
		return dateFin;
	}

	public void setDateFin(Date dateFin) {
		this.dateFin = dateFin;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return pass;
	}

	@Override
	public String getUsername() {
		return getLogin();
	}

	public boolean isEnabled() {
		return getDateFin().after(getDateDebut());
	}

	@Override
	public Collection<? extends org.springframework.security.core.GrantedAuthority> getAuthorities() {
		if (admin) {
			return ADMIN;
		}
		return USER;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	private static Set<GrantedAuthority> wrapAuthority(
			GrantedAuthority authority) {
		return Collections.<GrantedAuthority> singleton(authority);
	}
}
