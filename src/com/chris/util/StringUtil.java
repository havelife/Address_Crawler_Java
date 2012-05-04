package com.chris.util;

public class StringUtil {
	public static Boolean startWithStr(String originStr, String compare){
		if(compare.length()>originStr.length())return false;
		originStr = originStr.substring(0, compare.length());
		return originStr.equals(compare);
	}
	
	public static Boolean endWithStr(String originStr, String compare){
		if(compare.length()>originStr.length())return false;
		originStr = originStr.substring(originStr.length()-compare.length());
		return originStr.equals(compare);
	}
	public static void main(String[] args){
		System.out.println(endWithStr("现售项目信息", "项目信息"));
	}
}
