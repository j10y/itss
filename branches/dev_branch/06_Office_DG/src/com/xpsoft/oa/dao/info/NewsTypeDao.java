package com.xpsoft.oa.dao.info;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.NewsType;
import java.util.List;

public abstract interface NewsTypeDao extends BaseDao<NewsType>
{
  public abstract Short getTop();

  public abstract NewsType findBySn(Short paramShort);

  public abstract List<NewsType> getAllBySn();

  public abstract List<NewsType> getAllBySn(PagingBean paramPagingBean);

  public abstract List<NewsType> findBySearch(NewsType paramNewsType, PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.dao.info.NewsTypeDao
 * JD-Core Version:    0.6.0
 */