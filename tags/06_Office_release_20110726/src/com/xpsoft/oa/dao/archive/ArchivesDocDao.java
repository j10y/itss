package com.xpsoft.oa.dao.archive;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.archive.ArchivesDoc;
import java.util.List;

public abstract interface ArchivesDocDao extends BaseDao<ArchivesDoc>
{
  public abstract List<ArchivesDoc> findByAid(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.archive.ArchivesDocDao
 * JD-Core Version:    0.6.0
 */