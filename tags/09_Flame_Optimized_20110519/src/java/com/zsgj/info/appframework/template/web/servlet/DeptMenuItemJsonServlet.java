package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.service.DeptTemplateMenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

@SuppressWarnings("serial")
public class DeptMenuItemJsonServlet extends HttpServlet {
	
	private DeptTemplateMenuService deptTemplateMenuService = (DeptTemplateMenuService) getBean("deptTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//���ڵ�ID
		String parentId = request.getParameter("id");
		//���Ų˵�ģ��ID
		String dmtId = request.getParameter("dmtId");
		//ϵͳ�˵�ģ��ID
		String smtId = request.getParameter("smtId");
		
		System.out.println("deptMenuJson ���Ų˵�ģ��ID :"+dmtId);
		
		if("0".equals(parentId) && !"".equals(dmtId)){  //���Ų˵�ģ���Ѿ�����,�Ӹ���㿪ʼ���غ��ӽ�㣨���ظ��ڵ�ΪNULL�Ľ�㣩
			request.setAttribute("list", deptTemplateMenuService.findDeptMenuTemplateItemNoParent(dmtId));
		}else if("0".equals(parentId) && "".equals(dmtId)){  //���Ų˵�ģ����δ����,�������κν��
			request.setAttribute("list", null);
		}else{  //���Ų˵�ģ���Ѿ�����,�ӷǸ������غ��ӽ��
			request.setAttribute("list", deptTemplateMenuService.findChildenByParentAndDeptMenuTemplate(parentId, dmtId));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/deptMenu/deptMenu-json.jsp");
		dispatcher.forward(request, response);
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
