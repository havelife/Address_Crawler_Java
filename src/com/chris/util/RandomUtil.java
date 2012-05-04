package com.chris.util;

public class RandomUtil {
	public static java.util.Random r;
	
	static {
		r = new java.util.Random();
	}
	
	
	public static int getOne2XRandomNum(int x){
		if(x==0)return 0;
		return (Math.abs(r.nextInt())%x);
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(RandomUtil.getOne2XRandomNum(10));
	}
}
