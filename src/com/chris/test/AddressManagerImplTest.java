package com.chris.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.chris.hibernate.model.Address;
import com.chris.service.AddressManager;

public class AddressManagerImplTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ApplicationContext cx = new ClassPathXmlApplicationContext("applicationContext.xml");
		AddressManager am = (AddressManager) cx.getBean("addressManager");
		Address address = new Address();
		address.setCity("test");
		am.save(address);
	}

}
