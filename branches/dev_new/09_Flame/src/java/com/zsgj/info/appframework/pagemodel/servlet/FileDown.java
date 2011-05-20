package com.zsgj.info.appframework.pagemodel.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ApplicationException;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;

public class FileDown extends BaseDispatchAction {
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private Service service = (Service) ContextHolder.getBean("baseService");	
	private SystemMainColumnService smss = (SystemMainColumnService) ContextHolder.getBean("systemMainColumnService");
	
	/**
	 * ����������ص��õ�action������
	 * @Methods Name downloadFile
	 * @Create In Jul 8, 2009 By sa
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception ActionForward
	 */
	@SuppressWarnings("deprecation")
	public ActionForward downloadFile(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String fileId = request.getParameter("id"); //��gridѡ��ĸ���id
		String columnId = request.getParameter("columnId");
		SystemMainTableColumn column = smss.findSystemMainTableColumnById(columnId);
		SystemMainTable ftable = column.getForeignTable();
		String uploadUrl = column.getUploadUrl();
//		String fileNamePrefix = column.getFileNamePrefix();
		SystemMainTableColumn fileNameColumn = column.getFileNameColumn();
		SystemMainTableColumn systemFileNameColumn = column.getSystemFileNameColumn();	
		//Ĭ�ϸ���ͳһ�洢��com.digitalchina.info.appframework.metadata.entity.SystemFile
		String className = ftable.getClassName();
		Class clazz = this.getClass(className);
		
		Object file = service.find(clazz, fileId);
		BeanWrapper wb = new BeanWrapperImpl(file);
		
		String fileName = (String) wb.getPropertyValue(fileNameColumn.getPropertyName());
		String systemFileName = (String) wb.getPropertyValue(systemFileNameColumn.getPropertyName());
		
		String path = request.getRealPath("/") + uploadUrl + FSP+ systemFileName;
		String downPath = fileName;
		int filelength=	downPath.length();
    	String appendix=downPath.substring(downPath.indexOf(".")+1, filelength);
    	
		try {
			java.io.File ioFile = new java.io.File(path);
			InputStream fis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[fis.available()];
			fis.read(buffer);
			fis.close();
			response.reset();
			response.setContentType("application/"+appendix);// �����ļ�����
			response.addHeader("Content-Disposition", "attachment;filename="//+downPath);
					+ HttpUtil.toUtf8String(downPath));
			response.addHeader("Content-Length", "" + ioFile.length()); // ���÷��ص��ļ�����
			response.setCharacterEncoding("gbk");
			OutputStream toClient = new BufferedOutputStream(response
					.getOutputStream()); // �õ���ͻ���������������ݵĶ���
			toClient.write(buffer); // �������
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			//ex.printStackTrace();
			//throw new ApplicationException("���ص��ļ������ڣ���͹���Ա��ϵ!");
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
