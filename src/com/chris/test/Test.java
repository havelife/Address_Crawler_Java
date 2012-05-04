package com.chris.test;

import java.util.HashSet;

import com.chris.service.FenyeManagerImpl;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String tmp = "（英）达姆特（Dummett,P.）,（英）本（Benn,C.）";
		System.out.println(tmp);
		HashSet<String> hs = new HashSet<String>();
		hs.add("hello");
		hs.add("hello");
		hs.add("hello2");
		hs.add("hello3");
		hs.add("hello2");
		System.out.println(hs.size());
//		JSoupUtil t = new JSoupUtil();
//		t.levelOrderWeb("http://beijing.anjuke.com/sale/daxing/p1/");
		
		
//		FenyeManagerImpl fym = new FenyeManagerImpl();
//		//System.out.println(fym.getCount());
//		System.out.println(fym.getAllByCity("广州"));
		
		int[] shu1 = {1, 2};
		int[] shu2 = {1, 2};
		int i1 = 1;
		int i2 = 1;
//		System.out.println(shu1.equals(shu2));
//		//if(i1 == i2) System.out.println("hello world");
		//System.out.println(i1.equals(i2));
		
		String[] str1 = {"a", "b"};
		String[] str2 = {"a", "b"};
		System.out.println(str1 == str2);
		System.out.println(str1.equals(str2));
		
	}
	
	public String fun(String src){
		int length = src.length();
		boolean flag = false;
		int i = 0;
		while(i<length){
			String c = src.substring(i, i+1);
			if(c.equals("（"))flag=true;
			
			if(flag){
				if(c.equals("[,，]")){
					
				}
			}
			i++;
		}
		return "";
	}
}
