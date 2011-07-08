package com.xpsoft.oa.dao.customer;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.customer.CusLinkman;

public abstract interface CusLinkmanDao extends BaseDao<CusLinkman>
{
  public abstract boolean checkMainCusLinkman(Long paramLong1, Long paramLong2);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.customer.CusLinkmanDao
 * JD-Core Version:    0.6.0
 */