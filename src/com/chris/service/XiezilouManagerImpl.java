package com.chris.service;

public class XiezilouManagerImpl {

	/**
	 * @param args
	 */
	// ר�ž������������Ǹ��� ������� ���������ķ�ҳ��Ϣ   ��ȥ���ض�Ӧ����ҳ�� �������ݿ�
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FenyeManagerImpl fym = new FenyeManagerImpl();
		fym.savePageDetail4Xiezilou(5, "ISO-8859-1");
		System.out.println("finished");
	}

}
