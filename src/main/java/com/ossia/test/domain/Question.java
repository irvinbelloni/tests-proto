package com.ossia.test.domain;//

import java.io.Serializable;
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

@Entity
@Table(name = "T_QUESTIONS")
public class Question implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

	private String intitule;
	private String niveau;
	private String contenu;
    
    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} , fetch=FetchType.EAGER )
    @JoinColumn(name = "test_id", referencedColumnName = "id", nullable = false)
    private TestSheet test;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER , mappedBy = "question")
    private Set<PropositionReponse> propositionsReponses ; 
    
    public Question() {
    	this.id = 0 ; 
	}

	public Question(String intitule, String niveau, String contenu) {
		super();
		this.intitule = intitule;
		this.niveau = niveau;
		this.contenu = contenu;
	}

	public Question(TestSheet testSheet) {
		this.id = 0 ; 
		this.test = testSheet ;
	}
	
	public Integer getPropositionSize () {
		if (propositionsReponses == null) {
			return 0 ; 
		}
		else {
			return propositionsReponses.size() ;  
		}
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

	public TestSheet getTest() {
		return test;
	}

	public void setTest(TestSheet test) {
		this.test = test;
	}

	public Set<PropositionReponse> getPropositionsReponses() {
		return propositionsReponses;
	}

	public void setPropositionsReponses(Set<PropositionReponse> propositionsReponses) {
		this.propositionsReponses = propositionsReponses;
	}
}
