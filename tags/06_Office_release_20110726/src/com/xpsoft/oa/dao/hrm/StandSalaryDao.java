package com.xpsoft.oa.dao.hrm;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.hrm.StandSalary;
import java.util.List;

public abstract interface StandSalaryDao extends BaseDao<StandSalary>
{
  public abstract boolean checkStandNo(String paramString);

  public abstract List<StandSalary> findByPassCheck();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.hrm.StandSalaryDao
 * JD-Core Version:    0.6.0
 */