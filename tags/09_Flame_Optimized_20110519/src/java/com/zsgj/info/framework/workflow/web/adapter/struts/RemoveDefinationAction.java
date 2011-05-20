package com.zsgj.info.framework.workflow.web.adapter.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.DefinitionService;

public class RemoveDefinationAction extends BaseDispatchAction {
	DefinitionService ds = (DefinitionService)ContextHolder.getBean("gefinitionService");
	
	public ActionForward remove(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
			
		String id = request.getParameter("id");
		long processDefinitionId = Long.parseLong(id);
		ds.deleteDefinition(processDefinitionId);
		return mapping.findForward("success");
	}
}
