package com.xpsoft.oa.dao.personal;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.personal.DutySystem;
import java.util.List;

public abstract interface DutySystemDao extends BaseDao<DutySystem>
{
  public abstract void updateForNotDefult();

  public abstract List<DutySystem> getDefaultDutySystem();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.personal.DutySystemDao
 * JD-Core Version:    0.6.0
 */