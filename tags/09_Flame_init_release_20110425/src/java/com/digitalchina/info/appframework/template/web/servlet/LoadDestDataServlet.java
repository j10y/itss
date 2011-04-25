package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.appframework.template.service.TemplateService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

/**
 * ����Ŀ��������(TemplateItem)
 * @Class Name LoadDestDataServlet
 * @author hp
 * @Create In Sep 9, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class LoadDestDataServlet extends HttpServlet {
	
	private TemplateService templateService = (TemplateService) getBean("templateService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String parentId = request.getParameter("id");
		//ģ��Id
		String tmId = request.getParameter("tmId");

		if("0".equals(parentId) && !"".equals(tmId)){
			request.setAttribute("list", templateService.findTemplateItemNoParent(tmId));
		}else if("".equals(tmId)){
			request.setAttribute("list", null);
		}else{
			request.setAttribute("list", templateService.findChildenByParentAndTemplate(parentId, tmId));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/sysTemplate/data-json.jsp");
		dispatcher.forward(request, response);
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
