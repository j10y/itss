package com.xpsoft.oa.dao.system;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.system.Region;
import java.util.List;

public abstract interface RegionDao extends BaseDao<Region>
{
  public abstract List<Region> getProvince();

  public abstract List<Region> getCity(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.system.RegionDao
 * JD-Core Version:    0.6.0
 */