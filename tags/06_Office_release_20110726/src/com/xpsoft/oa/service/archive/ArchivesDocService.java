package com.xpsoft.oa.service.archive;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.archive.ArchivesDoc;
import java.util.List;

public abstract interface ArchivesDocService extends BaseService<ArchivesDoc>
{
  public abstract List<ArchivesDoc> findByAid(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.archive.ArchivesDocService
 * JD-Core Version:    0.6.0
 */