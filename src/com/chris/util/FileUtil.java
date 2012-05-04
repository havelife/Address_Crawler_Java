package com.chris.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class FileUtil {

	public static BufferedWriter getBufferedWriter(String filePath) {
		File file = new File(filePath);
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			return bw;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedWriter getBufferedWriter(String filePath,
			String encoding) {
		File file = new File(filePath);
		try {
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(file), encoding);
			BufferedWriter bw = new BufferedWriter(write);
			return bw;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedReader getBufferedReader(String filePath) {
		File file = new File(filePath);
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			return br;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static BufferedReader getBufferedReader(String filePath,
			String encoding) {
		File file = new File(filePath);
		try {
			InputStreamReader read = new InputStreamReader(new FileInputStream(
					file), encoding);
			BufferedReader br = new BufferedReader(read);
			return br;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

/*	public static String getDataFile2Str(String filePath) {
		BufferedReader br = getBufferedReader(filePath);
		String tmp;
		StringBuffer sb = new StringBuffer();
		try {
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
*/
	public static String getDataFile2Str(String filePath) { //, String encoding
		String encoding = getFileEncoding(filePath);
		BufferedReader br = getBufferedReader(filePath, encoding);
		String tmp;
		StringBuffer sb = new StringBuffer();
		try {
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String getDataFile2Str(String filePath, String encoding) { 
		BufferedReader br = getBufferedReader(filePath, encoding);
		String tmp;
		StringBuffer sb = new StringBuffer();
		try {
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static String getDataFile2StrKeepReturn(String filePath, String encoding) { 
		BufferedReader br = getBufferedReader(filePath, encoding);
		String tmp;
		StringBuffer sb = new StringBuffer();
		try {
			while ((tmp = br.readLine()) != null) {
				sb.append(tmp + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public static boolean writeStr2File(String src, String filePath) {
		BufferedWriter bw = getBufferedWriter(filePath);
		try {
			bw.append(src);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}

	public static boolean writeStr2File(String src, String filePath, String encoding) {
		BufferedWriter bw = getBufferedWriter(filePath, encoding);
		try {
			bw.append(src);
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	/**
	 * Java：判断文件的编�?
	 * 
	 * @param sourceFile
	 *            需�?判断编�?的文件
	 * @return String 文件编�?
	 */
	public static String getFileEncoding(String filePath) { //File sourceFile
		String charset = "GBK";
		byte[] first3Bytes = new byte[3];
		try {
			// boolean checked = false;

			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(new File(filePath)));
			bis.mark(0);

			int read = bis.read(first3Bytes, 0, 3);
			//System.out.println("字节大�?：" + read);

			if (read == -1) {
				return charset; // 文件编�?为 ANSI
			} else if (first3Bytes[0] == (byte) 0xFF
					&& first3Bytes[1] == (byte) 0xFE) {

				charset = "UTF-16LE"; // 文件编�?为 Unicode
				// checked = true;
			} else if (first3Bytes[0] == (byte) 0xFE
					&& first3Bytes[1] == (byte) 0xFF) {

				charset = "UTF-16BE"; // 文件编�?为 Unicode big endian
				// checked = true;
			} else if (first3Bytes[0] == (byte) 0xEF
					&& first3Bytes[1] == (byte) 0xBB
					&& first3Bytes[2] == (byte) 0xBF) {

				charset = "UTF-8"; // 文件编�?为 UTF-8
				// checked = true;
			}
			bis.reset();

			/*
			 * if (!checked) { int loc = 0;
			 * 
			 * while ((read = bis.read()) != -1) { loc++; if (read >= 0xF0)
			 * break; if (0x80 <= read && read <= 0xBF) // �?�独出现BF以下的，也算是GBK
			 * break; if (0xC0 <= read && read <= 0xDF) { read = bis.read(); if
			 * (0x80 <= read && read <= 0xBF) // �?�字节 (0xC0 - 0xDF) // (0x80 // -
			 * 0xBF),也�?�能在GB编�?内 continue; else break; } else if (0xE0 <= read &&
			 * read <= 0xEF) {// 也有�?�能出错，但是几率较�? read = bis.read(); if (0x80 <=
			 * read && read <= 0xBF) { read = bis.read(); if (0x80 <= read &&
			 * read <= 0xBF) { charset = "UTF-8"; break; } else break; } else
			 * break; } } // System.out.println( loc + " " +
			 * Integer.toHexString( read ) // ); }
			 */

			bis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return charset;
	}
	
	public static void levelOrder(File file){
		File c;
		File[] cc;
		ArrayList<File> q = new ArrayList<File>();
		if(file == null) return;
		c = file; q.add(c);
		while(q.size()!=0){
			c = q.remove(0); doInLevelOrder(c);   //System.out.println(c.getAbsolutePath());
			cc = c.listFiles(); 
			if(cc != null)
				for(File tmp : cc)q.add(tmp);
		}
	}
	// 在宽度�??历的时候，你对于�?个节点想�?�的�?作，就写在这个doInLevelOrder()里
	public static void doInLevelOrder(File c){
		System.out.println(c.getAbsolutePath());
	}
}
