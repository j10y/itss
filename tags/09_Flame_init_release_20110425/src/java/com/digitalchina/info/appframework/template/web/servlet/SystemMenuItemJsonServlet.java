package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.appframework.template.service.SystemTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

@SuppressWarnings("serial")
public class SystemMenuItemJsonServlet extends HttpServlet {
	
	private SystemTemplateMenuService systemMenuService = (SystemTemplateMenuService)getBean("systemTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parentId = request.getParameter("id");
		String smtId = request.getParameter("smtId");
		
		System.out.println("sysMenuJson 系统菜单模板ID :"+smtId);
		
		if("0".equals(parentId) && !"".equals(smtId)){
			request.setAttribute("list", systemMenuService.findSystemMenuTemplateItemNoParent(smtId));
		}else{
			request.setAttribute("list", systemMenuService.findChildenByParentAndSystemMenuTemplate(parentId, smtId));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/sysMenu/sysMenu-json.jsp");
		dispatcher.forward(request, response);
	}
	
	/**
	 * 返回spring管理的服务service
	 * @param name
	 * @return
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
}
