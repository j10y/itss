package com.zsgj.info.appframework.extjs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.jbpm.JbpmContext;
import org.jbpm.context.exe.ContextInstance;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.def.Task;
import org.jbpm.taskmgmt.exe.TaskInstance;

//import com.digitalchina.info.framework.workflow.handler.TimerCreateActionHandler;
import com.zsgj.info.appframework.pagemodel.entity.PageModel;
import com.zsgj.info.appframework.pagemodel.service.PageModelService;
import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.exception.RuleFileException;
import com.zsgj.info.framework.message.mail.service.MailSenderService;
import com.zsgj.info.framework.security.entity.Department;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.HttpUtil;
import com.zsgj.info.framework.util.PropertiesUtil;
import com.zsgj.info.framework.workflow.ConfigUnitService;
import com.zsgj.info.framework.workflow.ContextService;
import com.zsgj.info.framework.workflow.ParameterService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.TaskAssignService;
import com.zsgj.info.framework.workflow.TaskPageModelService;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.WorkFlowGoBackService;
import com.zsgj.info.framework.workflow.WorkflowConstants;
import com.zsgj.info.framework.workflow.action.SynchronousAction;
import com.zsgj.info.framework.workflow.base.FormHelper;
import com.zsgj.info.framework.workflow.base.JbpmContextFactory;
import com.zsgj.info.framework.workflow.entity.ConfigUnitMail;
import com.zsgj.info.framework.workflow.entity.ConfigUnitRole;
import com.zsgj.info.framework.workflow.entity.DefinitionPreAssign;
import com.zsgj.info.framework.workflow.entity.TaskPreAssign;
import com.zsgj.info.framework.workflow.entity.VirtualDefinitionInfo;
import com.zsgj.info.framework.workflow.entity.VirtualNodeInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRecordTaskInfo;
import com.zsgj.info.framework.workflow.entity.WorkflowRegressionParameters;
import com.zsgj.info.framework.workflow.entity.WorkflowRole;
import com.zsgj.info.framework.workflow.info.HistoryInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;

public class Workflow extends HttpServlet {
	/**
	 * @Field long serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	private ProcessService ps = (ProcessService) ContextHolder.getBean("processService");
	private Service service = (Service) ContextHolder.getBean("baseService");
	private TaskService tm = (TaskService) ContextHolder.getBean("taskService");
	private ContextService vm = (ContextService) ContextHolder
			.getBean("contextService");
	private TaskService ts = (TaskService) ContextHolder.getBean("taskService");
	private ParameterService pms = (ParameterService) ContextHolder
			.getBean("parameterService");
	private TaskAssignService tas = (TaskAssignService) ContextHolder
			.getBean("taskAssignService");
	private PageModelService pagemodels = (PageModelService) ContextHolder
			.getBean("pageModelService");
	private TaskAssignService si = (TaskAssignService) ContextHolder
			.getBean("taskAssignService");
	private MailSenderService ms = (MailSenderService) ContextHolder
			.getBean("mailSenderService");
	private ConfigUnitService cs = (ConfigUnitService) ContextHolder
			.getBean("configUnitService");
	private WorkFlowGoBackService wfBack = (WorkFlowGoBackService) ContextHolder.getBean("workflowGoBackService");
	private TaskPageModelService pageModelService = (TaskPageModelService) ContextHolder.getBean("taskPageModelService");
	public Workflow() {
		super();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setCharacterEncoding("gbk");
		String msg = "";
		String method = request.getParameter("method");
		if (StringUtils.isBlank(method)) {
			msg = "Error: no method specified.";
		} else {
			if (method.trim().equalsIgnoreCase("apply")) {// 申请
				msg = apply(request);
			} else if (method.trim().equalsIgnoreCase("audit")) {// 审核
				msg = audit(request);
			} else if (method.trim().equalsIgnoreCase("tasks")) {// 任务列表
				msg = tasks(request);
			} else if (method.trim().equalsIgnoreCase("next")) {// 任务列表
				msg = next(request);
			} else if (method.trim().equalsIgnoreCase("assign")) {// 指派
				// msg = export(request);
			} else if (method.trim().equalsIgnoreCase("getDataFromPage")) {//从页面上得到数据放进实例的bizParam中
				msg = getDataFromPage(request);
			} else if (method.trim().equalsIgnoreCase("getData")) {//从页面中获得指派人的信息放进bizParam中的userList中
				msg = getData(request);
			} else if (method.trim().equalsIgnoreCase("reAssignToNode")) {//给当前节点指派人
				msg = reAssignToNode(request);
			} else if (method.trim().equalsIgnoreCase("addMarkUsersInfo")) {//获得加签人的信息放进bizParam中的addMarkUsers中
				msg = addMarkUsersInfo(request);
			} else if (method.trim().equalsIgnoreCase("getMarkUsers")) {//从bizParam中的addMarkUsers获得加签人的信息
				msg = getMarkUsers(request);
			} else if (method.trim().equalsIgnoreCase("getMarkUsersToNextNode")) {//从bizParam中的addMarkUsersToNextNode获得加签人的信息
				msg = getMarkUsersToNextNode(request);
			} else if (method.trim().equalsIgnoreCase("addMarkUsersInfoToNextNode")) {//获得下一个节点加签人的信息放进bizParam中的addMarkUsersToNextNode中
				msg = addMarkUsersInfoToNextNode(request);
			}else if (method.trim().equalsIgnoreCase("deleteMarkUsersInfo")) {//删除加签人的信息放进bizParam中的addMarkUsers中
				msg = deleteMarkUsersInfo(request);
			} else if (method.trim().equalsIgnoreCase("deleteMarkUsersInfoToNextNode")) {//获得下一个节点加签人的信息放进bizParam中的addMarkUsersToNextNode中
				msg = deleteMarkUsersInfoToNextNode(request);
			} else if (method.trim().equalsIgnoreCase("getTaskInfoMessage")) {//得到任务信息
				msg = getTaskInfoMessage(request);
			} else if (method.trim().equalsIgnoreCase("getAllNodeMessagek")) {//得到某流程的节点信息 
				msg = getAllNodeMessagek(request);
			}else if (method.trim().equalsIgnoreCase("getWorkFlowSkipGoBack")) {//得到任务信息
				msg = getWorkFlowSkipGoBack(request);
			}else if (method.trim().equalsIgnoreCase("StartStateAfreshSubmit")) {//得到任务信息
				msg = StartStateAfreshSubmit(request);
			}else if (method.trim().equalsIgnoreCase("StartStateToCancelFlow")) {//得到任务信息
				msg = StartStateToCancelFlow(request);
			}else if (method.trim().equalsIgnoreCase("getWorkFlowGoBack")) {//获得后退流程的参数
				try {
					msg = getWorkFlowGoBack(request);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		response.setContentType("text/plain");
		PrintWriter out = response.getWriter();
		out.println(msg);
		out.flush();
		out.close();
	}

	/**
	 * 根据流程名称和部门名称计算基本的预指派信息 再根据代理设置信息修正预指派信息 再根据用户临时指定的预指派信息完备预指派信息
	 * 最后返回实际需要的完整预指派信息
	 * 
	 * @Methods Name getTaskPreAssign
	 * @Create In Dec 15, 2008 By yang
	 * @param definitionName
	 * @param departmentCode
	 * @param proxies
	 * @param emptyRols
	 * @return
	 * @ReturnType List<TaskPreAssign>
	 */
	private List<TaskPreAssign> sumTaskPreAssign(
			Map<DefinitionPreAssign, List<UserInfo>> mapAssign,
			List<TaskPreAssign> proxies, String userAssign) {
		// {"wfRoleId":"userName2,userName2",...}
		List<TaskPreAssign> preAssign = new ArrayList<TaskPreAssign>();
		// 整理用户指派信息
		Map<String, List<String>> mapUserAssign = new HashMap<String, List<String>>();
		JSONObject jo = JSONObject.fromObject(userAssign);
		Set<String> userKeySet = jo.keySet();
		for (String key : userKeySet) {
			String valueStr = jo.getString(key);
			String[] values = valueStr.split(",");
			List<String> users = new ArrayList<String>();
			for (String value : values) {
				users.add(value);
			}
			mapUserAssign.put(key, users);
		}

		// 开始计算

		Set<DefinitionPreAssign> defKeySet = mapAssign.keySet();
		for (DefinitionPreAssign def : defKeySet) {

			// 其次看有没有可计算的角色设定用户，如果有，取角色用户
			List<UserInfo> users = mapAssign.get(def);
			if (users != null) {
				for (UserInfo user : users) {
					TaskPreAssign tpa = new TaskPreAssign();
					tpa.setActorName(user.getRealName());
					tpa.setActorId(user.getUserName());
					tpa.setTaskName(def.getNodeName());
					tpa.setDefinitionDesc(def.getDefinitionDesc());
					tpa.setDefinitionName(def.getDefinitionName());
					tpa.setDepartmentCode(def.getDepartmentCode());
					tpa.setDepartmentName(def.getDepartmentName());
					preAssign.add(tpa);
				}
			}
			// 最后，从用户预指派的用户中补全审批用户信息
			else {
				if (def.getWorkflowRole() != null) {// 流程角色为空表示此节点不需要指派，返回发起人
					String wfRoleId = def.getWorkflowRole().getId() + "";
					List<String> userAssignUsers = mapUserAssign.get(wfRoleId);
					if (userAssignUsers == null || userAssignUsers.isEmpty()) {
						throw new RuntimeException(
								"no user assign to workflowRole: "
										+ def.getWorkflowRole().getName());
					} else {
						for (String userName : userAssignUsers) {
							TaskPreAssign tpa = new TaskPreAssign();
							List<UserInfo> userInfos = service.find(
									UserInfo.class, "userName", userName);
							if (userInfos == null || userInfos.isEmpty()) {
								throw new RuntimeException(
										"No such user userName is: " + userName);
							}
							tpa.setActorName(userInfos.get(0).getRealName());
							tpa.setActorId(userName);
							tpa.setTaskName(def.getNodeName());
							tpa.setDefinitionDesc(def.getDefinitionDesc());
							tpa.setDefinitionName(def.getDefinitionName());
							tpa.setDepartmentCode(def.getDepartmentCode());
							tpa.setDepartmentName(def.getDepartmentName());
							preAssign.add(tpa);
						}
					}
				}
			}
		}
		// 如果有代理信息，加入代理信息
		for (TaskPreAssign proxie : proxies) {
			TaskPreAssign tpa = new TaskPreAssign();
			tpa.setActorName(proxie.getActorName());
			tpa.setActorId(proxie.getActorId());
			tpa.setTaskName(proxie.getTaskName());
			tpa.setDefinitionDesc(proxie.getDefinitionDesc());
			tpa.setDefinitionName(proxie.getDefinitionName());
			tpa.setDepartmentCode(proxie.getDepartmentCode());
			tpa.setDepartmentName(proxie.getDepartmentName());
			tpa.setProxyBegin(proxie.getProxyBegin());
			tpa.setProxyEnd(proxie.getProxyEnd());
			tpa.setProxyId(proxie.getProxyId());
			tpa.setProxyName(proxie.getProxyName());
			preAssign.add(tpa);
		}
		return preAssign;
	}

