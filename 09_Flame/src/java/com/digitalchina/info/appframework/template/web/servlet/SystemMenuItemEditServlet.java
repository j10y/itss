package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.appframework.template.entity.SystemMenuTemplate;
import com.digitalchina.info.appframework.template.entity.SystemMenuTemplateItem;
import com.digitalchina.info.appframework.template.service.SystemTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

@SuppressWarnings("serial")
public class SystemMenuItemEditServlet extends HttpServlet {
	
	private SystemTemplateMenuService systemMenuService = (SystemTemplateMenuService)getBean("systemTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		String leaf = request.getParameter("leaf");
		//��ʱ�Ե�
		//leaf="0";
		String number = request.getParameter("number");
		String smtId = request.getParameter("smtId");
		
		SystemMenuTemplateItem obj = null;
		
		if(null != id){
			
			obj = systemMenuService.findMenuById(id);
		}else{
			obj = new SystemMenuTemplateItem();
			SystemMenuTemplateItem parentMenu = null;
			if("0".equals(parentId)){
				obj.setParentMenu(null);
			}else{
				parentMenu = systemMenuService.findMenuById(parentId);
				obj.setParentMenu(parentMenu);
			}
			
			obj.setLeafFlag(new Integer(leaf));
			obj.setMenuOrder(new Integer(number));
			if(!"".equals(smtId)){
				SystemMenuTemplate sysMenuTemplate = systemMenuService.findSystemMenuTemplateById(smtId);
				obj.setSystemMenuTemplate(sysMenuTemplate);
			}else{
				obj.setSystemMenuTemplate(null);
			}
		}
		request.setAttribute("obj", obj);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/sysMenu/sysMenu-edit.jsp");
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
