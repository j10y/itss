package com.xpsoft.oa.dao.system;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.system.SysConfig;
import java.util.List;

public abstract interface SysConfigDao extends BaseDao<SysConfig>
{
  public abstract SysConfig findByKey(String paramString);

  public abstract List<SysConfig> findConfigByTypeName(String paramString);

  public abstract List findTypeNames();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.system.SysConfigDao
 * JD-Core Version:    0.6.0
 */