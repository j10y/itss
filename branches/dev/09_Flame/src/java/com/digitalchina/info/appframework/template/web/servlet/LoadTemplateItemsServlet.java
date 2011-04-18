package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.appframework.template.entity.Template;
import com.digitalchina.info.appframework.template.service.TemplateService;
import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;

/**
 * ����ģ����
 * @Class Name LoadTemplateItemsServlet
 * @author hp
 * @Create In Sep 9, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class LoadTemplateItemsServlet extends HttpServlet {
	
	private TemplateService templateService = (TemplateService) getBean("templateService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//ģ��id
		String tmtId = request.getParameter("tmtId");	
		Template  template = templateService.findTemplateById(tmtId);
		
		String json = templateService.findTemplateItemWithChild(template);
		System.out.println("LoadTemplateItemsServlet ����ģ������Ϣ��"+json);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * ����spring����ķ���service
	 * @param name
	 * @return
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if(serviceBean==null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
}
