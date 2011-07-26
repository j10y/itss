package com.xpsoft.oa.dao.personal;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.personal.DutyRegister;

public abstract interface DutyRegisterDao extends BaseDao<DutyRegister>
{
  public abstract DutyRegister getTodayUserRegister(Long paramLong1, Short paramShort, Long paramLong2);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.personal.DutyRegisterDao
 * JD-Core Version:    0.6.0
 */