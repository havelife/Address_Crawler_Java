package com.chris.service;

import java.util.ArrayList;

import com.chris.hibernate.dao.FenyeDaoImpl;
import com.chris.hibernate.model.Fenye;
import com.chris.util.FenyeUtil;
import com.chris.util.WebPageUtil;

public class TblmMgr {
		/*
		 * 存储2000个页面      淘宝客交流        淘宝联盟。
		 * */
		public static void fun1(){
			FenyeUtil fu = new FenyeUtil(1, 2000, "http://club.alimama.com/thread-htm-fid-68-page-", ".html");
			ArrayList<String> list = fu.generateUrls();

			FenyeDaoImpl dao = new FenyeDaoImpl();
			int i = 0;
			for (String url : list)
			{
				String content = WebPageUtil.getPageContentByUrl(url);
				Fenye fenye = new Fenye();
				fenye.setContent(content);
				fenye.setUrl(url);
				fenye.setCity("淘宝客交流");
				fenye.setPage(++i + "");
				dao.save(fenye);
			}
		}
		
		public static void main(String[] args){
			fun1();
//			String url =  "http://tieba.baidu.com/f?kw=%B0%D9%B6%C8%C1%AA%C3%CB&pn=500";
//			System.out.println(WebPageUtil.getPageContentByUrl(url));
			
		}

}
