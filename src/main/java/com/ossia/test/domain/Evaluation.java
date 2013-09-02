package com.ossia.test.domain;

import org.hibernate.annotations.Sort;
import org.hibernate.annotations.SortType;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "T_EVALUATIONS")
public class Evaluation implements Serializable {

    /**
	 * Test comment
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
    @Sort(type = SortType.NATURAL)
    private Set<Response> responses;

    public Evaluation() {
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
}
