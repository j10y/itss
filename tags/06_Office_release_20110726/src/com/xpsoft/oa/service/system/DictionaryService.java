package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.Dictionary;
import java.util.List;

public abstract interface DictionaryService extends BaseService<Dictionary>
{
  public abstract List<String> getAllItems();

  public abstract List<String> getAllByItemName(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.DictionaryService
 * JD-Core Version:    0.6.0
 */