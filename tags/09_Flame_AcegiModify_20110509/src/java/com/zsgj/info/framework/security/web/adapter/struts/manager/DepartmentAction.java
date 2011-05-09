package com.zsgj.info.framework.security.web.adapter.struts.manager;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.service.DepartmentService;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;

public class DepartmentAction extends BaseDispatchAction{
	private DepartmentService departmentService = (DepartmentService)getBean("deptService");
    /**
     * 查询出所有的部门以树的形式显示
     * @Methods Name queryAllDepartment
     * @Create In Apr 20, 2009 By sujs
     * @param mapping
     * @param actionForm
     * @param request
     * @param httpServletResponse
     * @return
     * @throws Exception ActionForward
     */
	@SuppressWarnings("unchecked")
	public ActionForward queryAllDepartment(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String departmentCode = request.getParameter("id");
		List<Department> storeDepartment = new ArrayList<Department>();
		if (departmentCode.equals("-1")) {
			List<Department> childDepartmentList = departmentService.findRootDepartments("50000075");
			for (Department department : childDepartmentList) {
				department.setDepartName(department.getDepartName()+"（<font color=blue >"+department.getId()+"</font>）");
				storeDepartment.add(department);
			}
		} else {
			 List<Department> childDepartmentList = departmentService.findRootDepartments(departmentCode);
				for (Department department : childDepartmentList) {
			department.setDepartName(department.getDepartName()+"（<font color=blue>"+department.getId()+"</font>）");
					storeDepartment.add(department);
				}
		}
		request.setAttribute("scirelation", storeDepartment);
		ActionForward forward = mapping.findForward("success");
		return forward;
	}

}