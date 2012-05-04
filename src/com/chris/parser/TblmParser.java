package com.chris.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.chris.hibernate.dao.FenyeDaoImpl;
import com.chris.hibernate.model.Fenye;
import com.chris.util.FileUtil;

public class TblmParser {
		public static FenyeDaoImpl dao = new FenyeDaoImpl();
		public static void getDateReplyOwner() {
			HashMap<String, TieZi> hashMap = new HashMap<String, TieZi>();
			
			ArrayList<Fenye> list = (ArrayList<Fenye>)dao.getAllByCity("淘宝客交流");
			for (Fenye fenye : list) 
			{
				String content = fenye.getContent();
				
				Document doc = Jsoup.parse(content);
				Elements base = doc.body().select("table[id~=^ajaxtable$]")
						.select("tbody").get(1).select("tr[class~=^tr3 t_one$]");
				if (base.hasText()) {
					for (Element tr : base) {
						String date = tr.select("td").get(4).select("span[class~=^f10 gray$]").text().split("\\s+")[0];
						//String day = date;
						System.out.println(date);
						
						if(date.contains("-")){
							if (hashMap.containsKey(date)) {
								TieZi tieZi = hashMap.get(date);
								tieZi.total += 1;
								tieZi.authority += 0;
							} else {
								TieZi tieZi = new TieZi(date, 1, 0);
								hashMap.put(date, tieZi);
							}
						}
						
					}
				}
			}
			
			{
				StringBuffer sb = new StringBuffer();
				
				Set entrys = hashMap.entrySet();
			    for(Iterator iter= entrys.iterator(); iter.hasNext();)
			    {
			       Map.Entry entry = (Map.Entry)iter.next();
			       //entry.getKey()拿出Map中的值
			       String date = (String)entry.getKey();
			       TieZi tieZi = (TieZi)entry.getValue();
			       sb.append(date);	sb.append("\t");
			       sb.append(tieZi.total);	sb.append("\t");
			       sb.append(tieZi.authority); sb.append("\t");
			       sb.append("\n");
			       System.out.println(entry.getValue());
			      }
			    FileUtil.writeStr2File(sb.toString(), "./data/tblmRes.txt", "utf-8");
			}
			
			System.out.println(hashMap);
		}
		
		public static void main(String[] args) {
			//String content = FileUtil.getDataFile2Str("./data/pageDetailContentBdlmSample.txt", "utf-8");
			getDateReplyOwner();
		}
	}
