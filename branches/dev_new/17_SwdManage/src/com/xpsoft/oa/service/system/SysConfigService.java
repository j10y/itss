package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.SysConfig;
import java.util.Map;

public abstract interface SysConfigService extends BaseService<SysConfig>
{
  public abstract SysConfig findByKey(String paramString);

  public abstract Map findByType();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.SysConfigService
 * JD-Core Version:    0.6.0
 */