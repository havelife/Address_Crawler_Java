package com.chris.hibernate.dao;

import java.util.List;

import com.chris.hibernate.model.Fenye;


public interface FenyeDao{
	public void save(Fenye fenye);
	public Long getCount();
	public List<Fenye> getAllByCity(String city);
	public List<Fenye> getAll();
	public void update(Fenye fenye);
	public boolean urlExist(String url);
}
