package com.chris.service;

import java.util.ArrayList;

import com.chris.hibernate.dao.FenyeDaoImpl;
import com.chris.hibernate.model.Fenye;
import com.chris.util.FenyeUtil;
import com.chris.util.WebPageUtil;

public class BdlmManager {
	
	/*
	 * 存储220个页面，百度联盟贴吧的。
	 * */
	public static void fun1(){
		FenyeUtil fu = new FenyeUtil(1, 220, 50, "http://tieba.baidu.com/f?kw=%B0%D9%B6%C8%C1%AA%C3%CB&pn=");
		ArrayList<String> list = fu.generateUrlsWithStep();
		
		FenyeDaoImpl dao = new FenyeDaoImpl();
		int i = 0;
		for (String url : list)
		{
			String content = WebPageUtil.getPageContentByUrl(url);
			Fenye fenye = new Fenye();
			fenye.setContent(content);
			fenye.setUrl(url);
			fenye.setCity("bdlm");
			fenye.setPage(++i + "");
			dao.save(fenye);
		}
	}
	
	public static void main(String[] args){
		fun1();
//		String url =  "http://tieba.baidu.com/f?kw=%B0%D9%B6%C8%C1%AA%C3%CB&pn=500";
//		System.out.println(WebPageUtil.getPageContentByUrl(url));
		
	}
}
