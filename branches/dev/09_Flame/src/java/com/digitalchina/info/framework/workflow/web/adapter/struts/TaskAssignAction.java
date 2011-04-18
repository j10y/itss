package com.digitalchina.info.framework.workflow.web.adapter.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.dao.support.Page;
import com.digitalchina.info.framework.security.entity.Department;
import com.digitalchina.info.framework.security.service.DepartmentService;
import com.digitalchina.info.framework.util.HttpUtil;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.DefinitionService;
import com.digitalchina.info.framework.workflow.TaskAssignService;
import com.digitalchina.info.framework.workflow.TaskService;
import com.digitalchina.info.framework.workflow.entity.DefinitionPreAssign;
import com.digitalchina.info.framework.workflow.entity.WorkflowRole;
import com.digitalchina.info.framework.workflow.entity.DefinitionInfo;
import com.digitalchina.info.framework.workflow.info.NodeInfo;

public class TaskAssignAction extends BaseDispatchAction {
	DefinitionService ds = (DefinitionService)getBean("definitionService");
	TaskService ts = (TaskService)getBean("taskService");
	TaskAssignService tas = (TaskAssignService)getBean("taskAssignService");
	DepartmentService dpts = (DepartmentService)getBean("deptService");
	
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		//所有流程定义
		java.util.List<DefinitionInfo> list = ds.getLatestDefinitions();
		request.setAttribute("definitions", list);
		
		String definiName = request.getParameter("definiName");
		request.setAttribute("definiName", definiName);
		
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		
		Page page = tas.listDefinitionPreAssign(definiName, pageNo, pageSize);
		request.setAttribute("page", page);
		
		return mapping.findForward("success");
	}
	
//	public ActionForward toAdd(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse httpServletResponse) throws Exception {
//	
//		String title = "增加流程预指派";
//		request.setAttribute("title", title);
//		String definitionName = request.getParameter("def");
//		String definitionDesc = "";
//		String pageNo = request.getParameter("pageNo");
//		
////任务列表
////		List<NodeInfo> list = ds.getTaskNodes(definitionName);
////		request.setAttribute("taskNodes",list);
//		
////所有流程定义
//		java.util.List<DefinitionInfo> defs = ds.getLatestDefinitions();
//		for(DefinitionInfo def: defs) {
//			if(def.getName().trim().equalsIgnoreCase(definitionName.trim())) {
//				definitionDesc = def.getDescription();
//				request.setAttribute("definitionDesc", definitionDesc);
//				request.setAttribute("definitionName", def.getName());
//				break;
//			}
//		}
//		
////所有部门
//		List<Department> departments = dpts.findDeptAll();
//		//getService().findAll(Department.class);
//		request.setAttribute("departments", departments);
//		
//		return mapping.findForward("add");
//	}
//	
	/**
	 * 保存增加时信息
	 * @Methods Name saveAdd
	 * @Create In Dec 9, 2008 By yang
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception 
	 * @ReturnType ActionForward
	 */
	public ActionForward saveAdd(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String pageNo = request.getParameter("pageNo");
		pageNo = pageNo.equals("")?"1":pageNo;
		
		String definitionName = request.getParameter("definitionName");
		String definitionDesc = request.getParameter("definitionDesc");
		String departmentCode = request.getParameter("departmentCode")+"";
		List<NodeInfo> nodes = ds.getTaskNodes(definitionName);
		tas.addDefinitionPreAssign(definitionName, definitionDesc, departmentCode,nodes);	
		
		return HttpUtil.redirect("taskPreAssign.do?methodCall=list&pageNo="+pageNo); //+pageNo+"&level="+level
	}
	
	public ActionForward deleteDefPreassign(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String pageNo = request.getParameter("pageNo");
		pageNo = pageNo==null||pageNo.equals("")?"1":pageNo;
		
		String definitionName = request.getParameter("def");
		tas.removeDefinitionPreAssign(definitionName);
		
		
		return HttpUtil.redirect("taskPreAssign.do?methodCall=list&pageNo="+pageNo); //+pageNo+"&level="+level

	}
	
	
	public ActionForward toForm(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
	
		String title = "修改流程预指派";
		request.setAttribute("title", title);
		String id = request.getParameter("id");
		DefinitionPreAssign detail = (DefinitionPreAssign) super.getService().find(DefinitionPreAssign.class, id);
		request.setAttribute("detail", detail);
		
//		所有流程定义
		request.setAttribute("pageNo", (String)request.getParameter("pageNo"));		
		List<WorkflowRole> workflowRoles = getService().find(WorkflowRole.class,"deleteFlag",Integer.valueOf(0));		
		request.setAttribute("workflowRoles", workflowRoles);				
		return mapping.findForward("detail");
	}
	/**
	 * 保存流程角色修改
	 * @Methods Name save
	 * @Create In Dec 9, 2008 By yang
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception 
	 * @ReturnType ActionForward
	 */
	public ActionForward save(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String strPage = (String)request.getAttribute("pageNo");		
		int pageNo = HttpUtil.getInt(request, "pageNo", 1);
		if(strPage!=null) {
			pageNo = Integer.parseInt(strPage);
		}
		String id = request.getParameter("id");
		
		DefinitionPreAssign dpa = (DefinitionPreAssign)getService().find(DefinitionPreAssign.class, id);
		String workflowRoleId = request.getParameter("workflowRoleId");
		
		WorkflowRole workflowRole = null;
		if(workflowRoleId!=null&&!workflowRoleId.trim().equals("")) {
			workflowRole = (WorkflowRole)getService().find(WorkflowRole.class, workflowRoleId);
		}
		 
		dpa.setWorkflowRole(workflowRole);
		getService().save(dpa);
				
		return HttpUtil.redirect("taskPreAssign.do?methodCall=list&pageNo="+pageNo); //+pageNo+"&level="+level
	}
	

}
