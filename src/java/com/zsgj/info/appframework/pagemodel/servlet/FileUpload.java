package com.zsgj.info.appframework.pagemodel.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import com.jspsmart.upload.SmartUpload;
import com.zsgj.info.appframework.metadata.MetaDataManager;
import com.zsgj.info.appframework.metadata.entity.SystemFile;
import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.metadata.entity.SystemMainTableColumn;
import com.zsgj.info.appframework.metadata.service.SystemColumnService;
import com.zsgj.info.appframework.metadata.service.SystemMainColumnService;
import com.zsgj.info.appframework.pagemodel.PageManager;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.web.adapter.servlet.BaseServlet;

/**
 * ��ܸ����ϴ�Servlet�࣬��ܶ������ӡ�
 * @Class Name FileUploadServlet
 * @Author sa
 * @Create In 2009-3-19 
 */
public class FileUpload extends BaseServlet {
	static final String FSP = System.getProperty("file.separator");
	static final String LSP = System.getProperty("line.separator");
	private PageManager pageManager = (PageManager) ContextHolder.getBean("pageManager");
	private MetaDataManager metaDataManager = (MetaDataManager) ContextHolder.getBean("metaDataManager");
	private Service service = (Service) ContextHolder.getBean("baseService");	
	private SystemMainColumnService smss = (SystemMainColumnService) ContextHolder.getBean("systemMainColumnService");
	private SystemColumnService scs = (SystemColumnService) ContextHolder.getBean("systemColumnService");
	private ServletConfig config;
	   
	public void init(ServletConfig config) throws ServletException {
		this.config = config;
	}
	    
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("gbk");
		response.setCharacterEncoding("gbk"); 
		String result = "";
		String columnId = request.getParameter("columnId");
		//nowtime�����ϴ�ʱ��Ҫ����ʱ����ַ���������������Щ������һ���ϴ��� peixf
		String nowtime = request.getParameter("nowtime");
		SystemMainTableColumn column = smss.findSystemMainTableColumnById(columnId);
		
		
		String uploadUrl = column.getUploadUrl();
		String fileNamePrefix = column.getFileNamePrefix();
		SystemMainTableColumn fileNameColumn = column.getFileNameColumn();
		SystemMainTableColumn systemFileNameColumn = column.getSystemFileNameColumn();	
		
		SystemMainTable ftable = column.getForeignTable();
		
		if(ftable==null|| fileNameColumn==null|| systemFileNameColumn==null){
			//�����̨�ø������͵��ֶ�û�а��տ��Ҫ�����ã��Զ���ʼ��
			scs.saveSystemFileColumnInit(column);
			ftable = column.getForeignTable();
			fileNameColumn = column.getFileNameColumn();
			systemFileNameColumn = column.getSystemFileNameColumn();	
		}
		// fileName
		String fileNamePropertyName = fileNameColumn.getPropertyName();
		// systemFileName
		String sysFileNamePropertyName = systemFileNameColumn.getPropertyName();
		//Ĭ�ϸ���ͳһ�洢��com.digitalchina.info.appframework.metadata.entity.SystemFile
		String className = ftable.getClassName();
		Class clazz = this.getClass(className);
				
		SmartUpload su = new SmartUpload();
		su.initialize(config, request,  response);
		//su.setMaxFileSize(9900000);
		//su.setTotalMaxFileSize(99000000);
		//su.setAllowedFilesList("doc,xls,txt,jpg,gif,rar,zip,mid,waw,mp3");
		
		try {
			//su.setDeniedFilesList("exe,bat,jsp,htm,html");
			su.upload();

			//String content =  su.getRequest().getParameter("Content"); //request.getParameter("Content");
			//String content = su.getRequest().getParameter("Content");
			//2����ֵ����--------sujs add--------------
			String fileNameTemp="";
			String fileIdTemp=""; 
			//2����ֵ����--------sujs add--------------
			for (int i=0;i<su.getFiles().getCount();i++){
				//�ļ��ϴ�����ļ�����
				com.jspsmart.upload.File myFile = su.getFiles().getFile(i);
				
				String filePathName = myFile.getFilePathName();
				
				if(StringUtils.isNotBlank(filePathName)){
					
					String sOriginalFileName = myFile.getFileName();
					fileNameTemp=sOriginalFileName;
					String extFileName = myFile.getFileExt();
					String sSaveFileName = fileNamePrefix + System.currentTimeMillis()+"."+extFileName;
					//String folder = uploadUrl; //"upload" + FSP + "NewsFiles"+ FSP;
					//ʵ���ϴ����ļ�·����Ӧ�õĸ�·�� + �ļ���·�� + ʵ�ʵ��ļ���
					String sPathFileName =request.getRealPath(FSP)+ FSP + uploadUrl +FSP +  sSaveFileName;
	//				sPathFileName = sPathFileName.replace("\\", "/");
	//				sSaveFileName = sSaveFileName.replace("\\", "/");
	//				folder = folder.replace("\\", "/");
					//�ϴ��������ļ�ϵͳ
					myFile.saveAs(sPathFileName);
			
					//���渽������com.digitalchina.info.appframework.metadata.entity.SystemFile�Ķ���
					Object fileObject = clazz.newInstance();
					BeanWrapper bw = new BeanWrapperImpl(fileObject);
					bw.setPropertyValue(fileNamePropertyName, sOriginalFileName);
					bw.setPropertyValue(sysFileNamePropertyName, sSaveFileName);
					bw.setPropertyValue("uploadDate", new Date());
					bw.setPropertyValue("uploadUser", UserContext.getUserInfo());
					//service.save(fileObject);
					//-----------sujs add--------------------------------------
					SystemFile fileTemp=(SystemFile)service.save(fileObject);
					 fileIdTemp=fileTemp.getId().toString();
					//-----------sujs------------------------------------------
					try {
						bw.setPropertyValue("nowtime", nowtime);
						service.save(fileObject);
					} catch (Exception e) {
						e.printStackTrace();
					}			
				}
			
			
		  }
			result = "{success:true,message:'�ϴ��ɹ�',fileNameTemp:'" +fileNameTemp+"',fileIdTemp:'"+fileIdTemp+"'}";
			
		} catch (java.lang.SecurityException e) {
			e.printStackTrace();
			result = "{success:flase,message:'�ϴ�ʧ��'}";
			
		} catch (Exception e) {
			e.printStackTrace();
			result = "{success:flase,message:'�ϴ�ʧ��'}";
		}
		response.setCharacterEncoding("gbk");
		PrintWriter writer = response.getWriter();
		writer.write(result);
		writer.flush();
		writer.close();
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
