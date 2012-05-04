package com.chris.service;

import java.util.ArrayList;
import java.util.List;

import com.chris.hibernate.model.Fenye;
import com.chris.vo.FenyeVo;

public interface FenyeManager {
	public void saveWebpageByCity(String city, String prefix, int start, int end);
	ArrayList<FenyeVo> initFenyeStore();
	public List<Fenye> getAllByCity(String city);
}
