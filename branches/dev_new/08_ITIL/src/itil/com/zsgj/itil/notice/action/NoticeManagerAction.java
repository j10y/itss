package com.zsgj.itil.notice.action;

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
import com.zsgj.info.framework.workflow.info.HistoryInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.notice.entity.NewNotice;
import com.zsgj.itil.system.entity.Notice;

public class NoticeManagerAction extends BaseAction{
	private TaskService ts = (TaskService)ContextHolder.getBean("taskService");
	private ParameterService pms = (ParameterService)ContextHolder.getBean("parameterService");
	private Service service = (Service) ContextHolder.getBean("baseService");
//	private ContextService vm = (ContextService)ContextHolder.getBean("contextService");
//	private TaskService tm = (TaskService)ContextHolder.getBean("taskService");
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
		String buzzParameters = super.getRequest().getParameter("bzparam");//在ajax当中已经把js对象变成了json字符串
		String dataId = super.getRequest().getParameter("dataId");//主数据id
//		String departmentCode = super.getRequest().getParameter("deptcode");
//		String userAssign = super.getRequest().getParameter("userAssign");
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
			mapBizz.put("workflowHistory", "com.zsgj.itil.notice.entity.NoticeAuditHis");
			//add by awen for add two param in bizMap on 2009-08-23 begin
			NewNotice notice = (NewNotice) service.find(NewNotice.class, dataId, true);
			if(notice != null){
				mapBizz.put("applyName",notice.getTitle());
			}
			//add by awen for add two param in bizMap on 2009-08-23 end
		}
		//modify by lee for appoint dept confirmer in 20090812 begin
		UserInfo creator = UserContext.getUserInfo(); //获取当前登录人
		List<SupportGroupEngineer> engineerList =super.getService().find(SupportGroupEngineer.class, "userInfo", creator);
		String error = "";
		if(engineerList.size()>0){
			SupportGroupEngineer engineer = engineerList.get(0);
			if(engineer!=null){
				SupportGroup supportGroup = engineer.getSupportGroup();//获取工程师对应支持组
				if(supportGroup!=null){
					UserInfo confirmer = supportGroup.getGroupConfirmer();//获取公告审批人
					if(confirmer!=null){
						String userListStr = "customerManagerAudit:" + confirmer.getUserName();
						mapBizz.put("userList",userListStr);//指定支持组审批人节点审批用户
					}else{
						error = "未找到指定公告审批人！";
					}
				}else{
					error = "未找到工程师所属支持组！";
				}
			}else{
				error = "非支持组工程师不能提交公告申请！";
			}
		}else{
			error = "非支持组工程师不能提交公告申请！";
		}
		//modify by lee for appoint dept confirmer in 20090812 end
		String creatorName = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try{
			if(!error.equals("")){
				json = "{success:true,Exception:'"+error+"'}";
			}else{
				instanceId = ps.createProcess(definitionName,creatorName,mapBizz);
				json = "{success:true,id:'"+instanceId+"'}";
			}
		//modify by lee for appoint dept confirmer in 20090812 end
		}catch(Exception e){
			meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"'}";
		}			
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
	 * 根据不同流程名称显示不同的任务列表
	 * @Methods Name tasks
	 * @Create In Mar 6, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String tasks() throws Exception{
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
//			String realName = getUserRealNameByName(taskInfo.getActorId());
//			str += "actorId:'"+realName+"',";
			str += "startDate:'"+toBlank(taskInfo.getStart())+"',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			String applyTypeString = (String)bizParams.get("applyType");
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
			if("nproject".equals(applyTypeString)){
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
	@SuppressWarnings("unused")
	private String next() throws Exception{
		//需要的参数
		String taskId = super.getRequest().getParameter("taskid");	 
		String procId = super.getRequest().getParameter("procid");		
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
//	 		String realName = getUserRealNameByName(historyInfo.getActorId());
//	 		str += "actorId:'"+realName+"',";
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
//	private String getUserRealNameByName(String userName) {
//		String userRealNames = "";
//		String[] userNames = null;
//		if(userName.indexOf('|')>0) {
//			userNames = userName.split("\\|");
//		}
//		else {
//			userNames = new String[]{userName};
//		}			
//		for(String userNameItem: userNames) {
//			List<UserInfo> users = service.find(UserInfo.class, "userName", userNameItem);
//			if(users!=null&&!users.isEmpty()) {
//				userRealNames += users.get(0).getRealName()+",";
//			}
//			else {
//				userRealNames += userNameItem+",";
//			}
//		}	
//		if(userRealNames.endsWith(",")) {
//			userRealNames = userRealNames.substring(0,userRealNames.length()-1);		
//		}
//		if(userRealNames.indexOf(",")>0) {//多人情况下加上中括号
//			userRealNames = "["+userRealNames+"]";
//		}
//		return userRealNames;
//	}

}
