package org.idchavan.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractDAO {

	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
    public void saveOrUpdate(Object entity) {
    	getSession().saveOrUpdate(entity);
    }
 
    public void delete(Object entity) {
    	getSession().delete(entity);
    }
    
    public void clear(){
    	getSession().flush();
    	getSession().clear();
    	getSession().close();
    }
}
