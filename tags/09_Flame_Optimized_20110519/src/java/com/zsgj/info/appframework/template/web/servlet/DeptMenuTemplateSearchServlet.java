package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.appframework.template.entity.DeptMenuTemplate;
import com.zsgj.info.appframework.template.service.DeptTemplateMenuService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Department;

/**
 * 查询部门菜单模板
 * 
 * @Class Name SystemMenuTemplateSearchServlet
 * @author zhangys
 * @Create In Sep 1, 2008 TODO
 */
@SuppressWarnings("serial")
public class DeptMenuTemplateSearchServlet extends HttpServlet {

	private DeptTemplateMenuService deptMenuTemplateService = (DeptTemplateMenuService) getBean("deptTemplateMenuService");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Department obj = null;
		String js = "";
		List<DeptMenuTemplate> dmtList = deptMenuTemplateService.findDeptMenuTemplates();
		for (DeptMenuTemplate item : dmtList) {
			Long id = item.getId();
			String templateName = item.getTemplateName();
			String systemMenuTemplate = item.getSystemMenuTemplate().getTemplateName();
			String dept = item.getDept().getDepartName();
			Long smtId = item.getSystemMenuTemplate().getId();
			Integer flag = item.getAdminFlag();
			String adminFlag = "";
			if(flag == 1){
				adminFlag += "是";
			}else{
				adminFlag += "否";
			}
			js += "{id:"+id+",systemMenuTemplate:\""+systemMenuTemplate+"\",templateName:\""+templateName+"\",dept:\""+dept+"\",adminFlag:\""+adminFlag+"\",smtId:"+smtId+"},";
		}
		
		js = "[" + js.substring(0, js.length() - 1) + "]";

		try {
			response.setCharacterEncoding("utf-8");
			System.out.println("{success: true,data:" + js + "}");
			response.getWriter().write("{success: true,rowCount:"+dmtList.size()+",data:"+ js + "}");
//			response.getWriter().write(js);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * 返回spring管理的服务service
	 * 
	 * @param name
	 * @return
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("没有名字为：" + name + "的服务！！");
		}
		return serviceBean;
	}
}
