package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.appframework.template.service.UserTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.util.PropertiesUtil;

/**
 * 前台修改指定用户的菜单项
 * (用户通过前台界面调整菜单结点的顺序和设置菜单结点的是否可见)
 * @Class Name UserMenuItemModifyServlet
 * @author hp
 * @Create In Sep 4, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class UserMenuItemModifyServlet extends HttpServlet {
	
	private UserTemplateMenuService userMenuService = (UserTemplateMenuService) getBean("userTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//父节点ID
		String parentId = request.getParameter("id");
		//用户菜单模板ID
		String userId = request.getParameter("userId");
		
		System.out.println("调用userMenuModify 用户ID :"+userId);
		
		if("0".equals(parentId)){  //从根结点开始加载孩子结点（加载父节点为NULL的结点）
			request.setAttribute("list", userMenuService.findUserMenuItemAllNoParentByUserId(userId));
		}else{  //从非根结点加载孩子结点
			request.setAttribute("list", userMenuService.findUserSettingMenuChildenByParentAndUserId(parentId, userId));
		}
		
		String forward = PropertiesUtil.getProperties("user.menu.front.usermenu.url");
		RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
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
