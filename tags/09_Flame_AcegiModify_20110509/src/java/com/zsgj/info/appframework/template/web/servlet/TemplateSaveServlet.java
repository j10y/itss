package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.entity.Menu;
import com.zsgj.info.appframework.template.service.MenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

/**
 * 模板保存
 * @Class Name TemplateSaveServlet
 * @author zhangys
 * @Create In Oct 24, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class TemplateSaveServlet extends HttpServlet {
	
	private MenuService menuService = (MenuService)getBean("menuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		Menu obj = null;
		request.setCharacterEncoding("gbk");
		String id = request.getParameter("id");
		String number = request.getParameter("number");
		String parentId = request.getParameter("parentId");
		String leaf = request.getParameter("leaf");
		String name = request.getParameter("title");
		String url = request.getParameter("url");
		
		if(null != id && !"".equals(id)){
			obj = menuService.findMenuById(id);
			if(obj == null){
				RequestDispatcher dispatcher = request.getRequestDispatcher("/user/menu/error.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}else{
			obj = new Menu();
			Menu parentMenu = null;
			if(null !=id && !"".equals(parentId)){
				parentMenu = menuService.findMenuById(parentId);
			}
			obj.setParentMenu(parentMenu);
		}
		obj.setLeafFlag(new Integer(leaf));
		obj.setMenuOrder(new Integer(number));
		obj.setMenuName(name);
		obj.setMenuUrl(url);
		
		menuService.saveMenu(obj);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/user/menu/success.jsp");
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
