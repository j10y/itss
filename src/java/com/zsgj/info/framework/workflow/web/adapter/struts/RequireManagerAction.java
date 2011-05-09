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
	 * ������루������������,��Ҫ���ǵ�һ���ڵ��п���ָ�ɸ����˵����
	 * ֧�ֵ��ڵ㵥������ָ�ɣ���ʽΪa|b|c,
	 * @Methods Name apply
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String apply() throws Exception{
		String json = ""; 
		//��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		
		//definitionName=StringUtils.substringAfter(definitionName, "$");
		//definitionName = definitionName.substring(8);
		String buzzParameters = super.getRequest().getParameter("bzparam");//��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");//������id
		String departmentCode = super.getRequest().getParameter("deptcode");
		String userAssign = super.getRequest().getParameter("userAssign");
		//��Ҫ���������ĵ�ҵ�����
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
	 * ��������ִ������ڵ㣩
	 * @Methods Name audit
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	public String audit(HttpServletRequest request) {
		String json = "";
		//����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
		String strTaskId = request.getParameter("id");
		String done = request.getParameter("done");
		long taskId = 0;		
		if(strTaskId!=null&&!strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		//�ҵ��ڵ���Ϣ����ӦСform��Ϣ		
		TaskInfo ti = tm.getTaskInfo(taskId);
		String formName = FormHelper.findForm(ti.getDefinitionName(),ti.getNodeName()).trim();
		
		
		if(done==null) {//ִ��ǰ��ʾ	
			json = "{success:true,formName:'"+formName+"'}";
		}
		else {//ִ������ 
			Enumeration para = request.getParameterNames();
			//������������ж�Ӧ���Ƶı��������ҳ�����ݴ���ȥ
			Map mapVar = vm.listVariablesByTaskId(taskId);
			for (;para.hasMoreElements();) {
				String varName = (String)para.nextElement();
				if(mapVar.containsKey(varName)) {
					String v = request.getParameter(varName);
					if(request.getMethod().equalsIgnoreCase("get")) {
						//�ַ���ת��
						try {//extjsת����ʽ
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
	 * ���ݲ�ͬ����������ʾ��ͬ�������б�
	 * @Methods Name tasks
	 * @Create In Mar 6, 2009 By guangsa
	 * @return
	 * @throws Exception String
	 */
	public String tasks() throws Exception{
		@SuppressWarnings("unused")
		HttpServletRequest request = super.getRequest();
		//��Ҫ�Ĳ���
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
			//��ʵ�����ƴ����û�ϵͳ��
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
	 * ���ĳ���̵�������ʷ��Ϣ
	 * @Methods Name history
	 * @Create In Sep 10, 2008 By yang
	 * @param request
	 * @return 
	 * @ReturnType String
	 */
	public String next(HttpServletRequest request) {
		//��Ҫ�Ĳ���
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
			System.out.println("ListHistoryAction��������");
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
	 		//��ʵ�����ƴ����û�ϵͳ��
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
	 * Ҫ���ǵ����˵����
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
		if(userRealNames.indexOf(",")>0) {//��������¼���������
			userRealNames = "["+userRealNames+"]";
		}
		return userRealNames;
	}

}
