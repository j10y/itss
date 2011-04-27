package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.ContextService;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.base.FormHelper;
import com.zsgj.info.framework.workflow.info.TaskInfo;

public class ExecuteTaskAction extends BaseDispatchAction {
	TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	ContextService cs = (ContextService)ContextHolder.getBean("contextService");
	
	@SuppressWarnings("unchecked")
	public ActionForward audit(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String strTaskId = request.getParameter("id");
		long taskId = 0;		
		if(strTaskId!=null&&!strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		

		
		TaskInfo ti = ts.getTaskInfo(taskId);
		String formurl = FormHelper.findForm(ti.getDefinitionName(),ti.getNodeName());
		String done = request.getParameter("done");
		
		if(done==null) {//执行前显示空的审批界面
			if(request.getParameter("system")!=null) {//管理界面
				formurl = "/infoAdmin/workflow/defaultForm.jsp";
			}
			else {
				formurl = request.getContextPath()+formurl+"?id="+taskId;
			}
			httpServletResponse.sendRedirect(formurl);
			return null; 
		}
		else {//实际执行任务
			Enumeration para = request.getParameterNames();
			//如果上下文中有对应名称的变量，则把页面数据传进去
			Map mapVar = cs.listVariablesByTaskId(taskId);
			for (;para.hasMoreElements();) {
				String varName = (String)para.nextElement();				
				if(mapVar.containsKey(varName)) {				
					String v = request.getParameter(varName);
					if(request.getMethod().equalsIgnoreCase("get")) {
						//字符集转换
						v = new String(v.getBytes("iso8859-1"),"gbk");
						cs.setVariableByTaskId(taskId, varName, v);
					}
				}
		    }
			ts.execute(taskId);
			return mapping.findForward("success");
		}	
	}
}
