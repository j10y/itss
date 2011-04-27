package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.entity.UserMenu;
import com.zsgj.info.appframework.template.entity.UserMenuItem;
import com.zsgj.info.appframework.template.service.UserTemplateMenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

/**
 * 后台用户菜单项保存
 * @Class Name UserMenuItemSaveServlet
 * @author zhangys
 * @Create In Oct 24, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class UserMenuItemSaveServlet extends HttpServlet {
	
	private UserTemplateMenuService userMenuService = (UserTemplateMenuService)getBean("userTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		UserMenuItem obj = null;
		request.setCharacterEncoding("gbk");
		String id = request.getParameter("id");
		String number = request.getParameter("number");
		String parentId = request.getParameter("parentId");
		String leaf = request.getParameter("leaf");
		String name = request.getParameter("title");
		String url = request.getParameter("url");
		String umtId = request.getParameter("umtId");
		
		
		if(null != id && !"".equals(id)){
			obj = userMenuService.findMenuById(id);
			if(obj == null){
				RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/userMenu/error.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}else{
			obj = new UserMenuItem();
			UserMenuItem parentMenu = null;
			if("".equals(parentId) || "0".equals(parentId)){
				obj.setParentMenu(null);
			}else{
				parentMenu = userMenuService.findMenuById(parentId);
				obj.setParentMenu(parentMenu);
			}
			
			if(!"".equals(umtId) && umtId != null){
				UserMenu umt = userMenuService.findUserMenuById(umtId);
				obj.setUserMenu(umt);
			}else{
				obj.setUserMenu(null);
			}
		}
		obj.setLeafFlag(new Integer(leaf));
		obj.setMenuOrder(new Integer(number));
		obj.setMenuName(name);
		obj.setMenuUrl(url);
		userMenuService.saveMenu(obj);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/userMenu/success.jsp");
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
