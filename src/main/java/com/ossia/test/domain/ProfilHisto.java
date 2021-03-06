package com.ossia.test.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "T_PROFIL_HISTORIQUES")
public class ProfilHisto implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne @JoinColumn(name = "admin_id")
	private Profil admin;
	
	private String action;
	
	private Date timestamp;
	
	@ManyToOne @JoinColumn(name = "profil_id")
	private Profil profil;
	
	private static final long serialVersionUID = -8442467703708358760L;
	
	public ProfilHisto() {
		this.timestamp = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public Profil getAdmin() {
		return admin;
	}

	public void setAdmin(Profil admin) {
		this.admin = admin;
	}
}
