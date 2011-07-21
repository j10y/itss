package com.xpsoft.oa.dao.task;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.task.WorkPlan;
import java.util.List;

public abstract interface WorkPlanDao extends BaseDao<WorkPlan>
{
  public abstract List<WorkPlan> findByDepartment(WorkPlan paramWorkPlan, AppUser paramAppUser, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.task.WorkPlanDao
 * JD-Core Version:    0.6.0
 */