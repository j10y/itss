package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.metadata.entity.SystemMainTable;
import com.zsgj.info.appframework.template.service.TemplateService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

/**
 * ���ش���ģ��ʱѡ���ϵͳ������Ϣ
 * @Class Name LoadDestDataServlet
 * @author hp
 * @Create In Sep 9, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class LoadTemplateMainTableServlet extends HttpServlet {
	
	private TemplateService templateService = (TemplateService) getBean("templateService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		List<SystemMainTable> mainTableList = templateService.findTemplateTable();
		
		String json = "";
		
		for(int i=0; i< mainTableList.size(); i++){
			SystemMainTable smt = (SystemMainTable)mainTableList.get(i);
			
			Long id = smt.getId();
			String name = smt.getTableCnName();
			json += "{\"id\":"+id+",\"name\":\""+name+"\"},";
		}
		
		json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		System.out.println("ģ��ϵͳ���� ��"+json);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ����spring����ķ���service
	 * @param name
	 * @return
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
}
