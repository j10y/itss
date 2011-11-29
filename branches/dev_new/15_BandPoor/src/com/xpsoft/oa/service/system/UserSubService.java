package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.UserSub;
import java.util.List;
import java.util.Set;

public abstract interface UserSubService extends BaseService<UserSub>
{
  public abstract Set<Long> findAllUpUser(Long paramLong);

  public abstract List<Long> subUsers(Long paramLong);

  public abstract List<Long> upUser(Long paramLong);
}
