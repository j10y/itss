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
		/* 45 */super(dao);
		/* 46 */this.dao = dao;
	}

	public ProcessRun getByExeId(String exeId) {
		/* 55 */ProcessInstance pi = this.jbpmService
				.getProcessInstanceByExeId(exeId);
		/* 56 */if (pi != null) {
			/* 57 */return getByPiId(pi.getId());
		}
		/* 59 */return null;
	}

	public ProcessRun getByTaskId(String taskId) {
		/* 63 */ProcessInstance pi = this.jbpmService
				.getProcessInstanceByTaskId(taskId);
		/* 64 */if (pi != null) {
			/* 65 */return getByPiId(pi.getId());
		}
		/* 67 */return null;
	}

	public ProcessRun getByPiId(String piId) {
		/* 71 */return this.dao.getByPiId(piId);
	}

	public ProcessRun initNewProcessRun(ProDefinition proDefinition) {
		/* 80 */ProcessRun processRun = new ProcessRun();
		/* 81 */AppUser curUser = ContextUtil.getCurrentUser();

		/* 83 */Date curDate = new Date();
		/* 84 */SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd-HHmmss");

		/* 86 */processRun.setSubject(proDefinition.getName()
				+ sdf.format(curDate) + "(" + curUser.getFullname() + ")");
		/* 87 */processRun.setCreator(curUser.getFullname());
		/* 88 */processRun.setAppUser(curUser);
		/* 89 */processRun.setCreatetime(curDate);
		/* 90 */processRun.setProDefinition(proDefinition);

		/* 92 */return processRun;
	}

	public void saveProcessRun(ProcessRun processRun, ProcessForm processForm,
			FlowRunInfo runInfo) {
		/* 102 */Map variables = new HashMap();

		/* 105 */variables.putAll(runInfo.getVariables());

		/* 108 */save(processRun);

		/* 110 */boolean isNewsForm = processForm.getFormId() == null;

		/* 112 */if (isNewsForm) {
			/* 113 */AppUser curUser = ContextUtil.getCurrentUser();

			/* 115 */processForm.setCreatorId(curUser.getUserId());
			/* 116 */processForm.setCreatorName(curUser.getFullname());
		}

		/* 120 */this.processFormDao.save(processForm);

		/* 122 */Iterator fieldNames = runInfo.getParamFields().keySet()
				.iterator();

		/* 124 */while (fieldNames.hasNext()) {
			/* 125 */String fieldName = (String) fieldNames.next();
			/* 126 */ParamField paramField = (ParamField) runInfo
					.getParamFields().get(fieldName);
			/* 127 */FormData fd = null;
			/* 128 */if (!isNewsForm) {
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
		/* 220 */ProcessRun processRun = (ProcessRun) this.dao.get(runId);
		/* 221 */if (ProcessRun.RUN_STATUS_INIT.equals(processRun
				.getRunStatus())) {
			/* 222 */List<ProcessForm> processForms = this.processFormDao
					.getByRunId(runId);
			/* 223 */for (ProcessForm processForm : processForms) {
				/* 224 */this.processFormDao.remove(processForm);
			}
		}
		/* 227 */this.dao.remove(processRun);
	}

	public void removeByDefId(Long defId) {
		/* 236 */List processRunList = this.dao.getByDefId(defId,
				new PagingBean(0, 25));
		/* 237 */for (int i = 0; i < processRunList.size(); i++) {
			/* 238 */this.dao.remove((ProcessRun) processRunList.get(i));
		}

		/* 241 */if (processRunList.size() == 25)
			/* 242 */removeByDefId(defId);
	}

	public List<ProcessRun> getByUserIdSubject(Long userId, String subject,
			PagingBean pb) {
		/* 254 */return this.dao.getByUserIdSubject(userId, subject, pb);
	}
}

/*
 * Location:
 * C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6
 * -joffice\webapps\joffice1.3.1\WEB-INF\classes\ Qualified Name:
 * com.xpsoft.oa.service.flow.impl.ProcessRunServiceImpl JD-Core Version: 0.6.0
 */