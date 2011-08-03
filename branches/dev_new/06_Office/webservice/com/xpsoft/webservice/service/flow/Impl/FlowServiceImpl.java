package com.xpsoft.webservice.service.flow.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.velocity.tools.generic.DateTool;
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
import com.xpsoft.oa.model.archive.Archives;
import com.xpsoft.oa.model.archive.ArchivesDoc;
import com.xpsoft.oa.model.flow.FormData;
import com.xpsoft.oa.model.flow.ProDefinition;
import com.xpsoft.oa.model.flow.ProcessForm;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.flow.Transform;
import com.xpsoft.oa.model.info.NoticeNewsDoc;
import com.xpsoft.oa.model.personal.ErrandsRegister;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.archive.ArchDispatchService;
import com.xpsoft.oa.service.archive.ArchivesService;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProcessFormService;
import com.xpsoft.oa.service.flow.ProcessRunService;
import com.xpsoft.oa.service.flow.TaskService;
import com.xpsoft.oa.service.personal.ErrandsRegisterService;
import com.xpsoft.oa.service.system.AppUserService;
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
			if (task.getTaskName().equals("承办传阅")) {
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
			if (!task.getTaskName().equals("承办传阅")) {
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
			json += "{label:\"内容\",value:\"" + archives.getShortContent()
					+ "\"},";
			for (ProcessForm pf : pformlist) {
				if(!pf.getActivityName().equals("开始")){
					json += "{label:\"审批流程名称\",value:\"" + pf.getActivityName()
							+ "\"},";
					json += "{label:\"审批人\",value:\"" + pf.getCreatorName()
							+ "\"},";
					Iterator<FormData> foemdates = pf.getFormDatas().iterator();
					for (; foemdates.hasNext();) {
						FormData fd = foemdates.next();
						if (fd.getIsShowed() == 1) {
							json += "{label:\"" + fd.getFieldLabel()
									+ "\",value:\"" + fd.getVal() + "\"},";
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
		} else if (processName.equals("收文流程")) {
			String id = model.get("archives_archivesId").toString();
			Archives archives = archivesService.get(Long.parseLong(id));
			json += "title:\"" + archives.getSubject() + "\",";
			json += "createDate:\"" + archives.getCreatetime() + "\",";
			json += "id:\"" + id + "\",";
			json += "activityName: \"" + this.activityName + "\",";
			json += "taskId: \"" + this.taskId + "\",";
			json += "signalName: \"" + tf.getName() + "\",";
			json += "type:\"1\",";
			json += "boxstatus:false,";
			json += "userquery:false,";
			json += "data:[";
			json += "{label:\"来文文字号\",value:\"" + archives.getArchivesNo()
					+ "\"},";
			json += "{label:\"发文人\",value:\"" + archives.getIssuer() + "\"},";
			json += "{label:\"发文机关\",value:\"" + archives.getIssueDep()
					+ "\"},";
			json += "{label:\"来文类型\",value:\"" + archives.getArchRecType().getTypeName()
					+ "\"},";
			json += "{label:\"主题词\",value:\"" + archives.getKeywords() + "\"},";
			json += "{label:\"公文来源\",value:\"" + archives.getSources() + "\"},";
			json += "{label:\"紧急程度\",value:\"" + archives.getUrgentLevel()
					+ "\"},";
			json += "{label:\"秘密等级\",value:\"" + archives.getPrivacyLevel()
					+ "\"},";
			json += "{label:\"内容\",value:\"" + archives.getShortContent()
					+ "\"},";
			for (ProcessForm pf : pformlist) {
				if(!pf.getActivityName().equals("开始")){
					json += "{label:\"审批流程名称\",value:\"" + pf.getActivityName()
							+ "\"},";
					json += "{label:\"审批人\",value:\"" + pf.getCreatorName()
							+ "\"},";
					Iterator<FormData> foemdates = pf.getFormDatas().iterator();
					for (; foemdates.hasNext();) {
						FormData fd = foemdates.next();
						if (fd.getIsShowed() == 1) {
							json += "{label:\"" + fd.getFieldLabel()
									+ "\",value:\"" + fd.getVal() + "\"},";
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
		} else if (processName.equals("请假-短")||processName.equals("请假-中")||processName.equals("请假-长")) {
				String dateId=model.get("dateId").toString();
				 ErrandsRegisterService errandsRegisterService=(ErrandsRegisterService) AppUtil.getBean("errandsRegisterService");
				 ErrandsRegister errandsRegister = (ErrandsRegister)errandsRegisterService.get(Long.parseLong(dateId));
				 json += "title:\"" + processName + "\",";
					json += "createDate:\"" + errandsRegister.getStartTime() + "\",";
					json += "id:\"" + dateId + "\",";
					json += "activityName: \"" + this.activityName + "\",";
					json += "taskId: \"" + this.taskId + "\",";
					json += "signalName: \"" + tf.getName() + "\",";
					json += "type:\"2\",";
					json += "boxstatus:false,";
					json += "userquery:false,";
					json += "data:[";
					json += "{label:\"开始时间\",value:\"" +errandsRegister.getStartTime()
							+ "\"},";
					json += "{label:\"结束时间\",value:\"" +errandsRegister.getEndTime()
					+ "\"},";
					json += "{label:\"描述\",value:\"" +errandsRegister.getDescp()
					+ "\"},";
					json += "{label:\"请假类型\",value:\"" +errandsRegister.getLeaveTypeName()
					+ "\"},";
					for (ProcessForm pf : pformlist) {
						if(!pf.getActivityName().equals("开始")){
							json += "{label:\"审批流程名称\",value:\"" + pf.getActivityName()
									+ "\"},";
							json += "{label:\"审批人\",value:\"" + pf.getCreatorName()
									+ "\"},";
							Iterator<FormData> foemdates = pf.getFormDatas().iterator();
							for (; foemdates.hasNext();) {
								FormData fd = foemdates.next();
								if (fd.getIsShowed() == 1) {
									json += "{label:\"" + fd.getFieldLabel()
											+ "\",value:\"" + fd.getVal() + "\"},";
								}
							}
						}
					}
					if (json.length() > 0 && json.lastIndexOf(",") == json.length() - 1) {
						json = json.substring(0, json.length() - 1);
					}
					json += "]}";
		}
		return json;
	}
	public String getYycyList(String userId, String passwd,String passType,String title,String pageNum,String pageSize) {
		filter( userId,passwd);
		Integer start=0;
		Integer limit=10;
		if(pageNum!=null&&pageSize!=null){
			start=(Integer.parseInt(pageNum)-1)*Integer.parseInt(pageSize);
			limit=start+Integer.parseInt(pageSize);
		}
		PagingBean pb=new PagingBean(start,limit);
		ArchDispatchService archDispatchService= (ArchDispatchService) AppUtil.getBean("archDispatchService");
		QueryFilter filter = new QueryFilter(pb);
		filter.addFilter("Q_userId_L_EQ", userId);
		filter.addFilter("Q_archUserType_SN_EQ","0");
		if(title!=null&&title.length()>0){
			filter.addFilter("Q_subject_S_LK",title);
		}
		if(passType.equals("0")){
			filter.addFilter("Q_archives.status_SN_EQ","6");
		}else if(passType.equals("1")){
			filter.addFilter("Q_archives.status_SN_EQ", "7");
		}
		List<ArchDispatch> list = archDispatchService.getAll(filter);
		String json="{success:true,totalCount:\""+pb.getTotalItems()+"\",data:[";
		for(ArchDispatch ad:list){
			json+="{id:\""+ad.getArchivesId()+"\",title:\""+ad.getSubject()+"\",gwzh :\""+ad.getArchives().getArchivesNo()+"\",author :\""+ad.getArchives().getIssuer()+"\", department:\""+ad.getArchives().getIssueDep()+"\",createDate :\""+ad.getArchives().getIssueDate()+"\"},";
		}
		if(list!=null&&list.size()>0){
			json=json.substring(0,json.length()-1);
		}
			json+="]}";
		return json;
	}

	public String getycyDetail(String userId, String passwd, String id) {
		// TODO Auto-generated method stub
		ArchivesService arService = (ArchivesService)AppUtil.getBean("archivesService");
		Archives archives=arService.load(new Long(id));
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
		json += "data:[";
		json += "{label:\"来文文字号\",value:\"" + archives.getArchivesNo()
				+ "\"},";
		json += "{label:\"发文人\",value:\"" + archives.getIssuer() + "\"},";
		json += "{label:\"发文机关\",value:\"" + archives.getIssueDep()
				+ "\"},";
		json += "{label:\"来文类型\",value:\"" + archives.getArchRecType().getTypeName()
				+ "\"},";
		json += "{label:\"主题词\",value:\"" + archives.getKeywords() + "\"},";
		json += "{label:\"公文来源\",value:\"" + archives.getSources() + "\"},";
		json += "{label:\"紧急程度\",value:\"" + archives.getUrgentLevel()
				+ "\"},";
		json += "{label:\"秘密等级\",value:\"" + archives.getPrivacyLevel()
				+ "\"},";
		json += "{label:\"内容\",value:\"" + archives.getShortContent()
				+ "\"},";		
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
		return json;
	}
	public String saveProcessAndToNext(String userId, String passwd, String id,String taskId,String activityName){
		
		return null;
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
			pf.setValue(parmap.get(name).toString());
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

	
}
