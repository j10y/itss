package com.xpsoft.oa.dao.hrm;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.hrm.EmpProfile;

public abstract interface EmpProfileDao extends BaseDao<EmpProfile>
{
  public abstract boolean checkProfileNo(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.hrm.EmpProfileDao
 * JD-Core Version:    0.6.0
 */