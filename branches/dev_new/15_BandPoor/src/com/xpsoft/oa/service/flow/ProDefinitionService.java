package com.xpsoft.oa.service.flow;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.flow.ProDefinition;

public abstract interface ProDefinitionService extends BaseService<ProDefinition>
{
  public abstract ProDefinition getByDeployId(String paramString);

  public abstract ProDefinition getByName(String paramString);
}
