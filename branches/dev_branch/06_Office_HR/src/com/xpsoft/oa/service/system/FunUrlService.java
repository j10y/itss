package com.xpsoft.oa.service.system;

import java.util.List;
import java.util.Set;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.FunUrl;

public abstract interface FunUrlService extends BaseService<FunUrl>
{
  public abstract FunUrl getByPathFunId(String paramString, Long paramLong);
  public abstract Set<String> getAdminDataSource();
  public abstract List<FunUrl> getByFunId();
}
