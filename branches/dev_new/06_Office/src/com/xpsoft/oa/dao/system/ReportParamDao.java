package com.xpsoft.oa.dao.system;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.system.ReportParam;
import java.util.List;

public abstract interface ReportParamDao extends BaseDao<ReportParam>
{
  public abstract List<ReportParam> findByRepTemp(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.system.ReportParamDao
 * JD-Core Version:    0.6.0
 */