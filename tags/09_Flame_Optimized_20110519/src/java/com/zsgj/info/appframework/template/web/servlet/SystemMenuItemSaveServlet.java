package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.entity.SystemMenuTemplate;
import com.zsgj.info.appframework.template.entity.SystemMenuTemplateItem;
import com.zsgj.info.appframework.template.service.SystemTemplateMenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

@SuppressWarnings("serial")
public class SystemMenuItemSaveServlet extends HttpServlet {
	
	private SystemTemplateMenuService systemMenuService = (SystemTemplateMenuService)getBean("systemTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		SystemMenuTemplateItem obj = null;
		request.setCharacterEncoding("gbk");
		String id = request.getParameter("id");
		String number = request.getParameter("number");
		String parentId = request.getParameter("parentId");
		String leaf = request.getParameter("leaf");
		String name = request.getParameter("title");
		String url = request.getParameter("url");
		String smtId = request.getParameter("smtId");
		
		
		if(null != id && !"".equals(id)){
			obj = systemMenuService.findMenuById(id);
			if(obj == null){//infoAdmin/sysMenu/error.jsp
				RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/sysMenu/error.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}else{
			obj = new SystemMenuTemplateItem();
			SystemMenuTemplateItem parentMenu = null;
			if("".equals(parentId) || "0".equals(parentId)){
				obj.setParentMenu(null);
			}else{
				parentMenu = systemMenuService.findMenuById(parentId);
				obj.setParentMenu(parentMenu);
			}
			
			if(!"".equals(smtId) && smtId != null){
				SystemMenuTemplate smt = systemMenuService.findSystemMenuTemplateById(smtId);
				obj.setSystemMenuTemplate(smt);
			}else{
				obj.setSystemMenuTemplate(null);
			}
		}
		obj.setLeafFlag(new Integer(leaf));
		obj.setMenuOrder(new Integer(number));
		obj.setMenuName(name);
		obj.setMenuUrl(url);
		systemMenuService.saveMenu(obj);
		/*try {
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("{success:false}");
			
		}	*/	
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/sysMenu/success.jsp");
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
