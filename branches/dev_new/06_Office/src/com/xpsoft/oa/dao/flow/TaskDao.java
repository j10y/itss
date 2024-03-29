package com.xpsoft.oa.dao.flow;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.flow.JbpmTask;
import java.util.List;
import org.jbpm.pvm.internal.task.TaskImpl;

public abstract interface TaskDao extends BaseDao<TaskImpl>
{
  public abstract List<TaskImpl> getTasksByUserId(String paramString, PagingBean paramPagingBean);

  public abstract List<JbpmTask> getByActivityNameVarKeyLongVal(String paramString1, String paramString2, Long paramLong);

  public abstract List<Long> getGroupByTask(Long paramLong);

  public abstract List<Long> getUserIdByTask(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.flow.TaskDao
 * JD-Core Version:    0.6.0
 */