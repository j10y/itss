package com.xpsoft.oa.dao.customer;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.oa.model.customer.Customer;

public abstract interface CustomerDao extends BaseDao<Customer>
{
  public abstract boolean checkCustomerNo(String paramString);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.customer.CustomerDao
 * JD-Core Version:    0.6.0
 */