package com.chris.parser;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chris.util.FileUtil;
import com.chris.util.StringUtil;
import com.chris.vo.FenyeVo;
//就是写字楼 www.office.com.cn 的那个网站
public class OfficeSiteParser {
	public ArrayList<FenyeVo> initAllCityByFile(){
		ArrayList<FenyeVo> resList = new ArrayList<FenyeVo>();
		String src = FileUtil.getDataFile2StrKeepReturn("./data/initAllCity4Xiezilou.txt", "UTF-8");
		src = src.substring(src.indexOf("#") + 1, src.indexOf("$"));
		String[] lines = src.split("\n");
		for(int i=1; i<lines.length; i++){
			String[] line = lines[i].split(":");
			resList.add(new FenyeVo("省#"+line[0], line[2]+ ":" + line[3], 1, Integer.parseInt(line[1])));
		}
		System.out.println("initAllCityByFile finished");
		return resList;
	}
	
	public ArrayList<String> getAllProjectUrl(String content){
		String urlStartWith = "http://www.chineseoffice.com.cn/zhongji/";
		ArrayList<String> resList = new ArrayList<String>();
		Document doc = Jsoup.parse(content);
		Elements base = doc.body().select("a");
		for(Element a: base){
			String href = a.attr("href");
			if(href.length() >= urlStartWith.length()) // 防止子串越界
				if(StringUtil.startWithStr(href, urlStartWith)) // 过滤杂质url
					if(!resList.contains(href)) //为了去除重复
						resList.add(href);
		}
		return resList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ArrayList<String> urlList = new OfficeSiteParser().getAllProjectUrl(FileUtil.getDataFile2Str("./data/pagecontentOfficeFenyeSample.txt", "UTF-8"));
		System.out.println(urlList);
	}

}
