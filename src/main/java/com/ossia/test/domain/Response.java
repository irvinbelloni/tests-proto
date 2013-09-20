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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "T_RESPONSES")
public class Response implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name = "question_id", referencedColumnName = "id", nullable = false)
    @NotNull
    private Question question;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
    private Set<PropositionReponse> reponseChoisie ; 

    public Response() {
    	this.id = 0 ; 
	}

	public Response(Question question, Set<PropositionReponse> reponseChoisie) {
		super();
		this.question = question;
		this.reponseChoisie = reponseChoisie;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

	public Set<PropositionReponse> getReponseChoisie() {
		return reponseChoisie;
	}

	public void setReponseChoisie(Set<PropositionReponse> reponseChoisie) {
		this.reponseChoisie = reponseChoisie;
	}
}
