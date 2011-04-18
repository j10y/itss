package com.digitalchina.info.appframework.template.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.exception.ServiceException;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.service.DepartmentService;

/**
 *�û��˵�ģ�屣��
 * 
 * @Class Name SystemMenuTemplateSearchServlet
 * @author hp
 * @Create In Sep 1, 2008 TODO
 */
@SuppressWarnings("serial")
public class UserMenuTemplateSaveServlet extends HttpServlet {

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
	 * ����spring����ķ���service
	 * 
	 * @param name
	 * @return
	 */
	protected Object getBean(String name) {
		Object serviceBean = ContextHolder.getBean(name);
		if (serviceBean == null) {
			throw new ServiceException("û������Ϊ��" + name + "�ķ��񣡣�");
		}
		return serviceBean;
	}
}
