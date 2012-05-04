package com.chris.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chris.hibernate.dao.base.GenericDAOImpl;
import com.chris.hibernate.model.Address;
import com.chris.util.HibernateUtil;

public class AddressDaoImpl{ // extends GenericDAOImpl<Address, Long> implements AddressDAO
	public static  SessionFactory sf = HibernateUtil.getSessionFactory();
	public void save2DB(Address add){
//		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(add);
		session.getTransaction().commit();
		session.close();
//		sf.close();
	}
	
	public Long getCount(){
//		SessionFactory sf = HibernateUtil.getSessionFactory();
		Session session = sf.openSession();
//		session.beginTransaction();
		
		String hql = "select COUNT(id) from Address";
    	Query query = session.createQuery(hql);
    	List<Long> list = query.list();
    	
//		session.getTransaction().commit();
		session.close();
//		sf.close();
		
		return list.get(0);

	}
}
