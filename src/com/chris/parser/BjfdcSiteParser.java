package com.chris.parser;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chris.hibernate.model.Office;
import com.chris.util.FileUtil;
import com.chris.util.StringUtil;
import com.chris.vo.FenyeVo;

public class BjfdcSiteParser {
	public static String suffixStr = "项目信息";
	public static String city = "北京";
	
	public ArrayList<FenyeVo> initAllCityByFile(){
		ArrayList<FenyeVo> resList = new ArrayList<FenyeVo>();
		String src = FileUtil.getDataFile2StrKeepReturn("./data/initAllCity4Bjfdc.txt", "UTF-8");
		src = src.substring(src.indexOf("#") + 1, src.indexOf("$"));
		String[] lines = src.split("\n");
		for(int i=1; i<lines.length; i++){
			String[] line = lines[i].split(":");
			resList.add(new FenyeVo(line[0], line[2]+ ":" + line[3], 1, Integer.parseInt(line[1])));
		}
		System.out.println("initAllCityByFile finished");
		return resList;
	}
	
	public ArrayList<String> getAllProjectUrl(String content){
		ArrayList<String> resList = new ArrayList<String>();
		String urlBase = "http://www.bjfdc.gov.cn/public/house/";
		Document doc = Jsoup.parse(content);
		Elements base = doc.body().select("a");
		for(Element a: base){
			String href = a.attr("href");
			if(href.length() >= "project.asp?id=".length()) // 防止子串越界
				if(StringUtil.startWithStr(href, "project.asp?id=")) // 过滤杂质url
					if(!resList.contains(urlBase + href)) //为了去除重复
						resList.add(urlBase + href);
		}
		return resList;
	}
	
	public Office getOfficeByPageContent(String content){
		ArrayList<Office> resList = new ArrayList<Office>();
			Document doc = Jsoup.parse(content);
			Elements base = doc.body().select("table[class~=^line18$]");
			if(base.hasText()){
				for(Element table : base){
					Elements trs = table.select("tbody").select("tr");
					if(trs.size()>2){
						Boolean isTargetArea = StringUtil.endWithStr(trs.get(0).select("td").get(0).text(), suffixStr);
						if(isTargetArea){
							String name = trs.get(1).select("td").get(1).text();
							String addr = trs.get(2).select("td").get(1).text();
							Office office = new Office();
							office.setCity(city);
							office.setName(name);
							office.setAddr(addr);
							System.out.println("##名称:" + name + "   地址:" + addr);
							return office;
						}
					}
				}
				//System.out.println(base.html());
			}
		return null;
	}
	
	
	public static void main(String[] args){
		/*
		ArrayList<String> urlList = new BjfdcSiteParser().getAllProjectUrl(FileUtil.getDataFile2Str("./data/pagecontentBjfdcFenyeSample.txt", "UTF-8"));
		System.out.println(urlList.size());
		*/
		String content = FileUtil.getDataFile2Str("./data/pageDetailContentBjfdcSample.txt", "utf-8");
		BjfdcSiteParser bParser = new BjfdcSiteParser();
		bParser.getOfficeByPageContent(content);
	}
}
