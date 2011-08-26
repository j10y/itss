package com.xpsoft.webservice.service.flow.Impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.velocity.tools.generic.DateTool;
import org.jbpm.api.ProcessInstance;
import org.jbpm.pvm.internal.model.Transition;
import org.springframework.security.AuthenticationManager;
import org.springframework.security.context.SecurityContext;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.providers.UsernamePasswordAuthenticationToken;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xpsoft.core.command.QueryFilter;
import com.xpsoft.core.jbpm.pv.ParamField;
import com.xpsoft.core.jbpm.pv.TaskInfo;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.action.flow.FlowRunInfo;
import com.xpsoft.oa.action.flow.ProcessActivityAssistant;
import com.xpsoft.oa.model.archive.ArchDispatch;
import com.xpsoft.oa.model.archive.ArchRecFiledType;
import com.xpsoft.oa.model.archive.ArchRecType;
import com.xpsoft.oa.model.archive.ArchRecUser;
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.archive.ArchivesAttend;
import com.xpsoft.oa.model.archive.ArchivesDep;
import com.xpsoft.oa.model.archive.ArchivesDoc;
import com.xpsoft.oa.model.archive.ArchivesHandle;
import com.xpsoft.oa.model.archive.LeaderRead;
import com.xpsoft.oa.model.flow.FormData;
import com.xpsoft.oa.model.flow.ProDefinition;
import com.xpsoft.oa.model.flow.ProcessForm;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.flow.Transform;
import com.xpsoft.oa.model.info.ShortMessage;
import com.xpsoft.oa.model.personal.ErrandsRegister;
import com.xpsoft.oa.model.personal.LeaveLeaderRead;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import com.xpsoft.oa.service.archive.ArchDispatchService;
import com.xpsoft.oa.service.archive.ArchRecFiledTypeService;
import com.xpsoft.oa.service.archive.ArchRecTypeService;
import com.xpsoft.oa.service.archive.ArchRecUserService;
import com.xpsoft.oa.service.archive.ArchUnderTakesService;
import com.xpsoft.oa.service.archive.ArchivesAttendService;
import com.xpsoft.oa.service.archive.ArchivesDepService;
import com.xpsoft.oa.service.archive.ArchivesHandleService;
import com.xpsoft.oa.service.archive.ArchivesService;
import com.xpsoft.oa.service.archive.LeaderReadService;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProcessFormService;
import com.xpsoft.oa.service.flow.ProcessRunService;
import com.xpsoft.oa.service.flow.TaskService;
import com.xpsoft.oa.service.info.ShortMessageService;
import com.xpsoft.oa.service.personal.ErrandsRegisterService;
import com.xpsoft.oa.service.personal.LeaveLeaderReadService;
import com.xpsoft.oa.service.system.AppUserService;
import com.xpsoft.oa.service.system.DepartmentService;
import com.xpsoft.webservice.service.flow.FlowService;

public class FlowServiceImpl implements FlowService {
	private String activityName;
	private String taskId;
	private Map parmap = new HashMap();

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	private String getType(String type) {
		if (type.equals("doc") || type.equals("docx")) {
			return "0";
		} else if (type.equals("xls")) {
			return "1";
		} else if (type.equals("ppt")) {
			return "2";
		} else if (type.equals("pdf")) {
			return "3";
		} else if (type.equals("rar")) {
			return "5";
		} else if (type.equals("txt")) {
			return "6";
		} else if (type.equals("jpg")) {
			return "7";
		} else {
			return "4";
		}
	}

