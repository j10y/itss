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
 * ǰ̨�޸�ָ���û��Ĳ˵���
 * (�û�ͨ��ǰ̨��������˵�����˳������ò˵������Ƿ�ɼ�)
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
		//���ڵ�ID
		String parentId = request.getParameter("id");
		//�û��˵�ģ��ID
		String userId = request.getParameter("userId");
		
		System.out.println("����userMenuModify �û�ID :"+userId);
		
		if("0".equals(parentId)){  //�Ӹ���㿪ʼ���غ��ӽ�㣨���ظ��ڵ�ΪNULL�Ľ�㣩
			request.setAttribute("list", userMenuService.findUserMenuItemAllNoParentByUserId(userId));
		}else{  //�ӷǸ������غ��ӽ��
			request.setAttribute("list", userMenuService.findUserSettingMenuChildenByParentAndUserId(parentId, userId));
		}
		
		String forward = PropertiesUtil.getProperties("user.menu.front.usermenu.url");
		RequestDispatcher dispatcher = request.getRequestDispatcher(forward);
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
