package com.chris.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chris.hibernate.model.Fenye;
import com.chris.hibernate.model.Url;
import com.chris.util.HibernateUtil;

public class FenyeDaoImpl  implements FenyeDao{ 
	public static  SessionFactory sf = HibernateUtil.getSessionFactory();
	public void save(Fenye fenye){
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(fenye);
		session.getTransaction().commit();
		session.close();
		System.out.print("content size:" + fenye.getContent().length() + " ");
		System.out.println(fenye.getUrl() + " saved.");
	}
	
	public void update(Fenye fenye){
		Session session = sf.openSession();
		session.beginTransaction();
		session.update(fenye);
		session.getTransaction().commit();
		session.close();
		System.out.print("id: " + fenye.getId() + " content size:" + fenye.getContent().length() + " ");
		System.out.println(fenye.getUrl() + " updated.");
	}
	
	public Long getCount(){
		Session session = sf.openSession();
		String hql = "select COUNT(id) from Fenye";
    	Query query = session.createQuery(hql);
    	List<Long> list = query.list();
		session.close();
		return list.get(0);
	}
	
	public List<Fenye> getAllByCity(String city){
		Session session = sf.openSession();
		String hql = "from Fenye where city='" + city + "'";
    	Query query = session.createQuery(hql);
    	List<Fenye> list = query.list();
		session.close();
		return list;
	}
	
	public boolean urlExist(String url){
		Session session = sf.openSession();
		String hql = "from Fenye where url='" + url + "'";
    	Query query = session.createQuery(hql);
    	List<Fenye> list = query.list();
		session.close();
		return list.size()>0;
	}
	
	public List<Fenye> getAll(){
		Session session = sf.openSession();
		String hql = "from Fenye";
    	Query query = session.createQuery(hql);
    	List<Fenye> list = query.list();
		session.close();
		return list;
	}
}
