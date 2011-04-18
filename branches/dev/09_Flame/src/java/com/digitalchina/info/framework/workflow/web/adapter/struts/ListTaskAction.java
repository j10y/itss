package com.digitalchina.info.framework.workflow.web.adapter.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.TaskService;
import com.digitalchina.info.framework.workflow.entity.TaskPreAssign;
import com.digitalchina.info.framework.workflow.info.TaskInfo;

public class ListTaskAction extends BaseDispatchAction {
	TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	
	public ActionForward logon(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {		
		return mapping.findForward("logon");
	}
	
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		String actor = request.getParameter("actor");
	  	List list = ts.listTasks(actor);
	  	request.setAttribute("tasks", list);
	  	request.setAttribute("length", list.size());
		return mapping.findForward("success");
	}
	
	public ActionForward listAll(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		//String actor = request.getParameter("actor");
	  	List list = ts.listAllTasks();
	  	request.setAttribute("tasks", list);
	  	request.setAttribute("length", list.size());
		return mapping.findForward("listAll");
	}
	
	/**
	 * Add By gaowen 2009-10-12 获取前台页面任务列表数据
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ActionForward listTaskByActor(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actor = request.getParameter("actor");
		String json = "";
		List<TaskInfo> task = ts.listTasks(actor);
		for(TaskInfo ta : task){
			Map BizParams=ta.getBizParams();
		   String vName = (String)BizParams.get("vProcessDesc");
			json += "{'id':"+ta.getId()+",'processName':'"+vName+"','taskName':'"+ta.getNodeName()+"','auditPerson':'"+ta.getActorId()+"'},";
		}
		if(json.endsWith(",")){
			json = "{data:[" + json.substring(0, json.length()-1) + "]}";
		}else{
			json = "{data:[" + json + "]}";
		}
		try {			
			response.setCharacterEncoding("utf-8");
			PrintWriter pw = response.getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
			}
		return null;
	}
	
//	public ActionForward cancel(ActionMapping mapping,
//			ActionForm actionForm, HttpServletRequest request,
//			HttpServletResponse httpServletResponse) throws Exception {
//		
//		String strTaskId = request.getParameter("id");
//		long taskId = 0;		
//		if(strTaskId!=null&&!strTaskId.equals("")) {
//			taskId = Long.parseLong(strTaskId);
//		}			
//		ts.cancel(taskId);
//		return mapping.findForward("listAll");
//	
//	}	
}
