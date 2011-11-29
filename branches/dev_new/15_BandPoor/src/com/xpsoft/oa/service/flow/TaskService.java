package com.xpsoft.oa.service.flow;

import com.xpsoft.core.jbpm.pv.TaskInfo;
import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import java.util.List;
import java.util.Set;
import org.jbpm.pvm.internal.task.TaskImpl;

public abstract interface TaskService extends BaseService<TaskImpl>
{
  public abstract List<TaskImpl> getTasksByUserId(String paramString, PagingBean paramPagingBean);

  public abstract List<TaskInfo> getTaskInfosByUserId(String paramString, PagingBean paramPagingBean);

  public abstract Set<Long> getHastenByActivityNameVarKeyLongVal(String paramString1, String paramString2, Long paramLong);
}
