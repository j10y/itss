package com.xpsoft.oa.dao.communicate;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.communicate.MailBox;
import java.util.List;

public abstract interface MailBoxDao extends BaseDao<MailBox>
{
  public abstract Long CountByFolderId(Long paramLong);

  public abstract List<MailBox> findByFolderId(Long paramLong);

  public abstract List<MailBox> findBySearch(String paramString, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.communicate.MailBoxDao
 * JD-Core Version:    0.6.0
 */