package com.ossia.test.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "T_EVALUATIONS")
public class Evaluation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "test_id")
	@NotNull
	private TestSheet test;

	@ManyToOne
	@JoinColumn(name = "profil_id", referencedColumnName = "id", nullable = false)
	@NotNull
	private Profil profil;

	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private Set<Response> responses;

	private Boolean statut;
	
	private Date start_time ; 
	
	private Date end_time ; 

	public Evaluation() {
		statut = false;
	}

	public Evaluation(TestSheet test, Profil profil, Set<Response> responses) {
		super();
		this.test = test;
		this.profil = profil;
		this.responses = responses;
	}
	
	@Transient
	public boolean isTestTaken(){
		return this.statut != null && this.statut;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TestSheet getTest() {
		return test;
	}

	public void setTest(TestSheet test) {
		this.test = test;
	}

	public Profil getProfil() {
		return profil;
	}

	public void setProfil(Profil profil) {
		this.profil = profil;
	}

	public Set<Response> getResponses() {
		return responses;
	}

	public void setResponses(Set<Response> responses) {
		this.responses = responses;
	}

	public Boolean getStatut() {
		return statut;
	}

	public void setStatut(Boolean statut) {
		this.statut = statut;
	}

	public Date getStart_time() {
		return start_time;
	}

	public void setStart_time(Date start_time) {
		this.start_time = start_time;
	}

	public Date getEnd_time() {
		return end_time;
	}

	public void setEnd_time(Date end_time) {
		this.end_time = end_time;
	}
}
