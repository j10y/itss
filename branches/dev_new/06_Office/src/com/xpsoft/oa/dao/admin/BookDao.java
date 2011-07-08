package com.xpsoft.oa.dao.admin;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.admin.Book;
import java.util.List;

public abstract interface BookDao extends BaseDao<Book>
{
  public abstract List<Book> findByTypeId(Long paramLong, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.admin.BookDao
 * JD-Core Version:    0.6.0
 */