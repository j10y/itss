package com.xpsoft.oa.service.flow;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.flow.ProDefinition;

public abstract interface ProDefinitionService extends BaseService<ProDefinition>
{
  public abstract ProDefinition getByDeployId(String paramString);

  public abstract ProDefinition getByName(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.flow.ProDefinitionService
 * JD-Core Version:    0.6.0
 */