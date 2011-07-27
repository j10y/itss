package com.xpsoft.oa.service.info;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.NoticeNews;
import java.util.List;

public abstract interface NoticeNewsService extends BaseService<NoticeNews>
{
  public abstract List<NoticeNews> findByTypeId(Long paramLong, PagingBean paramPagingBean);

  public abstract List<NoticeNews> findBySearch(String paramString, PagingBean paramPagingBean);

  public abstract List<NoticeNews> findImageNews(PagingBean paramPagingBean);
}
