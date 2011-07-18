package com.xpsoft.framework.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
