package com.digitalchina.info.framework.workflow.web.adapter.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.DefinitionService;
import com.digitalchina.info.framework.workflow.info.ProcessInfo;

public class ListProcessAction extends BaseDispatchAction {
	DefinitionService ds = (DefinitionService)ContextHolder.getBean("definitionService");
	/**
	 * 2010-05-12 abate by 光顺安 for 具体失效缘由
	 * 删除该方法
	 * 用新的方法替代
	 * public ActionForward listAllActiveProcessInstance
	 */
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		java.util.List<ProcessInfo> list = ds.getAllActiveProcess();
		request.setAttribute("processes", list);
		request.setAttribute("length", list==null?0:list.size());
		return mapping.findForward("success");
	}
	/**
	 * 新建的查询所有活动流程
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ActionForward listAllActiveProcessInstance(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		java.util.List<ProcessInfo> list = ds.getAllActiveProcessInstance();
		request.setAttribute("processes", list);
		request.setAttribute("length", list==null?0:list.size());
		return mapping.findForward("success");
	}
}
