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
 * �ҽ�task-node�����¼���������Ҫ������ָ�������ˣ�������̨���ã���ָ̬�ɣ���ǩ�ȣ�
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

		//���浱ǰ�ڵ��nodeName���Ա������̻��ˣ�
		String paramId = "";
		ContextInstance ci = ec.getContextInstance();
		Long processInstanceId = ec.getProcessInstance().getId();
		Node node = ec.getNode();
		Long nodeId=node.getId();
		String nodeName = ec.getToken().getNode().getName();//��ǰ�ڵ�����
		String nodeDesc = ec.getToken().getNode().getDescription();//��ǰ�ڵ�����
		String nodeType = ec.getToken().getNode().toString();//��ǰ�ڵ�����
		Token token = ec.getToken();
//		TaskInstance ti = ec.getTaskInstance();
		Long processId=(Long)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		//add by guangsa for �����������˱�ʶ in 20090819 begin
		//Long workflowNormalBackFlag=(Long)ec.getProcessInstance().getContextInstance().getVariable("workflowNormalBackFlag");
		//add by guangsa for �����������˱�ʶ in 20090819 end
		String processName = (String)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_NAME");
		String vProcessDesc = (String)ec.getProcessInstance().getContextInstance().getVariable("VIRTUALDEFINITIONINFO_DESC");
		String creator = (String)ec.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
		
		Map mapParams=(Map)ec.getProcessInstance().getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		
		String dataId = (String)mapParams.get("dataId");
		String reqClass = (String)mapParams.get("reqClass");
		String goStartState = (String)mapParams.get("goStartState");
		String applyType = (String)mapParams.get("applyType");
		/************************************���ȿ�ʼָ��������**************************************************/
		String[] auditUserInfo = null;
		ConfigUnitRole unitRole = si.findUnitRoleByNodeTypeAndProDesc(vProcessDesc, nodeDesc,String.valueOf(processId),nodeId);
		List<ConfigUnitRoleTable> list = si.findRoleTableByConfigUnitRole(unitRole);
		String addDynAssign = (String)mapParams.get("addDynAssignPer");//���Ƕ�ָ̬�ɼӺ�̨����
		String dynaAssign = (String)mapParams.get("userList");//���Ƕ�ָ̬�ɵĲ���
		String counterSign = (String)mapParams.get("dynCounterSign");//��̬��ǩ
		String addDynCounterSign = (String)mapParams.get("addDynCounterSign");//���Ƕ�̬��ǩ�Ӻ�̨����
		TaskInstance ti = null;
		//add by guangsa for newTask in 20090724 begin
		//�������Զ���������ʵ��,������ԭ��������Ͳ����Զ���������ʵ����Ҳ�Ͳ����ظ�ָ������
		((TaskNode)ec.getToken().getNode()).setCreateTasks(false);
		Task task = ((TaskNode)ec.getToken().getNode()).getTask(nodeName);
		//add by guangsa for newTask in 20090724 end
		//add by guangsa for countersSignAuditTaskId in 20090729 begin
		Map counterSignAuditMegs = new HashMap();
		//add by guangsa for countersSignAuditTaskId in 20090729 end
		//add by guangsa for the number of sendMailPerson in 20090729 begin
		String[] auditPers = null;
		//add by guangsa for the number of sendMailPerson in 20090729 end
		String[] browsePers = null;//�ʼ��鿴���б�
		
		boolean mark = false;
		
		//���ӻ�ǩ��Ӧ���������ˣ�
		if(addDynCounterSign!=null&&!"".equals(addDynCounterSign)){
			//addDynCounterSign�ĸ�ʽ:nodeDesc:userName,userName$userName,userName
			if(addDynCounterSign.contains("$")){//��ڵ㶯̬��ǩ�Ӻ�̨������
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
						//��ʱ���Ѻ�̨��ɫ������ʵ���ŵ�һ����
						Set totalAuditPers = new HashSet();
						for(ConfigUnitRoleTable roles : list){
							Set configPer = new HashSet();
							Role role = roles.getRole();
							Set<UserInfo> userinfos=role.getUserInfos();
							for(UserInfo userinfo:userinfos){
								configPer.add(userinfo.getUserName());
								totalAuditPers.add(userinfo.getUserName());
							}	
							if(unitRole.getIsGiveCreate()==1){//˵����̨������������
//								ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
//								ti.setActorId(creator);
								configPer.add(creator);
								totalAuditPers.add(creator);
//								//add by guangsa for countersSignAuditTaskId in 20090729 begin
//								counterSignAuditMegs.put(ti.getId(), users);
//								//add by guangsa for countersSignAuditTaskId in 20090729 end
							}
							//add by guangsa for ˵��������һ����ɫ��һ������ʵ�� in 20090816 begin
							if(configPer.size()!=0){
								String[] user = (String[])configPer.toArray(new String[0]);
								ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
								ti.setPooledActors(user);
								//add by guangsa for countersSignAuditTaskId in 20090729 begin
								counterSignAuditMegs.put(ti.getId(), users);
								//add by guangsa for countersSignAuditTaskId in 20090729 end
							}
							//add by guangsa for ˵��������һ����ɫ��һ������ʵ�� in 20090816 begin
						}	
						
						//��ʱ��ʼ�ϲ�����������
						for(String user : users){
							totalAuditPers.add(user);
						}
						auditPers = (String[])totalAuditPers.toArray(new String[0]);
						mark = true;
					}
				}
			}else{//��һ���ڵ㶯̬��ǩ�Ӻ�̨������Ա
				//���ȵõ���Ӧ�Ķ�̬��ǩ����
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
					//��ʱ���Ѻ�̨��ɫ������ʵ���ŵ�һ����
					Set totalAuditPers = new HashSet();
					for(ConfigUnitRoleTable roles : list){
						Set configPer = new HashSet();
						Role role = roles.getRole();
						Set<UserInfo> userinfos=role.getUserInfos();
						for(UserInfo userinfo:userinfos){
							configPer.add(userinfo.getUserName());
							totalAuditPers.add(userinfo.getUserName());
						}	
						if(unitRole.getIsGiveCreate()==1){//˵����̨������������
//							ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
//							ti.setActorId(creator);
							configPer.add(creator);
							totalAuditPers.add(creator);
//							//add by guangsa for countersSignAuditTaskId in 20090729 begin
//							counterSignAuditMegs.put(ti.getId(), users);
//							//add by guangsa for countersSignAuditTaskId in 20090729 end
						}
						//add by guangsa for ˵��������һ����ɫ��һ������ʵ�� in 20090816 begin
						if(configPer.size()!=0){
							String[] user = (String[])configPer.toArray(new String[0]);
							ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
							ti.setPooledActors(user);
							//add by guangsa for countersSignAuditTaskId in 20090729 begin
							counterSignAuditMegs.put(ti.getId(), users);
							//add by guangsa for countersSignAuditTaskId in 20090729 end
						}
						//add by guangsa for ˵��������һ����ɫ��һ������ʵ�� in 20090816 begin
					}	
					
					//��ʱ��ʼ�ϲ�����������
					for(String user : users){
						totalAuditPers.add(user);
					}
					auditPers = (String[])totalAuditPers.toArray(new String[0]);
					mark = true;
				}
			}
		}
		if(counterSign!=null&&!"".equals(counterSign)){//����ȡ����̬��ǩ����
			//dynCounterSign�ĸ�ʽ:nodeDesc:��������+userName,userName;��������+userName,userName$��������+userName,userName;��������+userName,userName
			if (counterSign.contains("$")) {//�����̬��ǩָ���˶���ڵ�Ļ�
				String[] nodeMegs = counterSign.split("\\$");
				for(String nodeMeg : nodeMegs){
					String counterNodeDesc = nodeMeg.substring(0, nodeMeg.indexOf(":")).trim();
					String counterNodeMeg = nodeMeg.substring(nodeMeg.indexOf(":")+1);
					if(nodeDesc.equals(counterNodeDesc)){//���Ϊ������̬��ǩ���ܵĽڵ�
						String[] counterUserMsgs = counterNodeMeg.split("\\;");
						for(String userMsg : counterUserMsgs){
							String auditType = userMsg.substring(0, userMsg.indexOf("+"));
							String auditUsers = userMsg.substring(userMsg.indexOf("+")+1);
							String[] users = null;
							String taskInstanceName = null;
							//add by guangsa for ��ǩ�ڵ�������ʵ�������ͬ��; in 20100729 begin
							if(auditUsers.contains("&")){//˵����ǰ��ǩ�ڵ���Ҫ���ݲ�ͬ����ʵ����ʾ��ͬҳ��Ч��
								String[] megName = auditUsers.split("\\&");
								users = megName[0].split(",");
								taskInstanceName = megName[1];
							}else{
								users = auditUsers.split(",");
							}
							//add by guangsa for ��ǩ�ڵ�������ʵ�������ͬ��; in 20100729 end
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
											//����ҵ���Ӧ�������ˣ��Ȱ�ԭ������Աȥ����
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
							if("1".equals(auditType)){//��̬��ǩ���Ϊһ��������ʱ��
								ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
								ti.setPooledActors(normalAndProxyPerson);
								if(taskInstanceName!=null&&!"".equals(taskInstanceName)){
									
									ti.setVariable("specialBusniessKey", taskInstanceName);
									
								}
								//add by guangsa for countersSignAuditTaskId in 20090729 begin
								counterSignAuditMegs.put(ti.getId(), users);
								//add by guangsa for countersSignAuditTaskId in 20090729 end
							}else{//��̬��ǩ���Ϊ����������ʱ��
								for(String user : normalAndProxyPerson){
									ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
									ti.setActorId(user);
									if(taskInstanceName!=null&&!"".equals(taskInstanceName)){
										ti.setVariable("specialBusniessKey", taskInstanceName);
									}
									/***************************�����ر���Ҫע��ط������˶�taskId***************************************************************/
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
			}else{//�����̬��ǩָ����һ���ڵ�Ļ�
				String counterNodeDesc = counterSign.substring(0, counterSign.indexOf(":")).trim();
				String counterNodeMeg = counterSign.substring(counterSign.indexOf(":")+1).trim();
				if(nodeDesc.equals(counterNodeDesc)){//���Ϊ������̬��ǩ���ܵĽڵ�
					String[] counterUserMsgs = counterNodeMeg.split("\\;");//˵����һ������ʵ����Ӧ��Ա
					for(String userMsg : counterUserMsgs){
						String auditType = userMsg.substring(0, userMsg.indexOf("+"));
						String auditUsers = userMsg.substring(userMsg.indexOf("+")+1);//�õ���ǩ������
						String[] users = null;
						String taskInstanceName = null;
						//add by guangsa for ��ǩ�ڵ�������ʵ�������ͬ��; in 20100729 begin
						if(auditUsers.contains("&")){//˵����ǰ��ǩ�ڵ���Ҫ���ݲ�ͬ����ʵ����ʾ��ͬҳ��Ч��
							String[] megName = auditUsers.split("\\&");
							users = megName[0].split(",");
							taskInstanceName = megName[1];
						}else{
							users = auditUsers.split(",");
						}
						//add by guangsa for ��ǩ�ڵ�������ʵ�������ͬ��; in 20100729 end
						
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
										//����ҵ���Ӧ�������ˣ��Ȱ�ԭ������Աȥ����
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
						if("1".equals(auditType)){//��̬��ǩ���Ϊһ��������ʱ��
							ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
							ti.setPooledActors(normalAndProxyPerson);
							if(taskInstanceName!=null&&!"".equals(taskInstanceName)){
								ti.setDescription(taskInstanceName);
								ti.setVariable("specialBusniessKey", taskInstanceName);
								System.out.println(ti.getId()+"����==="+ti.getVariable("specialBusniessKey")+"��=="+normalAndProxyPerson);
							}
							//add by gaowen for countersSignAuditTaskId in 20090927 begin
							counterSignAuditMegs.put(ti.getId(), users);
							//add by gaowen for countersSignAuditTaskId in 20090927 end
						}else{//��̬��ǩ���Ϊ����������ʱ��
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
		if(!"".equals(addDynAssign)&&addDynAssign!=null){//������ǰ�����̬��ǩ���ܵĽڵ�,�ٿ���û�ж�ָ̬��+��̨��Ա
			//addDynAssign�ĸ�ʽΪnodeDesc��userName,userName$nodeDesc��userName,userName
			if (addDynAssign.contains("$")) {//����ָ̬��+��̨��ԱΪ����ڵ��ʱ����Ҫ�Ȱ�$����Ӧ����Ϣ�ֿ�
				String[] nodeUser = addDynAssign.split("\\$");
				for (String dyNodeName : nodeUser) {
					String dynaName = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();
					if(nodeDesc.equals(dynaName)){
						String dyMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
						String[] users = dyMeg.split(",");
						//��ǰ�ƶ����ˣ���ô����Ҫ�Ѻ�̨���˼ӽ�ȥ
						List<String> userInfos = this.takeConfigPerson(list, unitRole, creator);
						if(userInfos.size()!=0){//��̨���˻�
							for(int i=0 ;i<users.length;i++){
								userInfos.add(users[i]);
							}
								//add by guangsa for proxyAuditPerson in 20090826 begin
								List allProxyUserName = new ArrayList();
								for(int i=0;i<userInfos.size();i++){
									List proxyList =  this.isHaveProxyAuditPerson(userInfos.get(i));
									if(proxyList.size()!=0){
										//����ҵ���Ӧ�������ˣ��Ȱ�ԭ������Աȥ����
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
										//����ҵ���Ӧ�������ˣ��Ȱ�ԭ������Աȥ����
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
						//add by guangsa for ��̨��ǩ���� in 20090725 begin
						String json = this.overSign(list);
						mapParams.put("signerUser", json);//��ǩ����Ա
						ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,mapParams);
						//add by guangsa for ��̨��ǩ���� in 20090725 begin
						log.info(vProcessDesc+"(����)"+nodeName+"Ϊ�����׷��Ԥָ������ڵ㣬�����Ѿ���Ԥָ����Ա�ɹ�ָ��");
					}
				}
			}else{
				//addDynAssign�ĸ�ʽΪnodeDesc��userName,userName$nodeDesc��userName,userName
				String assignNodeName = addDynAssign.substring(0, addDynAssign.indexOf(":")).trim();
				if(nodeDesc.equals(assignNodeName)){
					String somePer =addDynAssign.substring(addDynAssign.indexOf(":")+1);
					String[] personStrings = somePer.split(",");
					List<String> userInfos = this.takeConfigPerson(list, unitRole, creator);
					if(userInfos.size()!=0){//�����̨������Ӧ�����������
						for(int i=0 ;i<personStrings.length;i++){
							userInfos.add(personStrings[i]);
						}
						
							//add by guangsa for proxyAuditPerson in 20090826 begin
							List allProxyUserName = new ArrayList();
							for(int i=0;i<userInfos.size();i++){
								List proxyList =  this.isHaveProxyAuditPerson(userInfos.get(i));
								if(proxyList.size()!=0){
									//����ҵ���Ӧ�������ˣ��Ȱ�ԭ������Աȥ����
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
					}else{//�����̨û��������Ӧ�����������
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
										//����ҵ���Ӧ�������ˣ��Ȱ�ԭ������Աȥ����
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
					//add by guangsa for ��̨��ǩ���� in 20090725 begin
					String json = this.overSign(list);
					mapParams.put("signerUser", json);//��ǩ����Ա
					ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,mapParams);
					//add by guangsa for ��̨��ǩ���� in 20090725 begin
					log.info(vProcessDesc+"(����)"+nodeName+"Ϊ�����׷��Ԥָ������ڵ㣬�����Ѿ���Ԥָ����Ա�ɹ�ָ��");
				}	
			}
		}
		if(!"".equals(dynaAssign)&&dynaAssign!=null){//���Ҳ���ǰ�����ָ̬��+��̨��Ա�Ļ���Ȼ���ٿ����ǲ��Ƕ�ָ̬��
			//userList�ĸ�ʽ:nodeDesc:userName,userName$nodeDesc:userName,userName
			if (dynaAssign.contains("$")) {//��ֻ�ж�ָ̬��Ϊ����ڵ��ʱ����Ҫ�Ȱ�$����Ӧ����Ϣ�ֿ�
				String[] nodeUser = dynaAssign.split("\\$");
				for (String dyNodeName : nodeUser) {
					String dynaName = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();
					if(nodeDesc.equals(dynaName)){
						String dyMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
						String[] users = dyMeg.split(",");//ͳһ��list
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
										//����ҵ���Ӧ�������ˣ��Ȱ�ԭ������Աȥ����
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
					log.info(processName+"(����)"+nodeName+"Ϊ��ͨ��Ԥָ������ڵ㣬�����Ѿ���Ԥָ����Ա�ɹ�ָ��");
				}
			}else{//��ָ̬����ָֻ����һ���ڵ�
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
									//����ҵ���Ӧ�������ˣ��Ȱ�ԭ������Աȥ����
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
					log.info(processName+"(����)"+nodeName+"Ϊ��ͨ��Ԥָ������ڵ㣬�����Ѿ���Ԥָ����Ա�ɹ�ָ��");
				}
			}	
		}
		if(!mark){//������������ϼ���ָ�ɵĻ���Ȼ���ٿ���̨���ƶ�����Ա
				List<String> userInfos = this.takeConfigPerson(list, unitRole, creator);//�õ���̨������Ա�������ֱ���roleTable,unitRole
					
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
						
				//add by guangsa for �鿴�ʼ����� in 20090826 begin
				//1.�����ҵ����к�̨���õĲ鿴��
				Set<String> browsePersonSet = new HashSet();
				for(int p=0;p<list.size();p++){
					String browsePersons = list.get(p).getWorkflowBrowsePerson();
					if(browsePersons!=null&&!"".equals(browsePersons)){
						String[] browsePer = browsePersons.split(",");
						for(int i=0;i<browsePer.length;i++){
							browsePer[i] = browsePer[i].substring(browsePer[i].indexOf("(")+1, browsePer[i].indexOf(")"));
							browsePersonSet.add(browsePer[i]);//�����ͰѲ鿴��һ��һ���ӵ�
						}
					}
				}
				//�ڶ�����Ҫ�Ӻ�̨������Ա�й��˵���Ӧ�Ĳ鿴��
				Iterator iterator = browsePersonSet.iterator();
				while(iterator.hasNext()){
					String configUser = (String)iterator.next();
					if(userInfos.contains(configUser)){
						userInfos.remove(configUser);
					}
				}
				//�������Ѳ鿴�˼��ϱ������
				browsePers = browsePersonSet.toArray(new String[0]);
			//add by guangsa for �鿴�ʼ����� in 20090826 end
				auditUserInfo = (String[])userInfos.toArray(new String[0]);
				if(auditUserInfo.length==0){
					throw new RuntimeException("��һ�ڵ�û�о���������,����ϵ����Ա�˲飡");	
				}
				//add by guangsa for the number of sendMailPerson in 20090729 begin
				auditPers = auditUserInfo;
				//add by guangsa for the number of sendMailPerson in 20090729 end
				ti = ec.getTaskMgmtInstance().createTaskInstance(task, token);
				ti.setPooledActors(auditUserInfo);
				//add by guangsa for ��̨��ǩ���� in 20090725 begin
				String json = this.overSign(list);
				mapParams.put("signerUser", json);//��ǩ����Ա
				ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY,mapParams);
				//add by guangsa for ��̨��ǩ���� in 20090725 end
				log.info(processName+"(����)"+nodeName+"û�н����κ�ָ�ɲ������Ѿ���Ԥָ����Ա�ɹ�ָ��");
			
		}
		System.out.println("�����˸���:"+auditPers.length);
		Long taskId = ti.getId();
		/*********************************Ȼ�󱣴浱ǰ�ڵ����Ϣ����*********************************************************************/
		WorkflowRegressionParameters regParam = null;//���浱ǰ�ڵ����Ϣ����
		try{
			//regParam = this.saveNodepParamMessage(processId, processInstanceId, nodeId, processName, vProcessDesc, nodeName, nodeDesc, nodeType, mapParams, ci,workflowNormalBackFlag);
		}catch(Exception e){
			//�����������Ϊ��������Id����ʵ����Id
			ti.setSignalling(false);//signalling����˼���ǲ��ýڵ�ת��
			ti.end();
			List BackNodeMessage = (List)ci.getVariable("goBack");
			String fromNodeName = this.findFromNodeName(BackNodeMessage, 0);//�����Ѿ��ѵ�ǰ�ڵ����Ϣ����,�˷����Ѿ��ѵ�ǰ�ڵ����һ���ڵ����Ϣ�������
			Node fromNode = ti.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
			if(fromNode.toString().indexOf("TaskNode")==0){//�����һ���ڵ�������ӵ��ʱ�����½����Ǹ��ڵ㣬Ȼ��رյ�ǰ�ڵ�
				this.handlerParamExceptionMethod(nodeType, ci, token, processName, nodeName, e,ti,fromNode);
				throw new RuntimeException(e.getMessage());
			}else{
				throw new RuntimeException(e.getMessage());//nodeName+"�ڵ�"+"�ı��浱ǰҵ�����ʱ�����쳣"
			}
		}
		
		//modify in 20090817 for �첽���ʼ� begin
		//modify by guangsa for ��Щ�������̲���Ҫ�����ʼ� in 20100524 begin
		if(unitRole!=null&&!"".equals(unitRole)){
			if(unitRole.getIsNotSendMail()==0){//�����ǰ�ڵ�û��ѡ�񡰲������ʼ���
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
		//modify by guangsa for ��Щ�������̲���Ҫ�����ʼ� in 20100524 end
		//this.sendMailMessage(nodeName,pageUrl,dataId,reqClass,goStartState,processInstanceId,taskId,applyType, vProcessDesc, processId, creator, mapParams, node, String.valueOf(nodeId),auditPers,counterSignAuditMegs);
		//modify in 20090817 for �첽���ʼ� end
		
		
		/**********************************�ٱ���ڵ���ϢΪ����������׼��*****************************************************************/
		//�������ϢΪ��ǰ�ڵ�����ƺ͵�ǰ�ڵ�Ĳ���ʵ��ID
		//��ʽΪ"3+�ڵ�һ��4+�ڵ����......",�������һ��list����
		List goBack = null;
		if(regParam!=null&&!"".equals(regParam)){
			goBack = this.saveWorkflowGoBackParam(String.valueOf(regParam.getId()), nodeName, ci);
		}
		/***********************************�ٱ�����������еĵ�ǰ�ڵ��taskId*************************************************************/
		try{
			//���浱ǰ�ڵ��������Ϣ
			cs.saveRecordTaskMessage(processId,nodeId,processInstanceId ,ti ,processName, dataId, nodeName, nodeDesc,auditPers,creator);
			log.info("^^^^^^^^^^Ȼ���ٰѵ�ǰ����ӵ��һЩ��Ҫ��ʵ�����ݽ��б���^^^^^^^^^^");
		}catch(Exception e){
			//Ҫ���ˣ����Ȱѵ�ǰ�ڵ�Ĳ���ɾ����Ȼ�������ǰ��������ж��ǲ���TaskNode��
			wfBack.removeNodeWorkflowRegressionParameters(processId, processInstanceId, nodeId);
			ti.setSignalling(false);
			ti.end();
			String fromNodeName = this.findFromNodeName(goBack, 1);//�����Ѿ��ѵ�ǰ�ڵ����Ϣ����,�˷����Ѿ��ѵ�ǰ�ڵ����һ���ڵ����Ϣ�������
			Node fromNode = ti.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
			if(fromNode.toString().indexOf("TaskNode")==0){//�����һ���ڵ�������ӵ��ʱ�����½����Ǹ��ڵ㣬Ȼ��رյ�ǰ�ڵ�
				this.handlerParamExceptionMethod(nodeType, ci, token, processName, nodeName, e,ti,fromNode);
				throw new RuntimeException(e.getMessage());
			}else{
				throw new RuntimeException(e.getMessage());//"��"+processName+"��"+nodeName+"�ڵ�"+"��������ӵ���Ϣ��ʱ�����쳣"
			}
		}
		/***********************************�ٿ�ʼ�����ʼ�*******************************************************************************/
		//add sendMail by guangsa in 2009-07-20 begin
		//add sendMail by guangsa in 2009-07-20 end
		/***********************************���ʼִ��ʱ�����************************************************************************/
		Timer timer = creatTimer(ec,ti);//��������Ǹ�timer������Ӧ����,������timerִ�е�ʱ�����
		
		if(timer!=null&&!"".equals(timer)){
			try{
				SchedulerService schedulerService = (SchedulerService)Services.getCurrentService(Services.SERVICENAME_SCHEDULER);
				schedulerService.createTimer(timer);	
			}catch(Exception e){
				//�������׶η����쳣�ˣ�����Ҫɾ����ǰ������Ϣ��ɾ���ڵ������ɾ��goBack��Ϣ
				WorkflowRecordTaskInfo workflowRecordTaskInfo = cs.findWorkflowRecordTaskInfo(dataId, processName);
				if(workflowRecordTaskInfo!=null&&!"".equals(workflowRecordTaskInfo)){
					service.remove(workflowRecordTaskInfo);//�����ͱ��������̻����ˣ�����ͬһ��������������Ϣ��Ψһ�Ĵ���
				}				
				wfBack.removeNodeWorkflowRegressionParameters(processId, processInstanceId, nodeId);
				ti.setSignalling(false);
				ti.end();
				String fromNodeName = this.findFromNodeName(goBack, 1);//�����Ѿ��ѵ�ǰ�ڵ����Ϣ����,�˷����Ѿ��ѵ�ǰ�ڵ����һ���ڵ����Ϣ�������
				Node fromNode = ti.getProcessInstance().getProcessDefinition().getNode(fromNodeName);
				if(fromNode.toString().indexOf("TaskNode")==0){//�����һ���ڵ�������ӵ��ʱ�����½����Ǹ��ڵ㣬Ȼ��رյ�ǰ�ڵ�
					this.handlerParamExceptionMethod(nodeType, ci, token, processName, nodeName, e,ti,fromNode);
					throw new RuntimeException(e.getMessage());
				}else{
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		log.info("���"+vProcessDesc+"(����)"+nodeName+"(����ӵ�)"+"��ʼ����Timer���.");	
	}
	
	
	//add by guangsa for proxyAuditPerson in 20090826 begin
	public List isHaveProxyAuditPerson(String userName){
		List proxyList = new ArrayList();
		Date currentDate = new Date();
		Long currentTime = currentDate.getTime();
		List<TaskPreAssign> proxyRecords = cs.getTaskProxyObject(userName);
		for(int i=0;i<proxyRecords.size();i++){//��ʼ����ÿһ����¼�������ǰ���ڵ��ڿ�ʼʱ�䲢��С�ڵ��ڽ���ʱ�䣬��ô�˼�¼����Ҫ��
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
	 * �õ���̨���õ���Ա
	 * @param list�����ý�ɫ��Ԫ������
	 * @param unitRole(���ý�ɫ��Ԫ)
	 * @param creator�������ߣ�
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
				if(isCreator==1){//˵������
					configPer.add(creator);
				}else{
					//��������Ѿ���audit��ʱ�������жϣ��������
				}
			}else{
				Iterator ite = user.iterator();
				while(ite.hasNext()){
					String use = (String)ite.next();
					configPer.add(use);
				}
				if(createFlag==1){//˵��Ҫ�������˼��뵽��ǰ�ڵ㣬�������˳�Ϊ������֮һ
					if(configPer.contains(creator)){
						//˵������������
					}else{
						configPer.add(creator);
					}
				}else{
					//˵��û�а������˼��뵽�������е���
				}
			}
		}else{
			if(createFlag==1){//˵��Ҫ�������˼��뵽��ǰ�ڵ㣬�������˳�Ϊ������֮һ
				configPer.add(creator);
			}else{
				//������������ܳ��֣���Ϊ��������ʱ���Ѿ�������Ӧ�����޽�ɫ�ж�
			}
		}
		return configPer;
	}
	
	
	//add by guangsa for counterSignAssign in 20090722 end
	/**
	 * ����Ϊ������������׼���Ĳ���
	 * @param regParmId����ǰ�ڵ��ҵ�����Id��
	 * @param nodeName����ǰ�ڵ�Ľڵ����ƣ�
	 * @param ci�����������ģ�
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
		log.info("^^^^^^^^^^Ȼ������̻�����Ҫ�Ĳ������浽��������^^^^^^^^^^");
		return goBack;
	}
	
	/**
	 * ���̽�������¼�֮������Ҫ���浱ǰ�ڵ�Ĳ������������̻���ʱ�����ϸ��ڵ�ҵ���������Ҫ��
	 * @param virtaulProcessId����������ʵ����
	 * @param processInstanceId����ʵ����ʵ����
	 * @param nodeId�����̽ڵ�Id��
	 * @param virProcessName�������������ƣ�
	 * @param vProcessDesc����������������
	 * @param nodeName�����̽ڵ����ƣ�
	 * @param nodeDesc�����̽ڵ�������
	 * @param nodeType�����̽ڵ����ͣ�
	 * @param mapParams(����ҵ�����)
	 * @param ci(����������)
	 */
	private WorkflowRegressionParameters saveNodepParamMessage(Long virtaulProcessId,Long processInstanceId,Long nodeId,String virProcessName,String vProcessDesc,String nodeName,String nodeDesc,String nodeType,Map mapParams,ContextInstance ci,Long workflowNormalBackFlag){
		/*************************�����ж��Ƿ��һ�ν��뵱ǰ�ڵ�******************************************/
		log.info(vProcessDesc+"(����)"+nodeName+"(����ӵ�)"+"��ʼ����Timer.�������м�����Ĺ���Ҫ����");
		WorkflowRegressionParameters regParam = wfBack.findWorkflowRegressionParametersByMuitlyId(virtaulProcessId, processInstanceId, nodeId);
		if(regParam==null||"".equals(regParam)){//����ǵ�һ�ν��뵱ǰ�ڵ㣬ֻ��Ҫ�����м�¼��ǰ�ڵ�Ĳ���
			log.info("^^^^^^^^^^���Ҫ�ǵ�һ�ν���˽ڵ㣬��Ҫ����Ӧ���б��汾�ڵ��ҵ�����^^^^^^^^^^");
			try{
				regParam = wfBack.saveWorkflowRegressionParams(virtaulProcessId, processInstanceId, nodeId,nodeName, nodeDesc,mapParams);
			}catch(Exception e){
				throw new RuntimeException(nodeName+"�ڵ�"+"��ҵ��������õ�ʱ�����쳣");
			}
		}else if(workflowNormalBackFlag!=0&&!"0".equals(workflowNormalBackFlag)){//������쳣���˽���ýڵ㣬��Ҫ�ӿ���ȡ������������ԭ����ҵ�����;��������������������������κβ���
			long NormalBackFlag = 0;
			String bizParam = regParam.getRegressionParams();
			Map nowNodeBizParam = new HashMap();
			//������ʽ��{key+value;key+value;key+value;+key+value}
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
				//����ǿ�
				throw new RuntimeException(nodeName+"�ڵ�"+"��ҵ��������õ�ʱ�����쳣");
			}
			log.info("^^^^^^^^^^������ǵ�һ�ν���˽ڵ㣬˵���ǻ��˻��������ύ����ʱ��Ҫ�ӱ��в�����Ӧ��ҵ������������е�ҵ��������Դ˱�֤ҵ�������Ψһ��^^^^^^^^^^");
		}
		return regParam;
	}

	/**
	 * ������һ���ڵ�Ľڵ����ƣ����ѵ�ǰ�ڵ��goBack��Ϣɾ����
	 * @param allNodeMessage
	 * @param flag
	 * @return
	 */
	public String findFromNodeName(List allNodeMessage , int flag){//��ʱflag��Ϊ1��0��1��ζ���Ѿ��ѵ�ǰ�ڵ�����������ˣ���0����ζ��û�аѵ�ǰ�ڵ����������
		
		String fromNodeName = "";
		String fromParamId = "";
		if(flag==1){
			allNodeMessage.remove(allNodeMessage.size()-1);//ɾ����ǰ�ڵ���Ϣ
		}
		String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
		String[] mutipleMessage = fromNodeMessage.split("\\+");
		fromParamId = mutipleMessage[0];//�ϸ��ڵ����Id
		fromNodeName = mutipleMessage[1];//�ڵ�����Ϊ����
		allNodeMessage.remove(allNodeMessage.size()-1);//ɾ����һ���ڵ���Ϣ
		return fromNodeName;
	}
	/**
	 * �����쳣����(�˽ڵ�ѱ��ڵ���Ϣ���ӵ�goBack��)
	 * @param nodeType
	 * @param ci
	 * @param token
	 * @param vProcessName
	 * @param nodeName
	 * @param e
	 */
	public void handlerParamExceptionMethod(String nodeType,ContextInstance ci,Token token,String vProcessName , String nodeName,Exception e,TaskInstance ti,Node fromNode){
		
		log.error(vProcessName+"(����)�ύ֮��"+"��"+nodeName+"(�ڵ�)�����쳣");
		log.debug(e.getMessage());
		this.nodeTypeParamException(ci, token,ti,fromNode);
		//Ȼ�����ʼ�,֪ͨ����Ա
		this.sendSimpleEmail(vProcessName, nodeName);
	}
	/**
	 * �����һ������������ڵ�ǰ�ڵ�Ļ��˲����Ѿ����뵽�ַ���������
	 * @param ci
	 * @param token
	 */
	public void nodeTypeParamException(ContextInstance ci,Token token,TaskInstance ti,Node fromNode){

		//��ʼ������
		try{
			token.setNode(fromNode);
			ExecutionContext ec = new ExecutionContext(token);
			fromNode.enter(ec);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * ϵͳ�쳣֮�����ʼ���ϵͳ����Ա��ά������Ա
	 * @param vProcessName
	 * @param nodeName
	 */
	public void sendSimpleEmail(String vProcessName , String nodeName){
		
		String contentDefault = vProcessName+"(����)�ύ֮��"+"��"+nodeName+"�ڵ㷢���쳣";
		String subject = PropertiesUtil.getProperties("system.mail.excepition.subject", "ITIL�������̷����쳣������ϵͳ");
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
	 * �����Ӻ�̨ȡ����ʱ�����õ��ӳ�ʱ��
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
				 * modify by guangsa for ��ʱ��Ӧ�ã��۹����չ���8ʱ����in 20100720 begin
				 * ��Ϊ���������Ϊ8Сʱ����ʱ�����˼·--�����赱ǰʱ��Ϊ���ڼ�X����5-X������ܻ�ʣ���죻������û���ƶ�ʱ��ʱ��N/8�����Ҫ���죻�����������бȽϣ����
				 * 			  ��Ҫ�������ڱ���ʣ�������򣨵�ǰʱ��+��N/8��*24+48��������Ϊ����ǰʱ��+��N/8��*24��
				 * 			  ����һ�����Ϊ������8Сʱ���������Ҫ�������ǣ�����������֮�����ȷ�ϣ�
				 */
				if(dueDate%8==0){
					int dayOfWeek = this.dayOfWeek();
					Date dueDateDate;
					//���Ϊ��ĩ�����ΪdayOfWeek<=0;����������Ϊ>0����һΪ1���ܶ�Ϊ2��
					if(dayOfWeek>0&&((5-dayOfWeek)>=(dueDate/8))){//˵��û�г�����ĩ
						dueDateDate = dt.addDate(new Date(), (dueDate/8)*24, Calendar.HOUR);
					}else if(dayOfWeek>0&&((5-dayOfWeek)<(dueDate/8))){//Ϊ������ĩ���
						dueDateDate = dt.addDate(new Date(), (dueDate/8)*24+48, Calendar.HOUR);
					}else{//Ϊ��ĩ�ύ�������
						dueDateDate = dt.addDate(new Date(), dueDate+48, Calendar.HOUR);
					}
					timer.setDueDate(dueDateDate);
					timer.setTaskInstance(taskInstance);//timer.setRepeat("30 seconds");
					Action createAction = DelegationFactory.getAction(DelegationFactory.JPDL_TIMER_EXECUTE_ASSIGN);
					timer.setAction(createAction);//�����Ǹ�ʱ����������
					timer.setTransitionName(configTimer.getInverseNodeName());
					ec.setTimer(timer);
				// modify by guangsa for ��ʱ��Ӧ�ã��۹����չ���8ʱ����in 20100720 begin
				}else{
					//��������ȷ��
				}
				
			}catch(Exception e){
				throw new RuntimeException("����ʵ������timerʱ�����쳣");
			}
			 return timer;
		}
		return null;  
	}
	/**
	 * �����ǰΪ�ܼ�������Calendar��Ϊ����Ϊ0��������һΪ2�����day-1��
	 * @return
	 */
	public int dayOfWeek(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		int day =calendar.get(Calendar.DAY_OF_WEEK);
		return day-1;
	}
	
	/**
	 * �����ʼ���Ϣ
	 * @param dataId��ҵ�������
	 * @param reqClass��ҵ�������
	 * @param goStartState�����˵���ʼ�ڵ�key��
	 * @param processInstanceId����ʵ����Id��
	 * @param taskId������ID��
	 * @param nodeName�����̽ڵ����ƣ�
	 * @param vDesc����������������
	 * @param virtualDefinintionId����������Id��
	 * @param creator(���̴�����)
	 * @param bizParam(����ҵ�����)
	 * @param node�����̽ڵ㣩
	 * @param nodeId�����̽ڵ�Id��
	 */
	private void sendMailMessage(String nodeName,String pageUrl,String dataId,String reqClass,String goStartState,Long processInstanceId,Long taskId,String applyType,String vDesc,Long virtualDefinintionId,String creator,Map bizParam,Node node,String nodeId,String[] auditPers,Map counterSignAuditMegs){
		//���浱ǰ�ڵ��nodeName���Ա������̻��ˣ�
		String virualDesc = "";
		String nowNodeDesc = "";
		String nowNodeName = "";
		String subject = null;
		String content = null;
		String[] ccEmail = null;//�����˵�email��ַ
		String[] configEmail = null;//��̨���ã�����о͸��ƽ��룬û�о�Ϊ��ֵ
		log.info(virualDesc+"��"+nodeName+"�Ľ����¼��и�ָ�ɵ����������ʼ�!");
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
		
//		ConfigUnitRole unitRole = si.findUnitRoleByNodeTypeAndProDesc(virualDesc, nowNodeDesc, String.valueOf(virtualDefinintionId),nodeid);//��̨���ý�ɫ
//		if(unitRole!=null&&!"".equals(unitRole)){
//			
//			int isCreator = unitRole.getIsGiveCreate();//��̨�����Ƿ��д�����
//			//�������õ���Ӧ��ɫ�ҵ���Ӧ����,Ȼ��Ҫƴ����Ӧ���ַ������Զ��ŷָ���
//			List<ConfigUnitRoleTable> list = si.findRoleTableByConfigUnitRole(unitRole);	
//			if(list.isEmpty()){
//				if(isCreator==1){//˵����̨û�����ý�ɫ�����ˣ���ʱ��Ҫ���������Ƿ��д�����
//					List creat = service.find(UserInfo.class, "userName", creator);
//					if(!creat.isEmpty()){
//						UserInfo creatorUser = (UserInfo)(creat.get(0));
//						creatorEmail = creatorUser.getEmail();
//					}else{
//						throw new RuntimeException("������Ϊ�յ����޷�����userinfo�е���Ϣ");
//					}
//				}else{
//					throw new RuntimeException("��̨��ɫ��û������");
//				}
//			}
//			Set user = new HashSet();
//			for(ConfigUnitRoleTable roles : list){
//				Role role = roles.getRole();
//				Set<UserInfo> userinfos=role.getUserInfos();
//				for(UserInfo userinfo:userinfos){
//					if(userinfo.getEmail()!=null&&!"".equals(userinfo.getEmail())){
//						user.add(userinfo.getEmail());//��set���Ϸ�ֹ������
//					}					
//				}							
//			} 
//			if(creatorEmail!=null&&!"".equals(creatorEmail)){//˵�������˰���������
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
//			log.info("***************��һ����Ϊ��̨���õ��ˣ����ҳɹ�");
//		}else{
//			log.info("(����һ��)"+virualDesc+"(����)�Ƿ��ú�̨������Ӧ��������");
//		}
//		
//		//���Ͼ����ַ�����װ�˺�̨���õ��ˣ����趯ָ̬�ɵ���
//		String[] toEmail = null;//��ָ̬���˵�email
//		if(!bizParam.isEmpty()){		
//			String dynaAssign = (String)bizParam.get("userList");
//			if(!"".equals(dynaAssign)&&dynaAssign!=null){	
//				if (dynaAssign.contains("$")) {
//					String[] nodeUser = dynaAssign.split("\\$");//�ж���ڵ���Ϣ(�����ڵ���������Ӧ��)
//					Set user = new HashSet();
//					for (String dyNodeName : nodeUser) {
//						
//						String dynaDesc = dyNodeName.substring(0, dyNodeName.indexOf(":")).trim();//nodeDesc(Ӣ��)
//						String dyUserMeg = dyNodeName.substring(dyNodeName.indexOf(":") + 1);
//						String[] users = dyUserMeg.split(",");//����Ķ����
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
//					String[] users = dyUserMeg.split(",");//����Ķ����
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
//			log.info("***************�ڶ�����Ϊ��ָ̬�ɵ��ˣ����ҳɹ�");
//		}else{
//			log.info("(����һ��)"+virualDesc+"(����)ҵ�����Ϊ��");
//		}	
//		//�ϲ���̨���úͶ�ָ̬�ɵ��˵�email��ַ
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
//		}else if(configEmail!=null&&toEmail==null){//��̨���õ�email��Ϊ�գ�����ָ̬�ɵ�emailΪ��
//			combinEmail = new String[configEmail.length];
//			for(int i=0;i<configEmail.length;i++){
//				com.add(configEmail[i]);
//			}
//			for(int j=0;j<configEmail.length;j++){
//				combinEmail[j] = (String)com.get(j);
//			}	
//		}else if(configEmail==null&&toEmail!=null){//��ָ̬�ɵ�email��Ϊ�գ�����̨���õ�emailΪ��
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
//		log.info("***************Ȼ��ϲ�ǰ�����ֵ�������Ա");
//		//�ټ��Ϻ�̨���õĳ�������Ϣ��δ���ǲ��Ǳ������˵������
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
//				throw new RuntimeException("�ʼ��������õ�Ԫ��ɫû������");
//			}
//			ccEmail = new String[userInfos.size()];
//			
//			List<ConfigUnitMailCC> mailCC = service.find(ConfigUnitMailCC.class, "configUnitMail", unitMail);
//			/*******************************************************************************************************************/
//			//�ⲿ����˼��������ݿ����û����ʼ�����ô�Ҿ������ݿ⡣����û����������ݿ��У��Ҿ����û��ֶ�������ʼ���ַ
//			if(!mailCC.isEmpty()){
//				for(int i=0;i<mailCC.size();i++){
//					
//					ConfigUnitMailCC confirmMailCC = mailCC.get(i);				
//					UserInfo userInfo = confirmMailCC.getUserInfo();
//					
//					if(userInfo!=null&&!"".equals(userInfo)){
//						String email = userInfo.getEmail();
//						if("".equals(email)||email==null){
//							email = confirmMailCC.getMail();//������û���д���ʼ���ַ
//						}
//						ccEmail[i] = email;
//					}				
//				}		
//			}
//			
//		}else if(unitMail==null||"".equals(unitMail)){		
//			ccEmail = new String[]{"guangshunan0813@163.com"};//   guangsa@information.digitalchina.com
//		}
//		log.info("***************��������Ϊ��̨���ó��͵��ˣ����ҳɹ�");
		
		//add by guangsa for sendComplexMail in 2009-07-15 begin
		String auditMeg = "��������ӣ��鿴��ϸ�������������ӣ�----------------------------";
		String workflowEntity = (String)bizParam.get("workflowHistory");		
		List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, processInstanceId);//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
		//add by guangsa for sendComplexMail in 2009-07-15 end
		
		/*************************�ٴε�����������*****************************************/
		if(subject==null||"".equals(subject)){
			subject=creator+"�ύ��"+vDesc+"������!";//"����֪ͨ";
		}
		if(content==null||"".equals(content)){
			content=auditMeg;//"ITIL��Ŀ����һ��������Ҫ������";
		}
		//add by guangsa for avoidAuditpers in 20090729 begin
		//remove by lee for ȥ���������� in 20091221 begin
//		if(ccEmail==null||"".equals(ccEmail)){
//			ccEmail = new String[]{"guangshunan0813@163.com"};
//		}
		//remove by lee for ȥ���������� in 20091221 end
		//add by guangsa for counterSignAuditTaskId in 20090729 begin
		List auditUserEmail = new ArrayList();
		if(counterSignAuditMegs.size()!=0&&counterSignAuditMegs!=null&&!"".equals(counterSignAuditMegs)){
			//���Ϊ��̬��ǩ������ʱ��
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
					log.info(virualDesc+"(����)��"+nodeName+"(�ڵ�)�������ֵ������˷����ʼ�ʱ�����쳣��");
					e.printStackTrace();
				}
			}
		}else{
			//���Ϊ����ָ�ɷ��ʼ�ʱ��
			for(int i=0;i<auditPers.length;i++){
				UserInfo userInfo = (UserInfo)service.findUnique(UserInfo.class, "userName", auditPers[i]);
				auditUserEmail.add(userInfo.getEmail());
			}
			String[] auditEmail = (String[])auditUserEmail.toArray(new String[0]);
			//String context = cs.htmlContent(nodeName,pageUrl,applyType,dataId,reqClass,goStartState,taskId,creator, vDesc, auditHis);
			try{
				//ms.sendMimeMail(auditEmail, ccEmail, null, subject, context, null);
			}catch(Exception e){
				log.info(virualDesc+"(����)��"+nodeName+"(�ڵ�)�������ֵ������˷����ʼ�ʱ�����쳣��");
				e.printStackTrace();
			}
		}
		//add by guangsa for counterSignAuditTaskId in 20090729 begin
		log.info(virualDesc+"��"+nodeName+"(�ڵ�)�������˷����ʼ��ɹ���");
	}
	
	/**
	 * ȥ��ÿ����ɫ�е���
	 * @param list
	 * @return
	 */
	public String overSign(List<ConfigUnitRoleTable> list){
		//�ѽ�ɫ�Ͷ�Ӧ����һһ��Ӧ����
		Map<ConfigUnitRoleTable,List> totalRole = new HashMap<ConfigUnitRoleTable,List>();			
		//�Ȱѽ�ɫ�Ͷ�Ӧ���˶��ŵ�һ��map����
		for(ConfigUnitRoleTable roles : list){
			List allUser = new ArrayList(); 
			Role role = roles.getRole();
			Set<UserInfo> userinfos=role.getUserInfos();
			for(UserInfo userinfo:userinfos){
				allUser.add(userinfo.getUserName());
			}
			totalRole.put(roles, allUser);
		}		
		//����map��ȡ����һ��ֵ���Ҵ�map��ȥ���������±���һ��map�õ����ǵ�һ��ֵ��������
		//�õ���ȥȡ�����˺�ʣ����˱Ƚϣ��ж���û���ظ������ظ���ȥ��
		 Map<ConfigUnitRoleTable,List>  personMap = new HashMap<ConfigUnitRoleTable,List> ();
	      Set<ConfigUnitRoleTable> personSet = totalRole.keySet();
	      Iterator it=personSet.iterator();
	      while(it.hasNext()){
	       ConfigUnitRoleTable key = (ConfigUnitRoleTable)it.next();
	       List person = (List)totalRole.get(key);
	       personMap.put(key, person);
	      }
	    //�ѽ�ɫ���������·�����һ��personMap�У�Ϊ�˷���ȥ�أ������ǲ����ڱ�����ʱ��ɾ������Ԫ�ص�
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
	           rePerson.addAll(remainPer);//ʣ�����е���
	          }
	          
	         for(int i=0;i<person.size();i++){
	          if(rePerson.contains(person.get(i))){
	           person.remove(person.get(i));
	          }
	         }
	         personMap.put(key, person);
		
	      }
	      //����õ��������еĲ��ظ��Ľ�ɫ����Ӧ����
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
