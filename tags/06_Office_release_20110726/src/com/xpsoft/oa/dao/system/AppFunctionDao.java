package com.xpsoft.oa.dao.system;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.system.AppFunction;

public abstract interface AppFunctionDao extends BaseDao<AppFunction>
{
  public abstract AppFunction getByKey(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.system.AppFunctionDao
 * JD-Core Version:    0.6.0
 */