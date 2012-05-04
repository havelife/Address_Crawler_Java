package com.chris.service;

import java.util.ArrayList;
import java.util.List;

import com.chris.hibernate.dao.FenyeDao;
import com.chris.hibernate.dao.FenyeDaoImpl;
import com.chris.hibernate.model.Fenye;
import com.chris.parser.BjfdcSiteParser;
import com.chris.parser.FocusSiteParser;
import com.chris.parser.JdershouSiteParser;
import com.chris.parser.OfficeSiteParser;
import com.chris.util.FenyeUtil;
import com.chris.util.WebPageUtil;
import com.chris.vo.FenyeVo;

public class FenyeManagerImpl implements FenyeManager{
	public FenyeDao dao = new FenyeDaoImpl();
	
	public void saveWebpageByCity(String city, String prefix, int start, int end){
		FenyeUtil fyutil = new FenyeUtil(start, end, prefix);
		ArrayList<String> urlList = fyutil.generateUrls();
		for(String url:urlList){
			String content = WebPageUtil.getPageContentByUrl(url);
			String page = url.replace(prefix, ""); 
			Fenye fenye = new Fenye();
			fenye.setCity(city);
			fenye.setContent(content);
			fenye.setPage(page);
			fenye.setUrl(url);
			dao.save(fenye);
		}
	}

	public ArrayList<FenyeVo> initFenyeStore(){
		return new FocusSiteParser().initAllCityByFile();
	}
	
	public void saveWebpageBatch(ArrayList<FenyeVo> voList){
		for(FenyeVo fenyevo:voList){
			saveWebpageByCity(fenyevo.getCity(), fenyevo.getPrefix(), fenyevo.getStart(), fenyevo.getEnd());
			System.out.println(fenyevo.getCity() + " is finished #####################################");
		}
	}
	
	public List<Fenye> getAllByCity(String city){
		return dao.getAllByCity(city);
	}
	
	public Long getCount(){
		return dao.getCount();
	}
	/**
	 * 获取 Focus网站的所有分页，就是各个城市，所有房子分页的页面内容
	 * */
	public void saveFenyePage4FocusWholeProcedure(){
		ArrayList<FenyeVo> voList = initFenyeStore();
		saveWebpageBatch(voList);
	}
	
	
	/**
	 * 带有 4Bjfdc 的函数，都是为"北京房地产管理网站"设计的
	 * */
	public ArrayList<FenyeVo> initFenye4BjfdcStore(){
		return new BjfdcSiteParser().initAllCityByFile();
	}
	public void saveWebpageByCity4Bjfdc(String city, String prefix, int start, int end){
		FenyeUtil fyutil = new FenyeUtil(start, end, prefix);
		ArrayList<String> urlList = fyutil.generateUrls();
		//System.out.println(urlList);
		for(String url:urlList){
			String content = WebPageUtil.getPageContentByUrlWithReferer(url);
			System.out.println(content);
			String page = url.replace(prefix, ""); 
			Fenye fenye = new Fenye();
			fenye.setCity(city);
			fenye.setContent(content);
			fenye.setPage(page);
			fenye.setUrl(url);
			dao.save(fenye);
		}
	}
	/**
	 * 用于从网上获取 并把所有bjfdc网站的 分页的页面都存入数据库
	 * */
	public void saveFenyePage4BjfdcWholeProcedure(){
		ArrayList<FenyeVo> voList = initFenye4BjfdcStore();
		saveWebpageBatch(voList);
	}
	/**
	 * 用于从网上获取 并把所有bjfdc网站分页页面中出现的项目的 详细信息 全部获取 
	 * 并且存入数据库
	 * */
	public void saveDetailPage4BjfdcWholeProcedure(int x){
		ArrayList<FenyeVo> voList = initFenye4BjfdcStore();
		saveDetailpageBatch4Bjfdc(voList, x);
	}
	public void saveDetailpageBatch4Bjfdc(ArrayList<FenyeVo> voList,int x){
		for(FenyeVo fenyevo:voList){
			savePageDetailByCity4Bjfdc(fenyevo.getCity(), x);
			System.out.println(fenyevo.getCity() + " is finished #####################################");
		}
	}
	/**
	 * 就是bjfdc中的一种房屋类型的所有项目的url
	 * */
	public ArrayList<String> generateAllUrl4OneCity(String city){
		ArrayList<String> resList = new ArrayList<String>();
		ArrayList<Fenye> fenyeList = (ArrayList<Fenye>) getAllByCity(city);
		BjfdcSiteParser bParser = new BjfdcSiteParser();
		for(Fenye fenye : fenyeList){
			String content = fenye.getContent();
			resList.addAll(bParser.getAllProjectUrl(content));
		}
		return resList;
	}
	
