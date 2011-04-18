package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.appframework.template.entity.DeptMenuTemplate;
import com.digitalchina.info.appframework.template.entity.DeptMenuTemplateItem;
import com.digitalchina.info.appframework.template.service.DeptTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

@SuppressWarnings("serial")
public class DeptMenuItemEditServlet extends HttpServlet {
	
	private DeptTemplateMenuService deptTemplateMenuService = (DeptTemplateMenuService) getBean("deptTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String id = request.getParameter("id");
		String parentId = request.getParameter("parentId");
		String leaf = request.getParameter("leaf");
		String number = request.getParameter("number");
		String smtId = request.getParameter("smtId");
		String dmtId = request.getParameter("dmtId");
		
		DeptMenuTemplateItem obj = null;
		
		if(null != id){
			
			obj = deptTemplateMenuService.findMenuById(id);
		}else{
			obj = new DeptMenuTemplateItem();
			DeptMenuTemplateItem parentMenu = null;
			if("0".equals(parentId)){
				obj.setParentMenu(null);
			}else{
				parentMenu = deptTemplateMenuService.findMenuById(parentId);
				obj.setParentMenu(parentMenu);
			}
			
			obj.setLeafFlag(new Integer(leaf));
			obj.setMenuOrder(new Integer(number));
			if(!"".equals(dmtId)){
				DeptMenuTemplate deptMenuTemplate = deptTemplateMenuService.findDeptMenuTemplateById(dmtId);
				obj.setDeptMenuTemplate(deptMenuTemplate);
			}else{
				obj.setDeptMenuTemplate(null);
			}
		}
		request.setAttribute("obj", obj);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/deptMenu/deptMenu-edit.jsp");
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
