package com.digitalchina.info.framework.workflow.web.adapter.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.ProcessService;

public class EndProcessAction extends BaseDispatchAction {
	ProcessService ps = (ProcessService)ContextHolder.getBean("processService"); 
	
	public ActionForward end(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		  String id = request.getParameter("id");
		  long instanceId = Long.parseLong(id);
		  ps.endProcess(instanceId);
		  return mapping.findForward("success");
	}
}
