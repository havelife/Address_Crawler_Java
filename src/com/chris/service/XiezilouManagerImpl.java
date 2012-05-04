package com.chris.service;

public class XiezilouManagerImpl {

	/**
	 * @param args
	 */
	// 专门就是用来解析那个从 搜索入口 下载下来的分页信息   再去下载对应详情页面 存入数据库
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FenyeManagerImpl fym = new FenyeManagerImpl();
		fym.savePageDetail4Xiezilou(5, "ISO-8859-1");
		System.out.println("finished");
	}

}