	/**
	 * 根据部门和流程计算每个流程角色所包含的用户 如果这个部门还没有指定的用户，则为map成null.
	 * 返回的是个动态的计算值，不是数据库中的实体，不可以保存
	 * 
	 * @Methods Name getPreAssignByDef
	 * @Create In Dec 15, 2008 By yang
	 * @param definitionName
	 * @param departmentCode
	 * @return
	 * @ReturnType Map<WorkflowRole,List<UserInfo>>
	 */
	private Map<DefinitionPreAssign, List<UserInfo>> getPreAssignByDef(
			String definitionName, String departmentCode) {
		// 需要的参数
		if (departmentCode == null || departmentCode.equals("")) {
			throw new RuntimeException("必须指明部门。");
		}
		// 现根据部门号找到部门
		List<Department> depts = service.find(Department.class, "departCode",
				new Long(departmentCode));
		if (depts == null || depts.isEmpty()) {
			throw new RuntimeException("部门编号不合法。");
		}

		// 整个思路就是先在流程基本信息当中找到此流程名对应的信息；获得所有需要指派的角色
		List<DefinitionPreAssign> defs = service.find(
				DefinitionPreAssign.class, "definitionName", definitionName);

		List<WorkflowRole> deptRoles = tas.findWorkflowRoleByDepartment(depts
				.get(0));// 部门下所有的流程角色
		Map<DefinitionPreAssign, List<UserInfo>> mapPreAssign = new HashMap<DefinitionPreAssign, List<UserInfo>>();
		for (DefinitionPreAssign def : defs) {
			WorkflowRole defRole = def.getWorkflowRole();
			def.setDepartmentCode(departmentCode);
			def.setDepartmentName(depts.get(0).getDepartName());
			if (deptRoles.contains(defRole)) {// 是本流程的角色
				List<UserInfo> users = tas
						.findUserInfoByWorkflowRoleAndDepartment(defRole, depts
								.get(0));
				mapPreAssign.put(def, users);
			} else {
				mapPreAssign.put(def, null);
			}
		}
		return mapPreAssign;
	}

	/**
	 * 查找还没有指派具体人员的流程角色
	 * 
	 * @Methods Name preAssign
	 * @Create In Dec 12, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String getEmptyRoles(
			Map<DefinitionPreAssign, List<UserInfo>> mapPreAssign) {
		String json = "";
		// 需要的参数
		Set<DefinitionPreAssign> defs = mapPreAssign.keySet();
		// 编写json
		for (DefinitionPreAssign def : defs) {
			if (mapPreAssign.get(def) == null) {// 筛选还没有指派用户的角色
				WorkflowRole wfrole = def.getWorkflowRole();
				if (wfrole != null) {// 流程角色还没有定义时，含义是本环节回到发起人
					String strRole = "";
					strRole += "'id':'" + wfrole.getId() + "',";
					strRole += "'name':'" + wfrole.getName() + "'";
					strRole = "{" + strRole + "},";
					json += strRole;
				}
			}
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "[" + json + "]";
		// [{'id':'wfroleId','name':'wfroleName'},{...}...]
		return json;
	}

	/**
	 * 获得某流程的审批历史信息
	 * 
	 * @Methods Name history
	 * @Create In Sep 10, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String next(HttpServletRequest request) {
		// 需要的参数
		String taskId = request.getParameter("taskid");
		String procId = request.getParameter("procid");
		long instId = 0;
		if (procId != null && procId.trim().length() != 0) {
			instId = Long.parseLong(procId);
			List tasks = ps.getActiveTasks(instId);
			if (!tasks.isEmpty()) {
				taskId = ((TaskInfo) tasks.get(0)).getId() + "";
			}
		} else if (taskId != null && taskId.trim().length() != 0) {
			instId = ts.getProcessInfo(Long.parseLong(taskId)).getId();
		} else {
			System.out.println("ListHistoryAction参数不对");
		}

		// List<HistoryInfo> list = ps.getHistory(instId);
		List<HistoryInfo> list = new ArrayList();
		List tasks = ps.getActiveTasks(instId);
		for (int i = 0; i < tasks.size(); i++) {
			TaskInfo ti = (TaskInfo) tasks.get(i);
			HistoryInfo hi = new HistoryInfo(ti);
			hi.setTaskName(ti.getName());
			list.add(hi);
		}
		String json = "";
		for (HistoryInfo historyInfo : list) {
			String str = "";
			// historyInfo.getComments()
			// 用实际名称代替用户系统名
			String realName = getUserRealNameByName(historyInfo.getActorId());
			str += "actorId:'" + realName + "',";
			str += "date:'" + historyInfo.getDate() + "',";
			str += "definitionName:'" + historyInfo.getDefinitionName() + "',";
			str += "processId:'" + historyInfo.getProcessId() + "',";
			str += "nodeName:'" + historyInfo.getNodeName() + "',";
			str += "taskName:'" + historyInfo.getTaskName() + "',";
			str += "name:'" + historyInfo.getName() + "',";
			str += "taskId:'" + historyInfo.getTaskId() + "'";
			str = "{" + str + "},";
			json += str;
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		json = "[" + json + "]";
		// json = "{data:["+json+"]}";
		// json = "{success:true,data:"+json+"}";
		return json;
	}

	/**
	 * 提出申请（启动工作流）,需要考虑到一个节点有可能指派给多人的情况 支持单节点单行数据指派，形式为a|b|c, 现在要按虚拟流程来
	 * 
	 * @Methods Name apply
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	private String apply(HttpServletRequest request) {
		String json = "";
		// 虚拟流程名
		String definitionName = request.getParameter("defname");
		String buzzParameters = request.getParameter("bzparam");// 在ajax当中已经把js对象变成了json字符串
		String dataId = request.getParameter("dataId");// 主数据id
		//
		String departmentCode = request.getParameter("deptcode");
		String userAssign = request.getParameter("userAssign");
		
		Map<String, String> mapBizz = new HashMap<String, String>();
		if (buzzParameters != null && !buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = (String) jo.get(key);
				try {
					value = new String(value.getBytes("iso8859-1"), "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				mapBizz.put(key, value);
			}
		}
		// Map mapBizz = new HashMap();
		// mapBizz.put("applyTypeName", "申请类型");
		String creator = UserContext.getUserInfo().getUserName();
		long instanceId = ps.createProcess(definitionName, creator, mapBizz);
		json = "{success:true,id:'" + instanceId + "'}";
		// }
		return json;
	}

	/**
	 * 审批任务（执行任务节点）
	 * 
	 * @Methods Name audit
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String audit(HttpServletRequest request) {
		
		String json = "";
		String formName = "";
		String nextNodeDesc = "";
        String nextNodeName = "";
        String nextnodeType = "";
        String processName = "";
        Long nextNodeId = null;
		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("id");
		String done = request.getParameter("done");
		Long taskId = null;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		// 找到节点信息和相应小form信息
		TaskInfo ti = tm.getTaskInfo(taskId);
		if(ti!=null&&!"".equals(ti)){
			formName = FormHelper.findForm(ti.getDefinitionName(),ti.getNodeDisc()).trim();
		}else{
			json = "{success:true,Exception:'" + "任务实例不存在，请您让管理员检查流程" + "'}";	
			return json;
		}
		if (done == null) {// 执行前显示
			json = "{success:true,formName:'" + formName + "'}";
		} else {// 执行审批任务
			Enumeration para = request.getParameterNames();//页面传递参数
			// 如果上下文中有对应名称的变量，则把页面数据传进去
			Map mapVar = vm.listVariablesByTaskId(taskId);//如果原来参数当中没有值，然后到了流程某步时需要给此参数赋值了，相当vm.listVariablesByTaskId(taskId)和vm.listBizVariablesByTaskId(long taskId);
			for (; para.hasMoreElements();) {
				String varName = (String) para.nextElement();
				if (mapVar.containsKey(varName)) {
					String v = request.getParameter(varName);	
					if (request.getMethod().equalsIgnoreCase("get")) {
						// 字符集转换
						try {// extjs转换方式
							v = new String(v.getBytes("iso8859-1"), "utf-8");
							//add start by gaowen 转换空格 否则后面解析错误 2009-10-13
							v=v.replace("&nbsp;", "");
							//add end
						} catch (UnsupportedEncodingException e) {
							json = "{success:true,Exception:'" + "流程审批时，传递的业务参数在转码时发生异常" + "'}";
							return json;
						}
					}
					if("dynUserList".equals(varName)){
						if(v!=null&&!"".equals(v)){
							UserInfo userInfo = (UserInfo)service.find(UserInfo.class, v);
							v = userInfo.getUserName();
						}
					}
					vm.setVariableByTaskId(taskId, varName, v);
				}
			}
			Map bizParam = vm.listBizVariablesByTaskId(taskId);//得到相应的业务参
			Map map = tm.getNextNodeInfo(taskId);
			ProcessInstance pi = null;
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			try{
				TaskInstance taskInstance = null;
				ContextInstance ci = null;
				try{
					taskInstance = jbpmContext.loadTaskInstance(taskId);
					ci = taskInstance.getContextInstance();
					pi = ci.getProcessInstance();
				}catch(Exception e){
					json = "{success:true,Exception:'" + "流程审批时，流程加载任务实例是发生异常，请您让管理员检查" + "'}";
					return json;
				}
				if(!taskInstance.isOpen()) {
					throw new RuntimeException("当前流程您已经在系统或邮件中审批过了，请您仔细核查！如果您是邮件审批请登录系统，如果您在系统请您点击框上面的刷新！");
				}
				Long nodeId = taskInstance.getTask().getTaskNode().getId();
				String taskNodeName = taskInstance.getTask().getTaskNode().getName();
	            String desc=taskInstance.getTask().getTaskNode().getDescription();
	            Token token = taskInstance.getToken();
				// 根据任务id得到下一个节点信息，判断是否有审批人；
				
				if(map.size()!=0){
					nextNodeDesc = (String) map.get("nodeDesc");
					nextNodeName = (String) map.get("nodeName");
					nextnodeType = (String) map.get("nodeType");//表明节点是Node还是TaskNode还是
					nextNodeId = (Long) map.get("nodeId");
				}else{
					json = "{success:true,Exception:'" + "流程审批时，通过任务实例或得流程下个节点信息时发生异常，请您让管理员检查" + "'}";
					return json;
				}
				Long virtualDefinintionId = (Long) taskInstance.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
				String creator = (String) taskInstance.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
				/** **********得到results***************** */
				Map variables = taskInstance.getContextInstance().getVariables();
				// 先清除TASKINFO,否则会有死循环问题。
				if (variables.containsKey(WorkflowConstants.TASKINFO_KEY)) {
					variables.remove(WorkflowConstants.TASKINFO_KEY);
				}
				JSONObject jo = JSONObject.fromObject(variables);
				String result = (String) jo.get(WorkflowConstants.RESULT_FLAG);
				/** *************************** */
				VirtualDefinitionInfo virtualDefinitionInfo = (VirtualDefinitionInfo) service.find(VirtualDefinitionInfo.class, String.valueOf(virtualDefinintionId));
				if(virtualDefinitionInfo!=null&&!"".equals(virtualDefinitionInfo)){
					processName = virtualDefinitionInfo.getVirtualDefinitionDesc();
				}else{
					json = "{success:true,Exception:'" + "流程审批时，没有找到后台配置的流程对象实体，请您让管理员检查" + "'}";
					return json;
				}
				// 这是后台配的角色,还需要判断那个上下文中的角色
				ConfigUnitRole nextUnitRole = si.findUnitRoleByNodeTypeAndProDesc(processName, nextNodeDesc, String	.valueOf(virtualDefinintionId),nextNodeId);
				//得到这个节点后台配置的角色类型
				ConfigUnitRole nowUnitRole = si.findUnitRoleByNodeTypeAndProDesc(processName, desc, String.valueOf(virtualDefinintionId),nodeId);
				Integer nodeRoleType= 1;
				if(nowUnitRole!=null&&!"".equals(nowUnitRole)){
					nodeRoleType= nowUnitRole.getNodeType();//这个就是节点类型(是一人审批还是多人审批)
				}
				
