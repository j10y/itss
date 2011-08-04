package com.xpsoft.oa.dao.system;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.system.AppUser;
import com.xpsoft.oa.model.system.Department;
import java.util.List;
import java.util.Set;

public abstract interface AppUserDao extends BaseDao<AppUser>
{
  public abstract AppUser findByUserName(String paramString);

  public abstract List findByDepartment(String paramString, PagingBean paramPagingBean);

  public abstract List findByDepartment(String paramString);

  public abstract List findByDepartment(Department paramDepartment);

  public abstract List findByRole(Long paramLong);

  public abstract List findByRole(Long paramLong, PagingBean paramPagingBean);

  public abstract List findByRoleId(Long paramLong);

  public abstract List<AppUser> findSubAppUser(String paramString, Set<Long> paramSet, PagingBean paramPagingBean);

  public abstract List<AppUser> findSubAppUserByRole(Long paramLong, Set<Long> paramSet, PagingBean paramPagingBean);

  public abstract List<AppUser> findByDepId(Long paramLong);

  public abstract List findByRoleIds(Long[] roleIds);
}
