package com.xpsoft.oa.service.task;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.task.Appointment;
import java.util.List;

public abstract interface AppointmentService extends BaseService<Appointment>
{
  public abstract List showAppointmentByUserId(Long paramLong, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.task.AppointmentService
 * JD-Core Version:    0.6.0
 */