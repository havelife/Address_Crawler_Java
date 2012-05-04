package com.chris.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chris.hibernate.model.Fenye;
import com.chris.hibernate.model.Office;
import com.chris.util.HibernateUtil;

public class OfficeDaoImpl implements OfficeDao{ 
	public static  SessionFactory sf = HibernateUtil.getSessionFactory();
	public void save(Office office){
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(office);
		session.getTransaction().commit();
		session.close();
		System.out.println(office.getName() + " saved.");
	}
	
	public Long getCount(){
		Session session = sf.openSession();
		String hql = "select COUNT(id) from Office";
    	Query query = session.createQuery(hql);
    	List<Long> list = query.list();
		session.close();
		return list.get(0);
	}
}
