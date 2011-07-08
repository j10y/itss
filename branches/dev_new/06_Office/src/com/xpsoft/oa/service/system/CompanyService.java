package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.Company;
import java.util.List;

public abstract interface CompanyService extends BaseService<Company>
{
  public abstract List<Company> findByHql(String paramString);

  public abstract List<Company> findCompany();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.CompanyService
 * JD-Core Version:    0.6.0
 */