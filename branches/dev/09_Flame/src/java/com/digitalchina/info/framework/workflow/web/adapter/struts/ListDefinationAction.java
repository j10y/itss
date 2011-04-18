package com.digitalchina.info.framework.workflow.web.adapter.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.DefinitionService;
import com.digitalchina.info.framework.workflow.entity.DefinitionInfo;

public class ListDefinationAction extends BaseDispatchAction {
	DefinitionService ds = (DefinitionService)ContextHolder.getBean("definitionService");
	
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		  
		java.util.List<DefinitionInfo> list = ds.getAllDefinitions();

		request.setAttribute("definations", list);
		return mapping.findForward("success");
	}
}
