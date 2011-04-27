package com.zsgj.info.appframework.template.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.exception.ServiceException;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.service.DepartmentService;

/**
 * 部门菜单模板保存
 * 
 * @Class Name SystemMenuTemplateSearchServlet
 * @author zhangys
 * @Create In Sep 1, 2008 TODO
 */
@SuppressWarnings("serial")
public class DeptMenuTemplateSaveServlet extends HttpServlet {

	private DepartmentService deptService = (DepartmentService) getBean("deptService");

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Department obj = null;
		request.setCharacterEncoding("gbk");
		String js = "";
		List<Department> depts = deptService.findDeptAll();
		for (Department item : depts) {
			Long id = item.getId();
			String departName = item.getDepartName();
//			js += "[" + id + ",\"" + departName + "\"],";
			js += "{id:"+id+",departName:\""+departName+"\"},";
		}
		js = "[" + js.substring(0, js.length() - 1) + "]";

		// JSONArray jsonObject = JSONArray.fromObject(depts);
		System.out.println(js);
		try {
			response.setCharacterEncoding("gbk");
			System.out.println("{success: true,data:" + js + "}");
			response.getWriter().write("{success: true,data:"+ js + "}");
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
