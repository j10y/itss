package com.xpsoft.oa.service.personal;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.personal.Duty;
import java.util.Date;

public abstract interface DutyService extends BaseService<Duty>
{
  public abstract boolean isExistDutyForUser(Long paramLong, Date paramDate1, Date paramDate2);

  public abstract Duty getCurUserDuty(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.personal.DutyService
 * JD-Core Version:    0.6.0
 */