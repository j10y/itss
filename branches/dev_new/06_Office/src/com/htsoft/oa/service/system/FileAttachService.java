package com.htsoft.oa.service.system;

import com.htsoft.core.service.BaseService;
import com.htsoft.oa.model.system.FileAttach;

public abstract interface FileAttachService extends BaseService<FileAttach>
{
  public abstract void removeByPath(String paramString);

  public abstract FileAttach getByPath(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.system.FileAttachService
 * JD-Core Version:    0.6.0
 */