package com.xpsoft.oa.service.task;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.task.PlanAttend;

public abstract interface PlanAttendService extends BaseService<PlanAttend>
{
  public abstract boolean deletePlanAttend(Long paramLong, Short paramShort1, Short paramShort2);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.task.PlanAttendService
 * JD-Core Version:    0.6.0
 */