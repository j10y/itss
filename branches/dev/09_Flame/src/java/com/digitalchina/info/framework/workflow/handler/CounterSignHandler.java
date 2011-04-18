package com.digitalchina.info.framework.workflow.handler;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.Action;
import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.graph.node.TaskNode;
import org.jbpm.job.Timer;
import org.jbpm.scheduler.SchedulerService;
import org.jbpm.svc.Services;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.digitalchina.info.framework.context.ContextHolder;
import com.digitalchina.info.framework.message.mail.service.MailSenderService;
import com.digitalchina.info.framework.security.entity.Role;
import com.digitalchina.info.framework.security.entity.UserInfo;
import com.digitalchina.info.framework.service.Service;
import com.digitalchina.info.framework.util.DateTool;
import com.digitalchina.info.framework.util.PropertiesUtil;
import com.digitalchina.info.framework.workflow.ConfigUnitService;
import com.digitalchina.info.framework.workflow.TaskAssignService;
import com.digitalchina.info.framework.workflow.WorkFlowGoBackService;
import com.digitalchina.info.framework.workflow.WorkflowConstants;
import com.digitalchina.info.framework.workflow.action.SynchronousAction;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitRole;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitRoleTable;
import com.digitalchina.info.framework.workflow.entity.ConfigUnitTimer;
import com.digitalchina.info.framework.workflow.entity.TaskPreAssign;
import com.digitalchina.info.framework.workflow.entity.WorkflowRecordTaskInfo;
import com.digitalchina.info.framework.workflow.entity.WorkflowRegressionParameters;
/**
 * 挂接task-node进入事件方法；主要操作，指派审批人（包括后台配置，动态指派，会签等）
 * @author guangsa
 *create date 20100704
 */
