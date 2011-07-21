package com.xpsoft.oa.dao.hrm;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.hrm.Job;
import java.util.List;

public abstract interface JobDao extends BaseDao<Job>
{
  public abstract List<Job> findByDep(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.hrm.JobDao
 * JD-Core Version:    0.6.0
 */