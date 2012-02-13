package com.xpsoft.oa.dao.document;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.document.DocFolder;
import java.util.List;

public abstract interface DocFolderDao extends BaseDao<DocFolder>
{
  public abstract List<DocFolder> getUserFolderByParentId(Long paramLong1, Long paramLong2);

  public abstract List<DocFolder> getPublicFolderByParentId(Long paramLong);

  public abstract List<DocFolder> getFolderLikePath(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.document.DocFolderDao
 * JD-Core Version:    0.6.0
 */