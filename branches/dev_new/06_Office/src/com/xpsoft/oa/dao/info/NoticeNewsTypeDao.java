package com.xpsoft.oa.dao.info;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.NoticeNewsType;
import java.util.List;

public abstract interface NoticeNewsTypeDao extends BaseDao<NoticeNewsType>
{
  public abstract Short getTop();

  public abstract NoticeNewsType findBySn(Short paramShort);

  public abstract List<NoticeNewsType> getAllBySn();

  public abstract List<NoticeNewsType> getAllBySn(PagingBean paramPagingBean);

  public abstract List<NoticeNewsType> findBySearch(NoticeNewsType paramNewsType, PagingBean paramPagingBean);
}
