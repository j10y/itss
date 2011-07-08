package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.FunUrl;

public abstract interface FunUrlService extends BaseService<FunUrl>
{
  public abstract FunUrl getByPathFunId(String paramString, Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.FunUrlService
 * JD-Core Version:    0.6.0
 */