				//add by guangsa for dynCounterSign in 20090725 begin
				/*************判断下一个节点是否有会签人员指派***********************/
				//dynCounterSign的格式:nodeDesc:审批类型+userName,userName;审批类型+userName,userName$审批类型+userName,userName;审批类型+userName,userName
				String dynCounterSign = (String)bizParam.get("dynCounterSign");
				Boolean dynFlag = false;
				if (dynCounterSign != null && !"".equals(dynCounterSign)) {
					String[] dynMegs = dynCounterSign.split("\\$");
					//add by guangsa for 如果当前节点是动态会签节点，此时审批人如果有一个人拒绝，则会沿着拒绝的线路流转 begin
					if("N".equals(result)){
						for (String user : dynMegs) {
							String counterNodeDesc = user.substring(0, user.indexOf(":")).trim();
							if (desc.equals(counterNodeDesc)) {
								dynFlag = true;
								List<TaskInstance> allTaskInstance = jbpmContext.getTaskMgmtSession().findTaskInstancesByToken(token.getId());//(List)taskInstance.getTaskMgmtInstance().getTaskInstances();//
								for(TaskInstance taskIns : allTaskInstance){
									if(taskId!=taskIns.getId()&&!taskId.equals(taskIns.getId())){
										taskIns.setSignalling(false);
										taskIns.cancel();
									}
								}
							}
						}
					}
					//add by guangsa for 如果当前节点是动态会签节点，此时审批人如果有一个人拒绝，则会沿着拒绝的线路流转 end
					else{
						for (String user : dynMegs) {
							String counterNodeDesc = user.substring(0, user.indexOf(":")).trim();
							if (nextNodeDesc.equals(counterNodeDesc)) {
								dynFlag = true;
							}
						}
					}
				}
				//add by guangsa for dynCounterSign in 20090725 end
				/*************判断是否有动态指派的人员***********************/
				String userList = (String) bizParam.get("userList");
				Boolean flag = false;// 判断动态指派中当前节点是否有人
				if (userList != null && !"".equals(userList)) {
					String[] ids = userList.split("\\$");
					for (String user : ids) {
						String nodeDesc = user.substring(0, user.indexOf(":"));
						if (nextNodeDesc.equals(nodeDesc)) {
							flag = true;
						}
					}
				}
				/*************判断是否有动态指派+后台的人员***********************/
				String addDynAssignPer = (String)bizParam.get("addDynAssignPer");
				Boolean addFlag = false;// 判断动态指派中当前节点是否有人
				if (addDynAssignPer != null && !"".equals(addDynAssignPer)) {
					String[] ids = addDynAssignPer.split("\\$");
					for (String user : ids) {
						String nodeDesc = user.substring(0, user.indexOf(":"));
						if (nextNodeDesc.equals(nodeDesc)) {
							addFlag = true;
						}
					}
				}
				/*************得到后台配置的人员（后台会签功能）***********************/
				//从bizParam中singerUser减去当前登陆人
				String remainSingerUsers="";
				String singerUser = (String) bizParam.get("signerUser");
				remainSingerUsers=singerUser;
				String nowUserName=UserContext.getUserInfo().getUserName();//得到当前登陆人的英文名
				/** ****************************** */
				if (nextnodeType.indexOf("EndState") == 0) {// 下一个节点是结束节点(nodeType表示下一个节点)
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if(nextnodeType.indexOf("MailNode") ==0){
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if(nextnodeType.indexOf("Node") == 0){//下一个节点是Node节点时
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				} else if(nextnodeType.indexOf("ProcessState") == 0){//下一个节点是ProcessState节点时
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if(nextnodeType.indexOf("Fork") == 0){//下一个节点是Fork节点时
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if(nextnodeType.indexOf("Join") == 0){//下一个节点是Join节点时
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if(nextnodeType.indexOf("Decision") == 0){//下一个节点是Decision节点时
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if (flag == true || nextUnitRole != null || addFlag==true ||dynFlag==true) {//下一个节点有配置角色或有动态指派人时
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						json = "{success:true,Exception:'" + e.getMessage() + "'}";				
						return json;
						
					}
					json = "{success:true}";
				} else if (nextUnitRole == null && flag == false && addFlag==false) {// 下一个节点没有配置角色时且没有动态指派人时，就弹出友好提示界面/* || unitRole.equals("") */
					json = "{success:true,id:'role',nextNodeName:'" + nextNodeName
							+ "'}";
				}
				try{
//					JbpmContext jc = JbpmContextFactory.getJbpmContext();
//					 bizParam.put("dynCounterSign", bizParam.get("dynCounterSign"));
//				     bizParam.put("userList", bizParam.get("userList"));
//					jc.loadProcessInstance(pi.getId()).getContextInstance().setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
				}finally{
					//JbpmContextFactory.closeJbpmContext();
				}
			}catch(Exception e ){
				jbpmContext.setRollbackOnly();
				json = "{success:true,Exception:'" + e.getMessage() + "'}";				
				return json;
			}finally{
				JbpmContextFactory.closeJbpmContext();
			}
		}
		
		return json;
	}

	/**
	 * 获得人员的任务列表
	 * 
	 * @Methods Name tasks
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	private String tasks(HttpServletRequest request) {

		// 需要的参数,列出当前用户的用户名
		String actor = request.getParameter("actorId");
		String json = "";
		List<TaskInfo> list = ts.listTasks(actor);
		for (TaskInfo taskInfo : list) {
			// PageModel pageUrl =
			// pagemodels.findPageModelByNode(taskInfo.getNodeDisc());
			// String pagePath = pageUrl==null?"":pageUrl.getPagePath();
			String str = "";
			str += "pageUrl:'"+taskInfo.getBizParams().get("goStartState")+"',";
			str += "defname:'" + taskInfo.getDefinitionName() + "',";
			str += "defdesc:'" + taskInfo.getDefinitionDesc() + "',";
			str += "nodeName:'" + taskInfo.getNodeName() + "',";
			str += "taskId:'" + taskInfo.getId() + "',";
			str += "taskName:'" + taskInfo.getName() + "',";
			// 用实际名称代替用户系统名
			String realName = getUserRealNameByName(taskInfo.getActorId());
			str += "actorId:'" + realName + "',";
			str += "startDate:'" + toBlank(taskInfo.getStart()) + "',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo
					.getProcessId());
			JSONObject jo = JSONObject.fromObject(bizParams);
			String strBizParams = jo.toString();
			strBizParams = strBizParams == null || strBizParams.equals("null") ? "''"
					: strBizParams;
			if (strBizParams.startsWith("{")) {
				strBizParams = strBizParams.substring(1);
			}
			if (strBizParams.endsWith("}")) {
				strBizParams = strBizParams.substring(0,
						strBizParams.length() - 1);
			}

			str += strBizParams + ",";
			str += "bizParam:{" + strBizParams + "},";
			str += "comments:'"
					+ toBlank(taskInfo.getComments().getValue("comment")) + "'";
			str = "{" + str + "},";
			json += str;
		}
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		return "{success: true, rowCount:'" + list.size() + "',data:[" + json
				+ "]}";
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void init() throws ServletException {
		// Put your code here
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	private String toBlank(Object o) {
		return o == null ? "" : (String) o;
	}

	/**
	 * 要考虑到多人的情况
	 * 
	 * @Methods Name getUserRealNameByName
	 * @Create In Nov 19, 2008 By yang
	 * @param userName
	 * @return
	 * @ReturnType String
	 */
	private String getUserRealNameByName(String userName) {
		String userRealNames = "";
		String[] userNames = null;
		if (userName.indexOf('|') > 0) {
			userNames = userName.split("\\|");
		} else {
			userNames = new String[] { userName };
		}
		for (String userNameItem : userNames) {
			List<UserInfo> users = service.find(UserInfo.class, "userName",
					userNameItem);
			if (users != null && !users.isEmpty()) {
				userRealNames += users.get(0).getRealName() + ",";
			} else {
				userRealNames += userNameItem + ",";
			}
		}
		if (userRealNames.endsWith(",")) {
			userRealNames = userRealNames.substring(0,
					userRealNames.length() - 1);
		}
		if (userRealNames.indexOf(",") > 0) {// 多人情况下加上中括号
			userRealNames = "[" + userRealNames + "]";
		}
		return userRealNames;
	}
	/**
	 * 回退流程按钮接口，目的就是让流程回退
	 * @param request
	 * @return
	 * @throws Exception 
	 */
	private String getWorkFlowGoBack(HttpServletRequest request) throws Exception{
		
		Long fromNodeId = null;
		String fromNodeName = "";
		String fromParamId = "";
		String fromNodeType = "";
		String json = "";
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try{
			String taskId = request.getParameter("taskId");
			TaskInstance taskInstance = jbpmContext.getTaskInstance(Long.valueOf(taskId));
			ContextInstance ci = taskInstance.getContextInstance();
			
			//设计思想就是首先把list中最后一个对象（即当前节点对象）去掉，取出前一个（这是上一个节点的信息），当取出之后再把前一个也去掉
			String vName = (String)ci.getVariable("VIRTUALDEFINITIONINFO_NAME");
			Long vProcessId = (Long)ci.getVariable("VIRTUALDEFINITIONINFO_ID");
			Map bizParam = (Map)ci.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			String creator= (String)ci.getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
			String dataId = (String)bizParam.get("dataId");		
			//需要为service准备参数，包括流程实例ID，节点ID，任务名称
			ProcessInstance processInstance = taskInstance.getProcessInstance();
			Long tokenId = taskInstance.getToken().getId();
			Long processId = processInstance.getId();//当前流程实例ID
			//得到相应的回退字符串
			List allNodeMessage = (List)ci.getVariable("goBack");//List中每一个对象就是一个String，格式为paramId+nodeName；
			//从A-B-C-D,此时我们要从D回到C，先把当前节点的信息删除掉，然后获得上一个节点的信息
			allNodeMessage.remove(allNodeMessage.size()-1);
			//modify by guangsa for workflowGoBackToTaskNode in 20090817 begin
			int goBackLength = allNodeMessage.size()-1;//之前去掉一个所以要减一
			Node fromNode = null;
			for(int i=0;i<goBackLength;i++){
				String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
				String[] mutipleMessage = fromNodeMessage.split("\\+");
				fromParamId = mutipleMessage[0];//上个节点参数Id
				fromNodeName = mutipleMessage[1];//节点名称为中文，这里无法用到nodeDesc（api限制）
				allNodeMessage.remove(allNodeMessage.size()-1);
				fromNode = processInstance.getProcessDefinition().getNode(fromNodeName);//这里不用考虑流程版本导致的节点名称重复问题，因为用的是当前的流程实例；
				//开始找上一个节点的NODEID和NODENAME
				if(fromNode!=null&&!"".equals(fromNode)){
					fromNodeId = fromNode.getId();
					fromNodeName = fromNode.getName();
					fromNodeType = fromNode.toString();
					if(fromNodeType.indexOf("Node")==0){
						//如果上一个节点为node类型的话，首先需要把上一个节点去掉。再找上一个节点
						allNodeMessage.remove(allNodeMessage.size()-1);
						i++;
					}else{
						break;//如果当前节点不是node类型的话，则直接调出本次循环
					}
				}
			}
			//modify by guangsa for workflowGoBackToTaskNode in 20090817 end
			//开始节点的字符串形式
			String goStartState = String.valueOf(fromNodeId)+","+vProcessId+","+processId;
			//需要把记录此任务接点的信息删除掉
			WorkflowRecordTaskInfo workflowRecordTaskInfo = cs.findWorkflowRecordTaskInfo(dataId, vName);
			if(workflowRecordTaskInfo!=null&&!"".equals(workflowRecordTaskInfo)){
				service.remove(workflowRecordTaskInfo);//这样就避免了流程回退了，但是同一个流程中任务信息不唯一的错误
			}
			//流程回退的思路就是“我把Signalling字段设置为false，让流程end()的时候不转向但是结束任务”
			if(fromNodeType.indexOf("StartState")==0){
				bizParam.put("goStartState", goStartState);//设置一个特殊的变量
				ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
				//既然回退到开始节点的话就相当于打回了，需要用户重新提交或者是取消当前流程
				String nodeName = taskInstance.getToken().getNode().getName();
				Task task = taskInstance.getTask().getTaskNode().getTask(nodeName);
				task.setName("申请流程打回节点(开始节点)");
				taskInstance.setActorId(creator);
				taskInstance.setTask(task);
				jbpmContext.save(taskInstance);
				Token oldeToken = jbpmContext.getToken(Long.valueOf(tokenId));
				if(fromNode==null){
					throw new RuntimeException("流程回退时上一个节点的信息为空");
				}
				oldeToken.setNode(fromNode);
			}else{
				//会签节点情况为考虑到
				taskInstance.setSignalling(false);//signalling的意思就是不让节点转向
				taskInstance.end();
				Token oldeToken = jbpmContext.getToken(Long.valueOf(tokenId));
				if(fromNode==null){
					throw new RuntimeException("流程回退时上一个节点的信息为空");
				}
				oldeToken.setNode(fromNode);
				ExecutionContext ec = new ExecutionContext(oldeToken);
				fromNode.enter(ec);
			}
		}catch(Exception e){
			jbpmContext.setRollbackOnly();
			e.printStackTrace();
			json = "{success:false}";
			return json;
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		json = "{success:true}";
		return json;
	}
	/**
	 * 实现跳步的工作流回退
	 * @param request
	 * @return
	 */
	private String getWorkFlowSkipGoBack(HttpServletRequest request){
		//前期参数准备
		boolean mark = false;
		String fromNodeName = "";
		String fromParamId = "";
		String fromNodeType = "";
		Long fromNodeId = null;
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		String taskId = request.getParameter("taskId");
		fromNodeName = request.getParameter("fromNodeName");
		fromNodeName = HttpUtil.ConverUnicode(fromNodeName);
		TaskInstance ti = jbpmContext.getTaskInstance(Long.valueOf(taskId));
		ContextInstance ci = ti.getContextInstance();
		String vName = (String)ti.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_NAME");
		Long vProcessId = (Long)ti.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
		Map bizParam = (Map)ti.getContextInstance().getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
		String creator= (String)ti.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
		String dataId = (String)bizParam.get("dataId");
		String nowNodeName = ti.getToken().getNode().getName();//当前节点名称
		Long tokenId = ti.getToken().getId();
		Long processId = ti.getContextInstance().getProcessInstance().getId();
		Node fromNode = ti.getContextInstance().getProcessInstance().getProcessDefinition().getNode(fromNodeName);
		//开始找上一个节点的NODEID和NODENAME
		if(fromNode!=null&&!"".equals(fromNode)){
			fromNodeId = fromNode.getId();
			fromNodeName = fromNode.getName();
			fromNodeType = fromNode.toString();
		}
		String goStartState = String.valueOf(fromNodeId)+","+vProcessId+","+processId;
		List<String> allNodeMessage = (List<String>)ti.getContextInstance().getVariable("goBack");//List中每一个对象就是一个String，格式为paramId+nodeName；
		//需要把记录此任务接点的信息删除掉
		WorkflowRecordTaskInfo workflowRecordTaskInfo = cs.findWorkflowRecordTaskInfo(dataId, vName);
		if(workflowRecordTaskInfo!=null&&!"".equals(workflowRecordTaskInfo)){
			service.remove(workflowRecordTaskInfo);//这样就避免了流程回退了，但是同一个流程中任务信息不唯一的错误
		}
		//从A-B-C-D,此时我们要从D回到B，我们需要把goBack中参数消除到B，这样当再次进入B节点的时候，goBack记录唯一的一个B记录
		//格式：id+nodeName，id+nodeName
		List<String> remainNodeMessage = new ArrayList<String>();
		for(String allMessage : allNodeMessage){
			String[] signleNodeMessage = allMessage.split("\\+");
			int i = 0;
			//System.out.println(signleNodeMessage[i]);
			try{
				while(i<signleNodeMessage.length){
					if(signleNodeMessage[i].equals(fromNodeName)){
						mark = true;
					}
					i++;
				}
			}catch(Exception e){
				new RuntimeException("在流程跳跃回退过程当中goBack参数保存不完全");
			}
			if(!mark){
				remainNodeMessage.add(allMessage);
			}
		}
		ti.getContextInstance().setVariable("goBack", remainNodeMessage);
		
		//最后开始流程回退的工作，因为当回退的时候需要参数的准备
		try{
			//流程回退的思路就是“我把Signalling字段设置为false，让流程end()的时候不转向但是结束任务”
			
			if(fromNodeType.indexOf("StartState")==0){
				bizParam.put("goStartState", goStartState);//设置一个特殊的变量
				ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
				//既然回退到开始节点的话就相当于打回了，需要用户重新提交或者是取消当前流程
				String nodeName = ti.getToken().getNode().getName();
				Task task = ti.getTask().getTaskNode().getTask(nodeName);
				task.setName("申请流程打回节点(开始节点)");
				ti.setActorId(creator);
				ti.setTask(task);
				jbpmContext.save(ti);
				Token oldeToken = jbpmContext.getToken(Long.valueOf(tokenId));
				oldeToken.setNode(fromNode);
			}else{
				ti.setSignalling(false);
				ti.end();
				Token oldeToken = jbpmContext.getToken(tokenId);
				oldeToken.setNode(fromNode);
				ExecutionContext ec = new ExecutionContext(oldeToken);
				fromNode.enter(ec);
			}
		}catch(Exception e){
			new RuntimeException("在流程跳跃回退过程中发生异常");
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		return null;
		
	}
	/**
	 * 从页面中获得数据放入上下文的map中，继续执行任务
	 * 
	 * @Methods Name getDataFromPage
	 * @Create In Mar 24, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String getDataFromPage(HttpServletRequest request) {
//		String json = "";
//		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
//		String strTaskId = request.getParameter("taskId");
//		long taskId = 0;
//		if (strTaskId != null && !strTaskId.equals("")) {
//			taskId = Long.parseLong(strTaskId);
//		}
//
//		TaskInfo ti = tm.getTaskInfo(taskId);
//		String nodeName = ti.getNodeName();
//
//		String userList = request.getParameter("users");
//		// 对userList信息整理一下
//
//		if (userList != null && !"".equals(userList)) {
//			userList = userListUtil(userList);
//			vm.setVariableToBizParam(taskId, "userList", userList);
//		}
		boolean flag = false;
		String json = "";
		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		Map bizParam = (Map)vm.getVariableByTaskId(taskId, WorkflowConstants.BUSINESS_PARAM_KEY);
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try{
			String nodeName = ti.getNodeName();
			String userList = request.getParameter("users");
			//add by guangsa for avoidRepeateAddParam in 20090807 begin
			String realUserList = "";
			String oldUserList = (String)bizParam.get("userList");
			String[] oldUsers = null;
			if(oldUserList!=null&&!"".equals(oldUserList)){
				oldUsers = oldUserList.split("\\$");
			}
			//add by guangsa for avoidRepeateAddParam in 20090807 end
			
			if (userList != null && !"".equals(userList)) {
				String[] newUsers = userList.split("\\$");
				//add by guangsa for avoidRepeateAddParam in 20090807 begin
				if(oldUsers!=null&&!"".equals(oldUsers)){//如果原来有的话，有重复的去重
					for(int i=0;i<oldUsers.length;i++){
						String oldDesc = oldUsers[i].substring(0,oldUsers[i].indexOf(":"));
						//modify by guangsa in 20090810 begin
						for(int j=0;j<newUsers.length;j++){
							String newDesc = newUsers[j].substring(0,newUsers[j].indexOf(":"));
							if(oldDesc.equals(newDesc)){//如果不是第一次保存的话,覆盖原来的部分
								userList = userListUtil(newUsers[j]);
								oldUsers[i] = userList;
								flag = true;
								break;
							}
						}
						//modify by guangsa in 20090810 end
					}
					if(!flag){//如果这是第一次进行保存的话,需要累加字符串并重新拼写字符串
						for(int i=0;i<oldUsers.length;i++){
							realUserList+=oldUsers[i];
							realUserList+="$";
						}
						userList = userListUtil(userList);
						realUserList+=userList;
					}else{//如果不是第一次,只需要把原来的字符串变成想要的格式即可
						for(int i=0;i<oldUsers.length;i++){
							realUserList+=oldUsers[i];
							realUserList+="$";
						}
						if(realUserList.endsWith("$")){
							realUserList = realUserList.substring(0,realUserList.length()-1);
						}
					}
				}else{//如果原来没有存入的时候，把userList直接放入
					userList = userListUtil(userList);
					realUserList = userList;
				}
				bizParam.put("userList", realUserList);
				jbpmContext.getTaskInstance(taskId).getContextInstance().setVariable(
						WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
				//add by guangsa for avoidRepeateAddParam in 20090807 end
				
				// 在动态指派页面传过来的数据字符串中有双引号
	//			if (userList.contains("\"")) {
	//				userList = userList.replaceAll("\"", "");
	//			}
				// 对userList信息整理一下
				//userList = userListUtil(userList);
				//vm.setVariableToBizParam(taskId, "userList", userList);
			}
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		Enumeration para = request.getParameterNames();
		// 如果上下文中业务参数Map有对应名称的变量，则把数据传进去
		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		for (; para.hasMoreElements();) {
			String varName = (String) para.nextElement();
			if (mapVar.containsKey(varName)) {
				String v = request.getParameter(varName);
				if (request.getMethod().equalsIgnoreCase("get")) {
					// 字符集转换
					try {// extjs转换方式
						v = new String(v.getBytes("iso8859-1"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				vm.setVariableToBizParam(taskId, varName, v);
			}
		}
		// 继续执行任务
		try{
			tm.execute(taskId);
		}catch(Exception e){
			//add by guangsa for 事件邮件重复审批 in 20090908 begin
			String meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"',formName:'',nodeName:'" + ti.getNodeDisc() + "'}";// ti.getNodeName()
			return json;
			//add by guangsa for 事件邮件重复审批 in 20090908 end
			//throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请找管理员核实核实");
		}		
		json = "{success:true,formName:'',nodeName:'" + ti.getNodeDisc() + "'}";// ti.getNodeName()

		return json;
	}

	/**
	 * 从页面中获得数据放入上下文的map中（不继续执行任务）
	 * 
	 * @Methods Name getData
	 * @Create In Mar 24, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String getData(HttpServletRequest request) {
		boolean flag = false;
		String json = "";
		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		Map bizParam = (Map)vm.getVariableByTaskId(taskId, WorkflowConstants.BUSINESS_PARAM_KEY);
		JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
		try{
			String nodeName = ti.getNodeName();
			String userList = request.getParameter("users");
			//add by guangsa for avoidRepeateAddParam in 20090807 begin
			String realUserList = "";
			String oldUserList = (String)bizParam.get("userList");
			String[] oldUsers = null;
			if(oldUserList!=null&&!"".equals(oldUserList)){
				oldUsers = oldUserList.split("\\$");
			}
			//add by guangsa for avoidRepeateAddParam in 20090807 end
			String repeateUser = "";
			if (userList != null && !"".equals(userList)) {
				String[] newUsers = userList.split("\\$");
				//add by guangsa for avoidRepeateAddParam in 20090807 begin
				if(oldUsers!=null&&!"".equals(oldUsers)){//如果原来有的话，有重复的去重
					for(int i=0;i<oldUsers.length;i++){
						String oldDesc = oldUsers[i].substring(0,oldUsers[i].indexOf(":"));
						//modify by guangsa in 20090810 begin
						for(int j=0;j<newUsers.length;j++){
							if(!"".equals(newUsers[j])){
								String newDesc = newUsers[j].substring(0,newUsers[j].indexOf(":"));
								if(oldDesc.equals(newDesc)){//如果不是第一次保存的话,覆盖原来的部分
									repeateUser = userListUtil(newUsers[j]);
									oldUsers[i] = repeateUser;
									flag = true;
									//modify by guangsa for 替换和新增动态指派 in 20100706 begin 
									newUsers[j]="";
									//modify by guangsa for 替换和新增动态指派 in 20100706 end
									break;
								}
							}
						}
						//modify by guangsa in 20090810 end
					}
					if(!flag){//如果这是第一次进行保存的话,需要累加字符串并重新拼写字符串
						for(int i=0;i<oldUsers.length;i++){
							realUserList+=oldUsers[i];
							realUserList+="$";
						}
						userList = userListUtil(userList);
						realUserList+=userList;
					}else{//如果不是第一次,只需要把原来的字符串变成想要的格式即可
						for(int i=0;i<oldUsers.length;i++){
							realUserList+=oldUsers[i];
							realUserList+="$";
						}
						
						for(int i = 0;i<newUsers.length;i++){
							if(!"".equals(newUsers[i])&&newUsers[i]!=null){
								repeateUser = userListUtil(newUsers[i]);
								realUserList+=repeateUser;
								realUserList+="$";
							}
						}
						if(realUserList.endsWith("$")){
							realUserList = realUserList.substring(0,realUserList.length()-1);
						}
					}
				}else{//如果原来没有存入的时候，把userList直接放入
					userList = userListUtil(userList);
					realUserList = userList;
				}
				bizParam.put("userList", realUserList);
				jbpmContext.getTaskInstance(taskId).getContextInstance().setVariable(
						WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
				//add by guangsa for avoidRepeateAddParam in 20090807 end
				
				// 在动态指派页面传过来的数据字符串中有双引号
	//			if (userList.contains("\"")) {
	//				userList = userList.replaceAll("\"", "");
	//			}
				// 对userList信息整理一下
				//userList = userListUtil(userList);
				//vm.setVariableToBizParam(taskId, "userList", userList);
			}
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		Enumeration para = request.getParameterNames();
		// 如果上下文中业务参数Map有对应名称的变量，则把数据传进去
		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		for (; para.hasMoreElements();) {
			String varName = (String) para.nextElement();
			if (mapVar.containsKey(varName)) {
				String v = request.getParameter(varName);
				if (request.getMethod().equalsIgnoreCase("get")) {
					// 字符集转换
					try {// extjs转换方式
						v = new String(v.getBytes("iso8859-1"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				vm.setVariableToBizParam(taskId, varName, v);
			}
		}
		json = "{success:true,formName:'',nodeName:'" + ti.getNodeDisc() + "'}";// ti.getNodeName()
		return json;
	}

	/**
	 * 继续给当前节点指定人
	 * 
	 * @Methods Name reAssignToNode
	 * @Create In Mar 24, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String reAssignToNode(HttpServletRequest request) {
		String json = "";
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		String userList = "";
		String actorIds = request.getParameter("users");
		if (actorIds != null && !"".equals(actorIds)) {
			actorIds = actorIds.substring(actorIds.indexOf(":") + 1);
			String[] users = actorIds.split(",");

			for (String id : users) {
				UserInfo user = (UserInfo) service.find(UserInfo.class, id);
				userList += user.getUserName();
				userList += "|";
			}
			if (userList.endsWith("|")) {
				userList = userList.substring(0, userList.length() - 1);
			}
		}
		tm.reAssign(taskId, userList);

		json = "{success:true}";// ti.getNodeName()

		return json;
	}
	/**
	 * 删除加签人记录
	 * @Methods Name deleteMarkUsersInfo
	 * @Create In Apr 23, 2009 By guangsa
	 * @param request
	 * @return String
	 */
	private String deleteMarkUsersInfo(HttpServletRequest request){
		String json = "";
		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();

		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String oldMarkUsers=(String)mapVar.get("addMarkUsers");//原来的加签人
		if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
			oldMarkUsers=addMarkUsersUtil(oldMarkUsers);
		}
		String nowMarkUsers = "";
		String nowAddMarkUsersId = request.getParameter("deleteMarkUsers");//当前新保存的加签人
		if (nowAddMarkUsersId != null && !"".equals(nowAddMarkUsersId)) {
			if (nowAddMarkUsersId.contains("\"")) {
				nowAddMarkUsersId = nowAddMarkUsersId.replaceAll("\"", "");
			}
			if(nowAddMarkUsersId!=null&&!"".equals(nowAddMarkUsersId)){
				String nowMarks=addMarkUsersUtil(nowAddMarkUsersId);//把用户ID变成UserName
				String taskid=nowAddMarkUsersId.substring(0,nowAddMarkUsersId.indexOf(":"));
				if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
					if(oldMarkUsers.contains(nowMarks)){
						String[] oldMark = oldMarkUsers.split(",");
						for(int i=0;i<oldMark.length;i++){
							if(!nowMarks.equals(oldMark[i])){
								nowMarkUsers+=oldMark[i];
								nowMarkUsers+=",";
							}
						}
						if(nowMarkUsers.endsWith(",")){
							nowMarkUsers = nowMarkUsers.substring(0, nowMarkUsers.length()-1);
						}
					}
				}
				nowMarkUsers=taskid.concat(":").concat(nowMarkUsers);
			}
			vm.setVariableToBizParam(taskId, "addMarkUsers", nowMarkUsers);
		} else {
			vm.setVariableToBizParam(taskId, "addMarkUsers", "");
		}
		json = "{success:true,formName:'',nodeName:'" + ti.getNodeDisc() + "'}";// ti.getNodeName()

		return json;
	}

	/**
	 * 把加签人的信息放入流程实例上下文的业务参数中
	 * 
	 * @Methods Name addMarkUsersInfo
	 * @Create In Mar 30, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String addMarkUsersInfo(HttpServletRequest request) {
		String json = "";
		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();

		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String oldMarkUsers=(String)mapVar.get("addMarkUsers");//原来的加签人
		if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
			oldMarkUsers=addMarkUsersUtil(oldMarkUsers);
		}
		String nowAddMarkUsersId = request.getParameter("addMarkUsers");//当前新保存的加签人
		//addMarkUsers的格式：taskId:userId+顺序+类型,userId+顺序+类型
		if (nowAddMarkUsersId != null && !"".equals(nowAddMarkUsersId)) {
//			if (nowAddMarkUsersId.contains("\"")) {
//				nowAddMarkUsersId = nowAddMarkUsersId.replaceAll("\"", "");
//			}
			//String nowMarks=addMarkUsersUtil(nowAddMarkUsersId);//把当前加签人进行排序
			String nowMarks = nowAddMarkUsersId.substring(nowAddMarkUsersId.indexOf(":")+1);
			String taskid=nowAddMarkUsersId.substring(0,nowAddMarkUsersId.indexOf(":"));
			String[] nowUserNames = nowMarks.split(",");
			if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
				for(int i=0;i<nowUserNames.length;i++){
						if(!oldMarkUsers.contains(nowUserNames[i])){
							oldMarkUsers = oldMarkUsers.concat(",").concat(nowUserNames[i]);
					}
				}
			}else{
				oldMarkUsers=nowMarks;
			}
			String nowAddMarkUser = addMarkUsersUtil(oldMarkUsers);
			nowAddMarkUsersId=taskid.concat(":").concat(nowAddMarkUser);
			
			vm.setVariableToBizParam(taskId, "addMarkUsers", nowAddMarkUsersId);
		} else {
			vm.setVariableToBizParam(taskId, "addMarkUsers", "");
		}
		json = "{success:true,formName:'',nodeName:'" + ti.getNodeDisc() + "'}";// ti.getNodeName()

		return json;
	}

	
	private String deleteMarkUsersInfoToNextNode(HttpServletRequest request){
		String json = "";
		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();

		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String oldMarkUsers=(String)mapVar.get("addMarkUsersToNextNode");//原来的加签人
		if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
			oldMarkUsers=addMarkUsersUtil(oldMarkUsers);
		}
		String nowMarkUsers = "";
		String nowAddMarkUsersId = request.getParameter("deleteMarkUsers");//当前新保存的加签人
		if (nowAddMarkUsersId != null && !"".equals(nowAddMarkUsersId)) {
			if (nowAddMarkUsersId.contains("\"")) {
				nowAddMarkUsersId = nowAddMarkUsersId.replaceAll("\"", "");
			}
			if(nowAddMarkUsersId!=null&&!"".equals(nowAddMarkUsersId)){
				String nowMarks=addMarkUsersUtil(nowAddMarkUsersId);//把用户ID变成UserName
				String taskid=nowAddMarkUsersId.substring(0,nowAddMarkUsersId.indexOf(":"));
				if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
					if(oldMarkUsers.contains(nowMarks)){
						String[] oldMark = oldMarkUsers.split(",");
						for(int i=0;i<oldMark.length;i++){
							if(!nowMarks.equals(oldMark[i])){
								nowMarkUsers+=oldMark[i];
								nowMarkUsers+=",";
							}
						}
						if(nowMarkUsers.endsWith(",")){
							nowMarkUsers = nowMarkUsers.substring(0, nowMarkUsers.length()-1);
						}
					}
				}
				nowMarkUsers=taskid.concat(":").concat(nowMarkUsers);
			}
			vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", nowMarkUsers);
		} else {
			vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");
		}
		json = "{success:true,formName:'',nodeName:'" + ti.getNodeDisc() + "'}";// ti.getNodeName()

		return json;
	}
	/**
	 * 把下一个节点加签人的信息放入流程实例上下文的业务参数中
	 * @Methods Name addMarkUsersInfo
	 * @Create In Mar 30, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String addMarkUsersInfoToNextNode(HttpServletRequest request) {
		String json = "";
		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();
		/*****************************************************************************************/
		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String oldMarkUsers=(String)mapVar.get("addMarkUsersToNextNode");//原来的加签人
		if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
			oldMarkUsers=addMarkUsersUtil(oldMarkUsers);
		}
		String nowAddMarkUsersId = request.getParameter("addMarkUsersToNextNode");//当前新保存的加签人
		if (nowAddMarkUsersId != null && !"".equals(nowAddMarkUsersId)) {
//			if (nowAddMarkUsersId.contains("\"")) {
//				nowAddMarkUsersId = nowAddMarkUsersId.replaceAll("\"", "");
//			}
			String nowMarks = nowAddMarkUsersId.substring(nowAddMarkUsersId.indexOf(":")+1);
			String taskid=nowAddMarkUsersId.substring(0,nowAddMarkUsersId.indexOf(":"));
			String[] nowUserNames = nowMarks.split(",");
			if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
				for(int i=0;i<nowUserNames.length;i++){
						if(!oldMarkUsers.contains(nowUserNames[i])){
							oldMarkUsers = oldMarkUsers.concat(",").concat(nowUserNames[i]);
					}
				}
			}else{
				oldMarkUsers=nowMarks;
			}
			String nowAddMarkUser = addMarkUsersUtil(oldMarkUsers);
			nowAddMarkUsersId=taskid.concat(":").concat(nowAddMarkUser);
			vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", nowAddMarkUsersId);
		} else {
			vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");
		}
		json = "{success:true,formName:'',nodeName:'" + ti.getNodeDisc() + "'}";

