package com.xpsoft.oa.service.archive;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.archive.ArchHasten;
import java.util.Date;

public abstract interface ArchHastenService extends BaseService<ArchHasten>
{
  public abstract Date getLeastRecordByUser(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.archive.ArchHastenService
 * JD-Core Version:    0.6.0
 */