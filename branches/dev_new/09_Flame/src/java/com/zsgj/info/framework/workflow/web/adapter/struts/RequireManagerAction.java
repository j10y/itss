package com.zsgj.info.framework.workflow.web.adapter.struts;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.ContextService;
import com.zsgj.info.framework.workflow.ParameterService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.base.FormHelper;
import com.zsgj.info.framework.workflow.entity.DefinitionInfo;
import com.zsgj.info.framework.workflow.info.HistoryInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;

public class RequireManagerAction extends BaseAction{
	private TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	private ParameterService pms = (ParameterService)ContextHolder.getBean("parameterService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private ContextService vm = (ContextService)ContextHolder.getBean("contextService");
	private TaskService tm = (TaskService)ContextHolder.getBean("taskService");
	private ProcessService ps = (ProcessService)ContextHolder.getBean("processService");
	
	/**
	 * 提出申请（启动工作流）,需要考虑到一个节点有可能指派给多人的情况
	 * 支持单节点单行数据指派，形式为a|b|c,
	 * @Methods Name apply
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String apply() throws Exception{
		String json = ""; 
		//需要的参数
		String definitionName = super.getRequest().getParameter("defname");
		
		//definitionName=StringUtils.substringAfter(definitionName, "$");
		//definitionName = definitionName.substring(8);
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//需要进入上下文的业务参数
		Map<String,String> mapBizz = new HashMap<String,String>();
		if(buzzParameters!=null&&!buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while(it.hasNext()) {
				String key = (String)it.next();
				String value = (String)jo.get(key);					
				mapBizz.put(key, value);
			}
		}
		String creator = UserContext.getUserInfo().getUserName();
		long instanceId = ps.createProcess(definitionName,creator,mapBizz);
		json = "{success:true,id:'"+instanceId+"'}";
		try {			
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();	
			pw.write(json);		
			} catch (IOException e) {
			e.printStackTrace();
		}
		return null;				
	}
	/**
	 * 审批任务（执行任务节点）
	 * @Methods Name audit
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	public String audit(HttpServletRequest request) {
		String json = "";
		//不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("id");
		String done = request.getParameter("done");
		long taskId = 0;		
		if(strTaskId!=null&&!strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		//找到节点信息和相应小form信息		
		TaskInfo ti = tm.getTaskInfo(taskId);
		String formName = FormHelper.findForm(ti.getDefinitionName(),ti.getNodeName()).trim();
		
		
		if(done==null) {//执行前显示	
			json = "{success:true,formName:'"+formName+"'}";
		}
		else {//执行任务 
			Enumeration para = request.getParameterNames();
			//如果上下文中有对应名称的变量，则把页面数据传进去
			Map mapVar = vm.listVariablesByTaskId(taskId);
			for (;para.hasMoreElements();) {
				String varName = (String)para.nextElement();
				if(mapVar.containsKey(varName)) {
					String v = request.getParameter(varName);
					if(request.getMethod().equalsIgnoreCase("get")) {
						//字符集转换
						try {//extjs转换方式
							v = new String(v.getBytes("iso8859-1"),"utf-8");
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
					vm.setVariableByTaskId(taskId, varName, v);
				}
		    }
			tm.execute(taskId);
			json = "{success:true,formName:'',nodeName:'"+ti.getNodeName()+"'}";
		}		
		return json;		
	}
	/**
	 * 根据不同流程名称显示不同的任务列表
	 * @Methods Name tasks
	 * @Create In Mar 6, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String tasks() throws Exception{
		@SuppressWarnings("unused")
		HttpServletRequest request = super.getRequest();
		//需要的参数
		String actor = request.getParameter("actorId");
		//String actor = UserContext.getUserInfo().getUserName();
		String json = "";
		
		int rowCount = 0;
	  	List<TaskInfo> list = ts.listTasks(actor);
		for(TaskInfo taskInfo:list) {
			String str = "";
			str += "defname:'"+taskInfo.getDefinitionName()+"',";
			str += "defdesc:'"+taskInfo.getDefinitionDesc()+"',";
			str += "nodeName:'"+taskInfo.getNodeName()+"',";
			str += "taskId:'"+taskInfo.getId()+"',";
			str += "taskName:'"+taskInfo.getName()+"',";
			//用实际名称代替用户系统名
			String realName = getUserRealNameByName(taskInfo.getActorId());
			str += "actorId:'"+realName+"',";
			str += "startDate:'"+toBlank(taskInfo.getStart())+"',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			String type = (String)bizParams.get("applyType");
			JSONObject jo = JSONObject.fromObject(bizParams);
			String strBizParams = jo.toString();
			strBizParams = strBizParams==null||strBizParams.equals("null")?"''":strBizParams;
			if(strBizParams.startsWith("{")) {
				strBizParams = strBizParams.substring(1);
			}
			if(strBizParams.endsWith("}")) {
				strBizParams = strBizParams.substring(0,strBizParams.length()-1);
			}
			
			str += strBizParams+",";
			str += "comments:'"+toBlank(taskInfo.getComments().getValue("comment"))+"'";
			str = "{"+str+"},";
			String defname = taskInfo.getDefinitionName();
			if("rproject".equals(type)){
				json += str;
				rowCount++;
			}
		}		
		json = deleteComma(json);
		json =  "{success: true, rowCount:'"+rowCount+"',data:["+json+"]}";
		
		HttpServletResponse res = super.getResponse();
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		return null;
	}
	/**
	 * 获得某流程的审批历史信息
	 * @Methods Name history
	 * @Create In Sep 10, 2008 By yang
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	public String next(HttpServletRequest request) {
		//需要的参数
		String taskId = request.getParameter("taskid");	 
		String procId = request.getParameter("procid");		
		long instId = 0;
		if(procId!=null&&procId.trim().length()!=0) {
			instId = Long.parseLong(procId);
			List tasks = ps.getActiveTasks(instId);
			if(!tasks.isEmpty()) {
				taskId = ((TaskInfo)tasks.get(0)).getId()+"";
			}
		}
		else if(taskId!=null&&taskId.trim().length()!=0){
			instId = ts.getProcessInfo(Long.parseLong(taskId)).getId();
		}
		else {
			System.out.println("ListHistoryAction参数不对");
		}
		
//	 	List<HistoryInfo> list = ps.getHistory(instId);
	 	List<HistoryInfo> list = new ArrayList();
	 	List tasks = ps.getActiveTasks(instId);
	 	for(int i=0;i<tasks.size();i++) {
	 		TaskInfo ti = (TaskInfo)tasks.get(i);
	 		HistoryInfo hi = new HistoryInfo(ti);
	 		hi.setTaskName(ti.getName());
	 		list.add(hi);
	 	}
	 	String json = "";
	 	for(HistoryInfo historyInfo:list) {
	 		String str = "";
	 		//historyInfo.getComments()
	 		//用实际名称代替用户系统名
	 		String realName = getUserRealNameByName(historyInfo.getActorId());
	 		str += "actorId:'"+realName+"',";
	 		str += "date:'"+historyInfo.getDate()+"',";	
	 		str += "definitionName:'"+historyInfo.getDefinitionName()+"',";
	 		str += "processId:'"+historyInfo.getProcessId()+"',";
	 		str += "nodeName:'"+historyInfo.getNodeName()+"',";
	 		str += "taskName:'"+historyInfo.getTaskName()+"',";
	 		str += "name:'"+historyInfo.getName()+"',";
	 		str += "taskId:'"+historyInfo.getTaskId()+"'";
	 		str = "{"+str+"},";
	 		json += str;
	 	}
	 	if(json.endsWith(",")) {
	 		json = json.substring(0,json.length()-1);
	 	}
	 	json = "["+json+"]";
	 	//json = "{data:["+json+"]}";
	 	//json = "{success:true,data:"+json+"}";
		return json;		
	}
	
	private String toBlank(Object o){
		return o==null?"":(String)o;		
	}
	
	private String deleteComma(String json){
		if(json.endsWith(",")) {
			json = json.substring(0, json.length()-1);
		}
		return json;
	}
	/**
	 * 要考虑到多人的情况
	 * @Methods Name getUserRealNameByName
	 * @Create In Nov 19, 2008 By yang
	 * @param userName
	 * @return 
	 * @ReturnType String
	 */
	private String getUserRealNameByName(String userName) {
		String userRealNames = "";
		String[] userNames = null;
		if(userName.indexOf('|')>0) {
			userNames = userName.split("\\|");
		}
		else {
			userNames = new String[]{userName};
		}			
		for(String userNameItem: userNames) {
			List<UserInfo> users = service.find(UserInfo.class, "userName", userNameItem);
			if(users!=null&&!users.isEmpty()) {
				userRealNames += users.get(0).getRealName()+",";
			}
			else {
				userRealNames += userNameItem+",";
			}
		}	
		if(userRealNames.endsWith(",")) {
			userRealNames = userRealNames.substring(0,userRealNames.length()-1);		
		}
		if(userRealNames.indexOf(",")>0) {//多人情况下加上中括号
			userRealNames = "["+userRealNames+"]";
		}
		return userRealNames;
	}

}