	public String filter(String userId, String passwd) {
		AppUserService userService = (AppUserService) AppUtil
				.getBean("appUserService");
		AppUser user = userService.get(Long.parseLong(userId));
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
				user.getUsername(), passwd);
		SecurityContext securityContext = SecurityContextHolder.getContext();
		AuthenticationManager authenticationManager = (AuthenticationManager) AppUtil
				.getBean("authenticationManager");
		securityContext.setAuthentication(authenticationManager
				.authenticate(authRequest));
		SecurityContextHolder.setContext(securityContext);
		return null;
	}

	public String getDycyList(String userId, String passwd) {
		TaskService flowTaskService = (TaskService) AppUtil
				.getBean("flowTaskService");
		filter(userId, passwd);
		PagingBean pb = new PagingBean(0, 999999);
		List<TaskInfo> tasks = flowTaskService.getTaskInfosByUserId(userId, pb);
		List<Map> dycylist = new ArrayList();
		for (TaskInfo task : tasks) {
			if (task.getTaskName().equals("科室主任传阅")) {
				Map map = new HashMap();
				map.put("taskid", task.getTaskId());
				map.put("taskName", task.getTaskName());
				map.put("nodeName", task.getActivityName());
				map.put("author", task.getAssignee());
				map.put("createDate", task.getCreateTime() != null ? task
						.getCreateTime().toString().substring(0, 19) : "");
				dycylist.add(map);
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,").append("data:");
		Gson gson = new Gson();
		buff.append(gson.toJson(dycylist));
		buff.append("}");
		return buff.toString();
	}

	public String getDbsxList(String userId, String passwd) {
		TaskService flowTaskService = (TaskService) AppUtil
				.getBean("flowTaskService");
		filter(userId, passwd);
		PagingBean pb = new PagingBean(0, 999999);
		List<TaskInfo> tasks = flowTaskService.getTaskInfosByUserId(userId, pb);
		List<Map> dycylist = new ArrayList();
		for (TaskInfo task : tasks) {
			if (!task.getTaskName().equals("科室主任传阅")) {
				Map map = new HashMap();
				map.put("taskid", task.getTaskId());
				map.put("taskName", task.getTaskName());
				map.put("nodeName", task.getActivityName());
				map.put("author", task.getAssignee()!=null?task.getAssignee().toString():"");
				map.put("createDate", task.getCreateTime() != null ? task
						.getCreateTime().toString().substring(0, 19) : "");
				dycylist.add(map);
			}
		}
		StringBuffer buff = new StringBuffer("{success:true,").append("data:");
		Gson gson = new Gson();
		buff.append(gson.toJson(dycylist));
		buff.append("}");
		return buff.toString();
	}

	public String getDbsxDetail(String userId, String passwd,
			String activityName, String taskId) {
		filter(userId, passwd);
		this.setActivityName(activityName);
		this.setTaskId(taskId);
		this.parmap.put("activityName", activityName);
		this.parmap.put("taskId", taskId);
		JbpmService jbpmService = (JbpmService) AppUtil.getBean("jbpmService");
		ProcessRunService processRunService = (ProcessRunService) AppUtil
				.getBean("processRunService");
		ProcessFormService processFormService = (ProcessFormService) AppUtil
				.getBean("processFormService");
		ArchivesService archivesService = (ArchivesService) AppUtil
				.getBean("archivesService");
		ProDefinition proDefinition = getProDefinition();
		String processName = proDefinition.getName();

		if (StringUtils.isEmpty(this.activityName)) {
			this.activityName = jbpmService.getStartNodeName(proDefinition);
		}

		Map model = new HashMap();
		Map formDataMap = null;
		Map fieldsMap = ProcessActivityAssistant.constructFieldMap(processName,
				this.activityName);
		Iterator fieldNames = fieldsMap.keySet().iterator();
		while (fieldNames.hasNext()) {
			String fieldName = (String) fieldNames.next();
			if (formDataMap != null) {
				Object fieldVal = formDataMap.get(fieldName);
				model.put(fieldName, fieldVal);
			}
			if (!model.containsKey(fieldName)) {
				model.put(fieldName, "");
			}
		}
		List<ProcessForm> pformlist = null;
		Transform tf = null;
		if (this.taskId != null) {
			ProcessRun processRun = processRunService.getByTaskId(this.taskId
					.toString());
			Map processRunVars = processFormService.getVariables(processRun
					.getRunId());
			pformlist = processFormService.getByRunId(processRun.getRunId());
			List<Transition> trans = jbpmService
					.getTransitionsByTaskId(this.taskId.toString());
			List allTrans = new ArrayList();
			for (Transition tran : trans) {
				allTrans.add(new Transform(tran));
			}
			tf = (Transform) allTrans.get(0);
			model.putAll(processRunVars);
			model.put("nextTrans", allTrans);
		}
		String json = "{success:true,";
		// ”0”,(0发文流程，1收文流程，2请假流程)
		Gson gson = new Gson();
		if (processName.equals("发文流程") || processName.equals("请示报告")
				|| processName.equals("发文流程-市局发文")) {
			String id = model.get("archives_archivesId").toString();
			Archives archives = archivesService.get(Long.parseLong(id));
			json += "title:\"" + archives.getSubject() + "\",";
			json += "createDate:\"" + archives.getCreatetime() + "\",";
			json += "id:\"" + id + "\",";
			json += "activityName: \"" + this.activityName + "\",";
			json += "taskId: \"" + this.taskId + "\",";
			json += "signalName: \"" + tf.getName() + "\",";
			json += "type:\"0\",";
			json += "boxstatus:false,";
			json += "userquery:false,";
			json += "showgdlx:false,";
			json += "showBack:true,";
			if(activityName.equals("编号录入")){
				json += "showbh:true,";
			}else{
				json += "showbh:false,";
			}
			json += "isdx:false,";
			json += "data:[";
			json += "{label:\"来文文字号\",value:\"" + archives.getArchivesNo()
					+ "\"},";
			json += "{label:\"发文人\",value:\"" + archives.getIssuer() + "\"},";
			json += "{label:\"发文机关\",value:\"" + archives.getIssueDep()
					+ "\"},";
			json += "{label:\"来文类型\",value:\"" + archives.getTypeName()
					+ "\"},";
			json += "{label:\"主题词\",value:\"" + archives.getKeywords() + "\"},";
			json += "{label:\"公文来源\",value:\"" + archives.getSources() + "\"},";
			json += "{label:\"紧急程度\",value:\"" + archives.getUrgentLevel()
					+ "\"},";
			json += "{label:\"秘密等级\",value:\"" + archives.getPrivacyLevel()
					+ "\"},";
			json += "{label:\"签收部门\",value:\"" + archives.getRecDepNames()
					+ "\"},";
			json += "{label:\"内容\",value:" +( archives.getShortContent()!=null?gson.toJson( archives.getShortContent()):"\"\"") + "},";
			for (ProcessForm pf : pformlist) {
				if (!pf.getActivityName().equals("开始")) {
					json += "{label:\"审批流程名称\",value:\"" + pf.getActivityName()
							+ "\"},";
					json += "{label:\"审批人\",value:\"" + pf.getCreatorName()
							+ "\"},";
					Iterator<FormData> foemdates = pf.getFormDatas().iterator();
					for (; foemdates.hasNext();) {
						FormData fd = foemdates.next();
						if (fd.getIsShowed() == 1&&fd.getVal()!=null) {
							json += "{label:\"" + fd.getFieldLabel()
									+ "\",value:" + (fd.getVal()!=null?gson.toJson(fd.getVal()):"\"\"") + "},";
						}
					}
				}
			}
			if (json.length() > 0 && json.lastIndexOf(",") == json.length() - 1) {
				json = json.substring(0, json.length() - 1);
			}
			json += "],";
			json += "accessories:[";
			Iterator<ArchivesDoc> iterator = archives.getArchivesDocs()
					.iterator();
			ArchivesDoc doc = null;
			for (; iterator.hasNext();) {
				doc = iterator.next();
				json += "{name:\"" + doc.getDocName() + "\",\"url\":\""
						+ doc.getFileAttach().getFilePath() + "\",type:\""
						+ getType(doc.getFileAttach().getExt()) + "\"}";
				if (iterator.hasNext()) {
					json += ",";
				}
			}
			json += "]}";
		} else if (processName.equals("收文流程")
				|| processName.equals("收文流程-市局收文")) {
			String id = model.get("archives_archivesId").toString();
			Archives archives = archivesService.get(Long.parseLong(id));
			json += "title:\"" + archives.getSubject() + "\",";
			json += "createDate:\"" + archives.getCreatetime() + "\",";
			json += "id:\"" + id + "\",";
			json += "activityName: \"" + this.activityName + "\",";
			json += "taskId: \"" + this.taskId + "\",";
			json += "signalName: \"" + tf.getName() + "\",";
			json += "type:\"1\",";
			if (this.activityName.equals("办公室主任批阅")) {
				json += "boxstatus:true,";
			} else {
				json += "boxstatus:false,";
			}
			if (activityName.equals("指定传阅人")
					|| this.activityName.equals("科室主任传阅")) {
				json += "userquery:true,";
			} else {
				json += "userquery:false,";
			}
			if (this.activityName.equals("承办归档")) {
				json += "showgdlx:true,";
			} else {
				json += "showgdlx:false,";
			}
			if ( this.activityName.equals("科室主任传阅")||this.activityName.equals("承办归档")) {
				json += "showBack:false,";
			} else {
				json += "showBack:true,";
			}
			json += "showbh:false,";
			json += "isdx:false,";
			json += "data:[";
			json += "{label:\"来文文字号\",value:\"" + archives.getArchivesNo()
					+ "\"},";
			json += "{label:\"发文人\",value:\"" + archives.getIssuer() + "\"},";
			json += "{label:\"发文机关\",value:\"" + archives.getIssueDep()
					+ "\"},";
			json += "{label:\"来文类型\",value:\""
					+ archives.getArchRecType().getTypeName() + "\"},";
			json += "{label:\"主题词\",value:\"" + archives.getKeywords() + "\"},";
			json += "{label:\"公文来源\",value:\"" + archives.getSources() + "\"},";
			json += "{label:\"紧急程度\",value:\"" + archives.getUrgentLevel()
					+ "\"},";
			json += "{label:\"秘密等级\",value:\"" + archives.getPrivacyLevel()
					+ "\"},";
			json += "{label:\"内容\",value:" +( archives.getShortContent()!=null?gson.toJson( archives.getShortContent()):"\"\"") + "},";
			for (ProcessForm pf : pformlist) {
				if (!pf.getActivityName().equals("开始")) {
					json += "{label:\"审批流程名称\",value:\"" + pf.getActivityName()
							+ "\"},";
					json += "{label:\"审批人\",value:\"" + pf.getCreatorName()
							+ "\"},";
					Iterator<FormData> foemdates = pf.getFormDatas().iterator();
					for (; foemdates.hasNext();) {
						FormData fd = foemdates.next();
						if (fd.getIsShowed() == 1&&fd.getVal()!=null) {
							json += "{label:\"" + fd.getFieldLabel()
									+ "\",value:" + (fd.getVal()!=null?gson.toJson(fd.getVal()):"\"\"") + "},";
						}
					}
				}
			}
			if (json.length() > 0 && json.lastIndexOf(",") == json.length() - 1) {
				json = json.substring(0, json.length() - 1);
			}
			json += "],";
			json += "accessories:[";
			Iterator<ArchivesDoc> iterator = archives.getArchivesDocs()
					.iterator();
			ArchivesDoc doc = null;
			for (; iterator.hasNext();) {
				doc = iterator.next();
				json += "{name:\"" + doc.getDocName() + "\",\"url\":\""
						+ doc.getFileAttach().getFilePath() + "\",type:\""
						+ getType(doc.getFileAttach().getExt()) + "\"}";
				if (iterator.hasNext()) {
					json += ",";
				}
			}
			json += "]}";
		} else if (processName.equals("请假-短") || processName.equals("请假-中")
				|| processName.equals("请假-长")) {
			String dateId = model.get("dateId").toString();
			ErrandsRegisterService errandsRegisterService = (ErrandsRegisterService) AppUtil
					.getBean("errandsRegisterService");
			ErrandsRegister errandsRegister = (ErrandsRegister) errandsRegisterService
					.get(Long.parseLong(dateId));
			json += "title:\"" + processName + "\",";
			json += "createDate:\"" + errandsRegister.getStartTime() + "\",";
			json += "id:\"" + dateId + "\",";
			json += "activityName: \"" + this.activityName + "\",";
			json += "taskId: \"" + this.taskId + "\",";
			json += "signalName: \"" + tf.getName() + "\",";
			json += "type:\"2\",";
			json += "boxstatus:false,";
			json += "userquery:false,";
			json += "showgdlx:false,";
			json += "showBack:true,";
			json += "showbh:false,";
			json += "isdx:false,";
			json += "data:[";
			json += "{label:\"开始时间\",value:\"" + errandsRegister.getStartTime()
					+ "\"},";
			json += "{label:\"结束时间\",value:\"" + errandsRegister.getEndTime()
					+ "\"},";
			json += "{label:\"申请人\",value:\"" + errandsRegister.getAppUser().getFullname()
			+ "\"},";
			json += "{label:\"部门\",value:\"" + errandsRegister.getAppUser().getDepartment().getDepName()
			+ "\"},";
			json += "{label:\"描述\",value:" +( errandsRegister.getDescp()!=null?gson.toJson( errandsRegister.getDescp()):"\"\"") + "},";
			json += "{label:\"请假类型\",value:\""
					+ errandsRegister.getLeaveTypeName() + "\"},";
			for (ProcessForm pf : pformlist) {
				if (!pf.getActivityName().equals("开始")) {
					json += "{label:\"审批流程名称\",value:\"" + pf.getActivityName()
							+ "\"},";
					json += "{label:\"审批人\",value:\"" + pf.getCreatorName()
							+ "\"},";
					Iterator<FormData> foemdates = pf.getFormDatas().iterator();
					for (; foemdates.hasNext();) {
						FormData fd = foemdates.next();
						if (fd.getIsShowed() == 1&&fd.getVal()!=null) {
							json += "{label:\"" + fd.getFieldLabel()
									+ "\",value:" + (fd.getVal()!=null?gson.toJson(fd.getVal()):"\"\"") + "},";
						}
					}
				}
			}
			if (json.length() > 0 && json.lastIndexOf(",") == json.length() - 1) {
				json = json.substring(0, json.length() - 1);
			}
			json += "],accessories:[]}";
		}
		return json;
	}

	public String getYycyList(String userId, String passwd, String passType,
			String title, String pageNum, String pageSize) {
		filter(userId, passwd);
		Integer start = 0;
		Integer limit = 10;
		if (pageNum != null && pageSize != null) {
			start = (Integer.parseInt(pageNum) - 1)
					* Integer.parseInt(pageSize);
			limit = Integer.parseInt(pageSize);
		}
		PagingBean pb = new PagingBean(start, limit);
		ArchDispatchService archDispatchService = (ArchDispatchService) AppUtil
				.getBean("archDispatchService");
		QueryFilter filter = new QueryFilter(pb);
		filter.addFilter("Q_userId_L_EQ", userId);
		filter.addFilter("Q_archUserType_SN_EQ", "0");
		if (title != null && title.length() > 0) {
			filter.addFilter("Q_subject_S_LK", title);
		}
		if (passType.equals("0")) {
			filter.addFilter("Q_archives.status_SN_EQ", "6");
		} else if (passType.equals("1")) {
			filter.addFilter("Q_archives.status_SN_NEQ", "6");
		}
		List<ArchDispatch> list = archDispatchService.getAll(filter);
		String json = "{success:true,totalCount:\"" + pb.getTotalItems()
				+ "\",data:[";
		for (ArchDispatch ad : list) {
			json += "{id:\"" + ad.getArchivesId() + "\",title:\""
					+ ad.getSubject() + "\",gwzh :\""
					+ ad.getArchives().getArchivesNo() + "\",author :\""
					+ ad.getArchives().getIssuer() + "\", department:\""
					+ ad.getArchives().getIssueDep() + "\",createDate :\""
					+ ad.getArchives().getIssueDate() + "\"},";
		}
		if (list != null && list.size() > 0) {
			json = json.substring(0, json.length() - 1);
		}
		json += "]}";
		return json;
	}

	public String getYycyDetail(String userId, String passwd, String id) {
		// TODO Auto-generated method stub
		ArchivesService arService = (ArchivesService) AppUtil
				.getBean("archivesService");
		Archives archives = arService.load(new Long(id));
		String json = "{success:true,";
		json += "title:\"" + archives.getSubject() + "\",";
		json += "createDate:\"" + archives.getCreatetime() + "\",";
		json += "id:\"" + id + "\",";
		json += "activityName: \"false\",";
		json += "taskId: \"false\",";
		json += "signalName: \"false\",";
		json += "type:\"1\",";
		json += "boxstatus:false,";
		json += "userquery:false,";
		json += "showgdlx:false,";
		json += "data:[";
		json += "{label:\"来文文字号\",value:\"" + archives.getArchivesNo() + "\"},";
		json += "{label:\"发文人\",value:\"" + archives.getIssuer() + "\"},";
		json += "{label:\"发文机关\",value:\"" + archives.getIssueDep() + "\"},";
		json += "{label:\"来文类型\",value:\""
				+ archives.getArchRecType().getTypeName() + "\"},";
		json += "{label:\"主题词\",value:\"" + archives.getKeywords() + "\"},";
		json += "{label:\"公文来源\",value:\"" + archives.getSources() + "\"},";
		json += "{label:\"紧急程度\",value:\"" + archives.getUrgentLevel() + "\"},";
		json += "{label:\"秘密等级\",value:\"" + archives.getPrivacyLevel()
				+ "\"},";
		Gson gson = new Gson();
		json += "{label:\"内容\",value:" +( archives.getShortContent()!=null?gson.toJson( archives.getShortContent()):"\"\"") + "},";
		if (json.length() > 0 && json.lastIndexOf(",") == json.length() - 1) {
			json = json.substring(0, json.length() - 1);
		}
		json += "],";
		json += "accessories:[";
		Iterator<ArchivesDoc> iterator = archives.getArchivesDocs().iterator();
		ArchivesDoc doc = null;
		for (; iterator.hasNext();) {
			doc = iterator.next();
			json += "{name:\"" + doc.getDocName() + "\",\"url\":\""
					+ doc.getFileAttach().getFilePath() + "\",type:\""
					+ getType(doc.getFileAttach().getExt()) + "\"}";
			if (iterator.hasNext()) {
				json += ",";
			}
		}
		json += "]}";
		return json;
	}

	public String saveProcessAndToNext(String userId, String passwd, String id,
			String taskId, String activityName, String signalName,
			String commentDesc, String nextuser, String checkboxvalue,
			String ispass, String gdlx,String bh) {
		filter(userId, passwd);
		ErrandsRegisterService errandsRegisterService = (ErrandsRegisterService) AppUtil.getBean("errandsRegisterService");
		LeaveLeaderReadService leaveLeaderReadService = (LeaveLeaderReadService) AppUtil.getBean("leaveLeaderReadService");
		ArchivesService archivesService = (ArchivesService) AppUtil.getBean("archivesService");
		LeaderReadService leaderReadService = (LeaderReadService) AppUtil.getBean("leaderReadService");
		DepartmentService departmentService = (DepartmentService) AppUtil.getBean("departmentService");
		ArchRecUserService archRecUserService = (ArchRecUserService) AppUtil.getBean("archRecUserService");
		ArchivesDepService archivesDepService = (ArchivesDepService) AppUtil.getBean("archivesDepService");
		ShortMessageService messageService = (ShortMessageService) AppUtil.getBean("shortMessageService");
		ArchivesAttendService archivesAttendService = (ArchivesAttendService) AppUtil.getBean("archivesAttendService");
		ArchDispatchService archDispatchService = (ArchDispatchService) AppUtil.getBean("archDispatchService");
		ArchUnderTakesService undertakesService = (ArchUnderTakesService) AppUtil.getBean("undertakesService");
		ArchivesHandleService archivesHandleService = (ArchivesHandleService) AppUtil.getBean("archivesHandleService");
		ArchRecFiledTypeService archRecFiledTypeService = (ArchRecFiledTypeService) AppUtil.getBean("archRecFiledTypeService");
		AppUserService appUserService = (AppUserService) AppUtil.getBean("appUserService");
		this.setActivityName(activityName);
		this.setTaskId(taskId);
		JbpmService jbpmService=(JbpmService) AppUtil.getBean("jbpmService");
		ProDefinition proDefinition = getProDefinition();
		String processName = proDefinition.getName();
		ProcessRunService processRunService = (ProcessRunService) AppUtil
				.getBean("processRunService");
		if(ispass!=null&&ispass.length()>0&&ispass.equals("false")){
			ProcessInstance pi=jbpmService.getProcessInstanceByTaskId(taskId);
			ProcessRun processRun = processRunService.getByTaskId(this.taskId.toString());
			processRun.setRunStatus(ProcessRun.RUN_STATUS_FINISHED);
	        processRun.setPiId(null);
	        processRunService.save(processRun);
			jbpmService.endProcessInstance(pi.getId());
			if (processName.equals("请假-短") || processName.equals("请假-中")
					|| processName.equals("请假-长")) {
				ErrandsRegister errandsRegister = ((ErrandsRegister) errandsRegisterService
						.get(Long.parseLong(id)));
				errandsRegister.setStatus(Short.valueOf(Short
							.parseShort("6")));
				errandsRegisterService.save(errandsRegister);
				LeaveLeaderRead leaderRead = new LeaveLeaderRead();
				leaderRead.setLeaderName(ContextUtil.getCurrentUser()
						.getFullname());
				leaderRead.setUserId(ContextUtil.getCurrentUserId());
				leaderRead.setErrandsRegister(errandsRegister);
				leaderRead.setCreatetime(new Date());
				leaderRead.setIsPass(LeaveLeaderRead.NOT_PASS);
				leaderRead.setLeaderOpinion("打回");
				leaderRead.setCheckName(activityName);
				leaderRead = leaveLeaderReadService.save(leaderRead);
			} else if (processName.equals("发文流程") || processName.equals("请示报告")
					|| processName.equals("发文流程-市局发文")||processName.equals("收文流程")
					|| processName.equals("收文流程-市局收文")) {
				Archives archives = ((Archives) archivesService.get(Long
						.parseLong(id)));
				archives.setStatus(Short.valueOf(Short
							.parseShort("0")));
				archivesService.save(archives);
				LeaderRead leaderRead = new LeaderRead();
				leaderRead.setLeaderName(ContextUtil.getCurrentUser()
						.getFullname());
				leaderRead.setUserId(ContextUtil.getCurrentUserId());
				leaderRead.setArchives(archives);
				leaderRead.setCreatetime(new Date());
				leaderRead.setIsPass(LeaderRead.NOT_PASS);
				leaderRead.setLeaderOpinion("打回");
				leaderRead.setCheckName(activityName);
				leaderReadService.save(leaderRead);
			}
			return "{success:true}";
		}else{
			this.parmap.put("activityName", activityName);
			this.parmap.put("taskId", taskId);
			this.parmap.put("signalName", signalName);
		}
		
		if (nextuser != null && nextuser.length() > 0) {
			this.parmap.put("signUserIds", nextuser);
		}
		Set userset=new HashSet();
		if((processName.equals("请示报告")&&activityName.equals("部门负责人"))
				||(processName.equals("发文流程-市局发文")&&activityName.equals("科室负责人核稿"))){
			Archives archives1 = ((Archives) archivesService.get(Long.parseLong(id)));
			Long userid=archives1.getIssuerId();
			AppUser appUser =appUserService.get(userid);
			Long departid=appUser.getDepartment().getDepId();
			ArchRecUser archRecUser = (ArchRecUser) archRecUserService.getByDepId(departid);
			if(archRecUser!=null&&archRecUser.getLeaderUserId()!=null){
				this.parmap.put("flowAssignId", archRecUser.getLeaderUserId());
			}
		}else if(processName.equals("收文流程-市局收文")&&activityName.equals("办公室主任批阅")){
			Archives archives1 = ((Archives) archivesService.get(Long.parseLong(id)));
			Long userid=archives1.getIssuerId();
			AppUser appUser =appUserService.get(userid);
			Long departid=appUser.getDepartment().getDepId();
			ArchRecUser archRecUser = (ArchRecUser) archRecUserService.getByDepId(departid);
			if(archRecUser.getLeaderUserId()!=null&&archRecUser.getLeaderUserId()>0){
				userset.add(archRecUser.getLeaderUserId().toString());
			}
		}else if(processName.equals("请假-中")&&activityName.equals("部门负责人审批")){
			ErrandsRegister errandsRegister = ((ErrandsRegister) errandsRegisterService.get(Long.parseLong(id)));
			Long userid=errandsRegister.getApprovalId();
			AppUser appUser =appUserService.get(userid);
			Long departid=appUser.getDepartment().getDepId();
			ArchRecUser archRecUser = (ArchRecUser) archRecUserService.getByDepId(departid);
			if(archRecUser!=null&&archRecUser.getLeaderUserId()!=null){
				this.parmap.put("flowAssignId", archRecUser.getLeaderUserId());
			}
		}
		// 1局长2分管局长3全有
		if (checkboxvalue != null && checkboxvalue.length() > 0) {			
			if (checkboxvalue.equals("1")||checkboxvalue.equals("3")) {
				String sql = "select app_user.* from user_role,app_role,app_user "
					+ "where user_role.roleId=app_role.roleId and app_user.userId=user_role.userId ";
				sql += "and app_role.roleId="+AppUtil.getPropertity("role.leaderId");
				List<Map> list = processRunService.findDataList(sql);
				String flowAssignId = "";
				for (Map map : list) {
					userset.add(map.get("userId").toString());
					//flowAssignId += map.get("userId").toString() + ",";
				}
				if(userset.size()>0){
					Iterator iterator =userset.iterator();
					while(iterator.hasNext()){
						flowAssignId+=iterator.next();
						if(iterator.hasNext()){
							flowAssignId+=",";
						}
					}
					this.parmap.put("flowAssignId", flowAssignId);
				}
			} else if (checkboxvalue.equals("2")) {
				String flowAssignId = "";
				if(userset.size()>0){
					Iterator iterator =userset.iterator();
					while(iterator.hasNext()){
						flowAssignId+=iterator.next();
						if(iterator.hasNext()){
							flowAssignId+=",";
						}
					}
					this.parmap.put("flowAssignId", flowAssignId);
				}
			}		
		}
		
		try {
			if (processName.equals("请假-短") || processName.equals("请假-中")
					|| processName.equals("请假-长")) {
				
				ErrandsRegister errandsRegister = ((ErrandsRegister) errandsRegisterService
						.get(Long.parseLong(id)));
				LeaveLeaderRead leaderRead = new LeaveLeaderRead();
				String errandsRegisterStatus = "";
				if (activityName.equals("部门负责人审批")) {// 1
					if (processName.equals("请假-短")) {
						errandsRegisterStatus = "4";
					} else if (processName.equals("请假-中")) {
						errandsRegisterStatus = "2";
					} else if (processName.equals("请假-长")) {
						errandsRegisterStatus = "3";
					}
				} else if (activityName.equals("分管局长审批")) {// 2
					if (processName.equals("请假-中")) {
						errandsRegisterStatus = "4";
					}
				} else if (activityName.equals("局长审批")) {// 3
					if (processName.equals("请假-中")) {
						errandsRegisterStatus = "4";
					} else if (processName.equals("请假-长")) {
						errandsRegisterStatus = "4";
					}
				} else if (activityName.equals("人事登记")) {// 4
					errandsRegisterStatus = "5";
				}
				if (StringUtils.isNotEmpty(errandsRegisterStatus)) {
					errandsRegister.setStatus(Short.valueOf(Short
							.parseShort(errandsRegisterStatus)));
				}
				errandsRegisterService.save(errandsRegister);
				leaderRead.setLeaderName(ContextUtil.getCurrentUser()
						.getFullname());
				leaderRead.setUserId(ContextUtil.getCurrentUserId());
				leaderRead.setErrandsRegister(errandsRegister);
				leaderRead.setCreatetime(new Date());
				leaderRead.setIsPass(LeaveLeaderRead.IS_PASS);
				leaderRead.setLeaderOpinion(commentDesc);
				leaderRead.setCheckName(activityName);
				leaderRead = leaveLeaderReadService.save(leaderRead);
				this.parmap.put("leaderRead.readId", leaderRead.getReadId());
				this.parmap.put("leaderRead.leaderOpinion", commentDesc);
				this.parmap.put("errandsRegister.dateId", id);
			} else if (processName.equals("发文流程") || processName.equals("请示报告")
					|| processName.equals("发文流程-市局发文")) {
				if (activityName.equals("部门负责人")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					String archivesStatus = "6";
					if (StringUtils.isNotEmpty(archivesStatus)) {
						archives.setStatus(Short.valueOf(Short
								.parseShort(archivesStatus)));
					}
					archivesService.save(archives);
					LeaderRead leaderRead = new LeaderRead();
					leaderRead.setLeaderName(ContextUtil.getCurrentUser()
							.getFullname());
					leaderRead.setUserId(ContextUtil.getCurrentUserId());
					leaderRead.setArchives(archives);
					leaderRead.setCreatetime(new Date());
					leaderRead.setIsPass(LeaderRead.IS_PASS);
					leaderRead.setLeaderOpinion(commentDesc);
					leaderRead.setCheckName(activityName);
					leaderReadService.save(leaderRead);
					this.parmap
							.put("leaderRead.readId", leaderRead.getReadId());
					this.parmap.put("leaderRead.leaderOpinion", commentDesc);
					this.parmap.put("archives.archivesId", id);
				} else if (activityName.equals("分管局长审核")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					String archivesStatus = "8";
					if (StringUtils.isNotEmpty(archivesStatus)) {
						archives.setStatus(Short.valueOf(Short
								.parseShort(archivesStatus)));
					}
					archivesService.save(archives);
					LeaderRead leaderRead = new LeaderRead();
					leaderRead.setLeaderName(ContextUtil.getCurrentUser()
							.getFullname());
					leaderRead.setUserId(ContextUtil.getCurrentUserId());
					leaderRead.setArchives(archives);
					leaderRead.setCreatetime(new Date());
					leaderRead.setIsPass(LeaderRead.IS_PASS);
					leaderRead.setLeaderOpinion(commentDesc);
					leaderRead.setCheckName(activityName);
					leaderReadService.save(leaderRead);
					this.parmap
							.put("leaderRead.readId", leaderRead.getReadId());
					this.parmap.put("leaderRead.leaderOpinion", commentDesc);
					this.parmap.put("archives.archivesId", id);
				}else if (activityName.equals("编号录入")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					archives.setStatus(Short.valueOf(Short.parseShort("4")));
					archives.setArchivesNo(bh);
					archivesService.save(archives);
					this.parmap.put("archives.archivesId", id);
					this.parmap.put("distributeOpinion", commentDesc);
				} else if (activityName.equals("局长审核")
						|| activityName.equals("盖章分发")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					String depIds = archives.getRecDepIds();
					StringBuffer msg = new StringBuffer("");
					// 获取不需要进入收文签收的发文类型类型
					String archivesTypeName = AppUtil
							.getPropertity("app.unrefArchivesTypeName");
					if (StringUtils.isNotEmpty(depIds)
							&& !archives.getArchivesType().getTypeName()
									.equals(archivesTypeName)) {
						String[] depIdArr = depIds.split("[,]");
						if (depIdArr != null) {
							StringBuffer recIds = new StringBuffer("");
							for (int i = 0; i < depIdArr.length; i++) {
								Long depId = new Long(depIdArr[i]);
								Department department = (Department) departmentService
										.get(depId);
								ArchRecUser archRecUser = archRecUserService
										.getByDepId(depId);
								ArchivesDep archivesDep = new ArchivesDep();
								archivesDep.setSubject(archives.getSubject());
								archivesDep.setDepartment(department);
								archivesDep.setArchives(archives);
								archivesDep.setIsMain(ArchivesDep.RECEIVE_MAIN);
								archivesDep
										.setStatus(ArchivesDep.STATUS_UNSIGNED);
								if ((archRecUser != null)
										&& (archRecUser.getUserId() != null)) {
									archivesDep.setSignUserID(archRecUser
											.getUserId());
									archivesDep.setSignFullname(archRecUser
											.getFullname());
									recIds.append(archRecUser.getUserId())
											.append(",");
								}
								archivesDepService.save(archivesDep);
							}
							if (StringUtils.isNotEmpty(recIds.toString())) {
								String content = "您有新的公文,请及时签收.";
								messageService.save(AppUser.SYSTEM_USER, recIds
										.toString(), content,
										ShortMessage.MSG_TYPE_TASK);
							}
						}
					}
					archives.setStatus(Short.valueOf(Short.parseShort("7")));
					archivesService.save(archives);
					this.parmap.put("distributeOpinion", commentDesc);
					this.parmap.put("archives.archivesId", id);
				} else if (activityName.equals("科室负责人核稿")) {
					String archivesStatus = "2";
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					if (StringUtils.isNotEmpty(archivesStatus)) {
						archives.setStatus(Short.valueOf(Short
								.parseShort(archivesStatus)));
					}
					archivesService.save(archives);
					ArchivesAttend archivesAttend = new ArchivesAttend();
					archivesAttend.setArchives(archives);
					archivesAttend.setUserID(ContextUtil.getCurrentUserId());
					archivesAttend.setFullname(ContextUtil.getCurrentUser()
							.getFullname());
					archivesAttend.setExecuteTime(new Date());
					archivesAttend.setAttendType("2");
					archivesAttend = archivesAttendService.save(archivesAttend);
					this.parmap.put("archivesAttend.memo", commentDesc);
					this.parmap.put("archives.archivesId", id);
					this.parmap.put("archivesAttend.attendId", archivesAttend
							.getAttendId());
				} else if (activityName.equals("分管或局领导签发")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					String archivesStatus = "3";
					if (StringUtils.isNotEmpty(archivesStatus)) {
						archives.setStatus(Short.valueOf(Short
								.parseShort(archivesStatus)));
					}
					archivesService.save(archives);
					LeaderRead leaderRead = new LeaderRead();
					leaderRead.setLeaderName(ContextUtil.getCurrentUser()
							.getFullname());
					leaderRead.setUserId(ContextUtil.getCurrentUserId());
					leaderRead.setArchives(archives);
					leaderRead.setCreatetime(new Date());
					leaderRead.setIsPass(LeaderRead.IS_PASS);
					leaderRead.setLeaderOpinion(commentDesc);
					leaderRead.setCheckName(activityName);
					leaderReadService.save(leaderRead);
					this.parmap
							.put("leaderRead.readId", leaderRead.getReadId());
					this.parmap.put("leaderRead.leaderOpinion", commentDesc);
					this.parmap.put("archives.archivesId", id);
				} else if (activityName.equals("办公室主任承办")) {
					String archivesStatus = "9";
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					if (StringUtils.isNotEmpty(archivesStatus)) {
						archives.setStatus(Short.valueOf(Short
								.parseShort(archivesStatus)));
					}
					archivesService.save(archives);
					ArchivesAttend archivesAttend = new ArchivesAttend();
					archivesAttend.setArchives(archives);
					archivesAttend.setUserID(ContextUtil.getCurrentUserId());
					archivesAttend.setFullname(ContextUtil.getCurrentUser()
							.getFullname());
					archivesAttend.setExecuteTime(new Date());
					archivesAttend.setAttendType("1");
					archivesAttend = archivesAttendService.save(archivesAttend);
					this.parmap.put("archivesAttend.memo", commentDesc);
					this.parmap.put("archives.archivesId", id);
					this.parmap.put("archivesAttend.attendId", archivesAttend
							.getAttendId());
				}
			} else if (processName.equals("收文流程")
					|| processName.equals("收文流程-市局收文")) {
//				ProcessRun processRun = processRunService
//						.getByTaskId(this.taskId.toString());
//				Map processRunVars = processFormService.getVariables(processRun
//						.getRunId());
				if (activityName.equals("办公室传阅")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					String archivesStatus = "2";
					if (StringUtils.isNotEmpty(archivesStatus)) {
						archives.setStatus(Short.valueOf(Short
								.parseShort(archivesStatus)));
					}
					archivesService.save(archives);
					LeaderRead leaderRead = new LeaderRead();
					leaderRead.setLeaderName(ContextUtil.getCurrentUser()
							.getFullname());
					leaderRead.setUserId(ContextUtil.getCurrentUserId());
					leaderRead.setArchives(archives);
					leaderRead.setCreatetime(new Date());
					leaderRead.setIsPass(LeaderRead.IS_PASS);
					leaderRead.setLeaderOpinion(commentDesc);
					leaderRead.setCheckName(activityName);
					leaderReadService.save(leaderRead);
					this.parmap.put("handleOpinion", commentDesc);
				} else if (activityName.equals("办公室主任批阅")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					String archivesStatus = "3";
					if (StringUtils.isNotEmpty(archivesStatus)) {
						archives.setStatus(Short.valueOf(Short
								.parseShort(archivesStatus)));
					}
					archivesService.save(archives);
					LeaderRead leaderRead = new LeaderRead();
					leaderRead.setLeaderName(ContextUtil.getCurrentUser()
							.getFullname());
					leaderRead.setUserId(ContextUtil.getCurrentUserId());
					leaderRead.setArchives(archives);
					leaderRead.setCreatetime(new Date());
					leaderRead.setIsPass(LeaderRead.IS_PASS);
					leaderRead.setLeaderOpinion(commentDesc);
					leaderRead.setCheckName(activityName);
					leaderReadService.save(leaderRead);
					this.parmap.put("handleOpinion", commentDesc);
				} else if (activityName.equals("分管或主管领导批示")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					String archivesStatus = "8";
					if (StringUtils.isNotEmpty(archivesStatus)) {
						archives.setStatus(Short.valueOf(Short
								.parseShort(archivesStatus)));
					}
					archivesService.save(archives);
					LeaderRead leaderRead = new LeaderRead();
					leaderRead.setLeaderName(ContextUtil.getCurrentUser()
							.getFullname());
					leaderRead.setUserId(ContextUtil.getCurrentUserId());
					leaderRead.setArchives(archives);
					leaderRead.setCreatetime(new Date());
					leaderRead.setIsPass(LeaderRead.IS_PASS);
					leaderRead.setLeaderOpinion(commentDesc);
					leaderRead.setCheckName(activityName);
					leaderReadService.save(leaderRead);
					this.parmap.put("leaderOpinion", commentDesc);
				}else if (activityName.equals("指定传阅人")){
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					archives.setStatus(Short.valueOf(Short
								.parseShort("4")));
					archivesService.save(archives);
					this.parmap.put("handleOpinion", commentDesc);
					undertakesService.saveArchUnderTakesByArchIdAndSign(id,  this.parmap.get("signUserIds").toString());
				} else if (activityName.equals("科室主任传阅")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					String upsignUserIds =undertakesService.findArchUnderTakesUpSignByArchId(id);					
					if (StringUtils.isNotEmpty(upsignUserIds)) {
						ArchDispatch archDispatch = new ArchDispatch();
						AppUser user = ContextUtil.getCurrentUser();
						archDispatch.setArchives(archives);
						archDispatch.setArchUserType((short) 0);
						archDispatch.setUserId(user.getUserId());
						archDispatch.setFullname(user.getFullname());
						archDispatch.setDispatchTime(new Date());
						archDispatch.setSubject(archives.getSubject());
						archDispatch.setIsRead(ArchDispatch.HAVE_READ);
						archDispatch.setReadFeedback(commentDesc);
						archDispatchService.save(archDispatch);
						int recordSize = archDispatchService
								.countArchDispatch(Long.parseLong(id));
						String archivesStatus = "4";
						String[] signId = upsignUserIds.split(",");
						if (signId.length == recordSize) {
							archivesStatus = "5";
							String suids =undertakesService.saveArchUnderTakesByArchId(id,
									this.parmap.get("signUserIds").toString());
							this.parmap.put("signUserIds", suids);
						} else {
							archivesStatus = "6";
							String suids = undertakesService
									.saveArchUnderTakesByArchId(id, this.parmap
											.get("signUserIds").toString());
						}
						if (StringUtils.isNotEmpty(archivesStatus)) {
							archives.setStatus(Short.valueOf(Short
									.parseShort(archivesStatus)));
						}
						archivesService.save(archives);
						this.parmap.put("handleOpinion", commentDesc);
					} else {
						System.out
								.println("---------------------所有审批人没有获取到-------------------");
						return "{success:false}";
					}
				} else if (activityName.equals("承办归档")) {
					Archives archives = ((Archives) archivesService.get(Long
							.parseLong(id)));
					ArchRecFiledType art = archRecFiledTypeService.get(Long
							.parseLong(gdlx));
					ArchivesHandle arh = new ArchivesHandle();
					AppUser user = ContextUtil.getCurrentUser();
					arh.setArchives(archives);
					arh.setCreatetime(new Date());
					arh.setFillTime(new Date());
					arh.setHandleOpinion(commentDesc);
					arh.setIsPass((short) 1);
					arh.setUserId(user.getUserId());
					arh.setUserFullname(user.getFullname());
					arh.setFiledDeptId(user.getDepartment().getDepId());
					arh.setFiledDeptName(user.getDepartment().getDepName());
					arh.setRecFiledTypeId(art.getRecFiledTypeId());
					arh.setRecFiledTypeName(art.getTypeName());
					archivesHandleService.save(arh);
					String archivesStatus = "7";
					if (StringUtils.isNotEmpty(archivesStatus)) {
						archives.setStatus(Short.valueOf(Short
								.parseShort(archivesStatus)));
					}
					archivesService.save(archives);
					ArchDispatch archDispatch = new ArchDispatch();
					archDispatch.setArchives(archives);
					archDispatch.setArchUserType((short) 1);
					archDispatch.setUserId(user.getUserId());
					archDispatch.setFullname(user.getFullname());
					archDispatch.setDispatchTime(new Date());
					archDispatch.setSubject(archives.getSubject());
					archDispatch.setIsRead(ArchDispatch.HAVE_READ);
					archDispatch.setReadFeedback(commentDesc);
					archDispatchService.save(archDispatch);
					this.parmap.put("handleOpinion", commentDesc);
				}
			}
			FlowRunInfo flowRunInfo = getFlowRunInfo();
			processRunService.saveAndNextStep(flowRunInfo);
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			return "{success:false}";
		}

		return "{success:true}";
	}

	protected Map<String, ParamField> constructFieldMap() {
		JbpmService jbpmService = (JbpmService) AppUtil.getBean("jbpmService");
		ProDefinition proDefinition = getProDefinition();

		if (StringUtils.isEmpty(this.activityName)) {
			this.activityName = jbpmService.getStartNodeName(proDefinition);
		}

		Map map = ProcessActivityAssistant.constructFieldMap(proDefinition
				.getName(), this.activityName);

		Iterator fieldNames = map.keySet().iterator();
		while (fieldNames.hasNext()) {
			String name = (String) fieldNames.next();
			ParamField pf = (ParamField) map.get(name);

			pf.setName(pf.getName().replace(".", "_"));
			pf.setValue(parmap.get(name) != null ? parmap.get(name).toString()
					: null);
		}
		return map;
	}

	protected ProDefinition getProDefinition() {
		ProcessRunService processRunService = (ProcessRunService) AppUtil
				.getBean("processRunService");
		ProDefinition proDefinition = null;
		ProcessRun processRun = processRunService.getByTaskId(this.taskId);
		proDefinition = processRun.getProDefinition();
		return proDefinition;
	}

	protected FlowRunInfo getFlowRunInfo() {
		FlowRunInfo info = new FlowRunInfo(parmap);
		Map fieldMap = constructFieldMap();
		info.setParamFields(fieldMap);
		return info;
	}

	public String getGdlx() {
		// TODO Auto-generated method stub
		ArchRecFiledTypeService archRecFiledTypeService = (ArchRecFiledTypeService) AppUtil
				.getBean("archRecFiledTypeService");
		String sql = "select typeId as id,typeName as name from arch_rec_filed_type";
		List list = archRecFiledTypeService.findDataList(sql);
		StringBuffer buff = new StringBuffer("{success:true,").append("data:");
		Gson gson = new Gson();
		buff.append(gson.toJson(list));
		buff.append("}");
		return buff.toString();
	}
}
