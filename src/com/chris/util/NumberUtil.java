package com.chris.util;

public class NumberUtil {
	public static boolean charIsNumber(String oneChar){
		Boolean resBoolean =  ("0".equals(oneChar)||"1".equals(oneChar)||"2".equals(oneChar)||"3".equals(oneChar)||"4".equals(oneChar)||"5".equals(oneChar)||"6".equals(oneChar)||"7".equals(oneChar)||"8".equals(oneChar)||"9".equals(oneChar));
		return resBoolean;
	}
}
