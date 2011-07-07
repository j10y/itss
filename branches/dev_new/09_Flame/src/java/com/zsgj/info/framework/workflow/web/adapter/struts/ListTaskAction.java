package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.web.adapter.struts.BaseDispatchAction;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.entity.TaskPreAssign;
import com.zsgj.info.framework.workflow.info.TaskInfo;

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
	 * Add By gaowen 2009-10-12 ��ȡǰ̨ҳ�������б�����
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
	
	/**
	 * Add By awen 2011-07-05 ��ȡǰ̨ҳ�������б�����<br>
	 * listTaskDetailByActor��TaskDetail��ʾ������ProcessInfo����Ϣ
	 * @param mapping
	 * @param actionForm
	 * @param request
	 * @param httpServletResponse
	 * @return
	 * @throws Exception
	 */
	public ActionForward listTaskDetailByActor(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String actor = request.getParameter("actor");
		String json = "";
		List<TaskInfo> task = ts.listTasks(actor);
		for(TaskInfo ta : task){
			Map BizParams=ta.getBizParams();
		   String vName = (String)BizParams.get("vProcessDesc");
			json += "{'id':"+ta.getId()+",'taskIdANDprocessId':'"+ta.getId() + "|"+ta.getProcessId()+"','processId':'"+ta.getProcessId()+"','processName':'"+vName+"','taskName':'"+ta.getNodeName()+"','auditPerson':'"+ta.getActorId()+"'},";
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