public class CounterSignHandler implements ActionHandler{
	private ConfigUnitService cs = (ConfigUnitService)ContextHolder.getBean("configUnitService");
	private WorkFlowGoBackService wfBack = (WorkFlowGoBackService) ContextHolder.getBean("workflowGoBackService");
	private MailSenderService ms = (MailSenderService)ContextHolder.getBean("mailSenderService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private TaskAssignService si = (TaskAssignService) ContextHolder.getBean("taskAssignService");
	private static Logger log;
	static 
	{
		log = Logger.getLogger("workflowlog");
	}
	public void execute(ExecutionContext ec) throws Exception {

		//保存当前节点的nodeName，以便于流程回退；
		String paramId = "";
		ContextInstance ci = ec.getContextInstance();
		Long processInstanceId = ec.getProcessInstance().getId();
		Node node = ec.getNode();
		Long nodeId=node.getId();
		String nodeName = ec.getToken().getNode().getName();//当前节点名称
		String nodeDesc = ec.getToken().getNode().getDescription();//当前节点描述
		String nodeType = ec.getToken().getNode().toString();//当前节点类型
		Token token = ec.getToken();
//		TaskInstance ti = ec.getTaskInstance();
		Long processId=(Long)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		//add by guangsa for 流程正常回退标识 in 20090819 begin
		//Long workflowNormalBackFlag=(Long)ec.getProcessInstance().getContextInstance().getVariable("workflowNormalBackFlag");
		//add by guangsa for 流程正常回退标识 in 20090819 end
		String processName = (String)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_NAME");
		String vProcessDesc = (String)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_DESC");
		String creator = (String)ec.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
		
		Map mapParams=(Map)ec.getProcessInstance().getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		
		String dataId = (String)mapParams.get("dataId");
		String reqClass = (String)mapParams.get("reqClass");
		String goStartState = (String)mapParams.get("goStartState");
		String applyType = (String)mapParams.get("applyType");
		/************************************首先开始指派审批人**************************************************/
		String[] auditUserInfo = null;
		ConfigUnitRole unitRole = si.findUnitRoleByNodeTypeAndProDesc(vProcessDesc, nodeDesc,String.valueOf(processId),nodeId);
		List<ConfigUnitRoleTable> list = si.findRoleTableByConfigUnitRole(unitRole);
		String addDynAssign = (String)mapParams.get("addDynAssignPer");//这是动态指派加后台配置
		String dynaAssign = (String)mapParams.get("userList");//这是动态指派的参数
		String counterSign = (String)mapParams.get("dynCounterSign");//动态会签
		String addDynCounterSign = (String)mapParams.get("addDynCounterSign");//这是动态会签加后台配置
		TaskInstance ti = null;
		//add by guangsa for newTask in 20090724 begin
		//不让其自动创建任务实例,这样话原来的任务就不会自动创建任务实例，也就不会重复指派两次
		((TaskNode)ec.getToken().getNode()).setCreateTasks(false);
		Task task = ((TaskNode)ec.getToken().getNode()).getTask(nodeName);
		//add by guangsa for newTask in 20090724 end
		//add by guangsa for countersSignAuditTaskId in 20090729 begin
		Map counterSignAuditMegs = new HashMap();
		//add by guangsa for countersSignAuditTaskId in 20090729 end
		//add by guangsa for the number of sendMailPerson in 20090729 begin
		String[] auditPers = null;
		//add by guangsa for the number of sendMailPerson in 20090729 end
		String[] browsePers = null;//邮件查看人列表
		
		boolean mark = false;
		
		//增加会签相应各种审批人，
		if(addDynCounterSign!=null&&!"".equals(addDynCounterSign)){
			//addDynCounterSign的格式:nodeDesc:userName,userName$userName,userName
			if(addDynCounterSign.contains("$")){//多节点动态会签加后台配置人
				String[] nodeMegs = addDynCounterSign.split("\\$");
				for(String nodeMeg : nodeMegs){
					String addCounterSignNodeDesc = nodeMeg.substring(0,nodeMeg.indexOf(":")).trim();
					String addCounterSignUserName = nodeMeg.substring(nodeMeg.indexOf(":")+1);
					if(nodeDesc.equals(addCounterSignNodeDesc)){
						String[] users = addCounterSignUserName.split(",");
						for(String user : users){
							ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
							ti.setActorId(user);
							//add by guangsa for countersSignAuditTaskId in 20090729 begin
//							counterSignAuditMegs.put(ti.getId(), users);
							//add by guangsa for countersSignAuditTaskId in 20090729 end
						}
						//这时还把后台角色和任务实例放到一起了
						Set totalAuditPers = new HashSet();
						for(ConfigUnitRoleTable roles : list){
							Set configPer = new HashSet();
							Role role = roles.getRole();
							Set<UserInfo> userinfos=role.getUserInfos();
							for(UserInfo userinfo:userinfos){
								configPer.add(userinfo.getUserName());
								totalAuditPers.add(userinfo.getUserName());
							}	
							if(unitRole.getIsGiveCreate()==1){//说明后台配置了申请人
//								ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
//								ti.setActorId(creator);
								configPer.add(creator);
								totalAuditPers.add(creator);
//								//add by guangsa for countersSignAuditTaskId in 20090729 begin
//								counterSignAuditMegs.put(ti.getId(), users);
//								//add by guangsa for countersSignAuditTaskId in 20090729 end
							}
							//add by guangsa for 说明现在是一个角色给一个任务实例 in 20090816 begin
							if(configPer.size()!=0){
								String[] user = (String[])configPer.toArray(new String[0]);
								ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
								ti.setPooledActors(user);
								//add by guangsa for countersSignAuditTaskId in 20090729 begin
								counterSignAuditMegs.put(ti.getId(), users);
								//add by guangsa for countersSignAuditTaskId in 20090729 end
							}
							//add by guangsa for 说明现在是一个角色给一个任务实例 in 20090816 begin
						}	
						
						//这时开始合并所有审批人
						for(String user : users){
							totalAuditPers.add(user);
						}
						auditPers = (String[])totalAuditPers.toArray(new String[0]);
						mark = true;
					}
				}
			}else{//就一个节点动态会签加后台配置人员
				//首先得到相应的动态会签的人
				String addCounterSignNodeDesc = addDynCounterSign.substring(0,addDynCounterSign.indexOf(":")).trim();
				String addCounterSignUserName = addDynCounterSign.substring(addDynCounterSign.indexOf(":")+1);
				if(nodeDesc.equals(addCounterSignNodeDesc)){
					String[] users = addCounterSignUserName.split(",");
					for(String user : users){
						ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
						ti.setActorId(user);
						//add by guangsa for countersSignAuditTaskId in 20090729 begin
						//counterSignAuditMegs.put(ti.getId(), users);
						//add by guangsa for countersSignAuditTaskId in 20090729 end
					}
					//这时还把后台角色和任务实例放到一起了
					Set totalAuditPers = new HashSet();
					for(ConfigUnitRoleTable roles : list){
						Set configPer = new HashSet();
						Role role = roles.getRole();
						Set<UserInfo> userinfos=role.getUserInfos();
						for(UserInfo userinfo:userinfos){
							configPer.add(userinfo.getUserName());
							totalAuditPers.add(userinfo.getUserName());
						}	
						if(unitRole.getIsGiveCreate()==1){//说明后台配置了申请人
//							ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
//							ti.setActorId(creator);
							configPer.add(creator);
							totalAuditPers.add(creator);
//							//add by guangsa for countersSignAuditTaskId in 20090729 begin
//							counterSignAuditMegs.put(ti.getId(), users);
//							//add by guangsa for countersSignAuditTaskId in 20090729 end
						}
						//add by guangsa for 说明现在是一个角色给一个任务实例 in 20090816 begin
						if(configPer.size()!=0){
							String[] user = (String[])configPer.toArray(new String[0]);
							ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
							ti.setPooledActors(user);
							//add by guangsa for countersSignAuditTaskId in 20090729 begin
							counterSignAuditMegs.put(ti.getId(), users);
							//add by guangsa for countersSignAuditTaskId in 20090729 end
						}
						//add by guangsa for 说明现在是一个角色给一个任务实例 in 20090816 begin
					}	
					
					//这时开始合并所有审批人
					for(String user : users){
						totalAuditPers.add(user);
					}
					auditPers = (String[])totalAuditPers.toArray(new String[0]);
					mark = true;
				}
			}
		}
		if(counterSign!=null&&!"".equals(counterSign)){//首先取出动态会签的人
			//dynCounterSign的格式:nodeDesc:审批类型+userName,userName;审批类型+userName,userName$审批类型+userName,userName;审批类型+userName,userName
			if (counterSign.contains("$")) {//如果动态会签指派了多个节点的话
				String[] nodeMegs = counterSign.split("\\$");
				for(String nodeMeg : nodeMegs){
					String counterNodeDesc = nodeMeg.substring(0, nodeMeg.indexOf(":")).trim();
					String counterNodeMeg = nodeMeg.substring(nodeMeg.indexOf(":")+1);
					if(nodeDesc.equals(counterNodeDesc)){//如果为包含动态会签功能的节点
						String[] counterUserMsgs = counterNodeMeg.split("\\;");
						for(String userMsg : counterUserMsgs){
							String auditType = userMsg.substring(0, userMsg.indexOf("+"));
							String auditUsers = userMsg.substring(userMsg.indexOf("+")+1);
							String[] users = null;
							String taskInstanceName = null;
							//add by guangsa for 会签节点多个任务实例多个不同用途 in 20100729 begin
							if(auditUsers.contains("&")){//说明当前会签节点需要根据不同任务实例显示不同页面效果
								String[] megName = auditUsers.split("\\&");
								users = megName[0].split(",");
								taskInstanceName = megName[1];
							}else{
								users = auditUsers.split(",");
							}
							//add by guangsa for 会签节点多个任务实例多个不同用途 in 20100729 end
							//add by guangsa for the number of sendMailPerson in 20090729 begin
							
									//add by guangsa for proxyAuditPerson in 20090826 begin
									List<String> userInfos = new ArrayList();
									List allProxyUserName = new ArrayList();
									for(int j=0;j<users.length;j++){
										userInfos.add(users[j]);					
									}
									for(int i=0;i<userInfos.size();i++){
										List proxyList =  this.isHaveProxyAuditPerson(userInfos.get(i));
										if(proxyList.size()!=0){
											//如果找到相应的审批人，先把原来的人员去除掉
											userInfos.remove(i);
											allProxyUserName.addAll(proxyList);
										}
									}
									if(allProxyUserName.size()!=0){
										userInfos.addAll(allProxyUserName);
									}
									String[] normalAndProxyPerson = userInfos.toArray(new String[0]);
									//add by guangsa for proxyAuditPerson in 20090826 end
							
							
							
							auditPers = normalAndProxyPerson;
							//add by guangsa for the number of sendMailPerson in 20090729 end
							if("1".equals(auditType)){//动态会签如果为一人审批的时候
								ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
								ti.setPooledActors(normalAndProxyPerson);
								if(taskInstanceName!=null&&!"".equals(taskInstanceName)){
									
									ti.setVariable("specialBusniessKey", taskInstanceName);
									
								}
								//add by guangsa for countersSignAuditTaskId in 20090729 begin
								counterSignAuditMegs.put(ti.getId(), users);
								//add by guangsa for countersSignAuditTaskId in 20090729 end
							}else{//动态会签如果为多人审批的时候
								for(String user : normalAndProxyPerson){
									ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
									ti.setActorId(user);
									if(taskInstanceName!=null&&!"".equals(taskInstanceName)){
										ti.setVariable("specialBusniessKey", taskInstanceName);
									}
									/***************************这是特别需要注意地方，多人多taskId***************************************************************/
									//add by guangsa for countersSignAuditTaskId in 20090729 begin
									counterSignAuditMegs.put(ti.getId(), users);
									//add by guangsa for countersSignAuditTaskId in 20090729 end
									/******************************************************************************************/
								}
							}
						}
						mark = true;
					}
				}
			}else{//如果动态会签指派了一个节点的话
				String counterNodeDesc = counterSign.substring(0, counterSign.indexOf(":")).trim();
				String counterNodeMeg = counterSign.substring(counterSign.indexOf(":")+1).trim();
				if(nodeDesc.equals(counterNodeDesc)){//如果为包含动态会签功能的节点
					String[] counterUserMsgs = counterNodeMeg.split("\\;");//说明是一个任务实例对应人员
					for(String userMsg : counterUserMsgs){
						String auditType = userMsg.substring(0, userMsg.indexOf("+"));
						String auditUsers = userMsg.substring(userMsg.indexOf("+")+1);//得到会签审批人
						String[] users = null;
						String taskInstanceName = null;
						//add by guangsa for 会签节点多个任务实例多个不同用途 in 20100729 begin
						if(auditUsers.contains("&")){//说明当前会签节点需要根据不同任务实例显示不同页面效果
							String[] megName = auditUsers.split("\\&");
							users = megName[0].split(",");
							taskInstanceName = megName[1];
						}else{
							users = auditUsers.split(",");
						}
						//add by guangsa for 会签节点多个任务实例多个不同用途 in 20100729 end
						
						//add by guangsa for the number of sendMailPerson in 20090729 begin
								//add by guangsa for proxyAuditPerson in 20090826 begin
								List<String> userInfos = new ArrayList();
								List allProxyUserName = new ArrayList();
								for(int j=0;j<users.length;j++){
									userInfos.add(users[j]);					
								}
								for(int i=0;i<userInfos.size();i++){
									List proxyList =  this.isHaveProxyAuditPerson(userInfos.get(i));
									if(proxyList.size()!=0){
										//如果找到相应的审批人，先把原来的人员去除掉
										userInfos.remove(i);
										allProxyUserName.addAll(proxyList);
									}
								}
								if(allProxyUserName.size()!=0){
									userInfos.addAll(allProxyUserName);
								}
								String[] normalAndProxyPerson = userInfos.toArray(new String[0]);
								//add by guangsa for proxyAuditPerson in 20090826 end
						
						
						auditPers = normalAndProxyPerson;
						//add by guangsa for the number of sendMailPerson in 20090729 end
						if("1".equals(auditType)){//动态会签如果为一人审批的时候
							ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
							ti.setPooledActors(normalAndProxyPerson);
							if(taskInstanceName!=null&&!"".equals(taskInstanceName)){
								ti.setDescription(taskInstanceName);
								ti.setVariable("specialBusniessKey", taskInstanceName);
								System.out.println(ti.getId()+"存入==="+ti.getVariable("specialBusniessKey")+"到=="+normalAndProxyPerson);
							}
							//add by gaowen for countersSignAuditTaskId in 20090927 begin
							counterSignAuditMegs.put(ti.getId(), users);
							//add by gaowen for countersSignAuditTaskId in 20090927 end
						}else{//动态会签如果为多人审批的时候
							for(String user : normalAndProxyPerson){
								ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
								ti.setActorId(user);
								if(taskInstanceName!=null&&!"".equals(taskInstanceName)){
									ti.setVariable("specialBusniessKey", taskInstanceName);
								}
								//add by gaowen for countersSignAuditTaskId in 20090927 begin
								counterSignAuditMegs.put(ti.getId(), users);
								//add by gaowen for countersSignAuditTaskId in 20090927 end
							}
						}
					}
					mark = true;
				}
			}
		}
		if(!"".equals(addDynAssign)&&addDynAssign!=null){//如果不是包含动态会签功能的节点,再看有没有动态指派+后台人员
			//addDynAssign的格式为nodeDesc：userName,userName$nodeDesc：userName,userName
			if (addDynAssign.contains("$")) {//当动态指派+后台人员为多个节点的时候，需要先按$把相应的信息分开
				String[] nodeUser = addDynAssign.split("\\$");
				for (String dyNodeName : nodeUser) {
					String dynaName = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();
					if(nodeDesc.equals(dynaName)){
						String dyMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
						String[] users = dyMeg.split(",");
						//当前制定了人，那么还需要把后台的人加进去
						List<String> userInfos = this.takeConfigPerson(list, unitRole, creator);
						if(userInfos.size()!=0){//后台有人话
							for(int i=0 ;i<users.length;i++){
								userInfos.add(users[i]);
							}
								//add by guangsa for proxyAuditPerson in 20090826 begin
								List allProxyUserName = new ArrayList();
								for(int i=0;i<userInfos.size();i++){
									List proxyList =  this.isHaveProxyAuditPerson(userInfos.get(i));
									if(proxyList.size()!=0){
										//如果找到相应的审批人，先把原来的人员去除掉
										userInfos.remove(i);
										allProxyUserName.addAll(proxyList);
									}
								}
								if(allProxyUserName.size()!=0){
									userInfos.addAll(allProxyUserName);
								}
								//add by guangsa for proxyAuditPerson in 20090826 end
								
							auditUserInfo = (String[])userInfos.toArray(new String[0]);
							//add by guangsa for the number of sendMailPerson in 20090729 begin
							auditPers = auditUserInfo;
							//add by guangsa for the number of sendMailPerson in 20090729 end
							ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
							ti.setPooledActors(auditUserInfo);
						}else{
							//add by guangsa for the number of sendMailPerson in 20090729 begin
							
								//add by guangsa for proxyAuditPerson in 20090826 begin
								List<String> sepecailUserInfos = new ArrayList();
								List allProxyUserName = new ArrayList();
								for(int j=0;j<users.length;j++){
									sepecailUserInfos.add(users[j]);					
								}
								for(int i=0;i<sepecailUserInfos.size();i++){
									List proxyList =  this.isHaveProxyAuditPerson(sepecailUserInfos.get(i));
									if(proxyList.size()!=0){
										//如果找到相应的审批人，先把原来的人员去除掉
										sepecailUserInfos.remove(i);
										allProxyUserName.addAll(proxyList);
									}
								}
								if(allProxyUserName.size()!=0){
									sepecailUserInfos.addAll(allProxyUserName);
								}
								String[] normalAndProxyPerson = sepecailUserInfos.toArray(new String[0]);
								//add by guangsa for proxyAuditPerson in 20090826 end
							
							auditPers = normalAndProxyPerson;
							//add by guangsa for the number of sendMailPerson in 20090729 end
							ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
							ti.setPooledActors(normalAndProxyPerson);
						}
						mark = true;
						//add by guangsa for 后台加签功能 in 20090725 begin
						String json = this.overSign(list);
						mapParams.put("signerUser", json);//会签的人员
						ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,mapParams);
						//add by guangsa for 后台加签功能 in 20090725 begin
						log.info(vProcessDesc+"(流程)"+nodeName+"为特殊的追加预指派任务节点，并且已经把预指派人员成功指派");
					}
				}
			}else{
				//addDynAssign的格式为nodeDesc：userName,userName$nodeDesc：userName,userName
				String assignNodeName = addDynAssign.substring(0, addDynAssign.indexOf(":")).trim();
				if(nodeDesc.equals(assignNodeName)){
					String somePer =addDynAssign.substring(addDynAssign.indexOf(":")+1);
					String[] personStrings = somePer.split(",");
					List<String> userInfos = this.takeConfigPerson(list, unitRole, creator);
					if(userInfos.size()!=0){//如果后台配置相应的审批人情况
						for(int i=0 ;i<personStrings.length;i++){
							userInfos.add(personStrings[i]);
						}
						
							//add by guangsa for proxyAuditPerson in 20090826 begin
							List allProxyUserName = new ArrayList();
							for(int i=0;i<userInfos.size();i++){
								List proxyList =  this.isHaveProxyAuditPerson(userInfos.get(i));
								if(proxyList.size()!=0){
									//如果找到相应的审批人，先把原来的人员去除掉
									userInfos.remove(i);
									allProxyUserName.addAll(proxyList);
								}
							}
							if(allProxyUserName.size()!=0){
								userInfos.addAll(allProxyUserName);
							}
							//add by guangsa for proxyAuditPerson in 20090826 end
							
						auditUserInfo = (String[])userInfos.toArray(new String[0]);
						//add by guangsa for the number of sendMailPerson in 20090729 begin
						auditPers = auditUserInfo;
						//add by guangsa for the number of sendMailPerson in 20090729 end
						ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
						ti.setPooledActors(auditUserInfo);
					}else{//如果后台没有配置相应的审批人情况
						//add by guangsa for the number of sendMailPerson in 20090729 begin
								//add by guangsa for proxyAuditPerson in 20090826 begin
								List<String> sepecailUserInfos = new ArrayList();
								List allProxyUserName = new ArrayList();
								for(int j=0;j<personStrings.length;j++){
									sepecailUserInfos.add(personStrings[j]);					
								}
								for(int i=0;i<sepecailUserInfos.size();i++){
									List proxyList =  this.isHaveProxyAuditPerson(sepecailUserInfos.get(i));
									if(proxyList.size()!=0){
										//如果找到相应的审批人，先把原来的人员去除掉
										sepecailUserInfos.remove(i);
										allProxyUserName.addAll(proxyList);
									}
								}
								if(allProxyUserName.size()!=0){
									sepecailUserInfos.addAll(allProxyUserName);
								}
								String[] normalAndProxyPerson = sepecailUserInfos.toArray(new String[0]);
								//add by guangsa for proxyAuditPerson in 20090826 end
						
						auditPers = normalAndProxyPerson;
						//add by guangsa for the number of sendMailPerson in 20090729 end
						ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
						ti.setPooledActors(normalAndProxyPerson);
					}
					mark = true;
					//add by guangsa for 后台加签功能 in 20090725 begin
					String json = this.overSign(list);
					mapParams.put("signerUser", json);//会签的人员
					ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,mapParams);
					//add by guangsa for 后台加签功能 in 20090725 begin
					log.info(vProcessDesc+"(流程)"+nodeName+"为特殊的追加预指派任务节点，并且已经把预指派人员成功指派");
				}	
			}
		}
		if(!"".equals(dynaAssign)&&dynaAssign!=null){//如果也不是包含动态指派+后台人员的话，然后再考虑是不是动态指派
			//userList的格式:nodeDesc:userName,userName$nodeDesc:userName,userName
			if (dynaAssign.contains("$")) {//当只有动态指派为多个节点的时候，需要先按$把相应的信息分开
				String[] nodeUser = dynaAssign.split("\\$");
				for (String dyNodeName : nodeUser) {
					String dynaName = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();
					if(nodeDesc.equals(dynaName)){
						String dyMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
						String[] users = dyMeg.split(",");//统一成list
						//add by guangsa for the number of sendMailPerson in 20090729 begin
								//add by guangsa for proxyAuditPerson in 20090826 begin
								List<String> userInfos = new ArrayList();
								List allProxyUserName = new ArrayList();
								for(int j=0;j<users.length;j++){
									userInfos.add(users[j]);					
								}
								for(int i=0;i<userInfos.size();i++){
									List proxyList =  this.isHaveProxyAuditPerson(userInfos.get(i));
									if(proxyList.size()!=0){
										//如果找到相应的审批人，先把原来的人员去除掉
										userInfos.remove(i);
										allProxyUserName.addAll(proxyList);
									}
								}
								if(allProxyUserName.size()!=0){
									userInfos.addAll(allProxyUserName);
								}
								String[] normalAndProxyPerson = userInfos.toArray(new String[0]);
								//add by guangsa for proxyAuditPerson in 20090826 end
						auditPers = normalAndProxyPerson;
						//add by guangsa for the number of sendMailPerson in 20090729 end
						ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
						ti.setPooledActors(normalAndProxyPerson);
						mark = true;
					}	
					log.info(processName+"(流程)"+nodeName+"为普通的预指派任务节点，并且已经把预指派人员成功指派");
				}
			}else{//动态指派中只指派了一个节点
				String assignNodeName = dynaAssign.substring(0, dynaAssign.indexOf(":")).trim();
				if(nodeDesc.equals(assignNodeName)){
					String somePer =dynaAssign.substring(dynaAssign.indexOf(":")+1);
					String[] personStrings = somePer.split(",");
					//add by guangsa for the number of sendMailPerson in 20090729 begin
					
							//add by guangsa for proxyAuditPerson in 20090826 begin
							List<String> userInfos = new ArrayList();
							List allProxyUserName = new ArrayList();
							for(int j=0;j<personStrings.length;j++){
								userInfos.add(personStrings[j]);					
							}
							for(int i=0;i<userInfos.size();i++){
								List proxyList =  this.isHaveProxyAuditPerson(userInfos.get(i));
								if(proxyList.size()!=0){
									//如果找到相应的审批人，先把原来的人员去除掉
									userInfos.remove(i);
									allProxyUserName.addAll(proxyList);
								}
							}
							if(allProxyUserName.size()!=0){
								userInfos.addAll(allProxyUserName);
							}
							String[] normalAndProxyPerson = userInfos.toArray(new String[0]);
							//add by guangsa for proxyAuditPerson in 20090826 end
					
					
					auditPers = normalAndProxyPerson;
					//add by guangsa for the number of sendMailPerson in 20090729 end
					ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
					ti.setPooledActors(normalAndProxyPerson);
					mark = true;
					log.info(processName+"(流程)"+nodeName+"为普通的预指派任务节点，并且已经把预指派人员成功指派");
				}
			}	
		}
		if(!mark){//如果都不是以上几种指派的话，然后再看后台的制定的人员
				List<String> userInfos = this.takeConfigPerson(list, unitRole, creator);//得到后台配置人员，参数分别是roleTable,unitRole
					
				List<String> allProxyUserName = new ArrayList();
				//add by guangsa for proxyAuditPerson in 20090826 begin
						for(int i=0;i<userInfos.size();i++){
							List<String> proxyList =  this.isHaveProxyAuditPerson(userInfos.get(i));
							if(proxyList.size()!=0){
								userInfos.remove(i);
								allProxyUserName.addAll(proxyList);
							}
						}
						if(allProxyUserName.size()!=0){
							userInfos.addAll(allProxyUserName);
						}
				//add by guangsa for proxyAuditPerson in 20090826 end
						
				//add by guangsa for 查看邮件功能 in 20090826 begin
				//1.首先找到所有后台配置的查看人
				Set<String> browsePersonSet = new HashSet();
				for(int p=0;p<list.size();p++){
					String browsePersons = list.get(p).getWorkflowBrowsePerson();
					if(browsePersons!=null&&!"".equals(browsePersons)){
						String[] browsePer = browsePersons.split(",");
						for(int i=0;i<browsePer.length;i++){
							browsePer[i] = browsePer[i].substring(browsePer[i].indexOf("(")+1, browsePer[i].indexOf(")"));
							browsePersonSet.add(browsePer[i]);//这样就把查看人一个一个加到
						}
					}
				}
				//第二步需要从后台配置人员中过滤掉相应的查看人
				Iterator iterator = browsePersonSet.iterator();
				while(iterator.hasNext()){
					String configUser = (String)iterator.next();
					if(userInfos.contains(configUser)){
						userInfos.remove(configUser);
					}
				}
				//第三步把查看人集合变成数组
				browsePers = browsePersonSet.toArray(new String[0]);
			//add by guangsa for 查看邮件功能 in 20090826 end
				auditUserInfo = (String[])userInfos.toArray(new String[0]);
				if(auditUserInfo.length==0){
					throw new RuntimeException("下一节点没有具体审批人,请联系管理员核查！");	
				}
				//add by guangsa for the number of sendMailPerson in 20090729 begin
				auditPers = auditUserInfo;
				//add by guangsa for the number of sendMailPerson in 20090729 end
				ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
				ti.setPooledActors(auditUserInfo);
				//add by guangsa for 后台加签功能 in 20090725 begin
				String json = this.overSign(list);
				mapParams.put("signerUser", json);//会签的人员
				ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,mapParams);
				//add by guangsa for 后台加签功能 in 20090725 end
				log.info(processName+"(流程)"+nodeName+"没有进行任何指派操作，已经把预指派人员成功指派");
			
		}
		System.out.println("审批人个数:"+auditPers.length);
		Long taskId = ti.getId();
		/*********************************然后保存当前节点的信息参数*********************************************************************/
		WorkflowRegressionParameters regParam = null;//保存当前节点的信息参数
		try{
			//regParam = this.saveNodepParamMessage(processId, processInstanceId, nodeId, processName, vProcessDesc, nodeName, nodeDesc, nodeType, mapParams, ci,workflowNormalBackFlag);
		}catch(Exception e){
			//最后两个参数为虚拟流程Id和真实流程Id
			ti.setSignalling(false);//signalling的意思就是不让节点转向
			ti.end();
			List BackNodeMessage = (List)ci.getVariable("goBack");
			String fromNodeName = this.findFromNodeName(BackNodeMessage, 0);//表明已经把当前节点的信息放入,此方法已经把当前节点和上一个节点的信息清除掉了
			Node fromNode = ti.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
			if(fromNode.toString().indexOf("TaskNode")==0){//如果上一个节点是任务接点的时候，重新进入那个节点，然后关闭当前节点
				this.handlerParamExceptionMethod(nodeType, ci, token, processName, nodeName, e,ti,fromNode);
				throw new RuntimeException(e.getMessage());
			}else{
				throw new RuntimeException(e.getMessage());//nodeName+"节点"+"的保存当前业务参数时候发生异常"
			}
		}
		
		//modify in 20090817 for 异步发邮件 begin
		//modify by guangsa for 有些特殊流程不需要发送邮件 in 20100524 begin
		if(unitRole!=null&&!"".equals(unitRole)){
			if(unitRole.getIsNotSendMail()==0){//如果当前节点没有选择“不发送邮件”
				String pageUrl = PropertiesUtil.getProperties("system.mail.develop.background.link", "/servlet/getPageModel?taskId=");
				SynchronousAction sa = new SynchronousAction(nodeName,pageUrl,dataId,reqClass,goStartState,processInstanceId,taskId,applyType, vProcessDesc, processId, creator, mapParams, node, String.valueOf(nodeId),auditPers,counterSignAuditMegs,browsePers);
				Thread t = new Thread(sa);
				t.start();
			}
		}else{
			String pageUrl = PropertiesUtil.getProperties("system.mail.develop.background.link", "/servlet/getPageModel?taskId=");
			SynchronousAction sa = new SynchronousAction(nodeName,pageUrl,dataId,reqClass,goStartState,processInstanceId,taskId,applyType, vProcessDesc, processId, creator, mapParams, node, String.valueOf(nodeId),auditPers,counterSignAuditMegs,browsePers);
			Thread t = new Thread(sa);
			t.start();
		}
		//modify by guangsa for 有些特殊流程不需要发送邮件 in 20100524 end
		//this.sendMailMessage(nodeName,pageUrl,dataId,reqClass,goStartState,processInstanceId,taskId,applyType, vProcessDesc, processId, creator, mapParams, node, String.valueOf(nodeId),auditPers,counterSignAuditMegs);
		//modify in 20090817 for 异步发邮件 end
		
		
		/**********************************再保存节点信息为回退流程作准备*****************************************************************/
		//保存的信息为当前节点的名称和当前节点的参数实体ID
		//格式为"3+节点一；4+节点二；......",把其存于一个list当中
		List goBack = null;
		if(regParam!=null&&!"".equals(regParam)){
			goBack = this.saveWorkflowGoBackParam(String.valueOf(regParam.getId()), nodeName, ci);
		}
		/***********************************再保存这个流程中的当前节点的taskId*************************************************************/
		try{
			//保存当前节点的任务信息
			cs.saveRecordTaskMessage(processId,nodeId,processInstanceId ,ti ,processName, dataId, nodeName, nodeDesc,auditPers,creator);
			log.info("^^^^^^^^^^然后再把当前任务接点的一些必要的实例数据进行保存^^^^^^^^^^");
		}catch(Exception e){
			//要回退，首先把当前节点的参数删除，然后结束当前任务，最后判断是不是TaskNode；
			wfBack.removeNodeWorkflowRegressionParameters(processId, processInstanceId, nodeId);
			ti.setSignalling(false);
			ti.end();
			String fromNodeName = this.findFromNodeName(goBack, 1);//表明已经把当前节点的信息放入,此方法已经把当前节点和上一个节点的信息清除掉了
			Node fromNode = ti.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
			if(fromNode.toString().indexOf("TaskNode")==0){//如果上一个节点是任务接点的时候，重新进入那个节点，然后关闭当前节点
				this.handlerParamExceptionMethod(nodeType, ci, token, processName, nodeName, e,ti,fromNode);
				throw new RuntimeException(e.getMessage());
			}else{
				throw new RuntimeException(e.getMessage());//"在"+processName+"的"+nodeName+"节点"+"保存任务接点信息的时候发生异常"
			}
		}
		/***********************************再开始发送邮件*******************************************************************************/
		//add sendMail by guangsa in 2009-07-20 begin
		//add sendMail by guangsa in 2009-07-20 end
		/***********************************最后开始执行时间调度************************************************************************/
		Timer timer = creatTimer(ec,ti);//这个方法是给timer附上相应变量,可以在timer执行的时候放入
		
		if(timer!=null&&!"".equals(timer)){
			try{
				SchedulerService schedulerService = (SchedulerService)Services.getCurrentService(Services.SERVICENAME_SCHEDULER);
				schedulerService.createTimer(timer);	
			}catch(Exception e){
				//如果这个阶段发生异常了，则需要删除当前任务信息，删除节点参数，删除goBack信息
				WorkflowRecordTaskInfo workflowRecordTaskInfo = cs.findWorkflowRecordTaskInfo(dataId, processName);
				if(workflowRecordTaskInfo!=null&&!"".equals(workflowRecordTaskInfo)){
					service.remove(workflowRecordTaskInfo);//这样就避免了流程回退了，但是同一个流程中任务信息不唯一的错误
				}				
				wfBack.removeNodeWorkflowRegressionParameters(processId, processInstanceId, nodeId);
				ti.setSignalling(false);
				ti.end();
				String fromNodeName = this.findFromNodeName(goBack, 1);//表明已经把当前节点的信息放入,此方法已经把当前节点和上一个节点的信息清除掉了
				Node fromNode = ti.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
				if(fromNode.toString().indexOf("TaskNode")==0){//如果上一个节点是任务接点的时候，重新进入那个节点，然后关闭当前节点
					this.handlerParamExceptionMethod(nodeType, ci, token, processName, nodeName, e,ti,fromNode);
					throw new RuntimeException(e.getMessage());
				}else{
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		log.info("最后"+vProcessDesc+"(流程)"+nodeName+"(任务接点)"+"开始创建Timer完毕.");	
	}
	
	
	//add by guangsa for proxyAuditPerson in 20090826 begin
	public List isHaveProxyAuditPerson(String userName){
		List proxyList = new ArrayList();
		Date currentDate = new Date();
		Long currentTime = currentDate.getTime();
		List<TaskPreAssign> proxyRecords = cs.getTaskProxyObject(userName);
		for(int i=0;i<proxyRecords.size();i++){//开始遍历每一条记录，如果当前大于等于开始时间并且小于等于结束时间，那么此记录符合要求
			Long beginDate = proxyRecords.get(i).getProxyBegin().getTime();
			Long endDate = proxyRecords.get(i).getProxyEnd().getTime();
			if(currentTime>=beginDate&&currentTime<=endDate){
				proxyList.add(proxyRecords.get(i).getProxyId());
			}
		}
		return proxyList;
	}
	
	//add by guangsa for proxyAuditPerson in 20090826 end
	
	
	//add by guangsa for counterSignAssign in 20090722 begin
	/**
	 * 得到后台配置的人员
	 * @param list（配置角色单元关联表）
	 * @param unitRole(配置角色单元)
	 * @param creator（创建者）
	 * @return
	 */
	public List takeConfigPerson(List<ConfigUnitRoleTable> list,ConfigUnitRole unitRole,String creator){
		
		Integer createFlag = 0;
		
		List configPer = new ArrayList();
		if(unitRole!=null&&!"".equals(unitRole)){
			createFlag = unitRole.getIsGiveCreate();
		}
		if(list.size()!=0){
			Set user = new HashSet();
			for(ConfigUnitRoleTable roles : list){
				Role role = roles.getRole();
				Set<UserInfo> userinfos=role.getUserInfos();
				for(UserInfo userinfo:userinfos){
					user.add(userinfo.getUserName());
				}							
			}	
			if(user.size()==0){
				int isCreator = unitRole.getIsGiveCreate();
				if(isCreator==1){//说明有人
					configPer.add(creator);
				}else{
					//这种情况已经在audit的时候做了判断，不会出现
				}
			}else{
				Iterator ite = user.iterator();
				while(ite.hasNext()){
					String use = (String)ite.next();
					configPer.add(use);
				}
				if(createFlag==1){//说明要把申请人加入到当前节点，让申请人成为审批人之一
					if(configPer.contains(creator)){
						//说明包含申请人
					}else{
						configPer.add(creator);
					}
				}else{
					//说明没有把申请人加入到审批人中当中
				}
			}
		}else{
			if(createFlag==1){//说明要把申请人加入到当前节点，让申请人成为审批人之一
				configPer.add(creator);
			}else{
				//这种情况不可能出现，因为在审批的时候已经做了相应的有无角色判断
			}
		}
		return configPer;
	}
	
	
	//add by guangsa for counterSignAssign in 20090722 end
	/**
	 * 保存为工作流回退做准备的参数
	 * @param regParmId（当前节点的业务参数Id）
	 * @param nodeName（当前节点的节点名称）
	 * @param ci（流程上下文）
	 */
	private List saveWorkflowGoBackParam(String regParmId ,String nodeName,ContextInstance ci){
		String nowNodeMessage = regParmId+"+"+nodeName;
		List goBack = (List)ci.getVariable("goBack");
		if(goBack!=null&&!"".equals(goBack)){
			goBack.add(nowNodeMessage);
			ci.setVariable("goBack", goBack);
		}else{
			goBack = new ArrayList();
			goBack.add(nowNodeMessage);
			ci.setVariable("goBack", goBack);
		}		
		log.info("^^^^^^^^^^然后把流程回退需要的参数保存到上下文中^^^^^^^^^^");
		return goBack;
	}
	
	/**
	 * 流程进入进入事件之后，首先要保存当前节点的参数（这是流程回退时查找上个节点业务参数的需要）
	 * @param virtaulProcessId（虚拟流程实例）
	 * @param processInstanceId（真实流程实例）
	 * @param nodeId（流程节点Id）
	 * @param virProcessName（虚拟流程名称）
	 * @param vProcessDesc（虚拟流程描述）
	 * @param nodeName（流程节点名称）
	 * @param nodeDesc（流程节点描述）
	 * @param nodeType（流程节点类型）
	 * @param mapParams(流程业务参数)
	 * @param ci(流程上下文)
	 */
	private WorkflowRegressionParameters saveNodepParamMessage(Long virtaulProcessId,Long processInstanceId,Long nodeId,String virProcessName,String vProcessDesc,String nodeName,String nodeDesc,String nodeType,Map mapParams,ContextInstance ci,Long workflowNormalBackFlag){
		/*************************首先判断是否第一次进入当前节点******************************************/
		log.info(vProcessDesc+"(流程)"+nodeName+"(任务接点)"+"开始创建Timer.接下来有几方面的工作要做：");
		WorkflowRegressionParameters regParam = wfBack.findWorkflowRegressionParametersByMuitlyId(virtaulProcessId, processInstanceId, nodeId);
		if(regParam==null||"".equals(regParam)){//如果是第一次进入当前节点，只需要往库中记录当前节点的参数
			log.info("^^^^^^^^^^如果要是第一次进入此节点，需要在相应表中保存本节点的业务参数^^^^^^^^^^");
			try{
				regParam = wfBack.saveWorkflowRegressionParams(virtaulProcessId, processInstanceId, nodeId,nodeName, nodeDesc,mapParams);
			}catch(Exception e){
				throw new RuntimeException(nodeName+"节点"+"的业务参数重置的时候发生异常");
			}
		}else if(workflowNormalBackFlag!=0&&!"0".equals(workflowNormalBackFlag)){//如果是异常回退进入该节点，需要从库中取出参数并覆盖原来的业务参数;而对于正常回退那种情况不做任何操作
			long NormalBackFlag = 0;
			String bizParam = regParam.getRegressionParams();
			Map nowNodeBizParam = new HashMap();
			//参数格式：{key+value;key+value;key+value;+key+value}
			String[] mutils = bizParam.split("(;)");
			for(int i=0;i<mutils.length;i++){
				String[] single = mutils[i].split("\\+");
				nowNodeBizParam.put(single[0], single[1]);
			}
			if(!nowNodeBizParam.isEmpty()){
				ci.deleteVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
				ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, nowNodeBizParam);
				ci.setVariable("workflowNormalBackFlag", NormalBackFlag);
			}else{
				//如果是空
				throw new RuntimeException(nodeName+"节点"+"的业务参数重置的时候发生异常");
			}
			log.info("^^^^^^^^^^如果不是第一次进入此节点，说明是回退或者重新提交。此时需要从表中查找相应的业务参数覆盖现有的业务参数，以此保证业务参数的唯一性^^^^^^^^^^");
		}
		return regParam;
	}

	/**
	 * 发现上一个节点的节点名称，并把当前节点的goBack信息删除掉
	 * @param allNodeMessage
	 * @param flag
	 * @return
	 */
	public String findFromNodeName(List allNodeMessage , int flag){//此时flag分为1和0，1意味着已经把当前节点包含在其中了；而0则意味着没有把当前节点包含在其中
		
		String fromNodeName = "";
		String fromParamId = "";
		if(flag==1){
			allNodeMessage.remove(allNodeMessage.size()-1);//删除当前节点信息
		}
		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
		String[] mutipleMessage = fromNodeMessage.split("\\+");
		fromParamId = mutipleMessage[0];//上个节点参数Id
		fromNodeName = mutipleMessage[1];//节点名称为中文
		allNodeMessage.remove(allNodeMessage.size()-1);//删除上一个节点信息
		return fromNodeName;
	}
	/**
	 * 处理异常方法(此节点把本节点信息增加到goBack中)
	 * @param nodeType
	 * @param ci
	 * @param token
	 * @param vProcessName
	 * @param nodeName
	 * @param e
	 */
	public void handlerParamExceptionMethod(String nodeType,ContextInstance ci,Token token,String vProcessName , String nodeName,Exception e,TaskInstance ti,Node fromNode){
		
		log.error(vProcessName+"(流程)提交之后"+"在"+nodeName+"(节点)发生异常");
		log.debug(e.getMessage());
		this.nodeTypeParamException(ci, token,ti,fromNode);
		//然后发送邮件,通知管理员
		this.sendSimpleEmail(vProcessName, nodeName);
	}
	/**
	 * 这和上一种情况区别在于当前节点的回退参数已经加入到字符串当中了
	 * @param ci
	 * @param token
	 */
	public void nodeTypeParamException(ContextInstance ci,Token token,TaskInstance ti,Node fromNode){

		//开始回退了
		try{
			token.setNode(fromNode);
			ExecutionContext ec = new ExecutionContext(token);
			fromNode.enter(ec);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 系统异常之后发送邮件给系统管理员和维护管理员
	 * @param vProcessName
	 * @param nodeName
	 */
	public void sendSimpleEmail(String vProcessName , String nodeName){
		
		String contentDefault = vProcessName+"(流程)提交之后"+"在"+nodeName+"节点发生异常";
		String subject = PropertiesUtil.getProperties("system.mail.excepition.subject", "ITIL审批流程发生异常，请检查系统");
		String content = PropertiesUtil.getProperties("system.mail.exception.content", contentDefault);
		String to = PropertiesUtil.getProperties("system.mail.sendmail.from");
		String cc = PropertiesUtil.getProperties("system.mail.develop.debug.mailrecirve");
		try{
			ms.sendSimplyMail(to,cc , null, subject, content);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * 创建从后台取出定时器设置的延长时间
	 * @param ec
	 * @param taskInstance
	 * @return
	 */
	public Timer creatTimer(ExecutionContext ec,TaskInstance taskInstance){
//		CreateTimerAction t = new CreateTimerAction();
		Timer timer = new Timer(ec.getToken());	
		
		Long virtualDefinintionId = (Long)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		Long nodeId = ec.getNode().getId();
		ConfigUnitTimer configTimer = cs.showConfigUnitTimer(virtualDefinintionId, nodeId);
		if(configTimer!=null&&!"".equals(configTimer)){
			try{
				int dueDate = configTimer.getEffectTime();
				DateTool dt = new DateTool();
				/**
				 * modify by guangsa for 定时器应用（论工作日工作8时长）in 20100720 begin
				 * 分为两种情况：为8小时的整时长设计思路--首先设当前时间为星期几X，用5-X算出本周还剩几天；其次用用户设计定时器时间N/8算出需要几天；两个变量进行比较，如果
				 * 			  需要天数大于本周剩余天数则（当前时间+（N/8）*24+48），否则为（当前时间+（N/8）*24）
				 * 			  另外一种情况为：不足8小时的情况，需要单独考虑（等新需求来之后进行确认）
				 */
				if(dueDate%8==0){
					int dayOfWeek = this.dayOfWeek();
					Date dueDateDate;
					//如果为周末情况即为dayOfWeek<=0;正常工作日为>0（周一为1，周二为2）
					if(dayOfWeek>0&&((5-dayOfWeek)>=(dueDate/8))){//说明没有超出周末
						dueDateDate = dt.addDate(new Date(), (dueDate/8)*24, Calendar.HOUR);
					}else if(dayOfWeek>0&&((5-dayOfWeek)<(dueDate/8))){//为超出周末情况
						dueDateDate = dt.addDate(new Date(), (dueDate/8)*24+48, Calendar.HOUR);
					}else{//为周末提交申请情况
						dueDateDate = dt.addDate(new Date(), dueDate+48, Calendar.HOUR);
					}
					timer.setDueDate(dueDateDate);
					timer.setTaskInstance(taskInstance);//timer.setRepeat("30 seconds");
					Action createAction = DelegationFactory.getAction(DelegationFactory.JPDL_TIMER_EXECUTE_ASSIGN);
					timer.setAction(createAction);//到了那个时间做的事情
					timer.setTransitionName(configTimer.getInverseNodeName());
					ec.setTimer(timer);
				// modify by guangsa for 定时器应用（论工作日工作8时长）in 20100720 begin
				}else{
					//后续需求确认
				}
				
			}catch(Exception e){
				throw new RuntimeException("任务实例创建timer时发生异常");
			}
			 return timer;
		}
		return null;  
	}
	/**
	 * 算出当前为周几（由于Calendar认为周六为0，例如周一为2，因此day-1）
	 * @return
	 */
	public int dayOfWeek(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int day =calendar.get(Calendar.DAY_OF_WEEK);
		return day-1;
	}
	
	/**
	 * 发送邮件信息
	 * @param dataId（业务参数）
	 * @param reqClass（业务参数）
	 * @param goStartState（回退到开始节点key）
	 * @param processInstanceId（真实流程Id）
	 * @param taskId（任务ID）
	 * @param nodeName（流程节点名称）
	 * @param vDesc（虚拟流程描述）
	 * @param virtualDefinintionId（虚拟流程Id）
	 * @param creator(流程创建者)
	 * @param bizParam(流程业务参数)
	 * @param node（流程节点）
	 * @param nodeId（流程节点Id）
	 */
	private void sendMailMessage(String nodeName,String pageUrl,String dataId,String reqClass,String goStartState,Long processInstanceId,Long taskId,String applyType,String vDesc,Long virtualDefinintionId,String creator,Map bizParam,Node node,String nodeId,String[] auditPers,Map counterSignAuditMegs){
		//保存当前节点的nodeName，以便于流程回退；
		String virualDesc = "";
		String nowNodeDesc = "";
		String nowNodeName = "";
		String subject = null;
		String content = null;
		String[] ccEmail = null;//抄送人的email地址
		String[] configEmail = null;//后台配置，如果有就复制进入，没有就为空值
		log.info(virualDesc+"在"+nodeName+"的进入事件中给指派的人审批发邮件!");
//		Long nodeid = 0l;
//		VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) service.find(VirtualDefinitionInfo.class, String.valueOf(virtualDefinintionId));
//		if(virtualDefinitionInfo!=null){
//			virualDesc = virtualDefinitionInfo.getVirtualDefinitionDesc();	
//			NodeInfo nodeInfo = new NodeInfo(node);//executionContext.getNode()
//			nowNodeDesc = nodeInfo.getDesc();
//			nowNodeName = nodeInfo.getNodeName();
//			nodeid = nodeInfo.getId();
//		}
//		String creatorEmail = "";
		
//		ConfigUnitRole unitRole = si.findUnitRoleByNodeTypeAndProDesc(virualDesc, nowNodeDesc, String.valueOf(virtualDefinintionId),nodeid);//后台配置角色
//		if(unitRole!=null&&!"".equals(unitRole)){
//			
//			int isCreator = unitRole.getIsGiveCreate();//后台配置是否有创建者
//			//根据配置的相应角色找到相应的人,然后要拼成相应的字符串（以逗号分隔）
//			List<ConfigUnitRoleTable> list = si.findRoleTableByConfigUnitRole(unitRole);	
//			if(list.isEmpty()){
//				if(isCreator==1){//说明后台没有配置角色审批人，这时需要看审批人是否有创建者
//					List creat = service.find(UserInfo.class, "userName", creator);
//					if(!creat.isEmpty()){
//						UserInfo creatorUser = (UserInfo)(creat.get(0));
//						creatorEmail = creatorUser.getEmail();
//					}else{
//						throw new RuntimeException("创建者为空导致无法查找userinfo中的信息");
//					}
//				}else{
//					throw new RuntimeException("后台角色并没有配置");
//				}
//			}
//			Set user = new HashSet();
//			for(ConfigUnitRoleTable roles : list){
//				Role role = roles.getRole();
//				Set<UserInfo> userinfos=role.getUserInfos();
//				for(UserInfo userinfo:userinfos){
//					if(userinfo.getEmail()!=null&&!"".equals(userinfo.getEmail())){
//						user.add(userinfo.getEmail());//用set集合防止人重名
//					}					
//				}							
//			} 
//			if(creatorEmail!=null&&!"".equals(creatorEmail)){//说明审批人包括创建者
//				user.add(creatorEmail);
//			}
//			configEmail = new String[user.size()];
//			int flag = 0;
//			Iterator ite1 = user.iterator();
//			while(ite1.hasNext()){
//				String userConfig = (String)ite1.next();
//				configEmail[flag] = userConfig;
//				flag++;
//			}			
//			log.info("***************第一部分为后台配置的人，查找成功");
//		}else{
//			log.info("(请检查一下)"+virualDesc+"(流程)是否不用后台配置相应的审批人");
//		}
//		
//		//以上就用字符串包装了后台配置的人，还需动态指派的人
//		String[] toEmail = null;//动态指派人的email
//		if(!bizParam.isEmpty()){		
//			String dynaAssign = (String)bizParam.get("userList");
//			if(!"".equals(dynaAssign)&&dynaAssign!=null){	
//				if (dynaAssign.contains("$")) {
//					String[] nodeUser = dynaAssign.split("\\$");//有多个节点信息(包括节点描述和相应人)
//					Set user = new HashSet();
//					for (String dyNodeName : nodeUser) {
//						
//						String dynaDesc = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();//nodeDesc(英文)
//						String dyUserMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
//						String[] users = dyUserMeg.split(",");//具体的多个人
//						
//						if(nowNodeDesc.equals(dynaDesc)){	
//							for(int i=0;i<users.length;i++){
//								UserInfo info = (UserInfo)service.find(UserInfo.class, "userName", users[i]).get(0);
//								String email = info.getEmail();
//								if(!"".equals(email)&&email!=null){
//									user.add(email);
//								}								
//							}
//						}						
//						
//					}
//					toEmail = new String[user.size()];
//					int flag = 0;
//					Iterator ite1 = user.iterator();
//					while(ite1.hasNext()){
//						String userConfig = (String)ite1.next();
//						toEmail[flag] = userConfig;
//						flag++;
//					}		
//				}else{
//					String dynaDesc = dynaAssign.substring(0, dynaAssign.indexOf(":")).trim();
//					String dyUserMeg = dynaAssign.substring(dynaAssign.indexOf(":") + 1);
//					String[] users = dyUserMeg.split(",");//具体的多个人
//					
//					Set user = new HashSet();
//					
//					if(nowNodeDesc.equals(dynaDesc)){	
//						for(int i=0;i<users.length;i++){
//							UserInfo info = (UserInfo)service.find(UserInfo.class, "userName", users[i]).get(0);
//							String email = info.getEmail();
//							if(!"".equals(email)&&email!=null){
//								user.add(email);
//								
//							}								
//						}
//					}
//					toEmail = new String[user.size()];
//					int flag = 0;
//					Iterator ite1 = user.iterator();
//					while(ite1.hasNext()){
//						String userConfig = (String)ite1.next();
//						toEmail[flag] = userConfig;
//						flag++;
//					}			
//				}
//			}
//			log.info("***************第二部分为动态指派的人，查找成功");
//		}else{
//			log.info("(请检查一下)"+virualDesc+"(流程)业务参数为空");
//		}	
//		//合并后台配置和动态指派的人的email地址
//		String[] combinEmail = null;
//		List com = new ArrayList();
//		if(configEmail!=null&&toEmail!=null){
//			combinEmail = new String[configEmail.length+toEmail.length];
//			for(int i=0;i<configEmail.length;i++){
//				com.add(configEmail[i]);
//			}
//			for(int i=0;i<toEmail.length;i++){
//				com.add(toEmail[i]);
//			}
//			for(int j=0;j<combinEmail.length;j++){
//				combinEmail[j] = (String)com.get(j);
//			}			
//		}else if(configEmail!=null&&toEmail==null){//后台配置的email不为空，而动态指派的email为空
//			combinEmail = new String[configEmail.length];
//			for(int i=0;i<configEmail.length;i++){
//				com.add(configEmail[i]);
//			}
//			for(int j=0;j<configEmail.length;j++){
//				combinEmail[j] = (String)com.get(j);
//			}	
//		}else if(configEmail==null&&toEmail!=null){//动态指派的email不为空，而后台配置的email为空
//			combinEmail = new String[toEmail.length];
//			for(int i=0;i<toEmail.length;i++){
//				com.add(toEmail[i]);
//			}
//			for(int j=0;j<toEmail.length;j++){
//				combinEmail[j] = (String)com.get(j);
//			}	
//		}else if(configEmail==null&&toEmail==null){
//			combinEmail = new String[]{"guangshunan0813@163.com"};
//		}
//		log.info("***************然后合并前两部分的审批人员");
//		//再加上后台配置的抄送人信息（未考虑不是本集团人的情况）
//		//String nodeId = String.valueOf(executionContext.getNode().getId());
//		ConfigUnitMail unitMail = cs.findMailObjectById(String.valueOf(virtualDefinintionId), nodeId);
//		
//		int flag = 0;
//		if(unitMail!=null&&!"".equals(unitMail)){
//			subject = unitMail.getSubject();
//			content = unitMail.getContent();
//			
//			Set<UserInfo> userInfos = unitMail.getUserInfos();
//			if(userInfos.isEmpty()){
//				throw new RuntimeException("邮件抄送配置单元角色没有配置");
//			}
//			ccEmail = new String[userInfos.size()];
//			
//			List<ConfigUnitMailCC> mailCC = service.find(ConfigUnitMailCC.class, "configUnitMail", unitMail);
//			/*******************************************************************************************************************/
//			//这部的意思是如果数据库中用户有邮件，那么我就用数据库。如果用户不想用数据库中，我就用用户手动输入的邮件地址
//			if(!mailCC.isEmpty()){
//				for(int i=0;i<mailCC.size();i++){
//					
//					ConfigUnitMailCC confirmMailCC = mailCC.get(i);				
//					UserInfo userInfo = confirmMailCC.getUserInfo();
//					
//					if(userInfo!=null&&!"".equals(userInfo)){
//						String email = userInfo.getEmail();
//						if("".equals(email)||email==null){
//							email = confirmMailCC.getMail();//这个是用户手写的邮件地址
//						}
//						ccEmail[i] = email;
//					}				
//				}		
//			}
//			
//		}else if(unitMail==null||"".equals(unitMail)){		
//			ccEmail = new String[]{"guangshunan0813@163.com"};//   guangsa@information.digitalchina.com
//		}
//		log.info("***************第三部分为后台配置抄送的人，查找成功");
		
		//add by guangsa for sendComplexMail in 2009-07-15 begin
		String auditMeg = "点击此链接，查看仔细并请审批！链接：----------------------------";
		String workflowEntity = (String)bizParam.get("workflowHistory");		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, processInstanceId);//查找出来的是所有的按流程顺序排列的节点信息
		//add by guangsa for sendComplexMail in 2009-07-15 end
		
		/*************************再次调用其他方法*****************************************/
		if(subject==null||"".equals(subject)){
			subject=creator+"提交了"+vDesc+"请审批!";//"审批通知";
		}
		if(content==null||"".equals(content)){
			content=auditMeg;//"ITIL项目中有一个需求需要您审批";
		}
		//add by guangsa for avoidAuditpers in 20090729 begin
		//remove by lee for 去掉垃圾抄送 in 20091221 begin
//		if(ccEmail==null||"".equals(ccEmail)){
//			ccEmail = new String[]{"guangshunan0813@163.com"};
//		}
		//remove by lee for 去掉垃圾抄送 in 20091221 end
		//add by guangsa for counterSignAuditTaskId in 20090729 begin
		List auditUserEmail = new ArrayList();
		if(counterSignAuditMegs.size()!=0&&counterSignAuditMegs!=null&&!"".equals(counterSignAuditMegs)){
			//如果为动态会签审批人时候
			Set set = counterSignAuditMegs.keySet();
			Iterator counterMegs = set.iterator();
			while(counterMegs.hasNext()){
				Long taskid = (Long)counterMegs.next();
				String[] users = (String[])counterSignAuditMegs.get(taskid);
				for(int i=0;i<users.length;i++){
					UserInfo userInfo = (UserInfo)service.findUnique(UserInfo.class, "userName", users[i]);
					auditUserEmail.add(userInfo.getEmail());
				}
				String[] auditEmail = (String[])auditUserEmail.toArray(new String[0]);
				//String context = cs.htmlContent(nodeName,pageUrl,applyType,dataId,reqClass,goStartState,taskid,creator, vDesc, auditHis);
				try{
					//ms.sendMimeMail(auditEmail, ccEmail, null, subject, context, null);
				}catch(Exception e){
					log.info(virualDesc+"(流程)在"+nodeName+"(节点)给三部分的审批人发送邮件时发生异常！");
					e.printStackTrace();
				}
			}
		}else{
			//如果为其他指派发邮件时候
			for(int i=0;i<auditPers.length;i++){
				UserInfo userInfo = (UserInfo)service.findUnique(UserInfo.class, "userName", auditPers[i]);
				auditUserEmail.add(userInfo.getEmail());
			}
			String[] auditEmail = (String[])auditUserEmail.toArray(new String[0]);
			//String context = cs.htmlContent(nodeName,pageUrl,applyType,dataId,reqClass,goStartState,taskId,creator, vDesc, auditHis);
			try{
				//ms.sendMimeMail(auditEmail, ccEmail, null, subject, context, null);
			}catch(Exception e){
				log.info(virualDesc+"(流程)在"+nodeName+"(节点)给三部分的审批人发送邮件时发生异常！");
				e.printStackTrace();
			}
		}
		//add by guangsa for counterSignAuditTaskId in 20090729 begin
		log.info(virualDesc+"在"+nodeName+"(节点)给审批人发送邮件成功！");
	}
	
	/**
	 * 去重每个角色中的人
	 * @param list
	 * @return
	 */
	public String overSign(List<ConfigUnitRoleTable> list){
		//把角色和对应的人一一对应以来
		Map<ConfigUnitRoleTable,List> totalRole = new HashMap<ConfigUnitRoleTable,List>();			
		//先把角色和对应的人都放到一个map当中
		for(ConfigUnitRoleTable roles : list){
			List allUser = new ArrayList(); 
			Role role = roles.getRole();
			Set<UserInfo> userinfos=role.getUserInfos();
			for(UserInfo userinfo:userinfos){
				allUser.add(userinfo.getUserName());
			}
			totalRole.put(roles, allUser);
		}		
		//遍历map，取出第一个值并且从map中去除掉，重新遍历一边map得到不是第一个值的所有人
		//让单独去取出的人和剩余的人比较，判断有没有重复，有重复就去掉
		 Map<ConfigUnitRoleTable,List>  personMap = new HashMap<ConfigUnitRoleTable,List> ();
	      Set<ConfigUnitRoleTable> personSet = totalRole.keySet();
	      Iterator it=personSet.iterator();
	      while(it.hasNext()){
	       ConfigUnitRoleTable key = (ConfigUnitRoleTable)it.next();
	       List person = (List)totalRole.get(key);
	       personMap.put(key, person);
	      }
	    //把角色和人又重新放入另一个personMap中，为了方便去重，集合是不能在遍历的时候删除集合元素的
	      Set<ConfigUnitRoleTable> totalSet = totalRole.keySet();
	      Iterator ite = totalSet.iterator();
	      while(ite.hasNext()){
	       ConfigUnitRoleTable key = (ConfigUnitRoleTable)ite.next();
	       List person = (List)totalRole.get(key);
	       personMap.remove(key);
	       
	       Set<ConfigUnitRoleTable> remain = personMap.keySet();
	          List rePerson = new ArrayList();
	          for(ConfigUnitRoleTable rePer : remain){
	           List remainPer = (List)totalRole.get(rePer);
	           rePerson.addAll(remainPer);//剩余所有的人
	          }
	          
	         for(int i=0;i<person.size();i++){
	          if(rePerson.contains(person.get(i))){
	           person.remove(person.get(i));
	          }
	         }
	         personMap.put(key, person);
		
	      }
	      //上面得到的是所有的不重复的角色和相应的人
		  String json = "";
		  Set<ConfigUnitRoleTable> total = personMap.keySet();
		  Iterator ites = total.iterator();
		  List allPerson = new ArrayList();
		  while(ites.hasNext()){
			  ConfigUnitRoleTable roleTable = (ConfigUnitRoleTable)ites.next();
			  allPerson = (List)personMap.get(roleTable);
			  json += roleTable.getRole().getName()+"+";
			  json += roleTable.getFlag()+":";
			  for(int i=0;i<allPerson.size();i++){
				 
				  json += allPerson.get(i);
				  json += "|";
			  }
			  if(json.endsWith("|")){
				  json = json.substring(0, json.length()-1);
			  }
			  json += "$";
		  }
		  if(json.endsWith("$")){
			  json = json.substring(0, json.length()-1);
		  }
		  return json;
	}
}
