package com.xpsoft.oa.dao.admin;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.admin.OfficeGoods;
import java.util.List;

public abstract interface OfficeGoodsDao extends BaseDao<OfficeGoods>
{
  public abstract List<OfficeGoods> findByWarm();
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.admin.OfficeGoodsDao
 * JD-Core Version:    0.6.0
 */