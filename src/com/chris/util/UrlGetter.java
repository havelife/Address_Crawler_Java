package com.chris.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.chris.hibernate.dao.UrlDaoImpl;
import com.chris.hibernate.model.Url;


public class UrlGetter {
	public static final int DivStartIndex = 1;
	public static final int DivEndIndex = 25;
	public static UrlDaoImpl urlDAO = new UrlDaoImpl();
	
	/**
	 * levelOrderWeb(String url) 方法里用到的变量 
	 */
	public static String c;
	public static LinkedList<String> cc = new LinkedList<String>();
	public static LinkedList<String> q = new LinkedList<String>();
	public static HashSet<String> urlStore = new HashSet<String>();
	public static List<Url> listSave2DB = new LinkedList<Url>();
	
	public static String startPoint;
	
	public static void main(String[] args) {
		System.out.println("Hello");
//		Long start = System.currentTimeMillis();
		UrlGetter t = new UrlGetter();
//		t.parseFile();
//		t.parseString();

//		t.parseAddressLiFile("http://beijing.anjuke.com/");


//		ArrayList<Address> addList = t.parseAddressByURL("http://beijing.anjuke.com/sale/daxing/p1/", "北京", "大兴");
//		arr2db(addList);
//		ArrayList<Address> addList = t.parseAddressByURL("http://nanjing.anjuke.com/community/list/", "北京", "大兴");
//		System.out.println("addList size: " + addList.size());
//		Long end = System.currentTimeMillis();
//		System.out.println("time consumption:" + (end - start));
//		JSoupTest.storeStart2EndIndex("", 1, 300, "beijing", "daxing");
		
//		t.parseUrl("http://www.baidu.com");

//		t.getUrlsInPage("http://beijing.anjuke.com/sale/daxing/p1/", ".anjuke.com", true);

		t.init4LevelOrder();
		t.levelOrderWeb("http://beijing.anjuke.com/sale/daxing/p1/");
	}

	public void parseString() {
		String html = "<html><head><title>blog</title></head><body onload='test()'><p>Parsed HTML into a doc.</p></body></html>";
		Document doc = Jsoup.parse(html);
		System.out.println(doc);
		Elements es = doc.body().getAllElements();
		System.out.println(es.attr("onload"));
		System.out.println(es.select("p"));
	}

	public void parseUrl() {
		 parseUrl("http://www.baidu.com");
	}
	
