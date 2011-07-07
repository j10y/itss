package com.htsoft.oa.service.system;

import com.htsoft.core.service.BaseService;
import com.htsoft.oa.model.system.AppFunction;

public abstract interface AppFunctionService extends BaseService<AppFunction>
{
  public abstract AppFunction getByKey(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.system.AppFunctionService
 * JD-Core Version:    0.6.0
 */