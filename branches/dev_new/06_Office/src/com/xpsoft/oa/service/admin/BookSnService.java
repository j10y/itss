package com.xpsoft.oa.service.admin;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.admin.BookSn;
import java.util.List;

public abstract interface BookSnService extends BaseService<BookSn>
{
  public abstract List<BookSn> findByBookId(Long paramLong);

  public abstract List<BookSn> findBorrowByBookId(Long paramLong);

  public abstract List<BookSn> findReturnByBookId(Long paramLong);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.admin.BookSnService
 * JD-Core Version:    0.6.0
 */