package com.xpsoft.oa.dao.info;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.Notice;
import java.util.Date;
import java.util.List;

public abstract interface NoticeDao extends BaseDao<Notice>
{
  public abstract List<Notice> findByNoticeId(Long paramLong, PagingBean paramPagingBean);

  public abstract List<Notice> findBySearch(Notice paramNotice, Date paramDate1, Date paramDate2, PagingBean paramPagingBean);

  public abstract List<Notice> findBySearch(String paramString, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.info.NoticeDao
 * JD-Core Version:    0.6.0
 */