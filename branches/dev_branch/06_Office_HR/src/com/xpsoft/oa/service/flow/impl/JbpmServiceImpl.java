package com.xpsoft.oa.service.flow.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Attribute;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.hibernate.Session;
import org.jbpm.api.Execution;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.HistoryService;
import org.jbpm.api.ProcessDefinition;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.RepositoryService;
import org.jbpm.api.TaskService;
import org.jbpm.api.history.HistoryProcessInstance;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.env.Environment;
import org.jbpm.pvm.internal.env.EnvironmentFactory;
import org.jbpm.pvm.internal.history.model.HistoryProcessInstanceImpl;
import org.jbpm.pvm.internal.model.Activity;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.jbpm.pvm.internal.model.ProcessDefinitionImpl;
import org.jbpm.pvm.internal.model.Transition;
import org.jbpm.pvm.internal.model.TransitionImpl;
import org.jbpm.pvm.internal.svc.TaskServiceImpl;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.xpsoft.core.engine.AsynMailSendProcess;
import com.xpsoft.core.jbpm.jpdl.Node;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.oa.model.flow.ProDefinition;
import com.xpsoft.oa.model.flow.ProUserAssign;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProDefinitionService;
import com.xpsoft.oa.service.flow.ProUserAssignService;
import com.xpsoft.oa.service.flow.ProcessRunService;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.UserSubService;

public class JbpmServiceImpl implements JbpmService {
	private static final Log logger = LogFactory.getLog(JbpmServiceImpl.class);

	@Resource
	private ProcessEngine processEngine;

	@Resource
	private RepositoryService repositoryService;

	@Resource
	private ExecutionService executionService;

	@Resource
	private ProDefinitionService proDefinitionService;

	@Resource
	private TaskService taskService;

	@Resource
	private HistoryService historyService;

	@Resource
	private ProUserAssignService proUserAssignService;

	@Resource
	private UserSubService userSubService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private AppUserService appUserService;

	public Task getTaskById(String taskId) {
		Task task = this.taskService.getTask(taskId);

		return task;
	}

	public void assignTask(String taskId, String userId) {
		this.taskService.assignTask(taskId, userId);
	}

	public void doUnDeployProDefinition(Long defId) {
		this.processRunService.removeByDefId(defId);

		ProDefinition pd = (ProDefinition) this.proDefinitionService.get(defId);
		if (pd != null) {
			this.repositoryService.deleteDeploymentCascade(pd.getDeployId());

			this.proDefinitionService.remove(pd);
		}
	}

	public ProDefinition saveOrUpdateDeploy(ProDefinition proDefinition) {
		if (proDefinition.getDeployId() == null) {
			if (logger.isDebugEnabled()) {
				logger.debug("deploy now===========");
			}
			String deployId = this.repositoryService
					.createDeployment()
					.addResourceFromString("process.jpdl.xml",
							proDefinition.getDefXml()).deploy();

			proDefinition.setDeployId(deployId);

			this.proDefinitionService.save(proDefinition);
		} else {
			this.proDefinitionService.evict(proDefinition);

			ProDefinition proDef = (ProDefinition) this.proDefinitionService
					.get(proDefinition.getDefId());

			if (!proDef.getDefXml().equals(proDefinition.getDefXml())) {
				if (proDef.getDeployId() != null) {
					this.repositoryService.deleteDeployment(proDef
							.getDeployId());
				}
				String deployId = this.repositoryService
						.createDeployment()
						.addResourceFromString("process.jpdl.xml",
								proDefinition.getDefXml()).deploy();
				proDefinition.setDeployId(deployId);
			}

			this.proDefinitionService.merge(proDefinition);
		}

		return proDefinition;
	}

	public ProcessDefinition getProcessDefinitionByKey(String processKey) {
		List list = this.repositoryService.createProcessDefinitionQuery()
				.processDefinitionKey(processKey)
				.orderDesc("versionProperty.longValue").list();
		if ((list != null) && (list.size() > 0)) {
			return (ProcessDefinition) list.get(0);
		}
		return null;
	}

