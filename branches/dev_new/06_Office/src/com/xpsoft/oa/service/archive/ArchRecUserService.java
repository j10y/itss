package com.xpsoft.oa.service.archive;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.archive.ArchRecUser;
import java.util.List;

public abstract interface ArchRecUserService extends BaseService<ArchRecUser>
{
  public abstract List findDepAll();

  public abstract ArchRecUser getByDepId(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.archive.ArchRecUserService
 * JD-Core Version:    0.6.0
 */