		return json;
		/*****************************************************************************************/
	}

	/**
	 * 得到某个taskId对应的加签人
	 * 
	 * @Methods Name getMarkUsers
	 * @Create In Apr 2, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String getMarkUsers(HttpServletRequest request) {
		String json = "{data:[";
		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("taskId");
		Long taskId = null;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();
		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String addMarkUsers = (String) mapVar.get("addMarkUsers");
		if (addMarkUsers != null && !"".equals(addMarkUsers)) {
			String taskIds = addMarkUsers.substring(0, addMarkUsers
					.indexOf(":"));

			if (taskId.equals(Long.valueOf(taskIds))) {
				addMarkUsers = addMarkUsers
						.substring(addMarkUsers.indexOf(":") + 1);
				if(addMarkUsers!=null&&!"".equals(addMarkUsers)){
				    String[] markUsers = addMarkUsers.split(",");
					for (String user : markUsers) {
						Long userId = Long.valueOf(user.substring(0, user
								.indexOf("+")));
						String sequence = user.substring(user.indexOf("+") + 1,
								user.lastIndexOf("+"));
						String typeId = user.substring(user.lastIndexOf("+") + 1);
						String typeName = "";
						if ("1".equals(typeId)) {
							typeName = "通知类型";
						} else {
							typeName = "审批类型";
						}
						UserInfo userInfo = (UserInfo) (service.find(
								UserInfo.class, String.valueOf(userId)));
						String userName = userInfo.getRealName();
						json += "{userId:" + userId + ",userName:'" + userName
								+ "',sequence:'" + sequence + "',typeName:'"
								+ typeName + "',typeId:" + typeId + "}";
						json += ",";
					}
				}
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
		}
		json += "]}";
		// json = "{success:true,formName:'',nodeName:'" + ti.getNodeDisc() +
		// "'}";// ti.getNodeName()

		return json;
	}

	/**
	 * 得到某个节点的下一个节点对应的加签人
	 * 
	 * @Methods Name getMarkUsers
	 * @Create In Apr 2, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String getMarkUsersToNextNode(HttpServletRequest request) {
		String json = "{data:[";
		// 不能使用框架提供的转码，否则不能从request里面取出特定的参数
		String strTaskId = request.getParameter("taskId");
		Long taskId = null;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();
		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String addMarkUsers = (String) mapVar.get("addMarkUsersToNextNode");
		if (addMarkUsers != null && !"".equals(addMarkUsers)) {
			addMarkUsers = addMarkUsers.substring(addMarkUsers.indexOf(":") + 1);
			if(addMarkUsers!=null&&!"".equals(addMarkUsers)){
				String[] markUsers = addMarkUsers.split(",");
				for (String user : markUsers) {
					Long userId = Long
							.valueOf(user.substring(0, user.indexOf("+")));
					String sequence = user.substring(user.indexOf("+") + 1, user
							.lastIndexOf("+"));
					String typeId = user.substring(user.lastIndexOf("+") + 1);
					String typeName = "";
					if ("1".equals(typeId)) {
						typeName = "通知类型";
					} else {
						typeName = "审批类型";
					}
					UserInfo userInfo = (UserInfo) (service.find(UserInfo.class,
							String.valueOf(userId)));
					String userName = userInfo.getRealName();
					json += "{userId:" + userId + ",userName:'" + userName
							+ "',sequence:'" + sequence + "',typeName:'" + typeName
							+ "',typeId:" + typeId + "}";
					json += ",";
				}
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
		}
		json += "]}";
		// json = "{success:true,formName:'',nodeName:'" + ti.getNodeDisc() +
		// "'}";// ti.getNodeName()

		return json;
	}

	/**
	 * 对传过来的userList信息进行整理一下(动态指派人)
	 * 
	 * @Methods Name userListUtil
	 * @Create In Mar 30, 2009 By Administrator
	 * @param value
	 * @return String
	 */
	private String userListUtil(String value) {
		String userList = "";
		if (value.contains("$")) {
			String[] nodeUser = value.split("\\$");
			for (String str : nodeUser) {
				String nodeName = str.substring(0, str.indexOf(":"));
				String str1 = str.substring(str.indexOf(":") + 1);
				String[] users = str1.split(",");
				userList += nodeName + ":";
				for (String id : users) {
					UserInfo user = (UserInfo) service.find(UserInfo.class, id);
					userList += user.getUserName();
					userList += ",";
				}
				if (userList.endsWith(",")) {
					userList = userList.substring(0, userList.length() - 1);
				}
				userList += "$";
			}
			if (userList.endsWith("$")) {
				userList = userList.substring(0, userList.length() - 1);
			}
		} else {
			String string = value;
			String nodeName = string.substring(0, string.indexOf(":"));
			string = string.substring(string.indexOf(":") + 1);
			if(string!=null&&!"".equals(string)){
				String[] users = string.split(",");
				userList += nodeName + ":";
				for (String id : users) {
					UserInfo user = (UserInfo) service.find(UserInfo.class, id);
					userList += user.getUserName();
					userList += ",";
				}
				if (userList.endsWith(",")) {
					userList = userList.substring(0, userList.length() - 1);
				}
			}
		}
		return userList;

	}

	/**
	 * 对传过来的addMarkUsers信息进行整理一下(指派加签人),即排一下顺序
	 * 加签的格式addMarkUsers: userId+顺序+类型,userId+顺序+类型,userId+顺序+类型
	 * @Methods Name addMarkUsersUtil
	 * @Create In Mar 31, 2009 By Administrator
	 * @param addMarkUsers
	 * @return String
	 */
	private String addMarkUsersUtil(String value) {
		
		String addMarkUsers = "";

		String users = value.substring(value.indexOf(":") + 1);
		if (users == null || users.equals("")) {

		} else {
			String[] usersId = users.split(",");
			List<Integer> list = new ArrayList<Integer>();
			for (String user : usersId) {
				Integer sequence = Integer.valueOf(user.substring(user
						.indexOf("+") + 1, user.lastIndexOf("+")));
				list.add(sequence);
			}
			Collections.sort(list);
			for (Integer integer : list) {
				String userId = "";
				String typeId = "";
				String s="";
				for (String user : usersId) {
					Integer sequence = Integer.valueOf(user.substring(user
							.indexOf("+") + 1, user.lastIndexOf("+")));
					if (sequence.equals(integer)) {
						userId = user.substring(0, user.indexOf("+"));
						typeId = user.substring(user.lastIndexOf("+") + 1);
						s=user.substring(user.indexOf("+") + 1, user.lastIndexOf("+"));
					}
				}
				addMarkUsers += userId;
				addMarkUsers += "+";
				addMarkUsers += s;
				addMarkUsers += "+";
				addMarkUsers += typeId;
				addMarkUsers += ",";
			}
			if (addMarkUsers.endsWith(",")) {
				addMarkUsers = addMarkUsers.substring(0,
						addMarkUsers.length() - 1);
			}
		}
		return addMarkUsers;
	}
	
	/**
	 * 审批时，有会签和加签的情况时，先考虑会签，再考虑加签
	 * @Methods Name continueAudit
	 * @Create In Apr 10, 2009 By Administrator
	 * @param bizParam
	 * @param virtualDefinintionId
	 * @param nodeId
	 * @param result
	 * @param taskId
	 * @param roleType
	 * @param remainSingerUsers void
	 */
	private void  continueAudit(Map bizParam,Long virtualDefinintionId,Long nodeId,String result,Long taskId,Integer nodeRoleType,String remainSingerUsers,String creator,String vProcessDesc,String taskNodeName)throws Exception{
		String auditFlag = (String) bizParam.get("symbol");// 区分是否是加签人进来审批的
		String dataId = (String)bizParam.get("dataId");
		String reqClass = (String)bizParam.get("reqClass");
		String goStartState = (String)bizParam.get("goStartState");
		String workflowEntity = (String)bizParam.get("workflowHistory");
		String processId = (String)bizParam.get("processId");
		String applyType = (String)bizParam.get("applyType");
		Node node=(Node) service.find(Node.class, nodeId.toString());
		String pageUrl = PropertiesUtil.getProperties("system.mail.develop.background.link", "/servlet/getPageModel?taskId=");

		if (auditFlag == null || "".equals(auditFlag)) {
			if ("N".equals(result)) {// 非加签人审批拒绝时，直接执行任务
				try{
					tm.execute(taskId);
				}catch(RuleFileException e){
					throw new RuleFileException(e.getMessage());
				}catch(Exception e){
					throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实");
				}				
				vm.setVariableToBizParam(taskId, "symbol", "");
				String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
				if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
					vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// 如果在当前节点给下一个节点设置了加签人时，就加进去
					vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//把存放下一个节点加签人的变量addMarkUsersToNextNode设置为空
				}else{
					vm.setVariableToBizParam(taskId, "addMarkUsers", "");//如果在当前节点没有给下一个节点设置加签人时， 把存放加签人的变量设置为空
				}
				
			} else if ("Y".equals(result)) {// 非加签人审批同意时，再指派给加签人
				//先看这个节点后台配置的角色类型
					if(nodeRoleType.equals(1)){//看是否有加签人
						try{
						this.addMarkUsersExecute(bizParam, taskId, virtualDefinintionId, nodeId,creator,vProcessDesc,taskNodeName);
						}catch(Exception e){
							throw new RuntimeException(e.getMessage());
						}
					}else if(nodeRoleType.equals(0)){//当前节点类型是多人审批时
						String nowUserName=UserContext.getUserInfo().getUserName();//得到当前登陆人的英文名
						String nowUserRole="";//得到当前登陆人的在singerUser中的一部分
						if(remainSingerUsers.contains("$")){
							String[] roles=remainSingerUsers.split("\\$");
							for(String r : roles ){
								if(r.contains(nowUserName)){
									nowUserRole=r;
									break;
								}
							}
						}else{
							nowUserRole=remainSingerUsers;
						}
						
						String type=nowUserRole.substring(nowUserRole.indexOf("+")+1, nowUserRole.indexOf(":"));
						Integer roleType=Integer.valueOf(type);
						if(roleType.equals(1)){//当前登陆人的角色类型是一人审批时
							/*----------去掉这个登陆人角色的部分---------------------------*/
						   if(remainSingerUsers.indexOf(nowUserRole)==0){//在最前面
							   if(remainSingerUsers.contains("$")){
								   remainSingerUsers=remainSingerUsers.substring(remainSingerUsers.indexOf("$")+1);
							   }else{
								   remainSingerUsers="";
							   }
						   }else if(remainSingerUsers.endsWith(nowUserRole)){//在最后面
							   remainSingerUsers=remainSingerUsers.substring(0, remainSingerUsers.lastIndexOf("$"));
						   }else{//在中间时
							   remainSingerUsers=remainSingerUsers.substring(0, remainSingerUsers.indexOf(nowUserRole))+remainSingerUsers.substring(remainSingerUsers.indexOf(nowUserRole)+1);
						   }
						   /*--------------查看剩余的角色信息----------------------------*/
						   if(remainSingerUsers.length()!=0){//有剩余的角色信息时，继续指派
							   String remainPersons=allPersonFromAllRole(remainSingerUsers);//把角色中所有人都找出来
							   tm.reAssign(taskId, remainPersons);
							   vm.setVariableToBizParam(taskId, "signerUser", remainSingerUsers);
						   }else{//没有剩余的角色信息时，就去看是否有加签人
							   this.addMarkUsersExecute(bizParam, taskId, virtualDefinintionId, nodeId,creator,vProcessDesc,taskNodeName);
						   }
						}else{//当前登陆人的角色类型是多人审批时
							String remainPersonFromRole="";
							if(nowUserRole.substring(nowUserRole.indexOf(":")+1).contains("|")){//如果有“|”标识的话，那么说明后面还有审批人
								if(nowUserRole.contains("|"+nowUserName+"|")){//在中间
									remainPersonFromRole=nowUserRole.substring(0, nowUserRole.indexOf(nowUserName))+nowUserRole.substring(nowUserRole.indexOf(nowUserName)+nowUserName.length()+1);
								}else if(nowUserRole.contains("|"+nowUserName+"")){//在最后面
							        remainPersonFromRole=nowUserRole.substring(0, nowUserRole.lastIndexOf("|"));
							    }else{//在最前面
							        remainPersonFromRole=nowUserRole.substring(0, nowUserRole.indexOf(nowUserName))+nowUserRole.substring(nowUserRole.indexOf(nowUserName)+nowUserName.length()+1);
							   }
							}else{
								remainPersonFromRole=nowUserRole.substring(0, nowUserRole.indexOf(":")+1)+"";
							}
							
							if(remainPersonFromRole.substring(remainPersonFromRole.indexOf(":")+1)!=null&&!"".equals(remainPersonFromRole.substring(remainPersonFromRole.indexOf(":")+1))){//登陆人对应的角色，下面还有人
								String remainPerson=remainPersonFromRole.substring(remainPersonFromRole.indexOf(":")+1);
								tm.reAssign(taskId, remainPerson);
								remainSingerUsers=remainSingerUsers.replace(nowUserRole, remainPersonFromRole);
								vm.setVariableToBizParam(taskId, "signerUser", remainSingerUsers);
							}else{//如果登陆人对应的角色，下面没有人了,去掉这个登陆人角色的部分,继续把下几个角色的人都指派进来
								 if(remainSingerUsers.indexOf(nowUserRole)==0){//在最前面
									   if(remainSingerUsers.contains("$")){
										   remainSingerUsers=remainSingerUsers.substring(remainSingerUsers.indexOf("$")+1);
									   }else{
										   remainSingerUsers="";
									   }
								   }else if(remainSingerUsers.endsWith(nowUserRole)){//在最后面
									   remainSingerUsers=remainSingerUsers.substring(0, remainSingerUsers.lastIndexOf("$"));
								   }else{//在中间时
									   remainSingerUsers=remainSingerUsers.substring(0, remainSingerUsers.indexOf(nowUserRole))+remainSingerUsers.substring(remainSingerUsers.indexOf(nowUserRole)+1);
								   }
								  /*--------------查看剩余的角色信息----------------------------*/
								   if(remainSingerUsers.length()!=0){//有剩余的角色信息时，继续指派
									   String remainPersons=allPersonFromAllRole(remainSingerUsers);
									   tm.reAssign(taskId, remainPersons);
									   vm.setVariableToBizParam(taskId, "signerUser", remainSingerUsers);
								   }else{//没有剩余的角色信息时，就去看是否有加签人
									   this.addMarkUsersExecute(bizParam, taskId, virtualDefinintionId, nodeId,creator,vProcessDesc,taskNodeName);
								   }
							}
						}
				}
		  }
		} else {// 是加签人进来审批的
			String symbol = (String) bizParam.get("symbol");// bizParam中symbol即区分是否是加签人来审批的，又存上一个加签人的类型值
			String addMarkUsers = (String) bizParam.get("addMarkUsers");// 存在bizParam中的加签人
			if ("1".equals(symbol)) {// 如果这个加签人是通知类型
				if (addMarkUsers != null && !"".equals(addMarkUsers)) {// 继续判断，还有加签人时,继续指派加签人
					String userAndType = addMarkUsersUtil(addMarkUsers);//这一步只是把加签格式中taskId去掉
					if(userAndType!=null&&!"".equals(userAndType)){
						String[] userArray = userAndType.split(",");
						String userId = userArray[0].substring(0,userArray[0].indexOf("+"));
						String typeId = userArray[0].substring(userArray[0].lastIndexOf("+") + 1);
						UserInfo userInfo = (UserInfo) service.find(
								UserInfo.class, userId);
						
						Long newTaskId = tm.addSignReAssign(taskId, userInfo.getUserName());// 此加签人指派完之后
						//add by guangsa for 应网审批按钮根据审批人的需求 in 20100309 begin
						WorkflowRecordTaskInfo currentAuditPer = cs.findWorkflowRecordByProcessId(Long.valueOf(processId));
						if(newTaskId != null&&!"".equals(newTaskId)){
							currentAuditPer.setTaskId(newTaskId);////add by debby 
						}else{
							throw new Exception("加签生成新任务实例时发生异常！");
						}
						currentAuditPer.setAuditUserInfos(userInfo.getUserName());
						cs.saveWorkflowTaskInfoByEntity(currentAuditPer);
						//add by guangsa for 应网审批按钮根据审批人的需求 in 20100309 end
						try{
							tm.execute(taskId);
						}catch(RuleFileException e){
							throw new RuleFileException(e.getMessage());
						}catch(Exception e){
							throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实");
						}			
						// 指派完之后，给这个加签人发邮件
						String[] auditPers=new String[]{userInfo.getUserName()};
						SynchronousAction sa = new SynchronousAction(taskNodeName,pageUrl,dataId,reqClass,goStartState,Long.parseLong(processId),newTaskId,applyType, vProcessDesc, virtualDefinintionId, creator, bizParam, node, String.valueOf(nodeId),auditPers,new HashMap(),null);
						Thread t = new Thread(sa);
						t.start();
//						ConfigUnitMail unitMail = cs.findMailObjectById(
//								String.valueOf(virtualDefinintionId),
//								String.valueOf(nodeId));
//						ms.sendSimplyMail(userInfo.getEmail(), null, null,
//								unitMail == null ? "通知" : unitMail
//										.getSubject(),
//								unitMail == null ? "审批任务" : unitMail
//										.getContent());
						/** ***********得到剩下的加签人************************ */
						String hasMarkUsers = "";
						String taskid = addMarkUsers.substring(0,
								addMarkUsers.indexOf(":"));
						addMarkUsers = addMarkUsers.substring(addMarkUsers
								.indexOf(":") + 1);
						String[] s = addMarkUsers.split(",");
						for (String ss : s) {
							if (ss.contains(userId)) {
	
							} else {
								hasMarkUsers += ss;
								hasMarkUsers += ",";
							}
						}
						if (hasMarkUsers.endsWith(",")) {
							hasMarkUsers = hasMarkUsers.substring(0,
									hasMarkUsers.length() - 1);
						}
						/** ************************************************* */
	
						if (userArray.length == 1) {// 如果没有下一个加签人了，就设置为空
							vm.setVariableToBizParam(taskId,"addMarkUsers", "");
						} else {
							vm.setVariableToBizParam(taskId,"addMarkUsers", taskid + ":"+ hasMarkUsers);
						}
						vm.setVariableToBizParam(taskId, "symbol", typeId);
					} else {// 继续判断,无加签人时，就执行任务(最后一个是通知类型的加签人时，都按Y线走
						vm.setVariableByTaskId(taskId,
								WorkflowConstants.RESULT_FLAG, "Y");
						try{
							tm.execute(taskId);
						}catch(RuleFileException e){
							throw new RuleFileException(e.getMessage());
						}catch(Exception e){
							throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实");
						}			
						vm.setVariableToBizParam(taskId, "symbol", "");
						String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
						if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
							vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// 如果在当前节点给下一个节点设置了加签人时，就加进去
							vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//把存放下一个节点加签人的变量addMarkUsersToNextNode设置为空
						}else{
							vm.setVariableToBizParam(taskId, "addMarkUsers", "");//如果在当前节点没有给下一个节点设置加签人时， 把存放加签人的变量设置为空
						}
					}
				} else {// 继续判断,无加签人时，就执行任务(最后一个是通知类型的加签人时，都按Y线走
					vm.setVariableByTaskId(taskId,
							WorkflowConstants.RESULT_FLAG, "Y");
					try{
						tm.execute(taskId);
					}catch(RuleFileException e){
						throw new RuleFileException(e.getMessage());
					}catch(Exception e){
						throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实");
					}			
					vm.setVariableToBizParam(taskId, "symbol", "");
					String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
					if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
						vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// 如果在当前节点给下一个节点设置了加签人时，就加进去
						vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//把存放下一个节点加签人的变量addMarkUsersToNextNode设置为空
					}else{
						vm.setVariableToBizParam(taskId, "addMarkUsers", "");//如果在当前节点没有给下一个节点设置加签人时， 把存放加签人的变量设置为空
					}
				}
			} else {// 如果这个加签人是审批类型
				if ("N".equals(result)) {
					try{
						tm.execute(taskId);
					}catch(RuleFileException e){
						throw new RuleFileException(e.getMessage());
					}catch(Exception e){
						throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实");
					}			
					vm.setVariableToBizParam(taskId, "symbol", "");
					String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
					if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
						vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// 如果在当前节点给下一个节点设置了加签人时，就加进去
						vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//把存放下一个节点加签人的变量addMarkUsersToNextNode设置为空
					}else{
						vm.setVariableToBizParam(taskId, "addMarkUsers", "");//如果在当前节点没有给下一个节点设置加签人时， 把存放加签人的变量设置为空
					}
				} else {
					if (addMarkUsers != null&& !"".equals(addMarkUsers)) {// 继续判断，还有加签人时,继续指派加签人
						String userAndType = addMarkUsersUtil(addMarkUsers);
						if(userAndType!=null&&!"".equals(userAndType)){
							String[] userArray = userAndType.split(",");
							String userId = userArray[0].substring(0,userArray[0].indexOf("+"));
							String typeId = userArray[0].substring(userArray[0].lastIndexOf("+") + 1);
							UserInfo userInfo = (UserInfo) service.find(UserInfo.class, userId);
							
							Long newTaskId = tm.addSignReAssign(taskId, userInfo.getUserName());// 此加签人指派完之后
							//add by guangsa for 应网审批按钮根据审批人的需求 in 20100309 begin
							WorkflowRecordTaskInfo currentAuditPer = cs.findWorkflowRecordByProcessId(Long.valueOf(processId));
							if(newTaskId != null&&!"".equals(newTaskId)){
								currentAuditPer.setTaskId(newTaskId);////add by debby 
							}else{
								throw new Exception("加签生成新任务实例时发生异常！");
							}
							currentAuditPer.setAuditUserInfos(userInfo.getUserName());
							cs.saveWorkflowTaskInfoByEntity(currentAuditPer);
							//add by guangsa for 应网审批按钮根据审批人的需求 in 20100309 end
							try{
								tm.execute(taskId);
							}catch(RuleFileException e){
								throw new RuleFileException(e.getMessage());
							}catch(Exception e){
								throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实");
							}			
							// 指派完之后，给这个加签人发邮件
							String[] auditPers=new String[]{userInfo.getUserName()};
							SynchronousAction sa = new SynchronousAction(taskNodeName,pageUrl,dataId,reqClass,goStartState,Long.parseLong(processId),newTaskId,applyType, vProcessDesc, virtualDefinintionId, creator, bizParam, node, String.valueOf(nodeId),auditPers,new HashMap(),null);
							Thread t = new Thread(sa);
							t.start();
//							ConfigUnitMail unitMail = cs
//									.findMailObjectById(String
//											.valueOf(virtualDefinintionId),
//											String.valueOf(nodeId));
//							ms.sendSimplyMail(userInfo.getEmail(), null,
//									null, unitMail == null ? "通知"
//											: unitMail.getSubject(),
//									unitMail == null ? "审批任务" : unitMail
//											.getContent());
	
							/** ***********得到剩下的加签人************************ */
							String hasMarkUsers = "";
							String taskid = addMarkUsers.substring(0,
									addMarkUsers.indexOf(":"));
							addMarkUsers = addMarkUsers
									.substring(addMarkUsers.indexOf(":") + 1);
							String[] s = addMarkUsers.split(",");
							for (String ss : s) {
								if (ss.contains(userId)) {
	
								} else {
									hasMarkUsers += ss;
									hasMarkUsers += ",";
								}
							}
							if (hasMarkUsers.endsWith(",")) {
								hasMarkUsers = hasMarkUsers.substring(0,
										hasMarkUsers.length() - 1);
							}
							/** ************************************************* */
							if (userArray.length == 1) {// 如果没有下一个加签人了，就设置为空
								vm.setVariableToBizParam(taskId,"addMarkUsers", "");
							} else {
								vm.setVariableToBizParam(taskId,"addMarkUsers", taskid + ":"+ hasMarkUsers);
							}
							vm.setVariableToBizParam(taskId, "symbol",
									typeId);
						}else {
							try{
								tm.execute(taskId);
							}catch(RuleFileException e){
								throw new RuleFileException(e.getMessage());
							}catch(Exception e){
								throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实");
							}			
							vm.setVariableToBizParam(taskId, "symbol", "");
							String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
							if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
								vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// 如果在当前节点给下一个节点设置了加签人时，就加进去
								vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//把存放下一个节点加签人的变量addMarkUsersToNextNode设置为空
							}else{
								vm.setVariableToBizParam(taskId, "addMarkUsers", "");//如果在当前节点没有给下一个节点设置加签人时， 把存放加签人的变量设置为空
							}
						}
					} else {
						try{
							tm.execute(taskId);
						}catch(RuleFileException e){
							throw new RuleFileException(e.getMessage());
						}catch(Exception e){
							throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实");
						}			
						vm.setVariableToBizParam(taskId, "symbol", "");
						String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
						if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
							vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// 如果在当前节点给下一个节点设置了加签人时，就加进去
							vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//把存放下一个节点加签人的变量addMarkUsersToNextNode设置为空
						}else{
							vm.setVariableToBizParam(taskId, "addMarkUsers", "");//如果在当前节点没有给下一个节点设置加签人时， 把存放加签人的变量设置为空
						}
					}
				}
			}
		}
	}
	
	/**
	 * 工具方法
	 * @Methods Name allPersonFromAllRole
	 * @Create In Apr 20, 2009 By Administrator
	 * @param remainSingerUsers
	 * @return String
	 */
	private String allPersonFromAllRole(String remainSingerUsers){
		String allPerson="";
		if(remainSingerUsers.contains("$")){
			String[] roles=remainSingerUsers.split("\\$");
			for(String role :roles){
				allPerson+=role.substring(role.indexOf(":")+1);
				allPerson+="|";
			}
			if(allPerson.endsWith("|")){
				allPerson=allPerson.substring(0, allPerson.length()-1);
			}
		}else{
			allPerson = remainSingerUsers.substring(remainSingerUsers.indexOf(":")+1);
		}
		
		return allPerson;
	}
	
	
	/**
	 * 看是否有加签人，由加签人执行
	 * @Methods Name addMarkUsersExecute
	 * @Create In Apr 20, 2009 By Administrator
	 * @param bizParam
	 * @param taskId
	 * @param virtualDefinintionId
	 * @param nodeId void
	 */
	private void addMarkUsersExecute(Map  bizParam,Long taskId,Long virtualDefinintionId,Long nodeId,String creator,String vDesc,String taskNodeName)throws Exception{

		//add by guangsa in 20090720 for sendMailContext begin
		String dataId = (String)bizParam.get("dataId");
		String reqClass = (String)bizParam.get("reqClass");
		String goStartState = (String)bizParam.get("goStartState");
		String workflowEntity = (String)bizParam.get("workflowHistory");
		String processId = (String)bizParam.get("processId");
		String applyType = (String)bizParam.get("applyType");
		Node node=(Node) service.find(Node.class, nodeId.toString());

		//add by gaowen in 20091125 for 新邮件审批格式
		UserInfo creatorMeg = (UserInfo)service.findUnique(UserInfo.class, "userName", creator);
		//add by guangsa in 20090720 for sendMailContext end
		String addMarkUsers = (String) bizParam.get("addMarkUsers");//存在bizParam中的加签人
		//加签的格式addMarkUsers: taskId:userId+顺序+类型,userId+顺序+类型,userId+顺序+类型
		if (addMarkUsers != null && !"".equals(addMarkUsers)) {
			//String userAndType = addMarkUsersUtil(addMarkUsers);//对于加签人排一下顺序
			String nowMarks = addMarkUsers.substring(addMarkUsers.indexOf(":")+1);
			if(nowMarks!=null&&!"".equals(nowMarks)){
				String[] userArray = nowMarks.split(",");
				String userId = userArray[0].substring(0,userArray[0].indexOf("+"));
				String typeId = userArray[0].substring(userArray[0].lastIndexOf("+") + 1);
				UserInfo userInfo = (UserInfo) service.find(UserInfo.class, userId);
				
				Long newTaskId = tm.addSignReAssign(taskId, userInfo.getUserName());// 此加签人指派完之后
				//add by guangsa for 应网审批按钮根据审批人的需求 in 20100309 begin
				WorkflowRecordTaskInfo currentAuditPer = cs.findWorkflowRecordByProcessId(Long.valueOf(processId));
				if(newTaskId != null&&!"".equals(newTaskId)){
					currentAuditPer.setTaskId(newTaskId);////add by debby 
				}else{
					throw new Exception("加签生成新任务实例时发生异常！");
				}
				currentAuditPer.setAuditUserInfos(userInfo.getUserName());
				cs.saveWorkflowTaskInfoByEntity(currentAuditPer);
				//add by guangsa for 应网审批按钮根据审批人的需求 in 20100309 end
				tm.execute(taskId);
				/**************************************************************************************************************/
				//add by guangsa for 页面审批 in 20090818 begin
				WorkflowRecordTaskInfo recordTaskInfo = vm.findWorkflowRecordTaskInfoById(Long.valueOf(processId), nodeId);//  virtualDefinintionId
				recordTaskInfo.setAuditUserInfos(userInfo.getUserName());
				service.save(recordTaskInfo);
				//add by guangsa for 页面审批 in 20090818 end
				/**************************************************************************************************************/
				// 指派完之后，给这个加签人发邮件
				String pageUrl = PropertiesUtil.getProperties("system.mail.develop.background.link", "/servlet/getPageModel?taskId=");
				List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.valueOf(processId));//查找出来的是所有的按流程顺序排列的节点信息
				
				String content = cs.htmlContent(taskNodeName,pageUrl,applyType,dataId, reqClass, goStartState, newTaskId, creator, vDesc, auditHis,"0",false);
				//add By gaowen for 加签邮件模板修改 in 2009-12-1 begin
//				JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
//				TaskInstance taskInstance = null;
//					try{
//						taskInstance = jbpmContext.loadTaskInstance(taskId);
//						Map variables = taskInstance.getContextInstance().getVariables();
//						// 先清除TASKINFO,否则会有死循环问题。
//						if (variables.containsKey(WorkflowConstants.TASKINFO_KEY)) {
//							variables.remove(WorkflowConstants.TASKINFO_KEY);
//						}
//						JSONObject jo = JSONObject.fromObject(variables);
//						String result = (String) jo.get(WorkflowConstants.RESULT_FLAG);
//						String comment = (String) jo.get(WorkflowConstants.COMMENT_FLAG);
//						cs.saveWorkflowHistoryAuditHis(workflowEntity, processId,dataId,reqClass,taskNodeName,nodeId.toString(),serviceItem,result,comment);
//					 }catch(Exception e){
//						 throw new RuntimeException("保存加签审批历史失败，请核实");
//					 }
						
					
					/** **********得到results***************** */
					
				
				String[] auditPers=new String[]{userInfo.getUserName()};
				SynchronousAction sa = new SynchronousAction(taskNodeName,pageUrl,dataId,reqClass,goStartState,Long.parseLong(processId),taskId,applyType, vDesc, virtualDefinintionId, creator, bizParam, node, String.valueOf(nodeId),auditPers,new HashMap(),null);
				Thread t = new Thread(sa);
				t.start();
			//add By gaowen for 加签邮件模板修改 in 2009-12-1 end
//				ConfigUnitMail unitMail = cs.findMailObjectById(
//						String.valueOf(virtualDefinintionId),
//						String.valueOf(nodeId));
//				
//				ms.sendSimplyMail(userInfo.getEmail(), null, null,
//						unitMail == null ? "审批通知" : unitMail
//								.getSubject(),
//						unitMail == null ? content : unitMail
//								.getContent());
				

				/** ***********得到剩下的加签人*************************/
				String hasMarkUsers = "";
				String taskid = addMarkUsers.substring(0,addMarkUsers.indexOf(":"));
				String[] userMegs = nowMarks.split(",");
				for(int i=1;i<userMegs.length;i++){
					hasMarkUsers += userMegs[i];
					hasMarkUsers += ",";
				}
				if (hasMarkUsers.endsWith(",")) {
					hasMarkUsers = hasMarkUsers.substring(0,
							hasMarkUsers.length() - 1);
				}
				/** ************************************************* */
				if (userArray.length == 1) {// 如果没有下一个加签人了，就设置为空
					vm.setVariableToBizParam(taskId,"addMarkUsers", "");
				} else {
					vm.setVariableToBizParam(taskId,"addMarkUsers", taskid + ":"+ hasMarkUsers);
				}
				vm.setVariableToBizParam(taskId, "symbol", typeId);
			
			}else {// 没有加签人时，就直接执行任务
				try{
					tm.execute(taskId);
				}catch(Exception e){
					throw new RuntimeException("在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实");
				}		
				vm.setVariableToBizParam(taskId, "symbol", "");
				String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
				if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
					vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// 如果在当前节点给下一个节点设置了加签人时，就加进去
					vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//把存放下一个节点加签人的变量addMarkUsersToNextNode设置为空
				}else{
					vm.setVariableToBizParam(taskId, "addMarkUsers", "");//如果在当前节点没有给下一个节点设置加签人时， 把存放加签人的变量设置为空
				}
			}
	} else {// 没有加签人时，就直接执行任务
			try{
				tm.execute(taskId);
			}catch(Exception e){
				throw new RuntimeException(e.getMessage());//"在执行任务时候发生异常，极大可能是读取规则文件时候发生异常，请核实"
			}		
			vm.setVariableToBizParam(taskId, "symbol", "");
			String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
			if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
				vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// 如果在当前节点给下一个节点设置了加签人时，就加进去
				vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//把存放下一个节点加签人的变量addMarkUsersToNextNode设置为空
			}else{
				vm.setVariableToBizParam(taskId, "addMarkUsers", "");//如果在当前节点没有给下一个节点设置加签人时， 把存放加签人的变量设置为空
			}
		}
   
	}
	
	/**
	 * 通过虚拟流程名和业务数据得到唯一一个节点信息，通过节点信息得到相应虚拟节点信息
	 * @param request
	 * @return
	 */
	private String getTaskInfoMessage(HttpServletRequest request){
		
		String json="";
		String pageUrl = "";
		String vProcessName = request.getParameter("vProcessName");
		String dataId = request.getParameter("dataId");
		WorkflowRecordTaskInfo workflowRecordTaskInfo = cs.findWorkflowRecordTaskInfo(dataId, vProcessName);
		Long nodeId = workflowRecordTaskInfo.getNodeId();
		Long vProcessId = workflowRecordTaskInfo.getVirtualProcessId();
		PageModel pageModel = pageModelService.findPageModelByVritualProcessIdAndNodeId(vProcessId, nodeId);
		if(pageModel!=null||!"".equals(pageModel)){
			pageUrl = pageModel.getPagePath();
		}
		json+=pageUrl;
		json+="";
		
		return json;
	}
	/**
	 * 得到某个流程所有节点信息
	 * @param request
	 * @return
	 */
	private String getAllNodeMessagek(HttpServletRequest request){
		
		String json = "";
		try{
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			String taskId = request.getParameter("taskId");
			TaskInstance ti = jbpmContext.loadTaskInstance(Long.valueOf(taskId));
			List<Node> nodes = ti.getProcessInstance().getProcessDefinition().getNodes();
			for(Node node : nodes){
				if(node!=null&&!"".equals(node)){
					Long nodeId = node.getId();
					String nodeName = node.getName();
					json += "{id:" + nodeId + ",name:'" + nodeName+ "'}";
					json += ",";
				}
			}
			if (json.endsWith(",")) {
				json = json.substring(0, json.length() - 1);
			}
			json = "{success: true"  + ",data:[" + json + "]}";
		}catch(Exception e ){
			e.printStackTrace();
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		return json;
	}
	/**
	 * 当流程回到开始节点以后，不需要重新提交的情况
	 * @param request
	 * @return
	 */
	private String StartStateToCancelFlow(HttpServletRequest request){
		
		String json = "";
		try{
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			String vProcessId = request.getParameter("vProcessId");
			String processId = request.getParameter("processId");
			ps.endProcess(Long.valueOf(processId));
			wfBack.removeWorkflowRegressionParametersByProcessId(Long.valueOf(vProcessId), Long.valueOf(processId));
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		json = "{success:true}";
		return json;
	}
	/**
	 * 当流程回到开始节点以后，需要重新提交的情况;此时需要把当前流程结束掉，然后再开启一个新流程
	 * @param request
	 * @return
	 */
	private String StartStateAfreshSubmit(HttpServletRequest request){
		String json = "";
		try{
			JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
			Map startNodeBizParam = new HashMap();
			String vProcessId = request.getParameter("vProcessId");
			String processId = request.getParameter("processId");
			String nodeId = request.getParameter("nodeId");
			ProcessInstance pi = jbpmContext.getProcessInstance(Long.valueOf(processId));
			ContextInstance ci = pi.getContextInstance();
			String vName = (String)ci.getVariable("VIRTUALDEFINITIONINFO_NAME");			
			String creator= (String)ci.getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);		
			VirtualNodeInfo nodeInfo = cs.findVirtualNodeInfoByDoubleId(Long.valueOf(vProcessId), Long.valueOf(nodeId));
			if(nodeInfo!=null&&!"".equals(nodeInfo)){
				Node node = pi.getProcessDefinition().getNode(nodeInfo.getVirtualNodeName());
				Long fromNodeId = node.getId();
				WorkflowRegressionParameters regParam = wfBack.findWorkflowRegressionParametersByMuitlyId(Long.valueOf(vProcessId), Long.valueOf(processId), fromNodeId);
				String params = regParam.getRegressionParams();
				//参数格式：{key+value;key+value;key+value;+key+value}
				String[] mutils = params.split("\\;");
				for(int i=0;i<mutils.length;i++){
					String[] single = mutils[i].split("\\+");
					if("nonValue".equals(single[1])){
						single[1]="";
					}
					startNodeBizParam.put(single[0], single[1]);
				}
			}
			ps.endProcess(Long.valueOf(processId));
			wfBack.removeWorkflowRegressionParametersByProcessId(Long.valueOf(vProcessId), Long.valueOf(processId));
			ps.createProcess(vName, creator, startNodeBizParam);//如果用户想回到开始节点，则说明用户想重新发起流程,所以原先保存的节点参数信息要删除掉
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		json = "{success:true}";
		return json;
	}
}
