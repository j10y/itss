package com.htsoft.oa.service.hrm;

import com.htsoft.core.service.BaseService;
import com.htsoft.core.web.paging.PagingBean;
import com.htsoft.oa.model.hrm.SalaryItem;
import java.util.List;

public abstract interface SalaryItemService extends BaseService<SalaryItem>
{
  public abstract List<SalaryItem> getAllExcludeId(String paramString, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.htsoft.oa.service.hrm.SalaryItemService
 * JD-Core Version:    0.6.0
 */