package com.chris.test;

import com.chris.hibernate.dao.AddressDaoImpl;
import com.chris.hibernate.model.Address;

public class AddressDAOImplTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AddressDaoImpl addDaoImpl = new AddressDaoImpl();
		
		System.out.println("count:" + addDaoImpl.getCount());
		
		Address address = new Address();
		address.setCity("Shandongjkkjjfkdsa");
		addDaoImpl.save2DB(address);
		System.out.println("completed!");
	}
}
