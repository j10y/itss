package com.xpsoft.oa.dao.admin;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.admin.BookBorRet;
import java.util.List;

public abstract interface BookBorRetDao extends BaseDao<BookBorRet>
{
  public abstract BookBorRet getByBookSnId(Long paramLong);

  public abstract List getBorrowInfo(PagingBean paramPagingBean);

  public abstract List getReturnInfo(PagingBean paramPagingBean);

  public abstract Long getBookBorRetId(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.admin.BookBorRetDao
 * JD-Core Version:    0.6.0
 */