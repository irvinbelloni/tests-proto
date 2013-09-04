package com.ossia.test.repository.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepositoryImpl {
	
	private final Log log = LogFactory.getLog(getClass()) ; 
	
	@Autowired
	private SessionFactory sessionFactory ; 
	
	@PostConstruct
	public Session getHibernateCurrentSession () {
		Session sessionToreturn = null ; 
		try {
			sessionToreturn = sessionFactory.getCurrentSession() ; 
		} catch (org.hibernate.HibernateException he) {
			log.warn(he.getMessage()) ; 
			
			sessionToreturn = sessionFactory.openSession() ; 
		} 
		
		return sessionToreturn ; 
	}
	
	@PreDestroy
	public void destroyHibernateCurrentSession () {
		try {
			sessionFactory.getCurrentSession().close() ; 
		} catch (org.hibernate.HibernateException he) {
			log.warn(he.getMessage()) ; 
		}
	}

}
