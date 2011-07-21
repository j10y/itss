package com.xpsoft.oa.service.hrm;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.StandSalary;
import java.util.List;

public abstract interface StandSalaryService extends BaseService<StandSalary>
{
  public abstract boolean checkStandNo(String paramString);

  public abstract List<StandSalary> findByPassCheck();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.hrm.StandSalaryService
 * JD-Core Version:    0.6.0
 */