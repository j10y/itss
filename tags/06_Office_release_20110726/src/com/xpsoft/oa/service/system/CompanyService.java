package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.Company;
import java.util.List;

public abstract interface CompanyService extends BaseService<Company>
{
  public abstract List<Company> findByHql(String paramString);

  public abstract List<Company> findCompany();
}
