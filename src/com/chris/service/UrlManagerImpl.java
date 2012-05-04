package com.chris.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chris.hibernate.dao.UrlDao;
import com.chris.hibernate.model.Url;
import com.chris.service.base.GenericManagerImpl;

@Service("urlManager")
public class UrlManagerImpl extends GenericManagerImpl<Url, Long> implements UrlManager{
	@Autowired
    private UrlDao urlDAO;

    public UrlManagerImpl() {

    }

    /*
     * @Autowired public BookPartManagerImpl(SessionFactory sessionFactory) { if
     * (bookPartDao == null) { this.dao = new GenericDaoHibernate<BookPart,
     * Long>(BookPart.class, sessionFactory); } else this.dao = bookPartDao; }
     */

    @Autowired
    public UrlManagerImpl(UrlDao urlDAO) {
	this.dao = urlDAO;
    }
}
