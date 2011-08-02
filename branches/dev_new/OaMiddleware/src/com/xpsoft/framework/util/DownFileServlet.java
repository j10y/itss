package com.xpsoft.framework.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownFileServlet extends HttpServlet {
    
//    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//		String url = request.getParameter("url");
//		String name = request.getParameter("name");
//		if (url != null && name != null) {
////			url = OAURL + url;
//			byte [] bytes = FileDownloaderUtil.readFileByUrl(url);
//			FileDownloaderUtil.downloadFile(response, name, bytes);
//		} 
//    }
//   
//    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
//        doGet(request, response);
//    }
    
    
    
    
    
    
    
    
    
    
    
    
    public void destroy() {
    	  super.destroy(); // Just puts "destroy" string in log
    	  // Put your code here
    	 }

    	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	  this.doPost(request, response);
    	 }

    	 public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//    	   this.download("E:\\wenj.txt", response);
    		 String url = request.getParameter("url");
    			String name = request.getParameter("name");
    		 byte [] bytes = FileDownloaderUtil.readFileByUrl(url);
 			FileDownloaderUtil.downloadFile(response, name, bytes);
    	 }

    	 public void init() throws ServletException {
    	  // Put your code here
    	 }

    	 public HttpServletResponse download(String path, HttpServletResponse response) {
    	  try {
    	   // path是指欲下载的文件的路径。
    	   File file = new File(path);
    	   // 取得文件名。
    	   String filename = file.getName();
    	   // 取得文件的后缀名。
    	   String ext = filename.substring(filename.lastIndexOf(".") + 1).toUpperCase();

    	   // 以流的形式下载文件。
    	   InputStream fis = new BufferedInputStream(new FileInputStream(path));
    	   
    	   // 清空response
    	   response.reset();
    	   // 设置response的Header
    	   response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes()));
    	   response.addHeader("Content-Length", "" + file.length());
    	   OutputStream toClient = new BufferedOutputStream(response.getOutputStream());
    	   response.setContentType("application/octet-stream");
    	   byte[] buffer = new byte[1024 * 1024 * 8];
    	   int i = -1;
    	   while ((i = fis.read(buffer)) != -1) {
    	    toClient.write(buffer, 0, i);    
    	    toClient.flush();
    	    
    	   }
    	   fis.close();
    	   toClient.close();
    	  } catch (IOException ex) {
    	   ex.printStackTrace();
    	  }
    	  return response;
    	 }

    	 public void downloadLocal(HttpServletResponse response) throws FileNotFoundException {
    	  // 下载本地文件
    	  String fileName = "Operator.doc".toString(); // 文件的默认保存名
    	  // 读到流中
    	  InputStream inStream = new FileInputStream("c:/Operator.doc");// 文件的存放路径
    	  // 设置输出的格式
    	  response.reset();
    	  response.setContentType("bin");
    	  response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
    	  // 循环取出流中的数据
    	  byte[] b = new byte[100];
    	  int len;
    	  try {
    	   while ((len = inStream.read(b)) > 0)
    	    response.getOutputStream().write(b, 0, len);
    	   inStream.close();
    	  } catch (IOException e) {
    	   e.printStackTrace();
    	  }
    	 }

    	 public void downloadNet(HttpServletResponse response) throws MalformedURLException {
    	  // 下载网络文件
    	  int bytesum = 0;
    	  int byteread = 0;

    	  URL url = new URL("windine.blogdriver.com/logo.gif");

    	  try {
    	   URLConnection conn = url.openConnection();
    	   InputStream inStream = conn.getInputStream();
    	   FileOutputStream fs = new FileOutputStream("c:/abc.gif");

    	   byte[] buffer = new byte[1204];
    	   int length;
    	   while ((byteread = inStream.read(buffer)) != -1) {
    	    bytesum += byteread;
    	    System.out.println(bytesum);
    	    fs.write(buffer, 0, byteread);
    	   }
    	  } catch (FileNotFoundException e) {
    	   e.printStackTrace();
    	  } catch (IOException e) {
    	   e.printStackTrace();
    	  }
    	 }


}
