package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.service.DeptTemplateMenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.util.PropertiesUtil;

/**
 * 用户部门菜单修改
 * @Class Name DeptMenuItemModifyServlet
 * @author zhangys
 * @Create In Oct 24, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class DeptMenuItemModifyServlet extends HttpServlet {
	
	private DeptTemplateMenuService deptTemplateMenuService = (DeptTemplateMenuService) getBean("deptTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//父节点ID
		String parentId = request.getParameter("id");
		//部门菜单模板ID
		String dmtId = request.getParameter("dmtId");
		//系统菜单模板ID
		String smtId = request.getParameter("smtId");
		
		request.setAttribute("dmtId", dmtId);
		request.setAttribute("smtId",smtId);
		String userDeptMenuPath = PropertiesUtil.getProperties("dept.menu.front.deptmenu.url", 
					"/user/menu/deptMenu/deptMenu-index.jsp"); 
		RequestDispatcher dispatcher = request.getRequestDispatcher(userDeptMenuPath);
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
