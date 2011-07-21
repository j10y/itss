package com.xpsoft.oa.service.flow;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.flow.ProUserAssign;
import java.util.List;

public abstract interface ProUserAssignService extends BaseService<ProUserAssign>
{
  public abstract List<ProUserAssign> getByDeployId(String paramString);

  public abstract ProUserAssign getByDeployIdActivityName(String paramString1, String paramString2);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.flow.ProUserAssignService
 * JD-Core Version:    0.6.0
 */