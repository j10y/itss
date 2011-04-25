package com.digitalchina.info.appframework.pagemodel.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.digitalchina.info.appframework.metadata.entity.SystemMainTable;
import com.digitalchina.info.appframework.metadata.entity.SystemMainTableColumn;
import com.digitalchina.info.appframework.metadata.service.SystemMainColumnService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ApplicationException;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.servlet.BaseServlet;
/**
 * �����ϴ�����Servlet
 * @Class Name FileUpDownData
 * @Author sa
 * @Create In Mar 26, 2009
 */
public class FileUpDownData extends BaseServlet {
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private SystemMainColumnService smss = (SystemMainColumnService) ContextHolder
			.getBean("systemMainColumnService");

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk");
		String msg = "";
		String method = request.getParameter("method");
		if (StringUtils.isBlank(method)) {
			msg = "Error: no method specified.";
		} else {
			if (method.trim().equalsIgnoreCase("down")) {
				msg = downloadFile(request, response);
			}
		}

	}

	private String downloadFile(HttpServletRequest request,
			HttpServletResponse response) {

		String fileId = request.getParameter("id");
		String columnId = request.getParameter("columnId");
		SystemMainTableColumn column = smss
				.findSystemMainTableColumnById(columnId);
		SystemMainTable ftable = column.getForeignTable();
		String uploadUrl = column.getUploadUrl();
		String fileNamePrefix = column.getFileNamePrefix();
		SystemMainTableColumn fileNameColumn = column.getFileNameColumn();
		SystemMainTableColumn systemFileNameColumn = column
				.getSystemFileNameColumn();

		String className = ftable.getClassName();
		Class clazz = this.getClass(className);

		Object file = service.find(clazz, fileId);
		BeanWrapper wb = new BeanWrapperImpl(file);

		String fileName = (String) wb.getPropertyValue(fileNameColumn
				.getPropertyName());
		String systemFileName = (String) wb
				.getPropertyValue(systemFileNameColumn.getPropertyName());

		String path = request.getRealPath("/") + uploadUrl + FSP
				+ systemFileName;
		String downPath = fileName;
		int filelength = downPath.length();
		String appendix = downPath.substring(downPath.indexOf(".") + 1,
				filelength);
		try {
			java.io.File ioFile = new java.io.File(path);
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			response.setContentType("application/" + appendix);// �����ļ�����
			response.addHeader("Content-Disposition", "attachment;filename="// +downPath);
					+ HttpUtil.toUtf8String(downPath));
			response.addHeader("Content-Length", "" + ioFile.length()); // ���÷��ص��ļ�����
			response.setCharacterEncoding("gbk");
			OutputStream toClient = new BufferedOutputStream(response
					.getOutputStream()); // �õ���ͻ���������������ݵĶ���
			toClient.write(buffer); // �������
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new ApplicationException("���ص��ļ������ڣ���͹���Ա��ϵ!");
		}
		return null;
	}

	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("������" + className + "����ȷ��");
			e.printStackTrace();
		}
		return clazz;
	}
}

//
// /**
// * ����ʹ�õ�Ԫ����
// * @Methods Name forSave
// * @Create In Sep 9, 2008 By yang
// * @param request
// * @return
// * @ReturnType String
// */
// private String forSave(HttpServletRequest request) {
// String json = "";
// String nowtime = request.getParameter("nowtime");
// String columnId = request.getParameter("columnId");
// SystemMainTableColumn column = smss.findSystemMainTableColumnById(columnId);
// SystemMainTable ftable = column.getForeignTable();
// String className = ftable.getClassName();
// Class clazz = this.getClass(className);
//		
// String uploadUrl = column.getUploadUrl();
// String fileNamePrefix = column.getFileNamePrefix();
// SystemMainTableColumn fileNameColumn = column.getFileNameColumn();
// SystemMainTableColumn systemFileNameColumn =
// column.getSystemFileNameColumn();
// // fileName
// String s = "";
// List list = service.find(clazz, "nowtime", nowtime);
// for(int i=0; i<list.size(); i++){
// Object file = list.get(i);
// BeanWrapper wb = new BeanWrapperImpl(file);
// String fileName = (String)
// wb.getPropertyValue(fileNameColumn.getPropertyName());
// //String systemFileName = (String)
// wb.getPropertyValue(systemFileNameColumn.getPropertyName());
// Long fileId = (Long) wb.getPropertyValue("id");
// String contextPath=request.getContextPath();
// String link = "<a
// href='"+contextPath+"/fileDown.do?methodCall=downloadFile&id="+fileId+"&columnId="+columnId+"'>";
// link += fileName + "</a><br/>";
//			
// s += "fieldLabel:'"+"����"+(i+1)+"',";
// s += "html :\"<font color=blouse>"+link+"</font>\",";
// s += "cls : 'common-text',";
// s += "width:135,";
// s += "id:'"+"����"+(i+1)+"',";
// s += "name:'"+"����"+(i+1)+"',";
//			
//	
// //s += "readOnly:"+blank(c.isReadOnly())+",";
// //s += "value:'"+blank(c.getValue())+"',";
// s += "allowBlank:true,"; //������
// s += "validator:true,"; //������
//			
//			
// s += "style : 'width:150;text-align:right',";
// s += "vtype:''";
// s += "},";
//
// }
// if (s.endsWith(",")) {
// s = "[" + s.substring(0, s.length() - 1) + "]";
// }
//		
// return s;
// }

