package com.chris.util;

import java.net.URL;

import java.io.*;

import org.w3c.tidy.Configuration;

import org.w3c.tidy.Tidy;

public class HTML2XML {

	private String url;

	private String outFileName;

	private String errOutFileName;

	public HTML2XML(String url, String outFileName, String

	errOutFileName) {

		this.url = url;

		this.outFileName = outFileName;

		this.errOutFileName = errOutFileName;

	}

	public void convert() {

		URL u;

		BufferedInputStream in;

		FileOutputStream out;

		Tidy tidy = new Tidy();

		// Tell Tidy to convert HTML to XML

		tidy.setXmlOut(true);

		// tidy.setDropFontTags(true); // 删除字体节点

		// tidy.setDropEmptyParas(true); // 删除空段落

		// tidy.setFixComments(true); // 修复注释

		// tidy.setFixBackslash(true); // 修复反斜杆

		// tidy.setMakeClean(true); // 删除混乱的表示

		// tidy.setQuoteNbsp(false); // 将空格输出为 &nbsp;

		// tidy.setQuoteMarks(false); // 将双引号输出为 &quot;

		// tidy.setQuoteAmpersand(true); // 将 &amp; 输出为 &

		tidy.setCharEncoding(Configuration.RAW);

		tidy.setXmlPi(true);

		try {

			// Set file for error messages

			tidy.setErrout(new PrintWriter(new FileWriter(errOutFileName), true));

			u = new URL(url);

			// Create input and output streams

			in = new BufferedInputStream(u.openStream());

			out = new FileOutputStream(outFileName);

			// Convert files

			tidy.parse(in, out);

			// Clean up

			in.close();

			out.close();

		}

		catch (IOException e) {

			System.out.println(this.toString() + e.toString());

		}

	}

	public static void main(String args[]) {

		/*
		 * 
		 * Parameters are:
		 * 
		 * URL of HTML file
		 * 
		 * Filename of output file r
		 * 
		 * Filename of error file
		 */

		HTML2XML t = new HTML2XML("http://beijing.anjuke.com/sale/daxing/p1",
				"beijing_daxin_add.xml", "beijing_daxin_add.txt");

		t.convert();

	}

}