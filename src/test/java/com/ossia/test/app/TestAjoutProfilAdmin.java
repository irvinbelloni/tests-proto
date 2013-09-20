package com.ossia.test.app;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ossia.test.domain.Profil;
import com.ossia.test.service.ProfilService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:/META-INF/config/applicationContext-*.xml"})
public class TestAjoutProfilAdmin {
	
	private final Log log = LogFactory.getLog(getClass()) ;

	@Autowired
    private ProfilService profilService ; 
    
    @Test
    public void testCreationProfilOK () {
    	Profil dumb = fillAdminProfil() ;
//    	dumb = profilService.createProfil(dumb, 0) ;
//    	log.debug("identifiant du profil créé : "+dumb.getId()) ; 
//    	Assert.assertNotNull(dumb) ; 
    }

    private Profil fillAdminProfil () {
        // creation de profil
        Profil admin = new Profil("DA SILVEIRA" , "Tata") ;
        admin.setLogin("admin") ; 
        admin.setPass("admin") ; 
        admin.setAdmin(Boolean.TRUE) ; 
        admin.setDateActivation(new Date()) ;
        admin.setEmail("dasilveira.tata@gmail.com"); // facultatif
        return admin ; 
    }
}
