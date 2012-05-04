package com.chris.hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.chris.hibernate.model.Url;
import com.chris.util.HibernateUtil;

public class UrlDaoImpl{  //  extends GenericDAOImpl<Url, Long> implements UrlDAO
	public static  SessionFactory sf = HibernateUtil.getSessionFactory();
	
	public void save2DB(Url url){
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(url);
		session.getTransaction().commit();
		session.close();
	}
	
	public void saveList2DB(List<Url> urlList, Long index){
		Session session = sf.openSession();
		session.beginTransaction();
		for ( int i=0; i<urlList.size(); i++ ) {
			Url url; //= new Url();
			url = urlList.get(i);
			//System.out.println(index + i + 1);
			url.setId(index + i + 2);
//			System.out.println("i:" + i + ", " + url);
			session.save(url);
			if ( i % 20 == 0 ) {
			//flush 插入数据和释放内存:
			session.flush(); session.clear(); 
			}
		}
		session.getTransaction().commit();
		session.close();
	}
	
	public void update2DB(Url url){
		Session session = sf.openSession();
		session.beginTransaction();
		session.update(url);
		session.getTransaction().commit();
		session.close();
	}
	
	public List<Url> getAllByState(String state){
		Session session = sf.openSession();
		String hql = "from Url where state='" + state + "'";
    	Query query = session.createQuery(hql);
    	List<Url> list = query.list();
		session.close();
		return list;
	}
	
	public List<Url> getAllUrl(){
		Session session = sf.openSession();
		String hql = "from Url";
    	Query query = session.createQuery(hql);
    	List<Url> list = query.list();
		session.close();
		return list;
	}
	
	public Url getByUrl(String url){
		Session session = sf.openSession();
		String hql = "from Url where url='" + url + "'";
    	Query query = session.createQuery(hql);
    	List<Url> list = query.list();
		session.close();
		if(list==null || list.size()==0) return null;
		return list.get(0);
	}
	
	public Long getCount(){
		Session session = sf.openSession();
		String hql = "select COUNT(id) from Url";
    	Query query = session.createQuery(hql);
    	List<Long> list = query.list();
		session.close();
		return list.get(0);
	}
	
	public List<Url> getByPagination(int pageNo, int pageSize, String parsed) {
		Session session = sf.openSession();
    	String hql = "from Url where parsed ='" + parsed +"'";
    	Query query = session.createQuery(hql);
    	int firstResultIndex=pageSize*(pageNo-1);
    	query.setFirstResult(firstResultIndex);
    	query.setMaxResults(pageSize);
    	List<Url> list = query.list();
    	session.clear();
    	return list;
	}
}
