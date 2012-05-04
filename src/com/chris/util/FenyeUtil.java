package com.chris.util;

import java.util.ArrayList;

public class FenyeUtil {
	private int start;
	private int end;
	private int step;
	private String urlPrefix;
	private String urlSuffix;
	public FenyeUtil(int start, int end, String urlPrefix){
		this.start = start;
		this.end = end;
		this.urlPrefix = urlPrefix;
		this.urlSuffix = "";
	}
	
	public FenyeUtil(int start, int end, int step, String urlPrefix){
		this(start, end, urlPrefix);
		this.step = step;
	}
	
	public FenyeUtil(int start, int end, String urlPrefix, String urlSuffix){
		this.start = start;
		this.end = end;
		this.urlPrefix = urlPrefix;
		this.urlSuffix = urlSuffix;
	}
	
	public ArrayList<String> generateUrls(){
		ArrayList<String> resList = new ArrayList<String>();
		for(int i=start; i<=end; i++){
			resList.add(urlPrefix + i + urlSuffix);
		}
		return resList;
	}
	
	public ArrayList<String> generateUrlsWithStep(){
		ArrayList<String> resList = new ArrayList<String>();
		for(int i=start; i<=end; i++){
			resList.add(urlPrefix + (i * step) + urlSuffix);
		}
		return resList;
	}
	//for test
	public static void main(String[] args){
		FenyeUtil util = new FenyeUtil(1, 153, 20, "http://house.focus.cn/housemarket/house_search/index.php?page=");
	    System.out.println(util.generateUrlsWithStep());
	}
}
