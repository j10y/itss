package com.xpsoft.oa.service.info;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.News;
import java.util.List;

public abstract interface NewsService extends BaseService<News>
{
  public abstract List<News> findByTypeId(Long paramLong, PagingBean paramPagingBean);

  public abstract List<News> findBySearch(String paramString, PagingBean paramPagingBean);

  public abstract List<News> findImageNews(PagingBean paramPagingBean);
}

/* Location:           C:\Users\Jack\Downloads\oa\joffice131Tomcat6\joffice131Tomcat6\tomcat6-joffice\webapps\joffice1.3.1\WEB-INF\classes\
 * Qualified Name:     com.xpsoft.oa.service.info.NewsService
 * JD-Core Version:    0.6.0
 */