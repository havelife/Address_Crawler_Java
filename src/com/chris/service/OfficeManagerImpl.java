package com.chris.service;

import java.util.ArrayList;

import com.chris.hibernate.dao.OfficeDao;
import com.chris.hibernate.dao.OfficeDaoImpl;
import com.chris.hibernate.model.Fenye;
import com.chris.hibernate.model.Office;
import com.chris.parser.BjfdcSiteParser;
import com.chris.parser.FocusSiteParser;
import com.chris.vo.FenyeVo;

public class OfficeManagerImpl implements OfficeManager{
	public OfficeDao dao = new OfficeDaoImpl();
	
	public ArrayList<FenyeVo> initAllCity(){
		return new FocusSiteParser().initAllCityByFile();
	}
	
	public ArrayList<Office> parseByCity(String city){
		ArrayList<Office> resList = new ArrayList<Office>();
		FenyeManager fym = new FenyeManagerImpl();
		ArrayList<Fenye> fenyeList = (ArrayList<Fenye>) fym.getAllByCity(city);
		FocusSiteParser fsp = new FocusSiteParser();
		for(Fenye fenye : fenyeList){
			ArrayList<Office> officeList = fsp.getOfficeListByPageContent(fenye.getContent());
			System.out.println(fenye.getCity()+":"+fenye.getPage()+":"+"finished #########");
			for(Office office:officeList){
				office.setCity(city);
				resList.add(office);
			}
		}
		System.out.println(city + "parse finished ########");
		return resList;
	}
	
	public ArrayList<Office> parseAllCity(ArrayList<FenyeVo> cityList){
		ArrayList<Office> resList = new ArrayList<Office>();
		for(FenyeVo fenyevo : cityList){
			resList.addAll(parseByCity(fenyevo.getCity()));
		}
		return resList;
	}
	
	public void saveOfficeList(ArrayList<Office> officeList){
		for(Office office : officeList){
			dao.save(office);
		}
		System.out.println("all saved ###");
	}
	
	public void parseOfficeWholeProcedure(){
		ArrayList<FenyeVo> cityList = initAllCity();
		ArrayList<Office> officeList = parseAllCity(cityList);
		saveOfficeList(officeList);
		System.out.println("parseOfficeWholeProcedure finished ###");
	}
	
	/**
	 * bjfdc 弄自己独立的一套。这个上面都是focus的，下面关于bjfdc，单独弄一套。
	 * 
	 * */
	
	public ArrayList<FenyeVo> initAllCity4Bjfdc(){
		return new BjfdcSiteParser().initAllCityByFile();
	}
	
	public ArrayList<Office> parseAllCity4Bjfdc(ArrayList<FenyeVo> cityList){
		ArrayList<Office> resList = new ArrayList<Office>();
		for(FenyeVo fenyevo : cityList){
			resList.addAll(parseByCity4Bjfdc(fenyevo.getCity()));
		}
		return resList;
	}
	public ArrayList<Office> parseByCity4Bjfdc(String city){
		ArrayList<Office> resList = new ArrayList<Office>();
		FenyeManager fym = new FenyeManagerImpl();
		city = "详情页面#" + city;
		ArrayList<Fenye> fenyeList = (ArrayList<Fenye>) fym.getAllByCity(city);
		BjfdcSiteParser bParser = new BjfdcSiteParser();
		for(Fenye fenye : fenyeList){
			Office office = bParser.getOfficeByPageContent(fenye.getContent());//fsp.getOfficeListByPageContent(fenye.getContent());
			//office.setCity(city);
			if(office == null){office = new Office(); office.setName("null"); office.setAddr(fenye.getUrl());}
			resList.add(office);
		}
		System.out.println(city + "parse finished ########");
		return resList;
	}
	
	public void parseOfficeWholeProcedure4Bjfdc(){
		ArrayList<FenyeVo> cityList = initAllCity4Bjfdc();
		ArrayList<Office> officeList = parseAllCity4Bjfdc(cityList);
		saveOfficeList(officeList);
		System.out.println("parseOfficeWholeProcedure finished ###");
	}
	
	public static void main(String[] args){
		System.out.println("Hello");
		OfficeManagerImpl fym = new OfficeManagerImpl();
/*		Office office = new Office("dizhi", "gongyu", "beijing", "dianhua");
		fym.save(office);*/
		//System.out.println(fym.parseByCity("盘锦"));
//		fym.parseOfficeWholeProcedure();
		fym.parseOfficeWholeProcedure4Bjfdc();
		System.out.print("finished");
		
	}



	public void save(Office office) {
		// TODO Auto-generated method stub
		dao.save(office);
	}
}
