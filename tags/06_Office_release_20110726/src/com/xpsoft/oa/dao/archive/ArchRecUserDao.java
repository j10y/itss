package com.xpsoft.oa.dao.archive;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.archive.ArchRecUser;
import java.util.List;

public abstract interface ArchRecUserDao extends BaseDao<ArchRecUser>
{
  public abstract List findDepAll();

  public abstract ArchRecUser getByDepId(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.archive.ArchRecUserDao
 * JD-Core Version:    0.6.0
 */