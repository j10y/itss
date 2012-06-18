package com.xpsoft.framework.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 转静态
 * @Class Name StaticHtml
 * @Author likang
 * @Create In Jun 14, 2011
 */
public class StaticHtml {

	private static long star = 0;
	private static long end = 0;
	private static long ttime = 0;

	// 返回html代码
	public static String getHtmlCode(String httpUrl) {
		Date before = new Date();
		star = before.getTime();
		String htmlCode = "";
		try {
			InputStream in;
			URL url = new java.net.URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.connect();
			in = connection.getInputStream();
			htmlCode = new String(InputStreamToByte(in), "utf8");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Date after = new Date();
			end = after.getTime();
			ttime = end - star;
			System.out.println("执行时间:" + ttime / 3600 + "秒");
		}
		System.out.println(htmlCode);
		return htmlCode;
	}

	public static byte[] InputStreamToByte(InputStream is) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		// 至少用1K的缓冲
		byte[] bs = new byte[1024]; 
		int len = -1;
		while ((len = is.read(bs)) != -1) {
			bos.write(bs, 0, len);
		}
		byte b[] = bos.toByteArray();
		bos.close();
		return b;
	}

	public static String refreshURL(String url) throws Throwable {
		InputStream in = null;
		byte[] buff = new byte[1024];
		List<Byte> byteList = new ArrayList<Byte>();
		URL theURL = new URL(url);
		URLConnection urlConnection = theURL.openConnection();
		urlConnection.connect();
		in = urlConnection.getInputStream();
		int i = 0;
		while ((i = in.read(buff)) != -1) {
			for (int k = 0; k < i; k++) {
				byteList.add(new Byte(buff[k]));
			}
		}
		in.close();
		byte[] content = new byte[byteList.size()];
		for (int j = 0, size = byteList.size(); j < size; j++) {
			content[j] = byteList.get(j).byteValue();
		}
		String ret = new String(content, "utf8");
		return ret;
	}

	// 存储文件
	public static synchronized void writeHtml(String filePath, String info,
			String flag) {
		try {
			File writeFile = new File(filePath);
			boolean isExit = writeFile.exists();
			if (isExit != true) {
				writeFile.createNewFile();
			} else {
				if (!flag.equals("NO")) {
					writeFile.delete();
					writeFile.createNewFile();
				}
			}
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(writeFile), "UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(info);
			writer.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}
	}
	
	
	// 存储文件
	public static synchronized void writeFile(String filePath, byte[] bytes,
			String flag) {
		try {
			File writeFile = new File(filePath);
			boolean isExit = writeFile.exists();
			if (isExit != true) {
				writeFile.createNewFile();
			} else {
				if (!flag.equals("NO")) {
					writeFile.delete();
					writeFile.createNewFile();
				}
			}
			FileOutputStream fos = new FileOutputStream(filePath);
			fos.write(bytes, 0, bytes.length);
			fos.flush();
			fos.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
		}
	}
	public static void writeFile(String filePathAndName, String fileContent) {
		try {
			File f = new File(filePathAndName);
			if (!f.exists()) {
				f.createNewFile();
			}
			OutputStreamWriter write = new OutputStreamWriter(
					new FileOutputStream(f), "UTF-8");
			BufferedWriter writer = new BufferedWriter(write);
			writer.write(fileContent);
			writer.close();
		} catch (Exception e) {
			System.out.println("写文件内容操作出错");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		String url = "http://127.0.0.1:8080/";
		writeHtml("c:/demo.htm", getHtmlCode(url), "NO");
		// writeFile("c:/demo.htm", getHtmlCode(url));
		try {
			// System.out.println(refreshURL(url));
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
