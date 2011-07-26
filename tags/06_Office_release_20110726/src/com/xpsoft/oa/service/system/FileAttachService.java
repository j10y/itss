package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.FileAttach;

public abstract interface FileAttachService extends BaseService<FileAttach>
{
  public abstract void removeByPath(String paramString);

  public abstract FileAttach getByPath(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.FileAttachService
 * JD-Core Version:    0.6.0
 */