package com.xpsoft.oa.action.flow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.Transition;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.google.gson.Gson;
import com.xpsoft.core.jbpm.pv.ParamField;
import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.util.JsonUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.flow.ProDefinition;
import com.xpsoft.oa.model.flow.ProcessForm;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.flow.Transform;
import com.xpsoft.oa.service.flow.FormDataService;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProDefinitionService;
import com.xpsoft.oa.service.flow.ProcessFormService;
import com.xpsoft.oa.service.flow.ProcessRunService;

import flexjson.JSONSerializer;

public class ProcessActivityAction extends BaseAction {

	@Resource
	private ProDefinitionService proDefinitionService;

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private ProcessFormService processFormService;

	@Resource
	private JbpmService jbpmService;

	@Resource
	private FormDataService formDataService;

	@Resource
	VelocityEngine flowVelocityEngine;
	private String activityName;
	private Long runId;
	private Long taskId;
	private Long defId;

	public Long getTaskId() {
		/* 68 */return this.taskId;
	}

	public void setTaskId(Long taskId) {
		/* 72 */this.taskId = taskId;
	}

	public Long getRunId() {
		/* 76 */return this.runId;
	}

	public void setRunId(Long runId) {
		/* 80 */this.runId = runId;
	}

	public String getActivityName() {
		/* 84 */return this.activityName;
	}

	public void setActivityName(String activityName) {
		/* 88 */this.activityName = activityName;
	}

	public Long getDefId() {
		/* 97 */return this.defId;
	}

	public void setDefId(Long defId) {
		/* 101 */this.defId = defId;
	}

	public String get() throws Exception {
		ProDefinition proDefinition = getProDefinition();
		String processName = proDefinition.getName();

		if (StringUtils.isEmpty(this.activityName)) {
			this.activityName = this.jbpmService
					.getStartNodeName(proDefinition);
		}

		String tempLocation = ProcessActivityAssistant.getFormPath(processName,
				this.activityName);

		Map model = new HashMap();

		Map formDataMap = null;
		if (this.runId != null) {
			formDataMap = this.formDataService.getFromDataMap(this.runId,
					this.activityName);
		}

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

		if (this.taskId != null) {
			ProcessRun processRun = this.processRunService
					.getByTaskId(this.taskId.toString());
			Map processRunVars = this.processFormService
					.getVariables(processRun.getRunId());

			List<Transition> trans = this.jbpmService
					.getTransitionsByTaskId(this.taskId.toString());

			List allTrans = new ArrayList();
			for (Transition tran : trans) {
				allTrans.add(new Transform(tran));
			}

			model.putAll(processRunVars);

			model.put("nextTrans", allTrans);
		}

		model.put("currentUser", ContextUtil.getCurrentUser());
		model.put("dateTool", new DateTool());
		String formUiJs = "";
		try {
			formUiJs = VelocityEngineUtils.mergeTemplateIntoString(
					this.flowVelocityEngine, tempLocation, "UTF-8", model);
		} catch (Exception ex) {
			/* 169 */formUiJs = VelocityEngineUtils.mergeTemplateIntoString(
					this.flowVelocityEngine, ProcessActivityAssistant
							.getCommonFormPath(this.activityName), "UTF-8",
					model);
		}

		/* 172 */if (StringUtils.isEmpty(formUiJs)) {
			/* 173 */formUiJs = "[]";
		}
		/* 175 */setJsonString(formUiJs);

		/* 177 */return "success";
	}

	public String check() {
		Task task = this.jbpmService.getTaskById(String.valueOf(this.taskId));

		if (task != null) {
			String assignId = task.getAssignee();
			Long curUserId = ContextUtil.getCurrentUserId();

			if (curUserId.toString().equals(assignId)) {
				this.jsonString = "{success:true,isValid:true,msg:''}";
			} else if (StringUtils.isNotEmpty(assignId)) {
				this.jsonString = "{success:true,isValid:false,msg:'该任务已经被其他成员锁定执行！'}";
			} else {
				this.jbpmService.assignTask(task.getId(), curUserId.toString());
				this.jsonString = "{success:true,isValid:true,msg:'该任务已经被您锁定执行!'}";
			}
		} else {
			this.jsonString = "{success:true,isValid:false,msg:'该任务已经完成了'}";
		}

		/* 206 */return "success";
	}

