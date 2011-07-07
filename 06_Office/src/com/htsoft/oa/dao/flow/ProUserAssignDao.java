package com.htsoft.oa.dao.flow;

import com.htsoft.core.dao.BaseDao;
import com.htsoft.oa.model.flow.ProUserAssign;
import java.util.List;

public abstract interface ProUserAssignDao extends BaseDao<ProUserAssign>
{
  public abstract List<ProUserAssign> getByDeployId(String paramString);

  public abstract ProUserAssign getByDeployIdActivityName(String paramString1, String paramString2);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.dao.flow.ProUserAssignDao
 * JD-Core Version:    0.6.0
 */