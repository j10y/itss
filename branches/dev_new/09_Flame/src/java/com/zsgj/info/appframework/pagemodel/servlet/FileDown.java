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
	 * 附件组件下载调用的action方法。
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

		String fileId = request.getParameter("id"); //从grid选择的附件id
		String columnId = request.getParameter("columnId");
		SystemMainTableColumn column = smss.findSystemMainTableColumnById(columnId);
		SystemMainTable ftable = column.getForeignTable();
		String uploadUrl = column.getUploadUrl();
//		String fileNamePrefix = column.getFileNamePrefix();
		SystemMainTableColumn fileNameColumn = column.getFileNameColumn();
		SystemMainTableColumn systemFileNameColumn = column.getSystemFileNameColumn();	
		//默认附件统一存储在com.digitalchina.info.appframework.metadata.entity.SystemFile
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
			response.setContentType("application/"+appendix);// 设置文件类型
			response.addHeader("Content-Disposition", "attachment;filename="//+downPath);
					+ HttpUtil.toUtf8String(downPath));
			response.addHeader("Content-Length", "" + ioFile.length()); // 设置返回的文件类型
			response.setCharacterEncoding("gbk");
			OutputStream toClient = new BufferedOutputStream(response
					.getOutputStream()); // 得到向客户端输出二进制数据的对象
			toClient.write(buffer); // 输出数据
			toClient.flush();
			toClient.close();
		} catch (IOException ex) {
			//ex.printStackTrace();
			//throw new ApplicationException("下载的文件不存在，请和管理员联系!");
		}
		return null;
	}
	
	private Class getClass(String className) {
		Class clazz = null;
		try {
			clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			System.out.print("类名：" + className + "不正确！");
			e.printStackTrace();
		}
		return clazz;
	}
	
}
