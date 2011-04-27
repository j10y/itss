package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.entity.DeptMenuTemplate;
import com.zsgj.info.appframework.template.entity.DeptMenuTemplateItem;
import com.zsgj.info.appframework.template.service.DeptTemplateMenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;

/**
 * 后台部门菜单保存
 * @Class Name DeptMenuItemSaveServlet
 * @author zhangys
 * @Create In Oct 24, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class DeptMenuItemSaveServlet extends HttpServlet {
	
	private DeptTemplateMenuService deptMenuService = (DeptTemplateMenuService)getBean("deptTemplateMenuService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	    
		DeptMenuTemplateItem obj = null;
		request.setCharacterEncoding("gbk");
		String id = request.getParameter("id");
		String number = request.getParameter("number");
		String parentId = request.getParameter("parentId");
		String leaf = request.getParameter("leaf");
		String name = request.getParameter("title");
		String url = request.getParameter("url");
//		String smtId = request.getParameter("smtId");
		String dmtId = request.getParameter("dmtId");
		
		if(null != id && !"".equals(id)){
			obj = deptMenuService.findMenuById(id);
			if(obj == null){
				RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/deptMenu/error.jsp");
				dispatcher.forward(request, response);
				return;
			}
		}else{
			obj = new DeptMenuTemplateItem();
			DeptMenuTemplateItem parentMenu = null;
			if("".equals(parentId) || "0".equals(parentId)){
				obj.setParentMenu(null);
			}else{
				parentMenu = deptMenuService.findMenuById(parentId);
				obj.setParentMenu(parentMenu);
			}
			
			if(!"".equals(dmtId) && dmtId != null){
				DeptMenuTemplate dmt = deptMenuService.findDeptMenuTemplateById(dmtId);
				obj.setDeptMenuTemplate(dmt);
			}else{
				obj.setDeptMenuTemplate(null);
			}
		}
		obj.setLeafFlag(new Integer(leaf));
		obj.setMenuOrder(new Integer(number));
		obj.setMenuName(name);
		obj.setMenuUrl(url);
		deptMenuService.saveMenu(obj);

		RequestDispatcher dispatcher = request.getRequestDispatcher("/infoAdmin/template/deptMenu/success.jsp");
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
