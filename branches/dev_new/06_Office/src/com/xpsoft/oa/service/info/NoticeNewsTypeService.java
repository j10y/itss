package com.xpsoft.oa.service.info;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.info.NoticeNewsType;
import java.util.List;

public abstract interface NoticeNewsTypeService extends BaseService<NoticeNewsType> {
	public abstract Short getTop();

	public abstract NoticeNewsType findBySn(Short paramShort);

	public abstract List<NoticeNewsType> getAllBySn();

	public abstract List<NoticeNewsType> getAllBySn(PagingBean paramPagingBean);

	public abstract List<NoticeNewsType> findBySearch(NoticeNewsType paramNewsType,
			PagingBean paramPagingBean);
}