	public ArrayList<String> parseUrl(String url) {
		try {
			ArrayList<String> hrefList = new ArrayList<String>();
			Document doc = Jsoup.connect(url).get();
			Elements hrefs = doc.select("a[href]");
			
			System.out.println("hrefs size:" + hrefs.size());
			System.out.println(hrefs);
			System.out.println("------------------");
			System.out.println(hrefs.text());
			System.out.println("##################");
			hrefs = hrefs.select("[href^=http]");
			for(int i = 0; i<hrefs.size(); i++){
				String hrefStr = hrefs.get(i).attr("href");
				hrefList.add(hrefStr);
				System.out.println("@@@ " + hrefStr);
			}
			System.out.println(hrefs.attr("href"));
			System.out.println("******************");
//			System.out.println(hrefs.select("[href^=http]"));
			return hrefList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public LinkedList<String> getUrlsInPage(String url, String domain, boolean withinSite) {
		try {
			LinkedList<String> hrefList = new LinkedList<String>();
			Document doc = Jsoup.connect(url).timeout(10000).userAgent("Peking University").get();
			Elements hrefs = doc.select("a[href]");
			
//			System.out.println("hrefs size:" + hrefs.size());
//			System.out.println(hrefs);
//			System.out.println("------------------");
//			System.out.println(hrefs.text());
//			System.out.println("##################");
			hrefs = hrefs.select("[href^=http]");
			for(int i = 0; i<hrefs.size(); i++){
				String hrefStr = hrefs.get(i).attr("href");
//				System.out.println("@@@ " + hrefStr);
				if(withinSite){
					if(withinSite(hrefStr, domain))hrefList.add(hrefStr);
//					else System.out.println("OUT LINKE###:" + hrefStr);
				}else{
					hrefList.add(hrefStr);
				}
			}
//			System.out.println("******************");
			return hrefList;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public LinkedList<String> getUrlsInPage(String url) {
		return getUrlsInPage(url, "",  false);
	}
	
	public static boolean withinSite(String url, String domain){
		return url.contains(domain);
	}
	public void parseFile() {
		try {
			File input = new File("./data/add_beijing_daxin.txt"); //input.html
			Document doc = Jsoup.parse(input, "UTF-8");
			// 提取出所有的编号
			Elements codes = doc.body().select("td[title^=IA] > a[href^=javascript:view]");
			System.out.println(codes);
			System.out.println("------------------");
			System.out.println(codes.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseAddressFile() {
		try {
//			File input = new File("./data/add_beijing_daxin.txt"); //input.html
//			Document doc = Jsoup.parse(input, "UTF-8");
			Document doc = Jsoup.connect("http://beijing.anjuke.com/sale/daxing/p1/").get();
			// 提取出所有的编号
			//Elements codes = doc.body().select("td[title^=IA] > a[href^=javascript:view]");
			for(int i=DivStartIndex; i <= DivEndIndex; i++){
				Elements codes = doc.body().select("div[id^=prop_" + i + "]");
				Elements address = codes.select("address");
				System.out.println(i+":  size(" + address.size() +")");
				System.out.println(address.get(0));
				//System.out.println(address.html().replaceAll("&nbsp;", " "));
				System.out.println("#####################");
			}
//			Elements codes = doc.body().select("div[id^=prop_8]");
//			Elements address = codes.select("address");
//			System.out.println(codes);
//			System.out.println("#####################");
//			System.out.println(address);
//			System.out.println(address.html());
//			System.out.println("------------------###################################");
//			System.out.println(codes.html());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void parseAddressLiFile(String url) {
		try {
			Document doc;
			if(url==null) doc = Jsoup.connect("http://beijing.anjuke.com/sale/daxing/p1/").get();
				else  doc = Jsoup.connect(url).get();
			for(int i=1; i < 26; i++){
				Elements codes = doc.body().select("li[id~=^li_prop_" + i + "$]");
				Elements address = codes.select("address");
				System.out.println(i+":  size(" + codes.size() +")");
				System.out.println(address);
				System.out.println(address==null);
				System.out.println("#####################");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void levelOrderFile(File file){
		File c;
		File[] cc;
		ArrayList<File> q = new ArrayList<File>();
		if(file == null) return;
		c = file; q.add(c);
		while(q.size()!=0){
			c = q.remove(0);System.out.println(c.getAbsolutePath());
			cc = c.listFiles(); 
			if(cc != null)
				for(File tmp : cc)q.add(tmp);
		}
	}
	public void init4LevelOrder(){
		//这些页面上的url还有待采集
		List<Url> waitList= urlDAO.getAllByState("1");
		//所有已经已知的url
		List<Url> allList = urlDAO.getAllUrl();
		for(Url tmpUrl : waitList){
			q.add(tmpUrl.getUrl());
		}
		for(Url tmpUrl : allList){
			urlStore.add(tmpUrl.getUrl());
		}
		//if(q.size()!=0)startPoint = q.remove(0);
		System.out.println("Queue size:" + q.size() +"\n urlStore size:" + urlStore.size() + "\n initialization finished.");
	}
	public void levelOrderWeb(String url){
		System.out.println("levelOrderWeb start!");
		//if(startPoint != null && !startPoint.equals("")) url = startPoint;
		if(url == null) return;
		if(q.size()==0){
			c = url; q.add(c);urlStore.add(c);saveOrUpdateUrlWithState(c, "1");
		}
		while(q.size()!=0){
			//System.out.println("size:" + q.size());
			c = q.remove(0);
			cc = getUrlsInPage(c, ".anjuke.com", true); 
			if(cc != null){
				//updateUrlWithState(c, "2"); // 这个不应该在这里，应该在所有的cc都放进去了，你才应该update c， 这个就和数据库做日志类似。确定做了，再更新
				for(String urlTmp : cc){
					boolean alreadyHas = urlStore.contains(urlTmp);
					if(!alreadyHas){
						urlStore.add(urlTmp);
						q.add(urlTmp);
						//saveUrlWithState(urlTmp, "1");  // 这个性能不好，换下面的批量处理
						saveUrlWithStateInListWay(urlTmp, "1");
					}
				}
			}
			updateUrlWithState(c, "2");
//			System.out.println("slow down intensely by code start...");
//			Long start = System.currentTimeMillis();
//			for(Long l=0L; l<100000000; l++)
//				for(int i=0; i<10000; i++);
//			Long end = System.currentTimeMillis();
//			System.out.println("time slow down:" + (end - start));
		}
	}

	public static void displayHashMap(HashMap<String, String> srcHm){
		Iterator it = srcHm.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String, String> entry = (Map.Entry<String, String>)it.next();
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println("key:" + key + ", value:" + value);
		}
	}
	
	public static void saveOrUpdateUrlWithState(String url, String state){
		Url urlObj = urlDAO.getByUrl(url);
		if(urlObj==null){
			urlObj = new Url();
			urlObj.setUrl(url);
			urlObj.setState(state);
			urlDAO.save2DB(urlObj);
			System.out.println("saved: " + url);
		}else{
			urlObj.setState(state);
			//setCityAndDistrict(urlObj);
			urlDAO.update2DB(urlObj);
			System.out.println("updated: " + url);
		}
	}
	
	public static void saveUrlWithState(String url, String state){
		Url urlObj = new Url();
		urlObj.setUrl(url);
		urlObj.setState(state);
		urlDAO.save2DB(urlObj);
		System.out.println("saved: " + url);
	}
	
	public static void saveUrlWithStateInListWay(String url, String state){
		Url urlObj = new Url();
		urlObj.setUrl(url);
		urlObj.setState(state);
		listSave2DB.add(urlObj);
		//System.out.println("listSave2DB size:" + listSave2DB.size() + ", url:" + url);
		if(listSave2DB.size()%500 == 0){
			//System.out.println("count:" + urlDAO.getCount());
			urlDAO.saveList2DB(listSave2DB, urlDAO.getCount());
			listSave2DB.clear();
			System.out.println("500 adresses stored into db");
		}
	}
	
	public static void updateUrlWithState(String url, String state){
		Url urlObj = urlDAO.getByUrl(url);
		urlObj.setState(state);
		//setCityAndDistrict(urlObj);
		urlDAO.update2DB(urlObj);
		//System.out.println("updated: " + url);
	}
	
	public static Url setCityAndDistrict(Url url){
		String city, district;
		city = AddressParser.getCityInPage(url.getUrl());
		district = AddressParser.getDistrictInPage(url.getUrl());
		if(city != null)url.setHcity(city);
		if(district != null)url.setHdistrict(district);
		return url;
	}
}
