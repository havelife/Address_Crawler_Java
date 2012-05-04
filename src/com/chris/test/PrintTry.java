package com.chris.test;

public class PrintTry {
	int x, y;

	PrintTry(int m) {
		x = m;
		y = m;
	}

	PrintTry(int m, int n) {
		x = m;
		y = n;

	}

	int compute() {
		return x + y;
	}

	int compute(int mul) {
		return (x + y) * mul;
	}

	public static void main(String[] args) {
//		PrintTry tryone;
//		PrintTry trytwo;
//		tryone = new PrintTry(5);
//		trytwo = new PrintTry(10, 10);
//		System.out.println("tryone调用compute()计算，值为：" + tryone.compute());
//		System.out.println("tryone调用compute(3)计算，值为：" + tryone.compute(3));
//		System.out.println("tryone调用compute()计算，值为：" + trytwo.compute());
//		System.out.println("tryone调用compute(3)计算，值为：" + trytwo.compute(3));
		int m = 1;
		System.out.println(m < 5? 1:"a");
	}
}
