package com.chris.util;

import java.io.IOException;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class WebPageUtil {
	public static String getPageContentByUrl(String url){
		try {
			Document doc;
			doc = Jsoup.connect(url).timeout(80000).get();
			//doc.
			return doc.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getPageContentByUrlWithReferer(String url){
		HttpClient client = new HttpClient();
        //设置代理服务器地址和端口        
        //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        //使用GET方法，如果服务器需要通过HTTPS连接，那只需要将下面URL中的http换成https
        HttpMethod method = new GetMethod(url);
        method.setRequestHeader("Referer", url);
        //使用POST方法
        //HttpMethod method = new PostMethod("http://java.sun.com");
        try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //打印服务器返回的状态
//        System.out.println(method.getStatusLine());
        //打印返回的信息
        String res = null;
		try {
			res = method.getResponseBodyAsString();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //释放连接
        method.releaseConnection();
        return res;
	}
	
	public static String getPageContentByUrlWithReferer(String url, String encoding){
		HttpClient client = new HttpClient();
        //设置代理服务器地址和端口        
        //client.getHostConfiguration().setProxy("proxy_host_addr",proxy_port);
        //使用GET方法，如果服务器需要通过HTTPS连接，那只需要将下面URL中的http换成https
        HttpMethod method = new GetMethod(url);
        method.setRequestHeader("Referer", url);
        //使用POST方法
        //HttpMethod method = new PostMethod("http://java.sun.com");
        try {
			client.executeMethod(method);
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //打印服务器返回的状态
//        System.out.println(method.getStatusLine());
        //打印返回的信息
        String res = null;
		try {
			res = new String(method.getResponseBodyAsString().getBytes(encoding));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //释放连接
        method.releaseConnection();
        return res;
	}
	
	public static String getPageContentByUrlWithRefererAndRandomTime(String url, int seconds){
		try {
			Thread.sleep(1000 * RandomUtil.getOne2XRandomNum(seconds));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPageContentByUrlWithReferer(url);
	}
	
	public static String getPageContentByUrlWithRefererAndRandomTime(String url, int seconds, String encoding){
		try {
			Thread.sleep(1000 * RandomUtil.getOne2XRandomNum(seconds));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return getPageContentByUrlWithReferer(url, encoding);
	}
	
	//for test
	public static void main(String[] args){
		/*
		String pageContent;
		pageContent = WebPageUtil.getPageContentByUrl("http://house.focus.cn/housemarket/house_search/index.php?page=153");
		FileUtil.writeStr2File(pageContent, "./data/pagecontent.txt");
		System.out.println(pageContent);
		*/
		try {
		for(int i=0; i<10; i++){
			Thread.sleep(3000);
			System.out.println(i);
		}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
