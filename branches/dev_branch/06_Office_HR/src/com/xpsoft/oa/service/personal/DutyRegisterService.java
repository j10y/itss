package com.xpsoft.oa.service.personal;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.personal.DutyRegister;
import com.xpsoft.oa.model.system.AppUser;
import java.util.Date;

public abstract interface DutyRegisterService extends BaseService<DutyRegister>
{
  public abstract void signInOff(Long paramLong, Short paramShort, AppUser paramAppUser, Date paramDate);

  public abstract DutyRegister getTodayUserRegister(Long paramLong1, Short paramShort, Long paramLong2);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.personal.DutyRegisterService
 * JD-Core Version:    0.6.0
 */