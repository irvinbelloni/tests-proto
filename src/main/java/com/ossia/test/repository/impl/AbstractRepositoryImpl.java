package com.ossia.test.repository.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.persistence.Transient;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ossia.test.repository.AbstractRepository;

public abstract class AbstractRepositoryImpl<T extends Serializable, PK extends Serializable> implements AbstractRepository<T, PK> {
	
	private final Log log = LogFactory.getLog(getClass()) ; 
	
	@Autowired
	private SessionFactory sessionFactory ; 
	
	@Transient
    private Class<T> type;
 
    @SuppressWarnings("unchecked")
	public AbstractRepositoryImpl() {
    	super();
    	type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
	
    public Session getHibernateCurrentSession () {
		try {
			return sessionFactory.getCurrentSession() ; 
		} catch (org.hibernate.HibernateException he) {
			log.warn(he.getMessage());		
			throw new RuntimeException("Probleme lors la recuperation de la session courante Hibernate");
		}
	}
	
	@PreDestroy
	public void destroyHibernateCurrentSession () {
		try {
			sessionFactory.getCurrentSession().close() ; 
		} catch (org.hibernate.HibernateException he) {
			log.warn(he.getMessage()); 
		}
	}
	
	@SuppressWarnings("unchecked")
	public PK create(final T newInstance) {
        return (PK) getHibernateCurrentSession().save(newInstance);
    }
 
    @SuppressWarnings("unchecked")
	public T getById(final PK instanceId) {
        return (T) getHibernateCurrentSession().get(type, instanceId);
    }
 
    @SuppressWarnings("unchecked")
	public List<T> getAll() {
        final Criteria crit = getHibernateCurrentSession().createCriteria(type);
        return crit.list();
    }
 
    public void update(final T object) {
    	getHibernateCurrentSession().update(object);
    }
 
    public void delete(final T object) {
    	getHibernateCurrentSession().delete(object);
    }

}
