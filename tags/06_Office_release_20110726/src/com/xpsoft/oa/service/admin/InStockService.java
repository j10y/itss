package com.xpsoft.oa.service.admin;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.admin.InStock;

public abstract interface InStockService extends BaseService<InStock>
{
  public abstract Integer findInCountByBuyId(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.InStockService
 * JD-Core Version:    0.6.0
 */