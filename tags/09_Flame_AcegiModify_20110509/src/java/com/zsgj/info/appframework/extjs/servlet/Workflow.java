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
			if (method.trim().equalsIgnoreCase("apply")) {// ����
				msg = apply(request);
			} else if (method.trim().equalsIgnoreCase("audit")) {// ���
				msg = audit(request);
			} else if (method.trim().equalsIgnoreCase("tasks")) {// �����б�
				msg = tasks(request);
			} else if (method.trim().equalsIgnoreCase("next")) {// �����б�
				msg = next(request);
			} else if (method.trim().equalsIgnoreCase("assign")) {// ָ��
				// msg = export(request);
			} else if (method.trim().equalsIgnoreCase("getDataFromPage")) {//��ҳ���ϵõ����ݷŽ�ʵ����bizParam��
				msg = getDataFromPage(request);
			} else if (method.trim().equalsIgnoreCase("getData")) {//��ҳ���л��ָ���˵���Ϣ�Ž�bizParam�е�userList��
				msg = getData(request);
			} else if (method.trim().equalsIgnoreCase("reAssignToNode")) {//����ǰ�ڵ�ָ����
				msg = reAssignToNode(request);
			} else if (method.trim().equalsIgnoreCase("addMarkUsersInfo")) {//��ü�ǩ�˵���Ϣ�Ž�bizParam�е�addMarkUsers��
				msg = addMarkUsersInfo(request);
			} else if (method.trim().equalsIgnoreCase("getMarkUsers")) {//��bizParam�е�addMarkUsers��ü�ǩ�˵���Ϣ
				msg = getMarkUsers(request);
			} else if (method.trim().equalsIgnoreCase("getMarkUsersToNextNode")) {//��bizParam�е�addMarkUsersToNextNode��ü�ǩ�˵���Ϣ
				msg = getMarkUsersToNextNode(request);
			} else if (method.trim().equalsIgnoreCase("addMarkUsersInfoToNextNode")) {//�����һ���ڵ��ǩ�˵���Ϣ�Ž�bizParam�е�addMarkUsersToNextNode��
				msg = addMarkUsersInfoToNextNode(request);
			}else if (method.trim().equalsIgnoreCase("deleteMarkUsersInfo")) {//ɾ����ǩ�˵���Ϣ�Ž�bizParam�е�addMarkUsers��
				msg = deleteMarkUsersInfo(request);
			} else if (method.trim().equalsIgnoreCase("deleteMarkUsersInfoToNextNode")) {//�����һ���ڵ��ǩ�˵���Ϣ�Ž�bizParam�е�addMarkUsersToNextNode��
				msg = deleteMarkUsersInfoToNextNode(request);
			} else if (method.trim().equalsIgnoreCase("getTaskInfoMessage")) {//�õ�������Ϣ
				msg = getTaskInfoMessage(request);
			} else if (method.trim().equalsIgnoreCase("getAllNodeMessagek")) {//�õ�ĳ���̵Ľڵ���Ϣ 
				msg = getAllNodeMessagek(request);
			}else if (method.trim().equalsIgnoreCase("getWorkFlowSkipGoBack")) {//�õ�������Ϣ
				msg = getWorkFlowSkipGoBack(request);
			}else if (method.trim().equalsIgnoreCase("StartStateAfreshSubmit")) {//�õ�������Ϣ
				msg = StartStateAfreshSubmit(request);
			}else if (method.trim().equalsIgnoreCase("StartStateToCancelFlow")) {//�õ�������Ϣ
				msg = StartStateToCancelFlow(request);
			}else if (method.trim().equalsIgnoreCase("getWorkFlowGoBack")) {//��ú������̵Ĳ���
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
	 * �����������ƺͲ������Ƽ��������Ԥָ����Ϣ �ٸ��ݴ���������Ϣ����Ԥָ����Ϣ �ٸ����û���ʱָ����Ԥָ����Ϣ�걸Ԥָ����Ϣ
	 * ��󷵻�ʵ����Ҫ������Ԥָ����Ϣ
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
		// �����û�ָ����Ϣ
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

		// ��ʼ����

		Set<DefinitionPreAssign> defKeySet = mapAssign.keySet();
		for (DefinitionPreAssign def : defKeySet) {

			// ��ο���û�пɼ���Ľ�ɫ�趨�û�������У�ȡ��ɫ�û�
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
			// ��󣬴��û�Ԥָ�ɵ��û��в�ȫ�����û���Ϣ
			else {
				if (def.getWorkflowRole() != null) {// ���̽�ɫΪ�ձ�ʾ�˽ڵ㲻��Ҫָ�ɣ����ط�����
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
		// ����д�����Ϣ�����������Ϣ
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
	 * ���ݲ��ź����̼���ÿ�����̽�ɫ���������û� ���������Ż�û��ָ�����û�����Ϊmap��null.
	 * ���ص��Ǹ���̬�ļ���ֵ���������ݿ��е�ʵ�壬�����Ա���
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
		// ��Ҫ�Ĳ���
		if (departmentCode == null || departmentCode.equals("")) {
			throw new RuntimeException("����ָ�����š�");
		}
		// �ָ��ݲ��ź��ҵ�����
		List<Department> depts = service.find(Department.class, "departCode",
				new Long(departmentCode));
		if (depts == null || depts.isEmpty()) {
			throw new RuntimeException("���ű�Ų��Ϸ���");
		}

		// ����˼·�����������̻�����Ϣ�����ҵ�����������Ӧ����Ϣ�����������Ҫָ�ɵĽ�ɫ
		List<DefinitionPreAssign> defs = service.find(
				DefinitionPreAssign.class, "definitionName", definitionName);

		List<WorkflowRole> deptRoles = tas.findWorkflowRoleByDepartment(depts
				.get(0));// ���������е����̽�ɫ
		Map<DefinitionPreAssign, List<UserInfo>> mapPreAssign = new HashMap<DefinitionPreAssign, List<UserInfo>>();
		for (DefinitionPreAssign def : defs) {
			WorkflowRole defRole = def.getWorkflowRole();
			def.setDepartmentCode(departmentCode);
			def.setDepartmentName(depts.get(0).getDepartName());
			if (deptRoles.contains(defRole)) {// �Ǳ����̵Ľ�ɫ
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
	 * ���һ�û��ָ�ɾ�����Ա�����̽�ɫ
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
		// ��Ҫ�Ĳ���
		Set<DefinitionPreAssign> defs = mapPreAssign.keySet();
		// ��дjson
		for (DefinitionPreAssign def : defs) {
			if (mapPreAssign.get(def) == null) {// ɸѡ��û��ָ���û��Ľ�ɫ
				WorkflowRole wfrole = def.getWorkflowRole();
				if (wfrole != null) {// ���̽�ɫ��û�ж���ʱ�������Ǳ����ڻص�������
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
	 * ���ĳ���̵�������ʷ��Ϣ
	 * 
	 * @Methods Name history
	 * @Create In Sep 10, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	private String next(HttpServletRequest request) {
		// ��Ҫ�Ĳ���
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
			System.out.println("ListHistoryAction��������");
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
			// ��ʵ�����ƴ����û�ϵͳ��
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
	 * ������루������������,��Ҫ���ǵ�һ���ڵ��п���ָ�ɸ����˵���� ֧�ֵ��ڵ㵥������ָ�ɣ���ʽΪa|b|c, ����Ҫ������������
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
		// ����������
		String definitionName = request.getParameter("defname");
		String buzzParameters = request.getParameter("bzparam");// ��ajax�����Ѿ���js��������json�ַ���
		String dataId = request.getParameter("dataId");// ������id
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
		// mapBizz.put("applyTypeName", "��������");
		String creator = UserContext.getUserInfo().getUserName();
		long instanceId = ps.createProcess(definitionName, creator, mapBizz);
		json = "{success:true,id:'" + instanceId + "'}";
		// }
		return json;
	}

	/**
	 * ��������ִ������ڵ㣩
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
		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
		String strTaskId = request.getParameter("id");
		String done = request.getParameter("done");
		Long taskId = null;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		// �ҵ��ڵ���Ϣ����ӦСform��Ϣ
		TaskInfo ti = tm.getTaskInfo(taskId);
		if(ti!=null&&!"".equals(ti)){
			formName = FormHelper.findForm(ti.getDefinitionName(),ti.getNodeDisc()).trim();
		}else{
			json = "{success:true,Exception:'" + "����ʵ�������ڣ������ù���Ա�������" + "'}";	
			return json;
		}
		if (done == null) {// ִ��ǰ��ʾ
			json = "{success:true,formName:'" + formName + "'}";
		} else {// ִ����������
			Enumeration para = request.getParameterNames();//ҳ�洫�ݲ���
			// ������������ж�Ӧ���Ƶı��������ҳ�����ݴ���ȥ
			Map mapVar = vm.listVariablesByTaskId(taskId);//���ԭ����������û��ֵ��Ȼ��������ĳ��ʱ��Ҫ���˲�����ֵ�ˣ��൱vm.listVariablesByTaskId(taskId)��vm.listBizVariablesByTaskId(long taskId);
			for (; para.hasMoreElements();) {
				String varName = (String) para.nextElement();
				if (mapVar.containsKey(varName)) {
					String v = request.getParameter(varName);	
					if (request.getMethod().equalsIgnoreCase("get")) {
						// �ַ���ת��
						try {// extjsת����ʽ
							v = new String(v.getBytes("iso8859-1"), "utf-8");
							//add start by gaowen ת���ո� �������������� 2009-10-13
							v=v.replace("&nbsp;", "");
							//add end
						} catch (UnsupportedEncodingException e) {
							json = "{success:true,Exception:'" + "��������ʱ�����ݵ�ҵ�������ת��ʱ�����쳣" + "'}";
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
			Map bizParam = vm.listBizVariablesByTaskId(taskId);//�õ���Ӧ��ҵ���
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
					json = "{success:true,Exception:'" + "��������ʱ�����̼�������ʵ���Ƿ����쳣�������ù���Ա���" + "'}";
					return json;
				}
				if(!taskInstance.isOpen()) {
					throw new RuntimeException("��ǰ�������Ѿ���ϵͳ���ʼ����������ˣ�������ϸ�˲飡��������ʼ��������¼ϵͳ���������ϵͳ��������������ˢ�£�");
				}
				Long nodeId = taskInstance.getTask().getTaskNode().getId();
				String taskNodeName = taskInstance.getTask().getTaskNode().getName();
	            String desc=taskInstance.getTask().getTaskNode().getDescription();
	            Token token = taskInstance.getToken();
				// ��������id�õ���һ���ڵ���Ϣ���ж��Ƿ��������ˣ�
				
				if(map.size()!=0){
					nextNodeDesc = (String) map.get("nodeDesc");
					nextNodeName = (String) map.get("nodeName");
					nextnodeType = (String) map.get("nodeType");//�����ڵ���Node����TaskNode����
					nextNodeId = (Long) map.get("nodeId");
				}else{
					json = "{success:true,Exception:'" + "��������ʱ��ͨ������ʵ����������¸��ڵ���Ϣʱ�����쳣�������ù���Ա���" + "'}";
					return json;
				}
				Long virtualDefinintionId = (Long) taskInstance.getContextInstance().getVariable("VIRTUALDEFINITIONINFO_ID");
				String creator = (String) taskInstance.getContextInstance().getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
				/** **********�õ�results***************** */
				Map variables = taskInstance.getContextInstance().getVariables();
				// �����TASKINFO,���������ѭ�����⡣
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
					json = "{success:true,Exception:'" + "��������ʱ��û���ҵ���̨���õ����̶���ʵ�壬�����ù���Ա���" + "'}";
					return json;
				}
				// ���Ǻ�̨��Ľ�ɫ,����Ҫ�ж��Ǹ��������еĽ�ɫ
				ConfigUnitRole nextUnitRole = si.findUnitRoleByNodeTypeAndProDesc(processName, nextNodeDesc, String	.valueOf(virtualDefinintionId),nextNodeId);
				//�õ�����ڵ��̨���õĽ�ɫ����
				ConfigUnitRole nowUnitRole = si.findUnitRoleByNodeTypeAndProDesc(processName, desc, String.valueOf(virtualDefinintionId),nodeId);
				Integer nodeRoleType= 1;
				if(nowUnitRole!=null&&!"".equals(nowUnitRole)){
					nodeRoleType= nowUnitRole.getNodeType();//������ǽڵ�����(��һ���������Ƕ�������)
				}
				
				//add by guangsa for dynCounterSign in 20090725 begin
				/*************�ж���һ���ڵ��Ƿ��л�ǩ��Աָ��***********************/
				//dynCounterSign�ĸ�ʽ:nodeDesc:��������+userName,userName;��������+userName,userName$��������+userName,userName;��������+userName,userName
				String dynCounterSign = (String)bizParam.get("dynCounterSign");
				Boolean dynFlag = false;
				if (dynCounterSign != null && !"".equals(dynCounterSign)) {
					String[] dynMegs = dynCounterSign.split("\\$");
					//add by guangsa for �����ǰ�ڵ��Ƕ�̬��ǩ�ڵ㣬��ʱ�����������һ���˾ܾ���������žܾ�����·��ת begin
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
					//add by guangsa for �����ǰ�ڵ��Ƕ�̬��ǩ�ڵ㣬��ʱ�����������һ���˾ܾ���������žܾ�����·��ת end
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
				/*************�ж��Ƿ��ж�ָ̬�ɵ���Ա***********************/
				String userList = (String) bizParam.get("userList");
				Boolean flag = false;// �ж϶�ָ̬���е�ǰ�ڵ��Ƿ�����
				if (userList != null && !"".equals(userList)) {
					String[] ids = userList.split("\\$");
					for (String user : ids) {
						String nodeDesc = user.substring(0, user.indexOf(":"));
						if (nextNodeDesc.equals(nodeDesc)) {
							flag = true;
						}
					}
				}
				/*************�ж��Ƿ��ж�ָ̬��+��̨����Ա***********************/
				String addDynAssignPer = (String)bizParam.get("addDynAssignPer");
				Boolean addFlag = false;// �ж϶�ָ̬���е�ǰ�ڵ��Ƿ�����
				if (addDynAssignPer != null && !"".equals(addDynAssignPer)) {
					String[] ids = addDynAssignPer.split("\\$");
					for (String user : ids) {
						String nodeDesc = user.substring(0, user.indexOf(":"));
						if (nextNodeDesc.equals(nodeDesc)) {
							addFlag = true;
						}
					}
				}
				/*************�õ���̨���õ���Ա����̨��ǩ���ܣ�***********************/
				//��bizParam��singerUser��ȥ��ǰ��½��
				String remainSingerUsers="";
				String singerUser = (String) bizParam.get("signerUser");
				remainSingerUsers=singerUser;
				String nowUserName=UserContext.getUserInfo().getUserName();//�õ���ǰ��½�˵�Ӣ����
				/** ****************************** */
				if (nextnodeType.indexOf("EndState") == 0) {// ��һ���ڵ��ǽ����ڵ�(nodeType��ʾ��һ���ڵ�)
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
				}else if(nextnodeType.indexOf("Node") == 0){//��һ���ڵ���Node�ڵ�ʱ
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				} else if(nextnodeType.indexOf("ProcessState") == 0){//��һ���ڵ���ProcessState�ڵ�ʱ
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if(nextnodeType.indexOf("Fork") == 0){//��һ���ڵ���Fork�ڵ�ʱ
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if(nextnodeType.indexOf("Join") == 0){//��һ���ڵ���Join�ڵ�ʱ
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if(nextnodeType.indexOf("Decision") == 0){//��һ���ڵ���Decision�ڵ�ʱ
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						throw new RuntimeException(e.getMessage());
					}
					json = "{success:true}";
				}else if (flag == true || nextUnitRole != null || addFlag==true ||dynFlag==true) {//��һ���ڵ������ý�ɫ���ж�ָ̬����ʱ
					try{
						this.continueAudit(bizParam, virtualDefinintionId, nodeId, result, taskId, nodeRoleType, remainSingerUsers,creator,processName,taskNodeName);
					}catch(Exception e){
						json = "{success:true,Exception:'" + e.getMessage() + "'}";				
						return json;
						
					}
					json = "{success:true}";
				} else if (nextUnitRole == null && flag == false && addFlag==false) {// ��һ���ڵ�û�����ý�ɫʱ��û�ж�ָ̬����ʱ���͵����Ѻ���ʾ����/* || unitRole.equals("") */
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
	 * �����Ա�������б�
	 * 
	 * @Methods Name tasks
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	private String tasks(HttpServletRequest request) {

		// ��Ҫ�Ĳ���,�г���ǰ�û����û���
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
			// ��ʵ�����ƴ����û�ϵͳ��
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
	 * Ҫ���ǵ����˵����
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
		if (userRealNames.indexOf(",") > 0) {// ��������¼���������
			userRealNames = "[" + userRealNames + "]";
		}
		return userRealNames;
	}
	/**
	 * �������̰�ť�ӿڣ�Ŀ�ľ��������̻���
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
			
			//���˼��������Ȱ�list�����һ�����󣨼���ǰ�ڵ����ȥ����ȡ��ǰһ����������һ���ڵ����Ϣ������ȡ��֮���ٰ�ǰһ��Ҳȥ��
			String vName = (String)ci.getVariable("VIRTUALDEFINITIONINFO_NAME");
			Long vProcessId = (Long)ci.getVariable("VIRTUALDEFINITIONINFO_ID");
			Map bizParam = (Map)ci.getVariable(WorkflowConstants.BUSINESS_PARAM_KEY);
			String creator= (String)ci.getVariable(WorkflowConstants.PROCESS_CREATOR_FLAG);
			String dataId = (String)bizParam.get("dataId");		
			//��ҪΪservice׼����������������ʵ��ID���ڵ�ID����������
			ProcessInstance processInstance = taskInstance.getProcessInstance();
			Long tokenId = taskInstance.getToken().getId();
			Long processId = processInstance.getId();//��ǰ����ʵ��ID
			//�õ���Ӧ�Ļ����ַ���
			List allNodeMessage = (List)ci.getVariable("goBack");//List��ÿһ���������һ��String����ʽΪparamId+nodeName��
			//��A-B-C-D,��ʱ����Ҫ��D�ص�C���Ȱѵ�ǰ�ڵ����Ϣɾ������Ȼ������һ���ڵ����Ϣ
			allNodeMessage.remove(allNodeMessage.size()-1);
			//modify by guangsa for workflowGoBackToTaskNode in 20090817 begin
			int goBackLength = allNodeMessage.size()-1;//֮ǰȥ��һ������Ҫ��һ
			Node fromNode = null;
			for(int i=0;i<goBackLength;i++){
				String  fromNodeMessage = (String)allNodeMessage.get(allNodeMessage.size()-1);
				String[] mutipleMessage = fromNodeMessage.split("\\+");
				fromParamId = mutipleMessage[0];//�ϸ��ڵ����Id
				fromNodeName = mutipleMessage[1];//�ڵ�����Ϊ���ģ������޷��õ�nodeDesc��api���ƣ�
				allNodeMessage.remove(allNodeMessage.size()-1);
				fromNode = processInstance.getProcessDefinition().getNode(fromNodeName);//���ﲻ�ÿ������̰汾���µĽڵ������ظ����⣬��Ϊ�õ��ǵ�ǰ������ʵ����
				//��ʼ����һ���ڵ��NODEID��NODENAME
				if(fromNode!=null&&!"".equals(fromNode)){
					fromNodeId = fromNode.getId();
					fromNodeName = fromNode.getName();
					fromNodeType = fromNode.toString();
					if(fromNodeType.indexOf("Node")==0){
						//�����һ���ڵ�Ϊnode���͵Ļ���������Ҫ����һ���ڵ�ȥ����������һ���ڵ�
						allNodeMessage.remove(allNodeMessage.size()-1);
						i++;
					}else{
						break;//�����ǰ�ڵ㲻��node���͵Ļ�����ֱ�ӵ�������ѭ��
					}
				}
			}
			//modify by guangsa for workflowGoBackToTaskNode in 20090817 end
			//��ʼ�ڵ���ַ�����ʽ
			String goStartState = String.valueOf(fromNodeId)+","+vProcessId+","+processId;
			//��Ҫ�Ѽ�¼������ӵ����Ϣɾ����
			WorkflowRecordTaskInfo workflowRecordTaskInfo = cs.findWorkflowRecordTaskInfo(dataId, vName);
			if(workflowRecordTaskInfo!=null&&!"".equals(workflowRecordTaskInfo)){
				service.remove(workflowRecordTaskInfo);//�����ͱ��������̻����ˣ�����ͬһ��������������Ϣ��Ψһ�Ĵ���
			}
			//���̻��˵�˼·���ǡ��Ұ�Signalling�ֶ�����Ϊfalse��������end()��ʱ��ת���ǽ�������
			if(fromNodeType.indexOf("StartState")==0){
				bizParam.put("goStartState", goStartState);//����һ������ı���
				ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
				//��Ȼ���˵���ʼ�ڵ�Ļ����൱�ڴ���ˣ���Ҫ�û������ύ������ȡ����ǰ����
				String nodeName = taskInstance.getToken().getNode().getName();
				Task task = taskInstance.getTask().getTaskNode().getTask(nodeName);
				task.setName("�������̴�ؽڵ�(��ʼ�ڵ�)");
				taskInstance.setActorId(creator);
				taskInstance.setTask(task);
				jbpmContext.save(taskInstance);
				Token oldeToken = jbpmContext.getToken(Long.valueOf(tokenId));
				if(fromNode==null){
					throw new RuntimeException("���̻���ʱ��һ���ڵ����ϢΪ��");
				}
				oldeToken.setNode(fromNode);
			}else{
				//��ǩ�ڵ����Ϊ���ǵ�
				taskInstance.setSignalling(false);//signalling����˼���ǲ��ýڵ�ת��
				taskInstance.end();
				Token oldeToken = jbpmContext.getToken(Long.valueOf(tokenId));
				if(fromNode==null){
					throw new RuntimeException("���̻���ʱ��һ���ڵ����ϢΪ��");
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
	 * ʵ�������Ĺ���������
	 * @param request
	 * @return
	 */
	private String getWorkFlowSkipGoBack(HttpServletRequest request){
		//ǰ�ڲ���׼��
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
		String nowNodeName = ti.getToken().getNode().getName();//��ǰ�ڵ�����
		Long tokenId = ti.getToken().getId();
		Long processId = ti.getContextInstance().getProcessInstance().getId();
		Node fromNode = ti.getContextInstance().getProcessInstance().getProcessDefinition().getNode(fromNodeName);
		//��ʼ����һ���ڵ��NODEID��NODENAME
		if(fromNode!=null&&!"".equals(fromNode)){
			fromNodeId = fromNode.getId();
			fromNodeName = fromNode.getName();
			fromNodeType = fromNode.toString();
		}
		String goStartState = String.valueOf(fromNodeId)+","+vProcessId+","+processId;
		List<String> allNodeMessage = (List<String>)ti.getContextInstance().getVariable("goBack");//List��ÿһ���������һ��String����ʽΪparamId+nodeName��
		//��Ҫ�Ѽ�¼������ӵ����Ϣɾ����
		WorkflowRecordTaskInfo workflowRecordTaskInfo = cs.findWorkflowRecordTaskInfo(dataId, vName);
		if(workflowRecordTaskInfo!=null&&!"".equals(workflowRecordTaskInfo)){
			service.remove(workflowRecordTaskInfo);//�����ͱ��������̻����ˣ�����ͬһ��������������Ϣ��Ψһ�Ĵ���
		}
		//��A-B-C-D,��ʱ����Ҫ��D�ص�B��������Ҫ��goBack�в���������B���������ٴν���B�ڵ��ʱ��goBack��¼Ψһ��һ��B��¼
		//��ʽ��id+nodeName��id+nodeName
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
				new RuntimeException("��������Ծ���˹��̵���goBack�������治��ȫ");
			}
			if(!mark){
				remainNodeMessage.add(allMessage);
			}
		}
		ti.getContextInstance().setVariable("goBack", remainNodeMessage);
		
		//���ʼ���̻��˵Ĺ�������Ϊ�����˵�ʱ����Ҫ������׼��
		try{
			//���̻��˵�˼·���ǡ��Ұ�Signalling�ֶ�����Ϊfalse��������end()��ʱ��ת���ǽ�������
			
			if(fromNodeType.indexOf("StartState")==0){
				bizParam.put("goStartState", goStartState);//����һ������ı���
				ci.setVariable(WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
				//��Ȼ���˵���ʼ�ڵ�Ļ����൱�ڴ���ˣ���Ҫ�û������ύ������ȡ����ǰ����
				String nodeName = ti.getToken().getNode().getName();
				Task task = ti.getTask().getTaskNode().getTask(nodeName);
				task.setName("�������̴�ؽڵ�(��ʼ�ڵ�)");
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
			new RuntimeException("��������Ծ���˹����з����쳣");
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		return null;
		
	}
	/**
	 * ��ҳ���л�����ݷ��������ĵ�map�У�����ִ������
	 * 
	 * @Methods Name getDataFromPage
	 * @Create In Mar 24, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String getDataFromPage(HttpServletRequest request) {
//		String json = "";
//		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
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
//		// ��userList��Ϣ����һ��
//
//		if (userList != null && !"".equals(userList)) {
//			userList = userListUtil(userList);
//			vm.setVariableToBizParam(taskId, "userList", userList);
//		}
		boolean flag = false;
		String json = "";
		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
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
				if(oldUsers!=null&&!"".equals(oldUsers)){//���ԭ���еĻ������ظ���ȥ��
					for(int i=0;i<oldUsers.length;i++){
						String oldDesc = oldUsers[i].substring(0,oldUsers[i].indexOf(":"));
						//modify by guangsa in 20090810 begin
						for(int j=0;j<newUsers.length;j++){
							String newDesc = newUsers[j].substring(0,newUsers[j].indexOf(":"));
							if(oldDesc.equals(newDesc)){//������ǵ�һ�α���Ļ�,����ԭ���Ĳ���
								userList = userListUtil(newUsers[j]);
								oldUsers[i] = userList;
								flag = true;
								break;
							}
						}
						//modify by guangsa in 20090810 end
					}
					if(!flag){//������ǵ�һ�ν��б���Ļ�,��Ҫ�ۼ��ַ���������ƴд�ַ���
						for(int i=0;i<oldUsers.length;i++){
							realUserList+=oldUsers[i];
							realUserList+="$";
						}
						userList = userListUtil(userList);
						realUserList+=userList;
					}else{//������ǵ�һ��,ֻ��Ҫ��ԭ�����ַ��������Ҫ�ĸ�ʽ����
						for(int i=0;i<oldUsers.length;i++){
							realUserList+=oldUsers[i];
							realUserList+="$";
						}
						if(realUserList.endsWith("$")){
							realUserList = realUserList.substring(0,realUserList.length()-1);
						}
					}
				}else{//���ԭ��û�д����ʱ�򣬰�userListֱ�ӷ���
					userList = userListUtil(userList);
					realUserList = userList;
				}
				bizParam.put("userList", realUserList);
				jbpmContext.getTaskInstance(taskId).getContextInstance().setVariable(
						WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
				//add by guangsa for avoidRepeateAddParam in 20090807 end
				
				// �ڶ�ָ̬��ҳ�洫�����������ַ�������˫����
	//			if (userList.contains("\"")) {
	//				userList = userList.replaceAll("\"", "");
	//			}
				// ��userList��Ϣ����һ��
				//userList = userListUtil(userList);
				//vm.setVariableToBizParam(taskId, "userList", userList);
			}
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		Enumeration para = request.getParameterNames();
		// �����������ҵ�����Map�ж�Ӧ���Ƶı�����������ݴ���ȥ
		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		for (; para.hasMoreElements();) {
			String varName = (String) para.nextElement();
			if (mapVar.containsKey(varName)) {
				String v = request.getParameter(varName);
				if (request.getMethod().equalsIgnoreCase("get")) {
					// �ַ���ת��
					try {// extjsת����ʽ
						v = new String(v.getBytes("iso8859-1"), "utf-8");
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				}
				vm.setVariableToBizParam(taskId, varName, v);
			}
		}
		// ����ִ������
		try{
			tm.execute(taskId);
		}catch(Exception e){
			//add by guangsa for �¼��ʼ��ظ����� in 20090908 begin
			String meg = e.getMessage();
			json = "{success:true,Exception:'"+meg+"',formName:'',nodeName:'" + ti.getNodeDisc() + "'}";// ti.getNodeName()
			return json;
			//add by guangsa for �¼��ʼ��ظ����� in 20090908 end
			//throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ҹ���Ա��ʵ��ʵ");
		}		
		json = "{success:true,formName:'',nodeName:'" + ti.getNodeDisc() + "'}";// ti.getNodeName()

		return json;
	}

	/**
	 * ��ҳ���л�����ݷ��������ĵ�map�У�������ִ������
	 * 
	 * @Methods Name getData
	 * @Create In Mar 24, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String getData(HttpServletRequest request) {
		boolean flag = false;
		String json = "";
		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
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
				if(oldUsers!=null&&!"".equals(oldUsers)){//���ԭ���еĻ������ظ���ȥ��
					for(int i=0;i<oldUsers.length;i++){
						String oldDesc = oldUsers[i].substring(0,oldUsers[i].indexOf(":"));
						//modify by guangsa in 20090810 begin
						for(int j=0;j<newUsers.length;j++){
							if(!"".equals(newUsers[j])){
								String newDesc = newUsers[j].substring(0,newUsers[j].indexOf(":"));
								if(oldDesc.equals(newDesc)){//������ǵ�һ�α���Ļ�,����ԭ���Ĳ���
									repeateUser = userListUtil(newUsers[j]);
									oldUsers[i] = repeateUser;
									flag = true;
									//modify by guangsa for �滻��������ָ̬�� in 20100706 begin 
									newUsers[j]="";
									//modify by guangsa for �滻��������ָ̬�� in 20100706 end
									break;
								}
							}
						}
						//modify by guangsa in 20090810 end
					}
					if(!flag){//������ǵ�һ�ν��б���Ļ�,��Ҫ�ۼ��ַ���������ƴд�ַ���
						for(int i=0;i<oldUsers.length;i++){
							realUserList+=oldUsers[i];
							realUserList+="$";
						}
						userList = userListUtil(userList);
						realUserList+=userList;
					}else{//������ǵ�һ��,ֻ��Ҫ��ԭ�����ַ��������Ҫ�ĸ�ʽ����
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
				}else{//���ԭ��û�д����ʱ�򣬰�userListֱ�ӷ���
					userList = userListUtil(userList);
					realUserList = userList;
				}
				bizParam.put("userList", realUserList);
				jbpmContext.getTaskInstance(taskId).getContextInstance().setVariable(
						WorkflowConstants.BUSINESS_PARAM_KEY, bizParam);
				//add by guangsa for avoidRepeateAddParam in 20090807 end
				
				// �ڶ�ָ̬��ҳ�洫�����������ַ�������˫����
	//			if (userList.contains("\"")) {
	//				userList = userList.replaceAll("\"", "");
	//			}
				// ��userList��Ϣ����һ��
				//userList = userListUtil(userList);
				//vm.setVariableToBizParam(taskId, "userList", userList);
			}
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		Enumeration para = request.getParameterNames();
		// �����������ҵ�����Map�ж�Ӧ���Ƶı�����������ݴ���ȥ
		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		for (; para.hasMoreElements();) {
			String varName = (String) para.nextElement();
			if (mapVar.containsKey(varName)) {
				String v = request.getParameter(varName);
				if (request.getMethod().equalsIgnoreCase("get")) {
					// �ַ���ת��
					try {// extjsת����ʽ
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
	 * ��������ǰ�ڵ�ָ����
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
	 * ɾ����ǩ�˼�¼
	 * @Methods Name deleteMarkUsersInfo
	 * @Create In Apr 23, 2009 By guangsa
	 * @param request
	 * @return String
	 */
	private String deleteMarkUsersInfo(HttpServletRequest request){
		String json = "";
		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();

		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String oldMarkUsers=(String)mapVar.get("addMarkUsers");//ԭ���ļ�ǩ��
		if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
			oldMarkUsers=addMarkUsersUtil(oldMarkUsers);
		}
		String nowMarkUsers = "";
		String nowAddMarkUsersId = request.getParameter("deleteMarkUsers");//��ǰ�±���ļ�ǩ��
		if (nowAddMarkUsersId != null && !"".equals(nowAddMarkUsersId)) {
			if (nowAddMarkUsersId.contains("\"")) {
				nowAddMarkUsersId = nowAddMarkUsersId.replaceAll("\"", "");
			}
			if(nowAddMarkUsersId!=null&&!"".equals(nowAddMarkUsersId)){
				String nowMarks=addMarkUsersUtil(nowAddMarkUsersId);//���û�ID���UserName
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
	 * �Ѽ�ǩ�˵���Ϣ��������ʵ�������ĵ�ҵ�������
	 * 
	 * @Methods Name addMarkUsersInfo
	 * @Create In Mar 30, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String addMarkUsersInfo(HttpServletRequest request) {
		String json = "";
		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();

		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String oldMarkUsers=(String)mapVar.get("addMarkUsers");//ԭ���ļ�ǩ��
		if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
			oldMarkUsers=addMarkUsersUtil(oldMarkUsers);
		}
		String nowAddMarkUsersId = request.getParameter("addMarkUsers");//��ǰ�±���ļ�ǩ��
		//addMarkUsers�ĸ�ʽ��taskId:userId+˳��+����,userId+˳��+����
		if (nowAddMarkUsersId != null && !"".equals(nowAddMarkUsersId)) {
//			if (nowAddMarkUsersId.contains("\"")) {
//				nowAddMarkUsersId = nowAddMarkUsersId.replaceAll("\"", "");
//			}
			//String nowMarks=addMarkUsersUtil(nowAddMarkUsersId);//�ѵ�ǰ��ǩ�˽�������
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
		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();

		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String oldMarkUsers=(String)mapVar.get("addMarkUsersToNextNode");//ԭ���ļ�ǩ��
		if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
			oldMarkUsers=addMarkUsersUtil(oldMarkUsers);
		}
		String nowMarkUsers = "";
		String nowAddMarkUsersId = request.getParameter("deleteMarkUsers");//��ǰ�±���ļ�ǩ��
		if (nowAddMarkUsersId != null && !"".equals(nowAddMarkUsersId)) {
			if (nowAddMarkUsersId.contains("\"")) {
				nowAddMarkUsersId = nowAddMarkUsersId.replaceAll("\"", "");
			}
			if(nowAddMarkUsersId!=null&&!"".equals(nowAddMarkUsersId)){
				String nowMarks=addMarkUsersUtil(nowAddMarkUsersId);//���û�ID���UserName
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
	 * ����һ���ڵ��ǩ�˵���Ϣ��������ʵ�������ĵ�ҵ�������
	 * @Methods Name addMarkUsersInfo
	 * @Create In Mar 30, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String addMarkUsersInfoToNextNode(HttpServletRequest request) {
		String json = "";
		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
		String strTaskId = request.getParameter("taskId");
		long taskId = 0;
		if (strTaskId != null && !strTaskId.equals("")) {
			taskId = Long.parseLong(strTaskId);
		}
		TaskInfo ti = tm.getTaskInfo(taskId);
		String nodeName = ti.getNodeName();
		/*****************************************************************************************/
		Map mapVar = vm.listBizVariablesByTaskId(taskId);
		String oldMarkUsers=(String)mapVar.get("addMarkUsersToNextNode");//ԭ���ļ�ǩ��
		if(oldMarkUsers!=null&&!"".equals(oldMarkUsers)){
			oldMarkUsers=addMarkUsersUtil(oldMarkUsers);
		}
		String nowAddMarkUsersId = request.getParameter("addMarkUsersToNextNode");//��ǰ�±���ļ�ǩ��
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
	 * �õ�ĳ��taskId��Ӧ�ļ�ǩ��
	 * 
	 * @Methods Name getMarkUsers
	 * @Create In Apr 2, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String getMarkUsers(HttpServletRequest request) {
		String json = "{data:[";
		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
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
							typeName = "֪ͨ����";
						} else {
							typeName = "��������";
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
	 * �õ�ĳ���ڵ����һ���ڵ��Ӧ�ļ�ǩ��
	 * 
	 * @Methods Name getMarkUsers
	 * @Create In Apr 2, 2009 By Administrator
	 * @param request
	 * @return String
	 */
	private String getMarkUsersToNextNode(HttpServletRequest request) {
		String json = "{data:[";
		// ����ʹ�ÿ���ṩ��ת�룬�����ܴ�request����ȡ���ض��Ĳ���
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
						typeName = "֪ͨ����";
					} else {
						typeName = "��������";
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
	 * �Դ�������userList��Ϣ��������һ��(��ָ̬����)
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
	 * �Դ�������addMarkUsers��Ϣ��������һ��(ָ�ɼ�ǩ��),����һ��˳��
	 * ��ǩ�ĸ�ʽaddMarkUsers: userId+˳��+����,userId+˳��+����,userId+˳��+����
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
	 * ����ʱ���л�ǩ�ͼ�ǩ�����ʱ���ȿ��ǻ�ǩ���ٿ��Ǽ�ǩ
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
		String auditFlag = (String) bizParam.get("symbol");// �����Ƿ��Ǽ�ǩ�˽���������
		String dataId = (String)bizParam.get("dataId");
		String reqClass = (String)bizParam.get("reqClass");
		String goStartState = (String)bizParam.get("goStartState");
		String workflowEntity = (String)bizParam.get("workflowHistory");
		String processId = (String)bizParam.get("processId");
		String applyType = (String)bizParam.get("applyType");
		Node node=(Node) service.find(Node.class, nodeId.toString());
		String pageUrl = PropertiesUtil.getProperties("system.mail.develop.background.link", "/servlet/getPageModel?taskId=");

		if (auditFlag == null || "".equals(auditFlag)) {
			if ("N".equals(result)) {// �Ǽ�ǩ�������ܾ�ʱ��ֱ��ִ������
				try{
					tm.execute(taskId);
				}catch(RuleFileException e){
					throw new RuleFileException(e.getMessage());
				}catch(Exception e){
					throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ");
				}				
				vm.setVariableToBizParam(taskId, "symbol", "");
				String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
				if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
					vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// ����ڵ�ǰ�ڵ����һ���ڵ������˼�ǩ��ʱ���ͼӽ�ȥ
					vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//�Ѵ����һ���ڵ��ǩ�˵ı���addMarkUsersToNextNode����Ϊ��
				}else{
					vm.setVariableToBizParam(taskId, "addMarkUsers", "");//����ڵ�ǰ�ڵ�û�и���һ���ڵ����ü�ǩ��ʱ�� �Ѵ�ż�ǩ�˵ı�������Ϊ��
				}
				
			} else if ("Y".equals(result)) {// �Ǽ�ǩ������ͬ��ʱ����ָ�ɸ���ǩ��
				//�ȿ�����ڵ��̨���õĽ�ɫ����
					if(nodeRoleType.equals(1)){//���Ƿ��м�ǩ��
						try{
						this.addMarkUsersExecute(bizParam, taskId, virtualDefinintionId, nodeId,creator,vProcessDesc,taskNodeName);
						}catch(Exception e){
							throw new RuntimeException(e.getMessage());
						}
					}else if(nodeRoleType.equals(0)){//��ǰ�ڵ������Ƕ�������ʱ
						String nowUserName=UserContext.getUserInfo().getUserName();//�õ���ǰ��½�˵�Ӣ����
						String nowUserRole="";//�õ���ǰ��½�˵���singerUser�е�һ����
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
						if(roleType.equals(1)){//��ǰ��½�˵Ľ�ɫ������һ������ʱ
							/*----------ȥ�������½�˽�ɫ�Ĳ���---------------------------*/
						   if(remainSingerUsers.indexOf(nowUserRole)==0){//����ǰ��
							   if(remainSingerUsers.contains("$")){
								   remainSingerUsers=remainSingerUsers.substring(remainSingerUsers.indexOf("$")+1);
							   }else{
								   remainSingerUsers="";
							   }
						   }else if(remainSingerUsers.endsWith(nowUserRole)){//�������
							   remainSingerUsers=remainSingerUsers.substring(0, remainSingerUsers.lastIndexOf("$"));
						   }else{//���м�ʱ
							   remainSingerUsers=remainSingerUsers.substring(0, remainSingerUsers.indexOf(nowUserRole))+remainSingerUsers.substring(remainSingerUsers.indexOf(nowUserRole)+1);
						   }
						   /*--------------�鿴ʣ��Ľ�ɫ��Ϣ----------------------------*/
						   if(remainSingerUsers.length()!=0){//��ʣ��Ľ�ɫ��Ϣʱ������ָ��
							   String remainPersons=allPersonFromAllRole(remainSingerUsers);//�ѽ�ɫ�������˶��ҳ���
							   tm.reAssign(taskId, remainPersons);
							   vm.setVariableToBizParam(taskId, "signerUser", remainSingerUsers);
						   }else{//û��ʣ��Ľ�ɫ��Ϣʱ����ȥ���Ƿ��м�ǩ��
							   this.addMarkUsersExecute(bizParam, taskId, virtualDefinintionId, nodeId,creator,vProcessDesc,taskNodeName);
						   }
						}else{//��ǰ��½�˵Ľ�ɫ�����Ƕ�������ʱ
							String remainPersonFromRole="";
							if(nowUserRole.substring(nowUserRole.indexOf(":")+1).contains("|")){//����С�|����ʶ�Ļ�����ô˵�����滹��������
								if(nowUserRole.contains("|"+nowUserName+"|")){//���м�
									remainPersonFromRole=nowUserRole.substring(0, nowUserRole.indexOf(nowUserName))+nowUserRole.substring(nowUserRole.indexOf(nowUserName)+nowUserName.length()+1);
								}else if(nowUserRole.contains("|"+nowUserName+"")){//�������
							        remainPersonFromRole=nowUserRole.substring(0, nowUserRole.lastIndexOf("|"));
							    }else{//����ǰ��
							        remainPersonFromRole=nowUserRole.substring(0, nowUserRole.indexOf(nowUserName))+nowUserRole.substring(nowUserRole.indexOf(nowUserName)+nowUserName.length()+1);
							   }
							}else{
								remainPersonFromRole=nowUserRole.substring(0, nowUserRole.indexOf(":")+1)+"";
							}
							
							if(remainPersonFromRole.substring(remainPersonFromRole.indexOf(":")+1)!=null&&!"".equals(remainPersonFromRole.substring(remainPersonFromRole.indexOf(":")+1))){//��½�˶�Ӧ�Ľ�ɫ�����滹����
								String remainPerson=remainPersonFromRole.substring(remainPersonFromRole.indexOf(":")+1);
								tm.reAssign(taskId, remainPerson);
								remainSingerUsers=remainSingerUsers.replace(nowUserRole, remainPersonFromRole);
								vm.setVariableToBizParam(taskId, "signerUser", remainSingerUsers);
							}else{//�����½�˶�Ӧ�Ľ�ɫ������û������,ȥ�������½�˽�ɫ�Ĳ���,�������¼�����ɫ���˶�ָ�ɽ���
								 if(remainSingerUsers.indexOf(nowUserRole)==0){//����ǰ��
									   if(remainSingerUsers.contains("$")){
										   remainSingerUsers=remainSingerUsers.substring(remainSingerUsers.indexOf("$")+1);
									   }else{
										   remainSingerUsers="";
									   }
								   }else if(remainSingerUsers.endsWith(nowUserRole)){//�������
									   remainSingerUsers=remainSingerUsers.substring(0, remainSingerUsers.lastIndexOf("$"));
								   }else{//���м�ʱ
									   remainSingerUsers=remainSingerUsers.substring(0, remainSingerUsers.indexOf(nowUserRole))+remainSingerUsers.substring(remainSingerUsers.indexOf(nowUserRole)+1);
								   }
								  /*--------------�鿴ʣ��Ľ�ɫ��Ϣ----------------------------*/
								   if(remainSingerUsers.length()!=0){//��ʣ��Ľ�ɫ��Ϣʱ������ָ��
									   String remainPersons=allPersonFromAllRole(remainSingerUsers);
									   tm.reAssign(taskId, remainPersons);
									   vm.setVariableToBizParam(taskId, "signerUser", remainSingerUsers);
								   }else{//û��ʣ��Ľ�ɫ��Ϣʱ����ȥ���Ƿ��м�ǩ��
									   this.addMarkUsersExecute(bizParam, taskId, virtualDefinintionId, nodeId,creator,vProcessDesc,taskNodeName);
								   }
							}
						}
				}
		  }
		} else {// �Ǽ�ǩ�˽���������
			String symbol = (String) bizParam.get("symbol");// bizParam��symbol�������Ƿ��Ǽ�ǩ���������ģ��ִ���һ����ǩ�˵�����ֵ
			String addMarkUsers = (String) bizParam.get("addMarkUsers");// ����bizParam�еļ�ǩ��
			if ("1".equals(symbol)) {// ��������ǩ����֪ͨ����
				if (addMarkUsers != null && !"".equals(addMarkUsers)) {// �����жϣ����м�ǩ��ʱ,����ָ�ɼ�ǩ��
					String userAndType = addMarkUsersUtil(addMarkUsers);//��һ��ֻ�ǰѼ�ǩ��ʽ��taskIdȥ��
					if(userAndType!=null&&!"".equals(userAndType)){
						String[] userArray = userAndType.split(",");
						String userId = userArray[0].substring(0,userArray[0].indexOf("+"));
						String typeId = userArray[0].substring(userArray[0].lastIndexOf("+") + 1);
						UserInfo userInfo = (UserInfo) service.find(
								UserInfo.class, userId);
						
						Long newTaskId = tm.addSignReAssign(taskId, userInfo.getUserName());// �˼�ǩ��ָ����֮��
						//add by guangsa for Ӧ��������ť���������˵����� in 20100309 begin
						WorkflowRecordTaskInfo currentAuditPer = cs.findWorkflowRecordByProcessId(Long.valueOf(processId));
						if(newTaskId != null&&!"".equals(newTaskId)){
							currentAuditPer.setTaskId(newTaskId);////add by debby 
						}else{
							throw new Exception("��ǩ����������ʵ��ʱ�����쳣��");
						}
						currentAuditPer.setAuditUserInfos(userInfo.getUserName());
						cs.saveWorkflowTaskInfoByEntity(currentAuditPer);
						//add by guangsa for Ӧ��������ť���������˵����� in 20100309 end
						try{
							tm.execute(taskId);
						}catch(RuleFileException e){
							throw new RuleFileException(e.getMessage());
						}catch(Exception e){
							throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ");
						}			
						// ָ����֮�󣬸������ǩ�˷��ʼ�
						String[] auditPers=new String[]{userInfo.getUserName()};
						SynchronousAction sa = new SynchronousAction(taskNodeName,pageUrl,dataId,reqClass,goStartState,Long.parseLong(processId),newTaskId,applyType, vProcessDesc, virtualDefinintionId, creator, bizParam, node, String.valueOf(nodeId),auditPers,new HashMap(),null);
						Thread t = new Thread(sa);
						t.start();
//						ConfigUnitMail unitMail = cs.findMailObjectById(
//								String.valueOf(virtualDefinintionId),
//								String.valueOf(nodeId));
//						ms.sendSimplyMail(userInfo.getEmail(), null, null,
//								unitMail == null ? "֪ͨ" : unitMail
//										.getSubject(),
//								unitMail == null ? "��������" : unitMail
//										.getContent());
						/** ***********�õ�ʣ�µļ�ǩ��************************ */
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
	
						if (userArray.length == 1) {// ���û����һ����ǩ���ˣ�������Ϊ��
							vm.setVariableToBizParam(taskId,"addMarkUsers", "");
						} else {
							vm.setVariableToBizParam(taskId,"addMarkUsers", taskid + ":"+ hasMarkUsers);
						}
						vm.setVariableToBizParam(taskId, "symbol", typeId);
					} else {// �����ж�,�޼�ǩ��ʱ����ִ������(���һ����֪ͨ���͵ļ�ǩ��ʱ������Y����
						vm.setVariableByTaskId(taskId,
								WorkflowConstants.RESULT_FLAG, "Y");
						try{
							tm.execute(taskId);
						}catch(RuleFileException e){
							throw new RuleFileException(e.getMessage());
						}catch(Exception e){
							throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ");
						}			
						vm.setVariableToBizParam(taskId, "symbol", "");
						String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
						if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
							vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// ����ڵ�ǰ�ڵ����һ���ڵ������˼�ǩ��ʱ���ͼӽ�ȥ
							vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//�Ѵ����һ���ڵ��ǩ�˵ı���addMarkUsersToNextNode����Ϊ��
						}else{
							vm.setVariableToBizParam(taskId, "addMarkUsers", "");//����ڵ�ǰ�ڵ�û�и���һ���ڵ����ü�ǩ��ʱ�� �Ѵ�ż�ǩ�˵ı�������Ϊ��
						}
					}
				} else {// �����ж�,�޼�ǩ��ʱ����ִ������(���һ����֪ͨ���͵ļ�ǩ��ʱ������Y����
					vm.setVariableByTaskId(taskId,
							WorkflowConstants.RESULT_FLAG, "Y");
					try{
						tm.execute(taskId);
					}catch(RuleFileException e){
						throw new RuleFileException(e.getMessage());
					}catch(Exception e){
						throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ");
					}			
					vm.setVariableToBizParam(taskId, "symbol", "");
					String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
					if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
						vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// ����ڵ�ǰ�ڵ����һ���ڵ������˼�ǩ��ʱ���ͼӽ�ȥ
						vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//�Ѵ����һ���ڵ��ǩ�˵ı���addMarkUsersToNextNode����Ϊ��
					}else{
						vm.setVariableToBizParam(taskId, "addMarkUsers", "");//����ڵ�ǰ�ڵ�û�и���һ���ڵ����ü�ǩ��ʱ�� �Ѵ�ż�ǩ�˵ı�������Ϊ��
					}
				}
			} else {// ��������ǩ������������
				if ("N".equals(result)) {
					try{
						tm.execute(taskId);
					}catch(RuleFileException e){
						throw new RuleFileException(e.getMessage());
					}catch(Exception e){
						throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ");
					}			
					vm.setVariableToBizParam(taskId, "symbol", "");
					String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
					if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
						vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// ����ڵ�ǰ�ڵ����һ���ڵ������˼�ǩ��ʱ���ͼӽ�ȥ
						vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//�Ѵ����һ���ڵ��ǩ�˵ı���addMarkUsersToNextNode����Ϊ��
					}else{
						vm.setVariableToBizParam(taskId, "addMarkUsers", "");//����ڵ�ǰ�ڵ�û�и���һ���ڵ����ü�ǩ��ʱ�� �Ѵ�ż�ǩ�˵ı�������Ϊ��
					}
				} else {
					if (addMarkUsers != null&& !"".equals(addMarkUsers)) {// �����жϣ����м�ǩ��ʱ,����ָ�ɼ�ǩ��
						String userAndType = addMarkUsersUtil(addMarkUsers);
						if(userAndType!=null&&!"".equals(userAndType)){
							String[] userArray = userAndType.split(",");
							String userId = userArray[0].substring(0,userArray[0].indexOf("+"));
							String typeId = userArray[0].substring(userArray[0].lastIndexOf("+") + 1);
							UserInfo userInfo = (UserInfo) service.find(UserInfo.class, userId);
							
							Long newTaskId = tm.addSignReAssign(taskId, userInfo.getUserName());// �˼�ǩ��ָ����֮��
							//add by guangsa for Ӧ��������ť���������˵����� in 20100309 begin
							WorkflowRecordTaskInfo currentAuditPer = cs.findWorkflowRecordByProcessId(Long.valueOf(processId));
							if(newTaskId != null&&!"".equals(newTaskId)){
								currentAuditPer.setTaskId(newTaskId);////add by debby 
							}else{
								throw new Exception("��ǩ����������ʵ��ʱ�����쳣��");
							}
							currentAuditPer.setAuditUserInfos(userInfo.getUserName());
							cs.saveWorkflowTaskInfoByEntity(currentAuditPer);
							//add by guangsa for Ӧ��������ť���������˵����� in 20100309 end
							try{
								tm.execute(taskId);
							}catch(RuleFileException e){
								throw new RuleFileException(e.getMessage());
							}catch(Exception e){
								throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ");
							}			
							// ָ����֮�󣬸������ǩ�˷��ʼ�
							String[] auditPers=new String[]{userInfo.getUserName()};
							SynchronousAction sa = new SynchronousAction(taskNodeName,pageUrl,dataId,reqClass,goStartState,Long.parseLong(processId),newTaskId,applyType, vProcessDesc, virtualDefinintionId, creator, bizParam, node, String.valueOf(nodeId),auditPers,new HashMap(),null);
							Thread t = new Thread(sa);
							t.start();
//							ConfigUnitMail unitMail = cs
//									.findMailObjectById(String
//											.valueOf(virtualDefinintionId),
//											String.valueOf(nodeId));
//							ms.sendSimplyMail(userInfo.getEmail(), null,
//									null, unitMail == null ? "֪ͨ"
//											: unitMail.getSubject(),
//									unitMail == null ? "��������" : unitMail
//											.getContent());
	
							/** ***********�õ�ʣ�µļ�ǩ��************************ */
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
							if (userArray.length == 1) {// ���û����һ����ǩ���ˣ�������Ϊ��
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
								throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ");
							}			
							vm.setVariableToBizParam(taskId, "symbol", "");
							String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
							if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
								vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// ����ڵ�ǰ�ڵ����һ���ڵ������˼�ǩ��ʱ���ͼӽ�ȥ
								vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//�Ѵ����һ���ڵ��ǩ�˵ı���addMarkUsersToNextNode����Ϊ��
							}else{
								vm.setVariableToBizParam(taskId, "addMarkUsers", "");//����ڵ�ǰ�ڵ�û�и���һ���ڵ����ü�ǩ��ʱ�� �Ѵ�ż�ǩ�˵ı�������Ϊ��
							}
						}
					} else {
						try{
							tm.execute(taskId);
						}catch(RuleFileException e){
							throw new RuleFileException(e.getMessage());
						}catch(Exception e){
							throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ");
						}			
						vm.setVariableToBizParam(taskId, "symbol", "");
						String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
						if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
							vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// ����ڵ�ǰ�ڵ����һ���ڵ������˼�ǩ��ʱ���ͼӽ�ȥ
							vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//�Ѵ����һ���ڵ��ǩ�˵ı���addMarkUsersToNextNode����Ϊ��
						}else{
							vm.setVariableToBizParam(taskId, "addMarkUsers", "");//����ڵ�ǰ�ڵ�û�и���һ���ڵ����ü�ǩ��ʱ�� �Ѵ�ż�ǩ�˵ı�������Ϊ��
						}
					}
				}
			}
		}
	}
	
	/**
	 * ���߷���
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
	 * ���Ƿ��м�ǩ�ˣ��ɼ�ǩ��ִ��
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

		//add by gaowen in 20091125 for ���ʼ�������ʽ
		UserInfo creatorMeg = (UserInfo)service.findUnique(UserInfo.class, "userName", creator);
		//add by guangsa in 20090720 for sendMailContext end
		String addMarkUsers = (String) bizParam.get("addMarkUsers");//����bizParam�еļ�ǩ��
		//��ǩ�ĸ�ʽaddMarkUsers: taskId:userId+˳��+����,userId+˳��+����,userId+˳��+����
		if (addMarkUsers != null && !"".equals(addMarkUsers)) {
			//String userAndType = addMarkUsersUtil(addMarkUsers);//���ڼ�ǩ����һ��˳��
			String nowMarks = addMarkUsers.substring(addMarkUsers.indexOf(":")+1);
			if(nowMarks!=null&&!"".equals(nowMarks)){
				String[] userArray = nowMarks.split(",");
				String userId = userArray[0].substring(0,userArray[0].indexOf("+"));
				String typeId = userArray[0].substring(userArray[0].lastIndexOf("+") + 1);
				UserInfo userInfo = (UserInfo) service.find(UserInfo.class, userId);
				
				Long newTaskId = tm.addSignReAssign(taskId, userInfo.getUserName());// �˼�ǩ��ָ����֮��
				//add by guangsa for Ӧ��������ť���������˵����� in 20100309 begin
				WorkflowRecordTaskInfo currentAuditPer = cs.findWorkflowRecordByProcessId(Long.valueOf(processId));
				if(newTaskId != null&&!"".equals(newTaskId)){
					currentAuditPer.setTaskId(newTaskId);////add by debby 
				}else{
					throw new Exception("��ǩ����������ʵ��ʱ�����쳣��");
				}
				currentAuditPer.setAuditUserInfos(userInfo.getUserName());
				cs.saveWorkflowTaskInfoByEntity(currentAuditPer);
				//add by guangsa for Ӧ��������ť���������˵����� in 20100309 end
				tm.execute(taskId);
				/**************************************************************************************************************/
				//add by guangsa for ҳ������ in 20090818 begin
				WorkflowRecordTaskInfo recordTaskInfo = vm.findWorkflowRecordTaskInfoById(Long.valueOf(processId), nodeId);//  virtualDefinintionId
				recordTaskInfo.setAuditUserInfos(userInfo.getUserName());
				service.save(recordTaskInfo);
				//add by guangsa for ҳ������ in 20090818 end
				/**************************************************************************************************************/
				// ָ����֮�󣬸������ǩ�˷��ʼ�
				String pageUrl = PropertiesUtil.getProperties("system.mail.develop.background.link", "/servlet/getPageModel?taskId=");
				List auditHis = cs.findAllWorkflowHistoryMessage(workflowEntity, Long.valueOf(processId));//���ҳ����������еİ�����˳�����еĽڵ���Ϣ
				
				String content = cs.htmlContent(taskNodeName,pageUrl,applyType,dataId, reqClass, goStartState, newTaskId, creator, vDesc, auditHis,"0",false);
				//add By gaowen for ��ǩ�ʼ�ģ���޸� in 2009-12-1 begin
//				JbpmContext jbpmContext = JbpmContextFactory.getJbpmContext();
//				TaskInstance taskInstance = null;
//					try{
//						taskInstance = jbpmContext.loadTaskInstance(taskId);
//						Map variables = taskInstance.getContextInstance().getVariables();
//						// �����TASKINFO,���������ѭ�����⡣
//						if (variables.containsKey(WorkflowConstants.TASKINFO_KEY)) {
//							variables.remove(WorkflowConstants.TASKINFO_KEY);
//						}
//						JSONObject jo = JSONObject.fromObject(variables);
//						String result = (String) jo.get(WorkflowConstants.RESULT_FLAG);
//						String comment = (String) jo.get(WorkflowConstants.COMMENT_FLAG);
//						cs.saveWorkflowHistoryAuditHis(workflowEntity, processId,dataId,reqClass,taskNodeName,nodeId.toString(),serviceItem,result,comment);
//					 }catch(Exception e){
//						 throw new RuntimeException("�����ǩ������ʷʧ�ܣ����ʵ");
//					 }
						
					
					/** **********�õ�results***************** */
					
				
				String[] auditPers=new String[]{userInfo.getUserName()};
				SynchronousAction sa = new SynchronousAction(taskNodeName,pageUrl,dataId,reqClass,goStartState,Long.parseLong(processId),taskId,applyType, vDesc, virtualDefinintionId, creator, bizParam, node, String.valueOf(nodeId),auditPers,new HashMap(),null);
				Thread t = new Thread(sa);
				t.start();
			//add By gaowen for ��ǩ�ʼ�ģ���޸� in 2009-12-1 end
//				ConfigUnitMail unitMail = cs.findMailObjectById(
//						String.valueOf(virtualDefinintionId),
//						String.valueOf(nodeId));
//				
//				ms.sendSimplyMail(userInfo.getEmail(), null, null,
//						unitMail == null ? "����֪ͨ" : unitMail
//								.getSubject(),
//						unitMail == null ? content : unitMail
//								.getContent());
				

				/** ***********�õ�ʣ�µļ�ǩ��*************************/
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
				if (userArray.length == 1) {// ���û����һ����ǩ���ˣ�������Ϊ��
					vm.setVariableToBizParam(taskId,"addMarkUsers", "");
				} else {
					vm.setVariableToBizParam(taskId,"addMarkUsers", taskid + ":"+ hasMarkUsers);
				}
				vm.setVariableToBizParam(taskId, "symbol", typeId);
			
			}else {// û�м�ǩ��ʱ����ֱ��ִ������
				try{
					tm.execute(taskId);
				}catch(Exception e){
					throw new RuntimeException("��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ");
				}		
				vm.setVariableToBizParam(taskId, "symbol", "");
				String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
				if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
					vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// ����ڵ�ǰ�ڵ����һ���ڵ������˼�ǩ��ʱ���ͼӽ�ȥ
					vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//�Ѵ����һ���ڵ��ǩ�˵ı���addMarkUsersToNextNode����Ϊ��
				}else{
					vm.setVariableToBizParam(taskId, "addMarkUsers", "");//����ڵ�ǰ�ڵ�û�и���һ���ڵ����ü�ǩ��ʱ�� �Ѵ�ż�ǩ�˵ı�������Ϊ��
				}
			}
	} else {// û�м�ǩ��ʱ����ֱ��ִ������
			try{
				tm.execute(taskId);
			}catch(Exception e){
				throw new RuntimeException(e.getMessage());//"��ִ������ʱ�����쳣����������Ƕ�ȡ�����ļ�ʱ�����쳣�����ʵ"
			}		
			vm.setVariableToBizParam(taskId, "symbol", "");
			String addMarkUsersToNextNode=(String)bizParam.get("addMarkUsersToNextNode");
			if(addMarkUsersToNextNode!=null&&!"".equals(addMarkUsersToNextNode)){
				vm.setVariableToBizParam(taskId, "addMarkUsers", addMarkUsersToNextNode);// ����ڵ�ǰ�ڵ����һ���ڵ������˼�ǩ��ʱ���ͼӽ�ȥ
				vm.setVariableToBizParam(taskId, "addMarkUsersToNextNode", "");//�Ѵ����һ���ڵ��ǩ�˵ı���addMarkUsersToNextNode����Ϊ��
			}else{
				vm.setVariableToBizParam(taskId, "addMarkUsers", "");//����ڵ�ǰ�ڵ�û�и���һ���ڵ����ü�ǩ��ʱ�� �Ѵ�ż�ǩ�˵ı�������Ϊ��
			}
		}
   
	}
	
	/**
	 * ͨ��������������ҵ�����ݵõ�Ψһһ���ڵ���Ϣ��ͨ���ڵ���Ϣ�õ���Ӧ����ڵ���Ϣ
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
	 * �õ�ĳ���������нڵ���Ϣ
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
	 * �����̻ص���ʼ�ڵ��Ժ󣬲���Ҫ�����ύ�����
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
	 * �����̻ص���ʼ�ڵ��Ժ���Ҫ�����ύ�����;��ʱ��Ҫ�ѵ�ǰ���̽�������Ȼ���ٿ���һ��������
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
				//������ʽ��{key+value;key+value;key+value;+key+value}
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
			ps.createProcess(vName, creator, startNodeBizParam);//����û���ص���ʼ�ڵ㣬��˵���û������·�������,����ԭ�ȱ���Ľڵ������ϢҪɾ����
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			JbpmContextFactory.closeJbpmContext();
		}
		json = "{success:true}";
		return json;
	}
}
