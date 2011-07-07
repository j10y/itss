package com.htsoft.oa.service.flow;

import com.htsoft.core.service.BaseService;
import com.htsoft.oa.model.flow.FormData;
import java.util.Map;

public abstract interface FormDataService extends BaseService<FormData>
{
  public abstract Map<String, Object> getFromDataMap(Long paramLong, String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.flow.FormDataService
 * JD-Core Version:    0.6.0
 */