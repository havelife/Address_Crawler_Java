package com.chris.test;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.chris.hibernate.model.Address;
import com.chris.util.HibernateUtil;

public class AddressTest {
	public static  SessionFactory sf = HibernateUtil.getSessionFactory();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Address add = new Address();
		//add.setId(1);
		add.setBigadd("枣园%%%");
		add.setSmladd("xx街xxx号码");
		add.setDistrict("大兴");
		add.setCity("北京");
		
		Configuration cfg = new Configuration();
		SessionFactory sf = cfg.configure().buildSessionFactory();
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(add);
		session.getTransaction().commit();
		session.close();
		sf.close();
		System.out.println("finished");
	}
	
	public static void saveAddress(Address add){
		Session session = sf.openSession();
		session.beginTransaction();
		session.save(add);
		session.getTransaction().commit();
		session.close();
		//sf.close();
	}
}
