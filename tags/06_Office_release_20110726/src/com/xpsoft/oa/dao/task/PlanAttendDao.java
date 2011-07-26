package com.xpsoft.oa.dao.task;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.task.PlanAttend;
import java.util.List;

public abstract interface PlanAttendDao extends BaseDao<PlanAttend>
{
  public abstract List<PlanAttend> FindPlanAttend(Long paramLong, Short paramShort1, Short paramShort2);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.task.PlanAttendDao
 * JD-Core Version:    0.6.0
 */