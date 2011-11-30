package com.xpsoft.oa.dao.system;

import java.util.Set;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.system.FunUrl;

public abstract interface FunUrlDao extends BaseDao<FunUrl>
{
  public abstract FunUrl getByPathFunId(String paramString, Long paramLong);
  public abstract Set<String> getAdminDataSource();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.system.FunUrlDao
 * JD-Core Version:    0.6.0
 */