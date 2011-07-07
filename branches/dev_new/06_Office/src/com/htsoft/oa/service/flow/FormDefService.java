package com.htsoft.oa.service.flow;

import com.htsoft.core.service.BaseService;
import com.htsoft.oa.model.flow.FormDef;
import java.util.List;

public abstract interface FormDefService extends BaseService<FormDef>
{
  public abstract List<FormDef> getByDeployId(String paramString);

  public abstract FormDef getByDeployIdActivityName(String paramString1, String paramString2);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.flow.FormDefService
 * JD-Core Version:    0.6.0
 */