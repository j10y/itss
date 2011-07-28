package com.xpsoft.oa.service.info;

import java.util.List;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.oa.model.info.NoticeNewsDoc;

public abstract interface NoticeNewsDocService extends BaseService<NoticeNewsDoc>
{
  public abstract List<NoticeNewsDoc> findByNid(Long paramLong);
}
