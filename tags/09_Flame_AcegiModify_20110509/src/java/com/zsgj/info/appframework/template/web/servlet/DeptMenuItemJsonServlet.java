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
		//父节点ID
		String parentId = request.getParameter("id");
		//部门菜单模板ID
		String dmtId = request.getParameter("dmtId");
		//系统菜单模板ID
		String smtId = request.getParameter("smtId");
		
		System.out.println("deptMenuJson 部门菜单模板ID :"+dmtId);
		
		if("0".equals(parentId) && !"".equals(dmtId)){  //部门菜单模板已经建立,从根结点开始加载孩子结点（加载父节点为NULL的结点）
			request.setAttribute("list", deptTemplateMenuService.findDeptMenuTemplateItemNoParent(dmtId));
		}else if("0".equals(parentId) && "".equals(dmtId)){  //部门菜单模板尚未建立,不加载任何结点
			request.setAttribute("list", null);
		}else{  //部门菜单模板已经建立,从非根结点加载孩子结点
			request.setAttribute("list", deptTemplateMenuService.findChildenByParentAndDeptMenuTemplate(parentId, dmtId));
		}
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/deptMenu/deptMenu-json.jsp");
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
