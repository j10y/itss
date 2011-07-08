package com.xpsoft.oa.service.hrm;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.hrm.StandSalaryItem;
import java.util.List;

public abstract interface StandSalaryItemService extends BaseService<StandSalaryItem>
{
  public abstract List<StandSalaryItem> getAllByStandardId(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.hrm.StandSalaryItemService
 * JD-Core Version:    0.6.0
 */