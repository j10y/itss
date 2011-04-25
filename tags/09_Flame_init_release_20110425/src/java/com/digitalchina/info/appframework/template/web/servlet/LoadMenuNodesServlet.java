package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.appframework.template.entity.UserMenuItem;
import com.digitalchina.info.appframework.template.service.UserTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.context.UserContext;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.util.PropertiesUtil;

@SuppressWarnings("serial")
public class LoadMenuNodesServlet extends HttpServlet {
	
	private UserTemplateMenuService userMenuService = (UserTemplateMenuService) getBean("userTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		UserInfo user = UserContext.getUserInfo();
		
		String parentId = request.getParameter("id");	
		Long userId = user.getId();
		List<UserMenuItem> objo = userMenuService.findChildenByParentAndUserId(parentId, userId.toString());
		 /* start modify by tongjp 20091202 merge menu with the same menuname*/
		List<UserMenuItem> obj=new ArrayList();
		obj=objo;
		UserMenuItem parentMenu=userMenuService.findMenuById(parentId);
		String umn=parentMenu.getMenuName();
		 List<UserMenuItem> itemList = userMenuService.findAllMenuTitleByUserId(userId.toString());
	    for(UserMenuItem um:itemList){
	    	String text = um.getMenuName();
	    	Long id = um.getId();
	    	if(text.equals(umn)&&!id.equals(parentMenu.getId())){
	    		List<UserMenuItem> obList = userMenuService.findChildenByParentAndUserId(id.toString(), userId.toString());
	    		for(UserMenuItem u:obList){
	    			u.setParentMenu(parentMenu);
	    			obj.add(u);
	    		}
	    	}
	    }
	    /* end modify by tongjp 20091202 merge menu with the same menuname*/
		//List<UserMenuItem> obj = userMenuService.findChildenByParentAndUserId(parentId, userId.toString());
		request.setAttribute("list", obj);
		String dispatchUrl = PropertiesUtil.getProperties("user.menu.front.loadMenu.url", "/user/menu/menu-json.jsp");
		RequestDispatcher dispatcher = request.getRequestDispatcher(dispatchUrl);
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
