package com.xpsoft.oa.service.customer;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.customer.CusLinkman;

public abstract interface CusLinkmanService extends BaseService<CusLinkman>
{
  public abstract boolean checkMainCusLinkman(Long paramLong1, Long paramLong2);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.customer.CusLinkmanService
 * JD-Core Version:    0.6.0
 */