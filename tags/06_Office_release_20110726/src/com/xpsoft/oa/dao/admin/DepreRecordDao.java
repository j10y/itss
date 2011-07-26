package com.xpsoft.oa.dao.admin;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.admin.DepreRecord;
import java.util.Date;

public abstract interface DepreRecordDao extends BaseDao<DepreRecord>
{
  public abstract Date findMaxDate(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.admin.DepreRecordDao
 * JD-Core Version:    0.6.0
 */