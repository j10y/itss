package com.digitalchina.info.framework.workflow.web.adapter.struts;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.ProcessService;
import com.digitalchina.info.framework.workflow.TaskService;
import com.digitalchina.info.framework.workflow.info.HistoryInfo;
import com.digitalchina.info.framework.workflow.info.TaskInfo;

public class ListHistoryAction extends BaseDispatchAction {
	ProcessService ps = (ProcessService)ContextHolder.getBean("processService");
	TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	private Service bs = getService();
	public ActionForward list(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		String taskId = request.getParameter("taskid");	 
		String procId = request.getParameter("procid");		
		long instId = 0;
		if(procId!=null&&procId.trim().length()!=0) {
			instId = Long.parseLong(procId);
			List tasks = ps.getActiveTasks(instId);//得到所有未结束的任务
			if(!tasks.isEmpty()) {
				taskId = ((TaskInfo)tasks.get(0)).getId()+"";
			}
		}else if(taskId!=null&&taskId.trim().length()!=0){
			instId = ts.getProcessInfo(Long.parseLong(taskId)).getId();
		}else {
			log.error("ListHistoryAction参数不对");
			return mapping.findForward("error");
		}
		
	 	List<HistoryInfo> list = ps.getHistory(instId);
	 	List tasks = ps.getActiveTasks(instId);
	 	for(int i=0;i<tasks.size();i++) {
	 		TaskInfo ti = (TaskInfo)tasks.get(i);
	 		HistoryInfo hi = new HistoryInfo(ti);
	 		hi.setTaskName(ti.getName()+"【等待执行】");
	 		list.add(hi);
	 	}
	 	request.setAttribute("history", list);
	 	request.setAttribute("instId", instId+"");
	 	request.setAttribute("taskId", taskId+"");
	 	request.setAttribute("length", list.size());
		return mapping.findForward("success");
	}
	
	public ActionForward view(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {		
		//String id = request.getParameter("id");
		
		String taskId = request.getParameter("id");	 
		String procId = request.getParameter("pid");	
		if(taskId==null||taskId.trim().length()==0) {
			long instId = 0;
			if(procId!=null&&procId.trim().length()!=0) {
				instId = Long.parseLong(procId);
				List tasks = ps.getActiveTasks(instId);
				if(!tasks.isEmpty()) {
					taskId = ((TaskInfo)tasks.get(0)).getId()+"";
				}else{
					List task = ps.getAllTasks(instId);
					if(!task.isEmpty()) {
						taskId = ((TaskInfo)task.get(task.size()-1)).getId()+"";
					}
					
				}
			}
		}
		request.setAttribute("id", taskId);
		return mapping.findForward("view");
	}

}
