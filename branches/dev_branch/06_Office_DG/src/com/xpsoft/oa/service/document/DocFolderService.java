package com.xpsoft.oa.service.document;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.document.DocFolder;
import java.util.List;

public abstract interface DocFolderService extends BaseService<DocFolder>
{
  public abstract List<DocFolder> getUserFolderByParentId(Long paramLong1, Long paramLong2);

  public abstract List<DocFolder> getFolderLikePath(String paramString);

  public abstract List<DocFolder> getPublicFolderByParentId(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.document.DocFolderService
 * JD-Core Version:    0.6.0
 */