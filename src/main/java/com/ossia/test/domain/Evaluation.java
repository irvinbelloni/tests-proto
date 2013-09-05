package com.ossia.test.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
	@JoinColumn(name = "test_id", referencedColumnName = "id", nullable = false)
	@NotNull
	private TestSheet test;

	@ManyToOne
	@JoinColumn(name = "profil_id", referencedColumnName = "id", nullable = false)
	@NotNull
	private Profil profil;

	@OneToMany
	private Set<Response> responses;

	private Boolean resultatOK;

	public Evaluation() {
	}

	public Evaluation(TestSheet test, Profil profil, Set<Response> responses) {
		super();
		this.test = test;
		this.profil = profil;
		this.responses = responses;
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

	public Boolean getResultatOK() {
		return resultatOK;
	}

	public void setResultatOK(Boolean resultatOK) {
		this.resultatOK = resultatOK;
	}
}
