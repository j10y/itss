package com.xpsoft.oa.service.flow.impl;

import com.xpsoft.core.jbpm.pv.ParamField;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.util.ContextUtil;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.action.flow.FlowRunInfo;
import com.xpsoft.oa.dao.flow.FormDataDao;
import com.xpsoft.oa.dao.flow.ProcessFormDao;
import com.xpsoft.oa.dao.flow.ProcessRunDao;
import com.xpsoft.oa.model.flow.FormData;
import com.xpsoft.oa.model.flow.ProDefinition;
import com.xpsoft.oa.model.flow.ProcessForm;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProcessRunService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.Resource;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessInstance;

public class ProcessRunServiceImpl extends BaseServiceImpl<ProcessRun>
		implements ProcessRunService {
	private ProcessRunDao dao;

	@Resource
	private ProcessFormDao processFormDao;

	@Resource
	private FormDataDao formDataDao;

	@Resource
	private JbpmService jbpmService;

	public ProcessRunServiceImpl(ProcessRunDao dao) {
		super(dao);
		this.dao = dao;
	}

	public ProcessRun getByExeId(String exeId) {
		ProcessInstance pi = this.jbpmService
				.getProcessInstanceByExeId(exeId);
		if (pi != null) {
			return getByPiId(pi.getId());
		}
		return null;
	}

	public ProcessRun getByTaskId(String taskId) {
		ProcessInstance pi = this.jbpmService
				.getProcessInstanceByTaskId(taskId);
		if (pi != null) {
			return getByPiId(pi.getId());
		}
		return null;
	}

	public ProcessRun getByPiId(String piId) {
		return this.dao.getByPiId(piId);
	}

	public ProcessRun initNewProcessRun(ProDefinition proDefinition) {
		ProcessRun processRun = new ProcessRun();
		AppUser curUser = ContextUtil.getCurrentUser();

		Date curDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");

		processRun.setSubject(proDefinition.getName()
				+ sdf.format(curDate) + "(" + curUser.getFullname() + ")");
		processRun.setCreator(curUser.getFullname());
		processRun.setAppUser(curUser);
		processRun.setCreatetime(curDate);
		processRun.setProDefinition(proDefinition);

		return processRun;
	}

	public void saveProcessRun(ProcessRun processRun, ProcessForm processForm,
			FlowRunInfo runInfo) {
		Map variables = new HashMap();

		variables.putAll(runInfo.getVariables());

		save(processRun);

		boolean isNewsForm = processForm.getFormId() == null;

		if (isNewsForm) {
			AppUser curUser = ContextUtil.getCurrentUser();

			processForm.setCreatorId(curUser.getUserId());
			processForm.setCreatorName(curUser.getFullname());
		}

		this.processFormDao.save(processForm);

		Iterator fieldNames = runInfo.getParamFields().keySet()
				.iterator();

		while (fieldNames.hasNext()) {
			String fieldName = (String) fieldNames.next();
			ParamField paramField = (ParamField) runInfo
					.getParamFields().get(fieldName);
			FormData fd = null;
			if (!isNewsForm) {
				/* 129 */fd = this.formDataDao.getByFormIdFieldName(
						processForm.getFormId(), fieldName);
				/* 130 */fd.copyValue(paramField);
			} else {
				/* 132 */fd = new FormData(paramField);
			}
			/* 134 */fd.setProcessForm(processForm);

			/* 136 */if (runInfo.isStartFlow()) {
				/* 137 */variables.put(fieldName, fd.getValue());
			}

			/* 140 */this.formDataDao.save(fd);
		}

		/* 143 */if (runInfo.isStartFlow()) {
			/* 145 */variables.put("flowStartUser",
					ContextUtil.getCurrentUser());

			/* 147 */variables.put("processName", processRun.getProDefinition()
					.getName());

			/* 149 */String piId = this.jbpmService.startProcess(processRun
					.getProDefinition().getDeployId(), variables);
			/* 150 */processRun.setRunStatus(ProcessRun.RUN_STATUS_RUNNING);
			/* 151 */processRun.setPiId(piId);
			/* 152 */save(processRun);
		}
	}

	public void saveAndNextStep(FlowRunInfo runInfo) {
		ProcessInstance pi;
		/* 166 */if (StringUtils.isNotEmpty(runInfo.getTaskId()))
			/* 167 */pi = this.jbpmService.getProcessInstanceByTaskId(runInfo
					.getTaskId());
		else {
			/* 169 */pi = this.jbpmService
					.getProcessInstance(runInfo.getPiId());
		}

		/* 172 */String xml = this.jbpmService.getDefinitionXmlByPiId(pi
				.getId());

		/* 174 */String nodeType = this.jbpmService.getNodeType(xml,
				runInfo.getActivityName());

		/* 176 */ProcessRun processRun = getByPiId(pi.getId());

		/* 179 */Integer maxSn = Integer.valueOf(this.processFormDao
				.getActvityExeTimes(processRun.getRunId(),
						runInfo.getActivityName()).intValue());
		/* 180 */ProcessForm processForm = new ProcessForm();
		/* 181 */processForm.setActivityName(runInfo.getActivityName());
		/* 182 */processForm.setSn(Integer.valueOf(maxSn.intValue() + 1));

		/* 184 */AppUser curUser = ContextUtil.getCurrentUser();

		/* 186 */processForm.setCreatorId(curUser.getUserId());
		/* 187 */processForm.setCreatorName(curUser.getFullname());

		/* 189 */processForm.setProcessRun(processRun);

		/* 191 */Map variables = runInfo.getVariables();

		/* 193 */Iterator it = runInfo.getParamFields().keySet().iterator();

		/* 195 */while (it.hasNext()) {
			/* 196 */String key = (String) it.next();
			/* 197 */ParamField paramField = (ParamField) runInfo
					.getParamFields().get(key);
			/* 198 */FormData fd = new FormData(paramField);
			/* 199 */fd.setProcessForm(processForm);

			/* 201 */variables.put(key, fd.getValue());
			/* 202 */processForm.getFormDatas().add(fd);
		}

		/* 205 */this.processFormDao.save(processForm);

		/* 208 */if ("task".equals(nodeType)) {
			/* 210 */this.jbpmService.completeTask(runInfo.getTaskId(),
					runInfo.getTransitionName(), runInfo.getDestName(),
					runInfo.getVariables());
		}
		/* 212 */else
			this.jbpmService.signalProcess(pi.getId(),
					runInfo.getTransitionName(), variables);
	}

	public void remove(Long runId) {
		ProcessRun processRun = (ProcessRun) this.dao.get(runId);
		if (ProcessRun.RUN_STATUS_INIT.equals(processRun
				.getRunStatus())) {
			List<ProcessForm> processForms = this.processFormDao
					.getByRunId(runId);
			for (ProcessForm processForm : processForms) {
				this.processFormDao.remove(processForm);
			}
		}
		this.dao.remove(processRun);
	}

	public void removeByDefId(Long defId) {
		List processRunList = this.dao.getByDefId(defId,
				new PagingBean(0, 25));
		for (int i = 0; i < processRunList.size(); i++) {
			this.dao.remove((ProcessRun) processRunList.get(i));
		}

		if (processRunList.size() == 25)
			removeByDefId(defId);
	}

	public List<ProcessRun> getByUserIdSubject(Long userId, String subject,
			PagingBean pb) {
		return this.dao.getByUserIdSubject(userId, subject, pb);
	}
}
