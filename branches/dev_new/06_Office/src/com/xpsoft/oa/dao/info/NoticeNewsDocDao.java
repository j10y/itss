package com.xpsoft.oa.dao.info;

import java.util.List;

import com.xpsoft.core.dao.BaseDao;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.Notice;
import com.xpsoft.oa.model.info.NoticeNewsDoc;

public abstract interface NoticeNewsDocDao extends BaseDao<NoticeNewsDoc>
{
  public abstract List<NoticeNewsDoc> findByNoticeId(Long paramLong);

}