	public String save() {
		FlowRunInfo flowRunInfo = getFlowRunInfo();

		if (this.runId != null) {
			ProcessRun processRun = (ProcessRun) this.processRunService
					.get(this.runId);
			ProcessForm processForm = this.processFormService
					.getByRunIdActivityName(this.runId, this.activityName);
			if (processForm != null) {
				this.processRunService.saveProcessRun(processRun, processForm,
						flowRunInfo);
			}
		} else if (this.defId != null) {
			ProcessRun processRun = initNewProcessRun();
			ProcessForm processForm = initNewProcessForm(processRun);
			this.processRunService.saveProcessRun(processRun, processForm,
					flowRunInfo);
		}

		setJsonString("{success:true}");
		return "success";
	}

	protected ProcessRun initNewProcessRun() {
		/* 239 */ProDefinition proDefinition = (ProDefinition) this.proDefinitionService
				.get(this.defId);

		/* 241 */return this.processRunService
				.initNewProcessRun(proDefinition);
	}

	protected ProcessForm initNewProcessForm(ProcessRun processRun) {
		/* 245 */ProcessForm processForm = new ProcessForm();
		/* 246 */processForm.setActivityName(this.activityName);
		/* 247 */processForm.setProcessRun(processRun);

		/* 249 */return processForm;
	}

	public String next() {
		/* 260 */FlowRunInfo flowRunInfo = getFlowRunInfo();

		/* 262 */this.processRunService.saveAndNextStep(flowRunInfo);

		/* 264 */setJsonString("{success:true}");

		/* 266 */return "success";
	}

	public String freeTrans() {
		/* 275 */Gson gson = new Gson();
		/* 276 */StringBuffer sb = new StringBuffer();

		/* 278 */sb.append("[");

		/* 280 */List<Transition> trans = this.jbpmService
				.getFreeTransitionsByTaskId(this.taskId.toString());

		/* 282 */for (Transition tran : trans) {
			/* 283 */sb.append("[").append(gson.toJson(tran.getName()))
					.append(",").append(
							gson.toJson(tran.getDestination().getName()))
					.append("],");
		}

		/* 286 */if (trans.size() > 0) {
			/* 287 */sb.deleteCharAt(sb.length() - 1);
		}

		/* 290 */sb.append("]");

		/* 292 */setJsonString(sb.toString());

		/* 294 */return "success";
	}

	public String trans() {
		/* 304 */List allTrans = new ArrayList();

		/* 306 */List<Transition> trans = this.jbpmService
				.getTransitionsByTaskId(this.taskId.toString());

		/* 309 */for (Transition tran : trans) {
			/* 310 */allTrans.add(new Transform(tran));
		}

		/* 313 */JSONSerializer serializer = JsonUtil
				.getJSONSerializer(new String[0]);
		/* 314 */String result = serializer.serialize(allTrans);

		/* 316 */setJsonString("{success:true,data:" + result + "}");
		/* 317 */return "success";
	}

	protected Map<String, ParamField> constructFieldMap() {
		/* 325 */HttpServletRequest request = getRequest();
		/* 326 */ProDefinition proDefinition = getProDefinition();

		/* 328 */if (StringUtils.isEmpty(this.activityName)) {
			/* 329 */this.activityName = this.jbpmService
					.getStartNodeName(proDefinition);
		}

		/* 332 */Map map = ProcessActivityAssistant.constructFieldMap(
				proDefinition.getName(), this.activityName);

		/* 334 */Iterator fieldNames = map.keySet().iterator();
		/* 335 */while (fieldNames.hasNext()) {
			/* 336 */String name = (String) fieldNames.next();
			/* 337 */ParamField pf = (ParamField) map.get(name);

			/* 340 */pf.setName(pf.getName().replace(".", "_"));
			/* 341 */pf.setValue(request.getParameter(name));
		}
		/* 343 */return map;
	}

	protected ProDefinition getProDefinition() {
		ProDefinition proDefinition = null;
		if (this.runId != null) {
			ProcessRun processRun = (ProcessRun) this.processRunService
					.get(this.runId);
			proDefinition = processRun.getProDefinition();
		} else if (this.defId != null) {
			proDefinition = (ProDefinition) this.proDefinitionService
					.get(this.defId);
		} else {
			ProcessRun processRun = this.processRunService
					.getByTaskId(this.taskId.toString());
			proDefinition = processRun.getProDefinition();
		}
		return proDefinition;
	}

	protected FlowRunInfo getFlowRunInfo() {
		/* 368 */FlowRunInfo info = new FlowRunInfo(getRequest());
		/* 369 */Map fieldMap = constructFieldMap();
		/* 370 */info.setParamFields(fieldMap);
		/* 371 */return info;
	}
}
