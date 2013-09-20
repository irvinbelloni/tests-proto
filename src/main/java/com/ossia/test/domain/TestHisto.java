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
@Table(name = "T_SHEET_HISTORIQUES")
public class TestHisto implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@ManyToOne @JoinColumn(name = "admin_id")
	private Profil admin;
	
	private String action;
	
	private Date timestamp;
	
	@ManyToOne @JoinColumn(name = "test_id")
	private TestSheet test;
	
	private static final long serialVersionUID = -8442467703345558760L;
	
	public TestHisto() {
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

	public TestSheet getTestSheet() {
		return test;
	}

	public void setTestSheet(TestSheet test) {
		this.test = test;
	}

	public Profil getAdmin() {
		return admin;
	}

	public void setAdmin(Profil admin) {
		this.admin = admin;
	}
}