	public void savePageDetailByCity4Bjfdc(String city, int x){
		//这个是为了和fenye_bjfdc中其他所有类型的数据区分
		String city4saveInDb = "详情页面#" + city; 
		ArrayList<String> urlList = generateAllUrl4OneCity(city);
		int i = 0;
		for(String url:urlList){          //这里一定要用withReferer的方法，这点很重要！！！
			//String content = WebPageUtil.getPageContentByUrlWithReferer(url);
			//检查下这个url在数据库中是否已经存在
			//不存在，才去抓
			System.out.println(++i + "/" + urlList.size());
			if(!dao.urlExist(url)){
				//这里是让间隔随机的 1~10秒钟去抓取页面。
				String content = WebPageUtil.getPageContentByUrlWithRefererAndRandomTime(url, x);
				Fenye fenye = new Fenye();
				
				fenye.setCity(city4saveInDb);
				fenye.setContent(content);
				fenye.setUrl(url);
				dao.save(fenye);
			}
		}
	}
	
	/**
	 * 对于一些由于反爬虫，而造成爬取内容为空的数据条，要根据url，重新爬取
	 * 我们根据每个类别来依次处理。在bjfdc的情况下，我们用city这个字段来存储类别信息
	 * */
	public void redownloadEmptyPages(String city){
		ArrayList<Fenye> fenyeList = (ArrayList<Fenye>) getAllByCity(city);
		for(Fenye fenye:fenyeList){
			if(fenye.getContent()==null || "".equals(fenye.getContent())){
				String url = fenye.getUrl();
				String content = WebPageUtil.getPageContentByUrlWithRefererAndRandomTime(url, 1);
				fenye.setContent(content);
				dao.update(fenye);
			}
		}
	}
	
	/**
	 * 房老大
	 * */
	public void saveFenyePage4Foloda(){
//		saveWebpageByCity("北京", "http://beijing.foloda.com/search/xiaoqusearch.aspx?p=", 1, 533);
		saveWebpageByCity("上海", "http://club.foloda.com/Html/V35/home.shtml#CityId=2&p=", 1, 904);
		
	}
	
	/**
	 * 写字楼网
	 * 这个函数是 那6个比较大的城市，有单独的二级域名的，我单独爬取了
	 * @return 
	 * */
	public void saveFenyePage4Xiezilou(){
//		saveWebpageByCity("北京", "http://beijing.chineseoffice.com.cn/Office/index.aspx?page=", 1, 84);
		//saveWebpageByCity("上海", "http://shanghai.chineseoffice.com.cn/Office/home.shtml#cityid=3&p=", 1, 63);
		//saveWebpageByCity("上海", "http://shanghai.chineseoffice.com.cn/Office/OfficeList.aspx?cityid=3&p=", 1, 63);
	    //saveWebpageByCity("天津", "http://tianjin.chineseoffice.com.cn/Office/home.shtml#cityid=2&p=", 1, 14);
//		saveWebpageByCity("天津", "http://tianjin.chineseoffice.com.cn/Office/OfficeList.aspx?cityid=2&p=", 1, 14);
		
//		saveWebpageByCity("广州", "http://guangzhou.chineseoffice.com.cn/Office/OfficeList.aspx?cityid=58&p=", 1, 15);
//		saveWebpageByCity("重庆", "http://chongqing.chineseoffice.com.cn/Office/OfficeList.aspx?cityid=7&p=", 1, 6);
//		saveWebpageByCity("重庆", "http://chongqing.chineseoffice.com.cn/Office/OfficeList.aspx?cityid=7&p=", 1, 6);
		saveWebpageByCity("成都", "http://chengdu.chineseoffice.com.cn/Office/OfficeList.aspx?cityid=32&p=", 1, 2);
	}
	
	/**
	 * 用于从网上获取 并把所有Office网站的 分页的页面都存入数据库
	 * 这里的分页是指它搜索口提供的 入口。
	 * */
	
	public void saveFenyePage4XiezilouWholeProcedure(){
		ArrayList<FenyeVo> voList = initFenye4XiezilouStore();
		saveWebpageBatch(voList);
	}
	
	public ArrayList<FenyeVo> initFenye4XiezilouStore(){
		return new OfficeSiteParser().initAllCityByFile();
	}
	
	public void savePageDetailByCity4Xiezilou(String city, int x, String encoding){
		 // 这个city 前面是已经有了"省#"这个前缀了的，在这里。
		// 这个是把抓回的页面详情最后存到数据库里时，和之前的数据区分。
		String city4saveInDb = "页面详情#" + city;
		ArrayList<String> urlList = generateAllUrl4OneCityOfXiezilou(city);
		for(String url:urlList){          //这里一定要用withReferer的方法，这点很重要！！！
			//String content = WebPageUtil.getPageContentByUrlWithReferer(url);
			//检查下这个url在数据库中是否已经存在
			//不存在，才去抓
			if(!dao.urlExist(url)){
				//这里是让间隔随机的 1~10秒钟去抓取页面。
				String content = WebPageUtil.getPageContentByUrlWithRefererAndRandomTime(url, x, encoding);
				//这句很重要，不然存到数据库都是乱码的，必须要这里转码一下。具体为什么别的页面就可以，唯独这个要转，我不是很清楚。
				//content = CharSetUtil.readStringWithSrcCharSet(content, "ISO-8859-1");
				Fenye fenye = new Fenye();
				
				fenye.setCity(city4saveInDb);
				fenye.setContent(content);
				fenye.setUrl(url);
				dao.save(fenye);
			}
		}
	}
	
