package com.xpsoft.oa.dao.system;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.system.AppRole;
import java.util.HashMap;
import java.util.Set;

public abstract interface AppRoleDao extends BaseDao<AppRole>
{
  public abstract AppRole getByRoleName(String paramString);

  public abstract HashMap<String, Set<String>> getSecurityDataSource();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.system.AppRoleDao
 * JD-Core Version:    0.6.0
 */