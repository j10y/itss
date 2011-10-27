package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppUser;
import java.util.List;
import java.util.Set;

public abstract interface AppUserService extends BaseService<AppUser>
{
  public abstract AppUser findByUserName(String paramString);
  
  public abstract AppUser getByUserName(String paramString);
  
  public abstract AppUser findByFullName(String paramString);

  public abstract List findByDepartment(String paramString, PagingBean paramPagingBean);

  public abstract List findByRole(Long paramLong, PagingBean paramPagingBean);

  public abstract List findByRoleId(Long paramLong);

  public abstract List<AppUser> findSubAppUser(String paramString, Set<Long> paramSet, PagingBean paramPagingBean);

  public abstract List<AppUser> findSubAppUserByRole(Long paramLong, Set<Long> paramSet, PagingBean paramPagingBean);

  public abstract List<AppUser> findByDepId(Long paramLong);
  
  public abstract List<AppUser> findByDepIdAndPostName(Long paramLong, String paramString);
  
  public abstract List findByRoleIds(Long [] roleIds);

}
