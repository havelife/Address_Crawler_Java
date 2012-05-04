package com.chris.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.chris.hibernate.model.Address;
import com.chris.util.AddressUtil;

public class JSoupTest {
	public static final int DivStartIndex = 1;
	public static final int DivEndIndex = 25;
	public static void main(String[] args) {
		Long start = System.currentTimeMillis();
		JSoupTest t = new JSoupTest();
//		t.parseFile();
//		t.parseString();
//		t.parseAddressLiFile();
//		ArrayList<Address> addList = t.parseAddressByURL("http://beijing.anjuke.com/sale/daxing/p1/", "北京", "大兴");
//		arr2db(addList);
//		ArrayList<Address> addList = t.parseAddressByURL("http://nanjing.anjuke.com/community/list/", "北京", "大兴");
//		System.out.println("addList size: " + addList.size());
//		Long end = System.currentTimeMillis();
//		System.out.println("time consumption:" + (end - start));
//		JSoupTest.storeStart2EndIndex("", 1, 300, "beijing", "daxing");
		
		t.parseUrl("http://www.baidu.com");
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
	
	public void parseUrl(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements hrefs = doc.select("a[href]");
			
			System.out.println("hrefs size:" + hrefs.size());
			System.out.println(hrefs);
			System.out.println("------------------");
			System.out.println(hrefs.text());
			System.out.println("##################");
//			for(Element elem : hrefs){
//				
//			}
			System.out.println(hrefs.attr("href"));
			System.out.println("******************");
			System.out.println(hrefs.select("[href^=http]"));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
	
	public void parseAddressLiFile() {
		try {
			Document doc = Jsoup.connect("http://beijing.anjuke.com/sale/daxing/p1/").get();
			for(int i=1; i < 26; i++){
				Elements codes = doc.body().select("li[id~=^li_prop_" + i + "$]");
				Elements address = codes.select("address");
				System.out.println(i+":  size(" + codes.size() +")");
				System.out.println(address);
				System.out.println("#####################");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<Address> parseAddressByURL(String url, String city, String district) {
		ArrayList res = new ArrayList<Address>();
		try {
			Document doc = Jsoup.connect(url).get();
			for(int i=1; i < 26; i++){
				Elements codes = doc.body().select("li[id~=^li_prop_" + i + "$]");
				Elements address = codes.select("address");
				String addstr = address.html().replaceAll("(&nbsp;)\\1", "#");
				System.out.println("addStr:" + addstr);
				String[] arr = addstr.split("#");
				if(AddressUtil.addressExist(arr)){
					Address add = new Address();
					add.setCity(city);
					add.setDistrict(district);	
					add.setBigadd(arr[0]);
					add.setSmladd(arr[1]);
					res.add(add);
					//System.out.println(add);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	public static void arr2db(ArrayList<Address> addList){
		for(Address add : addList){
			AddressTest.saveAddress(add);
		}
	}
	
	public static void storeStart2EndIndex(String url, int start, int end, String city, String district){
		for(int i=start; i<=end; i++){
			Long startTime = System.currentTimeMillis();
			ArrayList<Address> addList = JSoupTest.parseAddressByURL("http://"+ city + ".anjuke.com/sale/"+district+"/p"+ i +"/", "北京", "大兴");
			Long endTime = System.currentTimeMillis();
			System.out.println("page:"+ i +", time consumption:" + (endTime - startTime));
			arr2db(addList);
		}
		System.out.println("storeStart2EndIndex completed");
	}
}
