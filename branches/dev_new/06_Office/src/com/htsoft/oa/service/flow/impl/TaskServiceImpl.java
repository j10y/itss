package com.htsoft.oa.service.flow.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.jbpm.api.ExecutionService;
import org.jbpm.pvm.internal.task.TaskImpl;

import com.htsoft.core.jbpm.pv.TaskInfo;
import com.htsoft.core.service.impl.BaseServiceImpl;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.dao.flow.TaskDao;
import com.htsoft.oa.model.flow.JbpmTask;
import com.htsoft.oa.model.flow.ProcessRun;
import com.htsoft.oa.model.system.AppUser;
import com.htsoft.oa.service.flow.ProcessRunService;
import com.htsoft.oa.service.flow.TaskService;
import com.htsoft.oa.service.system.AppUserService;

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
		/* 50 */List<TaskImpl> list = getTasksByUserId(userId, pb);
		/* 51 */List taskInfoList = new ArrayList();
		/* 52 */for (TaskImpl taskImpl : list) {
			/* 53 */TaskInfo taskInfo = new TaskInfo(taskImpl);
			/* 54 */if (taskImpl.getAssignee() != null) {
				/* 55 */AppUser user = (AppUser) this.appUserService
						.get(new Long(taskImpl.getAssignee()));
				/* 56 */taskInfo.setAssignee(user.getFullname());
			}

			/* 59 */ProcessRun processRun = this.processRunService
					.getByPiId(taskImpl.getExecutionId());
			/* 60 */if (processRun != null) {
				/* 61 */taskInfo.setTaskName(processRun.getProDefinition()
						.getName() + "--" + taskImpl.getActivityName());
				/* 62 */taskInfo.setActivityName(taskImpl.getActivityName());
			}

			/* 65 */taskInfoList.add(taskInfo);
		}
		/* 67 */return taskInfoList;
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
				/* 79 */List<Long> groupList = this.dao.getGroupByTask(jtask
						.getTaskId());
				/* 80 */for (Long l : groupList) {
					/* 81 */List<AppUser> uList = this.appUserService
							.findByRoleId(l);
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
