package com.zsgj.itil.knowledge.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.zsgj.info.framework.context.ContextHolder;
import com.zsgj.info.framework.context.UserContext;
import com.zsgj.info.framework.dao.BaseObject;
import com.zsgj.info.framework.security.entity.UserInfo;
import com.zsgj.info.framework.service.Service;
import com.zsgj.info.framework.util.DateUtil;
import com.zsgj.info.framework.web.adapter.struts2.BaseAction;
import com.zsgj.info.framework.workflow.ContextService;
import com.zsgj.info.framework.workflow.ParameterService;
import com.zsgj.info.framework.workflow.ProcessService;
import com.zsgj.info.framework.workflow.TaskService;
import com.zsgj.info.framework.workflow.info.HistoryInfo;
import com.zsgj.info.framework.workflow.info.TaskInfo;
import com.zsgj.itil.actor.entity.SupportGroup;
import com.zsgj.itil.actor.entity.SupportGroupEngineer;
import com.zsgj.itil.knowledge.entity.KnowContract;
import com.zsgj.itil.knowledge.entity.KnowFile;
import com.zsgj.itil.knowledge.entity.Knowledge;
import com.zsgj.itil.knowledge.entity.KnowledgeType;
import com.zsgj.itil.knowledge.service.KnowledgeService;

public class KnowledgeManagerAction extends BaseAction {
	private TaskService ts = (TaskService) ContextHolder.getBean("taskService");
	private ParameterService pms = (ParameterService) ContextHolder.getBean("parameterService");
	private Service service = (Service) ContextHolder.getBean("baseService");
//	private ContextService vm = (ContextService) ContextHolder.getBean("contextService");
//	private TaskService tm = (TaskService) ContextHolder.getBean("taskService");
	private ProcessService ps = (ProcessService) ContextHolder.getBean("processService");
	private KnowledgeService knowService = (KnowledgeService) getBean("KnowledgeService");

