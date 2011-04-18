package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.appframework.template.entity.SystemMenuTemplate;
import com.digitalchina.info.appframework.template.service.SystemTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.util.HttpUtil;

/**
 * 系统菜单基本信息保存
 * @Class Name SystemMenuTemplateSaveServlet
 * @author zhangys
 * @Create In Aug 31, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class SystemMenuTemplateSaveServlet extends HttpServlet {
	
	private SystemTemplateMenuService systemMenuService = (SystemTemplateMenuService)getBean("systemTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		SystemMenuTemplate obj = null;
//		String id = request.getParameter("id");
//		request.setCharacterEncoding("gbk");
		String tempateName = HttpUtil.ConverUnicodeToString(request.getParameter("name"));
		String descn = HttpUtil.ConverUnicodeToString(request.getParameter("descn"));
		
		obj = new SystemMenuTemplate();
		obj.setTemplateName(tempateName);
		obj.setDescn(descn);
		
		obj = systemMenuService.saveSystemMenuTemplate(obj);
		System.out.println(obj.getId());
		request.getSession().setAttribute("sysMenuTemplate", obj);
		
		response.getWriter().write("{success: true,id:"+obj.getId()+"}");
//		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/sysMenu/success.jsp");
//		dispatcher.forward(request, response);		
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
