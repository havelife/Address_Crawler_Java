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

public class BdlmParser {
	public static FenyeDaoImpl dao = new FenyeDaoImpl();
	public static Boolean flagOpen = true;
	public static void getDateReplyOwner() {
		HashMap<String, TieZi> hashMap = new HashMap<String, TieZi>();
		
		ArrayList<Fenye> list = (ArrayList<Fenye>)dao.getAllByCity("bdlm");
		for (Fenye fenye : list) 
		{
			String content = fenye.getContent();
			
			Document doc = Jsoup.parse(content);
			Elements base = doc.body().select("table[id~=^thread_list_table$]")
					.select("tbody").select("tr");
			if (base.hasText()) {
				for (Element tr : base) {
					String owner = tr.select("td").get(3).text();
					String[] arr = tr.select("td").get(4).text().split("[\\t\\n\\s  ]+");
					String date = arr[0].trim();
					String reply = arr[1].trim();
					Line tri = new Line(date, reply, owner);
					System.out.println(tri);
					if(date.contains("-")){
						int aut;
						if (flagOpen) {aut = (reply.equals("union66")||reply.equals("百度联盟")||owner.equals("union66")||owner.equals("百度联盟"))?1:0;}
						else {aut = (reply.equals("union66")||reply.equals("百度联盟"))?1:0;}
						if (hashMap.containsKey(date)) {
							TieZi tieZi = hashMap.get(date);
							tieZi.total += 1;
							tieZi.authority += aut;
						} else {
							TieZi tieZi = new TieZi(date, 1, aut);
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
		    FileUtil.writeStr2File(sb.toString(), "./data/bdlmRes.txt", "utf-8");
		}
		
		System.out.println(hashMap);
	}
	
	public static void main(String[] args) {
		//String content = FileUtil.getDataFile2Str("./data/pageDetailContentBdlmSample.txt", "utf-8");
		getDateReplyOwner();
	}
}


class Line{
	String date;
	String reply;
	String owner;
	Line(String date, String reply, String owner){
		this.date = date;
		this.reply = reply;
		this.owner = owner;
	}
	public String toString(){
		return "date: " + date + " reply:" + reply + " owner:" + owner;
	}
}


class TieZi{
	String date;
	int total;
	int authority;
	TieZi(String date, int total, int authority){
		this.date = date;
		this.total = total;
		this.authority = authority;
	}
	
	public String toString(){
		return "date: " + date + " total: " + total + " authority: " + authority;
	}
}