	/**
	 * 就是Xiezilou中的一种房屋类型的所有项目的url
	 * */
	public ArrayList<String> generateAllUrl4OneCityOfXiezilou(String city){
		ArrayList<String> resList = new ArrayList<String>();
		ArrayList<Fenye> fenyeList = (ArrayList<Fenye>) getAllByCity(city);
		OfficeSiteParser oParser = new OfficeSiteParser();
		for(Fenye fenye : fenyeList){
			String content = fenye.getContent();
			resList.addAll(oParser.getAllProjectUrl(content));
		}
		return resList;
	}
	
	public void savePageDetail4Xiezilou(int x, String encoding){
		ArrayList<FenyeVo> voList = initFenye4XiezilouStore();
		for(FenyeVo fenye : voList){
			savePageDetailByCity4Xiezilou(fenye.getCity(), x, encoding);
		}
	}
	
	/**
	 * 下面是关于 焦点二手 的代码。以 4Jdershou 为后缀
	 * */
	public ArrayList<FenyeVo> initFenye4JdershouStore(){
		return new JdershouSiteParser().initAllCityByFile();
	}
	
	public void saveWebpageByCity4Jdershou(String city, String prefix, String suffix, int start, int end){
		FenyeUtil fyutil = new FenyeUtil(start, end, prefix, suffix);
		ArrayList<String> urlList = fyutil.generateUrls();
		for(String url:urlList){
			String content = WebPageUtil.getPageContentByUrl(url);
			String page = url.replace(prefix, "").replace(suffix, ""); 
			Fenye fenye = new Fenye();
			fenye.setCity(city);
			fenye.setContent(content);
			fenye.setPage(page);
			fenye.setUrl(url);
			dao.save(fenye);
		}
	}
	
	public void saveWebpageBatch4Jdershou(ArrayList<FenyeVo> voList){
		for(FenyeVo fenyevo:voList){
			saveWebpageByCity4Jdershou(fenyevo.getCity(), fenyevo.getPrefix(), fenyevo.getSuffix(), fenyevo.getStart(), fenyevo.getEnd());
			System.out.println(fenyevo.getCity() + " is finished #####################################");
		}
	}


	public void saveFenyePage4JdershouWholeProcedure(){
		ArrayList<FenyeVo> voList = initFenye4JdershouStore();
		saveWebpageBatch4Jdershou(voList);
	}
	
	public static void main(String[] args){
		System.out.println("Hello");
		FenyeManagerImpl fym = new FenyeManagerImpl();
//		ArrayList<FenyeVo> voList = fym.initFenyeStore();
//		fym.saveWebpageBatch(voList);
//		System.out.println(fym.getAllByCity("济南").size());
//		System.out.println(fym.getCount());

		//fym.saveDetailPage4BjfdcWholeProcedure();
		
		//fym.saveWebpageByCity("", "", 1, 533);
		//fym.saveFenyePage4Foloda();
		
//		fym.saveFenyePage4Xiezilou();
//		fym.saveFenyePage4XiezilouWholeProcedure();
//		fym.savePageDetail4Xiezilou(1, "");

		//fym.savePageDetailByCity4Bjfdc("预售商品房住宅");
		//fym.redownloadEmptyPages("详情页面#预售商品房住宅");
//		fym.savePageDetailByCity4Bjfdc("现售商品房住宅", 30);
//		fym.redownloadEmptyPages("详情页面#现售商品房住宅");
//		fym.savePageDetailByCity4Bjfdc("预售商品房非住宅", 30);
//		fym.savePageDetailByCity4Bjfdc("现售商品房非住宅项目", 30);
//		fym.redownloadEmptyPages("详情页面#现售商品房非住宅项目");
//		System.out.println("现售商品房非住宅项目 finished.");
//		fym.savePageDetailByCity4Bjfdc("经济适用住房、两限房", 30);
		fym.savePageDetailByCity4Bjfdc("期房退房", 30);
//		fym.savePageDetailByCity4Bjfdc("现房退房", 30);
//		fym.redownloadEmptyPages("详情页面#经济适用住房、两限房");
//		fym.redownloadEmptyPages("详情页面#期房退房");
//		fym.redownloadEmptyPages("详情页面#现房退房");
//		System.out.println("预售商品房非住宅 finished.");
		
//		fym.saveFenyePage4JdershouWholeProcedure();
		System.out.print("finished");
	}
}