	/**
	 * ������루������������,��Ҫ���ǵ�һ���ڵ��п���ָ�ɸ����˵���� ֧�ֵ��ڵ㵥������ָ�ɣ���ʽΪa|b|c,
	 * 
	 * @Methods Name apply
	 * @Create In Sep 9, 2008 By yang
	 * @param request
	 * @return
	 * @ReturnType String
	 */
	@SuppressWarnings("unchecked")
	public String apply() throws Exception {
		String json = "";
		// ��Ҫ�Ĳ���
		String definitionName = super.getRequest().getParameter("defname");
		String buzzParameters = super.getRequest().getParameter("bzparam");// ��ajax�����Ѿ���js��������json�ַ���
		String dataId = super.getRequest().getParameter("dataId");// ������id
//		String departmentCode = super.getRequest().getParameter("deptcode");
//		String userAssign = super.getRequest().getParameter("userAssign");
		String createUser = super.getRequest().getParameter("createUser");
		BaseObject baseObject = null;
		Class classInstance = null;
		// ��Ҫ���������ĵ�ҵ�����
		Map mapBizz = new HashMap();
		if (buzzParameters != null && !buzzParameters.equals("")) {
			JSONObject jo = JSONObject.fromObject(buzzParameters);
			Iterator it = jo.keys();
			while (it.hasNext()) {
				String key = (String) it.next();
				String value = (String) jo.get(key);
				mapBizz.put(key, value);
			}

			// add by awen for add two param in bizMap on 2009-08-23 begin
			KnowledgeType knowledgeType = (KnowledgeType) service.find(KnowledgeType.class, (String) mapBizz
					.get("dataType"), true);
			String clazz = knowledgeType.getClassName();
			classInstance = Class.forName(clazz);
			baseObject = (BaseObject) service.find(classInstance, dataId, true);
			String applyName = "";
			String applyNum = "";
			if (baseObject != null && baseObject instanceof KnowFile) {
				applyName = ((KnowFile) baseObject).getName();
				applyNum = ((KnowFile) baseObject).getNumber();
			} else if (baseObject != null && baseObject instanceof Knowledge) {
				applyName = ((Knowledge) baseObject).getSummary();
				applyNum = ((Knowledge) baseObject).getKnowledgeCisn();
			} else if (baseObject != null && baseObject instanceof KnowContract) {
				applyName = ((KnowContract) baseObject).getName();
				applyNum = ((KnowContract) baseObject).getNumber();
			}
			mapBizz.put("applyName", applyName);
			mapBizz.put("applyNum", applyNum);
			// add by awen for add two param in bizMap on 2009-08-23 begin

		}
		String dataType = (String) mapBizz.get("dataType");

		KnowledgeType knowledgeType = (KnowledgeType) super.getService().find(KnowledgeType.class, dataType);
		mapBizz.put("workflowHistory", "com.zsgj.itil.knowledge.entity.KnowledgeAuditHis");
		UserInfo userInfo = null;
		if (createUser != null) {
			userInfo = new UserInfo();
			userInfo.setId(Long.parseLong(createUser));
		} else {
			userInfo = UserContext.getUserInfo(); // ��ȡ��ǰ��¼��
		}
		//2010-06-23 modified by huzh for bug(Ҫ������ɾ����֧����) begin
//		List<SupportGroupEngineer> engineerList = super.getService().find(SupportGroupEngineer.class, "userInfo",
//				userInfo);
		List<SupportGroupEngineer> engineerList = knowService.findSupportGroupByEngineer(userInfo);
		//2010-06-23 modified by huzh for bug(Ҫ������ɾ����֧����) end
		String error = "";

		if ("com.zsgj.itil.knowledge.entity.Knowledge".equals(knowledgeType.getClassName())) {

			if (engineerList.size() > 0) {
				SupportGroupEngineer engineer = engineerList.get(0);
				if (engineer != null) {
					SupportGroup supportGroup = engineer.getSupportGroup();// ��ȡ����ʦ��Ӧ֧����
					if (supportGroup != null) {
						UserInfo solutioner = supportGroup.getGroupSolutioner();// ��ȡ�������������
						if (solutioner != null) {
							String userListStr = "knowledgeApproval:" + solutioner.getUserName();
							mapBizz.put("userList", userListStr);// ָ��֧���������˽ڵ������û�
						} else {
							error = "δ�ҵ�ָ��������������ˣ�";
						}
					} else {
						error = "δ�ҵ�����ʦ����֧���飡";
					}
				} else {
					error = "��֧���鹤��ʦ�����ύ����������룡";
				}
			} else {
				error = "��֧���鹤��ʦ�����ύ����������룡";
			}
		} else if ("com.zsgj.itil.knowledge.entity.KnowFile".equals(knowledgeType.getClassName())) {
			mapBizz.put("workflowHistory", "com.zsgj.itil.knowledge.entity.KnowFileAuditHis");
			if (engineerList.size() > 0) {
				SupportGroupEngineer engineer = engineerList.get(0);
				if (engineer != null) {
					SupportGroup supportGroup = engineer.getSupportGroup();// ��ȡ����ʦ��Ӧ֧����
					if (supportGroup != null) {
						UserInfo Contracter = supportGroup.getGroupContractOrFileer();// ��ȡ��ͬ/�ļ�������
						if (Contracter != null) {
							String userListStr = "fileApproval:" + Contracter.getUserName()+"$serviceDeptServiceStationApproval:"+Contracter.getUserName();
							mapBizz.put("userList", userListStr);// ָ��֧���������˽ڵ������û�
						} else {
							error = "δ�ҵ�ָ���ļ������ˣ�";
						}
					} else {
						error = "δ�ҵ�����ʦ����֧���飡";
					}
				} else {
					error = "��֧���鹤��ʦ�����ύ�ļ����룡";
				}
			} else {
				error = "��֧���鹤��ʦ�����ύ�ļ����룡";
			}
		} else {
			mapBizz.put("workflowHistory", "com.zsgj.itil.knowledge.entity.KnowContractAuditHis");
			if (engineerList.size() > 0) {
				SupportGroupEngineer engineer = engineerList.get(0);
				if (engineer != null) {
					SupportGroup supportGroup = engineer.getSupportGroup();// ��ȡ����ʦ��Ӧ֧����
					if (supportGroup != null) {
						UserInfo filer = supportGroup.getGroupContractOrFileer();// ��ȡ��ͬ/�ļ�������
						if (filer != null) {
							String userListStr = "contractApproval:" + filer.getUserName()+"$serviceDeptThreeStationApproval:"+filer.getUserName();
							mapBizz.put("userList", userListStr);// ָ��֧���������˽ڵ������û�
						} else {
							error = "δ�ҵ�ָ����ͬ�����ˣ�";
						}
					} else {
						error = "δ�ҵ�����ʦ����֧���飡";
					}
				} else {
					error = "��֧���鹤��ʦ�����ύ��ͬ���룡";
				}
			} else {
				error = "��֧���鹤��ʦ�����ύ��ͬ���룡";
			}
		}
		String creator;
		if(createUser!=null)
			creator=((UserInfo)getService().find(UserInfo.class, createUser)).getUserName();
		else
			creator = UserContext.getUserInfo().getUserName();
		Long instanceId = null;
		String meg = "";
		try {
			if (!error.equals("")) {
				json = "{success:true,Exception:'" + error + "'}";
			} else {
				instanceId = ps.createProcess(definitionName, creator, mapBizz);
				json = "{success:true,id:'" + instanceId + "'}";
				// add by awen for change the status of knowledge on 2009-08-23
				// begin
				Method setStatus = classInstance.getMethod("setStatus", java.lang.Integer.class);
				setStatus.invoke(baseObject, new Integer("2"));
				// add by awen for change the status of knowledge on 2009-08-23
				// end
			}
		} catch (Exception e) {
			meg = e.getMessage();
			json = "{success:true,Exception:'" + meg + "'}";
		}
		try {
			super.getResponse().setContentType("text/plain");
			super.getResponse().setCharacterEncoding("utf-8");
			PrintWriter pw = super.getResponse().getWriter();
			pw.write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * ���ݲ�ͬ����������ʾ��ͬ�������б�
	 * 
	 * @Methods Name tasks
	 * @Create In Mar 6, 2009 By guangsa
	 * @return
	 * @throws Exception
	 *             String
	 */
	public String tasks() throws Exception {
		HttpServletRequest request = super.getRequest();
		// ��Ҫ�Ĳ���
		String actor = request.getParameter("actorId");
		// String actor = UserContext.getUserInfo().getUserName();
		String json = "";

		int rowCount = 0;
		List<TaskInfo> list = ts.listTasks(actor);
		for (TaskInfo taskInfo : list) {
			String str = "";
			str += "defname:'" + taskInfo.getDefinitionName() + "',";
			str += "defdesc:'" + taskInfo.getDefinitionDesc() + "',";
			str += "nodeName:'" + taskInfo.getNodeName() + "',";
			str += "taskId:'" + taskInfo.getId() + "',";
			str += "taskName:'" + taskInfo.getName() + "',";
			// ��ʵ�����ƴ����û�ϵͳ��
			// String realName = getUserRealNameByName(taskInfo.getActorId());
			// str += "actorId:'"+realName+"',";
			str += "startDate:'" + toBlank(taskInfo.getStart()) + "',";
			Map bizParams = pms.listVariablesByProcessId(taskInfo.getProcessId());
			// String eventName = (String)bizParams.get("eventName");
			// if(eventName == null || "null".equalsIgnoreCase(eventName)){
			// bizParams.put("eventName", "δ����");
			// }

			// add by lee for ����û�Ҫ����ʾ��Ϣ in 20090914 begin
			String dataType = (String) bizParams.get("dataType");
			String applyId = (String) bizParams.get("applyId");
			if (dataType != null) {
				if (dataType.equals("6")) {// ��ͬ����
					KnowContract kc = (KnowContract) service.find(KnowContract.class, applyId);
					str += "applyName:'" + kc.getName() + "',";
					str += "applyNum:'" + kc.getNumber() + "',";
					str += "applyUser:'" + kc.getCreateUser().getRealName() + "/" + kc.getCreateUser().getUserName()
							+ "',";
					str += "applyDate:'" + DateUtil.convertDateTimeToString(kc.getCreateDate()).substring(0, 16) + "',";//2010-05-17 modified by huzh for ��ʾʱ��
				} else if (dataType.equals("7")) {// �ļ�����
					KnowFile kf = (KnowFile) service.find(KnowFile.class, applyId);
					str += "applyName:'" + kf.getName() + "',";
					str += "applyNum:'" + kf.getNumber() + "',";
					str += "applyUser:'" + kf.getCreateUser().getRealName() + "/" + kf.getCreateUser().getUserName()
							+ "',";
					str += "applyDate:'" + DateUtil.convertDateTimeToString(kf.getCreateDate()).substring(0, 16) + "',";//2010-05-17 modified by huzh for ��ʾʱ��
				} else if (dataType.equals("8")) {// �����������
					Knowledge kl = (Knowledge) service.find(Knowledge.class, applyId);
					str += "applyName:'" + kl.getName() + "',";
					str += "applyNum:'" + kl.getKnowledgeCisn() + "',";
					str += "applyUser:'" + kl.getCreateUser().getRealName() + "/" + kl.getCreateUser().getUserName()
							+ "',";
					str += "applyDate:'" + DateUtil.convertDateTimeToString(kl.getCreateDate()).substring(0, 16) + "',";//2010-05-17 modified by huzh for ��ʾʱ��
				}
			}
			// add by lee for ����û�Ҫ����ʾ��Ϣ in 20090914 end

			String applyTypeString = (String) bizParams.get("applyType");
			JSONObject jo = JSONObject.fromObject(bizParams);
			String strBizParams = jo.toString();
			strBizParams = strBizParams == null || strBizParams.equals("null") ? "''" : strBizParams;
			if (strBizParams.startsWith("{")) {
				strBizParams = strBizParams.substring(1);
			}
			if (strBizParams.endsWith("}")) {
				strBizParams = strBizParams.substring(0, strBizParams.length() - 1);
			}

			str += strBizParams + ",";
			str += "comments:'" + toBlank(taskInfo.getComments().getValue("comment")) + "'";
			str = "{" + str + "},";
			if ("kproject".equals(applyTypeString)) {
				json += str;
				rowCount++;
			}
		}
		json = deleteComma(json);
		json = "{success: true, rowCount:'" + rowCount + "',data:[" + json + "]}";

		HttpServletResponse res = super.getResponse();
		res.setContentType("text/plain");
		res.setCharacterEncoding("utf-8");
		PrintWriter pw = res.getWriter();
		pw.write(json);
		return null;
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
	@SuppressWarnings("unused")
	private String next() throws Exception {
		// ��Ҫ�Ĳ���
		String taskId = super.getRequest().getParameter("taskid");
		String procId = super.getRequest().getParameter("procid");
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
			// String realName =
			// getUserRealNameByName(historyInfo.getActorId());
			// str += "actorId:'"+realName+"',";
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

	private String toBlank(Object o) {
		return o == null ? "" : (String) o;
	}

	private String deleteComma(String json) {
		if (json.endsWith(",")) {
			json = json.substring(0, json.length() - 1);
		}
		return json;
	}
	// /**
	// * Ҫ���ǵ����˵����
	// * @Methods Name getUserRealNameByName
	// * @Create In Nov 19, 2008 By yang
	// * @param userName
	// * @return
	// * @ReturnType String
	// */
	// private String getUserRealNameByName(String userName) {
	// String userRealNames = "";
	// String[] userNames = null;
	// if(userName.indexOf('|')>0) {
	// userNames = userName.split("\\|");
	// }
	// else {
	// userNames = new String[]{userName};
	// }
	// for(String userNameItem: userNames) {
	// List<UserInfo> users = service.find(UserInfo.class, "userName",
	// userNameItem);
	// if(users!=null&&!users.isEmpty()) {
	// userRealNames += users.get(0).getRealName()+",";
	// }
	// else {
	// userRealNames += userNameItem+",";
	// }
	// }
	// if(userRealNames.endsWith(",")) {
	// userRealNames = userRealNames.substring(0,userRealNames.length()-1);
	// }
	// if(userRealNames.indexOf(",")>0) {//��������¼���������
	// userRealNames = "["+userRealNames+"]";
	// }
	// return userRealNames;
	// }

}
