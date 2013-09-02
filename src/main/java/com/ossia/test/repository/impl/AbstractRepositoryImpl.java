package com.ossia.test.repository.impl;

import javax.annotation.PostConstruct;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepositoryImpl {
	
	@Autowired
	private SessionFactory sessionFactory ; 
	
	@PostConstruct
	public Session getHibernateCurrentSession () {
		return sessionFactory.getCurrentSession() ; 
	}

}
