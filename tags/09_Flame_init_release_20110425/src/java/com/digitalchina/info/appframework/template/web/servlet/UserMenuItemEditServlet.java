package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.appframework.template.entity.UserMenu;
import com.digitalchina.info.appframework.template.entity.UserMenuItem;
import com.digitalchina.info.appframework.template.service.UserTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

/**
 * 后台菜单项修改
 * @Class Name UserMenuItemEditServlet
 * @author hp
 * @Create In Oct 24, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class UserMenuItemEditServlet extends HttpServlet {
	
	private UserTemplateMenuService userMenuService = (UserTemplateMenuService) getBean("userTemplateMenuService");
	
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
		String umtId = request.getParameter("umtId");
		
		UserMenuItem obj = null;
		
		if(null != id){
			
			obj = userMenuService.findMenuById(id);
		}else{
			obj = new UserMenuItem();
			UserMenuItem parentMenu = null;
			if("0".equals(parentId)){
				obj.setParentMenu(null);
			}else{
				parentMenu = userMenuService.findMenuById(parentId);
				obj.setParentMenu(parentMenu);
			}
			
			obj.setLeafFlag(new Integer(leaf));
			obj.setMenuOrder(new Integer(number));
			if(!"".equals(umtId)){
				UserMenu userMenu = userMenuService.findUserMenuById(umtId);
				obj.setUserMenu(userMenu);
			}else{
				obj.setUserMenu(null);
			}
		}
		request.setAttribute("obj", obj);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/userMenu/userMenu-edit.jsp");
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
