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
 * 加载所有的部门信息
 * @Class Name LoadAllDeptInfoServlet
 * @author zhangys
 * @Create In Oct 24, 2008
 * TODO
 */
@SuppressWarnings("serial")
public class LoadAllDeptInfoServlet extends HttpServlet {
	
	private DepartmentService deptService = (DepartmentService)getBean("deptService");
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
	    List<Department> itemList = deptService.findDeptAll();
	    String result = "";
		for(int i = 0; i< itemList.size(); i++){
			Department item = (Department)itemList.get(i);
			Long id = item.getId();
			String name = item.getDepartName();
			result += "[\""+id+"\",\""+name+"\"]";
//			result += "[\"+id+\",\""+name+"\"],";
		}
		result = "["+ result.substring(0, result.length()-1) + "]";
		System.out.println("FindAllDeptInfoServlet 部门信息："+result);
		try {
			response.setCharacterEncoding("utf-8");
			response.getWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
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
