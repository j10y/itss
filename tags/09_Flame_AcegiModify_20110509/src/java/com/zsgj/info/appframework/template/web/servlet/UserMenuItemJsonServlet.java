package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.service.UserTemplateMenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

/**
 * 后台菜单项JSON
 * @Class Name UserMenuItemJsonServlet
 * @author zhangys
 * @Create In Oct 24, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class UserMenuItemJsonServlet extends HttpServlet {
	
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
		String umtId = request.getParameter("umtId");
		
		System.out.println("调用userMenuJson 用户菜单模板ID :"+umtId);
		
		if("0".equals(parentId) && !"".equals(umtId)){  //用户菜单模板已经建立,从根结点开始加载孩子结点（加载父节点为NULL的结点）
			request.setAttribute("list", userMenuService.findUserMenuItemNoParent(umtId));
		}else if("0".equals(parentId) && "".equals(umtId)){  //用户菜单模板尚未建立,不加载任何结点
			request.setAttribute("list", null);
		}else{  //用户菜单模板已经建立,从非根结点加载孩子结点
			request.setAttribute("list", userMenuService.findChildenByParentAndUserMenu(parentId, umtId));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/userMenu/userMenu-json.jsp");
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
