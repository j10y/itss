package com.htsoft.oa.dao.system;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.oa.model.system.FileAttach;

public abstract interface FileAttachDao extends BaseDao<FileAttach>
{
  public abstract void removeByPath(String paramString);

  public abstract FileAttach getByPath(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.system.FileAttachDao
 * JD-Core Version:    0.6.0
 */