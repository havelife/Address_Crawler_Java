package com.chris.parser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chris.hibernate.model.Office;
import com.chris.util.FileUtil;
import com.chris.util.NumberUtil;
import com.chris.vo.FenyeVo;

public class FocusSiteParser {
	
	public String getName(Element rinfo){
		String name = rinfo.select("div[class~=^h3 clear$]").select("a").get(1).text().trim();
		return name;
	}
	
	public String getPhone(Element rinfo){
		String phone = null;
		Elements phonebase = rinfo.select("div[class~=^phone$]");
		if(phonebase.hasText()){
			phone = phonebase.select("b[class~=^org$]").text().trim();
		}					
						//增加这个大于1，也就是至少2，是因为下面那个取子串的函数报过错
		                //愿意就是当时那个phone长度为1，你index无法取到1.
		if(phone != null && phone.length()>1)
			// 下面是为了排除那种类似"经核实，电话已取消"的数据
			if(!NumberUtil.charIsNumber(phone.substring(0,1)))return null;
		
		return phone;
	}
	
	public String getAddress(Element rinfo){
		String address = null;
		address = rinfo.select("p").text();
		String tmpStr = address.split("：")[1];
		System.out.println(tmpStr);
		//if(tmpStr.length() < 10)
		if(tmpStr.length() >= 10)
			address = tmpStr.substring(0, tmpStr.length()-10).trim();
		return address;
	}
	
	public String getPrice(Element rinfo){
		String price = rinfo.select("div[class~=^h3 clear$]").select("div[class~=^price$]").select("b[class~=^red$]").text().trim();
		return price;
	}
	public Office getOfficeByElement(Element rinfo){
		Office office = new Office();
		office.setName(getName(rinfo));
		office.setTel(getPhone(rinfo));
		office.setAddr(getAddress(rinfo));
		return office;
	}
	
	public ArrayList<Office> getOfficeListByUrl(String url){
		ArrayList<Office> resList = new ArrayList<Office>();
		try {
			String content = Jsoup.connect(url).timeout(4000).get().html();
			resList = getOfficeListByPageContent(content);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resList;
	}
	
	public ArrayList<Office> getOfficeListByPageContent(String content){
		ArrayList<Office> resList = new ArrayList<Office>();
			Document doc = Jsoup.parse(content);
			Elements base = doc.body().select("div[class~=^listBox$]");
			if(base.hasText()){
				Elements ul = base.select("ul");
				if(ul.hasText()){
					Elements lis = ul.select("li");
					if(lis.hasText()){
						for(Element li : lis){
							Element rinfo = li.select("div[class~=^rinfo$]").get(0);
							resList.add(getOfficeByElement(rinfo));
						}
					}
				}
			}
		return resList;
	}
	/**
	 * 从focusSiteMap.txt 获取所有城市的首页url，最大分页，城市名信息，并且会把结果写到initAllCity.txt
	 * */
	public ArrayList<FenyeVo> initAllCityByWeb(){
		ArrayList<FenyeVo> resList = new ArrayList<FenyeVo>();
		StringBuffer sb = new StringBuffer();
		try {
			Document doc = Jsoup.parse(new File("./data/focusSiteMap.txt"), "UTF-8");
			Elements base = doc.body().select("div[id~=^MATjiameng_sitemap_left$]");
			base = base.select("p");
			for(Element p:base){
				Elements cities = p.select("a");
				for(Element city:cities){
					String cityName = city.text();
					String prefix = city.attr("href");
					int end = Integer.parseInt(getMaxPageNum(prefix));
					prefix += "/housemarket/house_search/index.php?page=";
					System.out.println("FocusSiteParser.initAllCity(): " + cityName + ":" + end + ":" + prefix);
					sb.append(cityName+":"+end+":"+prefix+"\n");
					resList.add(new FenyeVo(cityName, prefix, 1, end));
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FileUtil.writeStr2File(sb.toString(), "./data/initAllCity.txt");
		return resList;
	}
	
	public ArrayList<FenyeVo> initAllCityByFile(){
		ArrayList<FenyeVo> resList = new ArrayList<FenyeVo>();
		String src = FileUtil.getDataFile2StrKeepReturn("./data/initAllCity.txt", "UTF-8");
		src = src.substring(src.indexOf("#") + 1, src.indexOf("$"));
		String[] lines = src.split("\n");
		for(int i=1; i<lines.length; i++){
			String[] line = lines[i].split(":");
			resList.add(new FenyeVo(line[0], line[2]+ ":" + line[3], 1, Integer.parseInt(line[1])));
		}
		System.out.println("initAllCityByFile finished");
		return resList;
	}
	
	public String getMaxPageNum(String cityHomePageUrl){
		String max=null;
		try {
			cityHomePageUrl += "/housemarket/house_search/index.php";
			Document doc = Jsoup.connect(cityHomePageUrl).timeout(40000).get();
			Element base = doc.body().select("a[alt~=^尾页$]").get(0);
			max = base.attr("href").replace("/housemarket/house_search/index.php?page=", "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return max;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FocusSiteParser fsp = new FocusSiteParser();
		/*ArrayList<Office> officeList = fsp.getOfficeListByUrl("http://house.focus.cn/housemarket/house_search/index.php?page=151");
		System.out.println(officeList);*/
		//fsp.initAllCity();
		System.out.println(fsp.initAllCityByFile());
		//System.out.println(fsp.initAllCity());
	}
}
