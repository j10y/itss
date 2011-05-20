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
 * ��̨�˵���JSON
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
		//���ڵ�ID
		String parentId = request.getParameter("id");
		//�û��˵�ģ��ID
		String umtId = request.getParameter("umtId");
		
		System.out.println("����userMenuJson �û��˵�ģ��ID :"+umtId);
		
		if("0".equals(parentId) && !"".equals(umtId)){  //�û��˵�ģ���Ѿ�����,�Ӹ���㿪ʼ���غ��ӽ�㣨���ظ��ڵ�ΪNULL�Ľ�㣩
			request.setAttribute("list", userMenuService.findUserMenuItemNoParent(umtId));
		}else if("0".equals(parentId) && "".equals(umtId)){  //�û��˵�ģ����δ����,�������κν��
			request.setAttribute("list", null);
		}else{  //�û��˵�ģ���Ѿ�����,�ӷǸ������غ��ӽ��
			request.setAttribute("list", userMenuService.findChildenByParentAndUserMenu(parentId, umtId));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/userMenu/userMenu-json.jsp");
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
