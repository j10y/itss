package com.xpsoft.oa.service.archive;

import com.xpsoft.core.service.BaseService;
import com.xpsoft.core.web.paging.PagingBean;
import com.xpsoft.oa.model.archive.ArchDispatch;
import com.xpsoft.oa.model.system.AppUser;
import java.util.List;

public abstract interface ArchDispatchService extends BaseService<ArchDispatch> {
	public abstract List<ArchDispatch> findByUser(AppUser paramAppUser,
			PagingBean paramPagingBean);

	public abstract int countArchDispatch(Long paramLong);
	
	/**
	 * 
	 * @param archivesId
	 * @param type 0传阅 1承办
	 * @return
	 */
	public int countArchDispatch(Long archivesId, Short type);
}
