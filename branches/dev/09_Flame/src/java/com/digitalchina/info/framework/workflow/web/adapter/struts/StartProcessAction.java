package com.digitalchina.info.framework.workflow.web.adapter.struts;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.web.adapter.struts.BaseDispatchAction;
import com.digitalchina.info.framework.workflow.DefinitionService;
import com.digitalchina.info.framework.workflow.ProcessService;
import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.info.ActorInfo;
import com.digitalchina.info.framework.workflow.info.NodeInfo;

public class StartProcessAction extends BaseDispatchAction {
	DefinitionService ds = (DefinitionService)ContextHolder.getBean("definitionService"); 
	ProcessService ps = (ProcessService)ContextHolder.getBean("processService");
	
	@SuppressWarnings("unchecked")
	public ActionForward start(ActionMapping mapping,
			ActionForm actionForm, HttpServletRequest request,
			HttpServletResponse httpServletResponse) throws Exception {
		
		  List list = (List)request.getSession().getAttribute("taskNodes");
		  Map<String,List<ActorInfo>> map = new HashMap<String,List<ActorInfo>>();
		  for(int i=0;i<list.size();i++){
			  String name = ((NodeInfo)list.get(i)).getName();
			  String actor = request.getParameter(name);
			  map.put(name,new ActorInfo(actor).toList());
		  }
		  //设置启动者，以后需要从系统的登陆用户信息中提取
		  String creator = "admin";
		  map.put(WorkflowConstants.PROCESS_CREATOR_FLAG,new ActorInfo(creator).toList());
		  String pdn = request.getParameter("pdn");  
		  //long id = ps.createProcess(pdn,map,null);
		  long id = ps.createProcess(pdn,creator,null);
		  request.setAttribute("id", id);
		  
		  return mapping.findForward("success");
	}
}
