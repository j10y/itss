package com.xpsoft.oa.dao.admin;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.admin.InStock;

public abstract interface InStockDao extends BaseDao<InStock>
{
  public abstract Integer findInCountByBuyId(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.admin.InStockDao
 * JD-Core Version:    0.6.0
 */