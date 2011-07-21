package com.xpsoft.oa.service.admin;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.admin.DepreRecord;
import java.util.Date;

public abstract interface DepreRecordService extends BaseService<DepreRecord>
{
  public abstract Date findMaxDate(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.DepreRecordService
 * JD-Core Version:    0.6.0
 */