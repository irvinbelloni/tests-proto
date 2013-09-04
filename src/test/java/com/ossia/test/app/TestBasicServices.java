package com.ossia.test.app;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.ossia.test.domain.Evaluation;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.Question;
import com.ossia.test.domain.Response;
import com.ossia.test.domain.TestSheet;
import com.ossia.test.service.EvaluationService;
import com.ossia.test.service.ProfilService;
import com.ossia.test.service.TestSheetService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/META-INF/config/applicationContext-*.xml"})
public class TestBasicServices {
	
	private final Log log = LogFactory.getLog(getClass()) ;

    @Autowired
    private EvaluationService evaluationService ;
    
    @Autowired
    private ProfilService profilService ; 
    
    @Autowired
    private TestSheetService testSheetService ; 

    @Test
    public void testValidationConfiguration () {
    	Assert.assertNotNull(evaluationService) ; 
    	
    	Assert.assertNotNull(profilService) ;
    	
    	Assert.assertNotNull(testSheetService) ;
    }
    
    @Test
    @Transactional
    public void testCreationProfil () {
    	Profil dumb = fillDumbProfil() ;
    	dumb = profilService.createProfil(dumb) ;
    	log.debug("identifiant du profil créé : "+dumb.getId()) ; 
    	Assert.assertNotNull(dumb) ; 
    }

    private Profil fillDumbProfil () {
        // creation de profil
        Profil candidat = new Profil() ;

        candidat.setNom("Tset");
        candidat.setPrenom("prenom");
        candidat.setLogin(candidat.getPrenom().substring(0, 1).toLowerCase().concat(candidat.getNom().trim().toLowerCase()) );
        candidat.setPass(candidat.getNom().trim().toLowerCase());
        candidat.setDateDebut(new Date());
        candidat.setDateFin(new Date());

        candidat.setEmail("candidat@test.com"); // facultatif
        
        return candidat ; 
    }
    
    private Question fillDumbQuestion () {
    	Question question = new Question() ;
        question.setIntitule("jeu de test , question pas très originale , qu'est ce qu'un objet");
//        question.setCorrectif("un objet n'est pas un objet ");
        
        String alt1 = "un objet en est un" ;
        String alt2 = "un objet en sera un" ;
        String alt3 = "un objet en était un" ;

//        question.getAlternatives().add(alt1) ;
//        question.getAlternatives().add(alt2) ;
//        question.getAlternatives().add(alt3) ;
        
        return question ; 
    }
    
    private TestSheet fillTestSheetWithOneQuestion (Question question) {
        TestSheet testJava = new TestSheet() ;
        testJava.setDuree(45);
        testJava.setIntitule("Tests pour tester l'implémentation");
        testJava.setType("Java");

        testJava.getQuestions().add(question) ;
        
        return testJava ; 
    }
    
    private Evaluation fillDumbEvaluation (Profil candidat, TestSheet testJava, Question question) {
    	Evaluation evaluation = new Evaluation() ;
        evaluation.setProfil(candidat);
        evaluation.setTest(testJava);

        Response reponse = new Response() ;
        reponse.setQuestion(question);
//        reponse.setContenu("un objet en serait un");

        Set<Response> reponses = new HashSet<Response>() ;
        reponses.add(reponse) ;
        evaluation.setResponses(reponses);
        
        return evaluation ; 
    }
}
