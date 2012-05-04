package com.chris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.hibernate.dao.AddressDao;
import com.chris.hibernate.model.Address;
import com.chris.service.base.GenericManagerImpl;

@Service("addressManager")
public class AddressManagerImpl extends GenericManagerImpl<Address, Long> implements AddressManager{
    @Autowired
    private AddressDao addressDAO;

    public AddressManagerImpl() {

    }

    /*
     * @Autowired public BookPartManagerImpl(SessionFactory sessionFactory) { if
     * (bookPartDao == null) { this.dao = new GenericDaoHibernate<BookPart,
     * Long>(BookPart.class, sessionFactory); } else this.dao = bookPartDao; }
     */

    @Autowired
    public AddressManagerImpl(AddressDao addDAO) {
	this.dao = addDAO;
    }
}
