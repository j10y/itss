package com.xpsoft.oa.service.hrm;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.hrm.SalaryItem;
import java.util.List;

public abstract interface SalaryItemService extends BaseService<SalaryItem>
{
  public abstract List<SalaryItem> getAllExcludeId(String paramString, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.hrm.SalaryItemService
 * JD-Core Version:    0.6.0
 */