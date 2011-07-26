package com.xpsoft.oa.dao.flow;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.flow.FormData;
import java.util.List;

public abstract interface FormDataDao extends BaseDao<FormData>
{
  public abstract List<FormData> getByRunIdActivityName(Long paramLong, String paramString);

  public abstract FormData getByFormIdFieldName(Long paramLong, String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.flow.FormDataDao
 * JD-Core Version:    0.6.0
 */