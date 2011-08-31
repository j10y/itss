package com.xpsoft.oa.action.flow;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ProcessInstance;

import com.xpsoft.core.util.AppUtil;
import com.xpsoft.core.web.action.BaseAction;
import com.xpsoft.oa.model.flow.ProcessForm;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.system.AppRole;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.flow.JbpmService;
import com.xpsoft.oa.service.flow.ProcessFormService;
import com.xpsoft.oa.service.flow.ProcessRunService;
import com.xpsoft.oa.service.system.AppUserService;

public class ProcessRunDetailAction extends BaseAction {

	@Resource
	private ProcessRunService processRunService;

	@Resource
	private ProcessFormService processFormService;

	@Resource
	private JbpmService jbpmService;
	@Resource
	private AppUserService appUserServcie;
	private Long runId;
	private Long taskId;

	public Long getRunId() {
		/* 35 */return this.runId;
	}

	public void setRunId(Long runId) {
		/* 39 */this.runId = runId;
	}

	public Long getTaskId() {
		/* 45 */return this.taskId;
	}

	public void setTaskId(Long taskId) {
		/* 49 */this.taskId = taskId;
	}

	public String execute() throws Exception {
		ProcessRun processRun = null;
		if (this.runId == null) {
			ProcessInstance pis = this.jbpmService
				.getProcessInstanceByTaskId(this.taskId.toString());
			processRun = this.processRunService.getByPiId(pis.getId());
			getRequest().setAttribute("processRun", processRun);
			this.runId = processRun.getRunId();
		} else {
			processRun = (ProcessRun) this.processRunService
					.get(this.runId);
		}
		List pfList = this.processFormService.getByRunId(this.runId);
		
		//add by awen for change the his sort on 2011-08-31 begin
		//修改排序
		Iterator<ProcessForm> iterator = pfList.iterator();
		List<ProcessForm> tmp = new ArrayList(); 
		ProcessForm form = null;
		while(iterator.hasNext()){
			form = iterator.next();
			if(form.getActivityName().equals("分管或局领导签发")||form.getActivityName().equals("分管或主管领导批示")||form.getActivityName().equals("局长审批")){
				 iterator.remove();
				 tmp.add(form);
			}
		}
		System.out.println("-----------删除后SIZE:" + pfList.size());
		int i=0;
		Iterator<ProcessForm> iterator1 = tmp.iterator();
		List<ProcessForm> tmp1 = new ArrayList();
		while(iterator1.hasNext()){
			ProcessForm form1 = iterator1.next();
			AppUser user = appUserServcie.get(form1.getCreatorId());
			Set<AppRole> roles = user.getRoles();
			for(AppRole role : roles){
				if(role.getId().toString().equals(AppUtil.getPropertity("role.leaderId"))){
					iterator1.remove();
					tmp1.add(form1);
					break;
				}
			}
			
		}
		tmp.addAll(0, tmp1);
		pfList.addAll(0, tmp);
		System.out.println("------------增加后SIZE:" + pfList.size());
		//add by awen for change the his sort on 2011-08-31 end
		
		getRequest().setAttribute("pfList", pfList);

		return "success";
	}
}
