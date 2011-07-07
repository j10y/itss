package com.htsoft.oa.dao.archive;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.oa.model.archive.ArchivesHandle;
import java.util.List;

public abstract interface ArchivesHandleDao extends BaseDao<ArchivesHandle>
{
  public abstract ArchivesHandle findByUAIds(Long paramLong1, Long paramLong2);

  public abstract List<ArchivesHandle> findByAid(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.archive.ArchivesHandleDao
 * JD-Core Version:    0.6.0
 */