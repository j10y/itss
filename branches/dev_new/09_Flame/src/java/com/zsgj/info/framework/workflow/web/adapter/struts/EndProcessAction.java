package com.zsgj.info.framework.workflow.web.adapter.struts;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.ProcessService;

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
	
	/**
	 * 新增
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ActionForward endProcess(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		  StringBuffer json = new StringBuffer("{success:true,msg:'操作成功'}");
		  
		try {
		 	String id = request.getParameter("id");
	 		long instanceId = Long.parseLong(id);
 			ps.endProcess(instanceId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			json = new StringBuffer("{success:false,msg:'操作失败'}");
		}
		httpServletResponse.setContentType("text/html;charset=gbk");
		httpServletResponse.getWriter().write(json.toString());
		httpServletResponse.getWriter().close();
		return null;
	}
}
