package com.xpsoft.oa.service.system;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.system.Region;
import java.util.List;

public abstract interface RegionService extends BaseService<Region>
{
  public abstract List<Region> getProvince();

  public abstract List<Region> getCity(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.system.RegionService
 * JD-Core Version:    0.6.0
 */