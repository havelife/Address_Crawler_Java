package com.chris.hibernate.dao;

import com.chris.hibernate.model.Office;


public interface OfficeDao{
	public void save(Office office);
	public Long getCount();
}
