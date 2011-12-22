package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.AppFunction;

public abstract interface AppFunctionService extends BaseService<AppFunction>
{
  public abstract AppFunction getByKey(String paramString);
  
  public void updateFunUrl(String[] urls,Long functionId);
}
