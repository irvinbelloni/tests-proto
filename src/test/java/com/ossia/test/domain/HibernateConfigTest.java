package com.ossia.test.domain;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.Assert ;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/META-INF/config/applicationContext*.xml")
public class HibernateConfigTest {

    @Autowired
    public SessionFactory sessionFactory ;

    @Test
    public void testProfil () {
        Session session = sessionFactory.openSession() ;

        Query query = session.createQuery("from Profil a where a.id=:id").setInteger("id", 1);
        Profil a = (Profil) query.uniqueResult();
        session.close();
        Assert.assertNotNull(a);
    }

    public void testConnexion () {

        // creation de profil
        Profil candidat = new Profil() ;

        candidat.setNom("Tset");
        candidat.setPrenom("prenom");
        candidat.setLogin("");
        candidat.setPass("");
        candidat.setDateDebut(new Date());
        candidat.setDateFin(new Date());

        candidat.setEmail("candidat@test.com"); // facultatif

        TestSheet testJava = new TestSheet() ;
        testJava.setDuree(45);
        testJava.setIntitule("Tests pour tester l'implémentation");
        testJava.setType("Java");

        Question question = new Question() ;
        question.setIntitule("jeu de test , question pas très originale , qu'est ce qu'un objet");
        question.setCorrectif("un objet n'est pas un objet ");
        
        String alt1 = "un objet en est un" ;
        String alt2 = "un objet en sera un" ;
        String alt3 = "un objet en était un" ;

        question.getAlternatives().add(alt1) ;
        question.getAlternatives().add(alt2) ;
        question.getAlternatives().add(alt3) ;

        testJava.getQuestions().add(question) ;

        Evaluation evaluation = new Evaluation() ;
        evaluation.setProfil(candidat);
        evaluation.setTest(testJava);

        Response reponse = new Response() ;
        reponse.setQuestion(question);
        reponse.setContenu("un objet en serait un");

        Set<Response> reponses = new HashSet<Response>() ;
        reponses.add(reponse) ;
        evaluation.setResponses(reponses);

    }
}
