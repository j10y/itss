package com.xpsoft.framework.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载或显示已字节数组形式存放在数据库中文件数据
 * @Class Name FileDownloaderUtil
 * @Author likang
 * @Create In Dec 30, 2010
 */
public class FileDownloaderUtil {
	//默认类别map
	public static Hashtable MIME;

	static {
		MIME = new Hashtable();
		MIME.put("jpeg", "image/jpeg");
		MIME.put("jpg", "image/jpeg");
		MIME.put("bmp", "image/bmp");
		MIME.put("tif", "image/tiff");
		MIME.put("gif", "image/gif");
		MIME.put("xls", "application/x-msexcel");
		MIME.put("doc", "application/msword");
		MIME.put("ppt", "application/x-mspowerpoint");
		MIME.put("zip", "application/x-zip-compressed");
	}
	
	/**
	 * 文件下载
	 * @Methods Name download
	 * @Create In Dec 30, 2010 By likang
	 * @param response	
	 * @param fileName	文件名
	 * @param bytes		字节数组
	 * @param down		是否下载 ture下载/false 直接显示
	 * @throws IOException void
	 */
	public static void download(HttpServletResponse response, String fileName, byte bytes[], boolean down)
			throws IOException {
		int begin = 0;
		String ext = "";
		if ((begin = fileName.indexOf('.')) > 0){
			ext = fileName.substring(begin + 1);
		}
		String mime = (String) MIME.get(ext);
		if (mime == null){
			response.setContentType("application/x-msdownload");
		} else {
			response.setContentType(mime);
		}
		if (down) {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
		}
		OutputStream os = response.getOutputStream();
		os.write(bytes, 0, bytes.length);
		os.flush();
		os.close();
	}
	
	/**
	 * 读取url的文件到byte数组
	 * @Methods Name readFileByUrl
	 * @Create In Jul 22, 2011 By likang
	 * @param httpUrl
	 * @return byte[]
	 */
	public static byte[] readFileByUrl(String httpUrl) {
		Date before = new Date();
		long star = before.getTime();
		byte[] datas = null;
		try {
			InputStream in;
			URL url = new java.net.URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.connect();
			in = connection.getInputStream();
			datas = StaticHtml.InputStreamToByte(in);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Date after = new Date();
			long end = after.getTime();
			long ttime = end - star;
			System.out.println("执行时间:" + ttime / 3600 + "秒");
		}
		return datas;
	}
	
	
	/**
	 * 下载文件
	 * @Methods Name downloadFile
	 * @Create In Jul 22, 2011 By likang
	 * @param response
	 * @param fileName
	 * @param bytes
	 * @throws IOException void
	 */
	public static void downloadFile(HttpServletResponse response, String fileName, byte bytes[])
		throws IOException {
		response.setContentType("application/x-msdownload;charset=UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName,"utf-8"));
	    response.addHeader("Content-Length", "" + bytes.length);
//	    	   response.setContentType("application/octet-stream");
		response.setCharacterEncoding("UTF-8");
		OutputStream os = response.getOutputStream();
		os.write(bytes, 0, bytes.length);
		os.flush();
		os.close();
	}
	
	
	/**
	 * 一边从其他服务器读取，一边向客户端返回
	 * @Methods Name downAndLoadFileTogether
	 * @Create In Jul 28, 2011 By likang
	 * @param httpUrl
	 * @param response
	 * @param fileName void
	 */
	public static void downAndLoadFileTogether(String httpUrl,HttpServletResponse response,String fileName) {
		Date before = new Date();
		long star = before.getTime();
		try {
			response.setContentType("application/x-msdownload;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName,"utf-8"));
//		    	   response.setContentType("application/octet-stream");
			response.setCharacterEncoding("UTF-8");
			OutputStream os = response.getOutputStream();
			InputStream in;
			URL url = new java.net.URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/4.0");
			connection.connect();
			in = connection.getInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			// 至少用1K的缓冲
			byte[] bs = new byte[1024]; 
			int len = -1;
			while ((len = in.read(bs)) != -1) {
				os.write(bs, 0, len);    
				os.flush();
			}
//		    response.addHeader("Content-Length", "" + bytes.length);
			os.close();
			bos.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Date after = new Date();
			long end = after.getTime();
			long ttime = end - star;
			System.out.println("执行时间:" + ttime / 3600 + "秒");
		}
	}
	
	
	
	
	

	/**
	 * 只显示，不下载
	 * @Methods Name write
	 * @Create In Dec 30, 2010 By likang
	 * @param response
	 * @param contentType	文件类型 如image/jpeg
	 * @param bytes			字节数组
	 * @throws IOException void
	 */
	public static void showImage(HttpServletResponse response, String contentType, byte bytes[]) throws IOException {
		//response.setContentType(contentType);
		response.reset();
		OutputStream os = response.getOutputStream();
		os.write(bytes, 0, bytes.length);
		os.flush();
		os.close();
	}
}