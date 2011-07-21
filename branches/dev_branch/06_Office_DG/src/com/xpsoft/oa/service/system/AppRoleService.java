package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.AppRole;
import java.util.HashMap;
import java.util.Set;

public abstract interface AppRoleService extends BaseService<AppRole>
{
  public abstract AppRole getByRoleName(String paramString);

  public abstract HashMap<String, Set<String>> getSecurityDataSource();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.AppRoleService
 * JD-Core Version:    0.6.0
 */