	public ProDefinition getProDefinitionByKey(String processKey) {
		ProcessDefinition processDefinition = getProcessDefinitionByKey(processKey);
		if (processDefinition != null) {
			ProDefinition proDef = this.proDefinitionService
					.getByDeployId(processDefinition.getDeploymentId());
			return proDef;
		}
		return null;
	}

	public String getDefinitionXmlByDefId(Long defId) {
		ProDefinition proDefinition = (ProDefinition) this.proDefinitionService
				.get(defId);
		return proDefinition.getDefXml();
	}

	public String getDefinitionXmlByDpId(String deployId) {
		ProDefinition proDefintion = this.proDefinitionService
				.getByDeployId(deployId);
		return proDefintion.getDefXml();
	}

	public String getDefinitionXmlByExeId(String exeId) {
		String pdId = this.executionService.findExecutionById(exeId)
				.getProcessDefinitionId();
		String deployId = this.repositoryService.createProcessDefinitionQuery()
				.processDefinitionId(pdId).uniqueResult().getDeploymentId();
		return getDefinitionXmlByDpId(deployId);
	}

	public String getDefinitionXmlByPiId(String piId) {
		ProcessInstance pi = this.executionService.createProcessInstanceQuery()
				.processInstanceId(piId).uniqueResult();
		ProcessDefinition pd = this.repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(pi.getProcessDefinitionId())
				.uniqueResult();
		return getDefinitionXmlByDpId(pd.getDeploymentId());
	}

	public ProcessDefinition getProcessDefinitionByTaskId(String taskId) {
		TaskImpl task = (TaskImpl) this.taskService.getTask(taskId);
		ProcessInstance pi = null;
		if (task.getSuperTask() != null)
			pi = task.getSuperTask().getProcessInstance();
		else {
			pi = task.getProcessInstance();
		}
		ProcessDefinition pd = this.repositoryService
				.createProcessDefinitionQuery()
				.processDefinitionId(pi.getProcessDefinitionId())
				.uniqueResult();
		return pd;
	}

	public ProcessInstance getProcessInstance(String piId) {
		ProcessInstance pi = this.executionService.createProcessInstanceQuery()
				.processInstanceId(piId).uniqueResult();
		return pi;
	}

	public List<Node> getTaskNodesByDefId(Long defId) {
		ProDefinition proDefinition = (ProDefinition) this.proDefinitionService
				.get(defId);
		return getTaskNodesFromXml(proDefinition.getDefXml(), false, false);
	}

	public List<Node> getJumpNodesByDeployId(String deployId) {
		/* 273 */ProDefinition proDefinition = this.proDefinitionService
				.getByDeployId(deployId);
		/* 274 */return getTaskNodesFromXml(proDefinition.getDefXml(), false,
				true);
	}

	public List<Node> getFormNodes(Long defId) {
		/* 283 */ProDefinition proDefinition = (ProDefinition) this.proDefinitionService
				.get(defId);
		/* 284 */return getTaskNodesFromXml(proDefinition.getDefXml(), true,
				false);
	}

	public String getStartNodeName(ProDefinition proDefinition) {
		/* 294 */String filePath = AppUtil.getAppAbsolutePath()
				+ "/WEB-INF/FlowForm/" + proDefinition.getName() + "/开始.vm";

		/* 296 */File file = new File(filePath);

		/* 298 */if (file.exists())
			/* 299 */return "开始";
		try {
			/* 302 */Element root = DocumentHelper.parseText(
					proDefinition.getDefXml()).getRootElement();
			List<Element> el = root.elements();
			/* 303 */for (Element elem : el) {
				/* 304 */String tagName = elem.getName();
				/* 305 */if ("start".equals(tagName)) {
					/* 306 */Attribute nameAttr = elem.attribute("name");
					/* 307 */if (nameAttr == null)
						break;
					/* 308 */return nameAttr.getValue();
				}
			}
		} catch (Exception ex) {
			/* 314 */logger.error(ex.getMessage());
		}
		/* 316 */return "开始";
	}

