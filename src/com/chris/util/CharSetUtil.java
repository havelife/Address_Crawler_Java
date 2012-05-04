package com.chris.util;

import java.io.UnsupportedEncodingException;

public class CharSetUtil {
	
	public static String readStringWithSrcCharSet(String str, String encoding){
		String res = null;
		try {
			res = new String(str.getBytes(encoding));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
