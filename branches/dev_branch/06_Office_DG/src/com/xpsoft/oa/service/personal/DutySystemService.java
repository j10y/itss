package com.xpsoft.oa.service.personal;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.personal.DutySystem;

public abstract interface DutySystemService extends BaseService<DutySystem>
{
  public abstract DutySystem getDefaultDutySystem();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.personal.DutySystemService
 * JD-Core Version:    0.6.0
 */