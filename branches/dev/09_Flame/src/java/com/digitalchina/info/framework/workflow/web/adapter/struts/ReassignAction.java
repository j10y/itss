package com.digitalchina.info.framework.workflow.web.adapter.struts;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.TaskService;
import com.digitalchina.info.framework.workflow.info.TaskInfo;

public class ReassignAction extends BaseDispatchAction {
	TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	
	public ActionForward reassign(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		  String id = request.getParameter("id");
		  String actor = request.getParameter("actor");
		  if(actor.trim().equals("")){
			  return mapping.findForward("noactor");
		  }
		  else{
			  long tid = Long.parseLong(id);	//Task Id		  
			  TaskInfo ti = ts.getTaskInfo(tid);
			  ts.reAssign(tid,actor);
			  request.setAttribute("id", tid);
			  request.setAttribute("name", ti.getName());
			  request.setAttribute("actor", actor);
			  return mapping.findForward("success");
		  }
	}
	
	public ActionForward reassignByActor(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		  String json = "";
		  String id = request.getParameter("id");
		  String actor = request.getParameter("actor");
		  long tid = Long.parseLong(id);	//Task Id		  
		  TaskInfo ti = ts.getTaskInfo(tid);
		  ts.reAssign(tid,actor);
		  if(ti!=null){
				 json ="{success:true}";
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
}
