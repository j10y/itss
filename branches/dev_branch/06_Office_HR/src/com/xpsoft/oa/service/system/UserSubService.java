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

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.UserSubService
 * JD-Core Version:    0.6.0
 */