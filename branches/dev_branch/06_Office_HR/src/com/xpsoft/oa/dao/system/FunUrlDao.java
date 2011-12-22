package com.xpsoft.oa.dao.system;

import java.util.Set;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.system.FunUrl;

public abstract interface FunUrlDao extends BaseDao<FunUrl>
{
  public abstract FunUrl getByPathFunId(String paramString, Long paramLong);
  public abstract Set<String> getAdminDataSource();
}
