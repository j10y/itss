package com.xpsoft.oa.service.customer;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.customer.Project;

public abstract interface ProjectService extends BaseService<Project>
{
  public abstract boolean checkProjectNo(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.customer.ProjectService
 * JD-Core Version:    0.6.0
 */