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
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadPicServlet1 extends HttpServlet {
    
     public void destroy() {
    	  super.destroy(); // Just puts "destroy" string in log
    	  // Put your code here
    	 }

	 public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 this.doPost(request, response);
	 }
	
	
	 public void init() throws ServletException {
	  // Put your code here
	 }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse resp)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String FSP = System.getProperty("file.separator");
		String LSP = System.getProperty("line.separator");
		String url = "";
		JSONObject backJsonObejct = new JSONObject();
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		String result;
		String fileName = "";
		try {
			List items = upload.parseRequest(request);
			Iterator iter = items.iterator();
			while (iter.hasNext()) {
				// 把实体换成map，然后保存
				FileItem item = (FileItem) iter.next();
				if (!item.isFormField()) {
					fileName = item.getName();
					String appendix = "";
					int indexOfDot = fileName.lastIndexOf(".");
					if(indexOfDot>=0) {
						appendix = fileName.substring(indexOfDot);
					}
					byte [] fileBytes = item.get();
					String systemFileName = "upload-"+ System.currentTimeMillis() + appendix;
					String filePath = "upload"  + FSP+ systemFileName;
					String realPath = request.getRealPath(FSP) + filePath;
					String dirPath = request.getRealPath(FSP) + "upload" ;
					File uploadedFile = new File(dirPath);
					if(!uploadedFile.isDirectory()) {
						uploadedFile.mkdirs();
					}
					System.out.println(item.getFieldName() + ":" +  fileName );
					uploadedFile = new File(realPath);
					item.write(uploadedFile);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//输出json
		PrintWriter pw = resp.getWriter();
		pw.append("{success:true}");
		pw.flush();
		pw.close();
	}


}
