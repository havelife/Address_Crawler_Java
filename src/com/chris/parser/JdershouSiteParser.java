package com.chris.parser;

import java.util.ArrayList;

import com.chris.util.FileUtil;
import com.chris.vo.FenyeVo;

public class JdershouSiteParser {
	
	public ArrayList<FenyeVo> initAllCityByFile(){
		ArrayList<FenyeVo> resList = new ArrayList<FenyeVo>();
		String src = FileUtil.getDataFile2StrKeepReturn("./data/initAllCity4Jdershou.txt", "UTF-8");
		src = src.substring(src.indexOf("#") + 1, src.indexOf("$"));
		String[] lines = src.split("\n");
		for(int i=1; i<lines.length; i++){
			String[] line = lines[i].split(":");
			resList.add(new FenyeVo(line[0], line[2]+ ":" + line[3], line[4], 1, Integer.parseInt(line[1])));
		}
		System.out.println("initAllCityByFile finished");
		return resList;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JdershouSiteParser jParser = new JdershouSiteParser();
		System.out.println(jParser.initAllCityByFile());
	}

}
