package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import com.digitalchina.info.appframework.template.entity.SystemMenuTemplate;
import com.digitalchina.info.appframework.template.service.SystemTemplateMenuService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

/**
 * 查询系统菜单模板
 * @Class Name SystemMenuTemplateSearchServlet
 * @author hp
 * @Create In Sep 1, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class SystemMenuTemplateSearchServlet extends HttpServlet {
	
	private SystemTemplateMenuService systemMenuService = (SystemTemplateMenuService)getBean("systemTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		SystemMenuTemplate obj = null;
		request.setCharacterEncoding("gbk");
		String templateName = request.getParameter("templateName");
		
		//查询条件为空,列出所有的系统菜单模板
		if("".equals(templateName) || templateName == null){
						
			List sysMenuTemplates = systemMenuService.findSystemMenuTemplates();
			JSONArray jsonObject = JSONArray.fromObject(sysMenuTemplates);
			System.out.println(jsonObject.toString());
			try {
				response.setCharacterEncoding("gbk");
				System.out.println("{success: true,rowCount:"+sysMenuTemplates.size()+",data:"+ jsonObject.toString() + "}");
				response.getWriter().write("{success: true,rowCount:"+sysMenuTemplates.size()+",data:"+ jsonObject.toString() + "}");
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}else{
			List sysMenuTemplates = systemMenuService.findSystemMenuTemplateByName(templateName);
			JSONArray jsonObject = JSONArray.fromObject(sysMenuTemplates);
			System.out.println(jsonObject.toString());			
			try {
				response.setCharacterEncoding("gbk");
				response.getWriter().write("{success: true,rowCount:"+sysMenuTemplates.size()+",data:"+ jsonObject.toString() + "}");
				response.getWriter().flush();
			} catch (IOException e) {
				e.printStackTrace();
			}		
		}
			
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