	private List<Node> getTaskNodesFromXml(String xml, boolean includeStart,
			boolean includeEnd) {
		List nodes = new ArrayList();
		try {
			Element root = DocumentHelper.parseText(xml)
					.getRootElement();
			List<Element> el = root.elements();
			for (Element elem : el) {
				String type = elem.getQName().getName();
				if ("task".equalsIgnoreCase(type)) {
					if (elem.attribute("name") != null) {
						Node node = new Node(elem.attribute("name")
								.getValue(), "任务节点");
						nodes.add(node);
					}
					} else if ((includeStart)
						&& ("start".equalsIgnoreCase(type))) {
					if (elem.attribute("name") != null) {
						Node node = new Node(elem.attribute("name")
								.getValue(), "开始节点");
						nodes.add(node);
					}
					} else if ((includeEnd)
						&& (type.startsWith("end"))) {
					Node node = new Node(elem.attribute("name")
							.getValue(), "结束节点");
					nodes.add(node);
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return nodes;
	}

	public String startProcess(String deployId, Map variables) {
		ProcessDefinition pd = this.repositoryService
				.createProcessDefinitionQuery().deploymentId(deployId)
				.uniqueResult();
		clearSession();

		ProcessInstance pi = this.executionService
				.startProcessInstanceById(pd.getId(), variables);

		// add by jack for send approve email to approve user at 2011-9-23 begin
		ProcessRun pr = this.processRunService.getByTaskId(pi.getId());
		String cc = appUserService.findByFullName(pr.getCreator()).getEmail();
		// add by jack for send approve email to approve user at 2011-9-23 end
		
		String assignId = (String) variables.get("flowAssignId");

		String signUserIds = (String) variables.get("signUserIds");

		if (StringUtils.isNotEmpty(signUserIds)) {
			List newTasks = getTasksByPiId(pi.getId());
			Iterator localIterator = newTasks.iterator();
			if (localIterator.hasNext()) {
				Task nTask = (Task) localIterator.next();
				newTask(nTask.getId(), signUserIds);
				//add by jack for send approve email to approve user at 2011-9-23 begin
				sendMsgToApprover(signUserIds, cc, (String)AppUtil.getSysConfig().get("process.sendmail.vmPath"));
				//add by jack for send approve email to approve user at 2011-9-23 end
			}
		} else {
			assignTask(pi, pd, assignId, null);
		}

		return pi.getId();
	}

	public ProcessInstance getProcessInstanceByExeId(String executionId) {
		Execution execution = this.executionService
				.findExecutionById(executionId);
		return (ProcessInstance) execution.getProcessInstance();
	}

	public ProcessInstance getProcessInstanceByTaskId(String taskId) {
		TaskImpl taskImpl = (TaskImpl) this.taskService.getTask(taskId
				.toString());
		if (taskImpl.getSuperTask() != null) {
			taskImpl = taskImpl.getSuperTask();
		}
		return taskImpl.getProcessInstance();
	}

	public void assignTask(ProcessInstance pi, ProcessDefinition pd,
			String assignId, String taskName) {
		if (pd == null) {
			pd = this.repositoryService.createProcessDefinitionQuery()
					.processDefinitionId(pi.getProcessDefinitionId())
					.uniqueResult();
		}
		

		// add by jack for send approve email to approve user at 2011-9-23 begin
		ProcessRun pr = this.processRunService.getByPiId(pi.getId());
		String cc = appUserService.findByFullName(pr.getCreator()).getEmail();
		// add by jack for send approve email to approve user at 2011-9-23 end

		List<Task> taskList = null;

		if (StringUtils.isNotEmpty(taskName)) {
			taskList = this.taskService.createTaskQuery()
					.processInstanceId(pi.getId()).activityName(taskName)
					.list();
		}

		if ((taskList == null) || (taskList.size() == 0)) {
			taskList = getTasksByPiId(pi.getId());
		}

		for (Task task : taskList) {
			if (StringUtils.isNotEmpty(assignId)) {
				// 当assignId时多个的时候，就遍历存入jbpm4_participation，但是只要有一个审批通过就被通过
				String[] assignIds = assignId.split("[,]");
				if (assignIds.length > 1) {
					for (String asid : assignIds) {
						this.taskService.addTaskParticipatingUser(task.getId(),
								asid, "candidate");
					}
				} else {
					this.taskService.assignTask(task.getId(), assignId);
				}

				//add by jack for send approve email to approve user at 2011-9-23 begin
				sendMsgToApprover(assignId, cc, (String)AppUtil.getSysConfig().get("process.sendmail.vmPath"));
				//add by jack for send approve email to approve user at 2011-9-23 end
			} else {
				ProUserAssign assign = this.proUserAssignService
						.getByDeployIdActivityName(pd.getDeploymentId(),
								task.getActivityName());
				String tos = "";

				if (assign != null) {
					if ("__start".equals(assign.getUserId())) {
						AppUser flowStartUser = (AppUser) this.executionService.getVariable(pi.getId(), "flowStartUser");
						if (flowStartUser != null){
							this.taskService.assignTask(task.getId(), flowStartUser.getUserId().toString());
							tos += flowStartUser.getUserId().toString() + ",";
						}
					} else {
						StringBuffer upIds;
						Object localObject;
						Long userId;
						if ("__super".equals(assign.getUserId())) {
							AppUser flowStartUser = (AppUser) this.executionService.getVariable(pi.getId(), "flowStartUser");

							if (flowStartUser != null) {
								List superUserIds = this.userSubService.upUser(flowStartUser.getUserId());
								upIds = new StringBuffer();
								for (localObject = superUserIds.iterator(); ((Iterator) localObject).hasNext();) {
									userId = (Long) ((Iterator) localObject).next();
									upIds.append(userId).append(",");
									tos += userId + ",";
								}
								if (superUserIds.size() > 0)
									upIds.deleteCharAt(upIds.length() - 1);
								else {
									upIds.append(flowStartUser.getUserId());
									tos += flowStartUser.getUserId() + ",";
								}
								this.taskService.addTaskParticipatingUser(task.getId(), upIds.toString(),"candidate");
							}
						} else if (StringUtils.isNotEmpty(assign.getUserId())) {
							String[] userIds = assign.getUserId().split(",");

							if ((userIds != null) && (userIds.length > 1)) {

								for (int upIds1 = 0; upIds1 < userIds.length; upIds1++) {
									String uId = userIds[upIds1];
									this.taskService.addTaskParticipatingUser(task.getId(), uId, Participation.CANDIDATE);
									tos += uId + ",";
								}
							} else {
								this.taskService.assignTask(task.getId(), assign.getUserId());
								tos += assign.getUserId() + ",";
							}
						}
					}
					//这个取到角色对应的人的时候是顺签
					if (StringUtils.isNotEmpty(assign.getRoleId())){
						String roleId = assign.getRoleId();
						this.taskService.addTaskParticipatingGroup(task.getId(), roleId,Participation.CANDIDATE);

						//add by jack for send approve email to approve user at 2011-9-23 begin
						List<AppUser> userList = this.appUserService.findByRoleId(Long.parseLong(roleId));
						if(userList.size()>0){
							for(AppUser item  : userList){
								tos += item.getId() + ",";
							}
						}
						//add by jack for send approve email to approve user at 2011-9-23 end
					}
				} else {
					AppUser flowStartUser = (AppUser) this.executionService.getVariable(pi.getId(), "flowStartUser");
					if (flowStartUser != null){
						this.taskService.assignTask(task.getId(), flowStartUser.getUserId().toString());
						tos += flowStartUser.getUserId().toString() + ",";
					}
				}
				
				//add by jack for send approve email to approve user at 2011-9-23 begin
				tos = tos.substring(0,tos.length()-1);
				sendMsgToApprover(tos, cc, (String)AppUtil.getSysConfig().get("process.sendmail.vmPath"));
				//add by jack for send approve email to approve user at 2011-9-23 end
			}
		}
	}

	public List<Transition> getTransitionsForSignalProcess(String piId) {
		ProcessInstance pi = this.executionService
				.findProcessInstanceById(piId);
		EnvironmentFactory environmentFactory = (EnvironmentFactory) this.processEngine;
		Environment env = environmentFactory.openEnvironment();
		try {
			ExecutionImpl executionImpl = (ExecutionImpl) pi;
			Activity activity = executionImpl.getActivity();

			List<Transition> localList = activity.getOutgoingTransitions();
			return localList;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			env.close();
		}
		return null;
	}

	public List<Transition> getTransitionsByTaskId(String taskId) {
		TaskImpl task = (TaskImpl) this.taskService.getTask(taskId);
		if (task.getSuperTask() != null) {
			task = task.getSuperTask();
		}
		EnvironmentFactory environmentFactory = (EnvironmentFactory) this.processEngine;
		Environment env = environmentFactory.openEnvironment();
		try {
			ProcessDefinitionImpl pd = task.getProcessInstance()
					.getProcessDefinition();
			ActivityImpl activityFind = pd.findActivity(task.getActivityName());

			if (activityFind != null)
				return activityFind.getOutgoingTransitions();
		} finally {
			env.close();
		}
		return new ArrayList();
	}

	public void addOutTransition(ProcessDefinitionImpl pd, String sourceName,
			String destName) {
		EnvironmentFactory environmentFactory = (EnvironmentFactory) this.processEngine;
		Environment env = null;
		try {
			env = environmentFactory.openEnvironment();

			ActivityImpl sourceActivity = pd.findActivity(sourceName);

			ActivityImpl destActivity = pd.findActivity(destName);

			TransitionImpl transition = sourceActivity
					.createOutgoingTransition();
			transition.setName("to" + destName);
			transition.setDestination(destActivity);

			sourceActivity.addOutgoingTransition(transition);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			if (env != null)
				env.close();
		}
	}

	public void removeOutTransition(ProcessDefinitionImpl pd,
			String sourceName, String destName) {
		EnvironmentFactory environmentFactory = (EnvironmentFactory) this.processEngine;
		Environment env = null;
		try {
			env = environmentFactory.openEnvironment();

			ActivityImpl sourceActivity = pd.findActivity(sourceName);

			List<Transition> trans = sourceActivity.getOutgoingTransitions();
			for (Transition tran : trans)
				if (destName.equals(tran.getDestination().getName())) {
					trans.remove(tran);
					break;
				}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			if (env != null)
				env.close();
		}
	}

	public List<Transition> getFreeTransitionsByTaskId(String taskId) {
		TaskImpl task = (TaskImpl) this.taskService.getTask(taskId);

		List outTrans = new ArrayList();

		if (task.getSuperTask() != null) {
			task = task.getSuperTask();
		}
		EnvironmentFactory environmentFactory = (EnvironmentFactory) this.processEngine;
		Environment env = null;
		try {
			env = environmentFactory.openEnvironment();
			ProcessDefinitionImpl pd = task.getProcessInstance()
					.getProcessDefinition();
			ActivityImpl curActivity = pd.findActivity(task.getActivityName());

			List<Node> allTaskNodes = getJumpNodesByDeployId(pd
					.getDeploymentId());
			boolean listForwardNode = Boolean.valueOf(AppUtil
					.getPropertity("app.listForwardNode"));
			for (Node taskNode : allTaskNodes) {

				// modified by awen for not skip some node on 2011-07-22 begin
				if (taskNode.getName().equals(task.getActivityName())) {
					if (listForwardNode) {
						continue;
					} else {
						break;
					}
				}
				// modified by awen for not skip some node on 2011-07-22 end

				TransitionImpl transition = curActivity
						.createOutgoingTransition();

				transition.setName("to" + taskNode.getName());
				transition.setDestination(pd.findActivity(taskNode.getName()));

				curActivity.getOutgoingTransitions().remove(transition);

				outTrans.add(transition);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		} finally {
			if (env != null)
				env.close();
		}

		return outTrans;
	}

	public String getProcessDefintionXMLByPiId(String piId) {
		ProcessRun processRun = this.processRunService.getByPiId(piId);
		return processRun.getProDefinition().getDefXml();
	}

	public List<Task> getTasksByPiId(String piId) {
		List taskList = this.taskService.createTaskQuery()
				.processInstanceId(piId).list();
		return taskList;
	}

	public String getNodeType(String xml, String nodeName) {
		/* 668 */String type = "";
		try {
			Element root = DocumentHelper.parseText(xml).getRootElement();
			List<Element> el = root.elements();
			/* 671 */for (Element elem : el)
				/* 672 */if (elem.attribute("name") != null) {
					/* 673 */String value = elem.attributeValue("name");
					/* 674 */if (value.equals(nodeName)) {
						/* 675 */type = elem.getQName().getName();
						/* 676 */return type;
					}
				}
		} catch (Exception ex) {
			/* 681 */logger.info(ex.getMessage());
		}
		/* 683 */return type;
	}

	protected void clearSession() {
		/* 687 */EnvironmentFactory environmentFactory = (EnvironmentFactory) this.processEngine;
		/* 688 */Environment env = environmentFactory.openEnvironment();
		try {
			/* 690 */Session session = (Session) env.get(Session.class);
			/* 691 */session.clear();
		} finally {
			/* 693 */env.close();
		}
	}

	protected void flush() {
		/* 698 */EnvironmentFactory environmentFactory = (EnvironmentFactory) this.processEngine;
		/* 699 */Environment env = environmentFactory.openEnvironment();
		try {
			/* 701 */Session session = (Session) env.get(Session.class);
			/* 702 */session.flush();
		} finally {
			/* 704 */env.close();
		}
	}

	public void completeTask(String taskId, String transitionName,
			String destName, Map variables) {
		TaskImpl taskImpl = (TaskImpl) this.taskService.getTask(taskId);

		// add by jack for send approve email to approve user at 2011-9-23 begin
		ProcessRun pr = this.processRunService.getByTaskId(taskId);
		String cc = appUserService.findByFullName(pr.getCreator()).getEmail();
		// add by jack for send approve email to approve user at 2011-9-23 end

		// String sourceName = taskImpl.getName();
		String sourceName = taskImpl.getActivityName();

		TaskImpl superTask = taskImpl.getSuperTask();

		ProcessDefinitionImpl pd = (ProcessDefinitionImpl) getProcessDefinitionByTaskId(taskId);
		ProcessInstance pi = null;
		String executionId = null;

		boolean isTransitionExist = false;

		List<Transition> trans = getTransitionsByTaskId(taskId);
		for (Transition tran : trans) {
			if (tran.getDestination().getName().equals(destName)) {
				isTransitionExist = true;
				break;
			}
		}

		if (!isTransitionExist && destName != null) {
			addOutTransition(pd, sourceName, destName);
		}

		if (superTask != null) {
			pi = superTask.getProcessInstance();
			executionId = superTask.getExecutionId();
			if (logger.isDebugEnabled()) {
				logger.debug("Super task is not null, task name is:"
						+ superTask.getActivityName());
			}

			if (superTask.getSubTasks() != null) {
				if (superTask.getSubTasks().size() == 1) {
					this.taskService.setVariables(taskId, variables);
					clearSession();

					this.taskService.completeTask(taskId);

					this.taskService.completeTask(superTask.getId(),
							transitionName);
				} else {
					this.taskService.setVariables(taskId, variables);
					clearSession();
					this.taskService.completeTask(taskId);

					return;
				}
			}
		} else {
			pi = taskImpl.getProcessInstance();
			executionId = taskImpl.getExecutionId();
			this.taskService.setVariables(taskId, variables);
			flush();
			this.taskService.completeTask(taskId, transitionName);
		}

		if (!isTransitionExist && destName != null) {
			removeOutTransition(pd, sourceName, destName);
		}

		boolean isEndProcess = isProcessInstanceEnd(executionId);
		if (isEndProcess) {
			ProcessRun processRun = this.processRunService
					.getByPiId(executionId);
			if (processRun != null) {
				processRun.setPiId(null);
				processRun.setRunStatus(ProcessRun.RUN_STATUS_FINISHED);
				this.processRunService.save(processRun);
			}
			return;
		}

		String signUserIds = (String) variables.get("signUserIds");
		if ((destName != null) && (StringUtils.isNotEmpty(signUserIds))) {
			List<Task> newTasks = getTasksByPiId(pi.getId());
			for (Task nTask : newTasks) {
				if (destName.equals(nTask.getName())) {
					newTask(nTask.getId(), signUserIds);
					break;
				}
			}
			// add by jack for send approve email to approve user at 2011-9-23 begin
			sendMsgToApprover(signUserIds, cc, (String) AppUtil.getSysConfig()
					.get("process.sendmail.vmPath"));
			// add by jack for send approve email to approve user at 2011-9-23 end
			return;
		} else if ((destName == null) && (StringUtils.isNotEmpty(signUserIds))) {
			List<Task> newTasks = getTasksByPiId(pi.getId());
			if (newTasks.size() > 0) {
				Task nTask = newTasks.get(0);
				newTask(nTask.getId(), signUserIds);
			} else {
				logger.debug("newTasks is null");
			}
			// add by jack for send approve email to approve user at 2011-9-23  begin
			sendMsgToApprover(signUserIds, cc, (String) AppUtil.getSysConfig()
					.get("process.sendmail.vmPath"));
			// add by jack for send approve email to approve user at 2011-9-23 end
			return;
		}
		destName = null;

		String assignId = (String) variables.get("flowAssignId");

		assignTask(pi, null, assignId, destName);
	}

	/**
	 * 异步发送邮件通知
	 * 
	 * @Methods Name sendMsgToApprover
	 * @Create In 2011-9-23 By Jack
	 * @param assignIds
	 *            审批人ID字符串数组
	 * @param cc
	 *            流程发起人
	 * @param vmPath
	 *            邮件模板地址
	 */
	private void sendMsgToApprover(String assignIds, String cc, String vmPath) {

		List<AppUser> tos = getAssignUserEmail(assignIds);
		if(tos != null && tos.size() > 0){
			AsynMailSendProcess amsp = new AsynMailSendProcess(tos, cc, vmPath, assignIds);
			Thread td = new Thread(amsp);
			td.start();
		}

	}

	/**
	 * 增加私有方法获取审批人邮件信息以提供邮件通知使用
	 * 
	 * @Methods Name getAssignUserEmail
	 * @Create In 2011-9-23 By Jack
	 * @param assignIds
	 *            将分配审批人ID
	 * @return List<AppUser> 用户列表
	 */
	private List<AppUser> getAssignUserEmail(String assignIds) {
		String[] userIds = assignIds.split("[,]");
		List<AppUser> mailList = new ArrayList<AppUser>();
		if(userIds.length > 0){
			for (String id : userIds) {
				mailList.add(((AppUser) this.appUserService.get(Long.parseLong(id))));
			}
		}else{
			mailList.add(((AppUser) this.appUserService.get(Long.parseLong(assignIds))));
		}

		return mailList;
	}

	protected boolean isProcessInstanceEnd(String executionId) {
		HistoryProcessInstance hpi = this.historyService
				.createHistoryProcessInstanceQuery()
				.processInstanceId(executionId).uniqueResult();
		if (hpi != null) {
			String endActivityName = ((HistoryProcessInstanceImpl) hpi)
					.getEndActivityName();
			if (endActivityName != null) {
				return true;
			}
		}
		return false;
	}

	public void newTask(String parentTaskId, String assignIds) {
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl) this.taskService;
		Task parentTask = taskServiceImpl.getTask(parentTaskId);

		if (assignIds != null) {
			String[] userIds = assignIds.split("[,]");
			for (int i = 0; i < userIds.length; i++) {
				TaskImpl task = (TaskImpl) taskServiceImpl
						.newTask(parentTaskId);
				task.setAssignee(userIds[i]);
				task.setName(parentTask.getName() + "-" + (i + 1));
				task.setActivityName(parentTask.getName());
				task.setDescription(parentTask.getDescription());

				taskServiceImpl.saveTask(task);
			}
		}
	}

	public void signalProcess(String executionId, String transitionName,
			Map<String, Object> variables) {
		this.executionService.setVariables(executionId, variables);
		this.executionService.signalExecutionById(executionId, transitionName);
	}

	public void endProcessInstance(String piId) {
		ExecutionService executionService = this.processEngine
				.getExecutionService();
		executionService.endProcessInstance(piId, "ended");
	}

}