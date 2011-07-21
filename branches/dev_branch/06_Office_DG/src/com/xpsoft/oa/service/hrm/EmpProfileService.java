package com.xpsoft.oa.service.hrm;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.EmpProfile;

public abstract interface EmpProfileService extends BaseService<EmpProfile>
{
  public abstract boolean checkProfileNo(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.hrm.EmpProfileService
 * JD-Core Version:    0.6.0
 */