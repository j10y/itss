package com.xpsoft.oa.service.flow.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ExecutionService;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.xpsoft.core.jbpm.pv.TaskInfo;
import com.xpsoft.core.service.impl.BaseServiceImpl;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.dao.flow.TaskDao;
import com.xpsoft.oa.model.flow.JbpmTask;
import com.xpsoft.oa.model.flow.ProcessRun;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.service.flow.ProcessRunService;
import com.xpsoft.oa.service.flow.TaskService;
import com.xpsoft.oa.service.system.AppUserService;

public class TaskServiceImpl extends BaseServiceImpl<TaskImpl> implements
		TaskService {

	@Resource
	private ExecutionService executionService;

	@Resource
	private ProcessRunService processRunService;
	private TaskDao dao;

	@Resource
	private AppUserService appUserService;

	public TaskServiceImpl(TaskDao dao) {
		/* 35 */super(dao);
		/* 36 */this.dao = dao;
	}

	public List<TaskImpl> getTasksByUserId(String userId, PagingBean pb) {
		/* 43 */return this.dao.getTasksByUserId(userId, pb);
	}

	public List<TaskInfo> getTaskInfosByUserId(String userId, PagingBean pb) {
		List<TaskImpl> list = getTasksByUserId(userId, pb);
		List taskInfoList = new ArrayList();
		for (TaskImpl taskImpl : list) {
			TaskInfo taskInfo = new TaskInfo(taskImpl);
			if (taskImpl.getAssignee() != null) {
				AppUser user = (AppUser) this.appUserService
						.get(new Long(taskImpl.getAssignee()));
				taskInfo.setAssignee(user.getFullname());
			}

			ProcessRun processRun = this.processRunService
					.getByPiId(taskImpl.getExecutionId());
			if (processRun != null) {
				taskInfo.setTaskName(processRun.getProDefinition()
						.getName() + "--" + taskImpl.getActivityName());
				taskInfo.setActivityName(taskImpl.getActivityName());
			}

			taskInfoList.add(taskInfo);
		}
		return taskInfoList;
	}

	public Set<Long> getHastenByActivityNameVarKeyLongVal(String activityName,
			String varKey, Long value) {
		/* 73 */List<JbpmTask> jtasks = this.dao
				.getByActivityNameVarKeyLongVal(activityName, varKey, value);
		/* 74 */Set userIds = new HashSet();
		/* 75 */for (JbpmTask jtask : jtasks) {
			/* 76 */if (jtask.getAssignee() == null) {
				/* 77 */List userlist = this.dao.getUserIdByTask(jtask
						.getTaskId());
				/* 78 */userIds.addAll(userlist);
				/* 79 */List groupList = this.dao.getGroupByTask(jtask
						.getTaskId());
				/* 80 */for (Object l : groupList) {
					/* 81 */List<AppUser> uList = this.appUserService
							.findByRoleId(new Long(l.toString()));
					/* 82 */List idList = new ArrayList();
					/* 83 */for (AppUser appUser : uList) {
						/* 84 */idList.add(appUser.getUserId());
					}
					/* 86 */userIds.addAll(idList);
				}
			} else {
				/* 89 */userIds.add(new Long(jtask.getAssignee()));
			}
		}
		/* 92 */return userIds;
	}

}
