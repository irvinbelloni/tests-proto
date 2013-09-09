package com.ossia.test.app;

import java.util.Arrays;
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
import com.ossia.test.domain.Niveau;
import com.ossia.test.domain.Profil;
import com.ossia.test.domain.PropositionReponse;
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
    public void testCreationProfilOK () {
    	Profil dumb = fillDumbProfil() ;
    	dumb = profilService.createProfil(dumb) ;
    	log.debug("identifiant du profil créé : "+dumb.getId()) ; 
    	Assert.assertNotNull(dumb) ; 
    }

    private Profil fillDumbProfil () {
        // creation de profil
        Profil candidat = new Profil("Tset" , "prenom") ;
        candidat.setEmail("candidat@test.com"); // facultatif
        return candidat ; 
    }
    
    @Test
    @Transactional
    public void testCreationTestSheetOK () {
    	
    	TestSheet t0 = fillTestSheetWithQuestions() ; 
    	t0 = testSheetService.createTestSheet(t0) ; 
    	
    	Assert.assertNotNull(t0.getId()) ; 
    	log.debug("test numero "+t0.getId()) ; 
    	Assert.assertEquals(t0.getQuestions().size(), 2) ; 
    	
    	for (Question ques : t0.getQuestions()) {
			
    		Assert.assertNotNull(ques.getId()) ;
    		log.debug("question numero "+t0.getId()) ; 
    		Assert.assertEquals(ques.getPropositionsReponses().size(), 3) ;
    		
    		for (PropositionReponse iterable_element : ques.getPropositionsReponses()) {
    			Assert.assertNotNull(iterable_element.getId()) ; 
    			log.debug("proposition numero "+iterable_element.getId()) ; 
    			log.debug("proposition valeur "+iterable_element.getValeur()) ;
			}
		}
    }
    
    private Question fillDumbQuestion0 (TestSheet testJava) {
    	Question question = new Question("Qu'est-ce qu'un constructeur ? " , Niveau.INTERMEDIAIRE.toString() , null) ;
        question.setTest(testJava) ; 
    	PropositionReponse pr0 = new PropositionReponse(question, "Une classe de base dont héritent toutes les classes de l'application.", Boolean.FALSE) ; 
    	PropositionReponse pr1 = new PropositionReponse(question, "Une fonction permettant de créer dynamiquement des instances.", Boolean.FALSE) ;
    	PropositionReponse pr2 = new PropositionReponse(question, "Une méthode appelée automatiquement lors de la création d'un objet.", Boolean.TRUE) ;
    	
    	PropositionReponse[] prs = {pr0 , pr1 , pr2 } ; 
    	Set<PropositionReponse> listPr = new HashSet<PropositionReponse>(Arrays.asList(prs)) ; 
    	question.setPropositionsReponses(listPr) ; 
        return question ; 
    }
    
    private Question fillDumbQuestion1 (TestSheet testJava) {
    	Question question = new Question("Dans une relation d'héritage, certains membres de la classe mère sont inaccessibles à la classe fille. Ce sont :" , Niveau.INTERMEDIAIRE.toString() , null) ;
    	question.setTest(testJava) ; 
    	PropositionReponse pr0 = new PropositionReponse(question, "Les membres protected", Boolean.FALSE) ; 
    	PropositionReponse pr1 = new PropositionReponse(question, "Les membres private", Boolean.FALSE) ;
    	PropositionReponse pr2 = new PropositionReponse(question, "Les membres protected et private", Boolean.TRUE) ;
    	
    	PropositionReponse[] prs = {pr0 , pr1 , pr2 } ; 
    	Set<PropositionReponse> listPr = new HashSet<PropositionReponse>(Arrays.asList(prs)) ; 
    	question.setPropositionsReponses(listPr) ; 
        return question ; 
    }
    
    private TestSheet fillTestSheetWithQuestions () {
        TestSheet testJava = new TestSheet() ;
        testJava.setDuree(45);
        testJava.setIntitule("Tests Java - niveau Intermédiare");
        testJava.setType("Java");

        Set<Question> questions = new HashSet<Question> ();
        questions.add(fillDumbQuestion0(testJava)) ;
        questions.add(fillDumbQuestion1(testJava)) ; 
		testJava.setQuestions(questions);
        
        return testJava ; 
    }
    
    @Test
    @Transactional
    public void testCreationEvaluation () {
    	
    	Profil dumb = fillDumbProfil() ;
    	dumb = profilService.createProfil(dumb) ;
    	
    	TestSheet t0 = fillTestSheetWithQuestions() ; 
    	t0 = testSheetService.createTestSheet(t0) ; 
    	
    	Evaluation eval = fillDumbEvaluation(dumb, t0)  ; 
    	eval = evaluationService.createEvaluation(eval) ;
    	
    	Assert.assertNotNull(eval) ; 
    }
    
    private Evaluation fillDumbEvaluation (Profil candidat, TestSheet testJava) {
        Set<Response> reponses = fillDumbResponses (testJava.getQuestions())  ; 
        
        Evaluation evaluation = new Evaluation(testJava , candidat, reponses) ;
        return evaluation ; 
    }

	private Set<Response> fillDumbResponses(Set<Question> questions) {
		Set<Response> rs = new HashSet<Response>() ; 
		
		for (Question question : questions) {
			PropositionReponse pr = null ; 
			// choix de la première occurence lors de la lecture 
			for (PropositionReponse pr0 : question.getPropositionsReponses()) {
				pr = pr0 ; 
				break ; 
			}
			Response e = new Response(question , pr );
			rs.add(e ) ; 
		}
		return rs ;
	}
}
