package com.xpsoft.oa.dao.archive;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.archive.ArchivesHandle;
import java.util.List;

public abstract interface ArchivesHandleDao extends BaseDao<ArchivesHandle>
{
  public abstract ArchivesHandle findByUAIds(Long paramLong1, Long paramLong2);

  public abstract List<ArchivesHandle> findByAid(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.archive.ArchivesHandleDao
 * JD-Core Version:    0.6.0
 */